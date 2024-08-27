package mono.focusider.domain.attendance.validate;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.attendance.repository.WeekInfoRepository;
import mono.focusider.global.error.exception.BusinessException;
import org.springframework.stereotype.Component;

import static mono.focusider.domain.attendance.error.WeekInfoError.WEEK_INFO_ERROR;

@Component
@RequiredArgsConstructor
public class WeekInfoValidate {
    private final WeekInfoRepository weekInfoRepository;

    public void validateWeekInfo(int year, int month, int weekOfMonth) {
        if(weekInfoRepository.existsWeekInfoByYearAndMonthAndWeek(year, month, weekOfMonth)) {
            throw new BusinessException(WEEK_INFO_ERROR);
        }
    }
}
