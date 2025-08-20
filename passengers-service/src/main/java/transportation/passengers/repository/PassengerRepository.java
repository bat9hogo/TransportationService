package transportation.passengers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import transportation.passengers.entity.Passenger;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Optional<Passenger> findByEmail(String email);

    Optional<Passenger> findByPhoneNumber(String phoneNumber);

    List<Passenger> findAllByDeletedFalse();
}
