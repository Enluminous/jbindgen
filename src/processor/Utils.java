package processor;

import analyser.Declare;
import analyser.Para;
import analyser.PrimitiveTypes;
import analyser.types.Enum;
import analyser.types.Record;
import analyser.types.*;
import generator.PackagePath;
import generator.generation.*;
import generator.types.*;
import utils.ConflictNameUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static utils.CommonUtils.Assert;

public class Utils {
    public static CommonTypes.BindTypes conv2BindTypes(PrimitiveTypes.CType type) {
        switch (type) {
            case C_I8 -> {
                return CommonTypes.BindTypes.I8;
            }
            case C_I16 -> {
                return CommonTypes.BindTypes.I16;
            }
            case C_I32 -> {
                return CommonTypes.BindTypes.I32;
            }
            case C_I64 -> {
                return CommonTypes.BindTypes.I64;
            }
            case C_FP32 -> {
                return CommonTypes.BindTypes.FP32;
            }
            case C_FP64 -> {
                return CommonTypes.BindTypes.FP64;
            }
            case C_FP128 -> {
                return CommonTypes.BindTypes.FP128;
            }
            default -> throw new IllegalArgumentException(type.toString());
        }
    }

    private static String getName(String nullAbleName, String name) {
        String ret = nullAbleName;
        if (ret == null)
            ret = name;
        return ret;
    }

    private record StructValue(StructType s, Record r) {
    }

    private static ArrayList<StructType.Member> solveMembers(Record record, HashMap<String, StructValue> structMap) {
        List<String> memberBlacks = record.getMembers().stream().map(Para::paraName).toList();
        ArrayList<StructType.Member> members = new ArrayList<>();
        for (Para member : record.getMembers()) {
            String input = ConflictNameUtils.getNonConflictsNameExt(member.paraName(), List.of(), memberBlacks);
            long offset = member.offset().orElseThrow();
            long bitSize = member.bitWidth().orElseThrow();
            Assert(offset >= 0);
            Assert(bitSize > 0);
            members.add(new StructType.Member(conv(member.paraType(), null, structMap), input,
                    offset, bitSize));
        }
        return members;
    }

    private static StructType getStruct(long byteSize, String typeName, Record record, HashMap<String, StructValue> structMap) {
        if (structMap.containsKey(typeName)) {
            if (!Objects.equals(record, structMap.get(typeName).r)) {
                throw new RuntimeException();
            }
            return structMap.get(typeName).s;
        }

        return new StructType(byteSize, typeName, structType -> {
            structMap.put(typeName, new StructValue(structType, record));
            return solveMembers(record, structMap);
        });
    }

    private static FunctionPtrType getTypeFunction(String typeName, TypeAttr.TypeRefer retType, TypeFunction typeFunction, HashMap<String, StructValue> structMap) {
        ArrayList<FunctionPtrType.Arg> args = new ArrayList<>();
        ArrayList<Para> paras = typeFunction.getParas();
        for (int i = 0; i < paras.size(); i++) {
            Para para = paras.get(i);
            String paraName = para.paraName();
            if (paraName == null || paraName.isEmpty()) {
                paraName = "arg" + i;
            }
            args.add(new FunctionPtrType.Arg(paraName, conv(para.paraType(), null, structMap)));
        }
        return new FunctionPtrType(typeName, args, retType);
    }

