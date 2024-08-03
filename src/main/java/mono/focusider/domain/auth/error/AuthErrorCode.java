package mono.focusider.domain.auth.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mono.focusider.global.error.code.ErrorCode;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AuthErrorCode implements ErrorCode {
    DUPLICATE_MEMBER_ACCOUNT_ID(HttpStatus.FORBIDDEN, "이미 존재하는 아이디입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
