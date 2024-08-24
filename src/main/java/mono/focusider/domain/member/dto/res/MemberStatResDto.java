package mono.focusider.domain.member.dto.res;

import lombok.AccessLevel;
import lombok.Builder;
import mono.focusider.domain.article.dto.info.ReadingStatInfo;

@Builder(access = AccessLevel.PRIVATE)
public record MemberStatResDto(
        Long totalSolveTime,
        ReadingStatInfo readingStatInfo
) {
    public static MemberStatResDto of(Long totalSolveTime, ReadingStatInfo readingStatInfo) {
        return MemberStatResDto.builder()
                .totalSolveTime(totalSolveTime)
                .readingStatInfo(readingStatInfo)
                .build();
    }
}
