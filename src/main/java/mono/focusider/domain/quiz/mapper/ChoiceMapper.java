package mono.focusider.domain.quiz.mapper;

import mono.focusider.domain.quiz.dto.res.QuizCheckResDto;
import org.springframework.stereotype.Component;

@Component
public class ChoiceMapper {

    public QuizCheckResDto toQuizCheckResDto(String correctContent, String chooseContent, String commentaryContent) {
        return QuizCheckResDto.of(correctContent, chooseContent, commentaryContent);
    }
}
