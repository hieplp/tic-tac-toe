package com.hieplp.tictactoe.common.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 23/06/2022
 * Time: 15:20
 */
@Data
@Builder
public class GameModel {
    private String gameId;
    private String ownerId;
    private String opponentId;
    private String gameStatus;
    private Map<Integer, List<GameItemModel>> items;
}
