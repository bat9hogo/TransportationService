package transportation.trips.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateTripRequestDto(
        @NotBlank
        String driverId,

        @NotBlank
        String passengerId,

        @NotBlank
        String fromAddress,

        @NotBlank
        String toAddress,

        @NotNull @Positive
        BigDecimal price
) {}
