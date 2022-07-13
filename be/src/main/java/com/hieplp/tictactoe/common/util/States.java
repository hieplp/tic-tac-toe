package com.hieplp.tictactoe.common.util;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 14/06/2022
 * Time: 14:07
 */
public class States {
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isNullOrEmpty(String str) {
        if (isNull(str)) {
            return true;
        }

        return str.isEmpty();
    }
}
