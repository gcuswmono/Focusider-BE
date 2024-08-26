package mono.focusider.domain.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.article.domain.Article;
import mono.focusider.domain.article.domain.Reading;
import mono.focusider.domain.article.dto.res.ReadingDetailResDto;
import mono.focusider.domain.article.dto.res.ReadingListDto;
import mono.focusider.domain.article.repository.ReadingRepository;
import mono.focusider.global.security.JwtUtil;
import mono.focusider.global.utils.cookie.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReadingService {
    private final ReadingRepository readingRepository;
    private final JwtUtil jwtUtil;

    private Long getMemberIdFromToken(HttpServletRequest request) {
        String accessToken = CookieUtils.getCookieValueWithName(request, "accessToken");
        if (accessToken == null) {
            throw new IllegalStateException("Access token not found in cookies.");
        }
        return jwtUtil.getMemberId(accessToken); // JWT에서 memberId 추출
    }

    public List<ReadingListDto> getReadingList(HttpServletRequest request) {
        Long memberId = getMemberIdFromToken(request);

        // 커스텀 쿼리를 호출하여 Reading 데이터 가져오기
        List<Reading> readings = readingRepository.findAllByMemberId(memberId);

        // 가져온 데이터를 ReadingListDto로 변환
        List<ReadingListDto> results = readings.stream()
                .map(read -> {
                    // Reading에서 연결된 Article 가져오기
                    Article article = read.getArticle();

                    // CategoryType의 설명을 가져옴
                    String categoryDescription = article.getCategoryType().getDesc();

                    return ReadingListDto.of(
                            read.getId(),
                            article.getTitle(),
                            categoryDescription,
                            Date.valueOf(read.getCreatedAt().toLocalDate()) // 읽은 날짜 설정
                    );
                })
                .collect(Collectors.toList());

        return results;
    }

    // ReadingDetailResDto를 반환하는 메서드 추가
    public ReadingDetailResDto getReadingDetail(Long readingId) {
        // Reading 정보를 가져옴
        Reading reading = readingRepository.findById(readingId)
                .orElseThrow(() -> new IllegalArgumentException("Reading not found for id: " + readingId));

        // 연결된 Article 정보를 가져옴
        Article article = reading.getArticle();

        // DTO로 변환하여 반환
        return ReadingDetailResDto.of(
                article.getTitle(),
                article.getContent());
    }
}
