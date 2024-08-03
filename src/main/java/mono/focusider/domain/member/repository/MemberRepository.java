package mono.focusider.domain.member.repository;

import mono.focusider.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByAccountId(String accountId);
}