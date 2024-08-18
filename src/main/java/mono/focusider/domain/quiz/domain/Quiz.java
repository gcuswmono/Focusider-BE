package mono.focusider.domain.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import mono.focusider.global.domain.BaseTimeEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Quiz extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "level", nullable = false)
    private Integer level;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentary_id")
    private Commentary commentary;

    public static Quiz of(String title, String content, Integer level, Commentary commentary) {
        return Quiz
                .builder()
                .title(title)
                .content(content)
                .level(level)
                .commentary(commentary)
                .build();
    }
}
