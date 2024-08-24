package mono.focusider.domain.member.redis.service;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.helper.MemberHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("MEMBER_DELETED_EXPIRED")
@RequiredArgsConstructor
@Transactional
public class MemberExpiredService {
    private final MemberHelper memberHelper;

    public void execute(Long memberId) {
        Member member = memberHelper.findMemberByIdOrThrow(memberId);
        memberHelper.deleteMember(member);
    }
}
