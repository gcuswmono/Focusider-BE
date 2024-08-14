package mono.focusider.domain.category.type.converter;

import mono.focusider.domain.category.type.CategoryType;
import mono.focusider.global.enm.AbstractEnumCodeAttributeConverter;

public class CategoryTypeConverter extends AbstractEnumCodeAttributeConverter<CategoryType> {
    public CategoryTypeConverter(Class<CategoryType> targetEnumClass) {
        super(targetEnumClass);
    }
}
