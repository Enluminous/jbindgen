package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.FuncProtocolGenerator;
import generator.generation.generator.FuncPtrUtils;
import generator.types.CommonTypes;
import generator.types.FunctionPtrType;
import generator.types.Holder;
import generator.types.TypeAttr;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * used to generate function pointer, normally used in callback ptr
 */
public final class FuncPointer extends AbstractGeneration<FunctionPtrType> {
    public FuncPointer(PackagePath packagePath, Holder<FunctionPtrType> type) {
        super(packagePath, type);
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        var type = new HashSet<>(super.getDefineImportTypes());
        type.addAll(CommonTypes.SpecificTypes.Utils.getUseImportTypes());
        type.addAll(CommonTypes.BasicOperations.Info.getUseImportTypes());
        type.addAll(CommonTypes.BasicOperations.Operation.getUseImportTypes());
        type.addAll(CommonTypes.BindTypeOperations.PtrOp.getUseImportTypes());
        type.addAll(CommonTypes.FFMTypes.FUNCTION_DESCRIPTOR.getUseImportTypes());
        type.addAll(CommonTypes.FFMTypes.ARENA.getUseImportTypes());
        type.addAll(CommonTypes.FFMTypes.METHOD_HANDLE.getUseImportTypes());
        type.addAll(CommonTypes.FFMTypes.METHOD_HANDLES.getUseImportTypes());
        type.addAll(CommonTypes.FFMTypes.MEMORY_LAYOUT.getUseImportTypes());
        type.addAll(CommonTypes.FFMTypes.MEMORY_SEGMENT.getUseImportTypes());
        type.addAll(typePkg.type().getArgs().stream().map(FunctionPtrType.Arg::type)
                .map(typeRefer -> ((TypeAttr.OperationType) typeRefer).getOperation()
                        .getCommonOperation().getUpperType().typeRefers()).flatMap(Collection::stream)
                .map(TypeAttr.TypeRefer::getUseImportTypes).flatMap(Collection::stream).collect(Collectors.toSet()));
        type.addAll(typePkg.type().getArgs().stream().map(FunctionPtrType.Arg::type)
                .map(typeRefer -> ((TypeAttr.OperationType) typeRefer).getOperation()
                        .getCommonOperation().makeOperation().typeRefers()).flatMap(Collection::stream)
                .map(TypeAttr.TypeRefer::getUseImportTypes).flatMap(Collection::stream).collect(Collectors.toSet()));
        FuncPtrUtils.getFuncArgPrimitives(typePkg.type().getArgs().stream()).forEach(p -> {
            if (p.getFfmType() != null)
                type.addAll(p.getFfmType().getUseImportTypes());
            type.addAll(CommonTypes.FFMTypes.VALUE_LAYOUT.getUseImportTypes());
        });
        return type;
    }

    @Override
    public void generate(Dependency dependency) {
        new FuncProtocolGenerator(this, dependency).generate();
    }
}
