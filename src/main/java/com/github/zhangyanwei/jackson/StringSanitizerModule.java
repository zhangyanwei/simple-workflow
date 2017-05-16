package com.github.zhangyanwei.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Strings;

import java.io.IOException;

public class StringSanitizerModule extends SimpleModule {

    private static final long serialVersionUID = 371010759871570629L;

    public StringSanitizerModule() {
        addDeserializer(String.class, new StdDeserializer<String>(String.class) {
            private static final long serialVersionUID = 9006456331344414499L;
            @Override
            public String deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
                return Strings.emptyToNull(jsonParser.getValueAsString().trim());
            }
        });
    }
}