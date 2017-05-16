package com.github.zhangyanwei.workflow.definition.builder.dsl;

import com.github.zhangyanwei.workflow.Querier;

public interface Query extends Link {
    Link query(Class<? extends Querier> querierType);
}
