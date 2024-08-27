package mono.focusider.domain.article.repository;

import mono.focusider.domain.article.dto.res.ReadingDetailResDto;

public interface ReadingDetailResDtoQueryRepository {
    ReadingDetailResDto findReadingDetail(Long articleId);
}
