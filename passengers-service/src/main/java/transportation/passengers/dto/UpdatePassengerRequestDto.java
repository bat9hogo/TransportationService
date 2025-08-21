package transportation.passengers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdatePassengerRequestDto(
        @Size(max = 100, message = "First name must be less than 100 characters")
        String firstName,

        @Size(max = 100, message = "Last name must be less than 100 characters")
        String lastName,

        @Email(message = "Invalid email format")
        String email,

        @Pattern(regexp = "^\\+\\d{7,15}$", message = "Phone number must be in international format")
        String phoneNumber
) {}
