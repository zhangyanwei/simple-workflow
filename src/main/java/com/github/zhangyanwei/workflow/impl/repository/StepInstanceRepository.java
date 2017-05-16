package com.github.zhangyanwei.workflow.impl.repository;

import com.github.zhangyanwei.workflow.impl.entity.StepInstance;
import com.github.zhangyanwei.workflow.impl.entity.StepInstance_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface StepInstanceRepository extends CrudRepository<StepInstance, Long>, JpaSpecificationExecutor<StepInstance> {

    class StepInstanceSpecs {
        public static Specification<StepInstance> workflow(Long workflowId) {
            return (root, query, builder) -> builder.equal(root.get(StepInstance_.workflow), workflowId);
        }
    }
}
