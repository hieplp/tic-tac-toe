package vn.hieplp.todo.common.request.auth;

import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 16:39
 */
@Data
public class LoginRequest {
    private String email;
    private String password;
}
