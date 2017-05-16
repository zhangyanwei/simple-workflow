package com.github.zhangyanwei.exception;

import static java.lang.String.format;

public abstract class WorkflowRuntimeException extends AbstractRuntimeException {

    private static final long serialVersionUID = -6045759006673606027L;

    public WorkflowRuntimeException(Message message) {
        super(message);
    }

    public WorkflowRuntimeException(Enum<?> code) {
        super(code);
    }

    public WorkflowRuntimeException(Enum<?> code, String message) {
        super(code, message);
    }

    public WorkflowRuntimeException(Enum<?> code, Throwable cause) {
        super(code, cause);
    }

    @Override
    protected final String messageResourceBaseName() {
        return "exception.workflow";
    }

    @Override
    protected final String moduleName() {
        return format("WKF_RT_%s", reasonCode());
    }

    protected abstract String reasonCode();
}
