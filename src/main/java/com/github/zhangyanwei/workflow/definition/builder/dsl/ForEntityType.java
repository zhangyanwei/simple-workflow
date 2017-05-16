package com.github.zhangyanwei.workflow.definition.builder.dsl;

public interface ForEntityType<E extends Enum<E>> {
    MaintainedBy entityType(E type);
}
