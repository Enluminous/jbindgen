package generator.generation.generator;

import generator.Dependency;
import generator.generation.Array;

public class ArrayGenerator implements Generator {
    private final Array array;
    private final Dependency dependency;

    public ArrayGenerator(Array array, Dependency dependency) {
        this.array = array;
        this.dependency = dependency;
    }

    @Override
    public void generate() {
        
    }
}
