package mono.focusider.domain.attendance.repository;

import mono.focusider.domain.attendance.domain.WeekInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeekInfoRepository extends JpaRepository<WeekInfo, Integer>,
        WeekInfoInfoQueryRepository, WeekInfoQueryRepository{
    boolean existsWeekInfoByYearAndMonthAndWeek(int year, int month, int week);
    Optional<WeekInfo> findByYearAndMonthAndWeek(int year, int month, int week);
}
