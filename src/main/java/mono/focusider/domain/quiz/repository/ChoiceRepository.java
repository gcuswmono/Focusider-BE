package mono.focusider.domain.quiz.repository;

import mono.focusider.domain.quiz.domain.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepository extends JpaRepository<Choice, Long>, ChoiceQueryRepository {

}
