package generator.types;

import java.util.*;

import static utils.CommonUtils.Assert;

public final class FunctionType implements TypeAttr.NType {
    private final String typeName;

    public record Arg(String argName, TypeAttr.NormalType type) {
    }

    private final List<Arg> args;

    private final Optional<TypeAttr.NormalType> returnType;

    private final boolean allocator;

    public FunctionType(String typeName, List<Arg> args, TypeAttr.NType retType) {
        this.typeName = typeName;
        this.args = args;
        returnType = Optional.ofNullable(switch (retType) {
            case TypeAttr.NormalType a -> a;
            case VoidType _, FunctionType _ -> null;
        });
        allocator = returnType.filter(TypeAttr::isValueBased).isPresent();
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        HashSet<TypeAttr.NType> ret = new HashSet<>();
        args.forEach(arg -> ret.add(arg.type));
        returnType.ifPresent(ret::add);
        Assert(!ret.contains(this), "should not contains this");
        return Set.copyOf(ret);
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
