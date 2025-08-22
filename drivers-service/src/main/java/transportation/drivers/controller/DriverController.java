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
import transportation.drivers.dto.RestoreDriverRequestDto;
import transportation.drivers.dto.CreateDriverRequestDto;
import transportation.drivers.dto.DriverResponseDto;
import transportation.drivers.dto.UpdateDriverRequestDto;
import transportation.drivers.service.DriverService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<DriverResponseDto> createDriver(
            @Valid @RequestBody CreateDriverRequestDto dto
    ) {
        DriverResponseDto created = driverService.createDriver(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{driverId}")
    public ResponseEntity<DriverResponseDto> updateDriver(
            @PathVariable("driverId") String driverId,
            @Valid @RequestBody UpdateDriverRequestDto dto
    ) {
        DriverResponseDto updated = driverService.updateDriver(driverId, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverResponseDto> getDriverById(
            @PathVariable("driverId") String driverId
    ) {
        DriverResponseDto driver = driverService.getDriverById(driverId);
        return ResponseEntity.ok(driver);
    }

    @GetMapping
    public ResponseEntity<List<DriverResponseDto>> getAllDrivers() {
        List<DriverResponseDto> drivers = driverService.getAllDrivers();
        return ResponseEntity.ok(drivers);
    }

    @DeleteMapping("/{driverId}")
    public ResponseEntity<Void> deleteDriver(
            @PathVariable("driverId") String driverId
    ) {
        driverService.deleteDriver(driverId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/restore")
    public ResponseEntity<DriverResponseDto> restoreDriver(
            @Valid @RequestBody RestoreDriverRequestDto request
    ) {
        DriverResponseDto restoredDriver = driverService.restoreDriver(request);
        return ResponseEntity.ok(restoredDriver);
    }
}
