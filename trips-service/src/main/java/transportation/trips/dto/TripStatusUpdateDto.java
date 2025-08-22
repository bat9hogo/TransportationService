package transportation.trips.dto;

import transportation.trips.entity.TripStatus;

public record TripStatusUpdateDto(
        TripStatus status
) {}
