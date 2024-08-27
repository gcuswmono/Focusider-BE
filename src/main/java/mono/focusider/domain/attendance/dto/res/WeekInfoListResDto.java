package mono.focusider.domain.attendance.dto.res;

import lombok.AccessLevel;
import lombok.Builder;
import mono.focusider.domain.attendance.dto.info.WeekInfoInfo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record WeekInfoListResDto(
        List<WeekInfoInfo> weekInfoInfos
) {
    public static WeekInfoListResDto of(List<WeekInfoInfo> weekInfoInfos) {
        return WeekInfoListResDto.builder().weekInfoInfos(weekInfoInfos).build();
    }
}
