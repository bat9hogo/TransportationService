package transportation.ratings.dto;

import jakarta.validation.constraints.*;

public record UpdateRatingRequestDto(
        @NotNull(message = "score is required")
        @Min(value = 1, message = "score must be at least 1")
        @Max(value = 5, message = "score must be at most 5")
        Integer score,

        String comment
) {}
