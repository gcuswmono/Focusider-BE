package mono.focusider.global.utils.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisUtils {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String VALUE_KEY_PREFIX = "reverse:";

    public void setData(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setDataWithExpireTime(RedisExpiredData redisExpiredData) {
        redisTemplate.opsForValue().set(
                redisExpiredData.key(),
                redisExpiredData.defaultValue(),
                redisExpiredData.expiredTime(),
                TimeUnit.MILLISECONDS
        );
    }

    public Object getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Object getKeyByValue(String value) {
        return redisTemplate.opsForValue().get(VALUE_KEY_PREFIX + value);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
