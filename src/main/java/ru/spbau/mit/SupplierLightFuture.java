package ru.spbau.mit;

import java.util.function.Supplier;

class SupplierLightFuture<R> extends AbstractLightFuture<R> {

    private Supplier<R> supplier;

    SupplierLightFuture(ThreadPoolImpl pool, Supplier<R> supplier) {
        super(pool);
        this.supplier = supplier;
    }

    @Override
    protected R createObject() {
        return supplier.get();
    }
}
