package generator.generation.generator;

import generator.Utils;
import generator.generation.RefOnly;

public class RefOnlyGenerator implements Generator {
    private final RefOnly refOnly;

    public RefOnlyGenerator(RefOnly refOnly) {
        this.refOnly = refOnly;
    }

    @Override
    public void generate() {
        Utils.write(refOnly.getTypePkg().packagePath().getFilePath(), makeContent(refOnly.getTypePkg().type().typeName()));
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
