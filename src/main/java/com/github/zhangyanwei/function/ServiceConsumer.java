package com.github.zhangyanwei.function;

import java.util.Objects;

public interface ServiceConsumer<F, S, E extends Exception> {

    void accept(F f, S s) throws E;

    default ServiceConsumer<F, S, E> andThen(ServiceConsumer<F, S, E> after) {
        Objects.requireNonNull(after);

        return (f, s) -> {
            accept(f, s);
            after.accept(f, s);
        };
    }
}
