package mono.focusider.domain.quiz.repository;

import mono.focusider.domain.quiz.domain.QuizAttempt;

import java.util.Optional;

public interface QuizAttemptQueryRepository {
    Optional<QuizAttempt> findQuizAttemptAndQuizById(Long id);
}
