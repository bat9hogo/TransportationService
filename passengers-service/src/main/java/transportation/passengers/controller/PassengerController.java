package transportation.passengers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(PassengerController.class);

    private final PassengerService service;

    public PassengerController(PassengerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PassengerResponseDto> createPassenger(
            @Valid @RequestBody CreatePassengerRequestDto dto
    ) {
        logger.info("POST /api/v1/passengers called with {}", dto);
        PassengerResponseDto created = service.createPassenger(dto);
        logger.info("Created passenger: {}", created);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponseDto> getPassengerById(
            @PathVariable("id") Long id
    ) {
        logger.info("GET /api/v1/passengers/{} called", id);
        PassengerResponseDto dto = service.getPassengerById(id);
        logger.info("Returning passenger: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<PassengerResponseDto>> getAllPassengers() {
        logger.info("GET /api/v1/passengers called");
        List<PassengerResponseDto> list = service.getAllPassengers();
        logger.info("Returning {} passengers", list.size());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponseDto> updatePassenger(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdatePassengerRequestDto dto
    ) {
        logger.info("PUT /api/v1/passengers/{} called with {}", id, dto);
        PassengerResponseDto updated = service.updatePassenger(id, dto);
        logger.info("Updated passenger: {}", updated);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(
            @PathVariable("id") Long id
    ) {
        logger.info("DELETE /api/v1/passengers/{} called", id);
        service.deletePassenger(id);
        logger.info("Passenger {} deleted", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/restore")
    public ResponseEntity<PassengerResponseDto> restorePassenger(
            @Valid @RequestBody RestorePassengerRequestDto dto
    ) {
        logger.info("POST /api/v1/passengers/restore called with {}", dto);
        PassengerResponseDto restored = service.restorePassenger(dto);
        logger.info("Restored passenger: {}", restored);
        return ResponseEntity.ok(restored);
    }
}
