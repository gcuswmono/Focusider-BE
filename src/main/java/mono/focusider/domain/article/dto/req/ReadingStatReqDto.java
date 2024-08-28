package mono.focusider.domain.article.dto.req;

import java.time.LocalDate;

public record ReadingStatReqDto(
        LocalDate statDate
) {
}
