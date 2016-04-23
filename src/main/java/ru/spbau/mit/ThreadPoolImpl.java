package ru.spbau.mit;

import java.util.function.Supplier;

public class ThreadPoolImpl implements ThreadPool {

    public ThreadPoolImpl(int n) {

    }

    @Override
    public <R> LightFuture<R> submit(Supplier<R> supplier) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void shutdown() {
        throw new UnsupportedOperationException();
    }
}
