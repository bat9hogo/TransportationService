package transportation.drivers.service;

import transportation.drivers.dto.CreateDriverRequestDto;
import transportation.drivers.dto.DriverResponseDto;
import transportation.drivers.dto.RestoreDriverRequestDto;
import transportation.drivers.dto.UpdateDriverRequestDto;

import java.util.List;

public interface DriverService {

    DriverResponseDto createDriver(CreateDriverRequestDto dto);

    DriverResponseDto updateDriver(String id, UpdateDriverRequestDto dto);

    DriverResponseDto getDriverById(String id);

    List<DriverResponseDto> getAllDrivers();

    void deleteDriver(String id);

    DriverResponseDto restoreDriver(RestoreDriverRequestDto dto);
}
