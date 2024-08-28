package mono.focusider.domain.attendance.validate;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.attendance.domain.WeekInfo;
import mono.focusider.domain.attendance.repository.CommentRepository;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.global.error.exception.BusinessException;
import org.springframework.stereotype.Component;

import static mono.focusider.domain.attendance.error.CommentError.DUPLICATE_COMMENT;

@Component
@RequiredArgsConstructor
public class CommentValidate {
    private final CommentRepository commentRepository;

    public void validateComment(Member member, WeekInfo weekInfo) {
        if(commentRepository.existsByMemberAndWeekInfo(member, weekInfo)) {
            throw new BusinessException(DUPLICATE_COMMENT);
        }
    }
}
