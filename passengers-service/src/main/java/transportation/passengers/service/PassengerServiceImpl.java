package transportation.passengers.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transportation.passengers.dto.PassengerRequestDto;
import transportation.passengers.dto.PassengerResponseDto;
import transportation.passengers.dto.PassengerUpdateDto;
import transportation.passengers.entity.Passenger;
import transportation.passengers.exception.PassengerConflictException;
import transportation.passengers.mapper.PassengerMapper;
import transportation.passengers.repository.PassengerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    public PassengerServiceImpl(PassengerRepository passengerRepository, PassengerMapper passengerMapper) {
        this.passengerRepository = passengerRepository;
        this.passengerMapper = passengerMapper;
    }

    @Transactional
    public PassengerResponseDto createPassenger(PassengerRequestDto dto) {
        Passenger restoredPassenger = restoreDeletedPassenger(dto);
        if (restoredPassenger != null) {
            return passengerMapper.toResponseDto(restoredPassenger);
        }

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
    public PassengerResponseDto updatePassenger(Long id, PassengerUpdateDto passengerUpdate) {
        Passenger passenger = passengerRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id));

        passengerMapper.updateEntityFromDto(passengerUpdate, passenger);
        Passenger updatedPassenger = passengerRepository.save(passenger);
        return passengerMapper.toResponseDto(updatedPassenger);
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


    private Passenger restoreDeletedPassenger(PassengerRequestDto dto) {
        Passenger deletedPassengerToRestore = null;

        if (dto.email() != null && !dto.email().isBlank()) {
            Optional<Passenger> passengerByEmail = passengerRepository.findByEmail(dto.email());
            if (passengerByEmail.isPresent()) {
                Passenger p = passengerByEmail.get();
                if (!p.isDeleted()) {
                    throw new PassengerConflictException(
                            "Can't create a user, because the email address '" + dto.email() + "' is already in use"
                    );
                } else {
                    deletedPassengerToRestore = p;
                }
            }
        }

        if (dto.phoneNumber() != null && !dto.phoneNumber().isBlank()) {
            Optional<Passenger> passengerByPhone = passengerRepository.findByPhoneNumber(dto.phoneNumber());
            if (passengerByPhone.isPresent()) {
                Passenger p = passengerByPhone.get();
                if (!p.isDeleted()) {
                    throw new PassengerConflictException(
                            "Can't create a user, because the phone number '" + dto.phoneNumber() + "' is already in use"
                    );
                } else {
                    if (deletedPassengerToRestore == null) {
                        deletedPassengerToRestore = p;
                    } else if (!deletedPassengerToRestore.equals(p)) {
                        throw new PassengerConflictException(
                                "Can't restore a user: email and phone number belong to different deleted passengers"
                        );
                    }
                }
            }
        }

        if (deletedPassengerToRestore != null) {
            deletedPassengerToRestore.setDeleted(false);
            deletedPassengerToRestore.setFirstName(dto.firstName());
            deletedPassengerToRestore.setLastName(dto.lastName());
            if (dto.email() != null && !dto.email().isBlank()) {
                deletedPassengerToRestore.setEmail(dto.email());
            }
            if (dto.phoneNumber() != null && !dto.phoneNumber().isBlank()) {
                deletedPassengerToRestore.setPhoneNumber(dto.phoneNumber());
            }
            return passengerRepository.save(deletedPassengerToRestore);
        }

        return null;
    }

}
