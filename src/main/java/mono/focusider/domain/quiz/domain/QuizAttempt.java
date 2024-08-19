package mono.focusider.domain.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import mono.focusider.domain.member.domain.Member;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "correct", nullable = false)
    @Convert(converter = QuizStatusTypeConverter.class)
    @Builder.Default
    private QuizStatusType quizStatusType = QuizStatusType.QUIZ_INCORRECT;

    public static QuizAttempt of(Quiz quiz, Member member, QuizStatusType quizStatusType) {
        return QuizAttempt
                .builder()
                .quiz(quiz)
                .member(member)
                .quizStatusType(quizStatusType)
                .build();
    }
}
