package ru.spbau.mit;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
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
        futures.add(pool.submit(new IntSupplier(50, 0)));
        futures.add(pool.submit(new IntSupplier(50, 1)));
        futures.add(pool.submit(new IntSupplier(100, 2)));
        futures.add(pool.submit(new IntSupplier(100, 3)));
        futures.add(pool.submit(new IntSupplier(50, 4)));
        futures.add(pool.submit(new IntSupplier(70, 5)));
        futures.add(pool.submit(new IntSupplier(50, 6)));

        Thread.sleep(300);

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
        futures.add(pool.submit(new IntSupplier(50, 0)));
        futures.add(pool.submit(new IntSupplier(50, 1)));
        futures.add(pool.submit(new IntSupplier(100, 2)));

        Thread.sleep(200);
        futures.add(pool.submit(new IntSupplier(50, 3)));
        Thread.sleep(60);

        int i = 0;
        for (LightFuture<Integer> future : futures) {
            assertTrue(future.isReady());
            assertEquals((int) future.get(), i);
            ++i;
        }
    }

    @Test
    public void testShutdown() throws InterruptedException, LightExecutionException {
        ThreadPool pool = new ThreadPoolImpl(2);
        List<LightFuture<Integer>> futures = new ArrayList<>();
        futures.add(pool.submit(new IntSupplier(100, 0)));
        futures.add(pool.submit(new IntSupplier(100, 1)));
        futures.add(pool.submit(new IntSupplier(100, 2)));
        futures.add(pool.submit(new IntSupplier(100, 3)));

        Thread.sleep(70);
        pool.shutdown();
        Thread.sleep(140);

        assertEquals(pool.submit(new IntSupplier(100, 4)), null);

        for (int i = 2; i < 4; ++i) {
            assertFalse(futures.get(i).isReady());
            assertEquals(futures.get(i).get(), null);
        }
    }

    @Test
    public void testThreadsNumber() throws InterruptedException {
        final int n = 5;
        ThreadPool pool = new ThreadPoolImpl(n);
        List<LightFuture<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            futures.add(pool.submit(new IntSupplier(100, i)));
        }

        Thread.sleep(105);

        for (LightFuture<Integer> future : futures) {
            assertTrue(future.isReady());
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
        assertFalse(nextFutures.get(0).isReady());
        assertFalse(nextFutures.get(2).isReady());

        Thread.sleep(150);
        nextFutures.add(futures.get(2).thenApply(new SumSupplier(10, 11)));
        Thread.sleep(100);

        int i = 0;
        for (LightFuture<Integer> future : futures) {
            assertTrue(future.isReady());
            assertEquals((int) future.get(), i);
            ++i;
        }

        i = 10;
        for (LightFuture<Integer> nextFuture : nextFutures) {
            assertTrue(nextFuture.isReady());
            assertEquals((int) nextFuture.get(), i);
            ++i;
        }
    }

}
