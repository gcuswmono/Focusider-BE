package mono.focusider.domain.article.dto.res;

import lombok.AccessLevel;
import lombok.Builder;
import mono.focusider.domain.article.dto.info.ReadingStatDetailInfo;

import java.time.LocalDateTime;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record ReadingStatResDto(
        String title,
        String comment,
        LocalDateTime startDate,
        List<ReadingStatDetailInfo> readingStatInfos
) {

}
