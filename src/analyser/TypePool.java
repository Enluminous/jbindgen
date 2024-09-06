package analyser;

import libclang.LibclangEnums;
import libclang.LibclangFunctions;
import libclang.structs.CXCursor;
import libclang.structs.CXType;
import utils.AutoCloseableChecker;
import utils.CheckedArena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
        public Union(String name) {
            super(name);
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

    public TypePool() {

    }

    public Type addOrCreateType(CXType cxType) {
        String name = getTypeName(cxType);
        if (types.containsKey(name)) {
            return types.get(name);
        }
        int kind = cxType.kind().value();
        Type ret = null;
        var typeName = Utils.cXString2String(LibclangFunctions.clang_getTypeSpelling$CXString(mem, cxType));
        if (kind == LibclangEnums.CXTypeKind.CXType_Pointer.value()) {
            var ptr = LibclangFunctions.clang_getPointeeType$CXType(mem, cxType);
            ret = new Pointer(typeName, addOrCreateType(ptr));
        } else if (kind == LibclangEnums.CXTypeKind.CXType_Void.value() ||
                kind == LibclangEnums.CXTypeKind.CXType_Bool.value() ||
                kind == LibclangEnums.CXTypeKind.CXType_Char_U.value() ||
                kind == LibclangEnums.CXTypeKind.CXType_UChar.value() ||
                kind == LibclangEnums.CXTypeKind.CXType_Char16.value() ||
                kind == LibclangEnums.CXTypeKind.CXType_Char32.value() ||
                kind == LibclangEnums.CXTypeKind.CXType_UShort.value() ||
                kind == LibclangEnums.CXTypeKind.CXType_UInt.value() ||
                kind == LibclangEnums.CXTypeKind.CXType_ULong.value() ||
                kind == LibclangEnums.CXTypeKind.CXType_ULongLong.value() ||

                kind == LibclangEnums.CXTypeKind.CXType_Int.value() ||
                kind == LibclangEnums.CXTypeKind.CXType_Long.value() ||

                kind == LibclangEnums.CXTypeKind.CXType_Float.value() ||
                false) {
            System.out.println("TYPE NAME: " + typeName);
            ret = new Type(typeName);
        } else if (kind == LibclangEnums.CXTypeKind.CXType_FunctionProto.value()) {
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
        } else {
            throw new RuntimeException("Unregistered type " + typeName);
        }
        types.put(ret.typeName, ret);
        return ret;
    }

    public Type addOrCreateType(CXCursor cursor) {
        return addOrCreateType(LibclangFunctions.clang_getCursorType$CXType(mem, cursor));
    }

    public Struct addOrCreateStruct(CXCursor cursor) {
        String name = getTypeName(LibclangFunctions.clang_getCursorType$CXType(mem, cursor));
        if (types.containsKey(name)) {
            return (Struct) types.get(name);
        }
        var ret = new Struct(name);
        types.put(ret.typeName, ret);
        return ret;
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