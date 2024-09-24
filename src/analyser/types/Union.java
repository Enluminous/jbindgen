package analyser.types;

public final class Union extends Record {
    public Union(String name, long sizeof) {
        super(name, sizeof);
    }


    @Override
    public String toString() {
        return "Union{" +
                "members=" + members +
                ", displayName='" + displayName + '\'' +
                ", sizeof=" + sizeof +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
