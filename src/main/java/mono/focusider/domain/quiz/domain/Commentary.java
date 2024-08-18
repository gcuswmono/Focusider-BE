package mono.focusider.domain.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import mono.focusider.global.domain.BaseTimeEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Commentary extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentary_id")
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    public static Commentary of(String content) {
        return Commentary
                .builder()
                .content(content)
                .build();
    }
}
