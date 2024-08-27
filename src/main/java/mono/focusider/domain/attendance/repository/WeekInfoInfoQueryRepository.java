package mono.focusider.domain.attendance.repository;

import mono.focusider.domain.attendance.dto.info.WeekInfoInfo;

import java.time.LocalDate;
import java.util.List;

public interface WeekInfoInfoQueryRepository {
    List<WeekInfoInfo> findWeekInfoInfoWithDate(Long memberId, LocalDate localDate);
}
