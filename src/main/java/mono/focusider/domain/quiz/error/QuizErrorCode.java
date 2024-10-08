package mono.focusider.domain.quiz.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mono.focusider.global.error.code.ErrorCode;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum QuizErrorCode implements ErrorCode {
    QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "퀴즈를 찾을 수 없습니다."),
    QUIZ_REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "퀴즈 풀이 기록을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
