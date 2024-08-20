package mono.focusider.domain.quiz.mapper;

import mono.focusider.domain.quiz.dto.info.QuizInfo;
import mono.focusider.domain.quiz.dto.res.QuizWrongResDto;
import mono.focusider.global.domain.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class QuizAttemptMapper {
    public QuizWrongResDto toQuizWrongResDto(Page<QuizInfo> quizInfos) {
        PageInfo pageInfo = PageInfo.of(quizInfos);
        return QuizWrongResDto.of(quizInfos.getContent(), pageInfo);
    }
}
