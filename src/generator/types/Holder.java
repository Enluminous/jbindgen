package generator.types;

import java.util.Objects;

public class Holder<T> {
    private final T t;

    Holder(T t) {
        this.t = Objects.requireNonNull(t);
    }

    public T getT() {
        return t;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Holder<?> that)) return false;
        return Objects.equals(t, that.t);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(t);
    }

    @Override
    public String toString() {
        return "Holder{" +
               "t=" + t +
               '}';
    }
}
