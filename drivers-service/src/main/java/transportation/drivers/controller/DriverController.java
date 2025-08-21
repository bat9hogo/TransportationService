package transportation.drivers.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import transportation.drivers.dto.DriverDto;
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
    public ResponseEntity<DriverDto> createDriver(@Valid @RequestBody DriverDto dto) {
        DriverDto created = driverService.createDriver(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{driverId}")
    public ResponseEntity<DriverDto> updateDriver(@PathVariable("driverId") String driverId,
                                                  @Valid @RequestBody DriverDto dto) {
        DriverDto updated = driverService.updateDriver(driverId, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverDto> getDriverById(@PathVariable("driverId") String driverId) {
        DriverDto driver = driverService.getDriverById(driverId);
        return ResponseEntity.ok(driver);
    }

    @GetMapping
    public ResponseEntity<List<DriverDto>> getAllDrivers() {
        List<DriverDto> drivers = driverService.getAllDrivers();
        return ResponseEntity.ok(drivers);
    }

    @DeleteMapping("/{driverId}")
    public ResponseEntity<Void> deleteDriver(@PathVariable("driverId") String driverId) {
        driverService.deleteDriver(driverId);
        return ResponseEntity.noContent().build();
    }
}
