package mono.focusider.domain.article.mapper;

import mono.focusider.domain.article.dto.res.ChatEndResDto;
import mono.focusider.domain.article.dto.res.ChatResDto;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {
    public ChatResDto toChatResponseDto(String question) {
        return ChatResDto.of(question);
    }

    public ChatEndResDto toChatEndResponseDto(String summary) {
        return ChatEndResDto.of(summary);
    }
}
