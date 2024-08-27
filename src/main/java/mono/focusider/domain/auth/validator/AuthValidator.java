package mono.focusider.domain.auth.validator;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.auth.error.AuthErrorCode;
import mono.focusider.domain.member.repository.MemberRepository;
import mono.focusider.global.error.code.GlobalErrorCode;
import mono.focusider.global.error.exception.ConflictException;
import mono.focusider.global.error.exception.ForbiddenException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Component
public class AuthValidator {

    private final MemberRepository memberRepository;

    public void checkAccountId(String accountId) {
        if(memberRepository.existsByAccountId(accountId)) {
            throw new ConflictException(AuthErrorCode.DUPLICATE_MEMBER_ACCOUNT_ID);
        }
    }

    public boolean checkAccountIdWithBoolean(String accountId) {
        return memberRepository.existsByAccountId(accountId);
    }

    public void validatePassword(String password, String memberPassword, PasswordEncoder passwordEncoder) {
        try {
            passwordEncoder.matches(password, memberPassword);
        } catch (Exception e) {
            throw new ForbiddenException(GlobalErrorCode.MISMATCH_PASSWORD);
        }
    }
}
