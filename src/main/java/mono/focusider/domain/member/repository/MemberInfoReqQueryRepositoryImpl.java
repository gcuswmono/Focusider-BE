package mono.focusider.domain.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.member.dto.req.MemberInfoReqDto;

import java.util.Optional;

import static mono.focusider.domain.file.domain.QFile.file;
import static mono.focusider.domain.member.domain.QMember.member;

@RequiredArgsConstructor
public class MemberInfoReqQueryRepositoryImpl implements MemberInfoReqQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MemberInfoReqDto> findMemberInfoById(Long memberId) {
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(MemberInfoReqDto.class,
                        file.url,
                        member.accountId,
                        member.birthDate,
                        member.gender,
                        member.createdAt
                ))
                .from(member)
                .leftJoin(member.profileImageFile, file)
                .where(member.id.eq(memberId))
                .fetchOne());
    }
}
