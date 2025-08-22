package transportation.trips.service;

import transportation.trips.dto.CreateTripRequestDto;
import transportation.trips.dto.TripResponseDto;
import transportation.trips.dto.TripStatusUpdateDto;
import transportation.trips.dto.RestoreTripRequestDto;

import java.util.List;

public interface TripService {

    TripResponseDto createTrip(CreateTripRequestDto request);

    TripResponseDto getTripById(String id);

    List<TripResponseDto> getAllTrips();

    TripResponseDto updateTrip(String id, CreateTripRequestDto request);

    void deleteTrip(String id);

    TripResponseDto updateTripStatus(String id, TripStatusUpdateDto statusUpdate);

    TripResponseDto restoreTrip(RestoreTripRequestDto request);
}
