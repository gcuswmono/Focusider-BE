package mono.focusider.global.utils.s3;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum FileType {
    MEMBER_PROFILE_IMAGE("member_profile_image");

    private final String desc;
}
