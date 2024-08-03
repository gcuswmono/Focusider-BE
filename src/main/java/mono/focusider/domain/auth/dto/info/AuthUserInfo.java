package mono.focusider.domain.auth.dto.info;

import lombok.AccessLevel;
import lombok.Builder;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.type.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Builder(access = AccessLevel.PRIVATE)
public record AuthUserInfo(
        Long memberId,
        String name,
        String gender,
        Integer level,
        MemberRole memberRole
) {
    public static AuthUserInfo of(Member member) {
        return AuthUserInfo
                .builder()
                .memberId(member.getId())
                .name(member.getName())
                .gender(member.getGender())
                .level(member.getLevel())
                .memberRole(member.getMemberRole())
                .build();
    }

    public static AuthUserInfo ofToken(Long memberId, String name, String gender, Integer level, MemberRole memberRole) {
        return AuthUserInfo
                .builder()
                .memberId(memberId)
                .name(name)
                .gender(gender)
                .level(level)
                .memberRole(memberRole)
                .build();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(memberRole.getDesc()));
    }
}
