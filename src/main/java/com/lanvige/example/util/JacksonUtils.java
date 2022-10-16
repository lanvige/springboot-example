package com.lanvige.example.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Slf4j
public class JacksonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public JacksonUtils() {
        // 禁止时间格式序列化为时间戳
        if (objectMapper == null) {
            ObjectMapper objectMapper2 = new ObjectMapper()
                    .findAndRegisterModules()
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
    }


    // JSONNode 无法创建，只能通过读取获得，并不可修改

    // ObjectNode 可以直接创建，并赋值
    public static ObjectNode createObjectNode() {
        ObjectNode newObjectNode = objectMapper.createObjectNode();

        return newObjectNode;
    }


    public static ArrayNode createArrayNode() {
        ArrayNode newArrayNode = objectMapper.createArrayNode();

        return newArrayNode;
    }


    // ---------------------------- to 是将 Obj 序列化为 JSON ---------------------------- //

    public static String toJsonStr(Object obj) {
        return toJsonStr(obj, PropertyNamingStrategies.SNAKE_CASE);
    }

    public static String toJsonStr(Object obj, PropertyNamingStrategy propertyNamingStrategy) {
        objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String toJsonStr(Object obj, String dateFormatPattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);

        try {
            return objectMapper.writer(dateFormat).writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    // ---------------------------- parse 是将 Json/Obj 反序列为 JsonNode ---------------------------- //

    public static <T> T parse(String jsonStr, Class<T> clazz) {
        return parse(jsonStr, clazz, PropertyNamingStrategies.SNAKE_CASE);
    }

    public static <T> T parse(String jsonStr, Class<T> clazz, PropertyNamingStrategy propertyNamingStrategy) {
        objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static JsonNode parseJsonNode(Object obj) {
        return parseJsonNode(obj, PropertyNamingStrategies.SNAKE_CASE);
    }

    public static JsonNode parseJsonNode(Object obj, PropertyNamingStrategy propertyNamingStrategy) {
        objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);

        JsonNode jsonNode = objectMapper.valueToTree(obj);

        return jsonNode;
    }

    public static JsonNode parseJsonNode2(Object obj, PropertyNamingStrategy propertyNamingStrategy) {
        objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);

        JsonNode jsonNode = objectMapper.convertValue(obj, JsonNode.class);

        return jsonNode;
    }

    public static JsonNode parseJsonNode(String jsonStr) {
        return parseJsonNode(jsonStr, PropertyNamingStrategies.SNAKE_CASE);
    }

    public static JsonNode parseJsonNode(String jsonStr, PropertyNamingStrategy propertyNamingStrategy) {
        objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);

        // Include.ALWAYS 是序列化对像所有属性
        // Include.NON_NULL 只有不为 null 的字段才被序列化
        // Include.NON_EMPTY 如果为 null 或者 空字符串和空集合都不会被序列化
        // 序列化时，序列化所有属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 序列化时，如果是空对象，不抛出异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 反序列化时，如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 格式化时间为时间戳
        // 取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
        // objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        try {
            JsonNode actualObj = objectMapper.readTree(jsonStr);
            return actualObj;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ObjectNode parseObjectNode(Object obj) {
        return parseObjectNode(obj, PropertyNamingStrategies.SNAKE_CASE);
    }

    public static ObjectNode parseObjectNode(Object obj, PropertyNamingStrategy propertyNamingStrategy) {
        JsonNode jsonNode = parseJsonNode(obj, propertyNamingStrategy);

        return convertObjectNode(jsonNode);
    }


    public static ObjectNode parseObjectNode(String jsonStr) {
        return parseObjectNode(jsonStr, PropertyNamingStrategies.SNAKE_CASE);
    }

    public static ObjectNode parseObjectNode(String jsonStr, PropertyNamingStrategy propertyNamingStrategy) {
        JsonNode jsonNode = parseJsonNode(jsonStr, propertyNamingStrategy);

        return convertObjectNode(jsonNode);
    }

    public static ArrayNode parseArrayNode(Object obj) {
        JsonNode jsonNode = parseJsonNode(obj, PropertyNamingStrategies.SNAKE_CASE);

        return convertArrayNode(jsonNode);
    }

    public static ArrayNode parseArrayNode(Object obj, PropertyNamingStrategy propertyNamingStrategy) {
        JsonNode jsonNode = parseJsonNode(obj, propertyNamingStrategy);

        return convertArrayNode(jsonNode);
    }

    public static ArrayNode parseArrayNode(String jsonStr) {
        return parseArrayNode(jsonStr, PropertyNamingStrategies.SNAKE_CASE);
    }

    public static ArrayNode parseArrayNode(String jsonStr, PropertyNamingStrategy propertyNamingStrategy) {
        JsonNode jsonNode = parseJsonNode(jsonStr, PropertyNamingStrategies.SNAKE_CASE);

        return convertArrayNode(jsonNode);
    }

    @Deprecated
    public static List parseList(String jsonArrayStr) {
        try {
            return objectMapper.readValue(jsonArrayStr, List.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public static <T> List<T> parseList(String jsonArrayStr, Type type) {
        TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {
            @Override
            public Type getType() {
                return type;
            }
        };

        try {
            return (List<T>) objectMapper.readValue(jsonArrayStr, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> parseList(String jsonArrayStr, Class<T> clazz) {
        return parseList(jsonArrayStr, clazz, PropertyNamingStrategies.SNAKE_CASE);
    }

    /**
     * parseList
     * 反序列化（JSON 转 List<T>）
     *
     * @param jsonArrayStr
     * @param clazz
     * @return java.util.List<E>
     */
    public static <T> List<T> parseList(String jsonArrayStr, Class<T> clazz, PropertyNamingStrategy propertyNamingStrategy) {
        objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);

            return objectMapper.readValue(jsonArrayStr, collectionType);
        } catch (IOException e) {
            log.error("JSON 解析出错：" + jsonArrayStr, e);
            return null;
        }
    }

    public static Map parseMap(String jsonStr) {
        try {
            return objectMapper.readValue(jsonStr, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <K, V> Map<K, V> parseMap(String jsonStr, Class<K> kClass, Class<V> vClass) {
        return parseMap(jsonStr, kClass, vClass, PropertyNamingStrategies.SNAKE_CASE);
    }

    /**
     * 转为 <Key, Value> 的 Map
     *
     * @param jsonStr
     * @param kClass  Key 的类型
     * @param vClass  Value 的类型
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> parseMap(String jsonStr, Class<K> kClass, Class<V> vClass, PropertyNamingStrategy propertyNamingStrategy) {
        objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            MapType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, kClass, vClass);

            return objectMapper.readValue(jsonStr, mapType);
        } catch (IOException e) {
            log.error("json 解析出错：" + jsonStr, e);
            return null;
        }
    }

    public static <K, V> Map<K, V> parseMap(String jsonStr, Type type) {
        return parseMap(jsonStr, type, PropertyNamingStrategies.SNAKE_CASE);
    }

    public static <K, V> Map<K, V> parseMap(String jsonStr, Type type, PropertyNamingStrategy propertyNamingStrategy) {
        objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TypeReference<Map<K, V>> typeReference = new TypeReference<>() {
            @Override
            public Type getType() {
                return type;
            }
        };

        try {
            return objectMapper.readValue(jsonStr, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T parseNative(String jsonStr, TypeReference<T> type) {
        return parseNative(jsonStr, type, PropertyNamingStrategies.SNAKE_CASE);
    }

    /**
     * json 转复杂对象
     *
     * @param jsonStr
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T parseNative(String jsonStr, TypeReference<T> type, PropertyNamingStrategy propertyNamingStrategy) {
        objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return objectMapper.readValue(jsonStr, type);
        } catch (IOException e) {
            log.error("json 解析出错：" + jsonStr, e);
            return null;
        }
    }


    // ---------------------------- convert 是将 JsonNode 处理为其它 JsonNode/Object ---------------------------- //

    public static ObjectNode convertObjectNode(JsonNode jsonNode) {
        if (jsonNode.isEmpty()) {
            return null;
        }

        return (ObjectNode) jsonNode;
    }

    public static ArrayNode convertArrayNode(JsonNode jsonNode) {
        if (jsonNode.isEmpty()) {
            return null;
        }

        if (jsonNode.isArray()) {
            return (ArrayNode) jsonNode;
        }

        return null;
    }

    public static <T> T convert(JsonNode jsonNode, Class<T> clazz) {
        return convert(jsonNode, clazz, PropertyNamingStrategies.SNAKE_CASE);
    }

    public static <T> T convert(JsonNode jsonNode, Class<T> clazz, PropertyNamingStrategy propertyNamingStrategy) {
        objectMapper.setPropertyNamingStrategy(propertyNamingStrategy);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return objectMapper.treeToValue(jsonNode, clazz);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static JsonNode convertJsonNode(Object obj) {
        return objectMapper.valueToTree(obj);
    }


    public static <T> List<T> convertList(JsonNode jsonNode, Class<T> clazz) {
        return convertList(jsonNode, clazz, PropertyNamingStrategies.SNAKE_CASE);
    }

    public static <T> List<T> convertList(JsonNode jsonNode, Class<T> clazz, PropertyNamingStrategy propertyNamingStrategy) {
        if (jsonNode.isArray()) {
            String jsonArrayStr = jsonNode.toString();
            return parseList(jsonArrayStr, clazz, propertyNamingStrategy);
        }

        return null;
    }


    public static <K, V> Map<K, V> convertMap2(Object fromValue) {
        return objectMapper.convertValue(fromValue, Map.class);
    }

    public static Map<String, Object> convertMap(JsonNode jsonNode) {
        // Map<String, Object> result = objectMapper.convertValue(jsonNode, new TypeReference<Map<String, Object>>(){});
        Map<String, Object> result = convertMap(jsonNode, String.class, Object.class);
        return result;
    }

    public static <K, V> Map<K, V> convertMap(JsonNode jsonNode, Class<K> kClass, Class<V> vClass) {
        try {
            MapType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, kClass, vClass);
            return objectMapper.convertValue(jsonNode, mapType);
        } catch (IllegalArgumentException ex) {
            log.error("json 解析出错：" + jsonNode, ex);
            return null;
        }
    }

}
