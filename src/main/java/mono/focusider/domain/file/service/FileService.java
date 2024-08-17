package mono.focusider.domain.file.service;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.file.domain.File;
import mono.focusider.domain.file.dto.res.FileUploadResDto;
import mono.focusider.domain.file.helper.FileHelper;
import mono.focusider.domain.file.mapper.FileMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {
    private final FileHelper fileHelper;
    private final FileMapper fileMapper;

    @Transactional
    public FileUploadResDto createFile(MultipartFile file) {
        File foundFile = fileHelper.uploadFile(file);
        return fileMapper.toProfileImageResDto(foundFile);
    }
}
