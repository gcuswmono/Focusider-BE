package mono.focusider.domain.quiz.type.converter;

import jakarta.persistence.Converter;
import mono.focusider.domain.quiz.type.KeywordType;
import mono.focusider.global.utils.enm.AbstractEnumCodeAttributeConverter;

@Converter
public class KeywordTypeConverter extends AbstractEnumCodeAttributeConverter<KeywordType> {
    public KeywordTypeConverter() {
        super(KeywordType.class);
    }
}
