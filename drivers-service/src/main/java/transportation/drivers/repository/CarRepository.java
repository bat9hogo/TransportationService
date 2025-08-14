package transportation.drivers.repository;

import transportation.drivers.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByIdAndDeletedFalse(Long id);
    List<Car> findAllByDeletedFalse();
}
