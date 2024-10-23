package preprocessor;

import analyser.Declare;
import analyser.Function;
import analyser.Para;
import analyser.types.*;
import analyser.types.Array;
import analyser.types.Enum;
import analyser.types.Record;
import generator.Generator;
import generator.PackagePath;
import generator.generation.*;
import generator.generation.Void;
import generator.types.*;

import java.nio.file.Path;
import java.util.*;

import static utils.CommonUtils.Assert;

public class Preprocessor {
    private static final HashMap<String, CommonTypes.BindTypes> map = new HashMap<>();

    static {
        // integers
        map.put("char", CommonTypes.BindTypes.I8);
        map.put("short", CommonTypes.BindTypes.I16);
        map.put("int", CommonTypes.BindTypes.I32);
        map.put("long", CommonTypes.BindTypes.I64);
        map.put("long long", CommonTypes.BindTypes.I64);

        /// floats
        map.put("float", CommonTypes.BindTypes.FP32);
        map.put("double", CommonTypes.BindTypes.FP64);

        /// pointer
        map.put("", CommonTypes.BindTypes.Pointer);

        /// ext
        map.put("", CommonTypes.BindTypes.FP16);
        map.put("", CommonTypes.BindTypes.FP128);
        map.put("", CommonTypes.BindTypes.I128);
    }

    private static String getName(String nullAbleName, String name) {
        String ret = nullAbleName;
        if (ret == null)
            ret = name;
        String cutStr = "const ";
        if (ret.startsWith(cutStr)) {
            ret = ret.substring(cutStr.length());
        }
        return ret;
    }

    // empty paras to avoid endless loop
    private final HashMap<StructType, Record> fakeStructs = new HashMap<>();

    private StructType getFakedStruct(long byteSize, String memoryLayout, String typeName, Record record) {
        StructType type = new StructType(byteSize, memoryLayout, typeName, List.of());
        if (fakeStructs.containsKey(type))
            return type;
        fakeStructs.put(type, record);
        return type;
    }

    private final HashMap<FunctionPtrType, TypeFunction> fakeFuncs = new HashMap<>();

    private FunctionPtrType getFakedTypeFunction(String typeName, TypeAttr.NType retType, TypeFunction typeFunction) {
        FunctionPtrType type = new FunctionPtrType(typeName, List.of(), retType);
        if (fakeFuncs.containsKey(type))
            return type;
        fakeFuncs.put(type, typeFunction);
        return type;
    }

    /**
     * conv analyser's type to generator's
     *
     * @param type analyser type
     * @param name type name, null means refer to type's displayName
     * @return converted type
     */
    private TypeAttr.NType conv(Type type, String name) {
        switch (type) {
            case Array array -> {
                return new ArrayType(getName(name, array.getDisplayName()), array.getElementCount(), (TypeAttr.NormalType) conv(array.getElementType(), null), array.getSizeof());
            }
            case Enum anEnum -> {
                TypeAttr.NType conv = conv(anEnum.getDeclares().getFirst().type(), null);
                ValueBasedType valueBasedType = (ValueBasedType) conv;
                List<EnumType.Member> members = new ArrayList<>();
                for (Declare declare : anEnum.getDeclares()) {
                    members.add(new EnumType.Member(Long.parseLong(declare.value()), declare.name()));
                }
                return new EnumType(valueBasedType.getBindTypes(), anEnum.getDisplayName(), members);
            }
            case Pointer pointer -> {
                return new PointerType(conv(pointer.getTarget(), null));
            }
            case Primitive primitive -> {
                String s = getName(name, primitive.getDisplayName());
                String primitiveName = primitive.getTypeName();
                primitiveName = primitiveName.replace("const ", "").replace("unsigned ", "").replace("volatile ", "");
                if (primitiveName.equals("void"))
                    return new VoidType(s);
                CommonTypes.BindTypes bindTypes = map.get(primitiveName);
                if (bindTypes == null)
                    throw new RuntimeException();
                Assert(bindTypes.getPrimitiveType().getByteSize() == primitive.getSizeof(), "Unhandled Data Model");
                return new ValueBasedType(s, bindTypes);
            }
            case Record record -> {
                return getFakedStruct(record.getSizeof(), "", getName(name, record.getDisplayName()), record);
            }
            case TypeDef typeDef -> {
                return conv(typeDef.getTarget(), getName(name, typeDef.getDisplayName()));
            }
            case TypeFunction typeFunction -> {
                return getFakedTypeFunction(getName(name, typeFunction.getDisplayName()), conv(typeFunction.getRet(), null), typeFunction);
            }
        }
    }

    HashSet<TypeAttr.Type> alreadyWalked = new HashSet<>();

