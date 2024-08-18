package mono.focusider.domain.quiz.repository;

import mono.focusider.domain.quiz.domain.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {
}
