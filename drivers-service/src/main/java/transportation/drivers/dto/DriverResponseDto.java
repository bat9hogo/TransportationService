package transportation.drivers.dto;

import java.util.List;

public record DriverResponseDto(
        String id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        List<String> carIds
) {}
