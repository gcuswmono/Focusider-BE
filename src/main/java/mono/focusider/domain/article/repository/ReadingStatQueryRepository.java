package mono.focusider.domain.article.repository;

import mono.focusider.domain.article.dto.res.ReadingStatResDto;

public interface ReadingStatQueryRepository {
    ReadingStatResDto findReadingStatInfo(Long memberId, Long weekInfoId);
}
