package mono.focusider.domain.quiz.dto.info;

import lombok.AccessLevel;
import lombok.Builder;
import mono.focusider.domain.quiz.domain.Choice;

@Builder(access = AccessLevel.PRIVATE)
public record ChoiceInfo(
        Long id,
        String content
) {
    public static ChoiceInfo of(Choice choice) {
        return ChoiceInfo
                .builder()
                .id(choice.getId())
                .content(choice.getContent())
                .build();
    }
}
