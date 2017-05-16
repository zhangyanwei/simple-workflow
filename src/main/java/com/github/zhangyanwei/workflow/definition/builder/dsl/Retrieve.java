package com.github.zhangyanwei.workflow.definition.builder.dsl;

import com.github.zhangyanwei.workflow.Retriever;

public interface Retrieve extends Query {
    Query retrieve(Class<? extends Retriever> type);
}
