package analyser;

import libclang.LibclangEnums;
import libclang.LibclangFunctions;
import libclang.shared.NString;
import libclang.shared.Pointer;
import libclang.shared.VI32List;
import libclang.shared.VPointerList;
import libclang.shared.values.VI32;
import libclang.shared.values.VI8;
import libclang.structs.CXCursor;
import libclang.structs.CXSourceLocation;
import libclang.structs.CXString;
import libclang.structs.CXType;
import libclang.values.CXFile;
import utils.LoggerUtils;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;

public class Utils {

    public static String cXString2String(CXString cxString) {
        Pointer<VI8<Byte>> str = LibclangFunctions.clang_getCString$Pointer(cxString);
        return new NString(str).reinterpretSize(Long.MAX_VALUE).get();
    }

    public static void printLocation(CXCursor cursor) {
        LoggerUtils.debug("Processing path " + getLocation(cursor));
    }

    public static String getLocation(CXType type, CXCursor fallback) {
        try (Arena mem = Arena.ofConfined()) {
            CXCursor cursor = LibclangFunctions.clang_getTypeDeclaration$CXCursor(mem, type);
            if (cursor.kind().equals(LibclangEnums.CXCursorKind.CXCursor_NoDeclFound)) {
                if (fallback == null)
                    return null;
                return getCursorLocation(fallback);
            }
            return getCursorLocation(cursor);
        }
    }

    public static String getTypeLocation(CXType type) {
        try (Arena mem = Arena.ofConfined()) {
            CXCursor cursor = LibclangFunctions.clang_getTypeDeclaration$CXCursor(mem, type);
            return getCursorLocation(cursor);
        }
    }

    public static String getCursorLocation(CXCursor cursor) {
        try (Arena mem = Arena.ofConfined()) {
            CXSourceLocation location = LibclangFunctions.clang_getCursorLocation$CXSourceLocation(mem, cursor);
            VPointerList<CXFile> file = CXFile.list(mem, 1);
            Pointer<VI32<Integer>> nullptr = () -> MemorySegment.NULL;
            LibclangFunctions.clang_getFileLocation(location, file, nullptr, nullptr, nullptr);
            CXString path = LibclangFunctions.clang_getFileName$CXString(mem, file.getFirst());
            String s = Utils.cXString2String(path);
            LibclangFunctions.clang_disposeString(path);
            return s;
        }
    }

    public static String getLocation(CXCursor cursor) {
        try (Arena mem = Arena.ofConfined()) {
            CXSourceLocation location = LibclangFunctions.clang_getCursorLocation$CXSourceLocation(mem, cursor);
            VPointerList<CXFile> file = CXFile.list(mem, 1);
            VI32List<VI32<Integer>> line = VI32.list(mem, 1);
            VI32List<VI32<Integer>> column = VI32.list(mem, 1);
            VI32List<VI32<Integer>> offset = VI32.list(mem, 1);
            LibclangFunctions.clang_getFileLocation(location, file, line, column, offset);
            CXString path = LibclangFunctions.clang_getFileName$CXString(mem, file.getFirst());
            String s = Utils.cXString2String(path) + " line " + line.getFirst() + " column " + column.getFirst() + " offset " + offset.getFirst();
            LibclangFunctions.clang_disposeString(path);
            return s;
        }
    }
}
