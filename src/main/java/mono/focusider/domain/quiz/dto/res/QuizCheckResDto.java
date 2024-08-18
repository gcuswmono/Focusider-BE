package mono.focusider.domain.quiz.dto.res;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record QuizCheckResDto(
        String correctContent,
        String chooseContent,
        String commentaryContent
) {
    public static QuizCheckResDto of(String correctContent, String chooseContent, String commentaryContent) {
        return QuizCheckResDto
                .builder()
                .correctContent(correctContent)
                .chooseContent(chooseContent)
                .commentaryContent(commentaryContent)
                .build();
    }
}
