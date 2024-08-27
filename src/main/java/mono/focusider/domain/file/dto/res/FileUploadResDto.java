package mono.focusider.domain.file.dto.res;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record FileUploadResDto(
        String imageUrl
) {
    public static FileUploadResDto of(String imageUrl) {
        return FileUploadResDto
                .builder()
                .imageUrl(imageUrl)
                .build();
    }
}
