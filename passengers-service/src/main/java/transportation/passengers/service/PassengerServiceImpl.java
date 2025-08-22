package transportation.passengers.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transportation.passengers.dto.CreatePassengerRequestDto;
import transportation.passengers.dto.PassengerResponseDto;
import transportation.passengers.dto.UpdatePassengerRequestDto;
import transportation.passengers.dto.RestorePassengerRequestDto;
import transportation.passengers.entity.Passenger;
import transportation.passengers.mapper.PassengerMapper;
import transportation.passengers.repository.PassengerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    public PassengerServiceImpl(PassengerRepository passengerRepository, PassengerMapper passengerMapper) {
        this.passengerRepository = passengerRepository;
        this.passengerMapper = passengerMapper;
    }

    @Override
    @Transactional
    public PassengerResponseDto createPassenger(CreatePassengerRequestDto dto) {
        checkDuplicateEmailOrPhone(dto.email(), dto.phoneNumber(), null);

        Passenger newPassenger = passengerMapper.toEntity(dto);
        Passenger savedPassenger = passengerRepository.save(newPassenger);
        return passengerMapper.toResponseDto(savedPassenger);
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerResponseDto getPassengerById(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id));
        return passengerMapper.toResponseDto(passenger);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerResponseDto> getAllPassengers() {
        return passengerRepository.findAllByDeletedFalse().stream()
                .map(passengerMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PassengerResponseDto updatePassenger(Long id, UpdatePassengerRequestDto dto) {
        Passenger passenger = passengerRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id));

        checkDuplicateEmailOrPhone(dto.email(), dto.phoneNumber(), id);

        if (dto.email() != null && !dto.email().isBlank()) {
            passenger.setEmail(dto.email());
        }

        if (dto.phoneNumber() != null && !dto.phoneNumber().isBlank()) {
            passenger.setPhoneNumber(dto.phoneNumber());
        }

        if (dto.firstName() != null) {
            passenger.setFirstName(dto.firstName());
        }

        if (dto.lastName() != null) {
            passenger.setLastName(dto.lastName());
        }

        Passenger updated = passengerRepository.save(passenger);
        return passengerMapper.toResponseDto(updated);
    }

    @Override
    @Transactional
    public void deletePassenger(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id));
        passenger.setDeleted(true);
        passengerRepository.save(passenger);
    }

    @Override
    @Transactional
    public PassengerResponseDto restorePassenger(RestorePassengerRequestDto request) {
        String email = request.email();
        String phone = request.phoneNumber();

        boolean hasEmail = email != null && !email.isBlank();
        boolean hasPhone = phone != null && !phone.isBlank();

        if (!hasEmail && !hasPhone) {
            throw new IllegalArgumentException("Either email or phone must be provided");
        }
        if (hasEmail && hasPhone) {
            throw new IllegalArgumentException("Only one of email or phone should be provided");
        }

        Passenger toRestore = hasEmail
                ? passengerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No passenger found by email: " + email))
                : passengerRepository.findByPhoneNumber(phone)
                .orElseThrow(() -> new IllegalArgumentException("No passenger found by phone: " + phone));

        if (!toRestore.isDeleted()) {
            throw new IllegalArgumentException("Passenger is not deleted: " + (hasEmail ? email : phone));
        }

        checkDuplicateEmailOrPhone(toRestore.getEmail(), toRestore.getPhoneNumber(),toRestore.getId());
        toRestore.setDeleted(false);
        Passenger restored = passengerRepository.save(toRestore);
        return passengerMapper.toResponseDto(restored);
    }

    private void checkDuplicateEmailOrPhone(String email, String phone, Long excludeId) {
        if (email != null && !email.isBlank()) {
            passengerRepository.findByEmail(email)
                    .filter(passenger -> !passenger.isDeleted() && (excludeId == null || !passenger.getId().equals(excludeId)))
                    .ifPresent(existing -> {
                        throw new IllegalArgumentException("Email is already in use: " + email);
                    });
        }

        if (phone != null && !phone.isBlank()) {
            passengerRepository.findByPhoneNumber(phone)
                    .filter(passenger -> !passenger.isDeleted() && (excludeId == null || !passenger.getId().equals(excludeId)))
                    .ifPresent(existing -> {
                        throw new IllegalArgumentException("Phone number is already in use: " + phone);
                    });
        }
    }
}
