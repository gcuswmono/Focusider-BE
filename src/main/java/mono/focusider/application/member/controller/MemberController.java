package mono.focusider.application.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.member.dto.req.MemberCategorySaveReqDto;
import mono.focusider.domain.member.service.MemberService;
import mono.focusider.global.annotation.MemberInfo;
import mono.focusider.global.aspect.member.MemberInfoParam;
import mono.focusider.global.domain.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/member")
@Tag(name = "Member", description = "Member API")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "카테고리 설정", description = "유저 카테고리 설정", responses = {
            @ApiResponse(responseCode = "200", description = "카테고리 설정 완료"),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @PostMapping("/add")
    public ResponseEntity<SuccessResponse<?>> add(@RequestBody MemberCategorySaveReqDto req,
                                                  @MemberInfo MemberInfoParam memberInfo) {
        log.info("memberId= {}", memberInfo.memberId());
        memberService.createMemberCategory(req, memberInfo);
        return SuccessResponse.ok(null);
    }
}
