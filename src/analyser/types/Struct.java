package analyser.types;

import java.util.Objects;

public final class Struct extends Record {
    public Struct(String name, long sizeof) {
        super(name, sizeof);
    }

    public Struct setName(String name) {
        Struct s = new Struct(name, sizeof);
        s.addMembers(members);
        return s;
    }

    @Override
    public String toString() {
        return "Struct{" +
                "members=" + members +
                ", displayName='" + displayName + '\'' +
                ", sizeof=" + sizeof +
                ", typeName='" + typeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Struct struct = (Struct) o;
        return Objects.equals(members, struct.members);
    }
}
