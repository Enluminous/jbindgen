package preprocessor;

import static utils.CommonUtils.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import analyser.Declare;
import analyser.Function;
import analyser.Macro;
import analyser.Para;
import analyser.PrimitiveTypes;
import analyser.types.Array;
import analyser.types.Enum;
import analyser.types.Pointer;
import analyser.types.Primitive;
import analyser.types.Record;
import analyser.types.Type;
import analyser.types.TypeDef;
import analyser.types.TypeFunction;
import generator.Generator;
import generator.TypePkg;
import generator.generation.ArrayNamed;
import generator.generation.Common;
import generator.generation.ConstValues;
import generator.generation.Enumerate;
import generator.generation.FuncPointer;
import generator.generation.FuncSymbols;
import generator.generation.Generation;
import generator.generation.Macros;
import generator.generation.RefOnly;
import generator.generation.Structure;
import generator.generation.SymbolProvider;
import generator.generation.ValueBased;
import generator.generation.VoidBased;
import generator.types.ArrayType;
import generator.types.ArrayTypeNamed;
import generator.types.CommonTypes;
import generator.types.EnumType;
import generator.types.FunctionPtrType;
import generator.types.PointerType;
import generator.types.RefOnlyType;
import generator.types.StructType;
import generator.types.SymbolProviderType;
import generator.types.TypeAttr;
import generator.types.ValueBasedType;
import generator.types.VoidType;

public class Preprocessor {
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

    private final HashMap<String, StructValue> structs = new HashMap<>();

    record StructValue(StructType s, Record r) {
    }

    private ArrayList<StructType.Member> solveMembers(Record record) {
        List<String> constBlack = Utils.getForbidNames();
        List<String> memberBlacks = record.getMembers().stream().map(Para::paraName).toList();
        ArrayList<StructType.Member> members = new ArrayList<>();
        for (Para member : record.getMembers()) {
            String input = member.paraName();
            if (constBlack.contains(input)) {
                input += "$";
                while (memberBlacks.contains(input)) {
                    input += "$";
                }
            }
            long offset = member.offset().orElseThrow();
            long bitSize = member.bitWidth().orElseThrow();
            Assert(offset >= 0);
            Assert(bitSize > 0);
            members.add(new StructType.Member(conv(member.paraType(), null), input,
                    offset, bitSize));
        }
        return members;
    }

    private StructType getStruct(long byteSize, String typeName, Record record) {
        if (structs.containsKey(typeName)) {
            if (!Objects.equals(record, structs.get(typeName).r)) {
                throw new RuntimeException();
            }
            return structs.get(typeName).s;
        }

        return new StructType(byteSize, typeName, structType -> {
            structs.put(typeName, new StructValue(structType, record));
            return solveMembers(record);
        });
    }

    private FunctionPtrType getTypeFunction(String typeName, TypeAttr.TypeRefer retType, TypeFunction typeFunction) {
        ArrayList<FunctionPtrType.Arg> args = new ArrayList<>();
        ArrayList<Para> paras = typeFunction.getParas();
        for (int i = 0; i < paras.size(); i++) {
            Para para = paras.get(i);
            String paraName = para.paraName();
            if (paraName == null || paraName.isEmpty()) {
                paraName = "arg" + i;
            }
            args.add(new FunctionPtrType.Arg(paraName, conv(para.paraType(), null)));
        }
        return new FunctionPtrType(typeName, args, retType);
    }

    private static Type typedefLookUp(Type type) {
        switch (type) {
            case Array array -> {
                return array;
            }
            case Enum anEnum -> {
                return anEnum;
            }
            case Pointer pointer -> {
                return pointer;
            }
            case Primitive primitive -> {
                return primitive;
            }
            case Record record -> {
                return record;
            }
            case TypeDef typeDef -> {
                return typedefLookUp(typeDef.getTarget());
            }
            case TypeFunction typeFunction -> {
                return typeFunction;
            }
        }
    }

