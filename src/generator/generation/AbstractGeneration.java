package generator.generation;

import generator.config.PackagePath;
import generator.types.TypeAttr;

import java.util.Set;

public sealed abstract class AbstractGeneration permits ConstValues, EnumGen, FuncPointer, FuncSymbols, StructGen, ValueGen, VarSymbols {
    protected final PackagePath packagePath;

    public AbstractGeneration(PackagePath packagePath) {
        this.packagePath = packagePath;
    }

    public PackagePath getPackagePath() {
        return packagePath;
    }

    public abstract Set<TypeAttr.NType> getSelfTypes();

    public abstract Set<TypeAttr.Type> getReferencedTypes();
}
