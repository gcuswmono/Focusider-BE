package mono.focusider.domain.article.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatContinueRequestDto {
    private String memberId;
    private String answer;
}
