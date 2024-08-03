package mono.focusider.global.error.exception;

import mono.focusider.global.error.code.ErrorCode;
import mono.focusider.global.error.code.GlobalErrorCode;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException() {
        super(GlobalErrorCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

