package mono.focusider.global.utils.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.global.error.exception.InvalidFileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import static mono.focusider.global.error.code.GlobalErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Utils {
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.s3.upload-path}")
    private String defaultUrl;

    public String uploadFile(FileType imageType, MultipartFile multipartFile) {
        validateMultipartFile(multipartFile);
        String savedFileName = getSavedFileName(multipartFile, imageType);
        ObjectMetadata metadata = new ObjectMetadata();
        setFileContentType(metadata, multipartFile);
        uploadFileToS3(multipartFile, savedFileName, metadata);
        return getResourceUrl(savedFileName);
    }

    public void deleteFile(String fileUrl) {
        String fileName = getFileNameFromResourceUrl(fileUrl);
        try {
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, encodedFileName));
        } catch (UnsupportedEncodingException e) {
            throw new InvalidFileUploadException(FAILED_FILE_DELETE);
        }
    }

    private void uploadFileToS3(MultipartFile multipartFile, String savedFileName, ObjectMetadata metadata) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(bucketName, savedFileName, inputStream, metadata);
        } catch (IOException e) {
            throw new InvalidFileUploadException(FAILED_FILE_UPLOAD);
        }
    }

    private void validateMultipartFile(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new InvalidFileUploadException(INVALID_MULTIPART_FILE);
        }
    }

    private String getSavedFileName(MultipartFile multipartFile, FileType imageType) {
        return String.format("%s/%s-%s", imageType.getDesc(), getRandomUUID(), multipartFile.getOriginalFilename());
    }

    private String getResourceUrl(String savedFileName) {
        return amazonS3Client.getResourceUrl(bucketName, savedFileName);
    }

    private String getFileNameFromResourceUrl(String fileUrl) {
        return fileUrl.replace(defaultUrl + "/", "");
    }

    private String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private void setFileContentType(ObjectMetadata metadata, MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        if (contentType == null) {
            throw new InvalidFileUploadException(INVALID_FILE_CONTENT_TYPE);
        }
        metadata.setContentType(contentType);
    }
}