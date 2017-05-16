package com.github.zhangyanwei.workflow;

import com.google.common.base.Strings;
import com.github.zhangyanwei.exception.IllegalAccessException;
import com.github.zhangyanwei.workflow.definition.RouteDefinition;
import com.github.zhangyanwei.workflow.definition.WorkflowDefinition;
import com.github.zhangyanwei.workflow.impl.entity.WorkflowInstance;
import com.github.zhangyanwei.workflow.route.RouteChecker;
import com.github.zhangyanwei.workflow.step.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.github.zhangyanwei.utils.Lists.isNullOrEmpty;
import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR;
import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_NO;

public abstract class DefaultWorkflow<T extends Enum<T>, E extends Enum<E>> implements Workflow<T, E> {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private WorkflowDefinition<T, E> definition;
    private WorkflowInstance instance;
    private Long userId;

    @Override
    public Workflow from(WorkflowDefinition<T, E> definition, Long userId) {
        this.definition = definition;
        this.userId = userId;
        this.instance = newInstance(definition, userId);
        return this;
    }

    @Override
    public Workflow from(WorkflowDefinition<T, E> definition, WorkflowInstance instance, Long userId) {
        this.definition = definition;
        this.userId = userId;
        this.instance = instance;
        return this;
    }

    @Nullable
    @Override
    public Step nextStep() {
        if (currentStep() == null) {
            Map<String, WorkflowDefinition.WorkflowLink> links = definition.getLinks();
            return toStep(definition, instance, links.keySet().iterator().next(), userId);
        }

        List<String> nextSteps = nextSteps(currentStep());
        return isNullOrEmpty(nextSteps) ? null : toStep(definition, instance, nextSteps.get(0), userId);
    }

    @Nullable
    @Override
    public Step nextStep(String stepName) {
        if (Strings.isNullOrEmpty(stepName)) {
            return nextStep();
        }

        List<String> steps = nextSteps(currentStep());
        if (!isNullOrEmpty(steps) && steps.contains(stepName)) {
            return toStep(definition, instance, stepName, userId);
        }
        return null;
    }

    @Override
    @Nullable
    public Step currentStep() {
        List<String> passedSteps = this.instance.getPassedSteps();
        return isNullOrEmpty(passedSteps) ? null : toStep(definition, instance, passedSteps.get(passedSteps.size() - 1), userId);
    }

    @Override
    public List<String> nextSteps(Step step) throws IllegalAccessException {
        Map<String, RouteDefinition> routeDefinitions = this.definition.getRouteDefinitions();
        WorkflowDefinition.WorkflowLink workflowLink = this.definition.getLinks().get(step.getName());
        List<String> possibleNextStepNames = new LinkedList<>();
        if (workflowLink != null) {
            Set<String> routes = workflowLink.routes();
            for (String route : routes) {
                RouteDefinition routeDefinition = routeDefinitions.get(route);
                Class<? extends RouteChecker> checkerType = routeDefinition.getCheckerType();
                RouteChecker routeChecker = (RouteChecker) beanFactory.autowire(checkerType, AUTOWIRE_CONSTRUCTOR, false);
                beanFactory.autowireBeanProperties(routeChecker, AUTOWIRE_NO, false);
                if (routeChecker.check(instance.getRelatedData())) {
                    possibleNextStepNames.add(workflowLink.getLinkedStep(route));
                }
            }
        }

        return possibleNextStepNames;
    }

    protected abstract WorkflowInstance newInstance(WorkflowDefinition<T, E> definition, Long userId);

    protected abstract Step toStep(WorkflowDefinition<T, E> definition, WorkflowInstance instance, String stepName, Long userId);

}
