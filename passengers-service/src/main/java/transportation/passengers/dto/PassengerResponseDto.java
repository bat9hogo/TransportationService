package transportation.passengers.dto;

import java.time.LocalDateTime;

public record PassengerResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        boolean deleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
