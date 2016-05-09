package ru.spbau.mit;


class ThreadTask implements Runnable {

    private final ThreadPoolImpl pool;

    ThreadTask(ThreadPoolImpl pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        Runnable currentTask;
        while (pool.isActive()) {
            currentTask = obtainNext();
            if (currentTask == null) {
                return;
            }

            currentTask.run();
        }
    }

    private Runnable obtainNext() {
        Runnable currentTask = pool.assignNextTask();
        while (currentTask == null && pool.isActive()) {
            try {
                synchronized (pool) {
                    pool.wait();
                }
            } catch (InterruptedException e) {
                return null;
            }
            currentTask = pool.assignNextTask();
        }

        return currentTask;
    }

}
