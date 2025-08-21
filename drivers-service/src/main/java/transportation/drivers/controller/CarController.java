package transportation.drivers.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import transportation.drivers.dto.CarDto;
import transportation.drivers.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<CarDto> createCar(@Valid @RequestBody CarDto dto) {
        CarDto created = carService.createCar(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{carId}")
    public ResponseEntity<CarDto> updateCar(@PathVariable("carId") String carId,
                                            @Valid @RequestBody CarDto dto) {
        CarDto updated = carService.updateCar(carId, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<CarDto> getCarById(@PathVariable("carId") String carId) {
        CarDto car = carService.getCarById(carId);
        return ResponseEntity.ok(car);
    }

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<CarDto> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable("carId") String carId) {
        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }
}
