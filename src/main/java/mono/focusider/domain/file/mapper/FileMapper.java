package mono.focusider.domain.file.mapper;

import mono.focusider.domain.file.domain.File;
import mono.focusider.domain.file.dto.res.FileUploadResDto;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

    public FileUploadResDto toProfileImageResDto(File file) {
        return FileUploadResDto.of(file.getUrl());
    }
}
