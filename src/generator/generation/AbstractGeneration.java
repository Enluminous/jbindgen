package generator.generation;

import generator.config.PackagePath;

import java.nio.file.Path;

public sealed abstract class AbstractGeneration permits ConstValues, EnumGen, FuncPointer, FuncSymbols, StructGen, ValueGen, VarSymbols {
    protected final PackagePath packagePath;

    public AbstractGeneration(PackagePath packagePath) {
        this.packagePath = packagePath;
    }

    public Path getFilePath() {
        return packagePath.getPath();
    }

    public String getImport() {
        return packagePath.getImport();
    }
}
