package mono.focusider.domain.article.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.dto.res.ArticleDetailResDto;
import mono.focusider.domain.category.type.CategoryType;
import mono.focusider.domain.member.domain.Member;

import java.util.List;

import static mono.focusider.domain.article.domain.QArticle.article;
import static mono.focusider.domain.article.domain.QReading.reading;
import static mono.focusider.domain.category.domain.QCategory.category;

@RequiredArgsConstructor
public class ArticleDetailDtoQueryRepositoryImpl implements ArticleDetailDtoQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public ArticleDetailResDto findArticleDetailDto(Member member, List<CategoryType> categoryTypes) {
        return queryFactory
                .select(Projections.constructor(ArticleDetailResDto.class,
                        article.article_id,
                        article.title,
                        article.content,
                        article.categoryType))
                .from(article)
                .leftJoin(article.reading, reading)
                .where(article.categoryType.in(categoryTypes).and(article.level.eq(member.getLevel()))
                        .and(reading.isNull().or(reading.member.id.ne(member.getId()))))
                .orderBy(Expressions.numberTemplate(Double.class, "RAND()").asc())
                .fetchOne();
    }
}
