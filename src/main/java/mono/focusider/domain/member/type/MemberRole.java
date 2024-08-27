package mono.focusider.domain.member.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mono.focusider.global.utils.enm.EnumField;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum MemberRole implements EnumField {
    ROLE_ADMIN(1, "관리지"),
    ROLE_MEMBER(2, "사용자");

    private Integer code;
    private String desc;
}
