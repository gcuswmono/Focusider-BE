package mono.focusider.global.utils.redis;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.Duration;
import java.time.LocalDate;

import static mono.focusider.global.utils.redis.RedisExpiredDataType.MEMBER_HARD_DELETE;

@Builder(access = AccessLevel.PRIVATE)
public record RedisExpiredData(
        String key,
        String defaultValue,
        Long expiredTime) {
    public static RedisExpiredData ofToken(String accessToken, String refreshToken, Long refreshTokenExpiredTime) {
        return RedisExpiredData.builder()
                .key(accessToken)
                .defaultValue(refreshToken)
                .expiredTime(refreshTokenExpiredTime)
                .build();
    }

    public static RedisExpiredData ofMemberHardDeleted(Long memberId) {
        return RedisExpiredData
                .builder()
                .key(MEMBER_HARD_DELETE.getPrefix() + memberId)
                .defaultValue(MEMBER_HARD_DELETE.getDefaultValue())
                .expiredTime(MEMBER_HARD_DELETE.getDeadLine())
                .build();
    }

    public static RedisExpiredData ofChatSession(String sessionId, String conversationHistoryJson, Long expiredTime) {
        return RedisExpiredData
                .builder()
                .key("chat_session:" + sessionId)
                .defaultValue(conversationHistoryJson)
                .expiredTime(expiredTime)
                .build();
    }

    private static Long calcExpiredTime(LocalDate expiredDate) {
        LocalDate now = LocalDate.now();
        Duration duration = Duration.between(now.atStartOfDay(), expiredDate.atStartOfDay());
        return duration.toMillis();
    }
}
