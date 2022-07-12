package vn.hieplp.todo.common.config;

import lombok.Getter;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 27/04/2022
 * Time: 16:17
 */
@Getter
public class ConfigInfo {
    private ServerConfig serverConfig;
    private Integer workerPoolSize;
    private Integer workerMaxExecuteTime;
    private DatabaseConfig databaseConfig;
    private TokenConfig tokenConfig;
    private AuthConfig authConfig;
    private EmailConfig emailConfig;
    private RedisConfig redisConfig;
    private RedisExpireTime redisExpireTime;
    private FEServerConfig feServer;
    private StaticConfig staticConfig;
}
