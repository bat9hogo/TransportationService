package transportation.ratings.service;

import transportation.ratings.dto.RatingRequestDto;
import transportation.ratings.dto.RatingResponseDto;
import transportation.ratings.dto.RatingUpdateDto;

import java.util.List;

public interface RatingService {

    RatingResponseDto createRating(RatingRequestDto requestDto);

    RatingResponseDto getRatingById(String id);

    List<RatingResponseDto> getAllRatings();

    List<RatingResponseDto> getRatingsByTripId(String tripId);

    RatingResponseDto updateRating(String id, RatingUpdateDto updateDto);

    void deleteRating(String id);
}
