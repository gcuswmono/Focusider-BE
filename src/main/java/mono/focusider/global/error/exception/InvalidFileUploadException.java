package mono.focusider.global.error.exception;

import mono.focusider.global.error.code.ErrorCode;
import mono.focusider.global.error.code.GlobalErrorCode;

public class InvalidFileUploadException extends BusinessException {
    public InvalidFileUploadException() {
        super(GlobalErrorCode.INVALID_FILE_UPLOAD);
    }

    public InvalidFileUploadException(ErrorCode errorCode) {
        super(errorCode);
    }
}

