package mono.focusider.domain.article.dto.res;

import lombok.AccessLevel;
import lombok.Builder;
import mono.focusider.domain.article.dto.info.ReadingInfo;
import mono.focusider.global.domain.PageInfo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record ReadingPageResDto(
        List<ReadingInfo> readingInfos,
        PageInfo pageInfo
) {
        public static ReadingPageResDto of(List<ReadingInfo> readingInfos, PageInfo pageInfo) {
                return ReadingPageResDto
                        .builder()
                        .readingInfos(readingInfos)
                        .pageInfo(pageInfo)
                        .build();
        }
}
