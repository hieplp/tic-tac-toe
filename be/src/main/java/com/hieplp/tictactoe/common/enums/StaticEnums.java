package com.hieplp.tictactoe.common.enums;

import com.hieplp.tictactoe.common.exception.CommonException;

public class StaticEnums {
    public enum UserStatus {
        ACTIVE(1),
        IN_GAME(2);

        private final Integer status;

        UserStatus(Integer status) {
            this.status = status;
        }

        public Integer getStatus() {
            return status;
        }

        public UserStatus safeValueOf(Integer status) {
            for (UserStatus userStatus : values()) {
                if (userStatus.getStatus().equals(status)) {
                    return userStatus;
                }
            }

            throw new CommonException.NotSupportedException("Not supported user status " + status);
        }
    }

    public enum GameItemType {
        UNKNOWN(-1),
        OWNER(1),
        OPPONENT(2);

        private final Integer type;

        GameItemType(Integer type) {
            this.type = type;
        }

        public Integer getType() {
            return type;
        }
    }

    public enum GameStatus {
        NOT_STARTED(-1),
        ON_GOING(0),
        FINISHED(2);

        private final Integer status;

        GameStatus(Integer status) {
            this.status = status;
        }

        public Integer getStatus() {
            return status;
        }
    }

    public enum GameRequestType {
        CREATE(1),
        JOIN(2),
        PLAY(3);

        private final Integer type;

        GameRequestType(Integer type) {
            this.type = type;
        }

        public static GameRequestType safeValuesOf(Integer type) {
            for (GameRequestType value : values()) {
                if (value.getType().equals(type)) {
                    return value;
                }
            }

            throw new CommonException.NotSupportedException("Not supported type " + type);
        }

        public Integer getType() {
            return type;
        }
    }
}
