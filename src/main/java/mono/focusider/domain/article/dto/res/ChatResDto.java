package mono.focusider.domain.article.dto.res;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ChatResDto(
        Long sessionId,
        String content) {
    public static ChatResDto of(Long sessionId, String content) {
        return ChatResDto
                .builder()
                .sessionId(sessionId)
                .content(content)
                .build();
    }
}
