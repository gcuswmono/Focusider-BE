package mono.focusider.domain.file.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.file.domain.File;
import mono.focusider.domain.file.repository.FileRepository;
import mono.focusider.global.error.exception.InvalidValueException;
import mono.focusider.global.utils.s3.FileType;
import mono.focusider.global.utils.s3.S3Utils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import static mono.focusider.domain.file.error.FileErrorCode.FILE_NOT_FOUND;
import static mono.focusider.domain.file.type.FileStatus.NOT_USED;

@Component
@RequiredArgsConstructor
public class FileHelper {

    private final S3Utils s3Utils;
    private final FileRepository fileRepository;

    public void deleteFile(String url) {
        s3Utils.deleteFile(url);
    }

    public File uploadFile(MultipartFile file) {
        String url = s3Utils.uploadFile(FileType.MEMBER_PROFILE_IMAGE, file);
        return fileRepository.save(File.createFile(url, NOT_USED.getIsUsed()));
    }

    public File findFileByUrlOrNull(String url) {
        return fileRepository.findByUrl(url)
                .orElse(null);
    }

    public File findFileByUrl(String url) {
        return fileRepository.findByUrl(url)
                .orElseThrow(() -> new InvalidValueException(FILE_NOT_FOUND));
    }

    public void checkFileIsPresent(File profileImageFile) {
        if(profileImageFile != null)
            profileImageFile.updateUsed();
    }
}
