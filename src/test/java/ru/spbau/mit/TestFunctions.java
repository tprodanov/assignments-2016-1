package ru.spbau.mit;

import java.util.Deque;

public class TestFunctions {

    public class A {}

    public class B extends A {}

    public static final Function1<Integer, A> intToA = new Function1<Integer, A>() {
        @Override
        public A apply(Integer integer) {
            return null;
        }
    };

    public static final Function1<Integer, B> intToB = new Function1<Integer, B>() {
        @Override
        public B apply(Integer integer) {
            return null;
        }
    };

    public static final Function2<Integer, Integer, A> intIntToA = new Function2<Integer, Integer, A>() {
        @Override
        public A apply(Integer t1, Integer t2) {
            return null;
        }
    };

    public static final Function2<Integer, Integer, B> intIntToB = new Function2<Integer, Integer, B>() {
        @Override
        public B apply(Integer t1, Integer t2) {
            return null;
        }
    };

    public static final Function1<Integer, Integer> square = new Function1<Integer, Integer>() {
        @Override
        public Integer apply(Integer a) {
            return a * a;
        }
    };

    public static final Function1<A, Integer> aToInt = new Function1<A, Integer>() {
        @Override
        public Integer apply(A a) {
            return null;
        }
    };

    public static final Function1<B, Integer> bToInt = new Function1<B, Integer>() {
        @Override
        public Integer apply(B b) {
            return null;
        }
    };

    public static final Function2<A, A, Integer> aaToInt = new Function2<A, A, Integer>() {
        @Override
        public Integer apply(A a1, A a2) {
            return null;
        }
    };

    public static final Function2<B, B, Integer> bbToInt = new Function2<B, B, Integer>() {
        @Override
        public Integer apply(B b1, B b2) {
            return null;
        }
    };

    public static final Predicate<A> aPredicate = new Predicate<A>() {
        @Override
        public Boolean apply(A a) {
            return a == null;
        }
    };

    public static final Predicate<B> bPredicate = new Predicate<B>() {
        @Override
        public Boolean apply(B b) {
            return b == null;
        }
    };

    public static final Predicate<Integer> isEven = new Predicate<Integer>() {
        @Override
        public Boolean apply(Integer integer) {
            return integer % 2 == 0;
        }
    };

    public static final Predicate<Integer> isPositive = new Predicate<Integer>() {
        @Override
        public Boolean apply(Integer integer) {
            return integer > 0;
        }
    };

    public static final Predicate<Object> throwingPredicate = new Predicate<Object>() {
        @Override
        public Boolean apply(Object o) {
            throw new PredicateError();
        }
    };

    public static final Function2<Deque<Integer>, Integer, Deque<Integer>> pushBack =
            new Function2<Deque<Integer>, Integer, Deque<Integer>>() {
        @Override
        public Deque<Integer> apply(Deque<Integer> deque, Integer i) {
            deque.addLast(i);
            return deque;
        }
    };

    public static final Function2<Integer, Deque<Integer>, Deque<Integer>> pushFront =
            new Function2<Integer, Deque<Integer>, Deque<Integer>>() {
        @Override
        public Deque<Integer> apply(Integer i, Deque<Integer> deque) {
            deque.addFirst(i);
            return deque;
        }
    };

    public static final Function2<Integer, Integer, Integer> sum =
            new Function2<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer x, Integer y) {
            return x + y;
        }
    };

    public static final Function2<Integer, Integer, Integer> xSquarePlusY =
            new Function2<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer x, Integer y) {
            return x * x + y;
        }
    };

    public static A aInstance = null;
    public static B bInstance = null;

    public static class PredicateError extends Error {

    }

}
