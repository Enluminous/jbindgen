package generator.generation.generator;

import generator.Dependency;
import generator.Utils;
import generator.generation.RefOnly;
import generator.types.TypeAttr;

public class RefOnlyGenerator implements Generator {
    private final RefOnly refOnly;
    private final Dependency dependency;

    public RefOnlyGenerator(RefOnly refOnly, Dependency dependency) {
        this.refOnly = refOnly;
        this.dependency = dependency;
    }

    @Override
    public void generate() {
        String out = refOnly.getTypePkg().packagePath().makePackage();
        out += makeContent(refOnly.getTypePkg().type().typeName(TypeAttr.NameType.GENERIC), Generator.extractImports(refOnly, dependency));
        Utils.write(refOnly.getTypePkg().packagePath().getFilePath(), out);
    }


    private static String makeContent(String className, String imports) {
        return """
                %2$s
                
                public class %1$s {
                    private %1$s() {
                        throw new UnsupportedOperationException();
                    }
                
                    public static final Info.Operations<%1$s> OPERATIONS = Info.makeOperations();
                }
                """.formatted(className, imports);
    }
}
