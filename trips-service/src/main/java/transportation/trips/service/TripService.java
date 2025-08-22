package transportation.trips.service;

import transportation.trips.dto.TripRequestDto;
import transportation.trips.dto.TripResponseDto;
import transportation.trips.dto.TripStatusUpdateDto;
import transportation.trips.dto.RestoreTripRequestDto;

import java.util.List;

public interface TripService {

    TripResponseDto createTrip(TripRequestDto request);

    TripResponseDto getTripById(String id);

    List<TripResponseDto> getAllTrips();

    TripResponseDto updateTrip(String id, TripRequestDto request);

    void deleteTrip(String id);

    TripResponseDto updateTripStatus(String id, TripStatusUpdateDto statusUpdate);
    TripResponseDto restoreTrip(RestoreTripRequestDto request);
}
