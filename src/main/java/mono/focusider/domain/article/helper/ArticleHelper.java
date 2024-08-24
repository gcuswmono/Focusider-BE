package mono.focusider.domain.article.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.article.dto.res.ArticleDetailResDto;
import mono.focusider.domain.article.repository.ArticleRepository;
import mono.focusider.domain.member.domain.Member;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleHelper {
    private final ArticleRepository articleRepository;

    public ArticleDetailResDto findArticleDetailRandWithMember(Member member, List<Long> categoryIds) {
        return articleRepository.findArticleDetailDto(member, categoryIds);
    }
}
