package mono.focusider.domain.article.dto.info;

import java.time.LocalDateTime;

public record ReadingStatDetailInfo(
        LocalDateTime readingDate,
        Long readingTime,
        Integer understanding
) {
}
