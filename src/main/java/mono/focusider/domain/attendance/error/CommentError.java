package mono.focusider.domain.attendance.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mono.focusider.global.error.code.ErrorCode;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CommentError implements ErrorCode {
    DUPLICATE_COMMENT(HttpStatus.CONFLICT, "이미 코멘트가 존재하는 정보입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
