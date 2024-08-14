package mono.focusider.global.utils.redis;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.Duration;
import java.time.LocalDate;

import static mono.focusider.global.utils.redis.RedisExpiredDataType.*;


@Builder(access = AccessLevel.PRIVATE)
public record RedisExpiredData(
        String key,
        String defaultValue,
        Long expiredTime
) {
    public static RedisExpiredData ofToken(String accessToken, String refreshToken, Long refreshTokenExpiredTime) {
        return RedisExpiredData.builder()
                .key(accessToken)
                .defaultValue(refreshToken)
                .expiredTime(refreshTokenExpiredTime)
                .build();
    }


    private static Long calcExpiredTime(LocalDate expiredDate) {
        LocalDate now = LocalDate.now();
        Duration duration = Duration.between(now.atStartOfDay(), expiredDate.atStartOfDay());
        return duration.toMillis();
    }
}
