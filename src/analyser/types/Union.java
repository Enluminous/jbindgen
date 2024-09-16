package analyser.types;

import analyser.Para;

import java.util.ArrayList;
import java.util.Collection;

public final class Union extends Record {
    private final ArrayList<Para> members;

    public Union(String name, ArrayList<Para> members) {
        super(name);
        this.members = members;
    }

    public void addMember(Para member) {
        members.add(member);
    }

    public void addMembers(Collection<Para> ms) {
        members.addAll(ms);
    }

    public ArrayList<Para> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        return "Union{" +
                "members=" + members +
                ", displayName='" + displayName + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
