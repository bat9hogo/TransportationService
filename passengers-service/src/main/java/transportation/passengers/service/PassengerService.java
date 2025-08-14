package transportation.passengers.service;

import transportation.passengers.dto.PassengerDto;

import java.util.List;

public interface PassengerService {

    PassengerDto createPassenger(PassengerDto dto);

    PassengerDto getPassengerById(Long id);

    List<PassengerDto> getAllPassengers();

    PassengerDto updatePassenger(Long id, PassengerDto dto);

    void deletePassenger(Long id);
}
