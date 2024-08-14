package mono.focusider.domain.quiz.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mono.focusider.global.utils.enm.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum KeywordType implements EnumField {
    SCIENCE(1, "과학");

    private final Integer code;
    private final String desc;
}
