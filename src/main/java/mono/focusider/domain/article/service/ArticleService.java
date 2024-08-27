package mono.focusider.domain.article.service;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.dto.info.ReadingStatInfo;
import mono.focusider.domain.article.dto.req.ReadingStatReqDto;
import mono.focusider.domain.article.dto.res.ArticleDetailResDto;
import mono.focusider.domain.article.dto.res.ReadingStatResDto;
import mono.focusider.domain.article.helper.ArticleHelper;
import mono.focusider.domain.article.helper.ReadingHelper;
import mono.focusider.domain.article.mapper.ReadingMapper;
import mono.focusider.domain.category.domain.Category;
import mono.focusider.domain.category.domain.MemberCategory;
import mono.focusider.domain.category.type.CategoryType;
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
    private final ReadingHelper readingHelper;
    private final ReadingMapper readingMapper;

    public ArticleDetailResDto findArticleDetail(MemberInfoParam memberInfoParam) {
        Member member = memberHelper.findMemberByIdWithCategoriesOrThrow(memberInfoParam.memberId());
        List<CategoryType> memberCategoryTypes = member.getMemberCategories().stream()
                .map(MemberCategory::getCategory)
                .map(Category::getCategoryType)
                .toList();
        return articleHelper.findArticleDetailRandWithMember(member, memberCategoryTypes);
    }

    public ReadingStatResDto findReadingMonthlyStat(MemberInfoParam memberInfoParam, ReadingStatReqDto reqDto) {
        List<ReadingStatInfo> readingStatInfo = readingHelper.findReadingStatInfo(memberInfoParam.memberId(), reqDto.statDate());
        return readingMapper.toReadingStatDto(readingStatInfo);
    }
}
