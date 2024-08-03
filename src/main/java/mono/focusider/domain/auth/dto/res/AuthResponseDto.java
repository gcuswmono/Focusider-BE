package mono.focusider.domain.auth.dto.res;

public record AuthResponseDto(
        String accessToken,
        String refreshToken
) {

}