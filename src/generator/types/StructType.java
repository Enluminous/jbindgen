package generator.types;

import generator.types.operations.MemoryBased;
import generator.types.operations.OperationAttr;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static utils.CommonUtils.Assert;

public final class StructType implements SingleGenerationType {
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
            return ((TypeAttr.NamedType) type).typeName(TypeAttr.NameType.GENERIC);
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
                   "type=" + ((TypeAttr.NamedType) type).typeName(TypeAttr.NameType.GENERIC) +
                   ", name='" + name + '\'' +
                   ", offset=" + offset +
                   ", bitSize=" + bitSize +
                   '}';
        }
    }

    private final long byteSize;
    private final String typeName;
    private final List<Member> members;
    private final MemoryLayouts memoryLayout;

    public interface MemberProvider {
        List<Member> provide(StructType structType);
    }

    public StructType(long byteSize, String typeName, MemberProvider memberProvider) {
        this.byteSize = byteSize;
        this.typeName = typeName;
        this.members = List.copyOf(memberProvider.provide(this));
        memoryLayout = makeMemoryLayout(members, byteSize);
    }

    public static MemoryLayouts makeMemoryLayout(List<Member> members, long byteSize) {
        for (Member member : members) {
            if (member.bitSize % 8 != 0)
                return AbstractGenerationType.makeMemoryLayout(byteSize);
        }
        ArrayList<MemoryLayouts> layouts = new ArrayList<>();
        long currentByteSize = 0;
        for (Member member : members) {
            long mByteSize = member.bitSize / 8;
            long mOffset = member.offset / 8;
            if (currentByteSize == 0) {
                Assert(mOffset == 0);
                currentByteSize = (mByteSize / 8);
            } else {
                if (currentByteSize < member.offset) {
                    long padding = mByteSize - currentByteSize;
                    Assert(currentByteSize + padding % mByteSize == 0);
                    layouts.add(MemoryLayouts.paddingLayout(padding));
                }
            }
            layouts.add(((TypeAttr.OperationType) member.type).getOperation().getCommonOperation().makeDirectMemoryLayout());
        }
        return MemoryLayouts.structLayout(layouts);
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new MemoryBased(this);
    }

    public List<Member> getMembers() {
        return members;
    }

    @Override
    public TypeImports getUseImportTypes() {
        return new TypeImports(this);
    }

    @Override
    public TypeImports getDefineImportTypes() {
        TypeImports imports = CommonTypes.SpecificTypes.Array.getUseImportTypes()
                .addUseImports(CommonTypes.SpecificTypes.StructOp)
                .addUseImports(CommonTypes.BindTypes.Ptr)
                .addUseImports(CommonTypes.BasicOperations.Info);
        for (Member member : members) {
            imports.addUseImports(member.type);
        }
        return imports.removeImport(this);
    }

    @Override
    public String typeName(TypeAttr.NameType nameType) {
        return typeName;
    }

    @Override
    public MemoryLayouts getMemoryLayout() {
        return memoryLayout;
    }

    @Override
    public long byteSize() {
        return byteSize;
    }

    @Override
    public String toString() {
        return "StructType{" +
               "members=" + members +
               ", memoryLayout='" + memoryLayout + '\'' +
               ", typeName='" + typeName + '\'' +
               '}';
    }

    // do not compare memoryLayout and members since which is null
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StructType that)) return false;
        return byteSize == that.byteSize && Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(byteSize, typeName);
    }
}
