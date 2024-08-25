package mono.focusider.domain.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.article.domain.Article;
import mono.focusider.domain.article.domain.ChatHistory;
import mono.focusider.domain.article.domain.Reading;
import mono.focusider.domain.article.dto.ConversationEntry;
import mono.focusider.domain.article.dto.req.ChatReqDto;
import mono.focusider.domain.article.dto.res.ChatResDto;
import mono.focusider.domain.article.mapper.ChatMapper;
import mono.focusider.domain.article.repository.ArticleRepository;
import mono.focusider.domain.article.repository.ChatHistoryRepository;
import mono.focusider.domain.article.repository.ReadingRepository;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.repository.MemberRepository;
import mono.focusider.global.utils.redis.RedisExpiredData;
import mono.focusider.global.utils.redis.RedisUtils;
import mono.focusider.global.security.JwtUtil;
import mono.focusider.global.utils.cookie.CookieUtils;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final OpenAiChatModel chatModel;
    private final ArticleRepository articleRepository;
    private final ReadingRepository readingRepository;
    private final MemberRepository memberRepository;
    private final ChatHistoryRepository chatHistoryRepository;
    private final RedisUtils redisUtils;
    private final ChatMapper chatMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtUtil jwtUtil;

    private Long getMemberIdFromToken(HttpServletRequest request) {
        String accessToken = CookieUtils.getCookieValueWithName(request, "accessToken");
        if (accessToken == null) {
            throw new IllegalStateException("Access token not found in cookies.");
        }
        return jwtUtil.getMemberId(accessToken); // JWT에서 memberId 추출
    }

    // 1. 질문과 답변 묶음을 List로 저장하는 메서드
    private List<ConversationEntry> createConversationEntry(Long articleId, String answer) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid article ID"));

        // 처음 질문을 담고, 그에 대한 유저의 답변을 담음
        List<ConversationEntry> conversation = new ArrayList<>();
        conversation.add(new ConversationEntry(article.getDefaultQuestion(), answer));

        return conversation;
    }

    // 2. Redis에 질문과 답변 배열 형식으로 저장하는 메서드
    private void saveToRedis(Long memberId, List<ConversationEntry> newEntries) throws JsonProcessingException {
        // 기존 대화 기록을 Redis에서 가져옴
        List<ConversationEntry> existingConversation = getChatFromRedisOrEmpty(memberId);

        // 새로운 대화 항목을 기존 대화에 추가
        existingConversation.addAll(newEntries);

        // 누적된 대화를 Redis에 저장
        String conversationJson = objectMapper.writeValueAsString(existingConversation);
        redisUtils
                .setDataWithExpireTime(RedisExpiredData.ofChatSession(memberId.toString(), conversationJson, 1800000L));
    }

    // Redis에서 대화 기록을 가져오거나 없으면 빈 리스트 반환
    private List<ConversationEntry> getChatFromRedisOrEmpty(Long memberId) {
        try {
            return getChatFromRedis(memberId);
        } catch (IllegalStateException | JsonProcessingException e) {
            return new ArrayList<>(); // 대화 기록이 없을 경우 빈 리스트 반환
        }
    }

    // 3. Redis 역직렬화해서 다시 String 타입으로 반환하는 메서드
    private List<ConversationEntry> getChatFromRedis(Long memberId) throws JsonProcessingException {
        String conversationHistoryJson = (String) redisUtils.getData("chat_session:" + memberId.toString());
        if (conversationHistoryJson == null) {
            throw new IllegalStateException("No conversation found for this session.");
        }

        // JSON 데이터를 로그로 출력하여 확인
        log.info("conversationHistoryJson: {}", conversationHistoryJson);

        // JSON 데이터를 List<ConversationEntry>로 역직렬화
        return objectMapper.readValue(conversationHistoryJson, new TypeReference<List<ConversationEntry>>() {
        });
    }

    // 4. GPT에게 이어지는 질문을 생성하도록 지시하는 메서드
    private String generateNextQuestion(List<ConversationEntry> conversationHistory) {
        // 기존 대화 내용을 Message 타입으로 변환
        List<Message> messages = new ArrayList<>();
        for (ConversationEntry entry : conversationHistory) {
            if (entry.getQuestion() != null) {
                messages.add(new UserMessage(entry.getQuestion())); // UserMessage를 추가
            }
            if (entry.getAnswer() != null) {
                messages.add(new AssistantMessage(entry.getAnswer())); // AssistantMessage를 추가
            }
        }

        // GPT에게 전달할 추가 메시지 생성
        String promptText = "학생의 이전 응답을 바탕으로 이해도를 평가할 수 있는 신중한 질문을 생성해 주세요.";
        messages.add(new UserMessage(promptText)); // UserMessage로 프롬프트 메시지 추가

        // Prompt 생성 시 List<Message> 사용
        Prompt prompt = new Prompt(messages);

        // GPT 응답 처리
        ChatResponse response = chatModel.call(prompt);
        return response.getResults().get(0).getOutput().getContent();
    }

    // 4. GPT에게 요약 요청
    public String summarizeConversation(List<ConversationEntry> conversationEntries) {
        String prompt = "다음 대화를 요약해 주세요.: " + conversationEntries.toString();
        Prompt gptPrompt = new Prompt(new UserMessage(prompt));
        ChatResponse response = chatModel.call(gptPrompt);
        return response.getResults().get(0).getOutput().getContent();
    }

    // 5. GPT에게 이해도 평가 요청
    public Long evaluateUnderstanding(List<ConversationEntry> conversationEntries, Article article) {
        // 대화 내용과 아티클 내용을 프롬프트에 포함하여 GPT에 전달
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder
                .append("다음 대화를 바탕으로 사용자가 해당 글을 얼마나 이해했는지 0에서 100까지의 숫자로 평가해 주세요.\n\n");

        // 아티클 내용을 추가
        promptBuilder.append("Article Content:\n");
        promptBuilder.append(article.getContent()).append("\n\n");

        // 사용자 대화 기록 추가
        promptBuilder.append("Conversation:\n");
        for (ConversationEntry entry : conversationEntries) {
            promptBuilder.append("Question: ").append(entry.getQuestion()).append("\n");
            promptBuilder.append("Answer: ").append(entry.getAnswer()).append("\n\n");
        }

        // GPT에게 이해도 평가 요청
        String prompt = promptBuilder.toString();
        Prompt gptPrompt = new Prompt(new UserMessage(prompt));
        ChatResponse response = chatModel.call(gptPrompt);

        // GPT 응답 처리
        String gptResponse = response.getResults().get(0).getOutput().getContent();

        // GPT 응답을 확인하고 숫자를 추출할 수 있는지 체크
        log.info("GPT response: {}", gptResponse);

        // GPT 응답에서 숫자를 추출하려고 시도
        String numericPart = gptResponse.replaceAll("[^0-9]", ""); // 숫자만 남김

        // 숫자가 없을 경우 기본값 반환
        if (numericPart.isEmpty()) {
            log.warn("GPT response did not contain a number, defaulting to 0.");
            return 0L; // 기본값으로 0을 반환하거나 다른 처리 방법 선택
        }

        try {
            // 이해도를 숫자로 변환하여 반환
            return Long.parseLong(numericPart);
        } catch (NumberFormatException e) {
            log.error("Failed to parse GPT response to Long: {}", gptResponse, e);
            return 0L; // 파싱 실패 시 기본값으로 0을 반환
        }
    }

    // 6. 대화 종료 및 이해도 평가 메서드 추가
    public String evaluateUnderstandingAndEndChat(Long articleId, HttpServletRequest request, Long readTime)
            throws JsonProcessingException {
        Long memberId = getMemberIdFromToken(request);

        // Article 엔티티 가져오기
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid article ID"));

        // Redis에서 대화 기록 가져오기
        List<ConversationEntry> conversationEntries = getChatFromRedis(memberId);

        // 1. 요약 생성
        String summary = summarizeConversation(conversationEntries);

        // 2. 이해도 평가
        Long understandingScore = evaluateUnderstanding(conversationEntries, article);

        // 3. Reading 엔티티 저장
        Reading reading = saveReading(memberId, article, readTime, summary, understandingScore);

        // 4. ChatHistory 엔티티 저장
        saveChatHistory(reading, article, conversationEntries);

        // 5. Redis 데이터 삭제
        deleteChatFromRedis(memberId);

        return summary; // 요약을 반환
    }

    // 7. processChat 메서드 - 컨트롤러에서 호출
    public ChatResDto processChat(ChatReqDto requestDto, HttpServletRequest request) throws JsonProcessingException {
        Long memberId = getMemberIdFromToken(request);

        // Redis에서 대화 기록 가져오기 (없으면 빈 리스트 반환)
        List<ConversationEntry> conversationHistory = getChatFromRedisOrEmpty(memberId);

        // 새로운 대화를 추가 (이전 대화가 없으면 새로 시작)
        if (conversationHistory.isEmpty()) {
            conversationHistory = createConversationEntry(requestDto.articleId(), requestDto.answer());
        } else {
            // 기존 마지막 질문에 대한 답변 추가
            ConversationEntry lastEntry = conversationHistory.get(conversationHistory.size() - 1);
            lastEntry.setQuestion(requestDto.question());
            lastEntry.setAnswer(requestDto.answer());
        }

        // 누적된 대화를 Redis에 저장
        saveToRedis(memberId, conversationHistory);

        // GPT에게 이어지는 질문 생성 요청
        String gptQuestion = generateNextQuestion(conversationHistory);

        // GPT 질문 반환 (저장하지 않음)
        return chatMapper.toChatResponseDto(memberId, gptQuestion);
    }

    public void deleteChatFromRedis(Long memberId) {
        redisUtils.deleteData("chat_session:" + memberId);
    }

    private Reading saveReading(Long memberId, Article article, Long readTime, String summary,
            Long understandingScore) {
        // Reading 엔티티 생성
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        Reading reading = Reading.createReading(member, article, readTime, summary, understandingScore.intValue());

        // Reading 엔티티를 데이터베이스에 저장
        return readingRepository.save(reading); // 저장된 Reading 엔티티 반환
    }

    private void saveChatHistory(Reading reading, Article article, List<ConversationEntry> conversationEntries) {
        // ConversationEntry 리스트를 순회하며 질문과 답변을 ChatHistory에 저장
        for (ConversationEntry entry : conversationEntries) {
            // 질문 저장
            if (entry.getQuestion() != null && entry.getAnswer() != null) {
                ChatHistory chatHistory = ChatHistory.createWithQuestionAnsAnswer(reading, article,
                        entry.getQuestion(), entry.getAnswer());
                chatHistoryRepository.save(chatHistory); // ChatHistory 엔티티 저장
            }
        }
    }
}
