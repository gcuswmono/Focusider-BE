package mono.focusider.domain.file.validate;

import mono.focusider.domain.file.domain.File;
import org.springframework.stereotype.Component;

@Component
public class FileValidate {

    public void validateFileAndUpdateUnUsed(File file) {
        if(file != null) {
            file.updateNotUsed();
        }
    }

    public void validateFileAndUpdateUsed(File file) {
        if(file != null) {
            file.updateUsed();
        }
    }
}
