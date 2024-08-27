package mono.focusider.domain.quiz.repository.keyword;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.quiz.domain.Keyword;
import mono.focusider.domain.quiz.type.KeywordType;

import java.util.List;

import static mono.focusider.domain.quiz.domain.QKeyword.keyword;

@RequiredArgsConstructor
public class KeywordTypeQueryRepositoryImpl implements KeywordTypeQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Keyword> findByKeywordTypes(List<KeywordType> keywordTypes) {
        return queryFactory
                .selectFrom(keyword)
                .where(keyword.keywordType.in(keywordTypes))
                .fetch();
    }
}
