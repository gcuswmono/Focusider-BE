package mono.focusider.global.aspect.member;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record MemberInfoParam(
        Long memberId,
        Integer memberLevel,
        String memberRole
) {
    public static MemberInfoParam of(Long memberId, Integer memberLevel, String memberRole) {
        return MemberInfoParam
                .builder()
                .memberId(memberId)
                .memberLevel(memberLevel)
                .memberRole(memberRole)
                .build();
    }
}
