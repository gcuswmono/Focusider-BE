package mono.focusider.domain.file.dto.info;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record FileUrlInfo(
        String url
) {
    public static FileUrlInfo of(String url) {
        return FileUrlInfo.builder().url(url).build();
    }
}
