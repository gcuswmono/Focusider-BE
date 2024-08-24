package mono.focusider.domain.article.service;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.domain.ChatHistory;
import mono.focusider.domain.article.dto.ChatContinueRequestDto;
import mono.focusider.domain.article.dto.ChatResponseDto;
import mono.focusider.domain.article.dto.ChatStartRequestDto;
import mono.focusider.domain.article.repository.ChatHistoryRepository;
import mono.focusider.domain.article.repository.ArticleRepository;
import mono.focusider.domain.member.repository.MemberRepository;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final OpenAiChatModel chatModel;
    private final ChatHistoryRepository chatHistoryRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    /**
     * 첫 번째 대화 시작 시 세션 ID를 생성하고, 대화 상태를 서버에서 관리합니다.
     */
    public ChatResponseDto startChat(ChatStartRequestDto requestDto, HttpSession session) {
        // 세션 ID를 생성하거나, 기존 세션이 있으면 해당 세션 사용
        String sessionId = (String) session.getAttribute("sessionId");
        if (sessionId == null) {
            sessionId = UUID.randomUUID().toString(); // 고유한 세션 ID 생성
            session.setAttribute("sessionId", sessionId);
        }

        // 대화 상태를 세션에 저장
        List<Message> conversationHistory = new ArrayList<>();
        session.setAttribute(sessionId + "_conversation", conversationHistory);

        // GPT로부터 첫 번째 응답 생성
        String gptResponse = getGptResponse(requestDto.getInitialAnswer(), conversationHistory);

        // 세션에 첫 번째 질문과 응답을 저장
        UserMessage userMessage = new UserMessage(requestDto.getInitialAnswer());
        conversationHistory.add(userMessage);

        AssistantMessage aiMessage = new AssistantMessage(gptResponse);
        conversationHistory.add(aiMessage);

        session.setAttribute(sessionId + "_conversation", conversationHistory);

        // 응답 반환
        ChatResponseDto responseDto = new ChatResponseDto();
        responseDto.setSessionId(sessionId);
        responseDto.setContent(gptResponse);

        return responseDto;
    }

    /**
     * 사용자의 응답을 처리하고, 서버 측 세션에서 대화 상태를 유지합니다.
     */
    public ChatResponseDto continueChat(ChatContinueRequestDto requestDto, HttpSession session) {
        String sessionId = requestDto.getMemberId();

        // 세션에서 대화 기록을 가져오기
        List<Message> conversationHistory = (List<Message>) session.getAttribute(sessionId + "_conversation");
        if (conversationHistory == null) {
            throw new IllegalStateException("대화 세션이 존재하지 않습니다.");
        }

        // 사용자의 응답을 기록
        UserMessage userMessage = new UserMessage(requestDto.getAnswer());
        conversationHistory.add(userMessage);

        // GPT로부터 응답 생성
        String gptResponse = getGptResponse(requestDto.getAnswer(), conversationHistory);

        // GPT 응답을 대화 기록에 추가
        AssistantMessage aiMessage = new AssistantMessage(gptResponse);
        conversationHistory.add(aiMessage);

        // 세션에 대화 기록 저장
        session.setAttribute(sessionId + "_conversation", conversationHistory);

        // 응답 반환
        ChatResponseDto responseDto = new ChatResponseDto();
        responseDto.setSessionId(sessionId);
        responseDto.setContent(gptResponse);

        return responseDto;
    }

    /**
     * 대화 종료 시 세션에서 데이터를 가져와 저장하고, 세션을 종료합니다.
     */
    public void finishChat(String memberId, HttpSession session) {
        String sessionId = memberId;

        // 세션에서 대화 기록을 가져오기
        List<Message> conversationHistory = (List<Message>) session.getAttribute(sessionId + "_conversation");
        if (conversationHistory == null) {
            throw new IllegalStateException("대화 세션이 존재하지 않습니다.");
        }

        // 대화 기록을 DB에 저장 (ChatHistory에 저장)
        for (int i = 0; i < conversationHistory.size(); i += 2) {
            UserMessage userMessage = (UserMessage) conversationHistory.get(i);
            AssistantMessage aiMessage = (AssistantMessage) conversationHistory.get(i + 1);

            ChatHistory chatHistory = ChatHistory.builder()
                    .question(userMessage.getContent())
                    .answer(aiMessage.getContent())
                    .build();

            chatHistoryRepository.save(chatHistory);
        }

        // 세션에서 대화 상태 제거
        session.removeAttribute(sessionId + "_conversation");
        session.invalidate(); // 세션 완전히 종료
    }

    /**
     * GPT에 질문을 던지고 응답을 받는 메소드 (GPT와 상호작용).
     */
    private String getGptResponse(String promptText, List<Message> conversationHistory) {
        UserMessage userMessage = new UserMessage(promptText);
        conversationHistory.add(userMessage);

        // GPT API 호출
        Prompt prompt = new Prompt(conversationHistory);
        ChatResponse response = chatModel.call(prompt);

        AssistantMessage aiMessage = response.getResults().get(0).getOutput();
        return aiMessage.getContent();
    }
}
