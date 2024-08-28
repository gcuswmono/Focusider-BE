package mono.focusider.domain.attendance.domain;

import jakarta.persistence.*;
import lombok.*;
import mono.focusider.domain.member.domain.Member;

@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "week_info_id")
    private WeekInfo weekInfo;

    public static Comment of(String content, Member member, WeekInfo weekInfo) {
        return Comment
                .builder()
                .content(content)
                .member(member)
                .weekInfo(weekInfo)
                .build();
    }
}
