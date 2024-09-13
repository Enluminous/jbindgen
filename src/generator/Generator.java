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


    private final HashMap<String, Struct> structs;

    private static class GenEnum {
        private final String name;
        private final Enum anEnum;

        public GenEnum(String name, Enum anEnum) {
            this.name = name;
            this.anEnum = anEnum;
        }

        public String getName() {
            return name;
        }

        public Enum getAnEnum() {
            return anEnum;
        }
    }

    private static class GenUnion {
        private final String name;
        private final Union union;

        public GenUnion(String name, Union union) {
            this.name = name;
            this.union = union;
        }
    }

    private static class GenStruct {
        private final String name;
        private final Struct struct;
        private int unnamedCount = 0;

        public GenStruct(String name, Struct struct) {
            this.name = name;
            this.struct = struct;
        }

        public Struct getStruct() {
            return struct;
        }

        public int addUnnamedCount() {
            return ++unnamedCount;
        }

        public int getUnnamedCount() {
            return unnamedCount;
        }

        public String getName() {
            return name;
        }
    }

    public Generator(ArrayList<Function> funcs, HashMap<String, Type> types, String basePackage, Path path) {
        sharedValueGeneration = new SharedValueGeneration(basePackage, path.resolve("shared"));
        sharedNativeGeneration = new SharedNativeGeneration(basePackage, path.resolve("shared"));
        primitiveValueGeneration = new PrimitiveValueGeneration(basePackage, path.resolve("values"));
        structGeneration = new StructGeneration(basePackage, path);

        structs = new HashMap<>();

        HashSet<TypeFunction> typeFunctions = new HashSet<>();
        HashMap<String, Type> primitiveTypes = new HashMap<>();
        HashMap<String, GenEnum> enums = new HashMap<>();
        HashMap<String, GenUnion> unions = new HashMap<>();

        HashSet<Type> usedTypes = new HashSet<>();
        for (Function function : funcs) {
            for (Para para : function.paras()) {
                usedTypes.add(para.paraType());
            }
            usedTypes.add(function.ret());
        }

        HashMap<String, GenStruct> stagingStructs = new HashMap<>();
        HashSet<Type> visited = new HashSet<>();
        usedTypes.forEach(v -> {
            processType(visited, v, primitiveTypes, stagingStructs, enums, typeFunctions, unions);
        });

        HashMap<String, Struct> structs1 = new HashMap<>();
        stagingStructs.forEach((k, v) -> {
            Struct s = v.struct.setName(v.getName());
            if (v.getName().contains("::(unnamed at")) {
                String name = cutStructName(v.getName());
                name = name.substring(0, name.indexOf("::"));
                name += "$" + v.addUnnamedCount();
                s = s.setName(name);
            }

            String name = cutStructName(s.getTypeName());
            if (!name.equals(s.getTypeName())) {
                if (structs1.containsKey(name))
                    return;
                name = "struct$" + name;
                s = s.setName(name);
            }
            structs1.put(s.getTypeName(), s);
        });

        structs1.forEach((k, s) -> {
            Struct n = new Struct(s.getTypeName());
            for (Para para : s.getParas()) {
                n.addPara(new Para(simplyType(para.paraType(), structs1, primitiveTypes, enums, unions), para.paraName()));
            }
            structs.put(n.getTypeName(), n);
        });
    }

    private static String cutStructName(String name) {
        String str = "struct ";
        if (name.contains(str)) {
            name = name.substring(str.length());
        }
        return name;
    }

    private static Type simplyType(Type type, HashMap<String, Struct> structs, HashMap<String, Type> primitives,
                                   HashMap<String, GenEnum> enums, HashMap<String, GenUnion> unions) {
        if (type instanceof Elaborated e) {
            return simplyType(e.getTarget(), structs, primitives, enums, unions);
        }
        if (type instanceof Pointer p) {
            return new Pointer(p.getTypeName(), simplyType(p.getTarget(), structs, primitives, enums, unions));
        }
        if (type instanceof Array a) {
            return new Array(a.getTypeName(), simplyType(a.getElementType(), structs, primitives, enums, unions), a.getElementCount());
        }

        if (Utils.isPrimitiveType(type)) {
            if (primitives.containsKey(type.getTypeName()))
                return primitives.get(type.getTypeName());
        }
        if (Utils.findRootStruct(type) != null) {
            if (structs.containsKey(type.getTypeName()))
                return structs.get(type.getTypeName());
        }
        if (Utils.findRootEnum(type) != null) {
            if (enums.containsKey(type.getTypeName()))
                return enums.get(type.getTypeName()).anEnum;
        }

        if (Utils.findRootUnion(type) != null) {
            if (unions.containsKey(type.getTypeName()))
                return unions.get(type.getTypeName()).union;
        }

        if (Utils.getTypeMappings().containsKey(type.getTypeName())) {
            return type;
        }
        throw new RuntimeException();
    }

    // walk 'type' and add record used types
    private static void processType(HashSet<Type> visited, Type type, HashMap<String, Type> primitiveTypes,
                                    HashMap<String, GenStruct> structs, HashMap<String, GenEnum> enums,
                                    HashSet<TypeFunction> typeFunctions, HashMap<String, GenUnion> unions) {
        if (visited.contains(type))
            return;
        visited.add(type);

        switch (type) {
            case Array a -> {
                processType(visited, a.getElementType(), primitiveTypes, structs, enums, typeFunctions, unions);
            }
            case Elaborated e -> {
                processType(visited, e.getTarget(), primitiveTypes, structs, enums, typeFunctions, unions);
            }
            case Enum e -> {
                enums.put(e.getTypeName(), new GenEnum(e.getTypeName(), e));
            }
            case Pointer p -> {
                processType(visited, p.getTarget(), primitiveTypes, structs, enums, typeFunctions, unions);
            }
            case Struct s -> {
                structs.put(s.getTypeName(), new GenStruct(s.getTypeName(), s));
                for (Para para : s.getParas()) {
                    processType(visited, para.paraType(), primitiveTypes, structs, enums, typeFunctions, unions);
                }
            }
            case TypeDef d -> {
                if (Utils.isPrimitiveType(d))
                    primitiveTypes.put(d.getTypeName(), d);
                Struct rootStruct = Utils.findRootStruct(d);
                if (rootStruct != null) {
                    structs.put(d.getTypeName(), new GenStruct(d.getTypeName(), rootStruct));
                }
                Enum rootEnum = Utils.findRootEnum(d);
                if (rootEnum != null) {
                    enums.put(d.getTypeName(), new GenEnum(d.getTypeName(), rootEnum));
                }
                Union rootUnion = Utils.findRootUnion(d);
                if (rootUnion != null) {
                    unions.put(d.getTypeName(), new GenUnion(d.getTypeName(), rootUnion));
                }
                processType(visited, d.getTarget(), primitiveTypes, structs, enums, typeFunctions, unions);
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

//        for (Type flatType : primitiveTypes) {
//            primitiveValueGeneration.gen(flatType);
//        }

//        for (Struct struct : structs) {
//            structGeneration.gen(struct);
//        }
    }
}
