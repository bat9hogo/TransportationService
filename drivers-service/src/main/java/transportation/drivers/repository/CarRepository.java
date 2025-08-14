package transportation.drivers.repository;

import transportation.drivers.entity.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends MongoRepository<Car, String> {

    Optional<Car> findByIdAndDeletedFalse(String carId);
    List<Car> findAllByIdIn(List<String> carIds);
    List<Car> findAllByDeletedFalse();

}
