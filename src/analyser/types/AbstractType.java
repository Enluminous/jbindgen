package analyser.types;

import java.util.Objects;

public sealed class AbstractType implements Type permits
        Array, Elaborated, Enum, Pointer, Primitive, Struct, TypeDef, TypeFunction, Union {
    protected final String typeName;

    public AbstractType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return "AbstractType{" +
                "typeName='" + typeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractType that = (AbstractType) o;
        return Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(typeName);
    }
}
