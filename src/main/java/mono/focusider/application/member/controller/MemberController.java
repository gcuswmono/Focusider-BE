package mono.focusider.application.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.member.dto.req.MemberCategorySaveReqDto;
import mono.focusider.domain.member.dto.req.MemberInfoReqDto;
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

    @Operation(summary = "내 정보 확인", description = "마이 페이지 필요 정보", responses = {
            @ApiResponse(responseCode = "200",  content = @Content(schema = @Schema(implementation = MemberInfoReqDto.class))),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getMemberInfo(@MemberInfo MemberInfoParam memberInfoParam) {
        MemberInfoReqDto result = memberService.findMemberInfo(memberInfoParam);
        return SuccessResponse.ok(result);
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴", responses = {
            @ApiResponse(responseCode = "200",  description = "삭제 완료"),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @DeleteMapping
    public ResponseEntity<SuccessResponse<?>> deleteMember(@MemberInfo MemberInfoParam memberInfoParam) {
        memberService.deleteMember(memberInfoParam);
        return SuccessResponse.ok(null);
    }
}
