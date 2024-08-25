package mono.focusider.domain.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.article.domain.Article;
import mono.focusider.domain.article.domain.ChatHistory;
import mono.focusider.domain.article.domain.Reading;
import mono.focusider.domain.article.dto.req.ChatContinueReqDto;
import mono.focusider.domain.article.dto.req.ChatStartReqDto;
import mono.focusider.domain.article.dto.res.ChatResDto;
import mono.focusider.domain.article.mapper.ChatMapper;
import mono.focusider.domain.article.repository.ChatHistoryRepository;
import mono.focusider.domain.article.repository.ReadingRepository;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.helper.MemberHelper;
import mono.focusider.domain.article.repository.ArticleRepository;
import mono.focusider.global.utils.redis.RedisExpiredData;
import mono.focusider.global.utils.redis.RedisUtils;
import mono.focusider.global.security.JwtUtil;
import mono.focusider.global.utils.cookie.CookieUtils;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final OpenAiChatModel chatModel;
    private final ChatHistoryRepository chatHistoryRepository;
    private final ArticleRepository articleRepository;
    private final RedisUtils redisUtils;
    private final ChatMapper chatMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MemberHelper memberHelper; // 추가
    private final ReadingRepository readingRepository; // 추가
    private final JwtUtil jwtUtil; // 추가

    // 필수 정보 검증 로직 추가
    private void validateChatRequest(String content, String errorMessage) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private List<Message> getConversationHistory(Long memberId) throws JsonProcessingException {
        String sessionId = memberId.toString();
        String conversationHistoryJson = (String) redisUtils.getData(sessionId);
        if (conversationHistoryJson == null) {
            throw new IllegalStateException("No chat history found for this session.");
        }
        return objectMapper.readValue(conversationHistoryJson, new TypeReference<List<Message>>() {
        });
    }

    private Long getMemberIdFromToken(HttpServletRequest request) {
        String accessToken = CookieUtils.getCookieValueWithName(request, "accessToken");
        if (accessToken == null) {
            throw new IllegalStateException("Access token not found in cookies.");
        }
        return jwtUtil.getMemberId(accessToken); // JWT에서 memberId 추출
    }

    /**
     * 0번: 첫 번째 질문 처리 및 Article 정보 저장
     */
    public ChatResDto startChat(ChatStartReqDto requestDto, HttpServletRequest request) throws JsonProcessingException {
        Long memberId = getMemberIdFromToken(request);

        // 필수 정보 검증: initialAnswer
        validateChatRequest(requestDto.initialAnswer(), "Initial answer is missing");

        String sessionId = String.valueOf(memberId); // 세션 ID는 사용자 ID로 설정

        // Article 조회
        Article article = articleRepository.findById(requestDto.articleId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid article ID"));

        // 대화 내용 초기화 및 첫 질문 포함
        List<Message> conversationHistory = new ArrayList<>();
        String promptText = String.format("Category: %s\nContent: %s\nLevel: %d\nUser: %s",
                article.getCategoryType(), article.getContent(), article.getLevel(), requestDto.initialAnswer());

        // 첫 번째 GPT 응답 생성
        String gptResponse = getGptResponse(promptText, conversationHistory);

        // 첫 번째 질문과 응답 기록
        UserMessage userMessage = new UserMessage(requestDto.initialAnswer());
        conversationHistory.add(userMessage);
        AssistantMessage aiMessage = new AssistantMessage(gptResponse);
        conversationHistory.add(aiMessage);

        // Redis에 저장
        String conversationHistoryJson = objectMapper.writeValueAsString(conversationHistory);
        redisUtils.setDataWithExpireTime(
                RedisExpiredData.ofChatSession(sessionId, conversationHistoryJson, 1800L));

        return chatMapper.toChatResponseDto(memberId, gptResponse);
    }

    /**
     * 질문과 답변을 받아서 Redis에 이어 저장
     */
    public void addMessageToChatHistory(Long memberId, String question, String answer) throws JsonProcessingException {
        String sessionId = String.valueOf(memberId);
        // Redis에서 기존 대화 기록을 가져옴 (JSON 형식으로 역직렬화)
        String conversationHistoryJson = (String) redisUtils.getData(sessionId);
        List<Message> conversationHistory = objectMapper.readValue(conversationHistoryJson,
                new TypeReference<List<Message>>() {
                });

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
     * GPT에게 이어지는 질문 요청 (아동 대상 친근한 질문)
     */
    public ChatResDto continueChat(ChatContinueReqDto requestDto, HttpServletRequest request)
            throws JsonProcessingException {
        String sessionId = getMemberIdFromToken(request).toString();

        // 필수 정보 검증: answer
        validateChatRequest(requestDto.answer(), "Answer is missing");

        // Redis에서 대화 기록을 가져옴
        String conversationHistoryJson = (String) redisUtils.getData(sessionId);
        List<Message> conversationHistory = objectMapper.readValue(conversationHistoryJson,
                new TypeReference<List<Message>>() {
                });

        if (conversationHistory == null) {
            throw new IllegalStateException("No chat history found for this session.");
        }

        // GPT에게 대화 이어가기 요청
        String gptResponse = getGptResponse(requestDto.answer(), conversationHistory);

        // 응답을 대화 기록에 추가
        AssistantMessage aiMessage = new AssistantMessage(gptResponse);
        conversationHistory.add(aiMessage);

        // Redis에 저장
        String updatedConversationHistoryJson = objectMapper.writeValueAsString(conversationHistory);
        redisUtils.setDataWithExpireTime(
                RedisExpiredData.ofChatSession(sessionId, updatedConversationHistoryJson, 1800L));

        return chatMapper.toChatResponseDto(Long.parseLong(sessionId), gptResponse);
    }

    /**
     * 대화를 요약하는 메소드
     */
    public String summarizeConversation(Long memberId) throws JsonProcessingException {
        List<Message> conversationHistory = getConversationHistory(memberId);

        // GPT에게 대화 요약 요청
        String summaryPrompt = "Summarize the conversation.";
        String gptResponse = getGptResponse(summaryPrompt, conversationHistory);

        // 요약된 내용을 반환
        return gptResponse;
    }

    /**
     * 사용자의 이해도를 평가하는 메소드
     */
    public Long evaluateUnderstanding(Long memberId) throws JsonProcessingException {
        List<Message> conversationHistory = getConversationHistory(memberId);

        // GPT에게 이해도 평가 요청
        String evaluationPrompt = "Evaluate the user's understanding based on the conversation.";
        String gptResponse = getGptResponse(evaluationPrompt, conversationHistory);

        // GPT로부터 이해도를 받아 Long 타입으로 변환
        Long understandingScore = parseUnderstandingScore(gptResponse);

        return understandingScore;
    }

    /**
     * Redis 데이터를 MariaDB에 저장
     */
    @Transactional
    public Long evaluateUnderstandingAndEndChat(Long articleId, HttpServletRequest request)
            throws JsonProcessingException {
        Long memberId = getMemberIdFromToken(request);
        List<Message> conversationHistory = getConversationHistory(memberId);

        // Member 및 Article 조회
        Member member = memberHelper.findMemberByIdOrThrow(memberId);
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid article ID"));

        // 1. 대화 요약
        String summary = summarizeConversation(memberId);

        // 2. 이해도 평가
        Long understandingScore = evaluateUnderstanding(memberId);

        // 3. Reading 엔티티 저장
        Reading reading = Reading.createReading(
                member,
                article,
                System.currentTimeMillis(), // 실제 읽기 시간
                summary, // 요약 내용
                understandingScore.intValue()); // 이해도 점수
        readingRepository.save(reading);

        // ChatHistory 저장
        saveChatHistory(reading, article, conversationHistory);

        // Redis 데이터 삭제
        deleteChatHistoryFromRedis(memberId);

        // 8. 최종적으로 이해도 점수를 반환
        return understandingScore;
    }

    /**
     * ChatHistory 저장을 분리한 메소드
     */
    private void saveChatHistory(Reading reading, Article article, List<Message> conversationHistory) {
        for (Message message : conversationHistory) {
            if (message instanceof UserMessage userMessage) {
                // 팩토리 메소드로 UserMessage 저장
                ChatHistory chatHistory = ChatHistory.createWithQuestion(reading, article, userMessage.getContent());
                chatHistoryRepository.save(chatHistory);
            } else if (message instanceof AssistantMessage assistantMessage) {
                // 팩토리 메소드로 AssistantMessage 저장
                ChatHistory chatHistory = ChatHistory.createWithAnswer(reading, article, assistantMessage.getContent());
                chatHistoryRepository.save(chatHistory);
            }
        }
    }

    /**
     * Redis 데이터 삭제
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
