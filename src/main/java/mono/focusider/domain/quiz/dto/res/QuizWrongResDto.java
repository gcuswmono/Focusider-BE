package mono.focusider.domain.quiz.dto.res;

import lombok.AccessLevel;
import lombok.Builder;
import mono.focusider.domain.quiz.dto.info.QuizInfo;
import mono.focusider.global.domain.PageInfo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record QuizWrongResDto(
        List<QuizInfo> quizInfos,
        PageInfo pageInfo
) {
    public static QuizWrongResDto of(List<QuizInfo> quizInfos, PageInfo pageInfo) {
        return QuizWrongResDto
                .builder()
                .quizInfos(quizInfos)
                .pageInfo(pageInfo)
                .build();
    }
}
