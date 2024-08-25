package mono.focusider.domain.article.dto.res;

import lombok.Builder;
import lombok.AccessLevel;
import mono.focusider.domain.category.type.CategoryType;

@Builder(access = AccessLevel.PRIVATE)
public record ArticleDetailResDto(
                Long articleId,
                String title,
                String content,
                String question,
                CategoryType categoryType) {
}
