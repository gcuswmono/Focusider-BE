package mono.focusider.global.utils.querydsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

public class QueryDslUtil {

    public static OrderSpecifier<?> getSortedColumn(Order order, Path<?> parent, String property) {
        Path<Object> fieldPath = Expressions.path(Object.class, parent, property);
        return new OrderSpecifier(order, fieldPath);
    }

    public static <T extends EntityPathBase<?>> OrderSpecifier<?>[] getAllOrderSpecifiers(Pageable pageable, T qEntity) {
        if (isEmpty(pageable.getSort())) return new OrderSpecifier<?>[0];
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        pageable.getSort().forEach(sort -> {
            Order order = sort.isAscending() ? Order.ASC : Order.DESC;
            String property = sort.getProperty();
            OrderSpecifier<?> orderSpecifier = getSortedColumn(order, qEntity, property);
            orders.add(orderSpecifier);
        });
        return orders.toArray(new OrderSpecifier<?>[0]);
    }
}
