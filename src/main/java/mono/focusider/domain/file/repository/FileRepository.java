package mono.focusider.domain.file.repository;

import mono.focusider.domain.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByUrl(String url);
}
