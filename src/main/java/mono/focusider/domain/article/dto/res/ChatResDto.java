package mono.focusider.domain.article.dto.res;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ChatResDto(
        String question) {
    public static ChatResDto of(String question) {
        return ChatResDto
                .builder()
                .question(question)
                .build();
    }
}
