package transportation.ratings.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;
import transportation.ratings.entity.Rating;

import java.io.InputStream;
import java.util.List;

@Component
public class ElasticsearchMigrationRunner implements CommandLineRunner {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ObjectMapper objectMapper;

    public ElasticsearchMigrationRunner(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Override
    public void run(String... args) throws Exception {
        migrateIndex();
        migrateInitialData();
    }

    private void migrateIndex() {
        IndexOperations indexOps = elasticsearchOperations.indexOps(Rating.class);

        if (!indexOps.exists()) {
            indexOps.create();
            indexOps.putMapping(Rating.class);
            System.out.println("Elasticsearch index 'ratings' created.");
        } else {
            System.out.println("Elasticsearch index 'ratings' already exists. Skipping creation.");
        }
    }

    private void migrateInitialData() throws Exception {
        InputStream dataStream =
                new ClassPathResource("db/migration/V2__insert_initial_data.json").getInputStream();
        List<Rating> initialRatings = objectMapper.readValue(
                dataStream,
                new TypeReference<List<Rating>>() {}
        );

        IndexCoordinates index = elasticsearchOperations.getIndexCoordinatesFor(Rating.class);

        for (Rating rating : initialRatings) {
            String docId = rating.getId();
            if (docId == null || rating.getId().trim().isEmpty()) {
                docId = rating.getTripId() + "_" + rating.getDriverId() + "_" + rating.getPassengerId();
                rating.setId(docId);
            }

            boolean exists = elasticsearchOperations.exists(docId, index);
            if (!exists) {
                elasticsearchOperations.save(rating, index);
            }
        }

        System.out.println("Initial ratings data migration finished.");
    }
}
