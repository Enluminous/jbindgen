package generator.types;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class FunctionType implements TypeAttr.Type {
    private final String typeName;

    public record Arg(String argName, TypeAttr.NormalType type) {
    }

    private final List<Arg> args;

    private final Optional<TypeAttr.NormalType> returnType;

    private final boolean allocator;

    public FunctionType(String typeName, List<Arg> args, TypeAttr.Type retType) {
        this.typeName = typeName;
        this.args = args;
        returnType = switch (retType) {
            case TypeAttr.NormalType a -> Optional.of(a);
            case VoidType _, FunctionType _ -> Optional.empty();
        };
        allocator = returnType.map(TypeAttr.NormalType::isValueBased).isPresent();
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        HashSet<TypeAttr.Type> ret = new HashSet<>();
        args.forEach(arg -> ret.add(arg.type));
        returnType.ifPresent(ret::add);
        ret.remove(this);
        return ret;
    }

    public boolean needAllocator() {
        return allocator;
    }

    public Optional<TypeAttr.NormalType> getReturnType() {
        return returnType;
    }

    public List<Arg> getArgs() {
        return args;
    }
}
