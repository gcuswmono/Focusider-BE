package mono.focusider.global.error.exception;

import mono.focusider.global.error.code.ErrorCode;
import mono.focusider.global.error.code.GlobalErrorCode;

public class ConflictException extends BusinessException {
    public ConflictException() {
        super(GlobalErrorCode.CONFLICT);
    }

    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}


