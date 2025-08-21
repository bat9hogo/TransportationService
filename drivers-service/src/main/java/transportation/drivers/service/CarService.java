package transportation.drivers.service;

import transportation.drivers.dto.CreateCarRequestDto;
import transportation.drivers.dto.UpdateCarRequestDto;
import transportation.drivers.dto.CarResponseDto;

import java.util.List;

public interface CarService {

    CarResponseDto createCar(CreateCarRequestDto dto);

    CarResponseDto updateCar(String id, UpdateCarRequestDto dto);

    CarResponseDto getCarById(String id);

    List<CarResponseDto> getAllCars();

    void deleteCar(String id);
}
