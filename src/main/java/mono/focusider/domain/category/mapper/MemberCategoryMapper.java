package mono.focusider.domain.category.mapper;

import mono.focusider.domain.category.domain.Category;
import mono.focusider.domain.category.domain.MemberCategory;
import mono.focusider.domain.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberCategoryMapper {

    public MemberCategory toMemberCategory(Member member, Category category) {
        return MemberCategory.of(member, category);
    }
}
