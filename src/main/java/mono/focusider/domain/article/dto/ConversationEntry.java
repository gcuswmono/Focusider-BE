package mono.focusider.domain.article.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor
public class ConversationEntry {
    private String question;
    private String answer;
}