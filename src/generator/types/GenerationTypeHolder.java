package generator.types;

import java.util.Objects;

public class GenerationTypeHolder<T extends TypeAttr.GenerationType> {
    private final T generationType;

    GenerationTypeHolder(T generationType) {
        this.generationType = Objects.requireNonNull(generationType);
    }

    public T getGenerationType() {
        return generationType;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GenerationTypeHolder<?> that)) return false;
        return Objects.equals(generationType, that.generationType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(generationType);
    }

    @Override
    public String toString() {
        return "GenerationTypeHolder{" +
               "generationType=" + generationType +
               '}';
    }
}
