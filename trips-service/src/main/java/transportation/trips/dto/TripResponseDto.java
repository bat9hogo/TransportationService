package transportation.trips.dto;

import transportation.trips.entity.TripStatus;
import java.math.BigDecimal;
import java.time.Instant;

public record TripResponseDto(
        String id,
        String driverId,
        String passengerId,
        String fromAddress,
        String toAddress,
        TripStatus status,
        Instant orderTime,
        BigDecimal price
) {}
