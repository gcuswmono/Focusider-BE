package mono.focusider.domain.article.dto.req;

public record ChatStartReqDto(
                Long articleId,
                String initialAnswer,
                Long readTime) {

}
