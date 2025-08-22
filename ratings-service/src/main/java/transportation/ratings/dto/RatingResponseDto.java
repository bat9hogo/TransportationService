package transportation.ratings.dto;

import transportation.ratings.entity.AuthorType;

import java.util.Date;

public record RatingResponseDto(
        String id,
        String tripId,
        String driverId,
        String passengerId,
        Integer score,
        String comment,
        boolean deleted,
        Date createdAt,
        Date updatedAt,
        AuthorType authorType
) {}
