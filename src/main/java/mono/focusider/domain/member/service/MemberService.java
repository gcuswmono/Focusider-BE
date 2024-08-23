package mono.focusider.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.category.domain.Category;
import mono.focusider.domain.category.helper.CategoryHelper;
import mono.focusider.domain.category.mapper.MemberCategoryMapper;
import mono.focusider.domain.file.domain.File;
import mono.focusider.domain.file.helper.FileHelper;
import mono.focusider.domain.file.validate.FileValidate;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.dto.req.MemberCategorySaveReqDto;
import mono.focusider.domain.member.dto.req.MemberUpdateReqDto;
import mono.focusider.domain.member.dto.req.MemberInfoReqDto;
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
    private final FileHelper fileHelper;
    private final FileValidate fileValidate;

    @Transactional
    public void createMemberCategory(MemberCategorySaveReqDto memberCategorySaveReqDto) {
        Member member = memberHelper.findMemberByAccountIdOrThrow(memberCategorySaveReqDto.accountId());
        Integer level = settingLevel(memberCategorySaveReqDto);
        List<Category> categories = categoryHelper.findCategoryListWithType(memberCategorySaveReqDto.categoryTypes());
        categories.forEach(category -> {
            member.addMemberCategory(memberCategoryMapper.toMemberCategory(member, category));
        });
        member.updateMemberLevel(level);
    }

    public MemberInfoReqDto findMemberInfo(MemberInfoParam memberInfoParam) {
        return memberHelper.findMemberInfoByIdOrThrow(memberInfoParam.memberId());
    }

    @Transactional
    public void updateMemberInfo(MemberUpdateReqDto memberUpdateReqDto, MemberInfoParam memberInfoParam) {
        Member member = memberHelper.findMemberByIdWithFileOrThrow(memberInfoParam.memberId());
        File file = fileHelper.findFileByUrlOrNull(memberUpdateReqDto.profileImageUrl());
        fileValidate.validateFileAndUpdateUnUsed(member.getProfileImageFile());
        member.updateMemberInfo(file, memberUpdateReqDto.name());
        fileValidate.validateFileAndUpdateUsed(file);
    }

    private Integer settingLevel(MemberCategorySaveReqDto memberCategorySaveReqDto) {
        ReadingTermType readingTermType = memberCategorySaveReqDto.readingTermType();
        ReadingHardType readingHardType = memberCategorySaveReqDto.readingHardType();
        return (int) Math.ceil((double) (readingTermType.getCode() * readingHardType.getCode()) / 3);
    }
}