    /**
     * conv analyser's type to generator's
     *
     * @param type analyser type
     * @param name type name, null means refer to type's displayName, normally be used for TypeDef->Primitive, Typedef->Pointer
     *             must be specified for single level
     * @return converted type
     */
    private TypeAttr.TypeRefer conv(Type type, String name) {
        switch (type) {
            case Array array -> {
                if (name != null) {
                    return new ArrayTypeNamed(name, array.getElementCount(), conv(array.getElementType(), null), array.getSizeof());
                }
                return new ArrayType(array.getElementCount(), conv(array.getElementType(), null), array.getSizeof());
            }
            case Enum anEnum -> {
                TypeAttr.TypeRefer conv = conv(anEnum.getDeclares().getFirst().type(), null);
                var bindTypes = (CommonTypes.BindTypes) conv;
                List<EnumType.Member> members = new ArrayList<>();
                for (Declare declare : anEnum.getDeclares()) {
                    members.add(new EnumType.Member(Long.parseLong(declare.value()), declare.name()));
                }
                return new EnumType(bindTypes, anEnum.getDisplayName(), members);
            }
            case Pointer pointer -> {
                if (typedefLookUp(pointer.getTarget()) instanceof TypeFunction f) {
                    // typedef void (*callback)(int a, int b);
                    // void accept(callback ptr);
                    // ptr is the pointer of callback.
                    return getTypeFunction(getName(name, f.getDisplayName()), conv(f.getRet(), null), f);
                }

                // name != null means comes from typedef
                PointerType t = new PointerType(conv(pointer.getTarget(), null));
                if (name != null)
                    return new ValueBasedType(name, t);
                return t;
            }
            case Primitive primitive -> {
                PrimitiveTypes.CType primitiveType = primitive.getPrimitiveType();
                if (primitiveType == PrimitiveTypes.CType.C_VOID) {
                    return name == null ? VoidType.VOID : new VoidType(name);
                }
                CommonTypes.BindTypes bindTypes = Utils.conv2BindTypes(primitiveType);
                if (bindTypes == null)
                    throw new RuntimeException();
                Assert(bindTypes.getPrimitiveType().getByteSize() == primitive.getSizeof(), "Unhandled Data Model");
                if (name == null) {
                    // primitive type
                    return bindTypes;
                }
                // name != null means comes from typedef
                return new ValueBasedType(name, bindTypes);
            }
            case Record record -> {
                String typeName = getName(name, record.getDisplayName());
                if (record.isIncomplete())
                    return new RefOnlyType(typeName);
                return getStruct(record.getSizeof(), typeName, record);
            }
            case TypeDef typeDef -> {
                return conv(typeDef.getTarget(), getName(name, typeDef.getDisplayName()));
            }
            case TypeFunction typeFunction -> {
                // typedef void (callback)(int a, int b);
                // void accept(callback ptr);
                // ptr IS the pointer of callback, can be converted to the FunctionPtrType safely.
                return getTypeFunction(getName(name, typeFunction.getDisplayName()), conv(typeFunction.getRet(), null), typeFunction);
            }
        }
    }

