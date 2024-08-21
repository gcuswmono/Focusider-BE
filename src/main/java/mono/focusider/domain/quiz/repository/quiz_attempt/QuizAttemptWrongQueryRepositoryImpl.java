package mono.focusider.domain.quiz.repository.quiz_attempt;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.quiz.dto.info.KeywordInfo;
import mono.focusider.domain.quiz.dto.info.QuizInfo;
import mono.focusider.domain.quiz.type.QuizStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static mono.focusider.domain.quiz.domain.QKeyword.keyword;
import static mono.focusider.domain.quiz.domain.QQuiz.quiz;
import static mono.focusider.domain.quiz.domain.QQuizAttempt.quizAttempt;
import static mono.focusider.domain.quiz.domain.QQuizKeyword.quizKeyword;

@RequiredArgsConstructor
public class QuizAttemptWrongQueryRepositoryImpl implements QuizAttemptWrongQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<QuizInfo> getQuizAttemptWrong(Pageable pageable, Long memberId) {
        List<QuizInfo> contents = queryFactory
                .from(quizAttempt)
                .leftJoin(quizAttempt.quiz, quiz)
                .leftJoin(quiz.quizKeywords, quizKeyword)
                .leftJoin(quizKeyword.keyword, keyword)
                .where(quizAttempt.member.id.eq(memberId))
                .transform(groupBy(quizAttempt.id).list(
                        Projections.constructor(QuizInfo.class,
                                quiz.id,
                                quizAttempt.id,
                                quiz.level,
                                list(Projections.constructor(KeywordInfo.class,
                                        keyword.keywordType // 필요한 필드들을 추가하세요
                                )),
                                quizAttempt.createdAt,
                                new CaseBuilder()
                                        .when(quizAttempt.quizStatusType.eq(QuizStatusType.QUIZ_CORRECT)
                                                .and(quizAttempt.createdAt.ne(quizAttempt.lastModifiedAt)))
                                        .then(quizAttempt.lastModifiedAt)
                                        .otherwise((LocalDateTime) null)
                        )
                ));

        JPAQuery<Long> countQuery = queryFactory
                .select(quizAttempt.id.countDistinct())
                .from(quizAttempt)
                .where(quizAttempt.member.id.eq(memberId));

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchCount);
    }
}
