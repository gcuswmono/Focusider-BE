package mono.focusider.domain.category.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.category.domain.Category;
import mono.focusider.domain.category.repository.category.CategoryRepository;
import mono.focusider.domain.category.type.CategoryType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryHelper {

    private final CategoryRepository categoryRepository;

    public List<Category> findCategoryListWithType(List<CategoryType> types) {
        return categoryRepository.findByCategoryType(types);
    }
}
