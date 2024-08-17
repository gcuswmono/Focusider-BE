package mono.focusider.domain.auth.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.auth.dto.req.SignupRequestDto;
import mono.focusider.domain.file.domain.File;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthHelper {

    private final MemberRepository memberRepository;

    public void createMemberAndSave(SignupRequestDto signupRequestDto, File profileImageFile, String password) {
        memberRepository.save(Member.createMember(signupRequestDto, profileImageFile, password));
    }
}
