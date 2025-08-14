package transportation.drivers.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transportation.drivers.dto.CarDto;
import transportation.drivers.entity.Car;
import transportation.drivers.mapper.CarMapper;
import transportation.drivers.repository.CarRepository;
import transportation.drivers.service.CarService;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    @Transactional
    public CarDto createCar(CarDto dto) {
        Car car = carMapper.toEntity(dto);
        return carMapper.toDto(carRepository.save(car));
    }

    @Override
    @Transactional
    public CarDto updateCar(Long id, CarDto dto) {
        Car existing = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        existing.setColor(dto.color());
        existing.setBrand(dto.brand());
        existing.setLicensePlate(dto.licensePlate());
        return carMapper.toDto(carRepository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public CarDto getCarById(Long id) {
        return carRepository.findByIdAndDeletedFalse(id)
                .map(carMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarDto> getAllCars() {
        return carRepository.findAllByDeletedFalse()
                .stream()
                .map(carMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteCar(Long id) {
        Car existing = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        existing.setDeleted(true);
        carRepository.save(existing);
    }
}
