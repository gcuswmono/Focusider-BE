package mono.focusider.domain.quiz.repository.quiz;

import mono.focusider.domain.quiz.domain.Quiz;

import java.util.Optional;

public interface QuizQueryRepository {
    Optional<Quiz> findAllDataByQuizId(Long quizId, Long userChoiceId);
}
