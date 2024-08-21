package mono.focusider.domain.quiz.repository.keyword;

import mono.focusider.domain.quiz.domain.Keyword;
import mono.focusider.domain.quiz.type.KeywordType;

import java.util.List;

public interface KeywordTypeQueryRepository {
    List<Keyword> findByKeywordTypes(List<KeywordType> keywordTypes);
}
