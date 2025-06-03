package analyser.types;

public final class Complex extends Type {
    private final Type elem;

    public Complex(String typeName, Type elem, long align, long sizeof, String location) {
        super(typeName, sizeof, location, align);
        this.elem = elem;
    }

    public Type getElem() {
        return elem;
    }
}
