package transportation.ratings.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    private final Dotenv dotenv = Dotenv.configure().directory("ratings-service").load();

    @Override
    public ClientConfiguration clientConfiguration() {
        String host = dotenv.get("ELASTICSEARCH_HOST");
        String port = dotenv.get("ELASTICSEARCH_PORT");
        String username = dotenv.get("ELASTICSEARCH_USERNAME");
        String password = dotenv.get("ELASTICSEARCH_PASSWORD");

        boolean authEnabled = Boolean.parseBoolean(dotenv.get("ELASTICSEARCH_AUTH_ENABLED"));

        if (authEnabled && notBlank(username) && notBlank(password)) {
            return ClientConfiguration.builder()
                    .connectedTo(host + ":" + port)
                    .withBasicAuth(username, password)
                    .build();
        } else {
            return ClientConfiguration.builder()
                    .connectedTo(host + ":" + port)
                    .build();
        }
    }

    private String getenvOrDefault(String key, String def) {
        String v = dotenv.get(key);
        return (v == null || v.trim().isEmpty()) ? def : v;
    }

    private boolean notBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }
}
