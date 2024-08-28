package mono.focusider.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.member.domain.Member;

import java.util.Optional;

import static mono.focusider.domain.category.domain.QMemberCategory.memberCategory;
import static mono.focusider.domain.file.domain.QFile.file;
import static mono.focusider.domain.member.domain.QMember.member;

@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Member> findByIdWithFile(Long memberId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .leftJoin(member.profileImageFile, file).fetchJoin()
                .where(member.id.eq(memberId))
                .fetchOne());
    }

    @Override
    public Optional<Member> findByIdWithCategories(Long memberId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .leftJoin(member.memberCategories, memberCategory).fetchJoin()
                .leftJoin(memberCategory.category).fetchJoin()
                .where(member.id.eq(memberId))
                .fetchOne()
        );
    }
}
