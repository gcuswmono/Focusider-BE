package mono.focusider.domain.category.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mono.focusider.global.utils.enm.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CategoryType implements EnumField {
    ART(1, "예술"),
    SCIENCE(2, "과학"),
    SOCIETY(3, "사회"),
    TECHNOLOGY(4, "기술"),
    HUMANITIES(5, "인문"),
    AMALGAMATION(6, "융합");

    private final Integer code;
    private final String desc;
}
