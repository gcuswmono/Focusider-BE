package mono.focusider.domain.quiz.repository;

import mono.focusider.domain.quiz.dto.info.QuizInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuizAttemptWrongQueryRepository {

    Page<QuizInfo> getQuizAttemptWrong(Pageable pageable, Long memberId);
}
