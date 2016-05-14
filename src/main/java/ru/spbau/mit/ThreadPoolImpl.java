package ru.spbau.mit;

import java.util.*;
import java.util.function.Supplier;

public class ThreadPoolImpl implements ThreadPool {

    private Queue<Runnable> futureTasks = new ArrayDeque<>();
    private Map<LightFuture<?>, List<DependableLightFuture<?, ?>>> dependableTasks
            = new HashMap<>();
    private boolean active = true;
    private List<Thread> threads = new ArrayList<>();

    public ThreadPoolImpl(int n) {
        for (int i = 0; i < n; ++i) {
            Thread newThread = new Thread(this::threadRun);
            threads.add(newThread);
            newThread.start();
        }
    }

    @Override
    public <R> LightFuture<R> submit(Supplier<R> supplier) {
        if (!isActive()) {
            return null;
        }

        SupplierLightFuture<R> future = new SupplierLightFuture<>(this, supplier);
        addRunnable(future);
        return future;
    }

    @Override
    public void shutdown() {
        active = false;
        threads.forEach(Thread::interrupt);
    }

    boolean isActive() {
        return active;
    }

    synchronized void futureReady(LightFuture<?> future, Object suppliedObject) {
        if (dependableTasks.containsKey(future)) {
            for (DependableLightFuture<?, ?> nextTask : dependableTasks.get(future)) {
                nextTask.setPreviousTaskResult(suppliedObject);
                addRunnable(nextTask);
            }
            dependableTasks.remove(future);
        }
    }

    synchronized void futureFailing(LightFuture<?> future, Throwable failure) {
        if (dependableTasks.containsKey(future)) {
            for (DependableLightFuture<?, ?> nextTask : dependableTasks.get(future)) {
                nextTask.setFailure(failure);
            }
            dependableTasks.remove(future);
        }
    }

    synchronized void addDependable(LightFuture<?> dependence,
                                    DependableLightFuture<?, ?> nextTask) {
        if (dependableTasks.containsKey(dependence)) {
            dependableTasks.get(dependence).add(nextTask);
        } else {
            List<DependableLightFuture<?, ?>> newDependenceList = new ArrayList<>();
            newDependenceList.add(nextTask);
            dependableTasks.put(dependence, newDependenceList);
        }
    }

    synchronized void addRunnable(Runnable future) {
        futureTasks.add(future);
        notify();
    }

    synchronized Runnable assignNextTask() {
        if (futureTasks.size() == 0) {
            return null;
        }
        return futureTasks.poll();
    }

    private void threadRun() {
        Runnable currentTask;
        while (isActive()) {
            currentTask = obtainNext();
            if (currentTask == null) {
                return;
            }

            currentTask.run();
        }
    }

    private Runnable obtainNext() {
        Runnable currentTask = assignNextTask();
        while (currentTask == null && isActive()) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                return null;
            }
            currentTask = assignNextTask();
        }

        return currentTask;
    }

}
