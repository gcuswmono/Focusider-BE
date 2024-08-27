package mono.focusider.domain.member.validate;

import mono.focusider.domain.member.domain.Member;
import mono.focusider.global.error.exception.BusinessException;
import org.springframework.stereotype.Component;

import static mono.focusider.domain.member.error.MemberErrorCode.MEMBER_NOT_DELETED;

@Component
public class MemberValidate {

    public void validateDeletedMember(Member member) {
        if(!member.getDeleted()) {
            throw new BusinessException(MEMBER_NOT_DELETED);
        }
    }
}
