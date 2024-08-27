package mono.focusider.domain.article.dto.res;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ChatEndResDto(
        String summary) {
    public static ChatEndResDto of(String summary) {
        return ChatEndResDto
                .builder()
                .summary(summary)
                .build();
    }
}
