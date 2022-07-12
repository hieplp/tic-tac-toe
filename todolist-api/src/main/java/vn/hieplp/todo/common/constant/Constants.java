package vn.hieplp.todo.common.constant;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 1/5/2022
 * Time: 22:9
 **/
public class Constants {
    public static class Token {
        public static final String PRIVATE_AUTH = "AUTH";
        public static final String PRIVATE_TOKEN = "JWT";
        public static final String PUBLIC_TOKEN = "PUBLIC_TOKEN";
    }

    public static class JWTHeader {
        public static final String USER = "user";
    }

    public static class Template {
        public static final String FORGOT_PASSWORD = "FORGOT_PASSWORD";
    }

    public static class Path {
        public static class User {
            public static final String EMAIL = "email";
            public static final String REQUEST_ID = "requestId";
        }

        public static class Note {
            public static final String NOTE_ID = "noteId";
        }

        public static class Task {
            public static final String TASK_ID = "taskId";
        }
    }
}
