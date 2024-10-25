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
    public Set<TypeAttr.Type> getDefineReferTypes() {
        HashSet<TypeAttr.Type> ret = new HashSet<>();
        args.forEach(arg -> ret.addAll(arg.type.getReferenceTypes()));
        if (returnType != null) {
            ret.addAll(returnType.getReferenceTypes());
        }
        return ret;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FunctionPtrType that)) return false;
        if (!super.equals(o)) return false;
        return allocator == that.allocator && Objects.equals(args, that.args) && Objects.equals(returnType, that.returnType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), args, returnType, allocator);
    }
}
