package transportation.trips.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import transportation.trips.entity.Trip;


@Repository
public interface TripRepository extends CassandraRepository<Trip, String> {
}
