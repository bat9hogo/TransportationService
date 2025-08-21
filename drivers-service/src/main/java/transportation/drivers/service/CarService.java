package transportation.drivers.service;

import transportation.drivers.dto.CarRequestDto;
import transportation.drivers.dto.CarUpdateDto;
import transportation.drivers.dto.CarResponseDto;

import java.util.List;

public interface CarService {

    CarResponseDto createCar(CarRequestDto dto);

    CarResponseDto updateCar(String id, CarUpdateDto dto);

    CarResponseDto getCarById(String id);

    List<CarResponseDto> getAllCars();

    void deleteCar(String id);
}
