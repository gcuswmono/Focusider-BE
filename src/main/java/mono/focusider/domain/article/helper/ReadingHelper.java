package mono.focusider.domain.article.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.dto.info.ReadingInfo;
import mono.focusider.domain.article.dto.info.ReadingStatInfo;
import mono.focusider.domain.article.dto.res.ReadingDetailResDto;
import mono.focusider.domain.article.repository.ReadingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<ReadingInfo> findReadingInfos(Long memberId, Pageable pageable) {
        return readingRepository.findReadingInfoPage(memberId, pageable);
    }

    public ReadingDetailResDto findReadingDetail(Long articleId) {
        return readingRepository.findReadingDetail(articleId);
    }
}
