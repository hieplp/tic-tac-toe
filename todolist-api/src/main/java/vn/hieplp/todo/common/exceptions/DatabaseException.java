package vn.hieplp.todo.common.exceptions;

public class DatabaseException {
    public static class QueryException extends BaseException {
        public QueryException(String msg) {
            super(msg);
        }

        public QueryException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    public static class NotFoundException extends BaseException {

        public NotFoundException(String msg) {
            super(msg);
        }

        public NotFoundException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    public static class DuplicateException extends BaseException {

        public DuplicateException(String msg) {
            super(msg);
        }

        public DuplicateException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }
}
