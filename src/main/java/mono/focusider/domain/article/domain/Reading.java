package mono.focusider.domain.article.domain;

import jakarta.persistence.*;
import lombok.*;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.global.domain.BaseTimeEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Reading extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "reading_time", nullable = false)
    private Long readingTime;

    @Column(name = "summary", nullable = false)
    @Builder.Default
    private String summary = "";

    @Column(name = "understating", nullable = false)
    @Builder.Default
    private Integer understating = 0;
}
