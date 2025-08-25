# PassengersService
Postgresql-based passengers service.

1. Ensure `.env` is set with database credentials.
2. Start the database:
```bash
docker-compose up -d
```
3. Run the service:
```bash
mvn spring-boot:run
```
   or in IDE.
## Endpoints

- REST endpoint are available at:
```bash
http://localhost:YOUR_PORT/api/YOUR_VERSION/passengers...
```
- Swagger UI:
```bash
http://localhost:YOUR_PORT/swagger-ui.html
```
- Kibana:
```bash
http://localhost:5601
```
- Prometheus:
```bash
http://localhost:9090
```
- Grafana:
```bash
http://localhost:3000
```

## Database

- Flyway migrations are located in:
```bash
src/main/resources/db/migration
```
- The service automatically applies migrations on startup.