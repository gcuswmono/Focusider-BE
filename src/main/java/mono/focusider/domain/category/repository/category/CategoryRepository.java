package mono.focusider.domain.category.repository.category;

import mono.focusider.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryTypeQueryRepository {
}
