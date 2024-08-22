package mono.focusider.domain.auth.mapper;

import mono.focusider.domain.auth.dto.info.AuthUserInfo;
import mono.focusider.domain.auth.dto.res.AuthResponseDto;
import mono.focusider.domain.auth.dto.res.CheckDuplicatedResDto;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.type.MemberGenderType;
import mono.focusider.domain.member.type.MemberRole;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public AuthUserInfo toAuthUserInfoWithToken(Long memberId, String name, MemberGenderType gender, Integer level, MemberRole memberRole) {
        return AuthUserInfo.ofToken(memberId, name, gender, level, memberRole);
    }

    public AuthUserInfo toAuthUserInfoWithMember(Member member) {
        return AuthUserInfo.of(member);
    }

    public AuthResponseDto toAuthResponseDto(String accountId) {
        return AuthResponseDto.of(accountId);
    }

    public CheckDuplicatedResDto toCheckDuplicatedResDto(boolean isDuplicated) {
        return CheckDuplicatedResDto.of(isDuplicated);
    }
}
