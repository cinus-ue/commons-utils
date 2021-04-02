package com.cinus.json;

import com.cinus.thirdparty.Assert;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;


public final class JsonUtils {


    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
    }

    public static String toJson(Object value) throws JsonProcessingException {
        Assert.notNull(value, "[Assertion failed] - value is required; it must not be null");
        return OBJECT_MAPPER.writeValueAsString(value);
    }


    public static <T> T toObject(String json, Class<T> valueType) throws IOException {
        Assert.hasText(json, "[Assertion failed] - json must have text; it must not be null, empty, or blank");
        Assert.notNull(valueType, "[Assertion failed] - valueType is required; it must not be null");
        return OBJECT_MAPPER.readValue(json, valueType);
    }


    public static <T> T toObject(String json, TypeReference<?> typeReference) throws IOException {
        Assert.hasText(json, "[Assertion failed] - json must have text; it must not be null, empty, or blank");
        Assert.notNull(typeReference, "[Assertion failed] - typeReference is required; it must not be null");
        return OBJECT_MAPPER.readValue(json, typeReference);
    }


    public static <T> T toObject(String json, JavaType javaType) throws IOException {
        Assert.hasText(json, "[Assertion failed] - json must have text; it must not be null, empty, or blank");
        Assert.notNull(javaType, "[Assertion failed] - javaType is required; it must not be null");
        return OBJECT_MAPPER.readValue(json, javaType);

    }


    public static JsonNode toTree(String json) throws IOException {
        Assert.hasText(json, "[Assertion failed] - json must have text; it must not be null, empty, or blank");
        return OBJECT_MAPPER.readTree(json);
    }


    public static void writeValue(Writer writer, Object value) throws IOException {
        Assert.notNull(writer, "[Assertion failed] - writer is required; it must not be null");
        Assert.notNull(value, "[Assertion failed] - value is required; it must not be null");
        OBJECT_MAPPER.writeValue(writer, value);
    }


    public static JavaType constructType(Type type) {
        Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");
        return TypeFactory.defaultInstance().constructType(type);
    }


    public static JavaType constructType(TypeReference<?> typeReference) {
        Assert.notNull(typeReference, "[Assertion failed] - typeReference is required; it must not be null");
        return TypeFactory.defaultInstance().constructType(typeReference);
    }

}