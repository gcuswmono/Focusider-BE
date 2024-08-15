package mono.focusider.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.category.domain.Category;
import mono.focusider.domain.category.helper.CategoryHelper;
import mono.focusider.domain.category.mapper.MemberCategoryMapper;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.dto.req.MemberCategorySaveReq;
import mono.focusider.domain.member.helper.MemberHelper;
import mono.focusider.domain.member.type.ReadingHardType;
import mono.focusider.domain.member.type.ReadingTermType;
import mono.focusider.global.aspect.member.MemberInfoParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberHelper memberHelper;
    private final CategoryHelper categoryHelper;
    private final MemberCategoryMapper memberCategoryMapper;

    @Transactional
    public void createMemberCategory(MemberCategorySaveReq memberCategorySaveReq, MemberInfoParam memberInfo) {
        Member member = memberHelper.findMemberByIdOrThrow(memberInfo.memberId());
        Integer level = settingLevel(memberCategorySaveReq);
        List<Category> categories = categoryHelper.findCategoryListWithType(memberCategorySaveReq.categoryTypes());
        categories.forEach(category -> {
            member.addMemberCategory(memberCategoryMapper.toMemberCategory(member, category));
        });
        member.updateMemberLevel(level);
    }

    private Integer settingLevel(MemberCategorySaveReq memberCategorySaveReq) {
        ReadingTermType readingTermType = memberCategorySaveReq.readingTermType();
        ReadingHardType readingHardType = memberCategorySaveReq.readingHardType();
        return (int) Math.ceil((double) (readingTermType.getCode() * readingHardType.getCode()) / 3);
    }
}
