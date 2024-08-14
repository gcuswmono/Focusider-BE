package mono.focusider.domain.category.repository.category;

import mono.focusider.domain.category.domain.Category;
import mono.focusider.domain.category.type.CategoryType;

import java.util.List;

public interface CategoryTypeQueryRepository {

    List<Category> findByCategoryType(List<CategoryType> categoryTypeList);
}
