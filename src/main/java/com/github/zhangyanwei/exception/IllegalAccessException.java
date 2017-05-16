package com.github.zhangyanwei.exception;

public class IllegalAccessException extends WorkflowRuntimeException {

    private static final long serialVersionUID = 5480252378829674080L;

    public enum Error {
        INSTANTIATION
    }

    public IllegalAccessException(Message message) {
        super(message);
        setCode(IllegalAccessException.Error.valueOf(message.getPartial()));
    }

    public IllegalAccessException(Error code) {
        super(code);
    }

    public IllegalAccessException(Error code, String message) {
        super(code, message);
    }

    public IllegalAccessException(Error code, Throwable cause) {
        super(code, cause);
    }

    @Override
    protected String reasonCode() {
        return "ACCESS";
    }
}
