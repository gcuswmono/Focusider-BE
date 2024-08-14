package mono.focusider.global.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum JwtEnum {
    ACCESS_TOKEN_NAME("accessToken");

    private final String desc;
}
