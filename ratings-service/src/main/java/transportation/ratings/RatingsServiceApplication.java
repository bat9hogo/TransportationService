package transportation.ratings;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RatingsServiceApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure().directory("ratings-service").load();

        System.setProperty("ELASTICSEARCH_HOST", dotenv.get("ELASTICSEARCH_HOST"));
        System.setProperty("ELASTICSEARCH_PORT", dotenv.get("ELASTICSEARCH_PORT"));
        System.setProperty("ELASTICSEARCH_AUTH_ENABLED", dotenv.get("ELASTICSEARCH_AUTH_ENABLED"));
        System.setProperty("ELASTICSEARCH_USERNAME", dotenv.get("ELASTICSEARCH_USERNAME"));
        System.setProperty("ELASTICSEARCH_PASSWORD", dotenv.get("ELASTICSEARCH_PASSWORD"));

        SpringApplication.run(RatingsServiceApplication.class, args);
    }
}
