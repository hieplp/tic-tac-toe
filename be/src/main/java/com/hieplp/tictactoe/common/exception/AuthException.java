package com.hieplp.tictactoe.common.exception;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 23/06/2022
 * Time: 11:24
 */
public class AuthException {
    public static class InvalidTokenException extends BaseException {

        public InvalidTokenException(String message) {
            super(message);
        }

        public InvalidTokenException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class NotFoundUserException extends BaseException {

        public NotFoundUserException(String message) {
            super(message);
        }

        public NotFoundUserException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
