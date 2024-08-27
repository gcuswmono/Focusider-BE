package mono.focusider.domain.quiz.dto.info;

import java.time.LocalDateTime;
import java.util.List;

public record QuizInfo(
        Long quizId,
        Long quizAttemptId,
        Integer level,
        List<KeywordInfo> keywordInfos,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
