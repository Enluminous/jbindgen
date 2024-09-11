package generator;

import analyser.types.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class Generator {
    private final SharedValueGeneration sharedValueGeneration;
    private final SharedNativeGeneration sharedNativeGeneration;
    private final ArrayList<Type> flatTypes;
    private final ArrayList<Struct> structs;
    private final PrimitiveValueGeneration primitiveValueGeneration;
    private final StructGeneration structGeneration;

    public Generator(HashMap<String, Type> types, String basePackage, Path path) {
        sharedValueGeneration = new SharedValueGeneration(basePackage, path.resolve("shared"));
        sharedNativeGeneration = new SharedNativeGeneration(basePackage, path.resolve("shared"));
        primitiveValueGeneration = new PrimitiveValueGeneration(basePackage, path.resolve("values"));
        structGeneration = new StructGeneration(basePackage, path);
        flatTypes = new ArrayList<>();
        structs = new ArrayList<>();

        types.forEach((k, v) -> {
            if (Utils.isPrimitiveType(v) && !(v instanceof Primitive)) {
                if (v.getTypeName().startsWith("const "))
                    return;
                if (v.getTypeName().startsWith("volatile "))
                    return;
                if (Utils.findRootPrimitive(v).getTypeName().startsWith("volatile "))
                    return;
                flatTypes.add(v);
            } else {
                Struct rootStruct = Utils.findRootStruct(v);
                if (rootStruct != null) {
                    structs.add(rootStruct);
                }
            }
        });
    }

    public void generate() {
        sharedValueGeneration.gen();
        sharedNativeGeneration.gen();
        for (Type flatType : flatTypes) {
            primitiveValueGeneration.gen(flatType);
        }
        for (Struct struct : structs) {
            structGeneration.gen(struct);
        }
    }
}
