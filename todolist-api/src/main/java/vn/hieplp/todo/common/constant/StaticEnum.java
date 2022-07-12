package vn.hieplp.todo.common.constant;

import vn.hieplp.todo.common.exceptions.CommonException;

public class StaticEnum {
    public enum UserStatus {
        DELETED(-1),
        ACTIVE(0);

        private final Integer status;

        UserStatus(Integer status) {
            this.status = status;
        }

        public Integer getStatus() {
            return status;
        }
    }

    public enum TaskStatus {
        DELETED(-1),
        ACTIVE(0),
        DONE(1);

        private final Integer status;

        TaskStatus(Integer status) {
            this.status = status;
        }

        public static TaskStatus safeValueOf(Integer status) {
            for (TaskStatus taskStatus : values()) {
                if (taskStatus.getStatus().equals(status)) {
                    return taskStatus;
                }
            }
            throw new CommonException.ValidationException("Not supported task status " + status);
        }

        public Integer getStatus() {
            return status;
        }
    }

    public enum NoteStatus {
        DELETED(-1),
        ACTIVE(0);

        private final Integer status;

        NoteStatus(Integer status) {
            this.status = status;
        }

        public static NoteStatus safeValueOf(Integer status) {
            for (NoteStatus noteStatus : values()) {
                if (noteStatus.getStatus().equals(status)) {
                    return noteStatus;
                }
            }
            throw new CommonException.ValidationException("Not supported note status " + status);
        }

        public Integer getStatus() {
            return status;
        }
    }

    public enum NoteIsPinned {
        NOT_PINNED(0),
        PINNED(1);

        private final Integer status;

        NoteIsPinned(Integer status) {
            this.status = status;
        }

        public static NoteIsPinned safeValueOf(Integer status) {
            for (NoteIsPinned isPinned : values()) {
                if (isPinned.getStatus().equals(status)) {
                    return isPinned;
                }
            }
            throw new CommonException.ValidationException("Not supported note isPinned " + status);
        }

        public Integer getStatus() {
            return status;
        }
    }

    public enum SendVia {
        EMAIL(1),
        ;
        private final Integer type;

        SendVia(Integer type) {
            this.type = type;
        }

        public Integer getType() {
            return type;
        }
    }

    public enum OrderType {
        ASC("asc"),
        DESC("desc");

        private final String by;

        OrderType(String by) {
            this.by = by;
        }

        public static OrderType safeValueOf(String by) {
            for (OrderType orderType : values()) {
                if (orderType.getBy().equals(by)) {
                    return orderType;
                }
            }
            throw new CommonException.ValidationException("Not supported orderBy " + by);
        }

        public String getBy() {
            return by;
        }
    }
}
