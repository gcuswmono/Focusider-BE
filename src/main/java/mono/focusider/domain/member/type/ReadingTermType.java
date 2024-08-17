package mono.focusider.domain.member.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ReadingTermType {
    EVERYDAY(3, "매일"),
    ONCE_A_WEEK(2, "일주일에 한번"),
    SOMETIMES(1, "가끔"),
    ALMOST_NONE(0, "거의 없음");

    private final Integer code;
    private final String desc;
}
