package mono.focusider.domain.quiz.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mono.focusider.global.utils.enm.EnumField;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum QuizStatusType implements EnumField {
    QUIZ_CORRECT(1, "정답"),
    QUIZ_INCORRECT(2, "오답");

    private Integer code;
    private String desc;
}
