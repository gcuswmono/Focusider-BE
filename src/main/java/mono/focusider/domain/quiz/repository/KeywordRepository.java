package mono.focusider.domain.quiz.repository;

import mono.focusider.domain.quiz.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Integer> {
}
