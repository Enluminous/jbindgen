package analyser.types;

public final class Pointer extends Type {
    private final Type target;

    public Pointer(String name, Type target, String location, long align) {
        super(name, 0, location, align);
        this.target = target;
    }

    public Type getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "Pointer{" +
                "target=" + target +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
