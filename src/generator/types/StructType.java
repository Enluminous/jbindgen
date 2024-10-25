package generator.types;

import generator.types.operations.MemoryBased;
import generator.types.operations.OperationAttr;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class StructType extends TypeAttr.AbstractType {
    /**
     * the struct member
     *
     * @param type    the member type
     * @param name    member name
     * @param offset  offsetof(TYPE, MEMBER)
     * @param bitSize when using bitfield
     */
    public record Member(TypeAttr.AbstractType type, String name, long offset, long bitSize) {

    }

    private List<Member> members;

    public StructType(long byteSize, String memoryLayout, String typeName, List<Member> members) {
        super(byteSize, memoryLayout, typeName);
        this.members = members;
    }


    @Override
    public OperationAttr.Operation getOperation() {
        return new MemoryBased(typeName, byteSize);
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    @Override
    public Set<TypeAttr.Type> getDefineReferTypes() {
        Set<TypeAttr.Type> types = new HashSet<>(CommonTypes.SpecificTypes.NList.getReferenceTypes());
        for (Member member : members) {
            types.addAll(member.type().getReferenceTypes());
        }
        types.remove(this);
        return types;
    }

    @Override
    public String toString() {
        return "StructType{" +
               "members=" + members +
               ", typeName='" + typeName + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StructType that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), members);
    }
}
