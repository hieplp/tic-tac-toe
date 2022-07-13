package com.hieplp.tictactoe.common.exception;

public class CommonException {
    public static class NotSupportedException extends BaseException {
        public NotSupportedException(String message) {
            super(message);
        }
    }

    public static class InvalidConfig extends BaseException {
        public InvalidConfig(String message) {
            super(message);
        }
    }

    public static class NotFoundException extends BaseException {
        public NotFoundException(String message) {
            super(message);
        }
    }
}
