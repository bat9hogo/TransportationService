package transportation.trips.dto;

import jakarta.validation.constraints.NotBlank;

public record RestoreTripRequestDto(
        @NotBlank String id
) {}
