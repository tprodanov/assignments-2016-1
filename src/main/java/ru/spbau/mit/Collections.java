package ru.spbau.mit;

import java.util.ArrayList;

public class Collections {

    // Here and below it is possible to make Iterable<? extends T>, but it is not intuitive for users
    public static <T, R> Iterable<R> map(Function1<? super T, R> f, Iterable<T> a) {
        ArrayList<R> result = new ArrayList<>();
        for (T t : a) {
            result.add(f.apply(t));
        }
        return result;
    }

    public static <T> Iterable<T> filter(Predicate<? super T> p, Iterable<T> a) {
        ArrayList<T> result = new ArrayList<>();
        for (T t : a) {
            if (p.apply(t)) {
                result.add(t);
            }
        }
        return result;
    }

    public static <T> Iterable<T> takeWhile(Predicate<? super T> p, Iterable<T> a) {
        return takeWhileEquals(p, a, true);
    }

    public static <T> Iterable<T> takeUntil(Predicate<? super T> p, Iterable<T> a) {
        return takeWhileEquals(p, a, false);
    }

    public static <T, G> G foldl(Function2<? super G, ? super T, ? extends G> f, G z, Iterable<T> a) {
        G result = z;
        for (T t : a) {
            result = f.apply(result, t);
        }
        return result;
    }

    public static <T, G> G foldr(Function2<? super T, ? super G, ? extends G> f, G z, Iterable<T> a) {
        ArrayList<T> aCopy = new ArrayList<>();
        for (T t : a) {
            aCopy.add(t);
        }

        G result = z;
        for (int i = aCopy.size() - 1; i >= 0; --i) {
            result = f.apply(aCopy.get(i), result);
        }
        return result;
    }

    private static <T> Iterable<T> takeWhileEquals(Predicate<? super T> p, Iterable<T> a, boolean value) {
        ArrayList<T> result = new ArrayList<>();
        for (T t : a) {
            if (p.apply(t) != value) {
                break;
            }
            result.add(t);
        }
        return result;
    }

}
