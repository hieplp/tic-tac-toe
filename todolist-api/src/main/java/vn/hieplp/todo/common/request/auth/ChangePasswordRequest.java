package vn.hieplp.todo.common.request.auth;

import lombok.Data;
import vn.hieplp.todo.common.annotations.NotNullOrEmpty;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 10/05/2022
 * Time: 15:04
 */
@Data
public class ChangePasswordRequest {
    private String userId;
    @NotNullOrEmpty
    private String oldPassword;
    @NotNullOrEmpty
    private String newPassword;
}
