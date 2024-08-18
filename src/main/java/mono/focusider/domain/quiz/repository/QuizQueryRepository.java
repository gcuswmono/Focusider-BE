package mono.focusider.domain.quiz.repository;

import mono.focusider.domain.quiz.dto.res.QuizGetResDto;

public interface QuizQueryRepository {
    QuizGetResDto findByLevelAndMemberId(int level, long memberId);
}
