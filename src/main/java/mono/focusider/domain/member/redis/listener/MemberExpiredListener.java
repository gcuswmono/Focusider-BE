package mono.focusider.domain.member.redis.listener;

import mono.focusider.domain.member.redis.service.MemberExpiredService;
import mono.focusider.global.utils.redis.RedisExpiredListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import static mono.focusider.global.utils.redis.RedisExpiredDataType.MEMBER_HARD_DELETE;

@Component
public class MemberExpiredListener extends RedisExpiredListener {
    private final MemberExpiredService memberExpiredService;

    public MemberExpiredListener(RedisMessageListenerContainer listenerContainer, MemberExpiredService memberExpiredService) {
        super(listenerContainer);
        this.memberExpiredService = memberExpiredService;
    }

    @Override
    protected void handleExpiredKey(String expiredKey) {
        if(expiredKey.startsWith("member_deleted:")) {
            Long memberId = Long.parseLong(expiredKey.substring(MEMBER_HARD_DELETE.getPrefix().length()));
            memberExpiredService.execute(memberId);
        }
    }
}
