package transportation.drivers.dto;

import jakarta.validation.constraints.Pattern;

public record RestoreCarRequestDto(
        @Pattern(
                regexp = "^[0-9]{4}\\s?[A-Z]{2}-[1-7]$",
                message = "License plate must match Belarus format (e.g., 1234 AB-7)"
        )
        String licensePlate,

        String carId
) {}
