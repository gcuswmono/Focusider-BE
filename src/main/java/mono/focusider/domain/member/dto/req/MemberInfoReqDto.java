package mono.focusider.domain.member.dto.req;

import mono.focusider.domain.member.type.MemberGenderType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MemberInfoReqDto(
        String profileImageUrl,
        String accountId,
        LocalDate birthDay,
        MemberGenderType memberGenderType,
        LocalDateTime createdAt
) {
}
