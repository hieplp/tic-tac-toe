package vn.hieplp.todo.common.handler;

import vn.hieplp.todo.common.config.RedisConfig;

public interface IRedisHandler {
    void init(RedisConfig redisConfig);

    void save(String key, String value, Integer expireTime);

    void delete(String key);

    String getValueNull(String key);

    String getValueNotNull(String key);
}
