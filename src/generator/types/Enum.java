package generator.types;

import java.util.List;

public final class Enum extends TypeAttr.AbstractType {
    /**
     * the enum member
     */
    public record Member(long val, String name) {
    }

    private final List<Member> members;

    public Enum(Primitives type, String typeName, List<Member> members) {
        super(type.getByteSize(), type.getMemoryLayout(), typeName);
        this.members = members;
    }
}
