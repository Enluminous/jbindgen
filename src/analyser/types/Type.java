package analyser.types;

import java.util.Objects;

public sealed abstract class Type permits
        Array, Enum, Pointer, Primitive, TypeDef, TypeFunction, Record {
    protected final String typeName;
    protected final long sizeof;

    public Type(String typeName, long sizeof) {
        this.typeName = typeName;
        this.sizeof = sizeof;
    }

    public Type(String typeName) {
        this.typeName = typeName;
        this.sizeof = 0;
    }

    public String getTypeName() {
        return typeName;
    }

    public long getSizeof() {
        return sizeof;
    }

    @Override
    public String toString() {
        return "Type{" +
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
