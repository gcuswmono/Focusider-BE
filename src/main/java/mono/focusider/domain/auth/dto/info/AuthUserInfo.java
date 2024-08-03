package mono.focusider.domain.auth.dto.info;

import lombok.AccessLevel;
import lombok.Builder;
import mono.focusider.domain.member.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Builder(access = AccessLevel.PRIVATE)
public record AuthUserInfo(
        Long memberId,
        String name,
        String gender,
        Integer level
) {
    public static AuthUserInfo ofToken(Long memberId, String name, String gender, Integer level) {
        return AuthUserInfo
                .builder()
                .memberId(memberId)
                .name(name)
                .gender(gender)
                .level(level)
                .build();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(null));
    }
}
