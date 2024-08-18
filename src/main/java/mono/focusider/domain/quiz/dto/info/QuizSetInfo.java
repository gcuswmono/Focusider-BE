package mono.focusider.domain.quiz.dto.info;

import mono.focusider.domain.quiz.type.KeywordType;

import java.util.List;

public record QuizSetInfo(
        String title,
        String content,
        Integer level,
        CommentaryInfo commentaryInfo,
        List<KeywordType> keywordTypes,
        List<ChoiceSetInfo> choiceSetInfos
) {
}
