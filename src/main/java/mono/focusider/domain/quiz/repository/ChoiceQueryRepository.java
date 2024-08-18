package mono.focusider.domain.quiz.repository;

import mono.focusider.domain.quiz.domain.Choice;

import java.util.List;

public interface ChoiceQueryRepository {
    List<Choice> findByQuizId(Long quizId, Long choiceId);
}
