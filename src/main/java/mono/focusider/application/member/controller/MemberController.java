package mono.focusider.application.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.member.dto.req.MemberCategorySaveReqDto;
import mono.focusider.domain.member.dto.req.MemberUpdateReqDto;
import mono.focusider.domain.member.service.MemberService;
import mono.focusider.global.annotation.MemberInfo;
import mono.focusider.global.aspect.member.MemberInfoParam;
import mono.focusider.global.domain.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/member")
@Tag(name = "Member", description = "Member API")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "카테고리 및 레벨 설정", description = "유저 프로필 설정", responses = {
            @ApiResponse(responseCode = "200", description = "프로필 설정 완료"),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @PostMapping("/add")
    public ResponseEntity<SuccessResponse<?>> add(@RequestBody MemberCategorySaveReqDto req) {
        memberService.createMemberCategory(req);
        return SuccessResponse.ok(null);
    }

    @Operation(summary = "유저 마이페이지 정보 수정", description = "마이페이지 정보 수정", responses = {
            @ApiResponse(responseCode = "200", description = "수정 완료"),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @PatchMapping
    public ResponseEntity<SuccessResponse<?>> updateMemberInfo(@RequestBody MemberUpdateReqDto reqDto, @MemberInfo MemberInfoParam memberInfoParam) {
        memberService.updateMemberInfo(reqDto, memberInfoParam);
        return SuccessResponse.ok(null);
    }
}
