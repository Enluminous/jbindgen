package analyser.types;

import analyser.Para;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public final class Struct extends Record {
    public Struct(String name, long sizeof) {
        super(name, sizeof);
    }

    public void addPara(Para para) {
        members.add(para);
    }

    public void addParas(Collection<Para> ps) {
        members.addAll(ps);
    }

    public ArrayList<Para> getMembers() {
        return members;
    }

    public Struct setName(String name) {
        Struct s = new Struct(name, sizeof);
        s.addParas(members);
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

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), members);
    }
}
