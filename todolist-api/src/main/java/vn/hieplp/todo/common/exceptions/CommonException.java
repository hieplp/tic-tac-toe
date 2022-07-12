package vn.hieplp.todo.common.exceptions;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 27/04/2022
 * Time: 16:04
 */
public class CommonException {
    public static class InvalidConfig extends BaseException {

        public InvalidConfig(String msg) {
            super(msg);
        }

        public InvalidConfig(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    public static class EncryptionException extends BaseException {

        public EncryptionException(String msg) {
            super(msg);
        }

        public EncryptionException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    public static class ValidationException extends BaseException {

        public ValidationException(String msg) {
            super(msg);
        }

        public ValidationException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    public static class EmailException extends BaseException {

        public EmailException(String msg) {
            super(msg);
        }

        public EmailException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    public static class RedisConnectionException extends BaseException {

        public RedisConnectionException(String msg) {
            super(msg);
        }

        public RedisConnectionException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }
}
