package mono.focusider.domain.auth.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "Username is required")
        String accountId,
        @NotBlank(message = "Password is required")
        String password
) {
}