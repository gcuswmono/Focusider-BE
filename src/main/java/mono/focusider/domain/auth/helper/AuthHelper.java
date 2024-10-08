package mono.focusider.domain.auth.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.auth.dto.req.SignupReqDto;
import mono.focusider.domain.file.domain.File;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthHelper {

    private final MemberRepository memberRepository;

    public Member createMemberAndSave(SignupReqDto signupReqDto, File profileImageFile, String password) {
        return memberRepository.save(Member.createMember(signupReqDto, profileImageFile, password));
    }
}
