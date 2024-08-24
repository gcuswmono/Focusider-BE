package mono.focusider.domain.member.redis.service;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.helper.MemberHelper;
import mono.focusider.domain.member.validate.MemberValidate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("MEMBER_DELETED_EXPIRED")
@RequiredArgsConstructor
@Transactional
public class MemberExpiredService {
    private final MemberHelper memberHelper;
    private final MemberValidate memberValidate;

    public void execute(Long memberId) {
        Member member = memberHelper.findMemberByIdOrThrow(memberId);
        memberValidate.validateDeletedMember(member);
        memberHelper.deleteMemberHard(member);
    }
}
