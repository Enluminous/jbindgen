package generator.types;

import java.util.*;

import static utils.CommonUtils.Assert;

// function ptr type, not function protocol type
public final class FunctionType implements TypeAttr.NType {
    private final String typeName;

    public record Arg(String argName, TypeAttr.NormalType type) {
    }

    private List<Arg> args;

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
        HashSet<TypeAttr.Type> ret = new HashSet<>();
        args.forEach(arg -> ret.add(arg.type));
        returnType.ifPresent(ret::add);
        ret.add(CommonTypes.BindTypes.Pointer);
        ret.addAll(CommonTypes.BindTypes.Pointer.getReferencedTypes());
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

    public void setArgs(List<Arg> args) {
        this.args = args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FunctionType that)) return false;
        return Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(typeName);
    }
}
