package mono.focusider.domain.file.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum FileStatus {
    USED("사용중입니다.", true),
    NOT_USED("사용중이지 않습니다.", false);

    private final String desc;
    private final Boolean isUsed;
}
