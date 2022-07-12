package vn.hieplp.todo.common.utils;

import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.exceptions.CommonException;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 27/04/2022
 * Time: 16:29
 */
public class Config {
    private static final Logger LOGGER = LogManager.getLogger(Config.class);

    public static <T> T loadConfig(String configStr, Class<T> clazz) {
        LOGGER.info("Start loading config");
        try {
            T config = JsonConverter.fromJson(configStr, clazz);
            LOGGER.info("Config after load: {}", new GsonBuilder()
                    .setPrettyPrinting().create()
                    .toJson((config)));
            return config;
        } catch (Exception e) {
            LOGGER.error("Error when load config {}", e.getMessage());
            throw new CommonException.InvalidConfig("Invalid config");
        }
    }
}
