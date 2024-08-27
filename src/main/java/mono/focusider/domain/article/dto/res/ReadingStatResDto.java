package mono.focusider.domain.article.dto.res;

import lombok.AccessLevel;
import lombok.Builder;
import mono.focusider.domain.article.dto.info.ReadingStatInfo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record ReadingStatResDto(
        List<ReadingStatInfo> readingStatInfos
) {
    public static ReadingStatResDto of(List<ReadingStatInfo> readingStatInfos) {
        return ReadingStatResDto.builder()
                .readingStatInfos(readingStatInfos)
                .build();
    }
}