    public static Type typedefLookUp(Type type) {
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
            case Complex complex -> {
                return complex;
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
    public static TypeAttr.TypeRefer conv(Type type, String name) {
        return conv(type, name, new HashMap<>());
    }

    private static TypeAttr.TypeRefer conv(Type type, String name, HashMap<String, StructValue> structMap) {
        switch (type) {
            case Array array -> {
                if (name != null) {
                    return new ArrayTypeNamed(name, array.getElementCount(), conv(array.getElementType(), null, structMap), array.getSizeof());
                }
                return new ArrayType(array.getElementCount(), conv(array.getElementType(), null, structMap), array.getSizeof());
            }
            case Enum anEnum -> {
                TypeAttr.TypeRefer conv = conv(anEnum.getDeclares().getFirst().type(), null, structMap);
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
                    return getTypeFunction(getName(name, f.getDisplayName()), conv(f.getRet(), null, structMap), f, structMap);
                }

                // name != null means comes from typedef
                PointerType t = new PointerType(conv(pointer.getTarget(), null, structMap));
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
                Assert(bindTypes.getPrimitiveType().byteSize() == primitive.getSizeof(), "Unhandled Data Model");
                if (name == null) {
                    // primitive type
                    return bindTypes;
                }
                // name != null means comes from typedef
                return new ValueBasedType(name, bindTypes);
            }
            case Complex complex -> {
                String n = getName(name, complex.getDisplayName());
                return new StructType(complex.getSizeof(), n, _ -> List.of());
            }
            case Record record -> {
                String typeName = getName(name, record.getDisplayName());
                if (record.isIncomplete())
                    return new RefOnlyType(typeName);
                return getStruct(record.getSizeof(), typeName, record, structMap);
            }
            case TypeDef typeDef -> {
                // Checks case-insensitive equality of def name and struct's display name
                // to prevent problems on case-insensitive operating systems
                String defName = getName(name, typeDef.getDisplayName());
                if (Utils.typedefLookUp(type) instanceof Struct struct) {
                    String dn = struct.getDisplayName();
                    if (!dn.equals(defName) && dn.equalsIgnoreCase(defName)) {
                        System.out.println("Overriding typedef name " + defName + " with " + dn + " (case-insensitive)");
                        defName = dn;
                    }
                }
                return conv(typeDef.getTarget(), defName, structMap);
            }
            case TypeFunction typeFunction -> {
                // typedef void (callback)(int a, int b);
                // void accept(callback ptr);
                // ptr IS the pointer of callback, can be converted to the FunctionPtrType safely.
                return getTypeFunction(getName(name, typeFunction.getDisplayName()), conv(typeFunction.getRet(), null, structMap), typeFunction, structMap);
            }
        }
    }

    public interface DestinationProvider {
        Destination symbolProvider();

        Destination macros();

        Destination constants();

        Destination funcSymbols();

        PathOnly common();

        PathOnly enumerate();

        PathOnly valueBased();

        PathOnly struct();

        PathOnly funcProtocol();

        PathOnly refOnly();

        PathOnly voidBased();

        PathOnly arrayNamed();

        static DestinationProvider ofDefault(PackagePath p, String libName) {
            return new DefaultDestinationProvider(p, libName);
        }
    }

    public interface Filter extends Predicate<Map.Entry<Generation<?>, Optional<String>>> {
        @Override
        default boolean test(Map.Entry<Generation<?>, Optional<String>> entry) {
            Generation<?> generation = entry.getKey();
            Optional<String> value = entry.getValue();

            return switch (generation) {
                case AbstractGeneration<?> abstractGeneration -> switch (abstractGeneration) {
                    case Common common -> testCommon(value);
                    case ArrayNamed arrayNamed -> testArrayNamed(value);
                    case Enumerate enumerate -> testEnumerate(value);
                    case FuncPointer funcPointer -> testFuncPointer(value);
                    case RefOnly refOnly -> testRefOnly(value);
                    case Structure structure -> testStructure(value);
                    case SymbolProvider symbolProvider -> testSymbolProvider(value);
                    case ValueBased valueBased -> testValueBased(value);
                    case VoidBased voidBased -> testVoidBased(value);
                };
                case ConstValues constValues -> testConstValues(value);
                case FuncSymbols funcSymbols -> testFuncSymbols(value);
                case Macros macros -> testMacros(value);
                case VarSymbols varSymbols -> testVarSymbols(value);
            };
        }

        default boolean testCommon(Optional<String> value) {
            return true;
        }

        default boolean testArrayNamed(Optional<String> value) {
            return true;
        }

        default boolean testEnumerate(Optional<String> value) {
            return true;
        }

        default boolean testFuncPointer(Optional<String> value) {
            return true;
        }

        default boolean testRefOnly(Optional<String> value) {
            return true;
        }

        default boolean testStructure(Optional<String> value) {
            return true;
        }

        default boolean testSymbolProvider(Optional<String> value) {
            return true;
        }

        default boolean testValueBased(Optional<String> value) {
            return true;
        }

        default boolean testVoidBased(Optional<String> value) {
            return true;
        }

        default boolean testConstValues(Optional<String> value) {
            return true;
        }

        default boolean testFuncSymbols(Optional<String> value) {
            return true;
        }

        default boolean testMacros(Optional<String> value) {
            return true;
        }

        default boolean testVarSymbols(Optional<String> value) {
            return true;
        }

        static Filter ofDefault(Function<String, Boolean> test) {
            return new Filter() {
                final Predicate<Optional<String>> filter =
                        value -> value.map(test).orElse(true);

                @Override
                public boolean testArrayNamed(Optional<String> value) {
                    return filter.test(value);
                }

                @Override
                public boolean testEnumerate(Optional<String> value) {
                    return filter.test(value);
                }

                @Override
                public boolean testFuncPointer(Optional<String> value) {
                    return filter.test(value);
                }

                @Override
                public boolean testRefOnly(Optional<String> value) {
                    return filter.test(value);
                }

                @Override
                public boolean testStructure(Optional<String> value) {
                    return filter.test(value);
                }

                @Override
                public boolean testValueBased(Optional<String> value) {
                    return filter.test(value);
                }
            };
        }
    }

    public record Destination(PackagePath path) {
        public Destination {
            path.reqClassName();
        }
    }

    public record PathOnly(PackagePath path) {
        public PathOnly {
            path.reqNonClassName();
        }
    }

    public static class DefaultDestinationProvider implements DestinationProvider {
        private final PackagePath p;
        private final String libName;

        public DefaultDestinationProvider(PackagePath p, String libName) {
            this.p = p;
            this.libName = libName;
        }

        @Override
        public Destination symbolProvider() {
            return new Destination(p.end(libName + "SymbolProvider"));
        }

        @Override
        public Destination macros() {
            return new Destination(p.end(libName + "Macros"));
        }

        @Override
        public Destination constants() {
            return new Destination(p.end(libName + "Constants"));
        }

        @Override
        public Destination funcSymbols() {
            return new Destination(p.end(libName + "FunctionSymbols"));
        }

        @Override
        public PathOnly common() {
            return new PathOnly(p.add("common"));
        }

        @Override
        public PathOnly enumerate() {
            return new PathOnly(p.add("enumerates"));
        }

        @Override
        public PathOnly valueBased() {
            return new PathOnly(p.add("values"));
        }

        @Override
        public PathOnly struct() {
            return new PathOnly(p.add("aggregates"));
        }

        @Override
        public PathOnly funcProtocol() {
            return new PathOnly(p.add("functions"));
        }

        @Override
        public PathOnly refOnly() {
            return new PathOnly(p.add("opaques"));
        }

        @Override
        public PathOnly voidBased() {
            return new PathOnly(p.add("opaques"));
        }

        @Override
        public PathOnly arrayNamed() {
            return new PathOnly(p.add("aggregates"));
        }
    }
}
