package generator.types;

import generator.types.operations.ValueBased;
import generator.types.operations.OperationAttr;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
        return new ValueBased(typeName, type.getPrimitiveType());
    }


    @Override
    public Set<TypeAttr.Type> getDefineReferTypes() {
        Set<TypeAttr.Type> types = new HashSet<>(type.getReferenceTypes());
        types.addAll(type.getListType().getReferenceTypes());
        return types;
    }

    @Override
    public String toString() {
        return "EnumType{" +
               "members=" + members +
               ", typeName='" + typeName + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnumType enumType)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(members, enumType.members) && type == enumType.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), members, type);
    }
}
