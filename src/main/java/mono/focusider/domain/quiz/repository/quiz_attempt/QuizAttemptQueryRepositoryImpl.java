package mono.focusider.domain.quiz.repository.quiz_attempt;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.quiz.domain.QuizAttempt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Optional;

import static mono.focusider.domain.quiz.domain.QChoice.choice;
import static mono.focusider.domain.quiz.domain.QQuiz.quiz;
import static mono.focusider.domain.quiz.domain.QQuizAttempt.quizAttempt;

@RequiredArgsConstructor
public class QuizAttemptQueryRepositoryImpl implements QuizAttemptQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<QuizAttempt> findQuizAttemptAndQuizByIdAndUserChoiceId(Long id, Long userChoiceId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(quizAttempt)
                .leftJoin(quizAttempt.quiz, quiz).fetchJoin()
                .leftJoin(quiz.choices, choice).fetchJoin()
                .leftJoin(quiz.commentary).fetchJoin()
                .where(quizAttempt.id.eq(id).and(choice.isAnswer.isTrue().or(choice.id.eq(userChoiceId))))
                .fetchFirst());
    }

    @Override
    public Long sumQuizSolveTime(Long memberId, LocalDate statDate) {
        YearMonth yearMonth = YearMonth.from(statDate);
        LocalDateTime startDateTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDateTime = yearMonth.atEndOfMonth().atTime(23, 59, 59, 999999999);
        return queryFactory
                .select(quizAttempt.time.sum())
                .from(quizAttempt)
                .where(quizAttempt.member.id.eq(memberId).and(quizAttempt.createdAt.between(startDateTime, endDateTime)))
                .fetchOne();
    }
}
