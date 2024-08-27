package mono.focusider.domain.article.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.dto.res.ReadingStatResDto;
import mono.focusider.global.chat.ChatEnum;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatHelper {
    private final OpenAiChatModel chatModel;

    public String getCommentContent(ReadingStatResDto readingStatResDto) {
        String prompt = readingStatResDto + " " + ChatEnum.PROMPT_COMMENT_REQUIRE.getPrefix();
        Prompt gptPrompt = new Prompt(new UserMessage(prompt));
        ChatResponse response = chatModel.call(gptPrompt);
        return response.getResults().getFirst().getOutput().getContent();
    }
}
