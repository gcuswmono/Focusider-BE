package mono.focusider.domain.quiz.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.quiz.domain.Keyword;
import mono.focusider.domain.quiz.domain.Quiz;
import mono.focusider.domain.quiz.domain.QuizKeyword;
import mono.focusider.domain.quiz.repository.quiz.QuizKeywordRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizKeywordHelper {
    private final QuizKeywordRepository quizKeywordRepository;

    public void createAndSaveQuizKeyword(Quiz quiz, Keyword keyword) {
        quizKeywordRepository.save(QuizKeyword.of(quiz, keyword));
    }
}
