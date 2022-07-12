package vn.hieplp.todo.common.response.auth;

import lombok.Builder;
import lombok.Data;
import vn.hieplp.todo.common.models.TokenModel;
import vn.hieplp.todo.common.models.UserModel;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 16:40
 */
@Data
@Builder
public class RegisterResponse {
    private UserModel user;
    private TokenModel token;
}
