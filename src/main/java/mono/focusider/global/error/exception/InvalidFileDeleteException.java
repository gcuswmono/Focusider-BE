package mono.focusider.global.error.exception;

import mono.focusider.global.error.code.ErrorCode;
import mono.focusider.global.error.code.GlobalErrorCode;

public class InvalidFileDeleteException extends BusinessException {
    public InvalidFileDeleteException() {
        super(GlobalErrorCode.INVALID_FILE_DELETE);
    }

    public InvalidFileDeleteException(ErrorCode errorCode) {
        super(errorCode);
    }
}

