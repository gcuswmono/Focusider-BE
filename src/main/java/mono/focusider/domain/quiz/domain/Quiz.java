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

    @Column(name = "name", nullable = false)
    private String name;
}
