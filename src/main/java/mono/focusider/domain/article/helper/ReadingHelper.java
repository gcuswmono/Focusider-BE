package mono.focusider.domain.article.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.dto.info.ReadingStatInfo;
import mono.focusider.domain.article.repository.ReadingRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReadingHelper {
    private final ReadingRepository readingRepository;

    public List<ReadingStatInfo> findReadingStatInfo(Long memberId, LocalDate statDate) {
        return readingRepository.findReadingStatInfo(memberId, statDate);
    }
}
