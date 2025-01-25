package generator.types;

import generator.Utils;
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
    public record Member(TypeAttr.TypeRefer type, String name, long offset, long bitSize) {
        private String typeName() {
            return ((TypeAttr.NamedType) type).typeName(NameType.GENERIC);
        }

        // note: to avoid member to be a graph, we should compare type name instead of type
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Member member = (Member) o;
            return offset == member.offset && bitSize == member.bitSize
                   && Objects.equals(name, member.name)
                   && Objects.equals(typeName(), member.typeName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(typeName(), name, offset, bitSize);
        }

        @Override
        public String toString() {
            return "Member{" +
                   "type=" + ((TypeAttr.NamedType) type).typeName(NameType.GENERIC) +
                   ", name='" + name + '\'' +
                   ", offset=" + offset +
                   ", bitSize=" + bitSize +
                   '}';
        }
    }

    private final List<Member> members;

    public interface MemberProvider {
        List<Member> provide(StructType structType);
    }

    public StructType(long byteSize, String typeName, MemberProvider memberProvider) {
        super(byteSize, Utils.makeMemoryLayout(byteSize), typeName);
        this.members = List.copyOf(memberProvider.provide(this));
    }


    @Override
    public OperationAttr.Operation getOperation() {
        return new MemoryBased(typeName, byteSize);
    }

    public List<Member> getMembers() {
        return members;
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        var types = new HashSet<>(CommonTypes.SpecificTypes.Array.getUseImportTypes());
        for (Member member : members) {
            types.addAll(member.type().getUseImportTypes());
        }
        types.remove(new Holder<>((TypeAttr.TypeRefer) this));
        return types;
    }

    @Override
    public Optional<Holder<StructType>> toGenerationTypes() {
        return Optional.of(new Holder<>(this));
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
        if (!super.equals(o)) return false;
        return Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), members);
    }
}
