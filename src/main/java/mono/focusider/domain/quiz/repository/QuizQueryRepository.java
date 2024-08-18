package mono.focusider.domain.quiz.repository;

import mono.focusider.domain.quiz.dto.res.QuizInfo;

public interface QuizQueryRepository {
    QuizInfo findByLevelAndMemberId(int level, long memberId);
}
