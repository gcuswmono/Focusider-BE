package mono.focusider.domain.category.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mono.focusider.global.enm.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CategoryType implements EnumField {
    SCIENCE(0, "과학");

    private final Integer code;
    private final String desc;
}
