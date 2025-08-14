package transportation.drivers.repository;

import transportation.drivers.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByIdAndDeletedFalse(Long id);
    List<Driver> findAllByDeletedFalse();
}
