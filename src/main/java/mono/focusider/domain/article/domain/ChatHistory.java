package mono.focusider.domain.article.domain;

import jakarta.persistence.*;
import lombok.*;
import mono.focusider.global.domain.BaseTimeEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class ChatHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chathistory_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reading_id", nullable = false)
    private Reading reading;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    // 질문을 저장할 때 사용
    public static ChatHistory createWithQuestion(Reading reading, Article article, String question) {
        return ChatHistory.builder()
                .reading(reading)
                .article(article)
                .question(question)
                .build();
    }

    // 답변을 저장할 때 사용
    public static ChatHistory createWithAnswer(Reading reading, Article article, String answer) {
        return ChatHistory.builder()
                .reading(reading)
                .article(article)
                .answer(answer)
                .build();
    }
}
