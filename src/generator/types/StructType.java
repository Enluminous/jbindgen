package generator.types;

import generator.types.operations.MemoryBased;
import generator.types.operations.OperationAttr;

import java.util.*;

public final class StructType extends AbstractGenerationType {
    /**
     * the struct member
     *
     * @param type    the member type
     * @param name    member name
     * @param offset  offsetof(TYPE, MEMBER)
     * @param bitSize when using bitfield
     */
    public record Member(TypeAttr.ReferenceType type, String name, long offset, long bitSize) {

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
    public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
        Set<TypeAttr.ReferenceType> types = new HashSet<>(CommonTypes.SpecificTypes.NList.getUseImportTypes());
        for (Member member : members) {
            types.addAll(member.type().getUseImportTypes());
        }
        types.remove(this);
        return types;
    }

    @Override
    public Optional<GenerationTypeHolder<StructType>> toGenerationTypes() {
        return Optional.of(new GenerationTypeHolder<>(this));
    }

    @Override
    public String toString() {
        return "StructType{" +
               "members=" + members +
               ", byteSize=" + byteSize +
               ", memoryLayout='" + memoryLayout + '\'' +
               ", typeName='" + typeName + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StructType that)) return false;
        return Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(members);
    }
}
