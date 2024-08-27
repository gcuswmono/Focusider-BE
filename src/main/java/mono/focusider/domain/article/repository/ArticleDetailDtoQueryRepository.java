package mono.focusider.domain.article.repository;

import mono.focusider.domain.article.dto.res.ArticleDetailResDto;
import mono.focusider.domain.category.type.CategoryType;
import mono.focusider.domain.member.domain.Member;

import java.util.List;

public interface ArticleDetailDtoQueryRepository {
    ArticleDetailResDto findArticleDetailDto(Member member, List<CategoryType> categoryTypes);
}
