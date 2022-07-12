package vn.hieplp.todo.common.handler.impl;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import vn.hieplp.todo.common.config.ConfigInfo;
import vn.hieplp.todo.common.config.RedisConfig;
import vn.hieplp.todo.common.exceptions.CommonException;
import vn.hieplp.todo.common.exceptions.DatabaseException;
import vn.hieplp.todo.common.handler.IRedisHandler;
import vn.hieplp.todo.common.utils.JsonConverter;
import vn.hieplp.todo.common.utils.States;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 10/05/2022
 * Time: 09:43
 */
public class RedisHandlerImpl implements IRedisHandler {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final ConfigInfo configInfo;

    private JedisPool pool = null;

    @Inject
    public RedisHandlerImpl(ConfigInfo configInfo) {
        this.configInfo = configInfo;
        this.init(this.configInfo.getRedisConfig());
    }

    @Override
    public void init(RedisConfig redisConfig) {
        LOGGER.info("Start init redis with config {}", JsonConverter.toJson(redisConfig));
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(32);
        poolConfig.setMaxIdle(16);
        poolConfig.setMinIdle(4);
        poolConfig.setTestOnBorrow(true);
        pool = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort());
    }

    @Override
    public void save(String key, String value, Integer expireTime) {
        LOGGER.info("Save to redis with key {}, value {} and expireTime {}", key, value, expireTime);
        try (Jedis client = pool.getResource()) {
            client.setex(key, expireTime, value);
        } catch (Exception e) {
            LOGGER.error("Error on saving Ex to request: {}", e.getMessage());
            throw new CommonException.RedisConnectionException("Save key with Ex to redis failed");
        }
    }

    @Override
    public void delete(String key) {
        LOGGER.info("Delete key {} from redis", key);
        try (Jedis client = pool.getResource()) {
            client.del(key);
        } catch (Exception e) {
            LOGGER.error("Error on deleting key: {}", e.getMessage());
            throw new CommonException.RedisConnectionException("Delete key failed");
        }
    }

    @Override
    public String getValueNull(String key) {
        LOGGER.info("Get null redis with key {}", key);
        try (Jedis client = pool.getResource()) {
            return client.get(key);
        } catch (Exception e) {
            LOGGER.error("Error on getting redis value with key: {}", e.getMessage());
            throw new CommonException.RedisConnectionException("Get redis value with key failed");
        }
    }

    @Override
    public String getValueNotNull(String key) {
        LOGGER.info("Get not null redis with key {}", key);
        try (Jedis client = pool.getResource()) {
            String value = client.get(key);
            if (States.isNull(value)) {
                throw new DatabaseException.NotFoundException("Redis not found error");
            }
            return value;
        } catch (DatabaseException.NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error on getting redis value with key: {}", e.getMessage());
            throw new CommonException.RedisConnectionException("Get redis value with key failed");
        }
    }
}
