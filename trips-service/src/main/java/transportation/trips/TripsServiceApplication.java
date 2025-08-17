package transportation.trips;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.datastax.oss.driver.api.core.CqlSession;
import java.net.InetSocketAddress;

@SpringBootApplication
public class TripsServiceApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure().directory("trips-service").load();

        System.setProperty("CASSANDRA_HOST", dotenv.get("CASSANDRA_HOST"));
        System.setProperty("CASSANDRA_PORT", dotenv.get("CASSANDRA_PORT"));
        System.setProperty("CASSANDRA_KEYSPACE", dotenv.get("CASSANDRA_KEYSPACE"));
        System.setProperty("CASSANDRA_USERNAME", dotenv.get("CASSANDRA_USERNAME"));
        System.setProperty("CASSANDRA_PASSWORD", dotenv.get("CASSANDRA_PASSWORD"));
        System.setProperty("SPRING_APPLICATION_NAME", dotenv.get("SPRING_APPLICATION_NAME"));
        System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
        System.setProperty("CASSANDRA_LOCAL_DATACENTER", dotenv.get("CASSANDRA_LOCAL_DATACENTER"));

        createKeyspaceIfNotExists();

        SpringApplication.run(TripsServiceApplication.class, args);
    }

    private static void createKeyspaceIfNotExists() {
        String host = System.getProperty("CASSANDRA_HOST");
        int port = Integer.parseInt(System.getProperty("CASSANDRA_PORT"));
        String keyspace = System.getProperty("CASSANDRA_KEYSPACE");
        String datacenter = System.getProperty("CASSANDRA_LOCAL_DATACENTER");

        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(host, port))
                .withLocalDatacenter(datacenter)
                .build()) {

            session.execute("CREATE KEYSPACE IF NOT EXISTS " + keyspace +
                    " WITH replication = {'class':'SimpleStrategy','replication_factor':1}");
        }
    }
}
