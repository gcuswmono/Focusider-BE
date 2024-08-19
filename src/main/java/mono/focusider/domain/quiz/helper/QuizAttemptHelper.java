package mono.focusider.domain.quiz.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.quiz.domain.Quiz;
import mono.focusider.domain.quiz.domain.QuizAttempt;
import mono.focusider.domain.quiz.repository.QuizAttemptRepository;
import mono.focusider.domain.quiz.type.QuizStatusType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizAttemptHelper {
    private final QuizAttemptRepository quizAttemptRepository;

    public void createAndSaveQuizAttempt(Quiz quiz, Member member, QuizStatusType quizStatusType) {
        quizAttemptRepository.save(QuizAttempt.of(quiz, member, quizStatusType));
    }
}
