package mono.focusider.domain.auth.mapper;

import mono.focusider.domain.auth.dto.info.AuthUserInfo;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public AuthUserInfo toAuthUserInfoWithToken(Long memberId, String name, String gender, Integer level) {
        return AuthUserInfo.ofToken(memberId, name, gender, level);
    }
}
