package analyser.types;

public final class Primitive extends AbstractType {
    public Primitive(String typeName) {
        super(typeName);
    }

    @Override
    public String toString() {
        return super.toString();
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
