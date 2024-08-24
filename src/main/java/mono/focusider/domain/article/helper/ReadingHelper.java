package mono.focusider.domain.article.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.dto.info.ReadingStatInfo;
import mono.focusider.domain.article.repository.ReadingRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ReadingHelper {
    private final ReadingRepository readingRepository;

    public ReadingStatInfo sumReadingTime(Long memberId, LocalDate statDate) {
        return readingRepository.sumReadingTime(memberId, statDate);
    }
}
