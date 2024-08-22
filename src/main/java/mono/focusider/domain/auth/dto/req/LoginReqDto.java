package mono.focusider.domain.auth.dto.req;

import jakarta.validation.constraints.NotBlank;

public record LoginReqDto(
        @NotBlank(message = "Username is required")
        String accountId,
        @NotBlank(message = "Password is required")
        String password
) {
}