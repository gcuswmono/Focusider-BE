package mono.focusider.application.quiz.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.quiz.dto.info.QuizSetInfo;
import mono.focusider.domain.quiz.dto.req.QuizCheckReReqDto;
import mono.focusider.domain.quiz.dto.req.QuizCheckReqDto;
import mono.focusider.domain.quiz.dto.res.QuizCheckResDto;
import mono.focusider.domain.quiz.dto.res.QuizGetResDto;
import mono.focusider.domain.quiz.dto.res.QuizWrongResDto;
import mono.focusider.domain.quiz.service.QuizService;
import mono.focusider.global.annotation.MemberInfo;
import mono.focusider.global.aspect.member.MemberInfoParam;
import mono.focusider.global.domain.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/quiz")
@Tag(name = "Quiz", description = "Quiz API")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @Operation(summary = "레벨에 맞는 퀴즈 출력", description = "퀴즈 출력", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = QuizGetResDto.class))),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getQuiz(@MemberInfo MemberInfoParam memberInfoParam) {
        QuizGetResDto result = quizService.findQuizByLevel(memberInfoParam);
        return SuccessResponse.ok(result);
    }

    @Operation(summary = "퀴즈 정답 선택", description = "퀴즈 정답 선택", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = QuizCheckResDto.class))),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> checkCorrect(@RequestBody QuizCheckReqDto reqDto,
            @MemberInfo MemberInfoParam memberInfoParam) {
        QuizCheckResDto result = quizService.checkAndSaveQuiz(reqDto, memberInfoParam);
        return SuccessResponse.ok(result);
    }

    @Operation(summary = "오답 퀴즈 정답 체크", description = "오답 퀴즈 정답 체크", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = QuizCheckResDto.class))),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @PatchMapping
    public ResponseEntity<SuccessResponse<?>> checkCorrectRe(@RequestBody QuizCheckReReqDto reqDto) {
        QuizCheckResDto result = quizService.checkAndUpdateQuiz(reqDto);
        return SuccessResponse.ok(result);
    }

    @Operation(summary = "퀴즈 오답 리스트", description = "퀴즈 오답 리스트", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = QuizWrongResDto.class))),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @GetMapping("/wrong/list")
    public ResponseEntity<SuccessResponse<?>> getWrongQuizList(@MemberInfo MemberInfoParam memberInfoParam,
            Pageable pageable) {
        QuizWrongResDto result = quizService.findWrongQuizList(memberInfoParam, pageable);
        return SuccessResponse.ok(result);
    }

    @Operation(summary = "특정 퀴즈 상세", description = "특정 퀴즈 상세", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = QuizGetResDto.class))),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @GetMapping("/{quizId}")
    public ResponseEntity<SuccessResponse<?>> getWrongQuizDetail(@PathVariable Long quizId) {
        QuizGetResDto result = quizService.findQuizById(quizId);
        return SuccessResponse.ok(result);
    }

    @PostMapping("/make")
    public ResponseEntity<SuccessResponse<?>> makeQuiz(@RequestBody QuizSetInfo quizSetInfo) {
        quizService.createAndSaveQuiz(quizSetInfo);
        return SuccessResponse.ok(null);
    }
}
