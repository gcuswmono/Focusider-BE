package mono.focusider.domain.quiz.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.quiz.domain.Quiz;
import mono.focusider.domain.quiz.domain.QuizAttempt;
import mono.focusider.domain.quiz.dto.info.QuizInfo;
import mono.focusider.domain.quiz.repository.QuizAttemptRepository;
import mono.focusider.domain.quiz.type.QuizStatusType;
import mono.focusider.global.error.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static mono.focusider.domain.quiz.error.QuizErrorCode.QUIZ_REPORT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class QuizAttemptHelper {
    private final QuizAttemptRepository quizAttemptRepository;

    public Page<QuizInfo> findWrongQuizInfo(Long memberId, Pageable pageable) {
        return quizAttemptRepository.getQuizAttemptWrong(pageable, memberId);
    }

    public void createAndSaveQuizAttempt(Quiz quiz, Member member, QuizStatusType quizStatusType, Long time) {
        quizAttemptRepository.save(QuizAttempt.of(quiz, member, quizStatusType, time));
    }

    public QuizAttempt findQuizAttemptAndQuizById(Long id) {
        return quizAttemptRepository.findQuizAttemptAndQuizById(id)
                .orElseThrow(() -> new EntityNotFoundException(QUIZ_REPORT_NOT_FOUND));
    }
}
