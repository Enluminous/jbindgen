package analyser.types;

public final class Pointer extends Type {
    private final Type target;

    public Pointer(String name, Type target) {
        super(name);
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
