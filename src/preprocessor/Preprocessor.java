package preprocessor;

import analyser.Declare;
import analyser.Function;
import analyser.Para;
import analyser.types.*;
import analyser.types.Enum;
import analyser.types.Record;
import generator.Generator;
import generator.config.Config;
import generator.config.PackagePath;
import generator.generation.FuncSymbols;
import generator.types.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private static final HashMap<StructType, Record> fakeStructs = new HashMap<>();

    private static StructType getFakedStruct(long byteSize, String memoryLayout, String typeName, Record record) {
        StructType type = new StructType(byteSize, memoryLayout, typeName, List.of());
        if (fakeStructs.containsKey(type))
            return type;
        fakeStructs.put(type, record);
        return type;
    }

    private static final HashMap<FunctionType, TypeFunction> fakeFuncs = new HashMap<>();

    private static FunctionType getFakedTypeFunction(String typeName, TypeAttr.NType retType, TypeFunction typeFunction) {
        FunctionType type = new FunctionType(typeName, List.of(), retType);
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
    private static TypeAttr.NType conv(Type type, String name) {
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


    public Preprocessor(List<Function> functions) {
        ArrayList<FunctionType> functionTypes = new ArrayList<>();
        for (Function function : functions) {
            ArrayList<FunctionType.Arg> args = new ArrayList<>();
            for (Para para : function.paras()) {
                args.add(new FunctionType.Arg(para.paraName(), (TypeAttr.NormalType) conv(para.paraType(), null)));
            }
            functionTypes.add(new FunctionType(function.name(), args, conv(function.ret(), null)));
        }

        new HashMap<>(fakeStructs).forEach((k, v) -> {
            ArrayList<StructType.Member> members = new ArrayList<>();
            for (Para member : v.getMembers()) {
                members.add(new StructType.Member((TypeAttr.AbstractType) conv(member.paraType(), null), member.paraName(), member.offset().orElse(-1), member.bitWidth().orElse(-1)));
            }
            k.setMembers(members);
        });

        new HashMap<>(fakeFuncs).forEach((k, v) -> {
            ArrayList<FunctionType.Arg> args = new ArrayList<>();
            for (Para para : v.getParas()) {
                args.add(new FunctionType.Arg(para.paraName(), (TypeAttr.NormalType) conv(para.paraType(), null)));
            }
            k.setArgs(args);
        });

        FuncSymbols funcSymbols = new FuncSymbols(new PackagePath(Path.of("test-out")), functionTypes);
        Generator generator = new Generator(List.of(), List.of(funcSymbols));
        generator.generate();
    }
}