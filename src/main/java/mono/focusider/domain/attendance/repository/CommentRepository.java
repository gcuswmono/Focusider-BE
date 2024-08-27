package mono.focusider.domain.attendance.repository;

import mono.focusider.domain.attendance.domain.Comment;
import mono.focusider.domain.attendance.domain.WeekInfo;
import mono.focusider.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByMemberAndWeekInfo(Member member, WeekInfo weekInfo);
}
