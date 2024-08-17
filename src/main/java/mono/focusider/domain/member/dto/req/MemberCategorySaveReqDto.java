package mono.focusider.domain.member.dto.req;

import mono.focusider.domain.category.type.CategoryType;
import mono.focusider.domain.member.type.ReadingHardType;
import mono.focusider.domain.member.type.ReadingTermType;

import java.util.List;

public record MemberCategorySaveReqDto(
        ReadingTermType readingTermType,
        ReadingHardType readingHardType,
        List<CategoryType> categoryTypes
) {
}
