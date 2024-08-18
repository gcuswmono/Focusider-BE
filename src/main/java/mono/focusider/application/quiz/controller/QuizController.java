package mono.focusider.application.quiz.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.quiz.dto.info.QuizSetInfo;
import mono.focusider.domain.quiz.dto.res.QuizInfo;
import mono.focusider.domain.quiz.service.QuizService;
import mono.focusider.global.annotation.MemberInfo;
import mono.focusider.global.aspect.member.MemberInfoParam;
import mono.focusider.global.domain.SuccessResponse;
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
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = QuizInfo.class))),
            @ApiResponse(responseCode = "500", description = "에러")
    })
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getQuiz(@MemberInfo MemberInfoParam memberInfoParam) {
        QuizInfo result = quizService.findQuizByLevel(memberInfoParam);
        return SuccessResponse.ok(result);
    }

    @PostMapping("/make")
    public ResponseEntity<SuccessResponse<?>> makeQuiz(@RequestBody QuizSetInfo quizSetInfo) {
        quizService.createAndSaveQuiz(quizSetInfo);
        return SuccessResponse.ok(null);
    }
}
