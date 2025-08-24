package transportation.drivers.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<CarResponseDto> createCar(
            @Valid @RequestBody CreateCarRequestDto dto
    ) {
        logger.info("POST /api/v1/cars called with {}", dto);
        CarResponseDto created = carService.createCar(dto);
        logger.info("Created car with id {}", created.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{carId}")
    public ResponseEntity<CarResponseDto> updateCar(
            @PathVariable("carId") String carId,
            @Valid @RequestBody UpdateCarRequestDto dto
    ) {
        logger.info("PUT /api/v1/cars/{} called with {}", carId, dto);
        CarResponseDto updated = carService.updateCar(carId, dto);
        logger.info("Updated car {}", carId);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<CarResponseDto> getCarById(
            @PathVariable("carId") String carId
    ) {
        logger.info("GET /api/v1/cars/{} called", carId);
        CarResponseDto car = carService.getCarById(carId);
        logger.info("Returning car: {}", car);
        return ResponseEntity.ok(car);
    }

    @GetMapping
    public ResponseEntity<List<CarResponseDto>> getAllCars() {
        logger.info("GET /api/v1/cars called");
        List<CarResponseDto> cars = carService.getAllCars();
        logger.info("Returning {} cars", cars.size());
        return ResponseEntity.ok(cars);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(
            @PathVariable("carId") String carId
    ) {
        logger.info("DELETE /api/v1/cars/{} called", carId);
        carService.deleteCar(carId);
        logger.info("Car {} deleted", carId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/restore")
    public ResponseEntity<CarResponseDto> restoreCar(
            @Valid @RequestBody RestoreCarRequestDto request
    ) {
        logger.info("POST /api/v1/cars/restore called with {}", request);
        CarResponseDto restored = carService.restoreCar(request);
        logger.info("Restored car: {}", restored);
        return ResponseEntity.ok(restored);
    }
}
