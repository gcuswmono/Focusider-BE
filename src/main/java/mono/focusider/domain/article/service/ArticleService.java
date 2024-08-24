package mono.focusider.domain.article.service;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.dto.res.ArticleDetailResDto;
import mono.focusider.domain.article.helper.ArticleHelper;
import mono.focusider.domain.category.domain.Category;
import mono.focusider.domain.category.domain.MemberCategory;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.helper.MemberHelper;
import mono.focusider.global.aspect.member.MemberInfoParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleHelper articleHelper;
    private final MemberHelper memberHelper;

    public ArticleDetailResDto findArticleDetail(MemberInfoParam memberInfoParam) {
        Member member = memberHelper.findMemberByIdWithCategoriesOrThrow(memberInfoParam.memberId());
        List<Long> memberCategoryIds = member.getMemberCategories().stream()
                .map(MemberCategory::getCategory)
                .map(Category::getId)
                .toList();
        return articleHelper.findArticleDetailRandWithMember(member, memberCategoryIds);
    }
}
