package mono.focusider.global.utils.redis;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum RedisExpiredDataType {
    MEMBER_HARD_DELETE("member_deleted:", "deleted", 1209600000L);

    private final String prefix;
    private final String defaultValue;
    private final Long deadLine;
}
