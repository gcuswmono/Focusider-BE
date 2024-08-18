package mono.focusider.domain.quiz.dto.res;

import lombok.AccessLevel;
import lombok.Builder;
import mono.focusider.domain.quiz.domain.Quiz;
import mono.focusider.domain.quiz.dto.info.ChoiceInfo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record QuizInfo(
        String title,
        String content,
        List<ChoiceInfo> choiceContent
) {
    public static QuizInfo of(Quiz quiz, List<ChoiceInfo> choiceInfos) {
        return QuizInfo
                .builder()
                .title(quiz.getTitle())
                .content(quiz.getContent())
                .choiceContent(choiceInfos)
                .build();
    }
}
