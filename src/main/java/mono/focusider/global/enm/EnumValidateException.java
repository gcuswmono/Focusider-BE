package mono.focusider.global.enm;

import mono.focusider.global.error.code.GlobalErrorCode;
import mono.focusider.global.error.exception.BusinessException;

public class EnumValidateException extends BusinessException {
    public static final BusinessException EXCEPTION = new EnumValidateException();

    public EnumValidateException() {
        super(GlobalErrorCode.INVALID_ENUM_CODE);
    }

}