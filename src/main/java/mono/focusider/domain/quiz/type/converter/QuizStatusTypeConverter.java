package mono.focusider.domain.quiz.type.converter;

import jakarta.persistence.Converter;
import mono.focusider.domain.quiz.type.QuizStatusType;
import mono.focusider.global.utils.enm.AbstractEnumCodeAttributeConverter;

@Converter
public class QuizStatusTypeConverter extends AbstractEnumCodeAttributeConverter<QuizStatusType> {
    public QuizStatusTypeConverter() {
        super(QuizStatusType.class);
    }
}
