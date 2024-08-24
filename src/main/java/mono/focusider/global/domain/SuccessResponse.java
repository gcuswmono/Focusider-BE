package mono.focusider.global.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class SuccessResponse<T> {
    private int status;
    private String message;
    private T data;

    public static <T> ResponseEntity<SuccessResponse<?>> ok(T data) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessCode.OK, data));
    }

    public static <T> ResponseEntity<SuccessResponse<?>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.of(SuccessCode.CREATED, data));
    }

    public static <T> ResponseEntity<SuccessResponse<?>> error(String errorMessage) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(SuccessResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(errorMessage)
                        .data(null) // No data in case of an error
                        .build());
    }

    public static <T> SuccessResponse<?> of(SuccessCode successCode, T data) {
        return SuccessResponse.builder()
                .status(successCode.getHttpStatus().value())
                .message(successCode.getMessage())
                .data(data)
                .build();
    }

}