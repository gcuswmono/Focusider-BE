package mono.focusider.domain.quiz.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.quiz.dto.info.ChoiceInfo;
import mono.focusider.domain.quiz.dto.res.QuizInfo;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static mono.focusider.domain.quiz.domain.QChoice.choice;
import static mono.focusider.domain.quiz.domain.QQuiz.quiz;
import static mono.focusider.domain.quiz.domain.QQuizAttempt.quizAttempt;

@RequiredArgsConstructor
public class QuizQueryRepositoryImpl implements QuizQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public QuizInfo findByLevelAndMemberId(int level, long memberId) {
        return queryFactory
                .from(quiz)
                .leftJoin(quiz.choices, choice)
                .leftJoin(quiz.quizAttempts, quizAttempt)
                .where(quiz.level.eq(level).and(quizAttempt.isNull().or(quizAttempt.member.id.ne(memberId))))
                .orderBy(Expressions.numberTemplate(Double.class, "RAND()").asc())
                .transform(groupBy(quiz.id).as(
                        Projections.constructor(QuizInfo.class,
                                quiz.id,
                                quiz.title,
                                quiz.content,
                                list(Projections.constructor(ChoiceInfo.class,
                                        choice.id,
                                        choice.content
                                ))
                        )
                )).values().stream().findFirst().orElse(null);
    }
}
