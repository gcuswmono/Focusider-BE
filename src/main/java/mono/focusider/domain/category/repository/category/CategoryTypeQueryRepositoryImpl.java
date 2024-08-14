package mono.focusider.domain.category.repository.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.category.domain.Category;
import mono.focusider.domain.category.type.CategoryType;

import java.util.List;

import static mono.focusider.domain.category.domain.QCategory.category;

@RequiredArgsConstructor
public class CategoryTypeQueryRepositoryImpl implements CategoryTypeQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Category> findByCategoryType(List<CategoryType> categoryTypeList) {
        return queryFactory
                .selectFrom(category)
                .where(category.categoryType.in(categoryTypeList))
                .fetch();
    }
}
