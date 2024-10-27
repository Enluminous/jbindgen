package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.TypePkg;
import generator.generation.generator.EnumGenerator;
import generator.types.EnumType;


public final class Enumerate extends AbstractGeneration<EnumType> {
    public Enumerate(PackagePath packagePath, EnumType type) {
        super(packagePath, type);
    }

    @Override
    public void generate(Dependency dependency) {
        new EnumGenerator(this, dependency).generate();
    }
}
