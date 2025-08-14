package transportation.drivers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transportation.drivers.dto.DriverDto;
import transportation.drivers.entity.Car;
import transportation.drivers.entity.Driver;
import transportation.drivers.exception.NotFoundException;
import transportation.drivers.mapper.DriverMapper;
import transportation.drivers.repository.CarRepository;
import transportation.drivers.repository.DriverRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private DriverRepository driverRepository;
    private CarRepository carRepository;
    private DriverMapper driverMapper;

    public DriverServiceImpl
            (DriverRepository driverRepository, CarRepository carRepository,
             DriverMapper driverMapper
            ) {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.driverMapper = driverMapper;
    }

    @Override
    public DriverDto createDriver(DriverDto dto) {
        Driver driver = driverMapper.toEntity(dto);
        Driver savedDriver = driverRepository.save(driver);

        if (dto.carIds() != null && !dto.carIds().isEmpty()) {
            List<Car> cars = carRepository.findAllByIdIn(dto.carIds());
            cars.forEach(car -> car.setDriverId(savedDriver.getId()));
            carRepository.saveAll(cars);

            savedDriver.setCarIds(cars.stream().map(Car::getId).toList());
            driverRepository.save(savedDriver);
        }

        return driverMapper.toDto(savedDriver);
    }

    @Override
    public DriverDto updateDriver(String id, DriverDto dto) {
        Driver existing = driverRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Driver not found"));

        existing.setFirstName(dto.firstName());
        existing.setLastName(dto.lastName());
        existing.setEmail(dto.email());
        existing.setPhoneNumber(dto.phoneNumber());

        if (dto.carIds() != null) {
            if (existing.getCarIds() != null) {
                List<Car> oldCars = carRepository.findAllByIdIn(existing.getCarIds());
                oldCars.forEach(car -> car.setDriverId(null));
                carRepository.saveAll(oldCars);
            }

            List<Car> newCars = carRepository.findAllByIdIn(dto.carIds());
            newCars.forEach(car -> car.setDriverId(existing.getId()));
            carRepository.saveAll(newCars);

            existing.setCarIds(newCars.stream().map(Car::getId).toList());
        }

        return driverMapper.toDto(driverRepository.save(existing));
    }

    @Override
    public DriverDto getDriverById(String id) {
        return driverRepository.findByIdAndDeletedFalse(id)
                .map(driverMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Driver not found"));
    }

    @Override
    public List<DriverDto> getAllDrivers() {
        return driverMapper.toDtoList(driverRepository.findAllByDeletedFalse());
    }

    @Override
    public void deleteDriver(String id) {
        Driver existing = driverRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Driver not found"));
        existing.setDeleted(true);
        driverRepository.save(existing);
    }
}
