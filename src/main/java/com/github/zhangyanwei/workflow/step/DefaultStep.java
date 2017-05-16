package com.github.zhangyanwei.workflow.step;

import com.github.zhangyanwei.exception.RequestValidateException;
import com.github.zhangyanwei.function.ServiceConsumer;
import com.github.zhangyanwei.workflow.impl.entity.WorkflowInstance;
import com.github.zhangyanwei.exception.ForbiddenException;
import com.github.zhangyanwei.exception.IllegalAccessException;
import com.github.zhangyanwei.exception.WorkflowException;
import com.github.zhangyanwei.utils.Json;
import com.github.zhangyanwei.workflow.definition.StepDefinition;
import com.github.zhangyanwei.workflow.impl.entity.StepInstance;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static com.github.zhangyanwei.exception.IllegalAccessException.Error.INSTANTIATION;
import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR;
import static org.springframework.beans.factory.config.AutowireCapableBeanFactory.AUTOWIRE_NO;

public abstract class DefaultStep<E extends Enum<E>> implements Step {

    private WorkflowInstance workflowInstance;
    private StepDefinition definition;
    private StepInstance instance;

    protected DefaultStep(WorkflowInstance workflowInstance,
                          StepDefinition stepDefinition, Long userId) {
        this.workflowInstance = workflowInstance;
        this.definition = stepDefinition;
        this.instance = from(workflowInstance, stepDefinition, userId);
    }

    public String getName() {
        return this.instance.getName();
    }

    @Override
    public void process(Long userId, String request) throws WorkflowException {
        process(request, (executorType, req) -> executeStartStep(executorType, userId, req == null ? request : req));
    }

    @Override
    public void process(Long userId, Long dataId, String request) throws WorkflowException {
        process(request, (executorType, req) -> executeNextStep(executorType, userId, dataId, req == null ? request : req));
    }

    @SuppressWarnings("unchecked")
    private void process(String request, ServiceConsumer<Class<? extends Executor>, Object, WorkflowException> consumer) throws WorkflowException {
        checkExecuteRoles(this.definition.getExecuteRoles());

        Object req = null;
        Class<?> requestType = this.definition.getRequestType();
        if (requestType != null) {
            req = Json.readValue(request, requestType);
            validatedRequest(req, requestType);
        }

        Class<? extends Executor> executorType = this.definition.getExecutorType();
        consumer.accept(executorType, req == null ? request : req);

        persistent(workflowInstance, this.instance);
    }

    private void executeStartStep(Class<? extends Executor> executorType, Long userId, Object request) throws WorkflowException {
        if (!StartStepExecutor.class.isAssignableFrom(executorType)) {
            throw new IllegalAccessException(INSTANTIATION, "the start step executor requires the StartStepExecutor");
        }

        @SuppressWarnings("unchecked")
        StartStepExecutor<Object> stepExecutor = (StartStepExecutor<Object>) beanFactory().autowire(executorType, AUTOWIRE_CONSTRUCTOR, false);
        beanFactory().autowireBeanProperties(stepExecutor, AUTOWIRE_NO, false);

        HashMap<String, String> additions = new HashMap<>();
        AdditionsAppender appender = new AdditionsAppender(additions);
        StepContext context = stepExecutor.execute(userId, request, appender);
        workflowInstance.setRelatedData(context.getDataId());
        workflowInstance.setRelatedDataType(context.getDataType());
        workflowInstance.addPassedStep(this.getName());
        instance.setAdditions(additions);
    }

    private void executeNextStep(Class<? extends Executor> executorType, Long userId, Long dataId, Object request) throws WorkflowException {
        if (!StepExecutor.class.isAssignableFrom(executorType)) {
            throw new IllegalAccessException(INSTANTIATION, "the next step executor requires the StepExecutor");
        }

        @SuppressWarnings("unchecked")
        StepExecutor<Object> stepExecutor = (StepExecutor<Object>) beanFactory().autowire(executorType, AUTOWIRE_CONSTRUCTOR, false);
        beanFactory().autowireBeanProperties(stepExecutor, AUTOWIRE_NO, false);

        HashMap<String, String> additions = new HashMap<>();
        AdditionsAppender appender = new AdditionsAppender(additions);
        stepExecutor.checkDataAccess(userId, dataId);
        stepExecutor.execute(userId, dataId, request, appender);
        workflowInstance.addPassedStep(this.getName());
        instance.setAdditions(additions);
    }

    private void validatedRequest(Object req, Class<?> requestType) {
        DataBinder dataBinder = new DataBinder(req);
        dataBinder.setValidator(beanFactory().getBean(Validator.class));
        dataBinder.validate();

        if (dataBinder.getBindingResult().hasErrors()) {
            throw new RequestValidateException(dataBinder.getBindingResult());
        }

        if (req == null) {
            throw new IllegalArgumentException(format("the input type can not convert to the expected type, expected is [%s]", requestType.getName()));
        }
    }

    protected abstract StepInstance from(WorkflowInstance workflowInstance, StepDefinition stepDefinition, Long userId);

    protected abstract void checkExecuteRoles(EnumSet<E> roles) throws ForbiddenException;

    protected abstract void persistent(WorkflowInstance workflowInstance, StepInstance stepInstance) throws WorkflowException;

    protected abstract AutowireCapableBeanFactory beanFactory();

    public static final class AdditionsAppender {

        private Map<String, String> additions;

        private AdditionsAppender(Map<String, String> additions) {
            this.additions = additions;
        }

        public void append(String key, String value) {
            additions.put(key, value);
        }

    }
}
