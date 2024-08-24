package mono.focusider.global.utils.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public abstract class RedisExpiredListener extends KeyExpirationEventMessageListener {
    public RedisExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        handleExpiredKey(expiredKey);
    }

    protected abstract void handleExpiredKey(String expiredKey);
}
