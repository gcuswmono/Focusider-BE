package mono.focusider.domain.article.repository;

import mono.focusider.domain.article.domain.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingRepository extends JpaRepository<Reading, Long>,
        ReadingStatQueryRepository, ReadingPageResDtoQueryRepository, ReadingDetailResDtoQueryRepository {

}
