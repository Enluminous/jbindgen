package analyser.types;

public final class Union extends Record {
    public Union(String name, long sizeof, String location) {
        super(name, sizeof, location);
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
