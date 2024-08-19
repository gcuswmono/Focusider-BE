package mono.focusider.domain.quiz.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.quiz.domain.Choice;

import java.util.List;

import static mono.focusider.domain.quiz.domain.QChoice.choice;
import static mono.focusider.domain.quiz.domain.QQuiz.quiz;

@RequiredArgsConstructor
public class ChoiceQueryRepositoryImpl implements ChoiceQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Choice> findByQuizId(Long quizId, Long choiceId) {
        return queryFactory
                .selectFrom(choice)
                .leftJoin(choice.quiz, quiz).fetchJoin()
                .leftJoin(quiz.commentary).fetchJoin()
                .where(choice.quiz.id.eq(quizId).and(choice.isAnswer.isTrue().or(choice.id.eq(choiceId))))
                .orderBy(choice.isAnswer.desc())
                .fetch();
    }
}
