package mono.focusider.domain.article.dto.res;

public record ArticleDetailResDto(
        Long articleId,
        String title,
        String content
) {
}
