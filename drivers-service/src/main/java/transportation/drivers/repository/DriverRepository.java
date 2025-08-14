package transportation.drivers.repository;

import transportation.drivers.entity.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends MongoRepository<Driver, String> {

    Optional<Driver> findByIdAndDeletedFalse(String driverId);
    List<Driver> findAllByDeletedFalse();
}
