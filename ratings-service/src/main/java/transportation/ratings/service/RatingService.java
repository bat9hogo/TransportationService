package transportation.ratings.service;

import transportation.ratings.dto.CreateRatingRequestDto;
import transportation.ratings.dto.RatingResponseDto;
import transportation.ratings.dto.UpdateRatingRequestDto;
import transportation.ratings.dto.RestoreRatingRequestDto;

import java.util.List;

public interface RatingService {

    RatingResponseDto createRating(CreateRatingRequestDto requestDto);

    RatingResponseDto getRatingById(String id);

    List<RatingResponseDto> getAllRatings();

    RatingResponseDto updateRating(String id, UpdateRatingRequestDto updateDto);

    void deleteRating(String id);

    RatingResponseDto restoreRating(RestoreRatingRequestDto request);

    List<RatingResponseDto> getRatingsByTripId(String tripId);
}
