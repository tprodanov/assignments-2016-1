package ru.spbau.mit;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.function.Function;
import java.util.function.Supplier;

public class ThreadPoolTest {

    private class IntSupplier implements Supplier<Integer> {

        private final int sleep;
        private final int number;

        IntSupplier(int sleep, int number) {
            this.sleep = sleep;
            this.number = number;
        }

        @Override
        public Integer get() {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return number;
        }
    }

    private class SumSupplier implements Function<Integer, Integer> {

        private final int sleep;
        private final int term1;

        SumSupplier(int sleep, int term1) {
            this.sleep = sleep;
            this.term1 = term1;
        }

        @Override
        public Integer apply(Integer term2) {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return term1 + term2;
        }
    }

    @Test
    public void testSubmit() throws InterruptedException, LightExecutionException {
        ThreadPool pool = new ThreadPoolImpl(4);
        List<LightFuture<Integer>> futures = new ArrayList<>();
        addIntSuppliers(pool, futures, 0, 7);

        Thread.sleep(10);

        int i = 0;
        for (LightFuture<Integer> future : futures) {
            assertTrue(future.isReady());
            assertEquals((int) future.get(), i);
            ++i;
        }
    }

    @Test
    public void testPostponeSubmit() throws InterruptedException, LightExecutionException {
        ThreadPool pool = new ThreadPoolImpl(2);
        List<LightFuture<Integer>> futures = new ArrayList<>();
        addIntSuppliers(pool, futures, 0, 3);

        int i = 0;
        for (LightFuture<Integer> future : futures) {
            assertEquals((int) future.get(), i++);
        }

        futures.add(pool.submit(() -> 3));

        i = 0;
        for (LightFuture<Integer> future : futures) {
            assertEquals((int) future.get(), i++);
        }
    }

    @Test
    public void testShutdown() throws InterruptedException, LightExecutionException {
        ThreadPool pool = new ThreadPoolImpl(4);
        List<LightFuture<Integer>> futures = new ArrayList<>();
        addIntSuppliers(pool, futures, 0, 4);

        for (LightFuture<?> future : futures) {
            future.get();
        }

        pool.shutdown();
        assertEquals(pool.submit(new IntSupplier(100, 4)), null);
    }

    @Test
    public void testThreadsNumber() throws InterruptedException, LightExecutionException {
        final int n = 5;
        ThreadPool pool = new ThreadPoolImpl(n);
        List<LightFuture<Integer>> futures = new ArrayList<>();

        CyclicBarrier barrier = new CyclicBarrier(n);
        for (int i = 0; i < n; ++i) {
            int current = i;
            futures.add(pool.submit(() -> {
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                }
                return current;
            }));
        }

        int i = 0;
        for (LightFuture<Integer> future : futures) {
            assertEquals((int) future.get(), i++);
        }
    }

    @Test
    public void testThenApply() throws LightExecutionException, InterruptedException {
        ThreadPool pool = new ThreadPoolImpl(10);
        List<LightFuture<Integer>> futures = new ArrayList<>();
        futures.add(pool.submit(new IntSupplier(50, 0)));
        futures.add(pool.submit(new IntSupplier(50, 1)));
        futures.add(pool.submit(new IntSupplier(50, 2)));

        List<LightFuture<Integer>> nextFutures = new ArrayList<>();
        nextFutures.add(futures.get(0).thenApply(new SumSupplier(50, 10)));
        nextFutures.add(futures.get(0).thenApply(new SumSupplier(40, 11)));
        nextFutures.add(nextFutures.get(0).thenApply(new SumSupplier(50, 2)));

        Thread.sleep(50);

        Thread.sleep(150);
        nextFutures.add(futures.get(2).thenApply(new SumSupplier(10, 11)));
        Thread.sleep(100);

        int i = 0;
        for (LightFuture<Integer> future : futures) {
            assertEquals((int) future.get(), i);
            ++i;
        }

        i = 10;
        for (LightFuture<Integer> nextFuture : nextFutures) {
            assertEquals((int) nextFuture.get(), i);
            ++i;
        }
    }

    @Test(expected = LightExecutionException.class)
    public void testException() throws LightExecutionException, InterruptedException {
        ThreadPool pool = new ThreadPoolImpl(10);
        List<LightFuture<Integer>> futures = new ArrayList<>();
        final int n = 5;
        addIntSuppliers(pool, futures, 0, n);

        futures.add(pool.submit(() -> {
            throw new Error();
        }));

        for (int i = 0; i < n; ++i) {
            assertEquals((int) futures.get(i).get(), i);
        }
        futures.get(n).get();
    }

    private static void addIntSuppliers(ThreadPool pool,
                                 List<LightFuture<Integer>> futures, int begin, int end) {
        for (int i = begin; i < end; ++i) {
            int current = i;
            futures.add(pool.submit(() -> current));
        }
    }

}
