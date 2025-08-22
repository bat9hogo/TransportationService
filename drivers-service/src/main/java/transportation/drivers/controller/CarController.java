package transportation.drivers.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import transportation.drivers.dto.RestoreCarRequestDto;
import transportation.drivers.dto.CreateCarRequestDto;
import transportation.drivers.dto.UpdateCarRequestDto;
import transportation.drivers.dto.CarResponseDto;
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
    public ResponseEntity<CarResponseDto> createCar(
            @Valid @RequestBody CreateCarRequestDto dto
    ) {
        CarResponseDto created = carService.createCar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{carId}")
    public ResponseEntity<CarResponseDto> updateCar(
            @PathVariable("carId") String carId,
            @Valid @RequestBody UpdateCarRequestDto dto
    ) {
        CarResponseDto updated = carService.updateCar(carId, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<CarResponseDto> getCarById(
            @PathVariable("carId") String carId
    ) {
        CarResponseDto car = carService.getCarById(carId);
        return ResponseEntity.ok(car);
    }

    @GetMapping
    public ResponseEntity<List<CarResponseDto>> getAllCars() {
        List<CarResponseDto> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(
            @PathVariable("carId") String carId
    ) {
        carService.deleteCar(carId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/restore")
    public ResponseEntity<CarResponseDto> restoreCar(
            @Valid @RequestBody RestoreCarRequestDto request
    ) {
        CarResponseDto restored = carService.restoreCar(request);
        return ResponseEntity.ok(restored);
    }
}
