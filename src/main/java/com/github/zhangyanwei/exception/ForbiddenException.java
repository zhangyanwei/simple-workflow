package com.github.zhangyanwei.exception;

public class ForbiddenException extends WorkflowException {

    private static final long serialVersionUID = -6353086391543989865L;

    public enum Error {
        EXECUTE_WORKFLOW_STEP
    }

    public ForbiddenException(Message message) {
        super(message);
        setCode(ForbiddenException.Error.valueOf(message.getPartial()));
    }

    public ForbiddenException(Error code) {
        super(code);
    }

    public ForbiddenException(Error code, Throwable cause) {
        super(code, cause);
    }

    @Override
    protected String reasonCode() {
        return "FORBIDDEN";
    }
}
