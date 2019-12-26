package com.nevzatcirak.example.oauth2.util;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.type.SimpleType;

import java.io.*;
import java.lang.annotation.Annotation;

/**
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 25.12.2019.
 */
public class CustomObjectMapper extends ObjectMapper {

    protected final static JavaType JSON_NODE_TYPE = SimpleType.constructUnsafe(JsonNode.class);

    public CustomObjectMapper() {
        super();
    }

    public CustomObjectMapper(JsonFactory jf, DefaultSerializerProvider sp, DefaultDeserializationContext dc) {
        super(jf, sp, dc);
    }

    public CustomObjectMapper(JsonFactory jf) {
        super(jf);
    }

    public CustomObjectMapper(ObjectMapper src) {
        super(src);
    }

    protected void autoconfigureFeatures(Object value) {
        if(value != null) {
            JavaType javaType = _typeFactory.constructType(value.getClass());
            autoconfigureFeatures(javaType);
        }
    }

    protected void autoconfigureFeatures(JavaType javaType) {
        Annotation jsonRootNameAnnotation = javaType.getRawClass().getAnnotation(JsonRootName.class);
        boolean enabled = jsonRootNameAnnotation != null;
        this.configure(SerializationFeature.WRAP_ROOT_VALUE, enabled);
        this.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, enabled);
    }

    protected void autoconfigureFeatures(DeserializationConfig config, JavaType javaType) {
        Annotation jsonRootNameAnnotation = javaType.getRawClass().getAnnotation(JsonRootName.class);
        boolean enabled = jsonRootNameAnnotation != null;
        if(enabled) {
            config.with(DeserializationFeature.UNWRAP_ROOT_VALUE);
        } else {
            config.without(DeserializationFeature.UNWRAP_ROOT_VALUE);
        }
    }

    protected void autoconfigureFeatures(SerializationConfig config, JavaType javaType) {
        Annotation jsonRootNameAnnotation = javaType.getRawClass().getAnnotation(JsonRootName.class);
        boolean enabled = jsonRootNameAnnotation != null;
        if(enabled) {
            config.with(SerializationFeature.WRAP_ROOT_VALUE);
        } else {
            config.without(SerializationFeature.WRAP_ROOT_VALUE);
        }
    }

    @Override
    protected ObjectReader _newReader(DeserializationConfig config, JavaType valueType, Object valueToUpdate,
                                      FormatSchema schema, InjectableValues injectableValues) {
        autoconfigureFeatures(config, valueType);
        return super._newReader(config, valueType, valueToUpdate, schema, injectableValues);
    }

    @Override
    protected ObjectWriter _newWriter(SerializationConfig config, JavaType rootType, PrettyPrinter pp) {
        autoconfigureFeatures(config, rootType);
        return super._newWriter(config, rootType, pp);
    }

    @Override
    protected JsonNode _readTreeAndClose(JsonParser p0) throws IOException {
        JavaType valueType = JSON_NODE_TYPE;
        autoconfigureFeatures(valueType);
        return super._readTreeAndClose(p0);
    }

    @Override
    protected Object _readValue(DeserializationConfig cfg, JsonParser p, JavaType valueType) throws IOException {
        autoconfigureFeatures(cfg, valueType);
        return super._readValue(cfg, p, valueType);
    }

    @Override
    public <T> MappingIterator<T> readValues(JsonParser p, JavaType valueType)
            throws IOException, JsonProcessingException {
        autoconfigureFeatures(valueType);
        return super.readValues(p, valueType);
    }

    @Override
    protected Object _readMapAndClose(JsonParser p0, JavaType valueType) throws IOException {
        autoconfigureFeatures(valueType);
        return super._readMapAndClose(p0, valueType);
    }

    @Override
    public void writeValue(JsonGenerator g, Object value)
            throws IOException, JsonGenerationException, JsonMappingException {
        autoconfigureFeatures(value);
        super.writeValue(g, value);
    }

    @Override
    public void writeTree(JsonGenerator jgen, TreeNode rootNode) throws IOException, JsonProcessingException {
        autoconfigureFeatures(rootNode);
        super.writeTree(jgen, rootNode);
    }

    @Override
    public void writeTree(JsonGenerator jgen, JsonNode rootNode) throws IOException, JsonProcessingException {
        autoconfigureFeatures(rootNode);
        super.writeTree(jgen, rootNode);
    }

    @Override
    public void writeValue(File resultFile, Object value)
            throws IOException, JsonGenerationException, JsonMappingException {
        autoconfigureFeatures(value);
        super.writeValue(resultFile, value);
    }

    @Override
    public void writeValue(OutputStream out, Object value)
            throws IOException, JsonGenerationException, JsonMappingException {
        autoconfigureFeatures(value);
        super.writeValue(out, value);
    }

    @Override
    public void writeValue(DataOutput out, Object value) throws IOException {
        autoconfigureFeatures(value);
        super.writeValue(out, value);
    }

    @Override
    public void writeValue(Writer w, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        autoconfigureFeatures(value);
        super.writeValue(w, value);
    }

    @Override
    public String writeValueAsString(Object value) throws JsonProcessingException {
        autoconfigureFeatures(value);
        return super.writeValueAsString(value);
    }

    @Override
    public byte[] writeValueAsBytes(Object value) throws JsonProcessingException {
        autoconfigureFeatures(value);
        return super.writeValueAsBytes(value);
    }

}
