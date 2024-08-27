package mono.focusider.application.article.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.article.dto.req.ChatEndReqDto;
import mono.focusider.domain.article.dto.req.ChatReqDto;
import mono.focusider.domain.article.dto.res.*;
import mono.focusider.domain.article.service.ArticleService;
import mono.focusider.domain.article.service.ChatService;
import mono.focusider.domain.article.service.ReadingService;
import mono.focusider.global.annotation.MemberInfo;
import mono.focusider.global.aspect.member.MemberInfoParam;
import mono.focusider.global.domain.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/article")
@Tag(name = "Article", description = "Article API")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final ReadingService readingService;
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

    @Operation(summary = "통계", description = "읽은 아티클 리스트", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ReadingStatResDto.class))),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @GetMapping("/stat/{weekInfoId}")
    public ResponseEntity<SuccessResponse<?>> getMemberStat(@MemberInfo MemberInfoParam memberInfoParam,
                                                            @PathVariable Long weekInfoId) {
        ReadingStatResDto result = articleService.findReadingMonthlyStat(memberInfoParam, weekInfoId);
        return SuccessResponse.ok(result);
    }

    // Reading 목록 조회 API 수정
    @Operation(summary = "읽은 아티클 목록 조회", description = "사용자가 읽은 아티클 목록을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ReadingPageResDto.class))),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @GetMapping("/reading-list")
    public ResponseEntity<SuccessResponse<?>> getReadingList(@MemberInfo MemberInfoParam memberInfoParam,
                                                             Pageable pageable) {
        ReadingPageResDto result = readingService.getReadingList(memberInfoParam, pageable);
        // SuccessResponse의 제네릭 타입을 명시적으로 지정
        return SuccessResponse.ok(result);
    }

    // 특정 Reading 상세 조회 API 수정
    @Operation(summary = "읽은 아티클 상세 조회", description = "특정 아티클의 상세 정보를 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ReadingDetailResDto.class))),
            @ApiResponse(responseCode = "404", description = "아티클을 찾을 수 없습니다.")
    })
    @GetMapping("/reading-detail/{articleId}")
    public ResponseEntity<SuccessResponse<?>> getReadingDetail(@PathVariable Long articleId) {
        ReadingDetailResDto readingDetail = readingService.getReadingDetail(articleId);
        return SuccessResponse.ok(readingDetail);
    }

    // 통합된 chat API: 대화 시작/이어가기 (하나의 메서드로 통합)
    @Operation(summary = "채팅 시작/이어가기", description = "GPT와 대화를 시작하거나 이어서 진행합니다.", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ChatResDto.class))),
            @ApiResponse(responseCode = "400", description = "필수 정보 누락"),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @PostMapping("/chat")
    public ResponseEntity<SuccessResponse<?>> processChat(
            @Valid @RequestBody ChatReqDto requestDto, HttpServletRequest request) {
        log.info("Received DTO: {}", requestDto);
        try {
            ChatResDto chatResponse = chatService.processChat(requestDto, request);
            return SuccessResponse.ok(chatResponse);
        } catch (IllegalArgumentException e) {
            // 필수 정보 누락 시 400 오류 반환
            return SuccessResponse.badRequest(e.getMessage());
        } catch (JsonProcessingException e) {
            log.error("Error processing chat with JSON", e);
            return SuccessResponse.error("Failed to process chat due to JSON processing error");
        } catch (Exception e) {
            log.error("Error processing GPT chat", e);
            return SuccessResponse.error("Failed to process chat");
        }
    }

    /**
     * 대화를 종료하고 사용자의 이해도를 평가합니다.
     */
    @Operation(summary = "대화 종료 및 이해도 평가", description = "대화를 종료하고 사용자의 이해도를 평가합니다.", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ChatEndResDto.class))),
            @ApiResponse(responseCode = "400", description = "필수 정보 누락"),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @PostMapping("/end")
    public ResponseEntity<SuccessResponse<?>> evaluateUnderstandingAndEndChat(
            @RequestBody ChatEndReqDto chatEndReqDto, HttpServletRequest request) {
        log.info("Ending chat for articleId={}, readTime={}", chatEndReqDto.articleId(), chatEndReqDto.readTime());
        try {
            ChatEndResDto chatEndResDto = chatService.evaluateUnderstandingAndEndChat(chatEndReqDto.articleId(),
                    request,
                    chatEndReqDto.readTime());
            return SuccessResponse.ok(chatEndResDto);
        } catch (IllegalArgumentException e) {
            // 필수 정보 누락 시 400 오류 반환
            return SuccessResponse.badRequest(e.getMessage());
        } catch (JsonProcessingException e) {
            log.error("Error processing chat end with JSON", e);
            return SuccessResponse.error("Failed to process end chat due to JSON processing error");
        } catch (Exception e) {
            log.error("Error evaluating understanding and ending chat", e);
            return SuccessResponse.error("Failed to evaluate and end chat");
        }
    }
}
