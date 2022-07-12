package vn.hieplp.todo.common.exceptions;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 27/04/2022
 * Time: 16:05
 */
public class BaseException extends RuntimeException {
    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