    public Preprocessor(List<Function> functions, HashSet<Macro> macros, ArrayList<Declare> varDeclares,
                        HashMap<String, Type> types, Utils.DestinationProvider dest, Utils.Filter filter) {
        record Generations(HashMap<Generation<?>, Optional<String>> genMap) {

            Generations() {
                this(new HashMap<>());
            }

            public void add(Generation<?> generation) {
                genMap.put(generation, Optional.empty());
            }

            public void add(Generation<?> generation, String location) {
                genMap.put(generation, Optional.ofNullable(location));
            }

            public void addAll(Collection<? extends Generation<?>> generation) {
                genMap.putAll(generation.stream().collect(Collectors.toMap(k -> k, v -> Optional.empty())));
            }

            void forEach(BiConsumer<? super Generation<?>, ? super Optional<String>> fun) {
                genMap.forEach(fun);
            }

            Set<Generation<?>> toGenerations(Predicate<Map.Entry<Generation<?>, Optional<String>>> filter) {
                return genMap.entrySet().stream().filter(filter).map(Map.Entry::getKey).collect(Collectors.toSet());
            }
        }

        Generations generations = new Generations();
        ArrayList<ConstValues.Value> constValues = new ArrayList<>(varDeclares.stream()
                .map(d -> new ConstValues.Value(conv(d.type(), null), d.value(), d.name())).toList());

        // types
        for (Type s : types.values()) {
            TypeAttr.TypeRefer conv = conv(s, null);
            switch (conv) {
                case ArrayTypeNamed arrayTypeNamed ->
                        generations.add(new ArrayNamed(dest.arrayNamed().path(), arrayTypeNamed));
                case EnumType e -> {
                    Type type = typedefLookUp(s);
                    Enum en = (Enum) type;
                    if (en.isUnnamed()) {
                        for (Declare declare : en.getDeclares()) {
                            constValues.add(new ConstValues.Value(conv(declare.type(), null), declare.value(), declare.name()));
                        }
                    } else
                        generations.add(new Enumerate(dest.enumerate().path(), e));
                }
                case FunctionPtrType functionPtrType ->
                        generations.add(new FuncPointer(dest.funcProtocol().path(), functionPtrType));
                case ValueBasedType valueBasedType ->
                        generations.add(new ValueBased(dest.valueBased().path(), valueBasedType));
                case VoidType voidType ->
                        generations.add(new VoidBased(dest.voidBased().path(), voidType));
                case RefOnlyType refOnlyType ->
                        generations.add(new RefOnly(dest.refOnly().path(), refOnlyType));
                case StructType structType ->
                        generations.add(new Structure(dest.struct().path(), structType));
                case CommonTypes.BindTypes _, PointerType _, ArrayType _ -> {
                }
                default -> throw new IllegalStateException("Unexpected value: " + conv);
            }
        }
        // constants
        generations.add(new ConstValues(dest.constants().path(), constValues));

        // common
        generations.addAll(Common.makeBindTypes(dest.common().path()));
        generations.addAll(Common.makeValueInterfaces(dest.common().path()));
        generations.addAll(Common.makeFFMs());
        generations.addAll(Common.makeBindTypeInterface(dest.common().path()));
        generations.addAll(Common.makeBasicOperations(dest.common().path()));
        generations.addAll(Common.makeSpecific(dest.common().path()));

        // macros
        HashSet<Macros.Macro> macro = new HashSet<>();
        macros.forEach(e -> {
            switch (e.type()) {
                case PrimitiveTypes.CType c ->
                        macro.add(new Macros.Primitive(Utils.conv2BindTypes(c).getPrimitiveType(), e.declName(), e.initializer(), e.comment()));
                case PrimitiveTypes.JType _ ->
                        macro.add(new Macros.StrMacro(e.declName(), e.initializer(), e.comment()));
            }
        });
        generations.add(new Macros(dest.macros().path(), macro));

        // symbol provider
        SymbolProviderType provider = new SymbolProviderType(dest.symbolProvider().path().getClassName());
        generations.add(new SymbolProvider(dest.symbolProvider().path().removeEnd(), provider));

        // function symbols
        ArrayList<FunctionPtrType> functionPtrTypes = new ArrayList<>();
        for (Function function : functions) {
            List<FunctionPtrType.Arg> args = function.paras().stream()
                    .map(para -> new FunctionPtrType.Arg(para.paraName(), conv(para.paraType(), null))).toList();
            functionPtrTypes.add(new FunctionPtrType(function.name(), args, conv(function.ret(), null)));
        }
        generations.add(new FuncSymbols(dest.funcSymbols().path(), functionPtrTypes, provider));

        // GenerationTypes
        HashMap<TypeAttr.GenerationType, Generation<?>> depGen = new HashMap<>();
        generations.forEach((g, _) -> g.getImplTypes().stream()
                .map(TypePkg::type).forEach(o -> depGen.put(o, g)));

        Generator generator = new Generator(generations.toGenerations(filter), depGen::get);
        generator.generate();
    }
}