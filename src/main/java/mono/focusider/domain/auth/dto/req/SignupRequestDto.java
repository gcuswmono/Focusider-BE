package mono.focusider.domain.auth.dto.req;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record SignupRequestDto(
        @NotBlank(message = "Username is required")
        String accountId,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Gender is required")
        String gender,
        @NotNull(message = "Birth date is required")
        @Past(message = "Birth date must be in the past")
        LocalDate birthday
) {

}