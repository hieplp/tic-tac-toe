package vn.hieplp.todo.common.models;

import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 16:46
 */
@Data
public class UserModel {
    private String userId;
    private String email;
    private String fullName;
    private Integer userStatus;
}
