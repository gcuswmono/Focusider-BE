package mono.focusider.domain.article.mapper;

import mono.focusider.domain.article.dto.info.ReadingInfo;
import mono.focusider.domain.article.dto.info.ReadingStatInfo;
import mono.focusider.domain.article.dto.res.ReadingPageResDto;
import mono.focusider.domain.article.dto.res.ReadingStatResDto;
import mono.focusider.global.domain.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReadingMapper {
    public ReadingStatResDto toReadingStatDto(List<ReadingStatInfo> readingStatInfos) {
        return ReadingStatResDto.of(readingStatInfos);
    }

    public ReadingPageResDto toReadingPageResDto(Page<ReadingInfo> readingInfos) {
        PageInfo pageInfo = PageInfo.of(readingInfos);
        return ReadingPageResDto.of(readingInfos.getContent(), pageInfo);
    }
}
