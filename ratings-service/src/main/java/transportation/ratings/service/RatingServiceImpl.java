package transportation.ratings.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transportation.ratings.dto.CreateRatingRequestDto;
import transportation.ratings.dto.RatingResponseDto;
import transportation.ratings.dto.UpdateRatingRequestDto;
import transportation.ratings.dto.RestoreRatingRequestDto;
import transportation.ratings.entity.AuthorType;
import transportation.ratings.entity.Rating;
import transportation.ratings.exception.RatingNotFoundException;
import transportation.ratings.mapper.RatingMapper;
import transportation.ratings.repository.RatingRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;

    public RatingServiceImpl(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }

    @Override
    public RatingResponseDto createRating(CreateRatingRequestDto request) {
        Rating rating = ratingMapper.toEntity(request);

        if (request.authorType() != null) {
            rating.setAuthorType(request.authorType());
        } else if ((request.passengerId() != null && !request.passengerId().isBlank()) &&
                (request.driverId() == null || request.driverId().isBlank())) {
            rating.setAuthorType(AuthorType.PASSENGER);
        } else if ((request.driverId() != null && !request.driverId().isBlank()) &&
                (request.passengerId() == null || request.passengerId().isBlank())) {
            rating.setAuthorType(AuthorType.DRIVER);
        } else {
            rating.setAuthorType(null);
        }

        Date now = new Date();

        rating.setCreatedAt(now);
        rating.setUpdatedAt(now);
        rating.setDeleted(false);

        Rating saved = ratingRepository.save(rating);
        return ratingMapper.toDto(saved);
    }

    @Override
    public RatingResponseDto getRatingById(String id) {
        return ratingMapper.toDto(findRatingByIdOrThrow(id));
    }

    @Override
    public List<RatingResponseDto> getAllRatings() {
        Iterable<Rating> ratings = ratingRepository.findAll();
        return StreamSupport.stream(ratings.spliterator(), false)
                .filter(r -> !r.isDeleted())
                .map(ratingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RatingResponseDto> getRatingsByTripId(String tripId) {
        Iterable<Rating> ratings = ratingRepository.findByTripIdAndDeletedFalse(tripId);
        return StreamSupport.stream(ratings.spliterator(), false)
                .map(ratingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RatingResponseDto updateRating(String id, UpdateRatingRequestDto updateDto) {
        Rating rating = findRatingByIdOrThrow(id);
        rating.setScore(updateDto.score());
        rating.setComment(updateDto.comment());
        rating.setUpdatedAt(new Date());
        Rating updated = ratingRepository.save(rating);
        return ratingMapper.toDto(updated);
    }

    @Override
    public void deleteRating(String id) {
        Rating rating = findRatingByIdOrThrow(id);
        rating.setDeleted(true);
        rating.setUpdatedAt(new Date());
        ratingRepository.save(rating);
    }

    @Override
    public RatingResponseDto restoreRating(RestoreRatingRequestDto request) {
        Rating rating = ratingRepository.findById(request.id())
                .orElseThrow(() -> new RatingNotFoundException("Rating with id " + request.id() + " not found"));

        if (!rating.isDeleted()) {
            throw new IllegalStateException("Rating with id " + request.id() + " is not deleted");
        }

        boolean exists = StreamSupport.stream(ratingRepository.findAll().spliterator(), false)
                .anyMatch(r -> !r.isDeleted() && r.getId().equals(rating.getId()));

        if (exists) {
            throw new IllegalStateException("Another active rating with id " + rating.getId() + " already exists");
        }

        rating.setDeleted(false);
        rating.setUpdatedAt(new Date());
        Rating restored = ratingRepository.save(rating);
        return ratingMapper.toDto(restored);
    }

    private Rating findRatingByIdOrThrow(String id) {
        return ratingRepository.findById(id)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new RatingNotFoundException("Rating with id " + id + " not found"));
    }
}
