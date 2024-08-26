package mono.focusider.domain.article.dto.res;

import lombok.Builder;

import java.sql.Date;

import lombok.AccessLevel;

@Builder(access = AccessLevel.PRIVATE)
public record ReadingListDto(
                Long id,
                String title,
                String category,
                Date readingDate) {
        public static ReadingListDto of(Long id, String title, String category, Date readingDate) {
                return ReadingListDto
                                .builder()
                                .id(id)
                                .title(title)
                                .category(category)
                                .readingDate(readingDate)
                                .build();
        }

}
