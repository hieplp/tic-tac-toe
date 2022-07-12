package vn.hieplp.todo.common.utils;

import org.jooq.Name;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import vn.hieplp.todo.common.constant.StaticEnum;
import vn.hieplp.todo.common.exceptions.CommonException;

import java.util.Locale;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 11/05/2022
 * Time: 09:37
 */
public class SortUtil {
    public static SortField<?> generateSortField(String orderBy, String orderType) {
        Name name = DSL.name(orderBy);
        StaticEnum.OrderType by = StaticEnum.OrderType.safeValueOf(orderType.toLowerCase(Locale.ROOT));
        switch (by) {
            case DESC:
                return DSL.field(name, String.class).desc();
            case ASC:
            default:
                return DSL.field(name, String.class).asc();
        }
    }

    public static SortField<?> generateSortField(String orderBy, String orderType, SortField<?> defaultSortField) {
        if (States.isNullOrEmpty(orderBy) || States.isNull(orderType)) {
            if (States.isNull(defaultSortField)) {
                throw new CommonException.ValidationException("Invalid sort field");
            }
            return defaultSortField;
        }
        return generateSortField(orderBy, orderType);
    }
}
