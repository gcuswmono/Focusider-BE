package mono.focusider.domain.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.article.dto.info.ReadingInfo;
import mono.focusider.domain.article.dto.res.ReadingDetailResDto;
import mono.focusider.domain.article.dto.res.ReadingPageResDto;
import mono.focusider.domain.article.helper.ReadingHelper;
import mono.focusider.domain.article.mapper.ReadingMapper;
import mono.focusider.global.aspect.member.MemberInfoParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReadingService {
    private final ReadingHelper readingHelper;
    private final ReadingMapper readingMapper;

    public ReadingPageResDto getReadingList(MemberInfoParam memberInfoParam, Pageable pageable) {
        Long memberId = memberInfoParam.memberId();
        Page<ReadingInfo> readingInfos = readingHelper.findReadingInfos(memberId, pageable);
        return readingMapper.toReadingPageResDto(readingInfos);
    }

    // ReadingDetailResDto를 반환하는 메서드 추가
    public ReadingDetailResDto getReadingDetail(Long articleId) {
        return readingHelper.findReadingDetail(articleId);
    }
}
