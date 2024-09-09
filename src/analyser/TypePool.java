package analyser;

import libclang.LibclangEnums;
import libclang.LibclangFunctions;
import libclang.functions.CXCursorVisitor;
import libclang.structs.CXCursor;
import libclang.structs.CXString;
import libclang.structs.CXType;
import libclang.values.CXClientData;
import utils.AutoCloseableChecker;
import utils.CheckedArena;
import utils.LoggerUtils;

import java.lang.foreign.MemorySegment;
import java.util.*;

import static utils.CommonUtils.Assert;

public class TypePool implements AutoCloseableChecker.NonThrowAutoCloseable {
    HashMap<String, Type> types = new HashMap<>();
    private final CheckedArena mem = CheckedArena.ofConfined();

    @Override
    public void close() {
        mem.close();
    }

    public static class Type {
        final String typeName;

        public Type(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeName() {
            return typeName;
        }

        @Override
        public String toString() {
            return "Type{" +
                    "typeName='" + typeName + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Type type = (Type) o;
            return Objects.equals(typeName, type.typeName);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(typeName);
        }
    }

    public static class TypeDef extends Type {
        private final Type target;

        public TypeDef(String name, Type target) {
            super(name);
            this.target = target;
        }

        public Type getTarget() {
            return target;
        }

