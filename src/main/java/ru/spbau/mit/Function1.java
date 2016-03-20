package ru.spbau.mit;

public abstract class Function1<T, R> {

    public abstract R apply(T t);

    public <G> Function1<T, G> compose(final Function1<? super R, G> g) {
        return new Function1<T, G>() {
            @Override
            public G apply(T t) {
                return g.apply(Function1.this.apply(t));
            }
        };
    }



}
