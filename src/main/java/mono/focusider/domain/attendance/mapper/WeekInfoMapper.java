package mono.focusider.domain.attendance.mapper;

import mono.focusider.domain.attendance.dto.info.WeekInfoInfo;
import mono.focusider.domain.attendance.dto.res.WeekInfoListResDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeekInfoMapper {
    public WeekInfoListResDto toWeekInfoListResDto(List<WeekInfoInfo> weekInfoInfos) {
        return WeekInfoListResDto.of(weekInfoInfos);
    }
}
