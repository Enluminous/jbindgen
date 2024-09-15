package generator.types;

import generator.types.operations.CommonMemoryBased;
import generator.types.operations.OperationAttr;

import java.util.List;

public final class Struct extends TypeAttr.AbstractType {
    /**
     * the struct member
     * @param type the member type
     * @param offset offsetof(TYPE, MEMBER)
     * @param bitSize when using bitfield
     */
    public record Member(TypeAttr.AbstractType type, long offset, long bitSize) {

    }

    private final List<Member> members;

    public Struct(long byteSize, String memoryLayout, String typeName, List<Member> members) {
        super(byteSize, memoryLayout, typeName);
        this.members = members;
    }


    @Override
    public OperationAttr.Operation getOperation() {
        return new CommonMemoryBased(typeName, byteSize);
    }
}
