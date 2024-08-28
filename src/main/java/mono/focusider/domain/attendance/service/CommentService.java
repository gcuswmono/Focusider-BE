package mono.focusider.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.dto.ConversationEntry;
import mono.focusider.global.chat.ChatEnum;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

}
