package com.hieplp.tictactoe.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.Map;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 17/06/2022
 * Time: 10:24
 */
public class JsonConverter {
    private final static Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .create();

    public static JsonElement toJsonElement(Object obj) {
        return gson.toJsonTree(obj);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> T fromMap(Map<?, ?> map, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, clazz);
    }
}
