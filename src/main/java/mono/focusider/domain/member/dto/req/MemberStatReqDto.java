package mono.focusider.domain.member.dto.req;

import java.time.LocalDate;

public record MemberStatReqDto(
        LocalDate statDate
) {
}
