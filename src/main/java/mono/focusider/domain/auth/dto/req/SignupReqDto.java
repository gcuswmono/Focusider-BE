package mono.focusider.domain.auth.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import mono.focusider.domain.member.type.MemberGenderType;

import java.time.LocalDate;

public record SignupReqDto(
        @NotBlank(message = "Username is required")
        String accountId,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "Name is required")
        String name,
        @NotNull(message = "Gender is required")
        MemberGenderType gender,
        @NotNull(message = "Birth date is required")
        @Past(message = "Birth date must be in the past")
        LocalDate birthday,
        String profileImage
) {

}