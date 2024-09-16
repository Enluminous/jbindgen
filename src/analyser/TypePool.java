package analyser;

import analyser.types.*;
import analyser.types.Enum;
import analyser.types.Record;
import analyser.types.Struct;
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
    private final HashMap<String, Type> types = new HashMap<>();
    private final CheckedArena mem = CheckedArena.ofConfined();

    @Override
    public void close() {
        mem.close();
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
            CXType ptr = LibclangFunctions.clang_getPointeeType$CXType(mem, cxType);
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
            ret = new Primitive(typeName);
        } else if (LibclangEnums.CXTypeKind.CXType_FunctionProto.equals(kind)) {
            CXType returnType = LibclangFunctions.clang_getResultType$CXType(mem, cxType);
            Type funcRet = addOrCreateType(returnType);

            int numArgs = LibclangFunctions.clang_getNumArgTypes$int(cxType);
            ArrayList<Para> paras = new ArrayList<>();
            for (int i = 0; i < numArgs; i++) {
                CXType argType = LibclangFunctions.clang_getArgType$CXType(mem, cxType, i);
                CXCursor cursor = LibclangFunctions.clang_getTypeDeclaration$CXCursor(mem, argType);
                CXString paraName = LibclangFunctions.clang_getCursorSpelling$CXString(mem, cursor);
                String argTypeName = Utils.cXString2String(paraName);
                LibclangFunctions.clang_disposeString(paraName);

                Type t = addOrCreateType(argType);
                paras.add(new Para(t, argTypeName));
            }
            ret = new TypeFunction(typeName, funcRet, paras);
        } else if (LibclangEnums.CXTypeKind.CXType_ConstantArray.equals(kind)) {
            CXType arrType = LibclangFunctions.clang_getArrayElementType$CXType(mem, cxType);
            long count = LibclangFunctions.clang_getArraySize$long(cxType);
            ret = new Array(typeName, addOrCreateType(arrType), count);
        } else if (LibclangEnums.CXTypeKind.CXType_Elaborated.equals(kind)) {
            CXType target = LibclangFunctions.clang_Type_getNamedType$CXType(mem, cxType);
            ret = addOrCreateType(target);
        } else if (LibclangEnums.CXTypeKind.CXType_Record.equals(kind)) {
            CXCursor recDecl = LibclangFunctions.clang_getTypeDeclaration$CXCursor(mem, cxType);
            if (Objects.equals(recDecl.kind().value(), LibclangEnums.CXCursorKind.CXCursor_UnionDecl.value()))
                ret = addOrCreateUnion(recDecl, null);
            else if (Objects.equals(recDecl.kind().value(), LibclangEnums.CXCursorKind.CXCursor_StructDecl.value())) {
                ret = addOrCreateStruct(recDecl, null);
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
        types.put(ret.getTypeName(), ret);
        return ret;
    }

    public Type addOrCreateType(CXCursor cursor) {
        return addOrCreateType(LibclangFunctions.clang_getCursorType$CXType(mem, cursor));
    }

    // todo: move to addOrCreateType()
    public Struct addOrCreateStruct(CXCursor cursor, String displayName) {
        Assert(LibclangEnums.CXCursorKind.CXCursor_StructDecl.equals(LibclangFunctions.clang_getCursorKind$CXCursorKind(cursor)));
        CXType cxType = LibclangFunctions.clang_getCursorType$CXType(mem, cursor);
        return addOrCreateStruct(cxType, displayName);
    }

    //todo: drop Elaborated
    private static Struct findTargetStruct(Type type) {
        return switch (type) {
            case Struct s -> s;
            case TypeDef t -> findTargetStruct(t.getTarget());
            default -> {
                Assert(false, "unexpected type " + type);
                throw new RuntimeException("Unhandled type " + type);
            }
        };
    }

    private static Union findTargetUnion(Type type) {
        return switch (type) {
            case Union s -> s;
            case TypeDef t -> findTargetUnion(t.getTarget());
            default -> {
                Assert(false, "unexpected type " + type);
                throw new RuntimeException("Unhandled type " + type);
            }
        };
    }

    public Struct addOrCreateStruct(CXType cxType, String displayName) {
        Assert(LibclangEnums.CXTypeKind.CXType_Record.equals(cxType.kind()));
        String name = getTypeName(cxType);
        CXCursor cursor = LibclangFunctions.clang_getTypeDeclaration$CXCursor(mem, cxType);
        if (types.containsKey(name)) {
            Type type = types.get(name);
            return findTargetStruct(type);
        }
        Struct struct = new Struct(name);
        struct.setDisplayName(displayName);
        types.put(struct.getTypeName(), struct);
        ArrayList<Para> paras = parseRecord(cursor, struct);
        struct.addParas(paras);
        return struct;
    }


    String getStructFieldName(CXCursor parent, CXCursor decl) {
        final boolean[] arrival = {false};
        final String[] name = new String[1];
        LibclangFunctions.clang_visitChildren$int(parent, ((CXCursorVisitor.CXCursorVisitor$CXChildVisitResult$0)
                (_cursor, _, _) -> {
                    if (!arrival[0])
                        arrival[0] = LibclangFunctions.clang_equalCursors$int(decl, _cursor) != 0;

                    LibclangEnums.CXCursorKind _kind = LibclangFunctions.clang_getCursorKind$CXCursorKind(_cursor);
                    if (arrival[0] && LibclangEnums.CXCursorKind.CXCursor_FieldDecl.equals(_kind)) {
                        CXString fieldName = LibclangFunctions.clang_getCursorSpelling$CXString(mem, _cursor);
                        name[0] = Utils.cXString2String(fieldName);
                        LibclangFunctions.clang_disposeString(fieldName);
                        return LibclangEnums.CXChildVisitResult.CXChildVisit_Break;
                    }
                    return LibclangEnums.CXChildVisitResult.CXChildVisit_Continue;
                }).toVPointer(mem), new CXClientData(MemorySegment.NULL));
        return name[0];
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
            if (LibclangEnums.CXCursorKind.CXCursor_StructDecl.equals(kind) || LibclangEnums.CXCursorKind.CXCursor_UnionDecl.equals(kind)) {
                boolean inlined = LibclangFunctions.clang_Cursor_isAnonymousRecordDecl$int(cursor) != 0;
                boolean unnamed = LibclangFunctions.clang_Cursor_isAnonymous$int(cursor) != 0;

                LoggerUtils.debug("Struct " + cursorName + " in " + ret + " inlined " + inlined + " unnamed " + unnamed);
                if (inlined) {
                    paras.addAll(parseRecord(cursor, ret));
                } else {
                    String displayName = null;
                    if (unnamed) {
                        Record p = (Record) ret;
                        String fieldName = getStructFieldName(parent, cursor);
                        displayName = p.getDisplayName() + "$" + fieldName;
                    }
                    if (LibclangEnums.CXCursorKind.CXCursor_StructDecl.equals(kind))
                        addOrCreateStruct(cursor, displayName);
                    else
                        addOrCreateUnion(cursor, displayName);
                }
            } else if (LibclangEnums.CXCursorKind.CXCursor_FunctionDecl.equals(kind)) {
                // function declared in Record
                LoggerUtils.error("Function declared " + cursorName + " in " + ret + " is not allowed");
                Assert(false);
            } else if (LibclangEnums.CXCursorKind.CXCursor_FieldDecl.equals(kind)) {
                LoggerUtils.debug("Field Declared " + cursorName + " in " + ret);
                var memberType = addOrCreateType(cursor);
                paras.add(new Para(memberType, cursorName));
            } else if (LibclangEnums.CXCursorKind.CXCursor_EnumDecl.equals(kind)) {
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

    public Union addOrCreateUnion(CXCursor cursor, String displayName) {
        CXType cxType = LibclangFunctions.clang_getCursorType$CXType(mem, cursor);
        Assert(LibclangEnums.CXCursorKind.CXCursor_UnionDecl.equals(LibclangFunctions.clang_getCursorKind$CXCursorKind(cursor)));
        return addOrCreateUnion(cxType, displayName);
    }

    public Union addOrCreateUnion(CXType cxType, String displayName) {
        Assert(LibclangEnums.CXTypeKind.CXType_Record.equals(cxType.kind()));
        CXCursor cursor = LibclangFunctions.clang_getTypeDeclaration$CXCursor(mem, cxType);
        String name = getTypeName(cxType);
        if (types.containsKey(name)) {
            Type type = types.get(name);
            return findTargetUnion(type);
        }
        Union ret = new Union(name, new ArrayList<>());
        ret.setDisplayName(displayName);
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
            if (obj instanceof TypeDef typeDef)
                return typeDef;
            TypeDef ref = new TypeDef(name, obj);
            types.put(name, ref);
            return ref;
        }
        var def = new TypeDef(name, addOrCreateType(typedef_type));
        types.put(def.getTypeName(), def);
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