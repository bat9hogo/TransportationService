# RatingsService
Elasticsearch-based ratings service.

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
http://localhost:YOUR_PORT/api/YOUR_VERSION/ratings...
```
- Swagger UI:
```bash
http://localhost:YOUR_PORT/swagger-ui.html
```

## Database

- Migrations are located in:
```bash
src/main/resources/db/migration
```
- The service automatically applies migrations on startup.