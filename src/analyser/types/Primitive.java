package analyser.types;

public final class Primitive extends Type {
    public Primitive(String typeName, long sizeof) {
        super(typeName, sizeof);
    }

    @Override
    public String toString() {
        return "Primitive{" +
                "typeName='" + typeName + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Primitive && super.equals(o);
    }
}
