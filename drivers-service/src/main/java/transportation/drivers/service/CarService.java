package transportation.drivers.service;

import transportation.drivers.dto.CarDto;
import java.util.List;

public interface CarService {

    CarDto createCar(CarDto dto);
    CarDto updateCar(String carId, CarDto dto);  // если используешь автоинкрементный int
    CarDto getCarById(String carId);
    List<CarDto> getAllCars();
    void deleteCar(String carId);
}
