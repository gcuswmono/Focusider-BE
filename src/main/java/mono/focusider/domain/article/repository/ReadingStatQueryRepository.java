package mono.focusider.domain.article.repository;

import mono.focusider.domain.article.dto.info.ReadingStatInfo;

import java.time.LocalDate;

public interface ReadingStatQueryRepository {
    ReadingStatInfo sumReadingTime(Long memberId, LocalDate statDate);
}
