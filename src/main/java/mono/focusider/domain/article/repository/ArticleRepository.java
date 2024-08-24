package mono.focusider.domain.article.repository;

import mono.focusider.domain.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleDetailDtoQueryRepository {
}
