package transportation.ratings.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transportation.ratings.dto.RatingRequestDto;
import transportation.ratings.dto.RatingResponseDto;
import transportation.ratings.dto.RatingUpdateDto;
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
    public RatingResponseDto createRating(RatingRequestDto requestDto) {
        if ((requestDto.getDriverId() == null || requestDto.getDriverId().trim().isEmpty()) &&
                (requestDto.getPassengerId() == null || requestDto.getPassengerId().trim().isEmpty()) &&
                requestDto.getAuthorType() == null) {
        }

        Rating rating = ratingMapper.toEntity(requestDto);
        Date now = new Date();
        rating.setCreatedAt(now);
        rating.setUpdatedAt(now);
        rating.setDeleted(false);

        if (requestDto.getAuthorType() != null) {
            rating.setAuthorType(requestDto.getAuthorType());
        } else if ((requestDto.getPassengerId() != null && !requestDto.getPassengerId().trim().isEmpty()) &&
                (requestDto.getDriverId() == null || requestDto.getDriverId().trim().isEmpty())) {
            rating.setAuthorType(AuthorType.PASSENGER);
        } else if ((requestDto.getDriverId() != null && !requestDto.getDriverId().trim().isEmpty()) &&
                (requestDto.getPassengerId() == null || requestDto.getPassengerId().trim().isEmpty())) {
            rating.setAuthorType(AuthorType.DRIVER);
        } else {
            rating.setAuthorType(null);
        }

        Rating saved = ratingRepository.save(rating);
        return ratingMapper.toDto(saved);
    }

    @Override
    public RatingResponseDto getRatingById(String id) {
        Rating rating = ratingRepository.findById(id)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new RatingNotFoundException("Rating not found with id: " + id));
        return ratingMapper.toDto(rating);
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
    public RatingResponseDto updateRating(String id, RatingUpdateDto updateDto) {
        Rating rating = ratingRepository.findById(id)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new RatingNotFoundException("Rating not found with id: " + id));

        rating.setScore(updateDto.getScore());
        rating.setComment(updateDto.getComment());
        rating.setUpdatedAt(new Date());

        Rating updated = ratingRepository.save(rating);
        return ratingMapper.toDto(updated);
    }

    @Override
    public void deleteRating(String id) {
        Rating rating = ratingRepository.findById(id)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new RatingNotFoundException("Rating not found with id: " + id));

        rating.setDeleted(true);
        rating.setUpdatedAt(new Date());
        ratingRepository.save(rating);
    }
}
