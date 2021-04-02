package com.cinus.xml;

import com.cinus.thirdparty.Assert;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;


public final class XmlUtils {


    private static final XmlMapper XML_MAPPER;

    static {
        XML_MAPPER = new XmlMapper();
        XML_MAPPER.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        XML_MAPPER.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    }

    public static String toXml(Object value) throws JsonProcessingException {
        Assert.notNull(value, "[Assertion failed] - value is required; it must not be null");
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(true);
        XmlMapper xmlMapper = new XmlMapper(module);
        return xmlMapper.writer().withRootName("xml").writeValueAsString(value);
    }


    public static <T> T toObject(String xml, Class<T> valueType) throws IOException {
        Assert.hasText(xml, "[Assertion failed] - xml must have text; it must not be null, empty, or blank");
        Assert.notNull(valueType, "[Assertion failed] - valueType is required; it must not be null");
        return XML_MAPPER.readValue(xml, valueType);
    }


    public static <T> T toObject(String xml, TypeReference<?> typeReference) throws IOException {
        Assert.hasText(xml, "[Assertion failed] - xml must have text; it must not be null, empty, or blank");
        Assert.notNull(typeReference, "[Assertion failed] - typeReference is required; it must not be null");
        return XML_MAPPER.readValue(xml, typeReference);
    }


    public static <T> T toObject(String xml, JavaType javaType) throws IOException {
        Assert.hasText(xml, "[Assertion failed] - xml must have text; it must not be null, empty, or blank");
        Assert.notNull(javaType, "[Assertion failed] - javaType is required; it must not be null");
        return XML_MAPPER.readValue(xml, javaType);
    }


    public static JsonNode toTree(String xml) throws IOException {
        Assert.hasText(xml, "[Assertion failed] - xml must have text; it must not be null, empty, or blank");
        return XML_MAPPER.readTree(xml);
    }

    public static void writeValue(Writer writer, Object value) throws IOException {
        Assert.notNull(writer, "[Assertion failed] - writer is required; it must not be null");
        Assert.notNull(value, "[Assertion failed] - value is required; it must not be null");
        XML_MAPPER.writeValue(writer, value);
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