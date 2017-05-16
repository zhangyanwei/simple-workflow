package com.github.zhangyanwei.workflow.step;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StepContext {

    private Long dataId;
    private Enum<?> dataType;

    @JsonCreator
    public StepContext(@JsonProperty("data_id") Long dataId,
                       @JsonProperty("data_type") Enum<?> dataType) {
        this.dataId = dataId;
        this.dataType = dataType;
    }

    public Long getDataId() {
        return dataId;
    }

    public Enum<?> getDataType() {
        return dataType;
    }
}
