package transportation.drivers.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import transportation.drivers.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends MongoRepository<Car, String> {

    Optional<Car> findByIdAndDeletedFalse(String id);

    List<Car> findAllByDeletedFalse();

    Optional<Car> findByLicensePlateAndDeletedFalse(String licensePlate);

    List<Car> findAllByIdInAndDeletedFalse(List<String> ids);

}
