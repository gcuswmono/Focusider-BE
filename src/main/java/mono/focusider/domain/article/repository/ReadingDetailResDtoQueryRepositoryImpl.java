package mono.focusider.domain.article.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.dto.res.ReadingDetailResDto;

import static mono.focusider.domain.article.domain.QArticle.article;

@RequiredArgsConstructor
public class ReadingDetailResDtoQueryRepositoryImpl implements ReadingDetailResDtoQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public ReadingDetailResDto findReadingDetail(Long articleId) {
        return queryFactory
                .select(Projections.constructor(ReadingDetailResDto.class,
                        article.title,
                        article.content))
                .from(article)
                .where(article.id.eq(articleId))
                .fetchOne();
    }
}
