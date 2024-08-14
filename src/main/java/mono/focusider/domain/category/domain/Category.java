package mono.focusider.domain.category.domain;

import jakarta.persistence.*;
import lombok.*;
import mono.focusider.domain.category.type.CategoryType;
import mono.focusider.domain.category.type.converter.CategoryTypeConverter;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.global.domain.BaseTimeEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Category extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_type", nullable = false)
    @Convert(converter = CategoryTypeConverter.class)
    private CategoryType categoryType;
}
