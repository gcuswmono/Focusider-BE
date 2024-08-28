package mono.focusider.domain.attendance.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.attendance.domain.Comment;
import mono.focusider.domain.attendance.domain.WeekInfo;
import mono.focusider.domain.attendance.repository.CommentRepository;
import mono.focusider.domain.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentHelper {
    private final CommentRepository commentRepository;

    public void createAndSaveComment(String comment, Member member, WeekInfo weekInfo) {
        commentRepository.save(Comment.of(comment, member, weekInfo));
    }
}
