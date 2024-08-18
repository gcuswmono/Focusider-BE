package mono.focusider.domain.quiz.helper;

import lombok.RequiredArgsConstructor;
import mono.focusider.domain.quiz.domain.Commentary;
import mono.focusider.domain.quiz.dto.info.CommentaryInfo;
import mono.focusider.domain.quiz.repository.CommentaryRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentaryHelper {
    private final CommentaryRepository commentaryRepository;

    public Commentary createAndSaveCommentary(CommentaryInfo commentaryInfo) {
        return commentaryRepository.save(Commentary.of(commentaryInfo.content()));
    }
}
