package mono.focusider.domain.member.dto.req;

import mono.focusider.domain.category.type.CategoryType;

import java.util.List;

public record MemberCategorySaveReq(
        List<CategoryType> categoryTypes
) {
}
