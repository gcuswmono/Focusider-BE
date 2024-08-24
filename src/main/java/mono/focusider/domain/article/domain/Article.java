package mono.focusider.domain.article.domain;

import jakarta.persistence.*;
import lombok.*;
import mono.focusider.domain.category.type.CategoryType;
import mono.focusider.domain.category.type.converter.CategoryTypeConverter;

@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long article_id;

    @Column(name = "title", columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "level", columnDefinition = "INT", nullable = false)
    private Integer level;

    @Convert(converter = CategoryTypeConverter.class)
    @Column(name = "article_type", nullable = false)
    CategoryType categoryType;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private Reading reading;
}
