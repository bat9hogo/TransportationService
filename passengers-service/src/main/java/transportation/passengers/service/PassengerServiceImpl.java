package transportation.passengers.service;

import transportation.passengers.dto.PassengerDto;
import transportation.passengers.entity.Passenger;
import transportation.passengers.mapper.PassengerMapper;
import transportation.passengers.repository.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public PassengerDto createPassenger(PassengerDto dto) {
        Passenger entity = mapper.toEntity(dto);
        Passenger saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerDto getPassengerById(Long id) {
        Passenger entity = repository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id));
        return mapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerDto> getAllPassengers() {
        return repository.findAllByDeletedFalse().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public PassengerDto updatePassenger(Long id, PassengerDto dto) {
        Passenger entity = repository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id));
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setEmail(dto.email());
        entity.setPhoneNumber(dto.phoneNumber());
        return mapper.toDto(repository.save(entity));
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

