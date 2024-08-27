package mono.focusider.domain.member.repository;

import mono.focusider.domain.member.domain.Member;

import java.util.Optional;

public interface MemberQueryRepository {
    Optional<Member> findByIdWithFile(Long memberId);
    Optional<Member> findByIdWithCategories(Long memberId);
}
