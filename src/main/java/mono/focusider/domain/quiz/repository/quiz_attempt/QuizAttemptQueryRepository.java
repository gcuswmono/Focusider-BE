package mono.focusider.domain.quiz.repository.quiz_attempt;

import mono.focusider.domain.quiz.domain.QuizAttempt;

import java.time.LocalDate;
import java.util.Optional;

public interface QuizAttemptQueryRepository {
    Optional<QuizAttempt> findQuizAttemptAndQuizByIdAndUserChoiceId(Long id, Long userChoiceId);
    Long sumQuizSolveTime(Long memberId, LocalDate statDate);
}
