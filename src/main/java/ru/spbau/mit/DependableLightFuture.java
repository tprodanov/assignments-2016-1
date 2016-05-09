package ru.spbau.mit;

import java.util.function.Function;

class DependableLightFuture<R, U> extends AbstractLightFuture<U> {

    private Function<? super R, ? extends U> f;
    private R previousTaskResult;

    DependableLightFuture(ThreadPoolImpl pool, Function<? super R, ? extends U> f) {
        setPool(pool);
        this.f = f;
    }

    @Override
    protected U createObject() {
        return f.apply(previousTaskResult);
    }

    void setPreviousTaskResult(Object previousTaskResult) {
        this.previousTaskResult = (R) previousTaskResult;
    }

    synchronized void setFailure(Throwable failure) {
        setSupplierException(failure);
        markReady();
    }
}
