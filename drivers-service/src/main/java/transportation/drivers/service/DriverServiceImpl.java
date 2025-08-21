package transportation.drivers.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transportation.drivers.dto.CreateCarRequestDto;
import transportation.drivers.dto.CreateDriverRequestDto;
import transportation.drivers.dto.DriverResponseDto;
import transportation.drivers.dto.UpdateDriverRequestDto;
import transportation.drivers.entity.Car;
import transportation.drivers.entity.Driver;
import transportation.drivers.exception.DriverConflictException;
import transportation.drivers.exception.NotFoundException;
import transportation.drivers.mapper.CarMapper;
import transportation.drivers.mapper.DriverMapper;
import transportation.drivers.repository.CarRepository;
import transportation.drivers.repository.DriverRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final CarRepository carRepository;
    private final DriverMapper driverMapper;
    private final CarMapper carMapper;

    private static final Pattern LICENSE_PLATE_PATTERN = Pattern.compile("^[0-9]{4}\\s?[A-Z]{2}-[1-7]$");

    public DriverServiceImpl(
            DriverRepository driverRepository,
            CarRepository carRepository,
            DriverMapper driverMapper,
            CarMapper carMapper
    ) {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.driverMapper = driverMapper;
        this.carMapper = carMapper;
    }

    @Override
    @Transactional
    public DriverResponseDto createDriver(CreateDriverRequestDto dto) {
        Driver restoredDriver = restoreDeletedDriver(dto);
        Driver driver = restoredDriver != null ? restoredDriver : driverMapper.toEntity(dto);
        driver = driverRepository.save(driver);

        handleCars(driver, dto.carIds(), dto.carsToCreate());
        driver = driverRepository.save(driver);

        return driverMapper.toResponseDto(driver);
    }

    @Override
    @Transactional
    public DriverResponseDto updateDriver(String driverId, UpdateDriverRequestDto dto) {
        Driver driver = driverRepository.findByIdAndDeletedFalse(driverId)
                .orElseThrow(() -> new NotFoundException("Driver not found with id " + driverId));

        if (dto.firstName() != null) driver.setFirstName(dto.firstName());
        if (dto.lastName() != null) driver.setLastName(dto.lastName());
        if (dto.email() != null) driver.setEmail(dto.email());
        if (dto.phoneNumber() != null) driver.setPhoneNumber(dto.phoneNumber());

        handleCars(driver, dto.carIds(), dto.carsToCreate());
        driver = driverRepository.save(driver);

        return driverMapper.toResponseDto(driver);
    }

    @Override
    @Transactional(readOnly = true)
    public DriverResponseDto getDriverById(String driverId) {
        return driverRepository.findByIdAndDeletedFalse(driverId)
                .map(driverMapper::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Driver not found with id " + driverId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverResponseDto> getAllDrivers() {
        return driverMapper.toResponseDtoList(driverRepository.findAllByDeletedFalse());
    }

    @Override
    @Transactional
    public void deleteDriver(String driverId) {
        Driver driver = driverRepository.findByIdAndDeletedFalse(driverId)
                .orElseThrow(() -> new NotFoundException("Driver not found with id " + driverId));
        driver.setDeleted(true);
        driverRepository.save(driver);

        List<Car> cars = carRepository.findAllByIdInAndDeletedFalse(driver.getCarIds());
        cars.forEach(car -> car.setDriverId(null));
        carRepository.saveAll(cars);
    }

    private Driver restoreDeletedDriver(CreateDriverRequestDto dto) {
        Driver deletedDriverToRestore = null;

        if (dto.email() != null && !dto.email().isBlank()) {
            Optional<Driver> byEmail = driverRepository.findByEmail(dto.email());
            if (byEmail.isPresent()) {
                Driver driver = byEmail.get();
                if (!driver.isDeleted()) throw new DriverConflictException("Email already in use " + dto.email());
                deletedDriverToRestore = driver;
            }
        }

        if (dto.phoneNumber() != null && !dto.phoneNumber().isBlank()) {
            Optional<Driver> byPhone = driverRepository.findByPhoneNumber(dto.phoneNumber());
            if (byPhone.isPresent()) {
                Driver driver = byPhone.get();
                if (!driver.isDeleted()) throw new DriverConflictException("Phone number already in use " + dto.phoneNumber());
                if (deletedDriverToRestore == null) deletedDriverToRestore = driver;
                else if (!deletedDriverToRestore.equals(driver))
                    throw new DriverConflictException("Email and phone number belong to different deleted drivers");
            }
        }

        if (deletedDriverToRestore != null) {
            deletedDriverToRestore.setDeleted(false);
            deletedDriverToRestore.setFirstName(dto.firstName());
            deletedDriverToRestore.setLastName(dto.lastName());
            if (dto.email() != null && !dto.email().isBlank()) deletedDriverToRestore.setEmail(dto.email());
            if (dto.phoneNumber() != null && !dto.phoneNumber().isBlank())
                deletedDriverToRestore.setPhoneNumber(dto.phoneNumber());
            return driverRepository.save(deletedDriverToRestore);
        }

        return null;
    }

    private void handleCars(Driver driver, List<String> carIds, List<CreateCarRequestDto> carsToCreate) {
        List<String> finalCarIds = new ArrayList<>();

        if (driver.getCarIds() != null && !driver.getCarIds().isEmpty()) {
            List<Car> oldCars = carRepository.findAllByIdInAndDeletedFalse(driver.getCarIds());
            oldCars.forEach(car -> car.setDriverId(null));
            carRepository.saveAll(oldCars);
        }

        if (carIds != null && !carIds.isEmpty()) {
            List<Car> existingCars = carRepository.findAllByIdInAndDeletedFalse(carIds);
            if (existingCars.size() != carIds.size())
                throw new NotFoundException("One or more carIds do not exist or are deleted");
            existingCars.forEach(car -> {
                car.setDriverId(driver.getId());
                finalCarIds.add(car.getId());
            });
            carRepository.saveAll(existingCars);
        }

        if (carsToCreate != null && !carsToCreate.isEmpty()) {
            for (CreateCarRequestDto carDto : carsToCreate) {
                if (carDto.licensePlate() == null || !LICENSE_PLATE_PATTERN.matcher(carDto.licensePlate()).matches()) {
                    throw new DriverConflictException("Invalid license plate format: " + carDto.licensePlate());
                }

                Car car = createOrRestoreCar(carDto, driver.getId());
                finalCarIds.add(car.getId());
            }
        }

        driver.setCarIds(finalCarIds);
    }

    private Car createOrRestoreCar(CreateCarRequestDto dto, String driverId) {
        Car car = carRepository.findByLicensePlateAndDeletedFalse(dto.licensePlate()).orElseGet(() -> carMapper.toEntity(dto));

        if (car.isDeleted()) car.setDeleted(false);

        car.setBrand(dto.brand());
        car.setColor(dto.color());
        car.setDriverId(driverId);

        Car savedCar = carRepository.save(car);

        if (savedCar.getDriverId() != null) {
            Driver driver = driverRepository.findByIdAndDeletedFalse(savedCar.getDriverId()).orElse(null);
            if (driver != null) {
                List<String> driverCarIds = driver.getCarIds() != null ? driver.getCarIds() : new ArrayList<>();
                if (!driverCarIds.contains(savedCar.getId())) {
                    driverCarIds.add(savedCar.getId());
                    driver.setCarIds(driverCarIds);
                    driverRepository.save(driver);
                }
            }
        }

        return savedCar;
    }
}
