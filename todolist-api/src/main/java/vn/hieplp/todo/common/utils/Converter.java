package vn.hieplp.todo.common.utils;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 15:59
 */
public class Converter {
    public static char[] toCharArray(byte[] bytes) {
        // Using the platform's default charset
        String str = new String(bytes);
        return str.toCharArray();
    }
}
