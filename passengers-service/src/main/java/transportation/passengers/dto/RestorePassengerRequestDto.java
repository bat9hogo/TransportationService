package transportation.passengers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record RestorePassengerRequestDto(
        @Email(message = "Invalid email format")
        String email,

        @Pattern(regexp = "^\\+?[0-9\\-\\s]{6,20}$", message = "Invalid phone format")
        String phoneNumber
) { }
