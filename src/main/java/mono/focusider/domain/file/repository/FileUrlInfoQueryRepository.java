package mono.focusider.domain.file.repository;

import mono.focusider.domain.file.dto.info.FileUrlInfo;

import java.util.List;

public interface FileUrlInfoQueryRepository {
    List<FileUrlInfo> findFilesByIsUsedFalse();

    void deleteFilesByIsUsedFalse();
}
