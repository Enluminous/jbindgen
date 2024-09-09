package analyser.types;

public class Pointer extends Type {
    private final Type target;

    public Pointer(String name, Type target) {
        super(name);
        this.target = target;
    }

    @Override
    public String toString() {
        return "Pointer{" +
                "target=" + target +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
