package generator;

import analyser.Function;
import analyser.Para;
import analyser.types.*;
import analyser.types.Enum;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Generator {
    private final SharedValueGeneration sharedValueGeneration;
    private final SharedNativeGeneration sharedNativeGeneration;
    private final PrimitiveValueGeneration primitiveValueGeneration;
    private final StructGeneration structGeneration;


    private final HashSet<TypeFunction> typeFunctions;
    private final HashSet<Type> primitiveTypes;
    private final HashSet<Struct> structs;
    private final HashSet<Enum> enums;

    public Generator(ArrayList<Function> funcs, HashMap<String, Type> types, String basePackage, Path path) {
        sharedValueGeneration = new SharedValueGeneration(basePackage, path.resolve("shared"));
        sharedNativeGeneration = new SharedNativeGeneration(basePackage, path.resolve("shared"));
        primitiveValueGeneration = new PrimitiveValueGeneration(basePackage, path.resolve("values"));
        structGeneration = new StructGeneration(basePackage, path);

        typeFunctions = new HashSet<>();
        primitiveTypes = new HashSet<>();
        structs = new HashSet<>();
        enums = new HashSet<>();

        HashSet<Type> usedTypes = new HashSet<>();
        for (Function function : funcs) {
            for (Para para : function.paras()) {
                usedTypes.add(para.paraType());
            }
            usedTypes.add(function.ret());
        }

        HashSet<Type> visited = new HashSet<>();
        usedTypes.forEach(v -> {
            processType(visited, v, primitiveTypes, structs, enums, typeFunctions);
        });
    }

    private static void processType(HashSet<Type> visited, Type type, HashSet<Type> primitiveTypes, HashSet<Struct> structs,
                                    HashSet<Enum> enums, HashSet<TypeFunction> typeFunctions) {
        if (visited.contains(type))
            return;
        visited.add(type);

        switch (type) {
            case Array a -> {
                processType(visited, a.getElementType(), primitiveTypes, structs, enums, typeFunctions);
            }
            case Elaborated e -> {
                processType(visited, e.getTarget(), primitiveTypes, structs, enums, typeFunctions);
                if (Utils.isPrimitiveType(e)) {
                    if (e.getTypeName().contains("const "))
                        return;
                    if (e.getTypeName().contains("volatile "))
                        return;
                    primitiveTypes.add(e);
                }
            }
            case Enum e -> {
                enums.add(e);
            }
            case Pointer p -> {
                processType(visited, p.getTarget(), primitiveTypes, structs, enums, typeFunctions);
            }
            case Struct s -> {
                structs.add(s);
                for (Para para : s.getParas()) {
                    processType(visited, para.paraType(), primitiveTypes, structs, enums, typeFunctions);
                }
            }
            case TypeDef d -> {
                if (Utils.isPrimitiveType(d)) primitiveTypes.add(d);
                processType(visited, d.getTarget(), primitiveTypes, structs, enums, typeFunctions);
            }
            case TypeFunction f -> {
                typeFunctions.add(f);
                return;
            }
            case Union u -> {
                return;
            }
            case Primitive p -> {
                return;
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public void generate() {
        sharedValueGeneration.gen();
        sharedNativeGeneration.gen();

        for (Type flatType : primitiveTypes) {
            primitiveValueGeneration.gen(flatType);
        }

        for (Struct struct : structs) {
            structGeneration.gen(struct);
        }
    }
}
