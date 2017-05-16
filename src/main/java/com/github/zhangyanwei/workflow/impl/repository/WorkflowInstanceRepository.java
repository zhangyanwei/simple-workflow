package com.github.zhangyanwei.workflow.impl.repository;

import com.github.zhangyanwei.workflow.impl.entity.WorkflowInstance;
import com.github.zhangyanwei.workflow.impl.entity.DataType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WorkflowInstanceRepository extends CrudRepository<WorkflowInstance, Long> {
    Optional<WorkflowInstance> findByRelatedDataAndRelatedDataType(Long dataId, DataType type);
}
