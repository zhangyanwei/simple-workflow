package com.github.zhangyanwei.workflow.impl;

import com.github.zhangyanwei.workflow.impl.entity.WorkflowInstance;
import com.github.zhangyanwei.workflow.impl.repository.WorkflowInstanceRepository;
import com.github.zhangyanwei.workflow.step.DefaultStep;
import com.github.zhangyanwei.exception.ForbiddenException;
import com.github.zhangyanwei.exception.WorkflowException;
import com.github.zhangyanwei.workflow.definition.StepDefinition;
import com.github.zhangyanwei.workflow.impl.entity.Role;
import com.github.zhangyanwei.workflow.impl.entity.StepInstance;
import com.github.zhangyanwei.workflow.impl.entity.User;
import com.github.zhangyanwei.workflow.impl.repository.StepInstanceRepository;
import com.github.zhangyanwei.workflow.impl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.util.Date;
import java.util.EnumSet;

import static com.github.zhangyanwei.exception.ForbiddenException.Error.EXECUTE_WORKFLOW_STEP;

public class StepImpl extends DefaultStep<Role> {

    @Autowired
    private WorkflowInstanceRepository workflowInstanceRepository;

    @Autowired
    private StepInstanceRepository stepInstanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private Long userId;

    StepImpl(WorkflowInstance workflowInstance, StepDefinition stepDefinition, Long userId) {
        super(workflowInstance, stepDefinition, userId);
        this.userId = userId;
    }

    @Override
    protected StepInstance from(WorkflowInstance workflowInstance, StepDefinition stepDefinition, Long userId) {
        StepInstance stepInstance = new StepInstance();
        stepInstance.setWorkflow(workflowInstance);
        stepInstance.setName(stepDefinition.getStepName());

        // execute user
        User user = new User();
        user.setId(userId);
        stepInstance.setUser(user);
        stepInstance.setExecuteTime(new Date());
        return stepInstance;
    }

    @Override
    protected void checkExecuteRoles(EnumSet<Role> roles) throws ForbiddenException {
        User user = userRepository.findOne(userId);
        if (!user.getAuthorities().containsAll(roles)) {
            throw new ForbiddenException(EXECUTE_WORKFLOW_STEP);
        }
    }

    @Override
    protected void persistent(WorkflowInstance workflowInstance, StepInstance stepInstance) throws WorkflowException {
        workflowInstance.setNextSteps(workflowInstance.getPassedSteps());
        workflowInstanceRepository.save(workflowInstance);
        stepInstanceRepository.save(stepInstance);
    }

    @Override
    protected AutowireCapableBeanFactory beanFactory() {
        return this.beanFactory;
    }
}
