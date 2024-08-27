package mono.focusider.domain.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import mono.focusider.domain.quiz.type.KeywordType;
import mono.focusider.domain.quiz.type.converter.KeywordTypeConverter;
import mono.focusider.global.domain.BaseTimeEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Keyword extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id", nullable = false)
    private Long id;

    @Column(name = "keyword_type", nullable = false)
    @Convert(converter = KeywordTypeConverter.class)
    private KeywordType keywordType;
}
