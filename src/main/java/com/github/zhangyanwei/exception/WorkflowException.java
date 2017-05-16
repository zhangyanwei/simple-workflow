package com.github.zhangyanwei.exception;

import static java.lang.String.format;

public abstract class WorkflowException extends AbstractException {

    private static final long serialVersionUID = -6045759006673606027L;

    public WorkflowException(Message message) {
        super(message);
    }

    public WorkflowException(Enum<?> code) {
        super(code);
    }

    public WorkflowException(Enum<?> code, String message) {
        super(code, message);
    }

    public WorkflowException(Enum<?> code, Throwable cause) {
        super(code, cause);
    }

    @Override
    protected final String messageResourceBaseName() {
        return "exception.workflow";
    }

    @Override
    protected final String moduleName() {
        return format("WKF_%s", reasonCode());
    }

    protected abstract String reasonCode();
}
