package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.generation.generator.FuncProtocolGenerator;
import generator.generation.generator.FuncPtrUtils;
import generator.types.CommonTypes;
import generator.types.FunctionPtrType;
import generator.types.Holder;
import generator.types.TypeAttr;

import java.util.HashSet;
import java.util.Set;

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
        type.addAll(CommonTypes.FFMTypes.FUNCTION_DESCRIPTOR.getUseImportTypes());
        type.addAll(CommonTypes.FFMTypes.ARENA.getUseImportTypes());
        type.addAll(CommonTypes.FFMTypes.METHOD_HANDLES.getUseImportTypes());
        FuncPtrUtils.getFuncArgPrimitives(typePkg.type().getArgs().stream()).forEach(p -> {
            if (p.getFfmType() != null)
                type.addAll(p.getFfmType().getUseImportTypes());
            type.addAll(CommonTypes.FFMTypes.ADDRESS_LAYOUT.getUseImportTypes());
        });
        return type;
    }

    @Override
    public void generate(Dependency dependency) {
        new FuncProtocolGenerator(this, dependency).generate();
    }
}
