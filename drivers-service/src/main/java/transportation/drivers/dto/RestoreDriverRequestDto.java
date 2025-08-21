package transportation.drivers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record RestoreDriverRequestDto(
        @Email
        String email,

        @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format")
        String phoneNumber
) {}
