package com.hieplp.tictactoe.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 16/06/2022
 * Time: 14:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String userId;
    private String userName;
    private Integer userStatus;
}
