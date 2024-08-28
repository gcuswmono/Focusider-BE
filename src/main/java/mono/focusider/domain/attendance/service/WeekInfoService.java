package mono.focusider.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.attendance.dto.info.WeekInfoInfo;
import mono.focusider.domain.attendance.dto.res.WeekInfoListResDto;
import mono.focusider.domain.attendance.helper.WeekInfoHelper;
import mono.focusider.domain.attendance.mapper.WeekInfoMapper;
import mono.focusider.global.aspect.member.MemberInfoParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeekInfoService {
    private final WeekInfoHelper weekInfoHelper;
    private final WeekInfoMapper weekInfoMapper;

    public WeekInfoListResDto findWeekInfo(MemberInfoParam memberInfoParam, LocalDate localDate) {
        List<WeekInfoInfo> result = weekInfoHelper.findWeekInfos(memberInfoParam.memberId(), localDate);
        return weekInfoMapper.toWeekInfoListResDto(result);
    }
}
