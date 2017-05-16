package com.github.zhangyanwei.workflow.impl;

import com.github.zhangyanwei.workflow.DefaultWorkflow;
import com.github.zhangyanwei.workflow.impl.entity.WorkflowInstance;
import com.github.zhangyanwei.workflow.definition.StepDefinition;
import com.github.zhangyanwei.workflow.definition.WorkflowDefinition;
import com.github.zhangyanwei.workflow.impl.entity.DataType;
import com.github.zhangyanwei.workflow.impl.entity.Role;
import com.github.zhangyanwei.workflow.impl.entity.User;
import com.github.zhangyanwei.workflow.step.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_NO;

@Component
@Scope("prototype")
public class WorkflowImpl extends DefaultWorkflow<DataType, Role> {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Override
    protected WorkflowInstance newInstance(WorkflowDefinition<DataType, Role> definition, Long userId) {
        User user = new User();
        user.setId(userId);
        WorkflowInstance instance = new WorkflowInstance();
        instance.setName(definition.getWorkflowName());
        instance.setUser(user);
        instance.setCreationTime(new Date());
        return instance;
    }

    @Override
    protected Step toStep(WorkflowDefinition<DataType, Role> definition, WorkflowInstance instance, String stepName, Long userId) {
        StepDefinition stepDefinition = definition.getStepDefinitions().get(stepName);
        StepImpl step = new StepImpl(instance, stepDefinition, userId);
        beanFactory.autowireBeanProperties(step, AUTOWIRE_NO, false);
        return step;
    }
}
