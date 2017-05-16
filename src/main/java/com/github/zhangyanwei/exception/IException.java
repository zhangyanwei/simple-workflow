package com.github.zhangyanwei.exception;

public interface IException {
    String getCodeValue();
    String getMessage();
    String getLocalizedMessage();
    <C extends Enum<C>> C getCode();
}
