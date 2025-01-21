package generator.types;

import generator.Utils;
import generator.types.operations.FunctionPtrBased;
import generator.types.operations.OperationAttr;

import java.util.*;

import static utils.CommonUtils.Assert;

// function ptr type, not function protocol type
public final class FunctionPtrType extends AbstractGenerationType {
    public record Arg(String argName, TypeAttr.ReferenceType type) {
        public Arg {
            Assert(Utils.isValidVarName(argName), "Arg name must be a valid variable name: " + argName);
        }
    }

    private final List<Arg> args;

    private final TypeAttr.ReferenceType returnType;

    private final boolean allocator;

    public FunctionPtrType(String typeName, List<Arg> args, TypeAttr.ReferenceType retType) {
        super(CommonTypes.Primitives.ADDRESS.getByteSize(), CommonTypes.Primitives.ADDRESS.getMemoryLayout(), typeName);
        this.args = List.copyOf(args);
        returnType = switch (retType) {
            case TypeAttr.SizedType normalType -> ((TypeAttr.ReferenceType) normalType);
            case VoidType _ -> null;
            default -> throw new IllegalStateException("Unexpected value: " + retType);
        };
        allocator = returnType instanceof TypeAttr.OperationType o && o.getOperation() instanceof OperationAttr.MemoryBasedOperation;
    }


    @Override
    public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
        HashSet<TypeAttr.ReferenceType> ret = new HashSet<>();
        args.forEach(arg -> ret.addAll(arg.type.getUseImportTypes()));
        if (returnType != null) {
            ret.addAll(returnType.getUseImportTypes());
        }
        return ret;
    }

    @Override
    public Optional<GenerationTypeHolder<FunctionPtrType>> toGenerationTypes() {
        return Optional.of(new GenerationTypeHolder<>(this));
    }

    public boolean needAllocator() {
        return allocator;
    }

    public Optional<TypeAttr.OperationType> getReturnType() {
        return Optional.ofNullable(returnType).map(referenceType -> ((TypeAttr.OperationType) referenceType));
    }

    public List<Arg> getArgs() {
        return args;
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
        if (!(o instanceof FunctionPtrType that)) return false;
        if (!super.equals(o)) return false;
        return allocator == that.allocator && Objects.equals(args, that.args) && Objects.equals(returnType, that.returnType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), args, returnType, allocator);
    }
}
