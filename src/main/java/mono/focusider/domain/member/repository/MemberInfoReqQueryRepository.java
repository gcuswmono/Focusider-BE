package mono.focusider.domain.member.repository;

import mono.focusider.domain.member.dto.req.MemberInfoReqDto;

import java.util.Optional;

public interface MemberInfoReqQueryRepository {
    Optional<MemberInfoReqDto> findMemberInfoById(Long memberId);
}
