package mono.focusider.global.error.exception;


import mono.focusider.global.error.code.ErrorCode;
import mono.focusider.global.error.code.GlobalErrorCode;

public class MethodNotAllowedException extends BusinessException {
    public MethodNotAllowedException() {
        super(GlobalErrorCode.METHOD_NOT_ALLOWED);
    }

    public MethodNotAllowedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
