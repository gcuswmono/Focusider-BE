package mono.focusider.domain.member.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ReadingHardType {
    OFTEN(3, "자주"),
    SOMETIMES(2, "가끔"),
    ALMOST_NONE(1, "거의 없음"),
    NOTHING(0, "전혀 없음");

    private final Integer code;
    private final String desc;
}
