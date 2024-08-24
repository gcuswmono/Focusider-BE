package mono.focusider.domain.member.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.dto.req.MemberInfoReqDto;
import mono.focusider.domain.member.error.MemberErrorCode;
import mono.focusider.domain.member.repository.MemberRepository;
import mono.focusider.global.error.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class MemberHelper {
    private final MemberRepository memberRepository;

    public Member findMemberByAccountIdOrThrow(String accountId) {
        return memberRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public Member findMemberByIdOrThrow(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public MemberInfoReqDto findMemberInfoByIdOrThrow(Long memberId) {
        return memberRepository.findMemberInfoById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public Member findMemberByIdWithFileOrThrow(Long id) {
        return memberRepository.findByIdWithFile(id)
                .orElseThrow(() -> new EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public void deleteMemberHard(Member member) {
        memberRepository.delete(member);
    }
}
