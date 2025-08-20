package transportation.passengers.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transportation.passengers.dto.PassengerRequestDto;
import transportation.passengers.dto.PassengerResponseDto;
import transportation.passengers.dto.PassengerUpdateDto;
import transportation.passengers.entity.Passenger;
import transportation.passengers.mapper.PassengerMapper;
import transportation.passengers.repository.PassengerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository repository;
    private final PassengerMapper mapper;

    public PassengerServiceImpl(PassengerRepository repository, PassengerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public PassengerResponseDto createPassenger(PassengerRequestDto dto) {
        Optional<Passenger> byEmail = repository.findByEmail(dto.getEmail());
        if (byEmail.isPresent()) {
            Passenger existing = byEmail.get();
            if (existing.isDeleted()) {
                existing.setDeleted(false);
                existing.setFirstName(dto.getFirstName());
                existing.setLastName(dto.getLastName());
                existing.setPhoneNumber(dto.getPhoneNumber());
                return mapper.toResponseDto(repository.save(existing));
            } else {
                throw new IllegalArgumentException("Email also used");
            }
        }

        Optional<Passenger> byPhone = repository.findByPhoneNumber(dto.getPhoneNumber());
        if (byPhone.isPresent()) {
            Passenger existing = byPhone.get();
            if (existing.isDeleted()) {
                existing.setDeleted(false);
                existing.setFirstName(dto.getFirstName());
                existing.setLastName(dto.getLastName());
                existing.setEmail(dto.getEmail());
                return mapper.toResponseDto(repository.save(existing));
            } else {
                throw new IllegalArgumentException("Phone number also used");
            }
        }

        Passenger entity = mapper.toEntity(dto);
        Passenger saved = repository.save(entity);
        return mapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerResponseDto getPassengerById(Long id) {
        Passenger entity = repository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id));
        return mapper.toResponseDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerResponseDto> getAllPassengers() {
        return repository.findAllByDeletedFalse().stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PassengerResponseDto updatePassenger(Long id, PassengerUpdateDto dto) {
        Passenger entity = repository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id));

        mapper.updateEntityFromDto(dto, entity);
        return mapper.toResponseDto(repository.save(entity));
    }

    @Override
    @Transactional
    public void deletePassenger(Long id) {
        Passenger entity = repository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id));
        entity.setDeleted(true);
        repository.save(entity);
    }
}
