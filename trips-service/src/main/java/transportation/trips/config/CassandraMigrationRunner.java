package transportation.trips.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class CassandraMigrationRunner implements CommandLineRunner {

    private final CassandraTemplate cassandraTemplate;

    public CassandraMigrationRunner(CassandraTemplate cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        String[] scripts = {
                "cassandra/migration/V1__create_trips_table.cql",
                "cassandra/migration/V2__insert_initial_data.cql"
        };

        for (String path : scripts) {
            ClassPathResource resource = new ClassPathResource(path);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (line.isEmpty() || line.startsWith("--")) continue;
                    sb.append(line).append(" ");

                    if (line.endsWith(";")) {
                        String cql = sb.toString();

                        cql = cql.substring(0, cql.length() - 1);
                        cassandraTemplate.getCqlOperations().execute(cql);
                        sb.setLength(0);
                    }
                }
            }
        }

    }
}
