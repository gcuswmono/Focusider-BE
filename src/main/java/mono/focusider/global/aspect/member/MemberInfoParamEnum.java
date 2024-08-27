package mono.focusider.global.aspect.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MemberInfoParamEnum {
    MEMBER_ID("memberId"),
    MEMBER_LEVEL("memberLevel"),
    MEMBER_ROLE("memberRole");

    private final String desc;
}
