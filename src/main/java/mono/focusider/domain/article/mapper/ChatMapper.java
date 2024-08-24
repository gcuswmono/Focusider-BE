package mono.focusider.domain.article.mapper;

import mono.focusider.domain.article.dto.res.ChatResDto;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {
    public ChatResDto toChatResponseDto(Long sessionId, String content) {
        return ChatResDto.of(sessionId, content);
    }
}
