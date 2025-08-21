package transportation.drivers.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transportation.drivers.dto.CreateCarRequestDto;
import transportation.drivers.dto.CreateDriverRequestDto;
import transportation.drivers.dto.DriverResponseDto;
import transportation.drivers.dto.RestoreDriverRequestDto;
import transportation.drivers.dto.UpdateDriverRequestDto;
import transportation.drivers.entity.Car;
import transportation.drivers.entity.Driver;
import transportation.drivers.exception.NotFoundException;
import transportation.drivers.mapper.CarMapper;
import transportation.drivers.mapper.DriverMapper;
import transportation.drivers.repository.CarRepository;
import transportation.drivers.repository.DriverRepository;

import java.util.ArrayList;
import java.util.List;
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
        checkDuplicateEmailOrPhone(dto.email(), dto.phoneNumber(), null);

        Driver driver = driverMapper.toEntity(dto);
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

        checkDuplicateEmailOrPhone(dto.email(), dto.phoneNumber(), driverId);

        if (dto.email() != null && !dto.email().isBlank()){
            checkDuplicateEmailOrPhone(dto.email(), null, driverId);
            driver.setEmail(dto.email());
        }

        if (dto.phoneNumber() != null && !dto.phoneNumber().isBlank()){
            checkDuplicateEmailOrPhone(null, dto.phoneNumber(), driverId);
            driver.setPhoneNumber(dto.phoneNumber());
        }

        if (dto.firstName() != null){
            driver.setFirstName(dto.firstName());
        }

        if (dto.lastName() != null){
            driver.setLastName(dto.lastName());
        }

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

    @Override
    @Transactional
    public DriverResponseDto restoreDriver(RestoreDriverRequestDto request) {
        String email = request.email();
        String phone = request.phoneNumber();

        if ((email == null || email.isBlank()) && (phone == null || phone.isBlank())) {
            throw new IllegalArgumentException("Either email or phone must be provided");
        }
        if (email != null && phone != null && !email.isBlank() && !phone.isBlank()) {
            throw new IllegalArgumentException("Only one of email or phone should be provided");
        }

        Driver driverToRestore;

        if (email != null && !email.isBlank()) {
            driverToRestore = driverRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("No driver found with email: " + email));
        } else {
            driverToRestore = driverRepository.findByPhoneNumber(phone)
                    .orElseThrow(() -> new IllegalArgumentException("No driver found with phone: " + phone));
        }

        if (!driverToRestore.isDeleted()) {
            throw new IllegalArgumentException("Driver is not deleted: " + (email != null ? email : phone));
        }

        checkDuplicateEmailOrPhone(driverToRestore.getEmail(), driverToRestore.getPhoneNumber(), driverToRestore.getId());

        driverToRestore.setDeleted(false);
        Driver restored = driverRepository.save(driverToRestore);


        if (restored.getCarIds() != null && !restored.getCarIds().isEmpty()) {
            List<Car> cars = carRepository.findAllByIdInAndDeletedFalse(restored.getCarIds());
            cars.forEach(car -> car.setDriverId(restored.getId()));
            carRepository.saveAll(cars);
        }

        return driverMapper.toResponseDto(restored);
    }

    private void checkDuplicateEmailOrPhone(String email, String phone, String excludeDriverId) {
        if (email != null && !email.isBlank()) {
            driverRepository.findAllByEmail(email)
                    .stream()
                    .filter(d -> !d.isDeleted() && (excludeDriverId == null || !d.getId().equals(excludeDriverId)))
                    .findFirst()
                    .ifPresent(d -> {
                        throw new IllegalArgumentException("Email is already in use: " + email);
                    });
        }

        if (phone != null && !phone.isBlank()) {
            driverRepository.findAllByPhoneNumber(phone)
                    .stream()
                    .filter(d -> !d.isDeleted() && (excludeDriverId == null || !d.getId().equals(excludeDriverId)))
                    .findFirst()
                    .ifPresent(d -> {
                        throw new IllegalArgumentException("Phone number is already in use: " + phone);
                    });
        }
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
                    throw new IllegalArgumentException("Invalid license plate format: " + carDto.licensePlate());
                }

                Car car = createOrRestoreCar(carDto, driver.getId());
                finalCarIds.add(car.getId());
            }
        }

        driver.setCarIds(finalCarIds);
    }

    private Car createOrRestoreCar(CreateCarRequestDto dto, String driverId) {
        Car car = carRepository.findByLicensePlateAndDeletedFalse(dto.licensePlate())
                .orElseGet(() -> carMapper.toEntity(dto));

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
