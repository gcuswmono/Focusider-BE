package mono.focusider.domain.quiz.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.quiz.domain.Choice;
import mono.focusider.domain.quiz.domain.Quiz;
import mono.focusider.domain.quiz.dto.info.ChoiceSetInfo;
import mono.focusider.domain.quiz.repository.ChoiceRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChoiceHelper {
    private final ChoiceRepository choiceRepository;

    public List<Choice> getChoices(Long quizId, Long choiceId) {
        return choiceRepository.findByQuizId(quizId, choiceId);
    }

    public void createAndSaveChoice(Quiz quiz, ChoiceSetInfo choiceSetInfo) {
        choiceRepository.save(Choice.of(quiz, choiceSetInfo.content(), choiceSetInfo.isAnswer()));
    }
}
