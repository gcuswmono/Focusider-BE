package mono.focusider.application.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.article.dto.res.ArticleDetailResDto;
import mono.focusider.domain.article.service.ArticleService;
import mono.focusider.domain.article.dto.req.ReadingStatReqDto;
import mono.focusider.domain.article.dto.res.ReadingStatResDto;
import mono.focusider.global.annotation.MemberInfo;
import mono.focusider.global.aspect.member.MemberInfoParam;
import mono.focusider.global.domain.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            @ApiResponse(responseCode = "200",  content = @Content(schema = @Schema(implementation = ArticleDetailResDto.class))),
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
    @GetMapping("/stat")
    public ResponseEntity<SuccessResponse<?>> getMemberStat(@MemberInfo MemberInfoParam memberInfoParam,
                                                            @RequestBody ReadingStatReqDto reqDto) {
        ReadingStatResDto result = articleService.findReadingMonthlyStat(memberInfoParam, reqDto);
        return SuccessResponse.ok(result);
    }
}
