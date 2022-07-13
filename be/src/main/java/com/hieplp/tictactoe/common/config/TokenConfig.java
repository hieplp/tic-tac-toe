package com.hieplp.tictactoe.common.config;

import lombok.Data;

@Data
public class TokenConfig {
    private Integer timeToLive;
    private String issuer;
    private String privateKey;
}