        @Override
        public String toString() {
            return "TypeDef{" +
                    "target=" + target +
                    ", typeName='" + typeName + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            TypeDef typeDef = (TypeDef) o;
            return Objects.equals(target, typeDef.target);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), target);
        }
    }

    public static class Union extends Type {
        private final ArrayList<Para> members;

        public Union(String name, ArrayList<Para> members) {
            super(name);
            this.members = members;
        }

        public void addMember(Para member) {
            members.add(member);
        }

        public void addMembers(Collection<Para> ms) {
            members.addAll(ms);
        }

        @Override
        public String toString() {
            return "Union{" +
                    "members=" + members +
                    ", typeName='" + typeName + '\'' +
                    '}';
        }
    }

    public static class TypeFunction extends Type {
        private final Type ret;
        private final ArrayList<Para> paras;

        public TypeFunction(String typeName, TypePool.Type ret, ArrayList<Para> paras) {
            super(typeName);
            this.ret = ret;
            this.paras = paras;
        }

        @Override
        public String toString() {
            return "TypeFunction{" +
                    "ret=" + ret +
                    ", paras=" + paras +
                    ", typeName='" + typeName + '\'' +
                    '}';
        }
    }

    public static class Struct extends Type {
        ArrayList<Para> paras = new ArrayList<>();

        public Struct(String name) {
            super(name);
        }

        void addPara(Para para) {
            paras.add(para);
        }

        void addParas(Collection<Para> ps) {
            paras.addAll(ps);
        }

        @Override
        public String toString() {
            return "Struct{" +
                    "paras=" + paras +
                    ", typeName='" + typeName + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Struct struct = (Struct) o;
            return Objects.equals(paras, struct.paras);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), paras);
        }
    }

    public static class Enum extends Type {
        ArrayList<Declare> declares = new ArrayList<>();

        public Enum(String typeName) {
            super(typeName);
        }

        public void addDeclare(Declare declare) {
            declares.add(declare);
        }

        @Override
        public String toString() {
            return "Enum{" +
                    "declares=" + declares +
                    ", typeName='" + typeName + '\'' +
                    '}';
        }
    }

    public static class Pointer extends Type {
        private final Type target;

        public Pointer(String name, Type target) {
            super(name);
            this.target = target;
        }

        @Override
        public String toString() {
            return "Pointer{" +
                    "target=" + target +
                    ", typeName='" + typeName + '\'' +
                    '}';
        }
    }

    public static class Array extends Type {
        private final Type elementType;
        private final long elementCount;

        public Array(String typeName, Type elementType, long elementCount) {
            super(typeName);
            this.elementType = elementType;
            this.elementCount = elementCount;
        }

        @Override
        public String toString() {
            return "Array{" +
                    "elementType=" + elementType +
                    ", elementCount=" + elementCount +
                    ", typeName='" + typeName + '\'' +
                    '}';
        }
    }

    public static class Elaborated extends Type {

        private final Type target;

        public Elaborated(String typeName, Type target) {
            super(typeName);
            this.target = target;
        }

        @Override
        public String toString() {
            return "Elaborated{" +
                    "target=" + target +
                    ", typeName='" + typeName + '\'' +
                    '}';
        }
    }

    public TypePool() {

    }

    public Type addOrCreateType(CXType cxType) {
        String name = getTypeName(cxType);
        if (types.containsKey(name)) {
            return types.get(name);
        }
        var kind = cxType.kind();
        Type ret = null;
        var typeName = Utils.cXString2String(LibclangFunctions.clang_getTypeSpelling$CXString(mem, cxType));
        if (LibclangEnums.CXTypeKind.CXType_Pointer.equals(kind)) {
            var ptr = LibclangFunctions.clang_getPointeeType$CXType(mem, cxType);
            ret = new Pointer(typeName, addOrCreateType(ptr));
        } else if (LibclangEnums.CXTypeKind.CXType_Void.equals(kind) ||
                LibclangEnums.CXTypeKind.CXType_Bool.equals(kind) ||

                LibclangEnums.CXTypeKind.CXType_UShort.equals(kind) ||
                LibclangEnums.CXTypeKind.CXType_UChar.equals(kind) ||
                LibclangEnums.CXTypeKind.CXType_UInt.equals(kind) ||
                LibclangEnums.CXTypeKind.CXType_ULong.equals(kind) ||
                LibclangEnums.CXTypeKind.CXType_ULongLong.equals(kind) ||


                LibclangEnums.CXTypeKind.CXType_Short.equals(kind) ||
                LibclangEnums.CXTypeKind.CXType_Char_S.equals(kind) ||
                LibclangEnums.CXTypeKind.CXType_SChar.equals(kind) ||
                LibclangEnums.CXTypeKind.CXType_Int.equals(kind) ||
                LibclangEnums.CXTypeKind.CXType_Long.equals(kind) ||
                LibclangEnums.CXTypeKind.CXType_LongLong.equals(kind) ||

                LibclangEnums.CXTypeKind.CXType_Float.equals(kind) ||
                LibclangEnums.CXTypeKind.CXType_Double.equals(kind) ||
                LibclangEnums.CXTypeKind.CXType_LongDouble.equals(kind)) {
            System.out.println("TYPE NAME: " + typeName);
            ret = new Type(typeName);
        } else if (LibclangEnums.CXTypeKind.CXType_FunctionProto.equals(kind)) {
            CXType returnType = LibclangFunctions.clang_getResultType$CXType(mem, cxType);
            var funcRet = addOrCreateType(returnType);

            int numArgs = LibclangFunctions.clang_getNumArgTypes$int(cxType);
            ArrayList<Para> paras = new ArrayList<>();
            for (int i = 0; i < numArgs; i++) {
                CXType argType = LibclangFunctions.clang_getArgType$CXType(mem, cxType, i);
                String argTypeName = Utils.cXString2String(LibclangFunctions.clang_getTypeSpelling$CXString(mem, argType));

                var t = addOrCreateType(argType);
                paras.add(new Para(t, argTypeName));
            }
            ret = new TypeFunction(typeName, funcRet, paras);
        } else if (LibclangEnums.CXTypeKind.CXType_ConstantArray.equals(kind)) {
            CXType arrType = LibclangFunctions.clang_getArrayElementType$CXType(mem, cxType);
            long count = LibclangFunctions.clang_getArraySize$long(cxType);
            ret = new Array(typeName, addOrCreateType(arrType), count);
        } else if (LibclangEnums.CXTypeKind.CXType_Elaborated.equals(kind)) {
            CXType target = LibclangFunctions.clang_Type_getNamedType$CXType(mem, cxType);
            ret = new Elaborated(typeName, addOrCreateType(target));
        } else if (LibclangEnums.CXTypeKind.CXType_Record.equals(kind)) {
            CXCursor recDecl = LibclangFunctions.clang_getTypeDeclaration$CXCursor(mem, cxType);
            if (Objects.equals(recDecl.kind().value(), LibclangEnums.CXCursorKind.CXCursor_UnionDecl.value()))
                ret = addOrCreateUnion(recDecl);
            else if (Objects.equals(recDecl.kind().value(), LibclangEnums.CXCursorKind.CXCursor_StructDecl.value())) {
                ret = addOrCreateStruct(recDecl);
            } else {
                Assert(false, "Unsupported declaration in " + typeName);
            }
        } else if (LibclangEnums.CXTypeKind.CXType_Typedef.equals(kind)) {
            CXCursor cursor = LibclangFunctions.clang_getTypeDeclaration$CXCursor(mem, cxType);
            ret = addOrCreateTypeDef(cursor);
        } else if (LibclangEnums.CXTypeKind.CXType_Enum.equals(kind)) {
            ret = addOrCreateEnum(cxType);
        } else if (LibclangEnums.CXTypeKind.CXType_IncompleteArray.equals(kind)) {
            CXType arrType = LibclangFunctions.clang_getArrayElementType$CXType(mem, cxType);
            ret = new Pointer(typeName, addOrCreateType(arrType));
        } else {
            throw new RuntimeException("Unhandled type " + typeName + "(" + kind + ")");
        }
        Assert(ret != null);
        LoggerUtils.debug("Creating " + ret);
        types.put(ret.typeName, ret);
        return ret;
    }

    public Type addOrCreateType(CXCursor cursor) {
        return addOrCreateType(LibclangFunctions.clang_getCursorType$CXType(mem, cursor));
    }

    // todo: move to addOrCreateType()
    public Struct addOrCreateStruct(CXCursor cursor) {
        Assert(LibclangEnums.CXCursorKind.CXCursor_StructDecl.equals(LibclangFunctions.clang_getCursorKind$CXCursorKind(cursor)));
        CXType cxType = LibclangFunctions.clang_getCursorType$CXType(mem, cursor);
        return addOrCreateStruct(cxType);
    }

    private static Struct findTarget(Type type) {
        if (type instanceof Struct s)
            return s;
        if (type instanceof TypeDef t)
            return findTarget(t.target);
        if (type instanceof Elaborated e)
            return findTarget(e.target);
        Assert(false, "unexpected type " + type);
        return null;
    }

    public Struct addOrCreateStruct(CXType cxType) {
        Assert(LibclangEnums.CXTypeKind.CXType_Record.equals(cxType.kind()));
        String name = getTypeName(cxType);
        CXCursor cursor = LibclangFunctions.clang_getTypeDeclaration$CXCursor(mem, cxType);
        if (types.containsKey(name)) {
            Type type = types.get(name);
            return findTarget(type);
        }
        Struct struct = new Struct(name);
        types.put(struct.typeName, struct);
        ArrayList<Para> paras = parseRecord(cursor, struct);
        struct.addParas(paras);
        return struct;
    }

    private ArrayList<Para> parseRecord(CXCursor cursor_, Type ret) {
        ArrayList<Para> paras = new ArrayList<>();
        LibclangFunctions.clang_visitChildren$int(cursor_, ((CXCursorVisitor.CXCursorVisitor$CXChildVisitResult$0) (cursor, parent, _) -> {
            Utils.printLocation(mem, cursor);
            cursor = cursor.reinterpretSize();
            var kind = LibclangFunctions.clang_getCursorKind$CXCursorKind(cursor);
            CXString cursorStr_ = LibclangFunctions.clang_getCursorSpelling$CXString(mem, cursor);
            String cursorName = Utils.cXString2String(cursorStr_);
            LibclangFunctions.clang_disposeString(cursorStr_);
            if (LibclangEnums.CXCursorKind.CXCursor_StructDecl.equals(kind)) {
                // struct declared in Record
                LoggerUtils.debug("Struct " + cursorName + " in " + ret);
                addOrCreateStruct(cursor);
            } else if (LibclangEnums.CXCursorKind.CXCursor_UnionDecl.equals(kind)) {
                LoggerUtils.debug("Union " + cursorName + " in " + ret);
                addOrCreateUnion(cursor);
            } else if (LibclangEnums.CXCursorKind.CXCursor_FunctionDecl.equals(kind)) {
                // function declared in Record
                LoggerUtils.error("Function declared " + cursorName + " in " + ret + " is not allowed");
                Assert(false);
            } else if (LibclangEnums.CXCursorKind.CXCursor_FieldDecl.equals(kind)) {
                LoggerUtils.debug("Field Declared " + cursorName + " in " + ret);
                var memberType = addOrCreateType(cursor);
                paras.add(new Para(memberType, cursorName));
            } else {
                Assert(false, "Unhandled kind:" + kind);
            }

            return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
        }).toVPointer(mem), new CXClientData(MemorySegment.NULL));
        return paras;
    }

    public Union addOrCreateUnion(CXCursor cursor) {
        CXType cxType = LibclangFunctions.clang_getCursorType$CXType(mem, cursor);
        Assert(LibclangEnums.CXCursorKind.CXCursor_UnionDecl.equals(LibclangFunctions.clang_getCursorKind$CXCursorKind(cursor)));
        return addOrCreateUnion(cxType);
    }

    public Union addOrCreateUnion(CXType cxType) {
        Assert(LibclangEnums.CXTypeKind.CXType_Record.equals(cxType.kind()));
        CXCursor cursor = LibclangFunctions.clang_getTypeDeclaration$CXCursor(mem, cxType);
        String name = getTypeName(cxType);
        if (types.containsKey(name)) {
            return (Union) types.get(name);
        }
        Union ret = new Union(name, new ArrayList<>());
        types.put(ret.getTypeName(), ret);
        ArrayList<Para> paras = parseRecord(cursor, ret);
        ret.addMembers(paras);
        return ret;
    }

    public Enum addOrCreateEnum(CXType cxType) {
        Assert(LibclangEnums.CXTypeKind.CXType_Enum.equals(cxType.kind()));
        CXCursor cursor = LibclangFunctions.clang_getTypeDeclaration$CXCursor(mem, cxType);
        var typeName = Utils.cXString2String(LibclangFunctions.clang_getTypeSpelling$CXString(mem, cxType));
        Enum e = new Enum(typeName);
        CXType enumType = LibclangFunctions.clang_getEnumDeclIntegerType$CXType(mem, cursor);
        Type type = addOrCreateType(enumType);
        LibclangFunctions.clang_visitChildren$int(cursor, ((CXCursorVisitor.CXCursorVisitor$CXChildVisitResult$0) (cur, _, _) -> {
            cur = cur.reinterpretSize();
            if (LibclangEnums.CXCursorKind.CXCursor_EnumConstantDecl.equals(LibclangFunctions.clang_getCursorKind$CXCursorKind(cur))) {
                CXString declName = LibclangFunctions.clang_getCursorSpelling$CXString(mem, cur);
                long constant_value = LibclangFunctions.clang_getEnumConstantDeclValue$long(cur);
                e.addDeclare(new Declare(type, Utils.cXString2String(declName), constant_value + ""));
            }
            return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
        }).toVPointer(mem), new CXClientData(MemorySegment.NULL));
        return e;
    }

    public TypeDef addOrCreateTypeDef(CXCursor cursor) {
        var typedef_type = LibclangFunctions.clang_getTypedefDeclUnderlyingType$CXType(mem, cursor);
        String name = getTypeName(LibclangFunctions.clang_getCursorType$CXType(mem, cursor));
        if (types.containsKey(name)) {
            var obj = types.get(name);
            if (obj instanceof TypeDef)
                return (TypeDef) obj;
            TypeDef ref = new TypeDef(name, obj);
            types.put(name, ref);
            return ref;
        }
        var def = new TypeDef(name, addOrCreateType(typedef_type));
        types.put(def.typeName, def);
        return def;
    }

    private String getTypeName(CXType cxType) {
        var typeName = LibclangFunctions.clang_getTypeSpelling$CXString(mem, cxType);
        var ret = Utils.cXString2String(typeName);
        LibclangFunctions.clang_disposeString(typeName);
        return ret;
    }

    public HashMap<String, Type> getTypes() {
        return types;
    }
}