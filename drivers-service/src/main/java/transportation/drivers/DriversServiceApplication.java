package transportation.drivers;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DriversServiceApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure()
                .directory("D:/Java/TransportationService/drivers-service")
                .load();

        System.setProperty("spring.data.mongodb.host", dotenv.get("MONGO_HOST"));
        System.setProperty("spring.data.mongodb.port", dotenv.get("MONGO_PORT"));
        System.setProperty("spring.data.mongodb.database", dotenv.get("MONGO_DB"));
        System.setProperty("spring.data.mongodb.username", dotenv.get("MONGO_USER"));
        System.setProperty("spring.data.mongodb.password", dotenv.get("MONGO_PASSWORD"));

        SpringApplication.run(DriversServiceApplication.class, args);
    }
}
