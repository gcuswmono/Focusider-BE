package mono.focusider.domain.member.type.converter;

import jakarta.persistence.Converter;
import mono.focusider.domain.member.type.MemberRole;
import mono.focusider.global.utils.enm.AbstractEnumCodeAttributeConverter;

@Converter
public class MemberRoleConverter extends AbstractEnumCodeAttributeConverter<MemberRole> {
    public MemberRoleConverter() {
        super(MemberRole.class);
    }
}
