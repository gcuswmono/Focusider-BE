package mono.focusider.global.aspect.member;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record MemberInfoParam(
        Long memberId,
        String memberRole
) {
    public static MemberInfoParam of(Long memberId, String memberRole) {
        return MemberInfoParam
                .builder()
                .memberId(memberId)
                .memberRole(memberRole)
                .build();
    }
}
