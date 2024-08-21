package mono.focusider.domain.quiz.repository.quiz;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.quiz.domain.Quiz;

import java.util.Optional;

import static mono.focusider.domain.quiz.domain.QChoice.choice;
import static mono.focusider.domain.quiz.domain.QQuiz.quiz;

@RequiredArgsConstructor
public class QuizQueryRepositoryImpl implements QuizQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Quiz> findAllDataByQuizId(Long quizId, Long userChoiceId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(quiz)
                .leftJoin(quiz.choices, choice).fetchJoin()
                .leftJoin(quiz.commentary).fetchJoin()
                .where(quiz.id.eq(quizId).and(choice.isAnswer.isTrue().or(choice.id.eq(userChoiceId))))
                .fetchOne());
    }
}
