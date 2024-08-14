package mono.focusider.domain.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import mono.focusider.domain.quiz.type.KeywordType;
import mono.focusider.domain.quiz.type.converter.KeywordTypeConverter;

@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @Convert(converter = KeywordTypeConverter.class)
    private KeywordType keywordType;
}
