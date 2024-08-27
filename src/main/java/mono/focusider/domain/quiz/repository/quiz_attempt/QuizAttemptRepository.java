package mono.focusider.domain.quiz.repository.quiz_attempt;

import mono.focusider.domain.quiz.domain.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long>,
        QuizAttemptWrongQueryRepository, QuizAttemptQueryRepository {
}
