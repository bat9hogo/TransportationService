package transportation.drivers.service;

import transportation.drivers.dto.DriverDto;

import java.util.List;

public interface DriverService {
    DriverDto createDriver(DriverDto dto);
    DriverDto updateDriver(Long id, DriverDto dto);
    DriverDto getDriverById(Long id);
    List<DriverDto> getAllDrivers();
    void deleteDriver(Long id);
}
