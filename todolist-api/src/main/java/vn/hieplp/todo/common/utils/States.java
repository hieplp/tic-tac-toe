package vn.hieplp.todo.common.utils;

import java.util.Collection;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 29/04/2022
 * Time: 16:03
 */
public class States {
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }


    public static boolean isNullOrEmpty(String str) {
        return isNull(str) || str.isEmpty();
    }

    public static boolean isNotNullOrEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return isNull(collection) || collection.isEmpty();
    }

    public static boolean isNotNullOrEmpty(Collection<?> collection) {
        return !isNullOrEmpty(collection);
    }
}
