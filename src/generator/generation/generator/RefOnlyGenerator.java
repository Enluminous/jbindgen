package generator.generation.generator;

import generator.Utils;
import generator.generation.RefOnly;
import generator.types.TypeAttr;

public class RefOnlyGenerator implements Generator {
    private final RefOnly refOnly;

    public RefOnlyGenerator(RefOnly refOnly) {
        this.refOnly = refOnly;
    }

    @Override
    public void generate() {
        String out = refOnly.getTypePkg().packagePath().makePackage();
        out += makeContent(refOnly.getTypePkg().type().typeName(TypeAttr.NameType.GENERIC));
        Utils.write(refOnly.getTypePkg().packagePath().getFilePath(), out);
    }


    private static String makeContent(String className) {
        return """
                public class %1$s {
                    private %1$s() {
                        throw new UnsupportedOperationException();
                    }
                }
                """.formatted(className);
    }
}
