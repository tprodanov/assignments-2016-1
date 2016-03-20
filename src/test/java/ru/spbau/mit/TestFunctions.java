package ru.spbau.mit;

import java.util.Deque;

public final class TestFunctions {

    public class A {}

    public class B extends A {}

    public static final Function1<Integer, A> INT_TO_A = new Function1<Integer, A>() {
        @Override
        public A apply(Integer integer) {
            return null;
        }
    };

    public static final Function1<Integer, B> INT_TO_B = new Function1<Integer, B>() {
        @Override
        public B apply(Integer integer) {
            return null;
        }
    };

    public static final Function2<Integer, Integer, A> INT_INT_TO_A = new Function2<Integer, Integer, A>() {
        @Override
        public A apply(Integer t1, Integer t2) {
            return null;
        }
    };

    public static final Function2<Integer, Integer, B> INT_INT_TO_B = new Function2<Integer, Integer, B>() {
        @Override
        public B apply(Integer t1, Integer t2) {
            return null;
        }
    };

    public static final Function1<Integer, Integer> SQUARE = new Function1<Integer, Integer>() {
        @Override
        public Integer apply(Integer a) {
            return a * a;
        }
    };

    public static final Function1<A, Integer> A_TO_INT = new Function1<A, Integer>() {
        @Override
        public Integer apply(A a) {
            return null;
        }
    };

    public static final Function1<B, Integer> B_TO_INT = new Function1<B, Integer>() {
        @Override
        public Integer apply(B b) {
            return null;
        }
    };

    public static final Function2<A, A, Integer> AA_TO_INT = new Function2<A, A, Integer>() {
        @Override
        public Integer apply(A a1, A a2) {
            return null;
        }
    };

    public static final Function2<B, B, Integer> BB_TO_INT = new Function2<B, B, Integer>() {
        @Override
        public Integer apply(B b1, B b2) {
            return null;
        }
    };

    public static final Predicate<A> A_PREDICATE = new Predicate<A>() {
        @Override
        public Boolean apply(A a) {
            return a == null;
        }
    };

    public static final Predicate<B> B_PREDICATE = new Predicate<B>() {
        @Override
        public Boolean apply(B b) {
            return b == null;
        }
    };

    public static final Predicate<Integer> IS_EVEN = new Predicate<Integer>() {
        @Override
        public Boolean apply(Integer integer) {
            return integer % 2 == 0;
        }
    };

    public static final Predicate<Integer> IS_POSITIVE = new Predicate<Integer>() {
        @Override
        public Boolean apply(Integer integer) {
            return integer > 0;
        }
    };

    public static final Predicate<Object> THROWING_PREDICATE = new Predicate<Object>() {
        @Override
        public Boolean apply(Object o) {
            throw new PredicateError();
        }
    };

    public static final Function2<Deque<Integer>, Integer, Deque<Integer>> PUSH_BACK =
            new Function2<Deque<Integer>, Integer, Deque<Integer>>() {
        @Override
        public Deque<Integer> apply(Deque<Integer> deque, Integer i) {
            deque.addLast(i);
            return deque;
        }
    };

    public static final Function2<Integer, Deque<Integer>, Deque<Integer>> PUSH_FRONT =
            new Function2<Integer, Deque<Integer>, Deque<Integer>>() {
        @Override
        public Deque<Integer> apply(Integer i, Deque<Integer> deque) {
            deque.addFirst(i);
            return deque;
        }
    };

    public static final Function2<Integer, Integer, Integer> SUM =
            new Function2<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer x, Integer y) {
            return x + y;
        }
    };

    public static final Function2<Integer, Integer, Integer> X_SQUARE_PLUS_Y =
            new Function2<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer x, Integer y) {
            return x * x + y;
        }
    };

    public static final A A_INSTANCE = null;
    public static final B B_INSTANCE = null;

    public static class PredicateError extends Error {
        // EMPTY
    }

    private TestFunctions() {
        // EMPTY
    }

}
