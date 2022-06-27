package org.example.api.web.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.example.api.common.string.EscapeUtil;

import java.io.IOException;

/**
 * Created by wjy on 2020/11/5.
 * 基于xss的JsonDeserializer
 */
public class XssStringJsonDeserializer extends JsonDeserializer<String> {


    @Override
    public Class<String> handledType() {
        return String.class;
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return EscapeUtil.escape(jsonParser.getValueAsString());
    }
} 