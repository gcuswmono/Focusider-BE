package mono.focusider.domain.quiz.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.quiz.domain.Quiz;
import mono.focusider.domain.quiz.domain.QuizAttempt;
import mono.focusider.domain.quiz.dto.info.QuizInfo;
import mono.focusider.domain.quiz.repository.QuizAttemptRepository;
import mono.focusider.domain.quiz.type.QuizStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizAttemptHelper {
    private final QuizAttemptRepository quizAttemptRepository;

    public Page<QuizInfo> findWrongQuizInfo(Long memberId, Pageable pageable) {
        return quizAttemptRepository.getQuizAttemptWrong(pageable, memberId);
    }

    public void createAndSaveQuizAttempt(Quiz quiz, Member member, QuizStatusType quizStatusType) {
        quizAttemptRepository.save(QuizAttempt.of(quiz, member, quizStatusType));
    }
}
