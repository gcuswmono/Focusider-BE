package mono.focusider.global.utils.cookie;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CookieEnum {
    ACCESS_TOKEN("accessToken", "1");

    private final String name;
    private final String value;
}
