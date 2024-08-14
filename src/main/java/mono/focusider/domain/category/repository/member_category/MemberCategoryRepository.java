package mono.focusider.domain.category.repository.member_category;

import mono.focusider.domain.category.domain.MemberCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCategoryRepository extends JpaRepository<MemberCategory, Long> {
}
