package transportation.passengers.controller;

import transportation.passengers.dto.CreatePassengerRequestDto;
import transportation.passengers.dto.UpdatePassengerRequestDto;
import transportation.passengers.dto.RestorePassengerRequestDto;
import transportation.passengers.dto.PassengerResponseDto;
import transportation.passengers.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passengers")
public class PassengerController {

    private final PassengerService service;

    public PassengerController(PassengerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PassengerResponseDto> createPassenger(
            @Valid @RequestBody CreatePassengerRequestDto dto
    ) {
        PassengerResponseDto created = service.createPassenger(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponseDto> getPassengerById(
            @PathVariable("id") Long id
    ) {
        PassengerResponseDto dto = service.getPassengerById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<PassengerResponseDto>> getAllPassengers() {
        List<PassengerResponseDto> list = service.getAllPassengers();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponseDto> updatePassenger(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdatePassengerRequestDto dto
    ) {
        PassengerResponseDto updated = service.updatePassenger(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(
            @PathVariable("id") Long id
    ) {
        service.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/restore")
    public ResponseEntity<PassengerResponseDto> restorePassenger(
            @Valid @RequestBody RestorePassengerRequestDto dto
    ) {
        return ResponseEntity.ok(service.restorePassenger(dto));
    }

}
