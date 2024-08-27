package mono.focusider.domain.article.dto.info;

import mono.focusider.domain.category.type.CategoryType;

import java.time.LocalDateTime;

public record ReadingStatInfo(
        Long readingId,
        String title,
        CategoryType articleCategory,
        Long readingTime,
        Integer understanding,
        LocalDateTime createdAt
) {
}
