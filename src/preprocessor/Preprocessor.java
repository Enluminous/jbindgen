package preprocessor;

import analyser.Declare;
import analyser.Function;
import analyser.Para;
import analyser.types.Enum;
import analyser.types.Record;
import analyser.types.*;
import generator.Generator;
import generator.PackagePath;
import generator.generation.Common;
import generator.generation.FuncSymbols;
import generator.generation.Generation;
import generator.generation.ValueBased;
import generator.types.*;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

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
    private final HashMap<String, FakeStructValue> fakeStructs = new HashMap<>();

    record FakeStructValue(StructType s, Record r) {
    }

    private StructType getFakedStruct(long byteSize, String memoryLayout, String typeName, Record record) {
        if (fakeStructs.containsKey(typeName)) {
            if (!Objects.equals(record, fakeStructs.get(typeName).r)) {
                throw new RuntimeException();
            }
            return fakeStructs.get(typeName).s;
        }

        return new StructType(byteSize, memoryLayout, typeName, new StructType.MemberProvider() {
            @Override
            public List<StructType.Member> provide(StructType structType) {
                fakeStructs.put(typeName, new FakeStructValue(structType, record));
                ArrayList<StructType.Member> members = new ArrayList<>();
                for (Para member : record.getMembers()) {
                    members.add(new StructType.Member(conv(member.paraType(), null), member.paraName(), member.offset().orElse(-1), member.bitWidth().orElse(-1)));
                }
                return members;
            }
        });
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
                return new ArrayType(Optional.ofNullable(name), array.getElementCount(), conv(array.getElementType(), null), array.getSizeof());
            }
            case Enum anEnum -> {
                TypeAttr.ReferenceType conv = conv(anEnum.getDeclares().getFirst().type(), null);
                var bindTypes = (CommonTypes.BindTypes) conv;
                List<EnumType.Member> members = new ArrayList<>();
                for (Declare declare : anEnum.getDeclares()) {
                    members.add(new EnumType.Member(Long.parseLong(declare.value()), declare.name()));
                }
                return new EnumType(bindTypes, anEnum.getDisplayName(), members);
            }
            case Pointer pointer -> {
                return new PointerType(conv(pointer.getTarget(), null));
            }
            case Primitive primitive -> {
                String primitiveName = primitive.getTypeName();
                primitiveName = primitiveName.replace("const ", "").replace("unsigned ", "").replace("volatile ", "").replace("signed ", "");
                if (primitiveName.equals("void")) {
                    return name == null ? VoidType.VOID : new VoidType(name);
                }
                CommonTypes.BindTypes bindTypes = map.get(primitiveName);
                if (bindTypes == null)
                    throw new RuntimeException();
                Assert(bindTypes.getPrimitiveType().getByteSize() == primitive.getSizeof(), "Unhandled Data Model");
                if (name == null) {
                    // primitive type
                    return bindTypes;
                }
                return new ValueBasedType(name, bindTypes);
            }
            case Record record -> {
                String typeName = getName(name, record.getDisplayName());
                if (record.isIncomplete())
                    return new RefOnlyType(typeName);
                return getFakedStruct(record.getSizeof(), "", typeName, record);
            }
            case TypeDef typeDef -> {
                return conv(typeDef.getTarget(), getName(name, typeDef.getDisplayName()));
            }
            case TypeFunction typeFunction -> {
                return getFakedTypeFunction(getName(name, typeFunction.getDisplayName()), conv(typeFunction.getRet(), null), typeFunction);
            }
        }
    }

    HashSet<TypeAttr.ReferenceType> alreadyWalked = new HashSet<>();

    void depWalker(TypeAttr.ReferenceType in, HashSet<GenerationTypeHolder<EnumType>> enu,
                   HashSet<GenerationTypeHolder<ValueBasedType>> value,
                   HashSet<GenerationTypeHolder<StructType>> struct,
                   HashSet<GenerationTypeHolder<FunctionPtrType>> funPtr,
                   HashSet<GenerationTypeHolder<VoidType>> voi,
                   HashSet<GenerationTypeHolder<RefOnlyType>> depRefOnlyType) {
        if (alreadyWalked.contains(in))
            return;
        alreadyWalked.add(in);
        switch (in) {
            case RefOnlyType refOnlyType -> {
                for (TypeAttr.ReferenceType r : refOnlyType.getDefineImportTypes()) {
                    depWalker(r, enu, value, struct, funPtr, voi, depRefOnlyType);
                }
                depRefOnlyType.add(refOnlyType.toGenerationTypes().orElseThrow());
            }
            case TypeAttr.SizedType normalType -> {
                switch (normalType) {
                    case AbstractGenerationType abstractType -> {
                        switch (abstractType) {
                            case EnumType enumType -> {
                                enu.add(enumType.toGenerationTypes().orElseThrow());
                                for (TypeAttr.ReferenceType r : enumType.getDefineImportTypes()) {
                                    depWalker(r, enu, value, struct, funPtr, voi, depRefOnlyType);
                                }
                            }
                            case FunctionPtrType functionPtrType -> {
                                funPtr.add(functionPtrType.toGenerationTypes().orElseThrow());
                                for (TypeAttr.ReferenceType r : functionPtrType.getDefineImportTypes()) {
                                    depWalker(r, enu, value, struct, funPtr, voi, depRefOnlyType);
                                }
                            }
                            case StructType structType -> {
                                struct.add(structType.toGenerationTypes().orElseThrow());
                                for (TypeAttr.ReferenceType r : structType.getDefineImportTypes()) {
                                    depWalker(r, enu, value, struct, funPtr, voi, depRefOnlyType);
                                }
                            }
                            case ValueBasedType valueBasedType -> {
                                value.add(valueBasedType.toGenerationTypes().orElseThrow());
                                for (TypeAttr.ReferenceType r : valueBasedType.getDefineImportTypes()) {
                                    depWalker(r, enu, value, struct, funPtr, voi, depRefOnlyType);
                                }
                            }
                        }
                    }
                    case ArrayType arrayType -> {
                        for (TypeAttr.ReferenceType r : arrayType.getDefineImportTypes()) {
                            depWalker(r, enu, value, struct, funPtr, voi, depRefOnlyType);
                        }
                    }
                    case PointerType pointerType -> {
                        for (TypeAttr.ReferenceType r : pointerType.getDefineImportTypes()) {
                            depWalker(r, enu, value, struct, funPtr, voi, depRefOnlyType);
                        }
                    }
                    case CommonTypes.BindTypes bindTypes -> {
                    }
                }
            }
            case VoidType voidType -> {
                voi.add(voidType.toGenerationTypes().orElseThrow());
            }
            case CommonTypes.BaseType baseType -> {
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

        new HashMap<>(fakeFuncs).forEach((k, v) -> {
            ArrayList<FunctionPtrType.Arg> args = new ArrayList<>();
            ArrayList<Para> paras = v.getParas();
            for (int i = 0; i < paras.size(); i++) {
                Para para = paras.get(i);
                String paraName = para.paraName();
                if (paraName == null || paraName.isEmpty()) {
                    paraName = "arg" + i;
                }
                args.add(new FunctionPtrType.Arg(paraName, conv(para.paraType(), null)));
            }
            k.setArgs(args);
        });

        HashSet<GenerationTypeHolder<EnumType>> depEnumType = new HashSet<>();
        HashSet<GenerationTypeHolder<ValueBasedType>> depValueBasedType = new HashSet<>();
        HashSet<GenerationTypeHolder<StructType>> depStructType = new HashSet<>();
        HashSet<GenerationTypeHolder<FunctionPtrType>> depFunctionPtrType = new HashSet<>();
        HashSet<GenerationTypeHolder<VoidType>> depVoidType = new HashSet<>();
        HashSet<GenerationTypeHolder<RefOnlyType>> depRefOnlyType = new HashSet<>();

        for (FunctionPtrType functionPtrType : functionPtrTypes) {
            depWalker(functionPtrType, depEnumType, depValueBasedType,
                    depStructType, depFunctionPtrType, depVoidType, depRefOnlyType);
        }

        PackagePath root = new PackagePath(Path.of("test-out/src")).add("test").end("test_class");
        ArrayList<Generation<?>> generations = new ArrayList<>();
        generations.add(new FuncSymbols(root, functionPtrTypes));
        generations.add(Common.makeBindTypes(root));
        generations.add(Common.makeValueTypes(root));
        generations.add(Common.makeFFMs());
        generations.add(Common.makeListTypes(root));
        generations.add(Common.makeSpecific(root));
        HashMap<GenerationTypeHolder<?>, Generation<?>> depGen = new HashMap<>();
        Consumer<Generation<?>> fillDep = array -> array.getImplTypes().forEach(arrayTypeTypePkg -> depGen.put(arrayTypeTypePkg.typeHolder(), array));

        depEnumType.stream().map(d -> new generator.generation.Enumerate(root, d)).forEach(fillDep);
        depValueBasedType.stream().map(d -> new ValueBased(root, d)).forEach(fillDep);
        depStructType.stream().map(d -> new generator.generation.Structure(root, d)).forEach(fillDep);
        depFunctionPtrType.stream().map(d -> new generator.generation.FuncPointer(root, d)).forEach(fillDep);
        depRefOnlyType.stream().map(d -> new generator.generation.RefOnly(root, d)).forEach(fillDep);
        depVoidType.stream().map(d -> new generator.generation.VoidBased(root, d)).forEach(fillDep);

        Generator generator = new Generator(generations, depGen::get);
        generator.generate();
    }
}