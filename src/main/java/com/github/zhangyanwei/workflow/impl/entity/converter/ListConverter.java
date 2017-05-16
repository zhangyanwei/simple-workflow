package com.github.zhangyanwei.workflow.impl.entity.converter;

import java.util.List;

public class ListConverter extends JsonConverter<List> {
    @Override
    protected Class<List> type() {
        return List.class;
    }
}