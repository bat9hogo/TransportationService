package transportation.drivers.service;

import transportation.drivers.dto.CarDto;

import java.util.List;

public interface CarService {
    CarDto createCar(CarDto dto);
    CarDto updateCar(Long id, CarDto dto);
    CarDto getCarById(Long id);
    List<CarDto> getAllCars();
    void deleteCar(Long id);
}
