package mono.focusider.domain.article.dto.res;

import lombok.Builder;
import lombok.AccessLevel;

@Builder(access = AccessLevel.PRIVATE)
public record ReadingDetailResDto(
        String title,
        String content) {
    public static ReadingDetailResDto of(String title, String content) {
        return new ReadingDetailResDto(title, content);
    }

}
