package mono.focusider.domain.article.repository;

import mono.focusider.domain.article.dto.info.ReadingInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadingPageResDtoQueryRepository {
    Page<ReadingInfo> findReadingInfoPage(Long memberId, Pageable pageable);
}
