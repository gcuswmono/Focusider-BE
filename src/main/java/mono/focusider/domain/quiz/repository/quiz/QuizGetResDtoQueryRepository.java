package mono.focusider.domain.quiz.repository.quiz;

import mono.focusider.domain.quiz.dto.res.QuizGetResDto;

public interface QuizGetResDtoQueryRepository {
    QuizGetResDto findByLevelAndMemberId(int level, long memberId);
    QuizGetResDto findByQuizId(long quizId);
}
