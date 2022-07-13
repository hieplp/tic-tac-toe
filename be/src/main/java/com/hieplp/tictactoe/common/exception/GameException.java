package com.hieplp.tictactoe.common.exception;

public class GameException {
    public static class OnGoingException extends BaseException {
        public OnGoingException(String message) {
            super(message);
        }
    }

    public static class JoinOwnGameException extends BaseException {
        public JoinOwnGameException(String message) {
            super(message);
        }
    }

    public static class NotOnGoingException extends BaseException {
        public NotOnGoingException(String message) {
            super(message);
        }
    }

    public static class NotUserTurnException extends BaseException {
        public NotUserTurnException(String message) {
            super(message);
        }
    }

}
