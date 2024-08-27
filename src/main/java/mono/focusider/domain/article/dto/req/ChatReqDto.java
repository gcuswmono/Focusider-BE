package mono.focusider.domain.article.dto.req;

public record ChatReqDto(
        Long articleId,
        String question,
        String answer) {

}
