package transportation.drivers.service;

import transportation.drivers.dto.*;

import java.util.List;

public interface CarService {

    CarResponseDto createCar(CreateCarRequestDto dto);

    CarResponseDto updateCar(String id, UpdateCarRequestDto dto);

    CarResponseDto getCarById(String id);

    List<CarResponseDto> getAllCars();

    void deleteCar(String id);

    CarResponseDto restoreCar(RestoreCarRequestDto dto);
}
