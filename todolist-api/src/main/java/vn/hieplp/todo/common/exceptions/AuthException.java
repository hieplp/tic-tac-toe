package vn.hieplp.todo.common.exceptions;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 05/05/2022
 * Time: 16:28
 */
public class AuthException {
    public static class InvalidPasswordException extends BaseException {

        public InvalidPasswordException(String msg) {
            super(msg);
        }

        public InvalidPasswordException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    public static class InvalidTokenException extends BaseException {

        public InvalidTokenException(String msg) {
            super(msg);
        }

        public InvalidTokenException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }
}
