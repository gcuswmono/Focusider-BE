package mono.focusider.domain.quiz.dto.res;

import lombok.AccessLevel;
import lombok.Builder;
import mono.focusider.domain.quiz.domain.Quiz;
import mono.focusider.domain.quiz.dto.info.ChoiceInfo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record QuizGetResDto(
        Long quizId,
        String title,
        String content,
        List<ChoiceInfo> choiceContent
) {
    public static QuizGetResDto of(Quiz quiz, List<ChoiceInfo> choiceInfos) {
        return QuizGetResDto
                .builder()
                .quizId(quiz.getId())
                .title(quiz.getTitle())
                .content(quiz.getContent())
                .choiceContent(choiceInfos)
                .build();
    }
}
