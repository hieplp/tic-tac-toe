package com.hieplp.tictactoe.common.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/06/2022
 * Time: 16:40
 */
@Data
@Builder
public class JoinedGameModel {
    private String userId;
    private List<String> games;
}
