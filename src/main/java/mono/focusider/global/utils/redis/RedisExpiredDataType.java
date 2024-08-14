package mono.focusider.global.utils.redis;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum RedisExpiredDataType {
    RAFFLE_WAIT_MEMBER_EXPIRED("raffle_wait_member:", "1", 86400000L),
    RAFFLE_WIN_MEMBER_EXPIRED("raffle_win_member:", "3", 259200000L),
    EMAIL_RECEIVED_MEMBER("certification:", null, 300000L),
    PENALTY_RECEIVED_MEMBER("penalty:", null, 300000L),
    WKT_END_EXPIRED("wkt_end_date:", null, null);

    private final String prefix;
    private final String defaultValue;
    private final Long deadLine;
}
