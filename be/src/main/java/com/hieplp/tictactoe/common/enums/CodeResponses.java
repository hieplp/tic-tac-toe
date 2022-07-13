package com.hieplp.tictactoe.common.enums;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 11/07/2022
 * Time: 14:02
 */
public class CodeResponses {
    public enum SuccessCode {
        SUCCESS("200", "Success"),
        ;
        private final String code;
        private final String message;

        SuccessCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum ErrorCode {
        // Common
        NOT_FOUND("404", "Not found error"),
        // Auth
        UNAUTHORIZED("AUTH_4000", "Unauthorized"),
        // Game
        GAME_NOT_STATED("GAME_4000", "Game is not started"),
        NOT_USER_TURN("GAME_4001", "Not user turn"),
        JOIN_OWN_GAME("GAME_4002", "User joined his own games"),
        GAME_NOT_FOUND("GAME_4003", "Game not found"),
        ;

        private final String code;
        private final String message;

        ErrorCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
