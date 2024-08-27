package mono.focusider.domain.article.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.dto.info.ReadingInfo;
import mono.focusider.domain.article.dto.res.ReadingDetailResDto;
import mono.focusider.domain.article.dto.res.ReadingStatResDto;
import mono.focusider.domain.article.repository.ReadingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadingHelper {
    private final ReadingRepository readingRepository;

    public ReadingStatResDto findReadingStatInfo(Long memberId, Long weekInfoId) {
        return readingRepository.findReadingStatInfo(memberId, weekInfoId);
    }

    public Page<ReadingInfo> findReadingInfos(Long memberId, Pageable pageable) {
        return readingRepository.findReadingInfoPage(memberId, pageable);
    }

    public ReadingDetailResDto findReadingDetail(Long articleId) {
        return readingRepository.findReadingDetail(articleId);
    }
}
