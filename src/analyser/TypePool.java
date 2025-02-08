package analyser;

import analyser.types.*;
import analyser.types.Array;
import analyser.types.Enum;
import analyser.types.Record;
import analyser.types.Struct;
import libclang.LibclangFunctionSymbols;
import libclang.common.*;
import libclang.enumerates.CXChildVisitResult;
import libclang.enumerates.CXCursorKind;
import libclang.enumerates.CXTypeKind;
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
import java.util.function.Function;

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
        return addOrCreateType(cxType, null, null);
    }

    public Type addOrCreateType(CXType cxType, CXCursor rootCursor, String sugName) {
        String name = getTypeName(cxType);
        if (types.containsKey(name)) {
            return types.get(name);
        }
        var kind = cxType.kind();
        Type ret = null;
        var typeName = Utils.cXString2String(LibclangFunctionSymbols.clang_getTypeSpelling(mem, cxType));
        if (CXTypeKind.CXType_Pointer.equals(kind)) {
            CXType ptr = LibclangFunctionSymbols.clang_getPointeeType(mem, cxType);
            ret = new Pointer(typeName, addOrCreateType(ptr, rootCursor, sugName), Utils.getLocation(cxType, rootCursor));
        } else if (CXTypeKind.CXType_Void.equals(kind)) {
            ret = new Primitive(typeName, -2, PrimitiveTypes.CType.C_VOID, Utils.getTypeLocation(cxType));
        } else if (CXTypeKind.CXType_Bool.equals(kind) ||
                CXTypeKind.CXType_UShort.equals(kind) ||
                CXTypeKind.CXType_UChar.equals(kind) ||
                CXTypeKind.CXType_UInt.equals(kind) ||
                CXTypeKind.CXType_ULong.equals(kind) ||
                CXTypeKind.CXType_ULongLong.equals(kind) ||

                CXTypeKind.CXType_Short.equals(kind) ||
                CXTypeKind.CXType_Char_S.equals(kind) ||
                CXTypeKind.CXType_SChar.equals(kind) ||
                CXTypeKind.CXType_Int.equals(kind) ||
                CXTypeKind.CXType_Long.equals(kind) ||
                CXTypeKind.CXType_LongLong.equals(kind)) {
            Long byteSize = LibclangFunctionSymbols.clang_Type_getSizeOf(cxType).operator().value();
            ret = new Primitive(typeName, byteSize, PrimitiveTypes.CType.getPrimitiveType(byteSize, true), Utils.getTypeLocation(cxType));
        } else if (CXTypeKind.CXType_Float.equals(kind) ||
                CXTypeKind.CXType_Double.equals(kind) ||
                CXTypeKind.CXType_LongDouble.equals(kind)) {
            Long byteSize = LibclangFunctionSymbols.clang_Type_getSizeOf(cxType).operator().value();
            ret = new Primitive(typeName, byteSize, PrimitiveTypes.CType.getPrimitiveType(byteSize, false), Utils.getTypeLocation(cxType));
        } else if (CXTypeKind.CXType_FunctionProto.equals(kind)) {
            CXType returnType = LibclangFunctionSymbols.clang_getResultType(mem, cxType);
            Type funcRet = addOrCreateType(returnType);
            ArrayList<String> paraNames = new ArrayList<>();

            Function<CXCursor, Boolean> canParse = c -> {
                if (CXCursorKind.CXCursor_ParmDecl.equals(c.kind())) {
                    return true;
                }
                CXType type = LibclangFunctionSymbols.clang_getCursorType(mem, c);
                if (!CXTypeKind.CXType_Pointer.equals(type.kind())) {
                    return false;
                }
                type = LibclangFunctionSymbols.clang_getPointeeType(mem, type);
                return LibclangFunctionSymbols.clang_equalTypes(type, cxType).operator().value() != 0;
            };
            LibclangFunctionSymbols.clang_visitChildren(rootCursor,
                    new CXCursorVisitor(mem, (CXCursorVisitor.Function) (c, _, _) -> {
                        if (!canParse.apply(c)) {
                            return CXChildVisitResult.CXChildVisit_Recurse;
                        }
                        CXString paraName = LibclangFunctionSymbols.clang_getCursorSpelling(mem, c);
                        String argTypeName = Utils.cXString2String(paraName);
                        LibclangFunctionSymbols.clang_disposeString(paraName);
                        paraNames.add(argTypeName);
                        return CXChildVisitResult.CXChildVisit_Continue;
                    }), new CXClientData(MemorySegment.NULL));

            int numArgs = LibclangFunctionSymbols.clang_getNumArgTypes(cxType).operator().value();
            ArrayList<Para> paras = new ArrayList<>();
            for (int i = 0; i < numArgs; i++) {
                CXType argType = LibclangFunctionSymbols.clang_getArgType(mem, cxType, I32I.of(i));
                Type t = addOrCreateType(argType, rootCursor, null);
                String paraName = paraNames.get(i);
                if (paraName.isEmpty())
                    paraName = "arg" + i;
                paras.add(new Para(t, paraName, OptionalLong.empty(), OptionalLong.empty()));
            }
            if (paras.size() != paraNames.size()) {
                throw new RuntimeException();
            }
            String funcName = sugName;
            if (funcName == null) {
                StringBuilder arg = new StringBuilder();
                for (Para para : paras) {
                    arg.append("_").append(para.paraType().getDisplayName());
                }
                funcName = "func_" + funcRet.getDisplayName() + arg.toString();
            }
            ret = new TypeFunction(typeName, funcRet, paras, Utils.getLocation(cxType, rootCursor));
            ret.setDisplayName(funcName);
        } else if (CXTypeKind.CXType_ConstantArray.equals(kind)) {
            CXType arrType = LibclangFunctionSymbols.clang_getArrayElementType(mem, cxType);
            long count = LibclangFunctionSymbols.clang_getArraySize(cxType).operator().value();
            long sizeOf = LibclangFunctionSymbols.clang_Type_getSizeOf(cxType).operator().value();
            CXCursor recDecl = LibclangFunctionSymbols.clang_getTypeDeclaration(mem, arrType);
            boolean unnamed = LibclangFunctionSymbols.clang_Cursor_isAnonymous(recDecl).operator().value() != 0;
            ret = new Array(typeName, addOrCreateType(arrType, rootCursor, sugName == null ? null : sugName + ""), count, sizeOf, Utils.getLocation(cxType, recDecl));
            if (unnamed) {
                if (sugName == null) throw new RuntimeException("Unhandled Error");
                ret.setDisplayName(sugName + "" + count + "_");
            }
        } else if (CXTypeKind.CXType_Elaborated.equals(kind)) {
            CXType target = LibclangFunctionSymbols.clang_Type_getNamedType(mem, cxType);
            ret = addOrCreateType(target, rootCursor, sugName);
        } else if (CXTypeKind.CXType_Record.equals(kind)) {
            CXCursor recDecl = LibclangFunctionSymbols.clang_getTypeDeclaration(mem, cxType);
            boolean unnamed = LibclangFunctionSymbols.clang_Cursor_isAnonymous(recDecl).operator().value() != 0;
            String displayName = unnamed ? sugName : null;
            if (Objects.equals(recDecl.kind().operator().value(), CXCursorKind.CXCursor_UnionDecl.operator().value()))
                ret = addOrCreateUnion(recDecl, displayName);
            else if (Objects.equals(recDecl.kind().operator().value(), CXCursorKind.CXCursor_StructDecl.operator().value())) {
                ret = addOrCreateStruct(recDecl, displayName);
            } else {
                Assert(false, "Unsupported declaration in " + typeName);
            }
        } else if (CXTypeKind.CXType_Typedef.equals(kind)) {
            CXCursor cursor = LibclangFunctionSymbols.clang_getTypeDeclaration(mem, cxType);
            ret = addOrCreateTypeDef(cursor);
        } else if (CXTypeKind.CXType_Enum.equals(kind)) {
            ret = addOrCreateEnum(cxType);
        } else if (CXTypeKind.CXType_IncompleteArray.equals(kind)) {
            CXType arrType = LibclangFunctionSymbols.clang_getArrayElementType(mem, cxType);
            ret = new Pointer(typeName, addOrCreateType(arrType), Utils.getLocation(cxType, rootCursor));
        } else {
            throw new RuntimeException("Unhandled type " + typeName + "(" + kind + ")");
        }
        Assert(ret != null);
        LoggerUtils.debug("Creating " + ret);
        types.put(ret.getTypeName(), ret);
        return ret;
    }

    public Type addOrCreateType(CXCursor cursor, String sugName) {
        return addOrCreateType(LibclangFunctionSymbols.clang_getCursorType(mem, cursor), cursor, sugName);
    }

    // todo: move to addOrCreateType()
    public Struct addOrCreateStruct(CXCursor cursor, String displayName) {
        Assert(CXCursorKind.CXCursor_StructDecl.equals(LibclangFunctionSymbols.clang_getCursorKind(cursor)));
        CXType cxType = LibclangFunctionSymbols.clang_getCursorType(mem, cursor);
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
        Assert(CXTypeKind.CXType_Record.equals(cxType.kind()));
        String name = getTypeName(cxType);
        CXCursor cursor = LibclangFunctionSymbols.clang_getTypeDeclaration(mem, cxType);
        if (types.containsKey(name)) {
            Type type = types.get(name);
            return findTargetStruct(type);
        }
        Struct struct = new Struct(name, LibclangFunctionSymbols.clang_Type_getSizeOf(cxType).operator().value(), Utils.getCursorLocation(cursor));
        struct.setDisplayName(displayName);
        types.put(struct.getTypeName(), struct);
        ArrayList<Para> paras = parseRecord(cursor, struct, cursor);
        struct.addMembers(paras);
        return struct;
    }


    String getStructFieldName(CXCursor parent, CXCursor decl) {
        final boolean[] arrival = {false};
        final String[] name = new String[1];
        LibclangFunctionSymbols.clang_visitChildren(parent,
                new CXCursorVisitor(mem, (CXCursorVisitor.Function) (_cursor, _, _) -> {
                    if (!arrival[0])
                        arrival[0] = LibclangFunctionSymbols.clang_equalCursors(decl, _cursor).operator().value() != 0;

                    CXCursorKind _kind = LibclangFunctionSymbols.clang_getCursorKind(_cursor);
                    if (arrival[0] && CXCursorKind.CXCursor_FieldDecl.equals(_kind)) {
                        CXString fieldName = LibclangFunctionSymbols.clang_getCursorSpelling(mem, _cursor);
                        name[0] = Utils.cXString2String(fieldName);
                        LibclangFunctionSymbols.clang_disposeString(fieldName);
                        return CXChildVisitResult.CXChildVisit_Break;
                    }
                    return CXChildVisitResult.CXChildVisit_Continue;
                }), new CXClientData(MemorySegment.NULL));
        return name[0];
    }

    private ArrayList<Para> parseRecord(CXCursor cursor_, Record ret, CXCursor offsetRef) {
        ArrayList<Para> paras = new ArrayList<>();
        LibclangFunctionSymbols.clang_visitChildren(cursor_,
                new CXCursorVisitor(mem, (CXCursorVisitor.Function) (cursor, parent, _) -> {
                    Utils.printLocation(cursor);
                    var kind = LibclangFunctionSymbols.clang_getCursorKind(cursor);
                    CXString cursorStr_ = LibclangFunctionSymbols.clang_getCursorSpelling(mem, cursor);
                    String cursorName = Utils.cXString2String(cursorStr_);
                    LibclangFunctionSymbols.clang_disposeString(cursorStr_);
                    if (CXCursorKind.CXCursor_StructDecl.equals(kind) || CXCursorKind.CXCursor_UnionDecl.equals(kind)) {
                        boolean inlined = LibclangFunctionSymbols.clang_Cursor_isAnonymousRecordDecl(cursor).operator().value() != 0;
                        boolean unnamed = LibclangFunctionSymbols.clang_Cursor_isAnonymous(cursor).operator().value() != 0;

                        LoggerUtils.debug("Struct " + cursorName + " in " + ret + " inlined " + inlined + " unnamed " + unnamed);
                        if (inlined) {
                            ArrayList<Para> tmp = parseRecord(cursor, ret, offsetRef);
                            paras.addAll(tmp);
                        } else {
                            String displayName = null;
                            if (unnamed) {
                                String fieldName = getStructFieldName(parent, cursor);
                                displayName = ret.getDisplayName() + "" + fieldName;
                            }
                            if (CXCursorKind.CXCursor_StructDecl.equals(kind))
                                addOrCreateStruct(cursor, displayName);
                            else
                                addOrCreateUnion(cursor, displayName);
                        }
                    } else if (CXCursorKind.CXCursor_FunctionDecl.equals(kind)) {
                        // function declared in Record
                        LoggerUtils.error("Function declared " + cursorName + " in " + ret + " is not allowed");
                        Assert(false);
                    } else if (CXCursorKind.CXCursor_FieldDecl.equals(kind)) {
                        LoggerUtils.debug("Field Declared " + cursorName + " in " + ret);
                        var memberType = addOrCreateType(cursor, ret.getDisplayName() + "" + cursorName);
//                long offset = LibclangFunctionSymbols.clang_Cursor_getOffsetOfField(cursor);
                        CXType cxType = LibclangFunctionSymbols.clang_getCursorType(mem, offsetRef);
                        long offset = LibclangFunctionSymbols.clang_Type_getOffsetOf(cxType, new Str(mem, cursorName)).operator().value();
                        long field = LibclangFunctionSymbols.clang_getFieldDeclBitWidth(cursor).operator().value();
                        if (field <= -1) {
                            long size = LibclangFunctionSymbols.clang_Type_getSizeOf(cxType).operator().value();
                            if (size <= -1) {
                                throw new RuntimeException();
                            }
                            field = size * 8;
                        }
                        if (!cursorName.isEmpty())
                            paras.add(new Para(memberType, cursorName, OptionalLong.of(offset), OptionalLong.of(field)));
                        else
                            System.out.println("Ignore unnamed field declare in [" + ret.getTypeName() + "] (" + Utils.getLocation(cursor) + ")");
                    } else if (CXCursorKind.CXCursor_EnumDecl.equals(kind)) {
                        LoggerUtils.debug("Field Declared " + cursorName + " in " + ret);
                        var memberType = addOrCreateType(cursor, null);
                        paras.add(new Para(memberType, cursorName, OptionalLong.empty(), OptionalLong.empty()));
                        Assert(false);
                    } else {
                        Assert(false, "Unhandled kind:" + kind);
                    }

                    return CXChildVisitResult.CXChildVisit_Continue;
                }), new CXClientData(MemorySegment.NULL));
        return paras;
    }

    public Union addOrCreateUnion(CXCursor cursor, String displayName) {
        CXType cxType = LibclangFunctionSymbols.clang_getCursorType(mem, cursor);
        Assert(CXCursorKind.CXCursor_UnionDecl.equals(LibclangFunctionSymbols.clang_getCursorKind(cursor)));
        return addOrCreateUnion(cxType, displayName);
    }

    public Union addOrCreateUnion(CXType cxType, String displayName) {
        Assert(CXTypeKind.CXType_Record.equals(cxType.kind()));
        CXCursor cursor = LibclangFunctionSymbols.clang_getTypeDeclaration(mem, cxType);
        String name = getTypeName(cxType);
        if (types.containsKey(name)) {
            Type type = types.get(name);
            return findTargetUnion(type);
        }
        Union ret = new Union(name, LibclangFunctionSymbols.clang_Type_getSizeOf(cxType).operator().value(), Utils.getCursorLocation(cursor));
        ret.setDisplayName(displayName);
        types.put(ret.getTypeName(), ret);
        ArrayList<Para> paras = parseRecord(cursor, ret, cursor);
        ret.addMembers(paras);
        return ret;
    }

    public Enum addOrCreateEnum(CXType cxType) {
        Assert(CXTypeKind.CXType_Enum.equals(cxType.kind()));
        CXCursor cursor = LibclangFunctionSymbols.clang_getTypeDeclaration(mem, cxType);
        var typeName = Utils.cXString2String(LibclangFunctionSymbols.clang_getTypeSpelling(mem, cxType));
        boolean unnamed = LibclangFunctionSymbols.clang_Cursor_isAnonymous(cursor).operator().value() != 0;
        Enum e = new Enum(typeName, unnamed, Utils.getCursorLocation(cursor));
        CXType enumType = LibclangFunctionSymbols.clang_getEnumDeclIntegerType(mem, cursor);
        Type type = addOrCreateType(enumType);
        LibclangFunctionSymbols.clang_visitChildren(cursor,
                new CXCursorVisitor(mem, (CXCursorVisitor.Function) (cur, _, _) -> {
                    if (CXCursorKind.CXCursor_EnumConstantDecl.equals(LibclangFunctionSymbols.clang_getCursorKind(cur))) {
                        CXString declName = LibclangFunctionSymbols.clang_getCursorSpelling(mem, cur);
                        long constant_value = LibclangFunctionSymbols.clang_getEnumConstantDeclValue(cur).operator().value();
                        e.addDeclare(new Declare(type, Utils.cXString2String(declName), constant_value + ""));
                    }
                    return CXChildVisitResult.CXChildVisit_Continue;
                }), new CXClientData(MemorySegment.NULL));
        return e;
    }

    public TypeDef addOrCreateTypeDef(CXCursor cursor) {
        var typedef_type = LibclangFunctionSymbols.clang_getTypedefDeclUnderlyingType(mem, cursor);
        String name = getTypeName(LibclangFunctionSymbols.clang_getCursorType(mem, cursor));
        if (types.containsKey(name)) {
            var obj = types.get(name);
            if (obj instanceof TypeDef typeDef)
                return typeDef;
            TypeDef ref = new TypeDef(name, obj, Utils.getCursorLocation(cursor));
            types.put(name, ref);
            return ref;
        }
        String sugName = name + "";
        var def = new TypeDef(name, addOrCreateType(typedef_type, cursor, sugName), Utils.getCursorLocation(cursor));
        types.put(def.getTypeName(), def);
        return def;
    }

    private String getTypeName(CXType cxType) {
        var typeName = LibclangFunctionSymbols.clang_getTypeSpelling(mem, cxType);
        var ret = Utils.cXString2String(typeName);
        LibclangFunctionSymbols.clang_disposeString(typeName);
        return ret;
    }

    public HashMap<String, Type> getTypes() {
        return types;
    }
}