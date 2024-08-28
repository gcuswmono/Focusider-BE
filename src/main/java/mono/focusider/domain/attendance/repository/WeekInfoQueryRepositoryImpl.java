package mono.focusider.domain.attendance.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.attendance.domain.WeekInfo;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;

import static mono.focusider.domain.attendance.domain.QWeekInfo.weekInfo;

@RequiredArgsConstructor
public class WeekInfoQueryRepositoryImpl implements WeekInfoQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<WeekInfo> findPreviousWeekInfo(LocalDate nowDate) {
        // 전주의 연도, 월, 주차 계산
        LocalDate lastWeekDate = nowDate.minusWeeks(1);
        int year = lastWeekDate.getYear();
        int month = lastWeekDate.getMonthValue();
        int week = lastWeekDate.get(WeekFields.of(Locale.getDefault()).weekOfMonth());

        return Optional.ofNullable(
                queryFactory.selectFrom(weekInfo)
                        .where(weekInfo.year.eq(year)
                                .and(weekInfo.month.eq(month))
                                .and(weekInfo.week.eq(week)))
                        .fetchOne()
        );
    }
}