    void depWalker(TypeAttr.Type in, HashSet<ArrayType> arr, HashSet<EnumType> enu,
                   HashSet<PointerType> ptr, HashSet<ValueBasedType> value,
                   HashSet<StructType> struct, HashSet<FunctionPtrType> funPtr) {
        if (alreadyWalked.contains(in))
            return;
        alreadyWalked.add(in);
        switch (in) {
            case RefOnlyType refOnlyType -> {
                return;
            }
            case TypeAttr.NormalType normalType -> {
                switch (normalType) {
                    case TypeAttr.AbstractType abstractType -> {
                        switch (abstractType) {
                            case ArrayType arrayType -> {
                                arr.add(arrayType);
                                depWalker(arrayType.getNormalType(), arr, enu, ptr, value, struct, funPtr);
                            }
                            case EnumType enumType -> {
                                enu.add(enumType);
                            }
                            case FunctionPtrType functionPtrType -> {
                                funPtr.add(functionPtrType);
                                functionPtrType.getReturnType().ifPresent(r -> {
                                    depWalker(r, arr, enu, ptr, value, struct, funPtr);
                                });
                                for (FunctionPtrType.Arg arg : functionPtrType.getArgs()) {
                                    depWalker(arg.type(), arr, enu, ptr, value, struct, funPtr);
                                }
                            }
                            case PointerType pointerType -> {
                                ptr.add(pointerType);
                                depWalker(pointerType.getPointee(), arr, enu, ptr, value, struct, funPtr);
                            }
                            case StructType structType -> {
                                struct.add(structType);
                                for (StructType.Member member : structType.getMembers()) {
                                    depWalker(member.type(), arr, enu, ptr, value, struct, funPtr);
                                }
                            }
                            case ValueBasedType valueBasedType -> {
                                value.add(valueBasedType);
                            }
                        }
                    }
                }
            }
            case VoidType voidType -> {
                return;
            }
            case CommonTypes.BaseType baseType -> {
                switch (baseType) {
                    case CommonTypes.BindTypes bindTypes -> {
                        return;
                    }
                    case CommonTypes.ListTypes listTypes -> {
                        return;
                    }
                    case CommonTypes.Primitives primitives -> {
                        return;
                    }
                    case CommonTypes.SpecificTypes specificTypes -> {
                        return;
                    }
                }
            }
        }
    }

    public Preprocessor(List<Function> functions) {
        ArrayList<FunctionPtrType> functionPtrTypes = new ArrayList<>();

        for (Function function : functions) {
            ArrayList<FunctionPtrType.Arg> args = new ArrayList<>();
            for (Para para : function.paras()) {
                args.add(new FunctionPtrType.Arg(para.paraName(), (TypeAttr.NormalType) conv(para.paraType(), null)));
            }
            functionPtrTypes.add(new FunctionPtrType(function.name(), args, conv(function.ret(), null)));
        }

        new HashMap<>(fakeStructs).forEach((k, v) -> {
            ArrayList<StructType.Member> members = new ArrayList<>();
            for (Para member : v.getMembers()) {
                members.add(new StructType.Member((TypeAttr.AbstractType) conv(member.paraType(), null), member.paraName(), member.offset().orElse(-1), member.bitWidth().orElse(-1)));
            }
            k.setMembers(members);
        });

        new HashMap<>(fakeFuncs).forEach((k, v) -> {
            ArrayList<FunctionPtrType.Arg> args = new ArrayList<>();
            for (Para para : v.getParas()) {
                args.add(new FunctionPtrType.Arg(para.paraName(), (TypeAttr.NormalType) conv(para.paraType(), null)));
            }
            k.setArgs(args);
        });

        HashSet<ArrayType> depArrayType = new HashSet<>();
        HashSet<EnumType> depEnumType = new HashSet<>();
        HashSet<PointerType> depPointerType = new HashSet<>();
        HashSet<ValueBasedType> depValueBasedType = new HashSet<>();
        HashSet<StructType> depStructType = new HashSet<>();
        HashSet<FunctionPtrType> depFunctionPtrType = new HashSet<>();
        for (FunctionPtrType functionPtrType : functionPtrTypes) {
            depWalker(functionPtrType, depArrayType, depEnumType, depPointerType, depValueBasedType, depStructType, depFunctionPtrType);
        }

        PackagePath root = new PackagePath(Path.of("test-out")).add("test").end("test_class");
        ArrayList<Generation<?>> generations = new ArrayList<>();
        generations.add(new FuncSymbols(root, functionPtrTypes));
        generations.add(Common.makeBindTypes(root));
        generations.add(Common.makeSpecific(root));
        generations.add(Common.makeListTypes(root));
        generations.add(Common.makePrimitives());
        generations.add(new Void(root, VoidType.JAVA_VOID));

        ArrayList<Generation<?>> depGen = new ArrayList<>();
        depArrayType.forEach(d -> depGen.add(new generator.generation.Array(root, d)));
        depEnumType.forEach(d -> depGen.add(new generator.generation.Enumerate(root, d)));
        depPointerType.forEach(d -> depGen.add(new generator.generation.StandardPointer(root, d)));
        depValueBasedType.forEach(d -> depGen.add(new generator.generation.Value(root, d)));
        depStructType.forEach(d -> depGen.add(new generator.generation.Structure(root, d)));
        depFunctionPtrType.forEach(d -> depGen.add(new generator.generation.FuncPointer(root, d)));

        Generator generator = new Generator(depGen, generations);
        generator.generate();
    }
}