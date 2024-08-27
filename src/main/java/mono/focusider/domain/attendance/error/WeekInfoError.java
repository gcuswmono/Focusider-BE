package mono.focusider.domain.attendance.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mono.focusider.global.error.code.ErrorCode;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum WeekInfoError implements ErrorCode {
    WEEK_INFO_ERROR(HttpStatus.CONFLICT, "이미 존재하는 주차 정보입니다."),
    WEEK_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "주차 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
