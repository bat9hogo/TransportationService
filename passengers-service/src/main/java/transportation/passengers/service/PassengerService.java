package transportation.passengers.service;

import transportation.passengers.dto.CreatePassengerRequestDto;
import transportation.passengers.dto.PassengerResponseDto;
import transportation.passengers.dto.UpdatePassengerRequestDto;

import java.util.List;

public interface PassengerService {

    PassengerResponseDto createPassenger(CreatePassengerRequestDto dto);

    PassengerResponseDto getPassengerById(Long id);

    List<PassengerResponseDto> getAllPassengers();

    PassengerResponseDto updatePassenger(Long id, UpdatePassengerRequestDto dto);

    void deletePassenger(Long id);
}
