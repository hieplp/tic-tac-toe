package vn.hieplp.todo.common.models;

import lombok.Builder;
import lombok.Data;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 1/5/2022
 * Time: 20:30
 **/
@Data
@Builder
public class TokenModel {
    private String token;
    private Long expiredAt;
}
