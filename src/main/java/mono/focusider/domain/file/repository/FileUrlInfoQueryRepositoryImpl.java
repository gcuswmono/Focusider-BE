package mono.focusider.domain.file.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mono.focusider.domain.file.dto.info.FileUrlInfo;

import java.util.List;

import static mono.focusider.domain.file.domain.QFile.file;

@RequiredArgsConstructor
public class FileUrlInfoQueryRepositoryImpl implements FileUrlInfoQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<FileUrlInfo> findFilesByIsUsedFalse() {
        return queryFactory
                .select(Projections.constructor(FileUrlInfo.class,
                        file.url))
                .from(file)
                .where(file.isUsed.isFalse())
                .fetch();
    }

    @Override
    public void deleteFilesByIsUsedFalse() {
        queryFactory
                .delete(file)
                .where(file.isUsed.isFalse())
                .execute();
    }
}
