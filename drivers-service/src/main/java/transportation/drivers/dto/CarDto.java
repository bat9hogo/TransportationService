package transportation.drivers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CarDto(
        String id,

        @NotBlank(message = "Color is required")
        @Size(max = 50, message = "Color must be less than 50 characters")
        String color,

        @NotBlank(message = "Brand is required")
        @Size(max = 100, message = "Brand must be less than 100 characters")
        String brand,

        @NotBlank(message = "License plate is required")
        @Pattern
                (
                regexp = "^[0-9]{4}\\s?[A-Z]{2}-[1-7]$",
                message = "License plate must match Belarus format (e.g., 1234 AB-7)"
                )
        String licensePlate
) {}
