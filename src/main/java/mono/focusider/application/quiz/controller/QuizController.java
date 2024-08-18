package mono.focusider.application.quiz.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.quiz.dto.info.QuizSetInfo;
import mono.focusider.domain.quiz.service.QuizService;
import mono.focusider.global.domain.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/quiz")
@Tag(name = "Quiz", description = "Quiz API")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping("/make")
    public ResponseEntity<SuccessResponse<?>> makeQuiz(@RequestBody QuizSetInfo quizSetInfo) {
        quizService.createAndSaveQuiz(quizSetInfo);
        return SuccessResponse.ok(null);
    }
}
