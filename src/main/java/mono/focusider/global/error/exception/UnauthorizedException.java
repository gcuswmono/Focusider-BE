package mono.focusider.global.error.exception;


import mono.focusider.global.error.code.ErrorCode;
import mono.focusider.global.error.code.GlobalErrorCode;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException() {
        super(GlobalErrorCode.UNAUTHORIZED);
    }

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}

