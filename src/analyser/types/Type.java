package analyser.types;

import java.util.Objects;

public sealed class Type permits
        Array, Elaborated, Enum, Pointer, Primitive, Struct, TypeDef, TypeFunction, Union {
    protected final String typeName;

    public Type(String typeName) {
        this.typeName = typeName;
    }

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
        Type that = (Type) o;
        return Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(typeName);
    }
}
