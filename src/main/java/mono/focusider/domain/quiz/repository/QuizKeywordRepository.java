package mono.focusider.domain.quiz.repository;

import mono.focusider.domain.quiz.domain.QuizKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizKeywordRepository extends JpaRepository<QuizKeyword, Long> {
}
