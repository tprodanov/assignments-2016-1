package ru.spbau.mit;

import java.util.function.Function;

abstract class AbstractLightFuture<R> implements Runnable, LightFuture<R> {

    private final ThreadPoolImpl pool;
    private boolean ready = false;
    private R obj;
    private Throwable supplierException;

    AbstractLightFuture(ThreadPoolImpl pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        synchronized (this) {
            if (ready) {
                return;
            }
        }
        try {
            obj = createObject();
        } catch (Throwable exception) {
            synchronized (this) {
                supplierException = exception;
                ready = true;
                this.notifyAll();
                pool.futureFailing(this, exception);
                return;
            }
        }

        if (Thread.interrupted()) {
            return;
        }

        synchronized (this) {
            ready = true;
            this.notifyAll();
        }
        pool.futureReady(this, obj);
    }

    @Override
    public synchronized boolean isReady() {
        return ready;
    }

    @Override
    public synchronized R get() throws LightExecutionException, InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }

        while (!ready) {
            this.wait();
        }
        if (supplierException != null) {
            throw new LightExecutionException(supplierException);
        }

        return obj;
    }

    @Override
    public <U> LightFuture<U> thenApply(Function<? super R, ? extends U> f) {
        if (!pool.isActive()) {
            return null;
        }

        DependableLightFuture<R, U> nextTask = new DependableLightFuture<>(pool, f);
        synchronized (this) {
            if (ready) {
                nextTask.setPreviousTaskResult(obj);
                pool.addRunnable(nextTask);
            } else {
                pool.addDependable(this, nextTask);
            }
        }
        return nextTask;
    }

    protected abstract R createObject();

    void markReady() {
        this.ready = true;
    }

    void setSupplierException(Throwable supplierException) {
        this.supplierException = supplierException;
    }

}
