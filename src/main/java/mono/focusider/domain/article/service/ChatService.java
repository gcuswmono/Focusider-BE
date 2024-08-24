package mono.focusider.domain.article.service;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.domain.Article;
import mono.focusider.domain.article.domain.ChatHistory;
import mono.focusider.domain.article.domain.Reading;
import mono.focusider.domain.article.dto.req.ChatContinueReqDto;
import mono.focusider.domain.article.dto.req.ChatStartReqDto;
import mono.focusider.domain.article.dto.res.ChatResDto;
import mono.focusider.domain.article.mapper.ChatMapper;
import mono.focusider.domain.article.repository.ChatHistoryRepository;
import mono.focusider.domain.article.repository.ArticleRepository;
import mono.focusider.global.utils.redis.RedisExpiredData;
import mono.focusider.global.utils.redis.RedisUtils;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final OpenAiChatModel chatModel;
    private final ChatHistoryRepository chatHistoryRepository;
    private final ArticleRepository articleRepository;
    private final RedisUtils redisUtils;
    private final ChatMapper chatMapper;

    /**
     * 0번: 첫 번째 질문 처리 및 Article 정보 저장
     */
    public ChatResDto startChat(ChatStartReqDto requestDto) {
        Long memberId = requestDto.memberId();
        String sessionId = String.valueOf(memberId); // 세션 ID는 사용자 ID로 설정

        // Redis에 세션 ID와 만료 시간 저장 (30분)
        redisUtils.setDataWithExpireTime(
                RedisExpiredData.ofChatSession(sessionId, memberId.toString(), 1800L));

        // Article 조회
        Article article = articleRepository.findById(requestDto.articleId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid article ID"));

        // 대화 내용 초기화 및 첫 질문 포함
        List<Message> conversationHistory = new ArrayList<>();
        String promptText = String.format("Category: %s\nContent: %s\nLevel: %d\nUser: %s",
                article.getCategory(), article.getContent(), article.getLevel(), requestDto.initialAnswer());

        // 첫 번째 GPT 응답 생성
        String gptResponse = getGptResponse(promptText, conversationHistory);

        // 첫 번째 질문과 응답 기록
        UserMessage userMessage = new UserMessage(requestDto.initialAnswer());
        conversationHistory.add(userMessage);
        AssistantMessage aiMessage = new AssistantMessage(gptResponse);
        conversationHistory.add(aiMessage);

        // Redis에 대화 기록 저장 및 만료 시간 갱신
        redisUtils.setDataWithExpireTime(
                RedisExpiredData.ofChatSession(sessionId, conversationHistory.toString(), 1800L));

        return chatMapper.toChatResponseDto(memberId, gptResponse);
    }

    /**
     * 1번: 질문과 답변을 받아서 Redis에 이어 저장
     */
    public void addMessageToChatHistory(Long memberId, String question, String answer) {
        String sessionId = String.valueOf(memberId);
        List<Message> conversationHistory = (List<Message>) redisUtils.getData(sessionId);

        if (conversationHistory == null) {
            throw new IllegalStateException("No chat history found for this session.");
        }

        // 사용자의 질문과 GPT의 응답을 추가
        conversationHistory.add(new UserMessage(question));
        conversationHistory.add(new AssistantMessage(answer));

        // Redis에 다시 저장
        redisUtils.setDataWithExpireTime(
                RedisExpiredData.ofChatSession(sessionId, conversationHistory.toString(), 1800L));
    }

    /**
     * 2번: GPT에게 이어지는 질문 요청 (아동 대상 친근한 질문)
     */
    public ChatResDto continueChat(ChatContinueReqDto requestDto) {
        String sessionId = requestDto.memberId().toString();

        // Redis에서 기존 대화 기록을 가져옴
        List<Message> conversationHistory = (List<Message>) redisUtils.getData(sessionId);

        if (conversationHistory == null) {
            throw new IllegalStateException("No chat history found for this session.");
        }

        // GPT에게 대화 이어가기 요청
        String gptResponse = getGptResponse(requestDto.answer(), conversationHistory);

        // GPT 응답을 대화 기록에 추가
        AssistantMessage aiMessage = new AssistantMessage(gptResponse);
        conversationHistory.add(aiMessage);

        // Redis에 대화 기록 저장 및 만료 시간 갱신
        redisUtils.setDataWithExpireTime(
                RedisExpiredData.ofChatSession(sessionId, conversationHistory.toString(), 1800L));

        // 응답 반환
        return chatMapper.toChatResponseDto(Long.parseLong(sessionId), gptResponse);
    }

    /**
     * 3번: GPT로부터 요약 및 이해도 평가 요청
     */
    public Long evaluateUnderstanding(ChatContinueReqDto requestDto) {
        String sessionId = requestDto.memberId().toString();
        List<Message> conversationHistory = (List<Message>) redisUtils.getData(sessionId);

        if (conversationHistory == null) {
            throw new IllegalStateException("No chat history found for this session.");
        }

        // GPT에게 대화 요약 및 이해도 평가 요청 (프롬프트를 다르게 설정)
        String summaryPrompt = "Summarize the conversation and evaluate the user's understanding.";
        String gptResponse = getGptResponse(summaryPrompt, conversationHistory);

        // GPT로부터 이해도를 받아 Long 타입으로 저장
        Long understandingScore = parseUnderstandingScore(gptResponse);

        return understandingScore;
    }

    /**
     * 4번: Redis 데이터를 MySQL에 저장
     */
    @Transactional
    public void saveChatHistoryToDB(Long memberId) {
        String sessionId = String.valueOf(memberId);
        List<Message> conversationHistory = (List<Message>) redisUtils.getData(sessionId);

        if (conversationHistory == null) {
            throw new IllegalStateException("No chat history found for this session.");
        }

        // ChatHistory와 Reading 데이터를 MySQL에 저장하는 로직 추가
        ChatHistory chatHistory = new ChatHistory();
        // 데이터 매핑 및 저장
        chatHistoryRepository.save(chatHistory);

        // Redis에서 가져온 데이터를 Reading 테이블에 저장
        Reading reading = new Reading();
        // 데이터 매핑 및 저장
        // readingRepository.save(reading);
    }

    /**
     * 5번: Redis 데이터 삭제
     */
    public void deleteChatHistoryFromRedis(Long memberId) {
        String sessionId = String.valueOf(memberId);
        redisUtils.deleteData(sessionId);
    }

    /**
     * GPT에 질문을 던지고 응답을 받는 메소드
     */
    private String getGptResponse(String promptText, List<Message> conversationHistory) {
        // GPT API 호출
        Prompt prompt = new Prompt(conversationHistory); // 대화 기록을 사용하여 프롬프트 생성
        ChatResponse response = chatModel.call(prompt);

        // GPT 응답 반환
        AssistantMessage aiMessage = response.getResults().get(0).getOutput();
        return aiMessage.getContent();
    }

    /**
     * GPT의 이해도 응답을 Long 타입으로 변환하는 메소드
     */
    private Long parseUnderstandingScore(String gptResponse) {
        // GPT의 응답에서 이해도를 추출하는 로직 추가
        // 예시로 숫자를 파싱하는 코드:
        return Long.parseLong(gptResponse.replaceAll("[^0-9]", ""));
    }
}
