package transportation.drivers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record UpdateDriverRequestDto(
        String firstName,
        String lastName,

        @Email
        String email,

        @Pattern(regexp = "^\\+\\d{7,15}$")
        String phoneNumber,

        List<String> carIds,
        List<CreateCarRequestDto> carsToCreate
) {}
