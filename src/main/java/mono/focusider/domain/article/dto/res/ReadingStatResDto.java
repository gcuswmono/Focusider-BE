package mono.focusider.domain.article.dto.res;

import lombok.AccessLevel;
import lombok.Builder;
import mono.focusider.domain.article.dto.info.ReadingStatDetailInfo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record ReadingStatResDto(
        String title,
        List<ReadingStatDetailInfo> readingStatInfos
        //String comment
) {

}
