package mono.focusider.domain.attendance.batch;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.attendance.helper.WeekInfoHelper;
import mono.focusider.domain.attendance.validate.WeekInfoValidate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class WeekInfoBatch {
    private final WeekInfoValidate weekInfoValidate;
    private final WeekInfoHelper weekInfoHelper;

    @Scheduled(cron = "0 0 0 * * MON")  // 매주 월요일 0시에 실행
    @Transactional
    public void createWeekInfoForNewWeek() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int weekOfMonth = today.get(WeekFields.of(Locale.getDefault()).weekOfMonth());

        // 주차 타이틀 생성
        String title = String.format("%d년 %d월 %d주차", year, month, weekOfMonth);

        // 해당 주차가 이미 존재하는지 확인
        weekInfoValidate.validateWeekInfo(year, month, weekOfMonth);
        weekInfoHelper.createAndSaveWeekInfo(year, month, weekOfMonth, title);
    }
}
