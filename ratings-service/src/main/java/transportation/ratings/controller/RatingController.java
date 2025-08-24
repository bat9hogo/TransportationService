package transportation.ratings.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import transportation.ratings.dto.CreateRatingRequestDto;
import transportation.ratings.dto.RatingResponseDto;
import transportation.ratings.dto.UpdateRatingRequestDto;
import transportation.ratings.dto.RestoreRatingRequestDto;
import transportation.ratings.service.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<RatingResponseDto> createRating(
            @Valid @RequestBody CreateRatingRequestDto requestDto
    ) {
        logger.info("POST /api/v1/ratings called with {}", requestDto);
        RatingResponseDto response = ratingService.createRating(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponseDto> getRatingById(
            @PathVariable("id") String id
    ) {
        logger.info("GET /api/v1/ratings/{} called", id);
        RatingResponseDto response = ratingService.getRatingById(id);
        logger.info("Returning rating: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RatingResponseDto>> getAllRatings() {
        logger.info("GET /api/v1/ratings called");
        List<RatingResponseDto> responses = ratingService.getAllRatings();
        logger.info("Returning {} ratings", responses.size());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingResponseDto> updateRating(
            @PathVariable("id") String id,
            @Valid @RequestBody UpdateRatingRequestDto updateDto
    ) {
        logger.info("PUT /api/v1/ratings/{} called with {}", id, updateDto);
        RatingResponseDto response = ratingService.updateRating(id, updateDto);
        logger.info("Updated rating: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(
            @PathVariable("id") String id
    ) {
        logger.info("DELETE /api/v1/ratings/{} called", id);
        ratingService.deleteRating(id);
        logger.info("Deleted rating with id {}", id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/restore")
    public ResponseEntity<RatingResponseDto> restoreRating(
            @Valid @RequestBody RestoreRatingRequestDto request
    ) {
        logger.info("PATCH /api/v1/ratings/restore called with {}", request);
        RatingResponseDto response = ratingService.restoreRating(request);
        logger.info("Restored rating: {}", response);
        return ResponseEntity.ok(response);
    }
}
