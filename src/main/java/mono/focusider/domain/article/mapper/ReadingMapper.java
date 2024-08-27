package mono.focusider.domain.article.mapper;

import mono.focusider.domain.article.dto.info.ReadingStatInfo;
import mono.focusider.domain.article.dto.res.ReadingStatResDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReadingMapper {
    public ReadingStatResDto toReadingStatDto(List<ReadingStatInfo> readingStatInfos) {
        return ReadingStatResDto.of(readingStatInfos);
    }
}
