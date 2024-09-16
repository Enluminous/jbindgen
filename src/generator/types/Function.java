package generator.types;

import java.util.List;
import java.util.Optional;

public final class Function implements TypeAttr.Type {
    private final String typeName;

    public record Arg(String argName, TypeAttr.NormalType type) {
    }

    private final List<Arg> args;

    private final Optional<TypeAttr.NormalType> returnType;

    private final boolean allocator;

    public Function(String typeName, List<Arg> args, TypeAttr.Type retType) {
        this.typeName = typeName;
        this.args = args;
        returnType = switch (retType) {
            case TypeAttr.NormalType a -> Optional.of(a);
            case VoidType _, Function _ -> Optional.empty();
        };
        allocator = returnType.map(n -> n instanceof TypeAttr.ValueBased ? null : n).isPresent();
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    public boolean needAllocator() {
        return allocator;
    }
}
