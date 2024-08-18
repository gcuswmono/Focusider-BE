package mono.focusider.domain.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import mono.focusider.domain.quiz.type.QuizStatusType;
import mono.focusider.domain.quiz.type.converter.QuizStatusTypeConverter;
import mono.focusider.global.domain.BaseTimeEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class QuizAttempt extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_attempt_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "correct", nullable = false)
    @Convert(converter = QuizStatusTypeConverter.class)
    @Builder.Default
    private QuizStatusType quizStatusType = QuizStatusType.QUIZ_INCORRECT;
}
