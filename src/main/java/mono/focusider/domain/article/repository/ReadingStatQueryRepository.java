package mono.focusider.domain.article.repository;

import mono.focusider.domain.article.dto.info.ReadingStatInfo;

import java.time.LocalDate;
import java.util.List;

public interface ReadingStatQueryRepository {
    List<ReadingStatInfo> findReadingStatInfo(Long memberId, LocalDate statDate);
}
