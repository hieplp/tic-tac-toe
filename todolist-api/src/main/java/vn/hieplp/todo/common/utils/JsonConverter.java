package vn.hieplp.todo.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import io.vertx.core.MultiMap;
import vn.hieplp.todo.common.exceptions.CommonException;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 27/04/2022
 * Time: 16:36
 */
public class JsonConverter {
    private static Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .setDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            .registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter())
            .create();

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().disableHtmlEscaping().create();
        }
        return gson;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return getGson().fromJson(json, clazz);
    }

    public static <T> T fromJson(JsonElement jsonElement, Class<T> clazz) {
        return getGson().fromJson(jsonElement, clazz);
    }

    public static <T> List<T> fromJsonToList(JsonElement jsonElement, Class<T[]> clazz) {
        T[] array = getGson().fromJson(jsonElement, clazz);
        return Arrays.asList(array);
    }

    public static String toJson(Object obj) {
        return getGson().toJson(obj);
    }

    public static JsonElement toJsonElement(Object obj) {
        return JsonParser.parseString(toJson(obj));
    }

    public static JsonElement toJsonElement(String json) {
        return JsonParser.parseString(json);
    }

    public static JsonObject fromMultiMap(MultiMap map) throws CommonException.ValidationException {
        JsonObject result = new JsonObject();
        if (map == null) {
            throw new CommonException.ValidationException("Multi map is null");
        }
        for (Map.Entry<String, String> entry : map.entries()) {
            result.addProperty(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static <T> T fromMap(Map<?, ?> map, Class<T> clazz) throws CommonException.ValidationException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, clazz);
    }

    private static class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.getDecoder().decode(json.getAsString());
        }

        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.getEncoder().encodeToString(src));
        }
    }

}

