package mono.focusider.domain.attendance.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.attendance.dto.info.WeekInfoInfo;

import java.time.LocalDate;
import java.util.List;

import static mono.focusider.domain.article.domain.QReading.reading;
import static mono.focusider.domain.attendance.domain.QWeekInfo.weekInfo;

@RequiredArgsConstructor
public class WeekInfoInfoQueryRepositoryImpl implements WeekInfoInfoQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<WeekInfoInfo> findWeekInfoInfoWithDate(Long memberId, LocalDate localDate) {
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        return queryFactory
                .selectDistinct(Projections.constructor(WeekInfoInfo.class,
                        weekInfo.id,
                        weekInfo.title
                        )
                )
                .from(weekInfo)
                .leftJoin(weekInfo.readings, reading)
                .where(reading.member.id.eq(memberId).and(reading.isNotNull())
                        .and(weekInfo.year.eq(year))
                        .and(weekInfo.month.eq(month)))
                .fetch();
    }
}
