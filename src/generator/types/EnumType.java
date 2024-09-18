package generator.types;

import generator.types.operations.CommonValueBased;
import generator.types.operations.OperationAttr;

import java.util.List;
import java.util.Set;

public final class EnumType extends TypeAttr.AbstractType {

    /**
     * the enum member
     */
    public record Member(long val, String name) {

    }

    private final List<Member> members;
    private final Primitives type;

    public EnumType(Primitives type, String typeName, List<Member> members) {
        super(type.getByteSize(), type.getMemoryLayout(), typeName);
        this.members = members;
        this.type = type;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new CommonValueBased(typeName, memoryLayout);
    }

    @Override
    public Primitives getNonWrappedType() {
        return type;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        return Set.of(type);
    }
}
