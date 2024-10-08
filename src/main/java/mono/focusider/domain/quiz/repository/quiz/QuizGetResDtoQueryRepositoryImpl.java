package mono.focusider.domain.quiz.repository.quiz;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.quiz.dto.info.ChoiceInfo;
import mono.focusider.domain.quiz.dto.res.QuizGetResDto;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static mono.focusider.domain.quiz.domain.QChoice.choice;
import static mono.focusider.domain.quiz.domain.QQuiz.quiz;
import static mono.focusider.domain.quiz.domain.QQuizAttempt.quizAttempt;

@RequiredArgsConstructor
public class QuizGetResDtoQueryRepositoryImpl implements QuizGetResDtoQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public QuizGetResDto findByLevelAndMemberId(int level, long memberId) {
        return queryFactory
                .from(quiz)
                .leftJoin(quiz.choices, choice)
                .leftJoin(quiz.quizAttempts, quizAttempt)
                .where(quiz.level.between(level - 1, level + 1).and(quizAttempt.isNull().or(quizAttempt.member.id.ne(memberId))))
                .orderBy(Expressions.numberTemplate(Double.class, "RAND()").asc())
                .transform(groupBy(quiz.id).as(
                        Projections.constructor(QuizGetResDto.class,
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

    @Override
    public QuizGetResDto findByQuizId(long quizId) {
        return queryFactory
                .from(quiz)
                .leftJoin(quiz.choices, choice)
                .where(quiz.id.eq(quizId))
                .transform(groupBy(quiz.id).as(
                        Projections.constructor(QuizGetResDto.class,
                                quiz.id,
                                quiz.title,
                                quiz.content,
                                list(Projections.constructor(ChoiceInfo.class,
                                        choice.id,
                                        choice.content)
                                ))
                )).values().stream().findFirst().orElse(null);
    }

}
