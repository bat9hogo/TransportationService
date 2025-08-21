package transportation.drivers.service;

import transportation.drivers.dto.DriverRequestDto;
import transportation.drivers.dto.DriverResponseDto;
import transportation.drivers.dto.DriverUpdateDto;

import java.util.List;

public interface DriverService {

    DriverResponseDto createDriver(DriverRequestDto dto);

    DriverResponseDto updateDriver(String id, DriverUpdateDto dto);

    DriverResponseDto getDriverById(String id);

    List<DriverResponseDto> getAllDrivers();

    void deleteDriver(String id);
}
