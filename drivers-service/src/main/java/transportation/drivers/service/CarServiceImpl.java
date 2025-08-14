package transportation.drivers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transportation.drivers.dto.CarDto;
import transportation.drivers.entity.Car;
import transportation.drivers.exception.NotFoundException;
import transportation.drivers.mapper.CarMapper;
import transportation.drivers.repository.CarRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;
    private CarMapper carMapper;

    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    public CarDto createCar(CarDto dto) {
        Car car = carMapper.toEntity(dto);
        Car savedCar = carRepository.save(car);
        return carMapper.toDto(savedCar);
    }

    @Override
    public CarDto updateCar(String id, CarDto dto) {
        Car existing = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Car not found"));

        existing.setColor(dto.color());
        existing.setBrand(dto.brand());
        existing.setLicensePlate(dto.licensePlate());

        return carMapper.toDto(carRepository.save(existing));
    }

    @Override
    public CarDto getCarById(String id) {
        return carRepository.findByIdAndDeletedFalse(id)
                .map(carMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Car not found"));
    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAllByDeletedFalse()
                .stream()
                .map(carMapper::toDto)
                .toList();
    }

    @Override
    public void deleteCar(String id) {
        Car existing = carRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Car not found"));
        existing.setDeleted(true);
        carRepository.save(existing);
    }
}
