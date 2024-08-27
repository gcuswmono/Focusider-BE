package mono.focusider.domain.article.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.dto.info.ReadingStatDetailInfo;
import mono.focusider.domain.article.dto.info.ReadingStatInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static mono.focusider.domain.article.domain.QReading.reading;

@RequiredArgsConstructor
public class ReadingStatQueryRepositoryImpl implements ReadingStatQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReadingStatInfo> findReadingStatInfo(Long memberId, LocalDate statDate) {
        List<ReadingStatDetailInfo> allReadings = queryFactory
                .select(Projections.constructor(ReadingStatDetailInfo.class,
                        reading.createdAt,
                        reading.readingTime,
                        reading.understating
                ))
                .from(reading)
                .where(
                        reading.member.id.eq(memberId)
                                .and(reading.createdAt.year().eq(statDate.getYear()))
                                .and(reading.createdAt.month().eq(statDate.getMonthValue()))
                )
                .fetch();
        Map<Integer, List<ReadingStatDetailInfo>> readingsByWeek = allReadings.stream()
                .collect(Collectors.groupingBy(readingDetail -> getWeekOfMonth(readingDetail.readingDate())));
        return readingsByWeek.entrySet().stream()
                .map(entry -> {
                    int week = entry.getKey();
                    List<ReadingStatDetailInfo> details = entry.getValue();

                    String title = week + "주차";
                    String summary = "Summary for " + title;

                    return new ReadingStatInfo(title, details, summary);
                })
                .collect(Collectors.toList());
    }

    private int getWeekOfMonth(LocalDateTime dateTime) {
        int dayOfMonth = dateTime.getDayOfMonth();
        return (dayOfMonth - 1) / 7 + 1;  // 1일~7일: 1주차, 8일~14일: 2주차, ...
    }
}
