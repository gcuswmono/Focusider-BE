package mono.focusider.domain.auth.dto.res;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record AuthResponseDto(
        String accountId
) {
    public static AuthResponseDto of(String accountId) {
        return AuthResponseDto
                .builder()
                .accountId(accountId)
                .build();
    }
}