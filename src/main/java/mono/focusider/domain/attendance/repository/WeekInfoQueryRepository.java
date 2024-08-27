package mono.focusider.domain.attendance.repository;

import mono.focusider.domain.attendance.domain.WeekInfo;

import java.time.LocalDate;
import java.util.Optional;

public interface WeekInfoQueryRepository {
    Optional<WeekInfo> findPreviousWeekInfo(LocalDate nowDate);
}
