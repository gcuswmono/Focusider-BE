package mono.focusider.domain.article.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatStartRequestDto {
    private String memberId;
    private String articleId;
    private String initialAnswer;
    private int readTime;
}
