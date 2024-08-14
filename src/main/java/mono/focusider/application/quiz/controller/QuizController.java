package mono.focusider.application.quiz.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/quiz")
@Tag(name = "Quiz", description = "Quiz API")
@RequiredArgsConstructor
public class QuizController {
}
