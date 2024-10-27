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

        // floats
        map.put("float", CommonTypes.BindTypes.FP32);
        map.put("double", CommonTypes.BindTypes.FP64);

        // pointer
        map.put("", CommonTypes.BindTypes.Pointer);

        // ext
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

    private FunctionPtrType getFakedTypeFunction(String typeName, TypeAttr.ReferenceType retType, TypeFunction typeFunction) {
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
     * @param name type name, null means refer to type's displayName, normally be used for TypeDef->Primitive
     * @return converted type
     */
    private TypeAttr.ReferenceType conv(Type type, String name) {
        switch (type) {
            case Array array -> {
                return new ArrayType(getName(name, array.getDisplayName()), array.getElementCount(), (TypeAttr.SizedType) conv(array.getElementType(), null), array.getSizeof());
            }
            case Enum anEnum -> {
                TypeAttr.ReferenceType conv = conv(anEnum.getDeclares().getFirst().type(), null);
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
                String primitiveName = primitive.getTypeName();
                primitiveName = primitiveName.replace("const ", "").replace("unsigned ", "").replace("volatile ", "");
                if (primitiveName.equals("void")) {
                    return new VoidType(name);
                }
                CommonTypes.BindTypes bindTypes = map.get(primitiveName);
                if (bindTypes == null)
                    throw new RuntimeException();
                Assert(bindTypes.getPrimitiveType().getByteSize() == primitive.getSizeof(), "Unhandled Data Model");
                if (name == null) {
                    name = bindTypes.getRawName();
                }
                return new ValueBasedType(name, bindTypes);
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
                   HashSet<StructType> struct, HashSet<FunctionPtrType> funPtr, HashSet<VoidType> voi) {
        if (alreadyWalked.contains(in))
            return;
        alreadyWalked.add(in);
        switch (in) {
            case RefOnlyType refOnlyType -> {
                return;
            }
            case TypeAttr.SizedType normalType -> {
                switch (normalType) {
                    case AbstractGenerationType abstractType -> {
                        switch (abstractType) {
                            case ArrayType arrayType -> {
                                arr.add(arrayType);
                                for (TypeAttr.Type r : arrayType.getDefineReferTypes()) {
                                    depWalker(r, arr, enu, ptr, value, struct, funPtr, voi);
                                }
                            }
                            case EnumType enumType -> {
                                enu.add(enumType);
                                for (TypeAttr.Type r : enumType.getDefineReferTypes()) {
                                    depWalker(r, arr, enu, ptr, value, struct, funPtr, voi);
                                }
                            }
                            case FunctionPtrType functionPtrType -> {
                                funPtr.add(functionPtrType);
                                for (TypeAttr.Type r : functionPtrType.getDefineReferTypes()) {
                                    depWalker(r, arr, enu, ptr, value, struct, funPtr, voi);
                                }
                            }
                            case StructType structType -> {
                                struct.add(structType);
                                for (TypeAttr.Type r : structType.getDefineReferTypes()) {
                                    depWalker(r, arr, enu, ptr, value, struct, funPtr, voi);
                                }
                            }
                            case ValueBasedType valueBasedType -> {
                                value.add(valueBasedType);
                                for (TypeAttr.Type r : valueBasedType.getDefineReferTypes()) {
                                    depWalker(r, arr, enu, ptr, value, struct, funPtr, voi);
                                }
                            }
                        }
                    }
                    case PointerType pointerType -> {
                        ptr.add(pointerType);
                        for (TypeAttr.Type r : pointerType.getDefineReferTypes()) {
                            depWalker(r, arr, enu, ptr, value, struct, funPtr, voi);
                        }
                    }
                }
            }
            case VoidType voidType -> {
                voi.add(voidType);
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
                args.add(new FunctionPtrType.Arg(para.paraName(), conv(para.paraType(), null)));
            }
            functionPtrTypes.add(new FunctionPtrType(function.name(), args, conv(function.ret(), null)));
        }

        new HashMap<>(fakeStructs).forEach((k, v) -> {
            ArrayList<StructType.Member> members = new ArrayList<>();
            for (Para member : v.getMembers()) {
                members.add(new StructType.Member(conv(member.paraType(), null), member.paraName(), member.offset().orElse(-1), member.bitWidth().orElse(-1)));
            }
            k.setMembers(members);
        });

        new HashMap<>(fakeFuncs).forEach((k, v) -> {
            ArrayList<FunctionPtrType.Arg> args = new ArrayList<>();
            for (Para para : v.getParas()) {
                args.add(new FunctionPtrType.Arg(para.paraName(), conv(para.paraType(), null)));
            }
            k.setArgs(args);
        });

        HashSet<ArrayType> depArrayType = new HashSet<>();
        HashSet<EnumType> depEnumType = new HashSet<>();
        HashSet<PointerType> depPointerType = new HashSet<>();
        HashSet<ValueBasedType> depValueBasedType = new HashSet<>();
        HashSet<StructType> depStructType = new HashSet<>();
        HashSet<FunctionPtrType> depFunctionPtrType = new HashSet<>();
        HashSet<VoidType> depVoidType = new HashSet<>();

        for (FunctionPtrType functionPtrType : functionPtrTypes) {
            depWalker(functionPtrType, depArrayType, depEnumType, depPointerType, depValueBasedType, depStructType, depFunctionPtrType, depVoidType);
        }

        PackagePath root = new PackagePath(Path.of("test-out/src")).add("test").end("test_class");
        HashMap<TypeAttr.Type, Generation<?>> generations = new HashMap<>();
        FuncSymbols funcSymbols = new FuncSymbols(root, functionPtrTypes);
        funcSymbols.getImplTypes().forEach(type -> generations.put(type.type(), funcSymbols));
        for (Common common : List.of(Common.makeBindTypes(root), Common.makeListTypes(root), Common.makeSpecific(root), Common.makePrimitives())) {
            common.getImplTypes().forEach(type -> generations.put(type.type(), common));
        }
        ArrayList<Generation<?>> depGen = new ArrayList<>();
        depArrayType.forEach(d -> depGen.add(new generator.generation.Array(root, d)));
        depEnumType.forEach(d -> depGen.add(new generator.generation.Enumerate(root, d)));
        depValueBasedType.forEach(d -> depGen.add(new generator.generation.Value(root, d)));
        depStructType.forEach(d -> depGen.add(new generator.generation.Structure(root, d)));
        depFunctionPtrType.forEach(d -> depGen.add(new generator.generation.FuncPointer(root, d)));

        Generator generator = new Generator(depGen, generations::get);
        generator.generate();
    }
}