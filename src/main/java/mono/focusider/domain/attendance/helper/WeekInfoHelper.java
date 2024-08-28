package mono.focusider.domain.attendance.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.attendance.domain.WeekInfo;
import mono.focusider.domain.attendance.dto.info.WeekInfoInfo;
import mono.focusider.domain.attendance.repository.WeekInfoRepository;
import mono.focusider.global.error.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static mono.focusider.domain.attendance.error.WeekInfoError.WEEK_INFO_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class WeekInfoHelper {
    private final WeekInfoRepository weekInfoRepository;

    public void createAndSaveWeekInfo(int year, int month, int weekOfMonth, String title) {
        weekInfoRepository.save(WeekInfo.of(title, year, month, weekOfMonth));
    }

    public WeekInfo findWeekInfoWithYearAndMonthAndWeek(int year, int month, int weekOfMonth) {
        return weekInfoRepository.findByYearAndMonthAndWeek(year, month, weekOfMonth)
                .orElseThrow(() -> new EntityNotFoundException(WEEK_INFO_NOT_FOUND));
    }

    public List<WeekInfoInfo> findWeekInfos(Long memberId, LocalDate localDate) {
        return weekInfoRepository.findWeekInfoInfoWithDate(memberId, localDate);
    }

    public WeekInfo findPreviousWeekInfo(LocalDate nowDate) {
        return weekInfoRepository.findPreviousWeekInfo(nowDate)
                .orElseThrow(() -> new EntityNotFoundException(WEEK_INFO_NOT_FOUND));
    }
}
