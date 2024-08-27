package mono.focusider.application.attendance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.attendance.dto.res.WeekInfoListResDto;
import mono.focusider.domain.attendance.service.WeekInfoService;
import mono.focusider.global.annotation.MemberInfo;
import mono.focusider.global.aspect.member.MemberInfoParam;
import mono.focusider.global.domain.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/week-info")
@Tag(name = "WeekInfo", description = "WeekInfo API")
@RequiredArgsConstructor
public class WeekInfoController {
    private final WeekInfoService weekInfoService;

    @Operation(summary = "통계 날짜", description = "통계 날짜 리스트", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = WeekInfoListResDto.class))),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> findWeekInfos(@MemberInfo MemberInfoParam memberInfoParam, @RequestParam LocalDate date) {
        WeekInfoListResDto result = weekInfoService.findWeekInfo(memberInfoParam, date);
        return SuccessResponse.ok(result);
    }
}
