package transportation.drivers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record CreateDriverRequestDto(
        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name must be less than 100 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last name must be less than 100 characters")
        String lastName,

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email,

        @Pattern(regexp = "^\\+\\d{7,15}$", message = "Phone number must be in international format")
        String phoneNumber,

        List<String> carIds,

        List<CreateCarRequestDto> carsToCreate
) {}