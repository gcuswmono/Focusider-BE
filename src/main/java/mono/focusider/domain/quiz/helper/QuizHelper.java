package mono.focusider.domain.quiz.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.quiz.domain.Commentary;
import mono.focusider.domain.quiz.domain.Quiz;
import mono.focusider.domain.quiz.dto.res.QuizGetResDto;
import mono.focusider.domain.quiz.repository.QuizRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizHelper {
    private final QuizRepository quizRepository;

    public Quiz createAndSaveQuiz(String title, String content, Integer level, Commentary commentary) {
        return quizRepository.save(Quiz.of(title, content, level, commentary));
    }

    public QuizGetResDto findQuizInfoByLevelAndMemberId(int level, Long memberId) {
        return quizRepository.findByLevelAndMemberId(level, memberId);
    }

    public QuizGetResDto findQuizInfoById(Long id) {
        return quizRepository.findByQuizId(id);
    }
}
