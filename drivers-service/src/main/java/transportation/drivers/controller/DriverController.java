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
import transportation.drivers.dto.RestoreDriverRequestDto;
import transportation.drivers.dto.CreateDriverRequestDto;
import transportation.drivers.dto.DriverResponseDto;
import transportation.drivers.dto.UpdateDriverRequestDto;
import transportation.drivers.service.DriverService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

    private static final Logger logger = LoggerFactory.getLogger(DriverController.class);

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<DriverResponseDto> createDriver(
            @Valid @RequestBody CreateDriverRequestDto dto
    ) {
        logger.info("POST /api/v1/drivers called with {}", dto);
        DriverResponseDto created = driverService.createDriver(dto);
        logger.info("Created driver with id {}", created.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{driverId}")
    public ResponseEntity<DriverResponseDto> updateDriver(
            @PathVariable("driverId") String driverId,
            @Valid @RequestBody UpdateDriverRequestDto dto
    ) {
        logger.info("PUT /api/v1/drivers/{} called with {}", driverId, dto);
        DriverResponseDto updated = driverService.updateDriver(driverId, dto);
        logger.info("Updated driver {}", driverId);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverResponseDto> getDriverById(
            @PathVariable("driverId") String driverId
    ) {
        logger.info("GET /api/v1/drivers/{} called", driverId);
        DriverResponseDto driver = driverService.getDriverById(driverId);
        logger.info("Returning driver: {}", driver);
        return ResponseEntity.ok(driver);
    }

    @GetMapping
    public ResponseEntity<List<DriverResponseDto>> getAllDrivers() {
        logger.info("GET /api/v1/drivers called");
        List<DriverResponseDto> drivers = driverService.getAllDrivers();
        logger.info("Returning {} drivers", drivers.size());
        return ResponseEntity.ok(drivers);
    }

    @DeleteMapping("/{driverId}")
    public ResponseEntity<Void> deleteDriver(
            @PathVariable("driverId") String driverId
    ) {
        logger.info("DELETE /api/v1/drivers/{} called", driverId);
        driverService.deleteDriver(driverId);
        logger.info("Driver {} deleted", driverId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/restore")
    public ResponseEntity<DriverResponseDto> restoreDriver(
            @Valid @RequestBody RestoreDriverRequestDto request
    ) {
        logger.info("POST /api/v1/drivers/restore called with {}", request);
        DriverResponseDto restoredDriver = driverService.restoreDriver(request);
        logger.info("Restored driver: {}", restoredDriver);
        return ResponseEntity.ok(restoredDriver);
    }
}
