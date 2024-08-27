package mono.focusider.domain.article.dto.info;

import java.util.List;

public record ReadingStatInfo(
        String title,
        List<ReadingStatDetailInfo> readingStatDetailInfos,
        String summary
) {
}
