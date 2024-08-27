package mono.focusider.domain.category.type.converter;

import jakarta.persistence.Converter;
import mono.focusider.domain.category.type.CategoryType;
import mono.focusider.global.utils.enm.AbstractEnumCodeAttributeConverter;

@Converter
public class CategoryTypeConverter extends AbstractEnumCodeAttributeConverter<CategoryType> {

    public CategoryTypeConverter() {
        super(CategoryType.class);
    }
}
