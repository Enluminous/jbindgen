package analyser.types;

import java.util.Objects;

public sealed abstract class Type permits Array, Complex, Enum, Pointer, Primitive, Record, TypeDef, TypeFunction {
    protected final String typeName;
    protected final long sizeof;
    protected String displayName;
    protected final String location;
    protected final long align;

    public Type(String typeName, long sizeof, String location, long align) {
        if (sizeof < 0 && sizeof != -2) {
            throw new RuntimeException("Unexpected sizeof value " + sizeof);
        }
        this.typeName = typeName;
        this.sizeof = sizeof;
        this.location = location;
        this.align = align;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        String ret = displayName;
        if (ret == null)
            ret = typeName;
        ret = ret.replace("struct ", "");
        ret = ret.replace("union ", "");
        ret = ret.replace("enum ", "");
        ret = ret.replace("[", "_").replace("]", "_");
        ret = ret.replace(" *", "_ptr_");
        ret = ret.replace("*", "_ptr_");
        ret = ret.replace("_Complex ", "_Complex");
        ret = ret.replace("const ", "const_");
        return ret;
    }

    public String getTypeName() {
        return typeName;
    }

    public long getSizeof() {
        return sizeof;
    }

    public long getAlign() {
        return align;
    }

    public String getLocation() {
        return location;
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
