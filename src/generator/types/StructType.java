package generator.types;

import generator.types.operations.CommonMemoryBased;
import generator.types.operations.OperationAttr;

import java.util.List;
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

    private final List<Member> members;

    public StructType(long byteSize, String memoryLayout, String typeName, List<Member> members) {
        super(byteSize, memoryLayout, typeName);
        this.members = members;
    }


    @Override
    public OperationAttr.Operation getOperation() {
        return new CommonMemoryBased(typeName, byteSize);
    }

    @Override
    public Primitives getNonWrappedType() {
        return Primitives.Address;
    }

    public List<Member> getMembers() {
        return members;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        // todo we need the NList<Struct> type;
        return null;
    }
}
