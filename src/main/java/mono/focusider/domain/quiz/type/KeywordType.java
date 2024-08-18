package mono.focusider.domain.quiz.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mono.focusider.global.utils.enm.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum KeywordType implements EnumField {
    VOCABULARY(1, "어휘력"),
    PROVERB(2, "속담"),
    FOUR_IDIOMS(3, "사자성어"),
    WORDS_AND_EXPRESSIONS(4, "단어와 표현"),
    GRAMMAR(5, "문법");

    private final Integer code;
    private final String desc;
}
