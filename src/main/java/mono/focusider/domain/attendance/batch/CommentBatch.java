package mono.focusider.domain.attendance.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.article.helper.ChatHelper;
import mono.focusider.domain.article.helper.ReadingHelper;
import mono.focusider.domain.attendance.domain.WeekInfo;
import mono.focusider.domain.attendance.helper.CommentHelper;
import mono.focusider.domain.attendance.helper.WeekInfoHelper;
import mono.focusider.domain.attendance.validate.CommentValidate;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.helper.MemberHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentBatch {
    private final WeekInfoHelper weekInfoHelper;
    private final MemberHelper memberHelper;
    private final ChatHelper chatHelper;
    private final ReadingHelper readingHelper;
    private final CommentHelper commentHelper;
    private final CommentValidate commentValidate;

    @Scheduled(cron = "0 0 0 * * MON")
    //@Scheduled(fixedDelay = 5000)
    public void batch() {
        LocalDate today = LocalDate.now();
        WeekInfo weekInfo = weekInfoHelper.findPreviousWeekInfo(today);
        List<Member> allMember = memberHelper.findAllMember();
        allMember.forEach(member -> {
            createAndSaveComment(member, weekInfo);
        });
    }

    private void createAndSaveComment(Member member, WeekInfo weekInfo) {
        commentValidate.validateComment(member, weekInfo);
        String commentContent =
                chatHelper.getCommentContent(readingHelper.findReadingStatInfo(member.getId(), weekInfo.getId()));
        commentHelper.createAndSaveComment(commentContent, member, weekInfo);
    }
}
