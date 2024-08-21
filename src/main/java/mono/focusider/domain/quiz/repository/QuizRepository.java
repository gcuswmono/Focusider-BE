package mono.focusider.domain.quiz.repository;

import mono.focusider.domain.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long>, QuizGetResDtoQueryRepository {
}
