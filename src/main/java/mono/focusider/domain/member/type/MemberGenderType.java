package mono.focusider.domain.member.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mono.focusider.global.utils.enm.EnumField;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum MemberGenderType implements EnumField {
    MALE(1, "남자"),
    FEMALE(2, "여자");

    private Integer code;
    private String desc;
}
