package transportation.ratings.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import transportation.ratings.dto.RatingRequestDto;
import transportation.ratings.dto.RatingResponseDto;
import transportation.ratings.dto.RatingUpdateDto;
import transportation.ratings.service.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<RatingResponseDto> createRating(@Valid @RequestBody RatingRequestDto requestDto) {
        RatingResponseDto response = ratingService.createRating(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponseDto> getRatingById(@PathVariable("id") String id) {
        RatingResponseDto response = ratingService.getRatingById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RatingResponseDto>> getAllRatings() {
        List<RatingResponseDto> responses = ratingService.getAllRatings();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingResponseDto> updateRating(
            @PathVariable("id") String id,
            @Valid @RequestBody RatingUpdateDto updateDto) {
        RatingResponseDto response = ratingService.updateRating(id, updateDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable("id") String id) {
        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }
}
