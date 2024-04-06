package analyser;

import libclang.LibclangEnums;
import libclang.LibclangFunctions;
import libclang.functions.CXCursorVisitor;
import libclang.shared.NString;
import libclang.shared.Pointer;
import libclang.shared.VPointerList;
import libclang.shared.values.VI8;
import libclang.structs.CXCursor;
import libclang.structs.CXString;
import libclang.structs.CXType;
import libclang.values.CXClientData;
import libclang.values.CXIndex;
import libclang.values.CXTargetInfo;
import libclang.values.CXTranslationUnit;
import utils.AutoCloseableChecker;
import utils.CheckedArena;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.List;

import static libclang.LibclangEnums.CXErrorCode.CXError_Success;
import static libclang.LibclangEnums.CXTranslationUnit_Flags.CXTranslationUnit_None;
import static utils.CommonUtils.*;

public class Analyser implements AutoCloseableChecker.NonThrowAutoCloseable {
    private final CheckedArena memory = CheckedArena.ofConfined();
    private final VPointerList<CXTranslationUnit> unit4declaration;

    public Analyser(String header, List<String> args) {
        unit4declaration = CXTranslationUnit.list(memory, 1);
        CXIndex index4declaration = LibclangFunctions.clang_createIndex$CXIndex(0, 0);
        try (Arena arena = Arena.ofConfined()) {
            var err = LibclangFunctions.clang_parseTranslationUnit2$CXErrorCode(
                    index4declaration,
                    new NString(arena, header), NString.list(arena, args)::pointer, args.size(),
                    nullptr(), 0,
                    CXTranslationUnit_None.value(), unit4declaration);
            if (!err.equals(CXError_Success) || isNull(unit4declaration)) {
                Assert(false, STR."Unable to parse translation unit (\{err}).");
            }
            CXTargetInfo cxTargetInfo = LibclangFunctions.clang_getTranslationUnitTargetInfo$CXTargetInfo(unit4declaration.getFirst());
            CXCursor cxCursor = LibclangFunctions.clang_getTranslationUnitCursor$CXCursor(arena, unit4declaration.getFirst());
            LibclangFunctions.clang_visitChildren$int(cxCursor, ((CXCursorVisitor.CXCursorVisitor$CXChildVisitResult$0) (cursor, parent, _) -> {
                cursor = cursor.reinterpretSize();
                parent = cursor.reinterpretSize();
                CXType cxType = LibclangFunctions.clang_getCursorType$CXType(arena, cursor);
                CXString cursorStr = LibclangFunctions.clang_getCursorSpelling$CXString(arena, cursor);
                CXString typeStr = LibclangFunctions.clang_getTypeSpelling$CXString(arena, cxType);
                System.err.println(cXString2String(cursorStr));
                System.err.println(cXString2String(typeStr));
                return LibclangEnums.CXChildVisitResult.CXChildVisit_Recurse;
            }).toVPointer(arena), new CXClientData(MemorySegment.NULL));
        }
    }

    public static String cXString2String(CXString cxString) {
        Pointer<VI8<Byte>> str = LibclangFunctions.clang_getCString$Pointer(cxString);
        String string = new NString(str).reinterpretSize(Long.MAX_VALUE).toString();
        LibclangFunctions.clang_disposeString(cxString);
        return string;
    }

    @Override
    public void close() {
        memory.close();
    }
}
