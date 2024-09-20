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
    private final CommonTypes.BindTypes type;

    public EnumType(CommonTypes.BindTypes type, String typeName, List<Member> members) {
        super(type.getPrimitiveType().getByteSize(), type.getPrimitiveType().getMemoryLayout(), typeName);
        this.members = members;
        this.type = type;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new CommonValueBased(typeName, type.getPrimitiveType());
    }


    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        return Set.of(type);
    }
}
