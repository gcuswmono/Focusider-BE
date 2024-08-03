package mono.focusider.global.error.exception;


import mono.focusider.global.error.code.ErrorCode;
import mono.focusider.global.error.code.GlobalErrorCode;

public class InvalidValueException extends BusinessException {
    public InvalidValueException() {
        super(GlobalErrorCode.BAD_REQUEST);
    }

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
