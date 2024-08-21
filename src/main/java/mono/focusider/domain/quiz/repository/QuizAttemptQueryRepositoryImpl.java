package mono.focusider.domain.quiz.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.quiz.domain.QuizAttempt;

import java.util.Optional;

import static mono.focusider.domain.quiz.domain.QQuiz.quiz;
import static mono.focusider.domain.quiz.domain.QQuizAttempt.quizAttempt;

@RequiredArgsConstructor
public class QuizAttemptQueryRepositoryImpl implements QuizAttemptQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<QuizAttempt> findQuizAttemptAndQuizById(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(quizAttempt)
                .leftJoin(quizAttempt.quiz, quiz).fetchJoin()
                .leftJoin(quiz.choices).fetchJoin()
                .leftJoin(quiz.commentary).fetchJoin()
                .where(quizAttempt.id.eq(id))
                .fetchFirst());
    }
}
