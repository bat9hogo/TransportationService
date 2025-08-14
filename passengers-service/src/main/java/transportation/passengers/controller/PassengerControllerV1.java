package transportation.passengers.controller;

import transportation.passengers.dto.PassengerDto;
import transportation.passengers.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passengers")
public class PassengerControllerV1 {

    private final PassengerService service;

    public PassengerControllerV1(PassengerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PassengerDto> createPassenger(
            @Valid @RequestBody PassengerDto dto
    ) {
        PassengerDto created = service.createPassenger(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerDto> getPassengerById(@PathVariable("id") Long id) {
        PassengerDto dto = service.getPassengerById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<PassengerDto>> getAllPassengers() {
        List<PassengerDto> list = service.getAllPassengers();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerDto> updatePassenger(
            @PathVariable("id") Long id,
            @Valid @RequestBody PassengerDto dto
    ) {
        PassengerDto updated = service.updatePassenger(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable("id") Long id) {
        service.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}
