package mono.focusider.application.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.http.MediaType;

@Slf4j
@RestController
@RequestMapping("/api/article")
@Tag(name = "Article", description = "Article API")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final ChatService chatService;

    @Operation(summary = "아티클 랜덤 조회", description = "아티클 랜덤 조회", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ArticleDetailResDto.class))),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> findArticleRandom(@MemberInfo MemberInfoParam memberInfoParam) {
        ArticleDetailResDto result = articleService.findArticleDetail(memberInfoParam);
        return SuccessResponse.ok(result);
    }

    @PostMapping(value = "/start", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse<?>> startChat(
            @RequestBody ChatStartReqDto requestDto, HttpServletRequest request) {
        log.info("Received DTO: {}", requestDto);
        try {
            ChatResDto chatResponse = chatService.startChat(requestDto, request);
            return SuccessResponse.ok(chatResponse);
        } catch (IllegalArgumentException e) {
            // 필수 정보 누락 시 400 오류 반환
            return SuccessResponse.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error starting GPT chat", e);
            return SuccessResponse.error("Failed to start chat");
        }
    }

    @PostMapping(value = "/continue", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse<?>> continueChat(
            @RequestBody ChatContinueReqDto requestDto, HttpServletRequest request) {
        log.info("Received DTO: {}", requestDto);
        try {
            ChatResDto chatResponse = chatService.continueChat(requestDto, request);
            return SuccessResponse.ok(chatResponse);
        } catch (IllegalArgumentException e) {
            // 필수 정보 누락 시 400 오류 반환
            return SuccessResponse.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error continuing GPT chat", e);
            return SuccessResponse.error("Failed to continue chat");
        }
    }

    @PostMapping(value = "/evaluate-and-end", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse<?>> evaluateUnderstandingAndEndChat(
            @RequestBody ChatContinueReqDto requestDto, HttpServletRequest request) {
        log.info("Received DTO: {}", requestDto);
        try {
            Long understandingScore = chatService.evaluateUnderstandingAndEndChat(requestDto.articleId(), request);
            return SuccessResponse.ok(understandingScore);
        } catch (IllegalArgumentException e) {
            // 필수 정보 누락 시 400 오류 반환
            return SuccessResponse.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error evaluating understanding and ending chat", e);
            return SuccessResponse.error("Failed to evaluate and end chat");
        }
    }
}