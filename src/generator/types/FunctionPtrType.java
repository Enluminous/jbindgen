package generator.types;

import generator.types.operations.FunctionPtrBased;
import generator.types.operations.OperationAttr;

import java.util.*;

import static utils.CommonUtils.Assert;

// function ptr type, not function protocol type
public final class FunctionPtrType extends TypeAttr.AbstractType {
    public record Arg(String argName, TypeAttr.NormalType type) {

    }

    private List<Arg> args;

    private final TypeAttr.NormalType returnType;

    private final boolean allocator;

    public FunctionPtrType(String typeName, List<Arg> args, TypeAttr.NType retType) {
        super(CommonTypes.Primitives.ADDRESS.getByteSize(), CommonTypes.Primitives.ADDRESS.getMemoryLayout(), typeName);
        this.args = args;
        returnType = switch (retType) {
            case TypeAttr.NormalType normalType -> normalType;
            case VoidType _ -> null;
            case RefOnlyType _ -> throw new IllegalArgumentException();
        };
        allocator = returnType != null && returnType.getOperation() instanceof OperationAttr.MemoryBasedOperation;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        HashSet<TypeAttr.Type> ret = new HashSet<>();
        args.forEach(arg -> ret.add(arg.type));
        if (returnType != null) {
            ret.add(returnType);
        }
        ret.add(CommonTypes.BindTypes.Pointer);
        ret.addAll(CommonTypes.BindTypes.Pointer.getReferencedTypes());
        Assert(!ret.contains(this), "should not contains this");
        return Set.copyOf(ret);
    }

    public boolean needAllocator() {
        return allocator;
    }

    public Optional<TypeAttr.NormalType> getReturnType() {
        return Optional.ofNullable(returnType);
    }

    public List<Arg> getArgs() {
        return args;
    }

    public void setArgs(List<Arg> args) {
        this.args = args;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new FunctionPtrBased(typeName);
    }

    @Override
    public String toString() {
        return "FunctionType{" +
               "returnType=" + returnType +
               ", args=" + args +
               ", typeName='" + typeName + '\'' +
               '}';
    }
}
