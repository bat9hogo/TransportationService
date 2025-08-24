package transportation.trips.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import transportation.trips.dto.CreateTripRequestDto;
import transportation.trips.dto.TripResponseDto;
import transportation.trips.dto.TripStatusUpdateDto;
import transportation.trips.dto.RestoreTripRequestDto;
import transportation.trips.service.TripService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
public class TripController {

    private static final Logger logger = LoggerFactory.getLogger(TripController.class);

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<TripResponseDto> createTrip(
            @Valid @RequestBody CreateTripRequestDto request
    ) {
        logger.info("POST /api/v1/trips called with {}", request);
        TripResponseDto created = tripService.createTrip(request);
        logger.info("Created trip with id {}", created.id());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponseDto> getTripById(
            @PathVariable("id") String id
    ) {
        logger.info("GET /api/v1/trips/{} called", id);
        TripResponseDto trip = tripService.getTripById(id);
        logger.info("Returning trip: {}", trip);
        return ResponseEntity.ok(trip);
    }

    @GetMapping
    public ResponseEntity<List<TripResponseDto>> getAllTrips() {
        logger.info("GET /api/v1/trips called");
        List<TripResponseDto> trips = tripService.getAllTrips();
        logger.info("Returning {} trips", trips.size());
        return ResponseEntity.ok(trips);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripResponseDto> updateTrip(
            @PathVariable("id") String id,
            @Valid @RequestBody CreateTripRequestDto request
    ) {
        logger.info("PUT /api/v1/trips/{} called with {}", id, request);
        TripResponseDto updated = tripService.updateTrip(id, request);
        logger.info("Updated trip {}", id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(
            @PathVariable("id") String id
    ) {
        logger.info("DELETE /api/v1/trips/{} called", id);
        tripService.deleteTrip(id);
        logger.info("Trip {} deleted", id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TripResponseDto> updateTripStatus(
            @PathVariable("id") String id,
            @Valid @RequestBody TripStatusUpdateDto statusUpdate
    ) {
        logger.info("PATCH /api/v1/trips/{}/status called with {}", id, statusUpdate);
        TripResponseDto updated = tripService.updateTripStatus(id, statusUpdate);
        logger.info("Updated status of trip {}", id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/restore")
    public ResponseEntity<TripResponseDto> restoreTrip(
            @Valid @RequestBody RestoreTripRequestDto request
    ) {
        logger.info("PATCH /api/v1/trips/restore called with {}", request);
        TripResponseDto restored = tripService.restoreTrip(request);
        logger.info("Restored trip: {}", restored);
        return ResponseEntity.ok(restored);
    }
}
