package mono.focusider.domain.quiz.dto.req;

public record QuizCheckReqDto(
        Long quizId,
        Long choiceId,
        Long time
) {
}
