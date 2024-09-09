package analyser.types;

import analyser.TypePool;

import java.util.Objects;

public class Type {
    protected final String typeName;

    public Type(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
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
        Type type = (Type) o;
        return Objects.equals(typeName, type.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(typeName);
    }
}
