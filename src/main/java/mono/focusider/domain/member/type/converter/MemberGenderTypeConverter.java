package mono.focusider.domain.member.type.converter;

import mono.focusider.domain.member.type.MemberGenderType;
import mono.focusider.global.utils.enm.AbstractEnumCodeAttributeConverter;

public class MemberGenderTypeConverter extends AbstractEnumCodeAttributeConverter<MemberGenderType> {
    public MemberGenderTypeConverter() {
        super(MemberGenderType.class);
    }
}
