package transportation.ratings.dto;

import jakarta.validation.constraints.NotBlank;

public record RestoreRatingRequestDto(
        @NotBlank
        String id
) {}
