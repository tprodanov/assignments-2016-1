package ru.spbau.mit;

public abstract class Function2<T1, T2, R> {

    public abstract R apply(T1 t1, T2 t2);

    public <G> Function2<T1, T2, G> compose(final Function1<? super R, G> g) {
        return new Function2<T1, T2, G>() {
            @Override
            public G apply(T1 t1, T2 t2) {
                return g.apply(Function2.this.apply(t1, t2));
            }
        };
    }

    public Function1<T2, R> bind1(final T1 t1) {
        return new Function1<T2, R>() {
            @Override
            public R apply(T2 t2) {
                return Function2.this.apply(t1, t2);
            }
        };
    }

    public Function1<T1, R> bind2(final T2 t2) {
        return new Function1<T1, R>() {
            @Override
            public R apply(T1 t1) {
                return Function2.this.apply(t1, t2);
            }
        };
    }

    public Function1<T1, Function1<T2, R>> curry() {

        return new Function1<T1, Function1<T2, R>>() {
            @Override
            public Function1<T2, R> apply(final T1 t1) {

                return new Function1<T2, R>() {
                    @Override
                    public R apply(T2 t2) {
                        return Function2.this.apply(t1, t2);
                    }
                };

            }
        };

    }

}
