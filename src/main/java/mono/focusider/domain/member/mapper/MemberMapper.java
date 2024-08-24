package mono.focusider.domain.member.mapper;

import mono.focusider.domain.article.dto.info.ReadingStatInfo;
import mono.focusider.domain.member.dto.res.MemberStatResDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public MemberStatResDto toMemberStatResDto(Long totalSolveTime, ReadingStatInfo readingStatInfo) {
        return MemberStatResDto.of(totalSolveTime, readingStatInfo);
    }
}

