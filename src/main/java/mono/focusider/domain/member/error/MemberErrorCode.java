package mono.focusider.domain.member.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mono.focusider.global.error.code.ErrorCode;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MemberErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    MEMBER_NOT_DELETED(HttpStatus.FORBIDDEN, "탈퇴하지 않은 사용자 입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
