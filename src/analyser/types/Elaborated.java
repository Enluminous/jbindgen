package analyser.types;

public final class Elaborated extends Type {

    private final Type target;

    public Elaborated(String typeName, Type target) {
        super(typeName);
        this.target = target;
    }

    public Type getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "Elaborated{" +
                "target=" + target +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
