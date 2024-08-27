package mono.focusider.domain.article.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.dto.info.ReadingStatDetailInfo;
import mono.focusider.domain.article.dto.res.ReadingStatResDto;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static mono.focusider.domain.article.domain.QReading.reading;
import static mono.focusider.domain.attendance.domain.QWeekInfo.weekInfo;

@RequiredArgsConstructor
public class ReadingStatQueryRepositoryImpl implements ReadingStatQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public ReadingStatResDto findReadingStatInfo(Long memberId, Long weekInfoId) {
        return queryFactory
                .from(reading)
                .leftJoin(reading.weekInfo, weekInfo)
                .where(reading.member.id.eq(memberId).and(weekInfo.id.eq(weekInfoId)))
                .transform(
                        groupBy(weekInfo.id).as(Projections.constructor(
                                ReadingStatResDto.class,
                                weekInfo.title,
                                list(Projections.constructor(ReadingStatDetailInfo.class,
                                        reading.createdAt,
                                        reading.readingTime,
                                        reading.understating))
                        ))
                ).values().stream().findFirst().orElse(null);
    }
}
