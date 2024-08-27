package mono.focusider.domain.article.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.dto.info.ReadingInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static mono.focusider.domain.article.domain.QArticle.article;
import static mono.focusider.domain.article.domain.QReading.reading;

@RequiredArgsConstructor
public class ReadingPageResDtoQueryRepositoryImpl implements ReadingPageResDtoQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReadingInfo> findReadingInfoPage(Long memberId, Pageable pageable) {
        List<ReadingInfo> content = queryFactory
                .select(Projections.constructor(ReadingInfo.class,
                        article.id,
                        article.title,
                        article.categoryType,
                        reading.createdAt
                ))
                .from(reading)
                .leftJoin(reading.article, article)
                .where(reading.member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .selectDistinct(reading.id)
                .from(reading)
                .where(reading.member.id.eq(memberId));
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }
}
