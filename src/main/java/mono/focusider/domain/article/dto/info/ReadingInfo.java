package mono.focusider.domain.article.dto.info;

import mono.focusider.domain.category.type.CategoryType;

import java.time.LocalDateTime;

public record ReadingInfo(
        Long articleId,
        String title,
        CategoryType categoryType,
        LocalDateTime readingDate
) {
}
