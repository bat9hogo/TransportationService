package transportation.drivers.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import transportation.drivers.entity.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends MongoRepository<Driver, String> {

    Optional<Driver> findByIdAndDeletedFalse(String id);

    Optional<Driver> findByEmail(String email);

    Optional<Driver> findByPhoneNumber(String phoneNumber);

    List<Driver> findAllByEmail(String email);

    List<Driver> findAllByPhoneNumber(String phoneNumber);

    List<Driver> findAllByDeletedFalse();
}
