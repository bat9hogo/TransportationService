package transportation.drivers.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transportation.drivers.dto.DriverDto;
import transportation.drivers.entity.Driver;
import transportation.drivers.mapper.CarMapper;
import transportation.drivers.mapper.DriverMapper;
import transportation.drivers.repository.CarRepository;
import transportation.drivers.repository.DriverRepository;

import java.util.List;

@Service

public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    public DriverServiceImpl(DriverRepository driverRepository, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
    }

    @Override
    @Transactional
    public DriverDto createDriver(DriverDto dto) {
        Driver driver = driverMapper.toEntity(dto);
        Driver savedDriver = driverRepository.save(driver);
        if (driver.getCars() != null) {
            driver.getCars().forEach(car -> car.setDriver(savedDriver));
        }
        return driverMapper.toDto(driverRepository.save(savedDriver));
    }

    @Override
    @Transactional
    public DriverDto updateDriver(Long id, DriverDto dto) {
        Driver existing = driverRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        existing.setFirstName(dto.firstName());
        existing.setLastName(dto.lastName());
        existing.setEmail(dto.email());
        existing.setPhoneNumber(dto.phoneNumber());
        return driverMapper.toDto(driverRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public DriverDto getDriverById(Long id) {
        return driverRepository.findByIdAndDeletedFalse(id)
                .map(driverMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverDto> getAllDrivers() {
        return driverMapper.toDtoList(driverRepository.findAllByDeletedFalse());
    }

    @Override
    @Transactional
    public void deleteDriver(Long id) {
        Driver existing = driverRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        existing.setDeleted(true);
        driverRepository.save(existing);
    }
}
