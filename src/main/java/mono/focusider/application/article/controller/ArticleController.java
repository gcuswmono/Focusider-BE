package mono.focusider.application.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.article.dto.req.ChatContinueReqDto;
import mono.focusider.domain.article.dto.req.ChatStartReqDto;
import mono.focusider.domain.article.dto.res.ArticleDetailResDto;
import mono.focusider.domain.article.dto.res.ChatResDto;
import mono.focusider.domain.article.service.ArticleService;
import mono.focusider.domain.article.service.ChatService;
import mono.focusider.global.annotation.MemberInfo;
import mono.focusider.global.aspect.member.MemberInfoParam;
import mono.focusider.global.domain.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/article")
@Tag(name = "Article", description = "Article API")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @Operation(summary = "아티클 랜덤 조회", description = "아티클 랜덤 조회", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ArticleDetailResDto.class))),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> findArticleRandom(@MemberInfo MemberInfoParam memberInfoParam) {
        ArticleDetailResDto result = articleService.findArticleDetail(memberInfoParam);
        return SuccessResponse.ok(result);
    }

    private final ChatService chatService;

    /**
     * Starts a new GPT chat session
     */
    @Operation(summary = "Start GPT chat", description = "Start a new chat session with GPT", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ChatResDto.class))),
            @ApiResponse(responseCode = "500", description = "Error occurred")
    })
    @PostMapping("/start")
    public ResponseEntity<SuccessResponse<?>> startChat(
            @RequestBody ChatStartReqDto requestDto, @MemberInfo MemberInfoParam memberInfoParam) {
        try {
            ChatResDto chatResponse = chatService.startChat(requestDto);
            return SuccessResponse.ok(chatResponse); // Return standard success response
        } catch (Exception e) {
            log.error("Error starting GPT chat", e);
            return SuccessResponse.error("Failed to start chat");
        }
    }

    /**
     * Continue an existing GPT chat session
     */
    @Operation(summary = "Continue GPT chat", description = "Continue the chat session with GPT", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ChatResDto.class))),
            @ApiResponse(responseCode = "500", description = "Error occurred")
    })
    @PostMapping("/continue")
    public ResponseEntity<SuccessResponse<?>> continueChat(
            @RequestBody ChatContinueReqDto requestDto, @MemberInfo MemberInfoParam memberInfoParam) {
        try {
            ChatResDto chatResponse = chatService.continueChat(requestDto);
            return SuccessResponse.ok(chatResponse); // Return standard success response
        } catch (Exception e) {
            log.error("Error continuing GPT chat", e);
            return SuccessResponse.error("Failed to continue chat");
        }
    }

    /**
     * Evaluate user's understanding from the chat session
     */
    @Operation(summary = "Evaluate understanding", description = "Evaluate the user's understanding of the conversation", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "500", description = "Error occurred")
    })
    @PostMapping("/evaluate")
    public ResponseEntity<SuccessResponse<?>> evaluateUnderstanding(
            @RequestBody ChatContinueReqDto requestDto, @MemberInfo MemberInfoParam memberInfoParam) {
        try {
            Long understandingScore = chatService.evaluateUnderstanding(requestDto);
            return SuccessResponse.ok(understandingScore); // Return standard success response
        } catch (Exception e) {
            log.error("Error evaluating understanding", e);
            return SuccessResponse.error("Failed to evaluate understanding");
        }
    }
}
