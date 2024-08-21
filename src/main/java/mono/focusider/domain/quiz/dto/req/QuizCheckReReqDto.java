package mono.focusider.domain.quiz.dto.req;

public record QuizCheckReReqDto(
        Long quizAttemptId,
        Long choiceId,
        Long time
) {
}
