package generator.generation.generator;

import generator.Dependency;
import generator.Utils;
import generator.generation.RefOnly;

public class RefOnlyGenerator implements Generator {
    private final RefOnly refOnly;
    private final Dependency dependency;

    public RefOnlyGenerator(RefOnly refOnly, Dependency dependency) {
        this.refOnly = refOnly;
        this.dependency = dependency;
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
