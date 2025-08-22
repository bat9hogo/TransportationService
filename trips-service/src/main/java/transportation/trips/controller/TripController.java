package transportation.trips.controller;

import jakarta.validation.Valid;
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
import transportation.trips.dto.TripRequestDto;
import transportation.trips.dto.TripResponseDto;
import transportation.trips.dto.TripStatusUpdateDto;
import transportation.trips.dto.RestoreTripRequestDto;
import transportation.trips.service.TripService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<TripResponseDto> createTrip(
            @Valid @RequestBody TripRequestDto request
    ) {
        TripResponseDto created = tripService.createTrip(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponseDto> getTripById(
            @PathVariable("id") String id
    ) {
        TripResponseDto trip = tripService.getTripById(id);
        return ResponseEntity.ok(trip);
    }

    @GetMapping
    public ResponseEntity<List<TripResponseDto>> getAllTrips() {
        List<TripResponseDto> trips = tripService.getAllTrips();
        return ResponseEntity.ok(trips);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripResponseDto> updateTrip(
            @PathVariable("id") String id,
            @Valid @RequestBody TripRequestDto request
    ) {
        TripResponseDto updated = tripService.updateTrip(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(
            @PathVariable("id") String id
    ) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TripResponseDto> updateTripStatus(
            @PathVariable("id") String id,
            @Valid @RequestBody TripStatusUpdateDto statusUpdate
    ) {
        TripResponseDto updated = tripService.updateTripStatus(id, statusUpdate);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/restore")
    public ResponseEntity<TripResponseDto> restoreTrip(
            @Valid @RequestBody RestoreTripRequestDto request
    ) {
        TripResponseDto restored = tripService.restoreTrip(request);
        return ResponseEntity.ok(restored);
    }
}
