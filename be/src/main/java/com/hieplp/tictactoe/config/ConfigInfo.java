package com.hieplp.tictactoe.config;

import com.hieplp.tictactoe.common.config.GRPCConfig;
import com.hieplp.tictactoe.common.config.TokenConfig;
import lombok.Data;

@Data
public class ConfigInfo {
    private GRPCConfig grpcConfig;
    private TokenConfig tokenConfig;
}
