package mono.focusider.domain.quiz.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.quiz.domain.Commentary;
import mono.focusider.domain.quiz.domain.Quiz;
import mono.focusider.domain.quiz.dto.res.QuizInfo;
import mono.focusider.domain.quiz.repository.QuizRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizHelper {
    private final QuizRepository quizRepository;

    public Quiz createAndSaveQuiz(String title, String content, Integer level, Commentary commentary) {
        return quizRepository.save(Quiz.of(title, content, level, commentary));
    }

    public QuizInfo findQuizInfoByLevelAndMemberId(int level, Long memberId) {
        return quizRepository.findByLevelAndMemberId(level, memberId);
    }
}
