package analyser.types;

import analyser.Para;
import libclang.enumerates.CXTypeLayoutError;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public sealed abstract class Record extends Type permits Struct, Union {
    protected final ArrayList<Para> members = new ArrayList<>();

    public Record(String typeName, long sizeof, String location) {
        super(typeName, sizeof, location);
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

    public boolean isIncomplete() {
        return CXTypeLayoutError.CXTypeLayoutError_Incomplete.operator().value() == sizeof;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Record record = (Record) o;
        return Objects.equals(members, record.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), members);
    }
}
