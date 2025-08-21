package transportation.drivers.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transportation.drivers.dto.CreateCarRequestDto;
import transportation.drivers.dto.UpdateCarRequestDto;
import transportation.drivers.dto.CarResponseDto;
import transportation.drivers.entity.Car;
import transportation.drivers.entity.Driver;
import transportation.drivers.exception.DriverConflictException;
import transportation.drivers.exception.NotFoundException;
import transportation.drivers.mapper.CarMapper;
import transportation.drivers.repository.CarRepository;
import transportation.drivers.repository.DriverRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final DriverRepository driverRepository;
    private final CarMapper carMapper;

    private static final Pattern LICENSE_PLATE_PATTERN = Pattern.compile("^[0-9]{4}\\s?[A-Z]{2}-[1-7]$");

    public CarServiceImpl(CarRepository carRepository, DriverRepository driverRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.driverRepository = driverRepository;
        this.carMapper = carMapper;
    }

    @Override
    @Transactional
    public CarResponseDto createCar(CreateCarRequestDto dto) {
        validateLicensePlate(dto.licensePlate());

        Car car = carRepository.findByLicensePlateAndDeletedFalse(dto.licensePlate()).orElseGet(() -> carMapper.toEntity(dto));

        if (car.isDeleted()) car.setDeleted(false);
        car.setBrand(dto.brand());
        car.setColor(dto.color());

        if (dto.driverId() != null) {
            Driver driver = driverRepository.findByIdAndDeletedFalse(dto.driverId())
                    .orElseThrow(() -> new NotFoundException("Driver not found with id " + dto.driverId()));
            car.setDriverId(driver.getId());
        }

        Car savedCar = carRepository.save(car);

        if (savedCar.getDriverId() != null) updateDriverCarIds(savedCar);

        return carMapper.toDto(savedCar);
    }

    @Override
    @Transactional
    public CarResponseDto updateCar(String carId, UpdateCarRequestDto dto) {
        Car car = carRepository.findByIdAndDeletedFalse(carId)
                .orElseThrow(() -> new NotFoundException("Car not found with id " + carId));

        validateLicensePlate(dto.licensePlate());
        carMapper.updateEntityFromDto(dto, car);

        if (dto.driverId() != null) {
            Driver driver = driverRepository.findByIdAndDeletedFalse(dto.driverId())
                    .orElseThrow(() -> new NotFoundException("Driver not found with id " + dto.driverId()));
            car.setDriverId(driver.getId());
        }

        Car savedCar = carRepository.save(car);
        updateDriverCarIds(savedCar);

        return carMapper.toDto(savedCar);
    }

    @Override
    @Transactional(readOnly = true)
    public CarResponseDto getCarById(String carId) {
        Car car = carRepository.findByIdAndDeletedFalse(carId)
                .orElseThrow(() -> new NotFoundException("Car not found with id " + carId));
        return carMapper.toDto(car);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarResponseDto> getAllCars() {
        return carMapper.toDtoList(carRepository.findAllByDeletedFalse());
    }

    @Override
    @Transactional
    public void deleteCar(String carId) {
        Car car = carRepository.findByIdAndDeletedFalse(carId)
                .orElseThrow(() -> new NotFoundException("Car not found with id " + carId));
        car.setDeleted(true);
        carRepository.save(car);

        if (car.getDriverId() != null) {
            Driver driver = driverRepository.findByIdAndDeletedFalse(car.getDriverId()).orElse(null);
            if (driver != null && driver.getCarIds() != null) {
                driver.getCarIds().remove(car.getId());
                driverRepository.save(driver);
            }
        }
    }

    private void validateLicensePlate(String licensePlate) {
        if (licensePlate == null || !LICENSE_PLATE_PATTERN.matcher(licensePlate).matches()) {
            throw new DriverConflictException("Invalid license plate format: " + licensePlate);
        }
    }

    private void updateDriverCarIds(Car car) {
        if (car.getDriverId() == null) return;

        Driver driver = driverRepository.findByIdAndDeletedFalse(car.getDriverId()).orElse(null);
        if (driver == null) return;

        List<String> driverCarIds = driver.getCarIds() != null ? driver.getCarIds() : new ArrayList<>();
        if (!driverCarIds.contains(car.getId())) driverCarIds.add(car.getId());
        driver.setCarIds(driverCarIds);
        driverRepository.save(driver);
    }
}
