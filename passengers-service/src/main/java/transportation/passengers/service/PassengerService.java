package transportation.passengers.service;

import transportation.passengers.dto.PassengerRequestDto;
import transportation.passengers.dto.PassengerResponseDto;
import transportation.passengers.dto.PassengerUpdateDto;

import java.util.List;

public interface PassengerService {

    PassengerResponseDto createPassenger(PassengerRequestDto dto);

    PassengerResponseDto getPassengerById(Long id);

    List<PassengerResponseDto> getAllPassengers();

    PassengerResponseDto updatePassenger(Long id, PassengerUpdateDto dto);

    void deletePassenger(Long id);
}
