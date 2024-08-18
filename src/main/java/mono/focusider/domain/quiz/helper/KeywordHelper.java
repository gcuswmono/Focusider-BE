package mono.focusider.domain.quiz.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.quiz.domain.Keyword;
import mono.focusider.domain.quiz.repository.KeywordRepository;
import mono.focusider.domain.quiz.type.KeywordType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KeywordHelper {
    private final KeywordRepository keywordRepository;

    public List<Keyword> findByKeywordType(List<KeywordType> keywordTypes) {
        return keywordRepository.findByKeywordTypes(keywordTypes);
    }
}
