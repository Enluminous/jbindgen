package generator.types;

import generator.Utils;
import generator.types.operations.CommonOperation;
import generator.types.operations.FunctionPtrBased;
import generator.types.operations.OperationAttr;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static utils.CommonUtils.Assert;

// function ptr type, not function protocol type
public final class FunctionPtrType extends AbstractGenerationType {
    public record Arg(String argName, TypeAttr.TypeRefer type) {
        public Arg {
            Assert(Utils.isValidVarName(argName), "Arg name must be a valid variable name: " + argName);
        }
    }

    private final List<Arg> args;

    private final TypeAttr.TypeRefer returnType;

    private final CommonOperation.AllocatorType allocator;

    public FunctionPtrType(String typeName, List<Arg> args, TypeAttr.TypeRefer retType) {
        super(CommonTypes.Primitives.ADDRESS.getMemoryLayout(), typeName, CommonTypes.Primitives.ADDRESS.byteSize());
        this.args = List.copyOf(args);
        returnType = switch (retType) {
            case TypeAttr.SizedType normalType -> ((TypeAttr.TypeRefer) normalType);
            case VoidType _ -> null;
            default -> throw new IllegalStateException("Unexpected value: " + retType);
        };
        allocator = returnType instanceof TypeAttr.OperationType o
                ? o.getOperation().getCommonOperation().getAllocatorType()
                : CommonOperation.AllocatorType.NONE;
    }


    @Override
    public TypeImports getDefineImportTypes() {
        TypeImports imports = new TypeImports();
        args.forEach(arg -> imports.addUseImports(arg.type));
        if (returnType != null) {
            imports.addImport(returnType.getUseImportTypes());
        }
        return imports;
    }

    public CommonOperation.AllocatorType allocatorType() {
        return allocator;
    }

    public Optional<TypeAttr.OperationType> getReturnType() {
        return Optional.ofNullable(returnType).map(referenceType -> ((TypeAttr.OperationType) referenceType));
    }

    public List<Arg> getArgs() {
        return args;
    }

    public List<MemoryLayouts> getMemoryLayouts() {
        ArrayList<MemoryLayouts> memoryLayout = new ArrayList<>();
        if (this.getReturnType().isPresent())
            memoryLayout.add(((TypeAttr.SizedType) this.getReturnType().get()).getMemoryLayout());
        for (Arg arg : this.getArgs()) {
            memoryLayout.add(((TypeAttr.SizedType) arg.type()).getMemoryLayout());
        }
        return memoryLayout;
    }

    public List<TypeAttr.TypeRefer> getFunctionSignatureTypes() {
        ArrayList<TypeAttr.TypeRefer> types = new ArrayList<>();
        args.forEach(arg -> types.add(arg.type));
        if (returnType != null) {
            types.add(returnType);
        }
        return types;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new FunctionPtrBased(this, typeName);
    }

    @Override
    public String toString() {
        return "FunctionPtrType{" +
               "args=" + args +
               ", returnType=" + returnType +
               ", allocator=" + allocator +
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
