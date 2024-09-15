package generator.types;

import generator.types.operations.CommonValueBased;
import generator.types.operations.OperationAttr;

import java.util.List;

public final class EnumType extends TypeAttr.AbstractType {
    /**
     * the enum member
     */
    public record Member(long val, String name) {

    }

    private final List<Member> members;

    public EnumType(Primitives type, String typeName, List<Member> members) {
        super(type.getByteSize(), type.getMemoryLayout(), typeName);
        this.members = members;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new CommonValueBased(typeName, memoryLayout);
    }
}
