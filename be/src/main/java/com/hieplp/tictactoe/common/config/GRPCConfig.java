package com.hieplp.tictactoe.common.config;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 14/06/2022
 * Time: 15:11
 */
public class GRPCConfig {
    private int port;

    public int getPort() {
        return port;
    }

    public GRPCConfig setPort(int port) {
        this.port = port;
        return this;
    }
}
