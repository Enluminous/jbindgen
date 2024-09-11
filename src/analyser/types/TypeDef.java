package analyser.types;

import analyser.TypePool;

import java.util.Objects;

public final class TypeDef extends Type {
    private final Type target;

    public TypeDef(String name, Type target) {
        super(name);
        this.target = target;
    }

    public Type getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "TypeDef{" +
                "target=" + target +
                ", typeName='" + typeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TypeDef typeDef = (TypeDef) o;
        return Objects.equals(target, typeDef.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), target);
    }
}
