package mono.focusider.global.enm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.EnumSet;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnumValueUtils {
    public static <T extends Enum<T> & EnumField> Integer toDBCode(T enumClass) {
        if (enumClass == null) return -1;
        return enumClass.getCode();
    }

    public static <T extends Enum<T> & EnumField> T toEntityCode(Class<T> enumClass, Integer dbCode) {
        if (Objects.isNull(dbCode) || dbCode == -1) return null;
        return EnumSet.allOf(enumClass).stream()
                .filter(e -> e.getCode().equals(dbCode))
                .findAny()
                .orElseThrow(() -> EnumValidateException.EXCEPTION);
    }
}