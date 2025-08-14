package transportation.drivers.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import transportation.drivers.dto.DriverDto;
import transportation.drivers.service.DriverService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverControllerV1 {

    private final DriverService driverService;

    public DriverControllerV1(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<DriverDto> createDriver(@Valid @RequestBody DriverDto dto) {
        DriverDto created = driverService.createDriver(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverDto> updateDriver(@PathVariable("id") Long id,
                                                  @Valid @RequestBody DriverDto dto) {
        DriverDto updated = driverService.updateDriver(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getDriverById(@PathVariable("id") Long id) {
        DriverDto driver = driverService.getDriverById(id);
        return ResponseEntity.ok(driver);
    }

    @GetMapping
    public ResponseEntity<List<DriverDto>> getAllDrivers() {
        List<DriverDto> drivers = driverService.getAllDrivers();
        return ResponseEntity.ok(drivers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable("id") Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
}
