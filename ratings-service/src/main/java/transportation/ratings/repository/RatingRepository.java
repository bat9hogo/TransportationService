package transportation.ratings.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import transportation.ratings.entity.Rating;

import java.util.List;

@Repository
public interface RatingRepository extends ElasticsearchRepository<Rating, String> {

    List<Rating> findByTripIdAndDeletedFalse(String tripId);
}
