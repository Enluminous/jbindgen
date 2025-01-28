package generator.generation.generator;

import generator.Utils;
import generator.generation.VoidBased;
import generator.types.TypeAttr;

public class VoidBasedGenerator implements Generator {
    private final VoidBased voidType;

    public VoidBasedGenerator(VoidBased voidType) {
        this.voidType = voidType;
    }

    @Override
    public void generate() {
        String out = voidType.getTypePkg().packagePath().makePackage();
        out += makeContent(voidType.getTypePkg().type().typeName(TypeAttr.NameType.GENERIC));
        Utils.write(voidType.getTypePkg().packagePath().getFilePath(), out);
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
