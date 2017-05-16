package com.github.zhangyanwei.workflow.impl.entity.converter;

import javax.persistence.AttributeConverter;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.github.zhangyanwei.utils.Json.readValue;
import static com.github.zhangyanwei.utils.Json.writeValueAsString;

abstract class JsonConverter<T> implements AttributeConverter<T, String> {

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return writeValueAsString(attribute);
    }

    @Override
    public T convertToEntityAttribute(String data) {
        return isNullOrEmpty(data) ? null : readValue(data, type());
    }

    abstract protected Class<T> type();
}
