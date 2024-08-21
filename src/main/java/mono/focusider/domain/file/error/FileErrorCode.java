package mono.focusider.domain.file.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mono.focusider.global.error.code.ErrorCode;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum FileErrorCode implements ErrorCode {
    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST, "옳바르지 않은 URL 입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
