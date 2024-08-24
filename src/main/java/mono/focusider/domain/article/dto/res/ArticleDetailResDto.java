package mono.focusider.domain.article.dto.res;

import mono.focusider.domain.category.type.CategoryType;

public record ArticleDetailResDto(
        Long articleId,
        String title,
        String content,
        CategoryType categoryType
) {
}
