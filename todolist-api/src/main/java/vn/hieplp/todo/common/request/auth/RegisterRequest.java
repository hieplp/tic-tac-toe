package vn.hieplp.todo.common.request.auth;

import lombok.Data;
import vn.hieplp.todo.common.annotations.CanNullOrEmpty;
import vn.hieplp.todo.common.annotations.Email;
import vn.hieplp.todo.common.annotations.MinLength;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 16:39
 */
@Data
public class RegisterRequest {
    @CanNullOrEmpty
    private String userId;
    @Email
    private String email;
    @MinLength(length = 6)
    private String fullName;
    private String password;
}

