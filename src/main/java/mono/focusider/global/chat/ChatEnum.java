package mono.focusider.global.chat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ChatEnum {
    PROMPT_COMMENT_REQUIRE("이 정보를 바탕으로 해당 유저의 상태에 대해 3줄 정도로 평가해주세요. 만약 정보가 없다면 더욱 정진하세요 라는 답변을 해줘");

    private final String prefix;
}
