package transportation.drivers.service;

import transportation.drivers.dto.DriverDto;
import java.util.List;

public interface DriverService {

    DriverDto createDriver(DriverDto dto);
    DriverDto updateDriver(String driverId, DriverDto dto);
    DriverDto getDriverById(String driverId);
    List<DriverDto> getAllDrivers();
    void deleteDriver(String driverId);
}
