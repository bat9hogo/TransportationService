# TripsService
Cassandra-based trips service.

1. Ensure `.env` is set with database credentials.
2. Start the database:
```bash
docker-compose up -d
``` 
3. Wait about 1 minute and check by
```bash
docker ps
```
   if Cassandra starts.
4. Run the service:
```bash
mvn spring-boot:run
```
   or in IDE.
5. You can connect to cqlsh
```bash
docker exec -it YOUR_CONTAINER_NAME cqlsh 
```
   use
```bash
USE YOUR_CONTAINER_NAME
```
   and make requests for by type
```bash
SELECT * FROM YOUR_TABLE_NAME
```

## Endpoints

- REST endpoint are available at:
```bash
http://localhost:YOUR_PORT/api/YOUR_VERSION/trips...
```
- Swagger UI:
```bash
http://localhost:YOUR_PORT/swagger-ui.html
```
- Kibana:
```bash
http://localhost:5604
```

## Database

- Migrations are located in:
```bash
src/main/resources/cassandra/migration
```
- The service automatically applies migrations on startup.