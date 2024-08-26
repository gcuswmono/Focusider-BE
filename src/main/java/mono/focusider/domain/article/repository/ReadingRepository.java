package mono.focusider.domain.article.repository;

import mono.focusider.domain.article.domain.Reading;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingRepository extends JpaRepository<Reading, Long>, ReadingStatQueryRepository {
    List<Reading> findAllByMemberId(Long memberId);
}
