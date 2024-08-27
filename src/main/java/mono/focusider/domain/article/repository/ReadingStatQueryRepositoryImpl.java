package mono.focusider.domain.article.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.dto.info.ReadingStatInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static mono.focusider.domain.article.domain.QArticle.article;
import static mono.focusider.domain.article.domain.QReading.reading;

@RequiredArgsConstructor
public class ReadingStatQueryRepositoryImpl implements ReadingStatQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReadingStatInfo> findReadingStatInfo(Long memberId, LocalDate statDate) {
        YearMonth yearMonth = YearMonth.from(statDate);
        LocalDateTime startDateTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDateTime = yearMonth.atEndOfMonth().atTime(23, 59, 59, 999999999);
        return queryFactory
                .select(Projections.constructor(ReadingStatInfo.class,
                        reading.id,
                        article.title,
                        article.categoryType,
                        reading.readingTime,
                        reading.understating,
                        reading.createdAt
                        )
                )
                .from(reading)
                .leftJoin(reading.article, article)
                .where(reading.member.id.eq(memberId).and(reading.createdAt.between(startDateTime, endDateTime)))
                .fetch();
    }
}
