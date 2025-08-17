package transportation.trips.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "transportation.trips.repository")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return System.getProperty("CASSANDRA_KEYSPACE", "trips_db");
    }

    @Override
    protected String getContactPoints() {
        return System.getProperty("CASSANDRA_HOST", "localhost");
    }

    @Override
    protected int getPort() {
        return Integer.parseInt(System.getProperty("CASSANDRA_PORT", "9042"));
    }

    @Override
    protected String getLocalDataCenter() {
        return System.getProperty("CASSANDRA_LOCAL_DATACENTER", "DC1");
    }
}
