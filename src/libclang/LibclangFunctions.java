package libclang;

import libclang.structs.*;
import libclang.LibclangEnums.*;
import libclang.functions.*;
import libclang.values.*;
import libclang.shared.values.*;
import libclang.shared.*;
import libclang.shared.natives.*;
import libclang.shared.Value;
import libclang.shared.Pointer;
import libclang.shared.FunctionUtils;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public final class LibclangFunctions {
    private static MethodHandle clang_getCString;

    private static MemorySegment clang_getCString(MemorySegment string) {
        if (clang_getCString == null) {
            clang_getCString = LibclangSymbols.toMethodHandle("clang_getCString", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCString"));
        }
        try {
            return (MemorySegment) clang_getCString.invoke(string);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<VI8<Byte>> clang_getCString$Pointer(CXString string) {
        return FunctionUtils.makePointer(clang_getCString(string.pointer()));
    }

    private static MethodHandle clang_disposeString;

    private static void clang_disposeString(MemorySegment string) {
        if (clang_disposeString == null) {
            clang_disposeString = LibclangSymbols.toMethodHandle("clang_disposeString", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_disposeString"));
        }
        try {
            clang_disposeString.invoke(string);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_disposeString(CXString string) {
        clang_disposeString(string.pointer());
    }

    private static MethodHandle clang_disposeStringSet;

    private static void clang_disposeStringSet(MemorySegment set) {
        if (clang_disposeStringSet == null) {
            clang_disposeStringSet = LibclangSymbols.toMethodHandle("clang_disposeStringSet", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_disposeStringSet"));
        }
        try {
            clang_disposeStringSet.invoke(set);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_disposeStringSet(Pointer<CXStringSet> set) {
        clang_disposeStringSet(set.pointer());
    }

    private static MethodHandle clang_getBuildSessionTimestamp;

    private static long clang_getBuildSessionTimestamp() {
        if (clang_getBuildSessionTimestamp == null) {
            clang_getBuildSessionTimestamp = LibclangSymbols.toMethodHandle("clang_getBuildSessionTimestamp", FunctionDescriptor.of(ValueLayout.JAVA_LONG)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getBuildSessionTimestamp"));
        }
        try {
            return (long) clang_getBuildSessionTimestamp.invoke();
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static long clang_getBuildSessionTimestamp$long() {
        return clang_getBuildSessionTimestamp();
    }

    private static MethodHandle clang_VirtualFileOverlay_create;

    private static MemorySegment clang_VirtualFileOverlay_create(int options) {
        if (clang_VirtualFileOverlay_create == null) {
            clang_VirtualFileOverlay_create = LibclangSymbols.toMethodHandle("clang_VirtualFileOverlay_create", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_VirtualFileOverlay_create"));
        }
        try {
            return (MemorySegment) clang_VirtualFileOverlay_create.invoke(options);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXVirtualFileOverlay clang_VirtualFileOverlay_create$CXVirtualFileOverlay(int options) {
        return new CXVirtualFileOverlay(clang_VirtualFileOverlay_create(options));
    }

    private static MethodHandle clang_VirtualFileOverlay_addFileMapping;

    private static int clang_VirtualFileOverlay_addFileMapping(MemorySegment para0, MemorySegment virtualPath, MemorySegment realPath) {
        if (clang_VirtualFileOverlay_addFileMapping == null) {
            clang_VirtualFileOverlay_addFileMapping = LibclangSymbols.toMethodHandle("clang_VirtualFileOverlay_addFileMapping", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_VirtualFileOverlay_addFileMapping"));
        }
        try {
            return (int) clang_VirtualFileOverlay_addFileMapping.invoke(para0, virtualPath, realPath);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXErrorCode clang_VirtualFileOverlay_addFileMapping$CXErrorCode(CXVirtualFileOverlay para0, Pointer<VI8<Byte>> virtualPath, Pointer<VI8<Byte>> realPath) {
        return new CXErrorCode(clang_VirtualFileOverlay_addFileMapping(para0.value(), virtualPath.pointer(), realPath.pointer()));
    }

    private static MethodHandle clang_VirtualFileOverlay_setCaseSensitivity;

    private static int clang_VirtualFileOverlay_setCaseSensitivity(MemorySegment para0, int caseSensitive) {
        if (clang_VirtualFileOverlay_setCaseSensitivity == null) {
            clang_VirtualFileOverlay_setCaseSensitivity = LibclangSymbols.toMethodHandle("clang_VirtualFileOverlay_setCaseSensitivity", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_VirtualFileOverlay_setCaseSensitivity"));
        }
        try {
            return (int) clang_VirtualFileOverlay_setCaseSensitivity.invoke(para0, caseSensitive);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXErrorCode clang_VirtualFileOverlay_setCaseSensitivity$CXErrorCode(CXVirtualFileOverlay para0, int caseSensitive) {
        return new CXErrorCode(clang_VirtualFileOverlay_setCaseSensitivity(para0.value(), caseSensitive));
    }

    private static MethodHandle clang_VirtualFileOverlay_writeToBuffer;

    private static int clang_VirtualFileOverlay_writeToBuffer(MemorySegment para0, int options, MemorySegment out_buffer_ptr, MemorySegment out_buffer_size) {
        if (clang_VirtualFileOverlay_writeToBuffer == null) {
            clang_VirtualFileOverlay_writeToBuffer = LibclangSymbols.toMethodHandle("clang_VirtualFileOverlay_writeToBuffer", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_VirtualFileOverlay_writeToBuffer"));
        }
        try {
            return (int) clang_VirtualFileOverlay_writeToBuffer.invoke(para0, options, out_buffer_ptr, out_buffer_size);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXErrorCode clang_VirtualFileOverlay_writeToBuffer$CXErrorCode(CXVirtualFileOverlay para0, int options, Pointer<Pointer<VI8<Byte>>> out_buffer_ptr, Pointer<VI32<Integer>> out_buffer_size) {
        return new CXErrorCode(clang_VirtualFileOverlay_writeToBuffer(para0.value(), options, out_buffer_ptr.pointer(), out_buffer_size.pointer()));
    }

    private static MethodHandle clang_free;

    private static void clang_free(MemorySegment buffer) {
        if (clang_free == null) {
            clang_free = LibclangSymbols.toMethodHandle("clang_free", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_free"));
        }
        try {
            clang_free.invoke(buffer);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_free(Pointer<?> buffer) {
        clang_free(buffer.pointer());
    }

    private static MethodHandle clang_VirtualFileOverlay_dispose;

    private static void clang_VirtualFileOverlay_dispose(MemorySegment para0) {
        if (clang_VirtualFileOverlay_dispose == null) {
            clang_VirtualFileOverlay_dispose = LibclangSymbols.toMethodHandle("clang_VirtualFileOverlay_dispose", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_VirtualFileOverlay_dispose"));
        }
        try {
            clang_VirtualFileOverlay_dispose.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_VirtualFileOverlay_dispose(CXVirtualFileOverlay para0) {
        clang_VirtualFileOverlay_dispose(para0.value());
    }

    private static MethodHandle clang_ModuleMapDescriptor_create;

    private static MemorySegment clang_ModuleMapDescriptor_create(int options) {
        if (clang_ModuleMapDescriptor_create == null) {
            clang_ModuleMapDescriptor_create = LibclangSymbols.toMethodHandle("clang_ModuleMapDescriptor_create", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_ModuleMapDescriptor_create"));
        }
        try {
            return (MemorySegment) clang_ModuleMapDescriptor_create.invoke(options);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXModuleMapDescriptor clang_ModuleMapDescriptor_create$CXModuleMapDescriptor(int options) {
        return new CXModuleMapDescriptor(clang_ModuleMapDescriptor_create(options));
    }

    private static MethodHandle clang_ModuleMapDescriptor_setFrameworkModuleName;

    private static int clang_ModuleMapDescriptor_setFrameworkModuleName(MemorySegment para0, MemorySegment name) {
        if (clang_ModuleMapDescriptor_setFrameworkModuleName == null) {
            clang_ModuleMapDescriptor_setFrameworkModuleName = LibclangSymbols.toMethodHandle("clang_ModuleMapDescriptor_setFrameworkModuleName", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_ModuleMapDescriptor_setFrameworkModuleName"));
        }
        try {
            return (int) clang_ModuleMapDescriptor_setFrameworkModuleName.invoke(para0, name);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXErrorCode clang_ModuleMapDescriptor_setFrameworkModuleName$CXErrorCode(CXModuleMapDescriptor para0, Pointer<VI8<Byte>> name) {
        return new CXErrorCode(clang_ModuleMapDescriptor_setFrameworkModuleName(para0.value(), name.pointer()));
    }

    private static MethodHandle clang_ModuleMapDescriptor_setUmbrellaHeader;

    private static int clang_ModuleMapDescriptor_setUmbrellaHeader(MemorySegment para0, MemorySegment name) {
        if (clang_ModuleMapDescriptor_setUmbrellaHeader == null) {
            clang_ModuleMapDescriptor_setUmbrellaHeader = LibclangSymbols.toMethodHandle("clang_ModuleMapDescriptor_setUmbrellaHeader", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_ModuleMapDescriptor_setUmbrellaHeader"));
        }
        try {
            return (int) clang_ModuleMapDescriptor_setUmbrellaHeader.invoke(para0, name);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXErrorCode clang_ModuleMapDescriptor_setUmbrellaHeader$CXErrorCode(CXModuleMapDescriptor para0, Pointer<VI8<Byte>> name) {
        return new CXErrorCode(clang_ModuleMapDescriptor_setUmbrellaHeader(para0.value(), name.pointer()));
    }

    private static MethodHandle clang_ModuleMapDescriptor_writeToBuffer;

    private static int clang_ModuleMapDescriptor_writeToBuffer(MemorySegment para0, int options, MemorySegment out_buffer_ptr, MemorySegment out_buffer_size) {
        if (clang_ModuleMapDescriptor_writeToBuffer == null) {
            clang_ModuleMapDescriptor_writeToBuffer = LibclangSymbols.toMethodHandle("clang_ModuleMapDescriptor_writeToBuffer", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_ModuleMapDescriptor_writeToBuffer"));
        }
        try {
            return (int) clang_ModuleMapDescriptor_writeToBuffer.invoke(para0, options, out_buffer_ptr, out_buffer_size);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXErrorCode clang_ModuleMapDescriptor_writeToBuffer$CXErrorCode(CXModuleMapDescriptor para0, int options, Pointer<Pointer<VI8<Byte>>> out_buffer_ptr, Pointer<VI32<Integer>> out_buffer_size) {
        return new CXErrorCode(clang_ModuleMapDescriptor_writeToBuffer(para0.value(), options, out_buffer_ptr.pointer(), out_buffer_size.pointer()));
    }

    private static MethodHandle clang_ModuleMapDescriptor_dispose;

    private static void clang_ModuleMapDescriptor_dispose(MemorySegment para0) {
        if (clang_ModuleMapDescriptor_dispose == null) {
            clang_ModuleMapDescriptor_dispose = LibclangSymbols.toMethodHandle("clang_ModuleMapDescriptor_dispose", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_ModuleMapDescriptor_dispose"));
        }
        try {
            clang_ModuleMapDescriptor_dispose.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_ModuleMapDescriptor_dispose(CXModuleMapDescriptor para0) {
        clang_ModuleMapDescriptor_dispose(para0.value());
    }

    private static MethodHandle clang_getFileName;

    private static MemorySegment clang_getFileName(SegmentAllocator allocator, MemorySegment SFile) {
        if (clang_getFileName == null) {
            clang_getFileName = LibclangSymbols.toMethodHandle("clang_getFileName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getFileName"));
        }
        try {
            return (MemorySegment) clang_getFileName.invoke(allocator, SFile);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getFileName$CXString(SegmentAllocator allocator,CXFile SFile) {
        return new CXString(FunctionUtils.makePointer(clang_getFileName(allocator,SFile.value())));
    }

    private static MethodHandle clang_getFileTime;

    private static long clang_getFileTime(MemorySegment SFile) {
        if (clang_getFileTime == null) {
            clang_getFileTime = LibclangSymbols.toMethodHandle("clang_getFileTime", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getFileTime"));
        }
        try {
            return (long) clang_getFileTime.invoke(SFile);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static time_t clang_getFileTime$time_t(CXFile SFile) {
        return new time_t(clang_getFileTime(SFile.value()));
    }

    private static MethodHandle clang_getFileUniqueID;

    private static int clang_getFileUniqueID(MemorySegment file, MemorySegment outID) {
        if (clang_getFileUniqueID == null) {
            clang_getFileUniqueID = LibclangSymbols.toMethodHandle("clang_getFileUniqueID", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getFileUniqueID"));
        }
        try {
            return (int) clang_getFileUniqueID.invoke(file, outID);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getFileUniqueID$int(CXFile file, Pointer<CXFileUniqueID> outID) {
        return clang_getFileUniqueID(file.value(), outID.pointer());
    }

    private static MethodHandle clang_File_isEqual;

    private static int clang_File_isEqual(MemorySegment file1, MemorySegment file2) {
        if (clang_File_isEqual == null) {
            clang_File_isEqual = LibclangSymbols.toMethodHandle("clang_File_isEqual", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_File_isEqual"));
        }
        try {
            return (int) clang_File_isEqual.invoke(file1, file2);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_File_isEqual$int(CXFile file1, CXFile file2) {
        return clang_File_isEqual(file1.value(), file2.value());
    }

    private static MethodHandle clang_File_tryGetRealPathName;

    private static MemorySegment clang_File_tryGetRealPathName(SegmentAllocator allocator, MemorySegment file) {
        if (clang_File_tryGetRealPathName == null) {
            clang_File_tryGetRealPathName = LibclangSymbols.toMethodHandle("clang_File_tryGetRealPathName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_File_tryGetRealPathName"));
        }
        try {
            return (MemorySegment) clang_File_tryGetRealPathName.invoke(allocator, file);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_File_tryGetRealPathName$CXString(SegmentAllocator allocator,CXFile file) {
        return new CXString(FunctionUtils.makePointer(clang_File_tryGetRealPathName(allocator,file.value())));
    }

    private static MethodHandle clang_getNullLocation;

    private static MemorySegment clang_getNullLocation(SegmentAllocator allocator) {
        if (clang_getNullLocation == null) {
            clang_getNullLocation = LibclangSymbols.toMethodHandle("clang_getNullLocation", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getNullLocation"));
        }
        try {
            return (MemorySegment) clang_getNullLocation.invoke(allocator);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceLocation clang_getNullLocation$CXSourceLocation(SegmentAllocator allocator) {
        return new CXSourceLocation(FunctionUtils.makePointer(clang_getNullLocation(allocator)));
    }

    private static MethodHandle clang_equalLocations;

    private static int clang_equalLocations(MemorySegment loc1, MemorySegment loc2) {
        if (clang_equalLocations == null) {
            clang_equalLocations = LibclangSymbols.toMethodHandle("clang_equalLocations", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_equalLocations"));
        }
        try {
            return (int) clang_equalLocations.invoke(loc1, loc2);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_equalLocations$int(CXSourceLocation loc1, CXSourceLocation loc2) {
        return clang_equalLocations(loc1.pointer(), loc2.pointer());
    }

    private static MethodHandle clang_Location_isInSystemHeader;

    private static int clang_Location_isInSystemHeader(MemorySegment location) {
        if (clang_Location_isInSystemHeader == null) {
            clang_Location_isInSystemHeader = LibclangSymbols.toMethodHandle("clang_Location_isInSystemHeader", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Location_isInSystemHeader"));
        }
        try {
            return (int) clang_Location_isInSystemHeader.invoke(location);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Location_isInSystemHeader$int(CXSourceLocation location) {
        return clang_Location_isInSystemHeader(location.pointer());
    }

    private static MethodHandle clang_Location_isFromMainFile;

    private static int clang_Location_isFromMainFile(MemorySegment location) {
        if (clang_Location_isFromMainFile == null) {
            clang_Location_isFromMainFile = LibclangSymbols.toMethodHandle("clang_Location_isFromMainFile", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Location_isFromMainFile"));
        }
        try {
            return (int) clang_Location_isFromMainFile.invoke(location);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Location_isFromMainFile$int(CXSourceLocation location) {
        return clang_Location_isFromMainFile(location.pointer());
    }

    private static MethodHandle clang_getNullRange;

    private static MemorySegment clang_getNullRange(SegmentAllocator allocator) {
        if (clang_getNullRange == null) {
            clang_getNullRange = LibclangSymbols.toMethodHandle("clang_getNullRange", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getNullRange"));
        }
        try {
            return (MemorySegment) clang_getNullRange.invoke(allocator);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceRange clang_getNullRange$CXSourceRange(SegmentAllocator allocator) {
        return new CXSourceRange(FunctionUtils.makePointer(clang_getNullRange(allocator)));
    }

    private static MethodHandle clang_getRange;

    private static MemorySegment clang_getRange(SegmentAllocator allocator, MemorySegment begin, MemorySegment end) {
        if (clang_getRange == null) {
            clang_getRange = LibclangSymbols.toMethodHandle("clang_getRange", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getRange"));
        }
        try {
            return (MemorySegment) clang_getRange.invoke(allocator, begin, end);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceRange clang_getRange$CXSourceRange(SegmentAllocator allocator,CXSourceLocation begin,CXSourceLocation end) {
        return new CXSourceRange(FunctionUtils.makePointer(clang_getRange(allocator,begin.pointer(),end.pointer())));
    }

    private static MethodHandle clang_equalRanges;

    private static int clang_equalRanges(MemorySegment range1, MemorySegment range2) {
        if (clang_equalRanges == null) {
            clang_equalRanges = LibclangSymbols.toMethodHandle("clang_equalRanges", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_equalRanges"));
        }
        try {
            return (int) clang_equalRanges.invoke(range1, range2);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_equalRanges$int(CXSourceRange range1, CXSourceRange range2) {
        return clang_equalRanges(range1.pointer(), range2.pointer());
    }

    private static MethodHandle clang_Range_isNull;

    private static int clang_Range_isNull(MemorySegment range) {
        if (clang_Range_isNull == null) {
            clang_Range_isNull = LibclangSymbols.toMethodHandle("clang_Range_isNull", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Range_isNull"));
        }
        try {
            return (int) clang_Range_isNull.invoke(range);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Range_isNull$int(CXSourceRange range) {
        return clang_Range_isNull(range.pointer());
    }

    private static MethodHandle clang_getExpansionLocation;

    private static void clang_getExpansionLocation(MemorySegment location, MemorySegment file, MemorySegment line, MemorySegment column, MemorySegment offset) {
        if (clang_getExpansionLocation == null) {
            clang_getExpansionLocation = LibclangSymbols.toMethodHandle("clang_getExpansionLocation", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getExpansionLocation"));
        }
        try {
            clang_getExpansionLocation.invoke(location, file, line, column, offset);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_getExpansionLocation(CXSourceLocation location, Pointer<CXFile> file, Pointer<VI32<Integer>> line, Pointer<VI32<Integer>> column, Pointer<VI32<Integer>> offset) {
        clang_getExpansionLocation(location.pointer(), file.pointer(), line.pointer(), column.pointer(), offset.pointer());
    }

    private static MethodHandle clang_getPresumedLocation;

    private static void clang_getPresumedLocation(MemorySegment location, MemorySegment filename, MemorySegment line, MemorySegment column) {
        if (clang_getPresumedLocation == null) {
            clang_getPresumedLocation = LibclangSymbols.toMethodHandle("clang_getPresumedLocation", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getPresumedLocation"));
        }
        try {
            clang_getPresumedLocation.invoke(location, filename, line, column);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_getPresumedLocation(CXSourceLocation location, Pointer<CXString> filename, Pointer<VI32<Integer>> line, Pointer<VI32<Integer>> column) {
        clang_getPresumedLocation(location.pointer(), filename.pointer(), line.pointer(), column.pointer());
    }

    private static MethodHandle clang_getInstantiationLocation;

    private static void clang_getInstantiationLocation(MemorySegment location, MemorySegment file, MemorySegment line, MemorySegment column, MemorySegment offset) {
        if (clang_getInstantiationLocation == null) {
            clang_getInstantiationLocation = LibclangSymbols.toMethodHandle("clang_getInstantiationLocation", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getInstantiationLocation"));
        }
        try {
            clang_getInstantiationLocation.invoke(location, file, line, column, offset);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_getInstantiationLocation(CXSourceLocation location, Pointer<CXFile> file, Pointer<VI32<Integer>> line, Pointer<VI32<Integer>> column, Pointer<VI32<Integer>> offset) {
        clang_getInstantiationLocation(location.pointer(), file.pointer(), line.pointer(), column.pointer(), offset.pointer());
    }

    private static MethodHandle clang_getSpellingLocation;

    private static void clang_getSpellingLocation(MemorySegment location, MemorySegment file, MemorySegment line, MemorySegment column, MemorySegment offset) {
        if (clang_getSpellingLocation == null) {
            clang_getSpellingLocation = LibclangSymbols.toMethodHandle("clang_getSpellingLocation", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getSpellingLocation"));
        }
        try {
            clang_getSpellingLocation.invoke(location, file, line, column, offset);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_getSpellingLocation(CXSourceLocation location, Pointer<CXFile> file, Pointer<VI32<Integer>> line, Pointer<VI32<Integer>> column, Pointer<VI32<Integer>> offset) {
        clang_getSpellingLocation(location.pointer(), file.pointer(), line.pointer(), column.pointer(), offset.pointer());
    }

    private static MethodHandle clang_getFileLocation;

    private static void clang_getFileLocation(MemorySegment location, MemorySegment file, MemorySegment line, MemorySegment column, MemorySegment offset) {
        if (clang_getFileLocation == null) {
            clang_getFileLocation = LibclangSymbols.toMethodHandle("clang_getFileLocation", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getFileLocation"));
        }
        try {
            clang_getFileLocation.invoke(location, file, line, column, offset);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_getFileLocation(CXSourceLocation location, Pointer<CXFile> file, Pointer<VI32<Integer>> line, Pointer<VI32<Integer>> column, Pointer<VI32<Integer>> offset) {
        clang_getFileLocation(location.pointer(), file.pointer(), line.pointer(), column.pointer(), offset.pointer());
    }

    private static MethodHandle clang_getRangeStart;

    private static MemorySegment clang_getRangeStart(SegmentAllocator allocator, MemorySegment range) {
        if (clang_getRangeStart == null) {
            clang_getRangeStart = LibclangSymbols.toMethodHandle("clang_getRangeStart", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getRangeStart"));
        }
        try {
            return (MemorySegment) clang_getRangeStart.invoke(allocator, range);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceLocation clang_getRangeStart$CXSourceLocation(SegmentAllocator allocator,CXSourceRange range) {
        return new CXSourceLocation(FunctionUtils.makePointer(clang_getRangeStart(allocator,range.pointer())));
    }

    private static MethodHandle clang_getRangeEnd;

    private static MemorySegment clang_getRangeEnd(SegmentAllocator allocator, MemorySegment range) {
        if (clang_getRangeEnd == null) {
            clang_getRangeEnd = LibclangSymbols.toMethodHandle("clang_getRangeEnd", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getRangeEnd"));
        }
        try {
            return (MemorySegment) clang_getRangeEnd.invoke(allocator, range);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceLocation clang_getRangeEnd$CXSourceLocation(SegmentAllocator allocator,CXSourceRange range) {
        return new CXSourceLocation(FunctionUtils.makePointer(clang_getRangeEnd(allocator,range.pointer())));
    }

    private static MethodHandle clang_disposeSourceRangeList;

    private static void clang_disposeSourceRangeList(MemorySegment ranges) {
        if (clang_disposeSourceRangeList == null) {
            clang_disposeSourceRangeList = LibclangSymbols.toMethodHandle("clang_disposeSourceRangeList", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_disposeSourceRangeList"));
        }
        try {
            clang_disposeSourceRangeList.invoke(ranges);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_disposeSourceRangeList(Pointer<CXSourceRangeList> ranges) {
        clang_disposeSourceRangeList(ranges.pointer());
    }

    private static MethodHandle clang_getNumDiagnosticsInSet;

    private static int clang_getNumDiagnosticsInSet(MemorySegment Diags) {
        if (clang_getNumDiagnosticsInSet == null) {
            clang_getNumDiagnosticsInSet = LibclangSymbols.toMethodHandle("clang_getNumDiagnosticsInSet", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getNumDiagnosticsInSet"));
        }
        try {
            return (int) clang_getNumDiagnosticsInSet.invoke(Diags);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getNumDiagnosticsInSet$int(CXDiagnosticSet Diags) {
        return clang_getNumDiagnosticsInSet(Diags.value());
    }

    private static MethodHandle clang_getDiagnosticInSet;

    private static MemorySegment clang_getDiagnosticInSet(MemorySegment Diags, int Index) {
        if (clang_getDiagnosticInSet == null) {
            clang_getDiagnosticInSet = LibclangSymbols.toMethodHandle("clang_getDiagnosticInSet", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDiagnosticInSet"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticInSet.invoke(Diags, Index);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXDiagnostic clang_getDiagnosticInSet$CXDiagnostic(CXDiagnosticSet Diags, int Index) {
        return new CXDiagnostic(clang_getDiagnosticInSet(Diags.value(), Index));
    }

    private static MethodHandle clang_loadDiagnostics;

    private static MemorySegment clang_loadDiagnostics(MemorySegment file, MemorySegment error, MemorySegment errorString) {
        if (clang_loadDiagnostics == null) {
            clang_loadDiagnostics = LibclangSymbols.toMethodHandle("clang_loadDiagnostics", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_loadDiagnostics"));
        }
        try {
            return (MemorySegment) clang_loadDiagnostics.invoke(file, error, errorString);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXDiagnosticSet clang_loadDiagnostics$CXDiagnosticSet(Pointer<VI8<Byte>> file, Pointer<CXLoadDiag_Error> error, Pointer<CXString> errorString) {
        return new CXDiagnosticSet(clang_loadDiagnostics(file.pointer(), error.pointer(), errorString.pointer()));
    }

    private static MethodHandle clang_disposeDiagnosticSet;

    private static void clang_disposeDiagnosticSet(MemorySegment Diags) {
        if (clang_disposeDiagnosticSet == null) {
            clang_disposeDiagnosticSet = LibclangSymbols.toMethodHandle("clang_disposeDiagnosticSet", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_disposeDiagnosticSet"));
        }
        try {
            clang_disposeDiagnosticSet.invoke(Diags);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_disposeDiagnosticSet(CXDiagnosticSet Diags) {
        clang_disposeDiagnosticSet(Diags.value());
    }

    private static MethodHandle clang_getChildDiagnostics;

    private static MemorySegment clang_getChildDiagnostics(MemorySegment D) {
        if (clang_getChildDiagnostics == null) {
            clang_getChildDiagnostics = LibclangSymbols.toMethodHandle("clang_getChildDiagnostics", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getChildDiagnostics"));
        }
        try {
            return (MemorySegment) clang_getChildDiagnostics.invoke(D);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXDiagnosticSet clang_getChildDiagnostics$CXDiagnosticSet(CXDiagnostic D) {
        return new CXDiagnosticSet(clang_getChildDiagnostics(D.value()));
    }

    private static MethodHandle clang_disposeDiagnostic;

    private static void clang_disposeDiagnostic(MemorySegment Diagnostic) {
        if (clang_disposeDiagnostic == null) {
            clang_disposeDiagnostic = LibclangSymbols.toMethodHandle("clang_disposeDiagnostic", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_disposeDiagnostic"));
        }
        try {
            clang_disposeDiagnostic.invoke(Diagnostic);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_disposeDiagnostic(CXDiagnostic Diagnostic) {
        clang_disposeDiagnostic(Diagnostic.value());
    }

    private static MethodHandle clang_formatDiagnostic;

    private static MemorySegment clang_formatDiagnostic(SegmentAllocator allocator, MemorySegment Diagnostic, int Options) {
        if (clang_formatDiagnostic == null) {
            clang_formatDiagnostic = LibclangSymbols.toMethodHandle("clang_formatDiagnostic", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_formatDiagnostic"));
        }
        try {
            return (MemorySegment) clang_formatDiagnostic.invoke(allocator, Diagnostic, Options);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_formatDiagnostic$CXString(SegmentAllocator allocator,CXDiagnostic Diagnostic,int Options) {
        return new CXString(FunctionUtils.makePointer(clang_formatDiagnostic(allocator,Diagnostic.value(),Options)));
    }

    private static MethodHandle clang_defaultDiagnosticDisplayOptions;

    private static int clang_defaultDiagnosticDisplayOptions() {
        if (clang_defaultDiagnosticDisplayOptions == null) {
            clang_defaultDiagnosticDisplayOptions = LibclangSymbols.toMethodHandle("clang_defaultDiagnosticDisplayOptions", FunctionDescriptor.of(ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_defaultDiagnosticDisplayOptions"));
        }
        try {
            return (int) clang_defaultDiagnosticDisplayOptions.invoke();
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_defaultDiagnosticDisplayOptions$int() {
        return clang_defaultDiagnosticDisplayOptions();
    }

    private static MethodHandle clang_getDiagnosticSeverity;

    private static int clang_getDiagnosticSeverity(MemorySegment para0) {
        if (clang_getDiagnosticSeverity == null) {
            clang_getDiagnosticSeverity = LibclangSymbols.toMethodHandle("clang_getDiagnosticSeverity", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDiagnosticSeverity"));
        }
        try {
            return (int) clang_getDiagnosticSeverity.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXDiagnosticSeverity clang_getDiagnosticSeverity$CXDiagnosticSeverity(CXDiagnostic para0) {
        return new CXDiagnosticSeverity(clang_getDiagnosticSeverity(para0.value()));
    }

    private static MethodHandle clang_getDiagnosticLocation;

    private static MemorySegment clang_getDiagnosticLocation(SegmentAllocator allocator, MemorySegment para0) {
        if (clang_getDiagnosticLocation == null) {
            clang_getDiagnosticLocation = LibclangSymbols.toMethodHandle("clang_getDiagnosticLocation", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDiagnosticLocation"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticLocation.invoke(allocator, para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceLocation clang_getDiagnosticLocation$CXSourceLocation(SegmentAllocator allocator,CXDiagnostic para0) {
        return new CXSourceLocation(FunctionUtils.makePointer(clang_getDiagnosticLocation(allocator,para0.value())));
    }

    private static MethodHandle clang_getDiagnosticSpelling;

    private static MemorySegment clang_getDiagnosticSpelling(SegmentAllocator allocator, MemorySegment para0) {
        if (clang_getDiagnosticSpelling == null) {
            clang_getDiagnosticSpelling = LibclangSymbols.toMethodHandle("clang_getDiagnosticSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDiagnosticSpelling"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticSpelling.invoke(allocator, para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getDiagnosticSpelling$CXString(SegmentAllocator allocator,CXDiagnostic para0) {
        return new CXString(FunctionUtils.makePointer(clang_getDiagnosticSpelling(allocator,para0.value())));
    }

    private static MethodHandle clang_getDiagnosticOption;

    private static MemorySegment clang_getDiagnosticOption(SegmentAllocator allocator, MemorySegment Diag, MemorySegment Disable) {
        if (clang_getDiagnosticOption == null) {
            clang_getDiagnosticOption = LibclangSymbols.toMethodHandle("clang_getDiagnosticOption", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDiagnosticOption"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticOption.invoke(allocator, Diag, Disable);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getDiagnosticOption$CXString(SegmentAllocator allocator,CXDiagnostic Diag,Pointer<CXString> Disable) {
        return new CXString(FunctionUtils.makePointer(clang_getDiagnosticOption(allocator,Diag.value(),Disable.pointer())));
    }

    private static MethodHandle clang_getDiagnosticCategory;

    private static int clang_getDiagnosticCategory(MemorySegment para0) {
        if (clang_getDiagnosticCategory == null) {
            clang_getDiagnosticCategory = LibclangSymbols.toMethodHandle("clang_getDiagnosticCategory", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDiagnosticCategory"));
        }
        try {
            return (int) clang_getDiagnosticCategory.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getDiagnosticCategory$int(CXDiagnostic para0) {
        return clang_getDiagnosticCategory(para0.value());
    }

    private static MethodHandle clang_getDiagnosticCategoryName;

    private static MemorySegment clang_getDiagnosticCategoryName(SegmentAllocator allocator, int Category) {
        if (clang_getDiagnosticCategoryName == null) {
            clang_getDiagnosticCategoryName = LibclangSymbols.toMethodHandle("clang_getDiagnosticCategoryName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDiagnosticCategoryName"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticCategoryName.invoke(allocator, Category);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getDiagnosticCategoryName$CXString(SegmentAllocator allocator,int Category) {
        return new CXString(FunctionUtils.makePointer(clang_getDiagnosticCategoryName(allocator,Category)));
    }

    private static MethodHandle clang_getDiagnosticCategoryText;

    private static MemorySegment clang_getDiagnosticCategoryText(SegmentAllocator allocator, MemorySegment para0) {
        if (clang_getDiagnosticCategoryText == null) {
            clang_getDiagnosticCategoryText = LibclangSymbols.toMethodHandle("clang_getDiagnosticCategoryText", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDiagnosticCategoryText"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticCategoryText.invoke(allocator, para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getDiagnosticCategoryText$CXString(SegmentAllocator allocator,CXDiagnostic para0) {
        return new CXString(FunctionUtils.makePointer(clang_getDiagnosticCategoryText(allocator,para0.value())));
    }

    private static MethodHandle clang_getDiagnosticNumRanges;

    private static int clang_getDiagnosticNumRanges(MemorySegment para0) {
        if (clang_getDiagnosticNumRanges == null) {
            clang_getDiagnosticNumRanges = LibclangSymbols.toMethodHandle("clang_getDiagnosticNumRanges", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDiagnosticNumRanges"));
        }
        try {
            return (int) clang_getDiagnosticNumRanges.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getDiagnosticNumRanges$int(CXDiagnostic para0) {
        return clang_getDiagnosticNumRanges(para0.value());
    }

    private static MethodHandle clang_getDiagnosticRange;

    private static MemorySegment clang_getDiagnosticRange(SegmentAllocator allocator, MemorySegment Diagnostic, int Range) {
        if (clang_getDiagnosticRange == null) {
            clang_getDiagnosticRange = LibclangSymbols.toMethodHandle("clang_getDiagnosticRange", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDiagnosticRange"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticRange.invoke(allocator, Diagnostic, Range);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceRange clang_getDiagnosticRange$CXSourceRange(SegmentAllocator allocator,CXDiagnostic Diagnostic,int Range) {
        return new CXSourceRange(FunctionUtils.makePointer(clang_getDiagnosticRange(allocator,Diagnostic.value(),Range)));
    }

    private static MethodHandle clang_getDiagnosticNumFixIts;

    private static int clang_getDiagnosticNumFixIts(MemorySegment Diagnostic) {
        if (clang_getDiagnosticNumFixIts == null) {
            clang_getDiagnosticNumFixIts = LibclangSymbols.toMethodHandle("clang_getDiagnosticNumFixIts", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDiagnosticNumFixIts"));
        }
        try {
            return (int) clang_getDiagnosticNumFixIts.invoke(Diagnostic);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getDiagnosticNumFixIts$int(CXDiagnostic Diagnostic) {
        return clang_getDiagnosticNumFixIts(Diagnostic.value());
    }

    private static MethodHandle clang_getDiagnosticFixIt;

    private static MemorySegment clang_getDiagnosticFixIt(SegmentAllocator allocator, MemorySegment Diagnostic, int FixIt, MemorySegment ReplacementRange) {
        if (clang_getDiagnosticFixIt == null) {
            clang_getDiagnosticFixIt = LibclangSymbols.toMethodHandle("clang_getDiagnosticFixIt", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDiagnosticFixIt"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticFixIt.invoke(allocator, Diagnostic, FixIt, ReplacementRange);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getDiagnosticFixIt$CXString(SegmentAllocator allocator,CXDiagnostic Diagnostic,int FixIt,Pointer<CXSourceRange> ReplacementRange) {
        return new CXString(FunctionUtils.makePointer(clang_getDiagnosticFixIt(allocator,Diagnostic.value(),FixIt,ReplacementRange.pointer())));
    }

    private static MethodHandle clang_createIndex;

    private static MemorySegment clang_createIndex(int excludeDeclarationsFromPCH, int displayDiagnostics) {
        if (clang_createIndex == null) {
            clang_createIndex = LibclangSymbols.toMethodHandle("clang_createIndex", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_createIndex"));
        }
        try {
            return (MemorySegment) clang_createIndex.invoke(excludeDeclarationsFromPCH, displayDiagnostics);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXIndex clang_createIndex$CXIndex(int excludeDeclarationsFromPCH, int displayDiagnostics) {
        return new CXIndex(clang_createIndex(excludeDeclarationsFromPCH, displayDiagnostics));
    }

    private static MethodHandle clang_disposeIndex;

    private static void clang_disposeIndex(MemorySegment index) {
        if (clang_disposeIndex == null) {
            clang_disposeIndex = LibclangSymbols.toMethodHandle("clang_disposeIndex", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_disposeIndex"));
        }
        try {
            clang_disposeIndex.invoke(index);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_disposeIndex(CXIndex index) {
        clang_disposeIndex(index.value());
    }

    private static MethodHandle clang_CXIndex_setGlobalOptions;

    private static void clang_CXIndex_setGlobalOptions(MemorySegment para0, int options) {
        if (clang_CXIndex_setGlobalOptions == null) {
            clang_CXIndex_setGlobalOptions = LibclangSymbols.toMethodHandle("clang_CXIndex_setGlobalOptions", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXIndex_setGlobalOptions"));
        }
        try {
            clang_CXIndex_setGlobalOptions.invoke(para0, options);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_CXIndex_setGlobalOptions(CXIndex para0, int options) {
        clang_CXIndex_setGlobalOptions(para0.value(), options);
    }

    private static MethodHandle clang_CXIndex_getGlobalOptions;

    private static int clang_CXIndex_getGlobalOptions(MemorySegment para0) {
        if (clang_CXIndex_getGlobalOptions == null) {
            clang_CXIndex_getGlobalOptions = LibclangSymbols.toMethodHandle("clang_CXIndex_getGlobalOptions", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXIndex_getGlobalOptions"));
        }
        try {
            return (int) clang_CXIndex_getGlobalOptions.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXIndex_getGlobalOptions$int(CXIndex para0) {
        return clang_CXIndex_getGlobalOptions(para0.value());
    }

    private static MethodHandle clang_CXIndex_setInvocationEmissionPathOption;

    private static void clang_CXIndex_setInvocationEmissionPathOption(MemorySegment para0, MemorySegment Path) {
        if (clang_CXIndex_setInvocationEmissionPathOption == null) {
            clang_CXIndex_setInvocationEmissionPathOption = LibclangSymbols.toMethodHandle("clang_CXIndex_setInvocationEmissionPathOption", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXIndex_setInvocationEmissionPathOption"));
        }
        try {
            clang_CXIndex_setInvocationEmissionPathOption.invoke(para0, Path);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_CXIndex_setInvocationEmissionPathOption(CXIndex para0, Pointer<VI8<Byte>> Path) {
        clang_CXIndex_setInvocationEmissionPathOption(para0.value(), Path.pointer());
    }

    private static MethodHandle clang_isFileMultipleIncludeGuarded;

    private static int clang_isFileMultipleIncludeGuarded(MemorySegment tu, MemorySegment file) {
        if (clang_isFileMultipleIncludeGuarded == null) {
            clang_isFileMultipleIncludeGuarded = LibclangSymbols.toMethodHandle("clang_isFileMultipleIncludeGuarded", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isFileMultipleIncludeGuarded"));
        }
        try {
            return (int) clang_isFileMultipleIncludeGuarded.invoke(tu, file);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isFileMultipleIncludeGuarded$int(CXTranslationUnit tu, CXFile file) {
        return clang_isFileMultipleIncludeGuarded(tu.value(), file.value());
    }

    private static MethodHandle clang_getFile;

    private static MemorySegment clang_getFile(MemorySegment tu, MemorySegment file_name) {
        if (clang_getFile == null) {
            clang_getFile = LibclangSymbols.toMethodHandle("clang_getFile", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getFile"));
        }
        try {
            return (MemorySegment) clang_getFile.invoke(tu, file_name);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXFile clang_getFile$CXFile(CXTranslationUnit tu, Pointer<VI8<Byte>> file_name) {
        return new CXFile(clang_getFile(tu.value(), file_name.pointer()));
    }

    private static MethodHandle clang_getFileContents;

    private static MemorySegment clang_getFileContents(MemorySegment tu, MemorySegment file, MemorySegment size) {
        if (clang_getFileContents == null) {
            clang_getFileContents = LibclangSymbols.toMethodHandle("clang_getFileContents", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getFileContents"));
        }
        try {
            return (MemorySegment) clang_getFileContents.invoke(tu, file, size);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<VI8<Byte>> clang_getFileContents$Pointer(CXTranslationUnit tu, CXFile file, Pointer<size_t> size) {
        return FunctionUtils.makePointer(clang_getFileContents(tu.value(), file.value(), size.pointer()));
    }

    private static MethodHandle clang_getLocation;

    private static MemorySegment clang_getLocation(SegmentAllocator allocator, MemorySegment tu, MemorySegment file, int line, int column) {
        if (clang_getLocation == null) {
            clang_getLocation = LibclangSymbols.toMethodHandle("clang_getLocation", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getLocation"));
        }
        try {
            return (MemorySegment) clang_getLocation.invoke(allocator, tu, file, line, column);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceLocation clang_getLocation$CXSourceLocation(SegmentAllocator allocator,CXTranslationUnit tu,CXFile file,int line,int column) {
        return new CXSourceLocation(FunctionUtils.makePointer(clang_getLocation(allocator,tu.value(),file.value(),line,column)));
    }

    private static MethodHandle clang_getLocationForOffset;

    private static MemorySegment clang_getLocationForOffset(SegmentAllocator allocator, MemorySegment tu, MemorySegment file, int offset) {
        if (clang_getLocationForOffset == null) {
            clang_getLocationForOffset = LibclangSymbols.toMethodHandle("clang_getLocationForOffset", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getLocationForOffset"));
        }
        try {
            return (MemorySegment) clang_getLocationForOffset.invoke(allocator, tu, file, offset);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceLocation clang_getLocationForOffset$CXSourceLocation(SegmentAllocator allocator,CXTranslationUnit tu,CXFile file,int offset) {
        return new CXSourceLocation(FunctionUtils.makePointer(clang_getLocationForOffset(allocator,tu.value(),file.value(),offset)));
    }

    private static MethodHandle clang_getSkippedRanges;

    private static MemorySegment clang_getSkippedRanges(MemorySegment tu, MemorySegment file) {
        if (clang_getSkippedRanges == null) {
            clang_getSkippedRanges = LibclangSymbols.toMethodHandle("clang_getSkippedRanges", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getSkippedRanges"));
        }
        try {
            return (MemorySegment) clang_getSkippedRanges.invoke(tu, file);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<CXSourceRangeList> clang_getSkippedRanges$Pointer(CXTranslationUnit tu, CXFile file) {
        return FunctionUtils.makePointer(clang_getSkippedRanges(tu.value(), file.value()));
    }

    private static MethodHandle clang_getAllSkippedRanges;

    private static MemorySegment clang_getAllSkippedRanges(MemorySegment tu) {
        if (clang_getAllSkippedRanges == null) {
            clang_getAllSkippedRanges = LibclangSymbols.toMethodHandle("clang_getAllSkippedRanges", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getAllSkippedRanges"));
        }
        try {
            return (MemorySegment) clang_getAllSkippedRanges.invoke(tu);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<CXSourceRangeList> clang_getAllSkippedRanges$Pointer(CXTranslationUnit tu) {
        return FunctionUtils.makePointer(clang_getAllSkippedRanges(tu.value()));
    }

    private static MethodHandle clang_getNumDiagnostics;

    private static int clang_getNumDiagnostics(MemorySegment Unit) {
        if (clang_getNumDiagnostics == null) {
            clang_getNumDiagnostics = LibclangSymbols.toMethodHandle("clang_getNumDiagnostics", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getNumDiagnostics"));
        }
        try {
            return (int) clang_getNumDiagnostics.invoke(Unit);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getNumDiagnostics$int(CXTranslationUnit Unit) {
        return clang_getNumDiagnostics(Unit.value());
    }

    private static MethodHandle clang_getDiagnostic;

    private static MemorySegment clang_getDiagnostic(MemorySegment Unit, int Index) {
        if (clang_getDiagnostic == null) {
            clang_getDiagnostic = LibclangSymbols.toMethodHandle("clang_getDiagnostic", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDiagnostic"));
        }
        try {
            return (MemorySegment) clang_getDiagnostic.invoke(Unit, Index);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXDiagnostic clang_getDiagnostic$CXDiagnostic(CXTranslationUnit Unit, int Index) {
        return new CXDiagnostic(clang_getDiagnostic(Unit.value(), Index));
    }

    private static MethodHandle clang_getDiagnosticSetFromTU;

    private static MemorySegment clang_getDiagnosticSetFromTU(MemorySegment Unit) {
        if (clang_getDiagnosticSetFromTU == null) {
            clang_getDiagnosticSetFromTU = LibclangSymbols.toMethodHandle("clang_getDiagnosticSetFromTU", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDiagnosticSetFromTU"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticSetFromTU.invoke(Unit);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXDiagnosticSet clang_getDiagnosticSetFromTU$CXDiagnosticSet(CXTranslationUnit Unit) {
        return new CXDiagnosticSet(clang_getDiagnosticSetFromTU(Unit.value()));
    }

    private static MethodHandle clang_getTranslationUnitSpelling;

    private static MemorySegment clang_getTranslationUnitSpelling(SegmentAllocator allocator, MemorySegment CTUnit) {
        if (clang_getTranslationUnitSpelling == null) {
            clang_getTranslationUnitSpelling = LibclangSymbols.toMethodHandle("clang_getTranslationUnitSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getTranslationUnitSpelling"));
        }
        try {
            return (MemorySegment) clang_getTranslationUnitSpelling.invoke(allocator, CTUnit);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getTranslationUnitSpelling$CXString(SegmentAllocator allocator,CXTranslationUnit CTUnit) {
        return new CXString(FunctionUtils.makePointer(clang_getTranslationUnitSpelling(allocator,CTUnit.value())));
    }

    private static MethodHandle clang_createTranslationUnitFromSourceFile;

    private static MemorySegment clang_createTranslationUnitFromSourceFile(MemorySegment CIdx, MemorySegment source_filename, int num_clang_command_line_args, MemorySegment clang_command_line_args, int num_unsaved_files, MemorySegment unsaved_files) {
        if (clang_createTranslationUnitFromSourceFile == null) {
            clang_createTranslationUnitFromSourceFile = LibclangSymbols.toMethodHandle("clang_createTranslationUnitFromSourceFile", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_createTranslationUnitFromSourceFile"));
        }
        try {
            return (MemorySegment) clang_createTranslationUnitFromSourceFile.invoke(CIdx, source_filename, num_clang_command_line_args, clang_command_line_args, num_unsaved_files, unsaved_files);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXTranslationUnit clang_createTranslationUnitFromSourceFile$CXTranslationUnit(CXIndex CIdx, Pointer<VI8<Byte>> source_filename, int num_clang_command_line_args, Pointer<Pointer<VI8<Byte>>> clang_command_line_args, int num_unsaved_files, Pointer<CXUnsavedFile> unsaved_files) {
        return new CXTranslationUnit(clang_createTranslationUnitFromSourceFile(CIdx.value(), source_filename.pointer(), num_clang_command_line_args, clang_command_line_args.pointer(), num_unsaved_files, unsaved_files.pointer()));
    }

    private static MethodHandle clang_createTranslationUnit;

    private static MemorySegment clang_createTranslationUnit(MemorySegment CIdx, MemorySegment ast_filename) {
        if (clang_createTranslationUnit == null) {
            clang_createTranslationUnit = LibclangSymbols.toMethodHandle("clang_createTranslationUnit", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_createTranslationUnit"));
        }
        try {
            return (MemorySegment) clang_createTranslationUnit.invoke(CIdx, ast_filename);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXTranslationUnit clang_createTranslationUnit$CXTranslationUnit(CXIndex CIdx, Pointer<VI8<Byte>> ast_filename) {
        return new CXTranslationUnit(clang_createTranslationUnit(CIdx.value(), ast_filename.pointer()));
    }

    private static MethodHandle clang_createTranslationUnit2;

    private static int clang_createTranslationUnit2(MemorySegment CIdx, MemorySegment ast_filename, MemorySegment out_TU) {
        if (clang_createTranslationUnit2 == null) {
            clang_createTranslationUnit2 = LibclangSymbols.toMethodHandle("clang_createTranslationUnit2", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_createTranslationUnit2"));
        }
        try {
            return (int) clang_createTranslationUnit2.invoke(CIdx, ast_filename, out_TU);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXErrorCode clang_createTranslationUnit2$CXErrorCode(CXIndex CIdx, Pointer<VI8<Byte>> ast_filename, Pointer<CXTranslationUnit> out_TU) {
        return new CXErrorCode(clang_createTranslationUnit2(CIdx.value(), ast_filename.pointer(), out_TU.pointer()));
    }

    private static MethodHandle clang_defaultEditingTranslationUnitOptions;

    private static int clang_defaultEditingTranslationUnitOptions() {
        if (clang_defaultEditingTranslationUnitOptions == null) {
            clang_defaultEditingTranslationUnitOptions = LibclangSymbols.toMethodHandle("clang_defaultEditingTranslationUnitOptions", FunctionDescriptor.of(ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_defaultEditingTranslationUnitOptions"));
        }
        try {
            return (int) clang_defaultEditingTranslationUnitOptions.invoke();
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_defaultEditingTranslationUnitOptions$int() {
        return clang_defaultEditingTranslationUnitOptions();
    }

    private static MethodHandle clang_parseTranslationUnit;

    private static MemorySegment clang_parseTranslationUnit(MemorySegment CIdx, MemorySegment source_filename, MemorySegment command_line_args, int num_command_line_args, MemorySegment unsaved_files, int num_unsaved_files, int options) {
        if (clang_parseTranslationUnit == null) {
            clang_parseTranslationUnit = LibclangSymbols.toMethodHandle("clang_parseTranslationUnit", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_parseTranslationUnit"));
        }
        try {
            return (MemorySegment) clang_parseTranslationUnit.invoke(CIdx, source_filename, command_line_args, num_command_line_args, unsaved_files, num_unsaved_files, options);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXTranslationUnit clang_parseTranslationUnit$CXTranslationUnit(CXIndex CIdx, Pointer<VI8<Byte>> source_filename, Pointer<Pointer<VI8<Byte>>> command_line_args, int num_command_line_args, Pointer<CXUnsavedFile> unsaved_files, int num_unsaved_files, int options) {
        return new CXTranslationUnit(clang_parseTranslationUnit(CIdx.value(), source_filename.pointer(), command_line_args.pointer(), num_command_line_args, unsaved_files.pointer(), num_unsaved_files, options));
    }

    private static MethodHandle clang_parseTranslationUnit2;

    private static int clang_parseTranslationUnit2(MemorySegment CIdx, MemorySegment source_filename, MemorySegment command_line_args, int num_command_line_args, MemorySegment unsaved_files, int num_unsaved_files, int options, MemorySegment out_TU) {
        if (clang_parseTranslationUnit2 == null) {
            clang_parseTranslationUnit2 = LibclangSymbols.toMethodHandle("clang_parseTranslationUnit2", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_parseTranslationUnit2"));
        }
        try {
            return (int) clang_parseTranslationUnit2.invoke(CIdx, source_filename, command_line_args, num_command_line_args, unsaved_files, num_unsaved_files, options, out_TU);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXErrorCode clang_parseTranslationUnit2$CXErrorCode(CXIndex CIdx, Pointer<VI8<Byte>> source_filename, Pointer<Pointer<VI8<Byte>>> command_line_args, int num_command_line_args, Pointer<CXUnsavedFile> unsaved_files, int num_unsaved_files, int options, Pointer<CXTranslationUnit> out_TU) {
        return new CXErrorCode(clang_parseTranslationUnit2(CIdx.value(), source_filename.pointer(), command_line_args.pointer(), num_command_line_args, unsaved_files.pointer(), num_unsaved_files, options, out_TU.pointer()));
    }

    private static MethodHandle clang_parseTranslationUnit2FullArgv;

    private static int clang_parseTranslationUnit2FullArgv(MemorySegment CIdx, MemorySegment source_filename, MemorySegment command_line_args, int num_command_line_args, MemorySegment unsaved_files, int num_unsaved_files, int options, MemorySegment out_TU) {
        if (clang_parseTranslationUnit2FullArgv == null) {
            clang_parseTranslationUnit2FullArgv = LibclangSymbols.toMethodHandle("clang_parseTranslationUnit2FullArgv", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_parseTranslationUnit2FullArgv"));
        }
        try {
            return (int) clang_parseTranslationUnit2FullArgv.invoke(CIdx, source_filename, command_line_args, num_command_line_args, unsaved_files, num_unsaved_files, options, out_TU);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXErrorCode clang_parseTranslationUnit2FullArgv$CXErrorCode(CXIndex CIdx, Pointer<VI8<Byte>> source_filename, Pointer<Pointer<VI8<Byte>>> command_line_args, int num_command_line_args, Pointer<CXUnsavedFile> unsaved_files, int num_unsaved_files, int options, Pointer<CXTranslationUnit> out_TU) {
        return new CXErrorCode(clang_parseTranslationUnit2FullArgv(CIdx.value(), source_filename.pointer(), command_line_args.pointer(), num_command_line_args, unsaved_files.pointer(), num_unsaved_files, options, out_TU.pointer()));
    }

    private static MethodHandle clang_defaultSaveOptions;

    private static int clang_defaultSaveOptions(MemorySegment TU) {
        if (clang_defaultSaveOptions == null) {
            clang_defaultSaveOptions = LibclangSymbols.toMethodHandle("clang_defaultSaveOptions", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_defaultSaveOptions"));
        }
        try {
            return (int) clang_defaultSaveOptions.invoke(TU);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_defaultSaveOptions$int(CXTranslationUnit TU) {
        return clang_defaultSaveOptions(TU.value());
    }

    private static MethodHandle clang_saveTranslationUnit;

    private static int clang_saveTranslationUnit(MemorySegment TU, MemorySegment FileName, int options) {
        if (clang_saveTranslationUnit == null) {
            clang_saveTranslationUnit = LibclangSymbols.toMethodHandle("clang_saveTranslationUnit", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_saveTranslationUnit"));
        }
        try {
            return (int) clang_saveTranslationUnit.invoke(TU, FileName, options);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_saveTranslationUnit$int(CXTranslationUnit TU, Pointer<VI8<Byte>> FileName, int options) {
        return clang_saveTranslationUnit(TU.value(), FileName.pointer(), options);
    }

    private static MethodHandle clang_suspendTranslationUnit;

    private static int clang_suspendTranslationUnit(MemorySegment para0) {
        if (clang_suspendTranslationUnit == null) {
            clang_suspendTranslationUnit = LibclangSymbols.toMethodHandle("clang_suspendTranslationUnit", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_suspendTranslationUnit"));
        }
        try {
            return (int) clang_suspendTranslationUnit.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_suspendTranslationUnit$int(CXTranslationUnit para0) {
        return clang_suspendTranslationUnit(para0.value());
    }

    private static MethodHandle clang_disposeTranslationUnit;

    private static void clang_disposeTranslationUnit(MemorySegment para0) {
        if (clang_disposeTranslationUnit == null) {
            clang_disposeTranslationUnit = LibclangSymbols.toMethodHandle("clang_disposeTranslationUnit", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_disposeTranslationUnit"));
        }
        try {
            clang_disposeTranslationUnit.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_disposeTranslationUnit(CXTranslationUnit para0) {
        clang_disposeTranslationUnit(para0.value());
    }

    private static MethodHandle clang_defaultReparseOptions;

    private static int clang_defaultReparseOptions(MemorySegment TU) {
        if (clang_defaultReparseOptions == null) {
            clang_defaultReparseOptions = LibclangSymbols.toMethodHandle("clang_defaultReparseOptions", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_defaultReparseOptions"));
        }
        try {
            return (int) clang_defaultReparseOptions.invoke(TU);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_defaultReparseOptions$int(CXTranslationUnit TU) {
        return clang_defaultReparseOptions(TU.value());
    }

    private static MethodHandle clang_reparseTranslationUnit;

    private static int clang_reparseTranslationUnit(MemorySegment TU, int num_unsaved_files, MemorySegment unsaved_files, int options) {
        if (clang_reparseTranslationUnit == null) {
            clang_reparseTranslationUnit = LibclangSymbols.toMethodHandle("clang_reparseTranslationUnit", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_reparseTranslationUnit"));
        }
        try {
            return (int) clang_reparseTranslationUnit.invoke(TU, num_unsaved_files, unsaved_files, options);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_reparseTranslationUnit$int(CXTranslationUnit TU, int num_unsaved_files, Pointer<CXUnsavedFile> unsaved_files, int options) {
        return clang_reparseTranslationUnit(TU.value(), num_unsaved_files, unsaved_files.pointer(), options);
    }

    private static MethodHandle clang_getTUResourceUsageName;

    private static MemorySegment clang_getTUResourceUsageName(int kind) {
        if (clang_getTUResourceUsageName == null) {
            clang_getTUResourceUsageName = LibclangSymbols.toMethodHandle("clang_getTUResourceUsageName", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getTUResourceUsageName"));
        }
        try {
            return (MemorySegment) clang_getTUResourceUsageName.invoke(kind);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<VI8<Byte>> clang_getTUResourceUsageName$Pointer(CXTUResourceUsageKind kind) {
        return FunctionUtils.makePointer(clang_getTUResourceUsageName(kind.value()));
    }

    private static MethodHandle clang_getCXTUResourceUsage;

    private static MemorySegment clang_getCXTUResourceUsage(SegmentAllocator allocator, MemorySegment TU) {
        if (clang_getCXTUResourceUsage == null) {
            clang_getCXTUResourceUsage = LibclangSymbols.toMethodHandle("clang_getCXTUResourceUsage", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCXTUResourceUsage"));
        }
        try {
            return (MemorySegment) clang_getCXTUResourceUsage.invoke(allocator, TU);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXTUResourceUsage clang_getCXTUResourceUsage$CXTUResourceUsage(SegmentAllocator allocator,CXTranslationUnit TU) {
        return new CXTUResourceUsage(FunctionUtils.makePointer(clang_getCXTUResourceUsage(allocator,TU.value())));
    }

    private static MethodHandle clang_disposeCXTUResourceUsage;

    private static void clang_disposeCXTUResourceUsage(MemorySegment usage) {
        if (clang_disposeCXTUResourceUsage == null) {
            clang_disposeCXTUResourceUsage = LibclangSymbols.toMethodHandle("clang_disposeCXTUResourceUsage", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_disposeCXTUResourceUsage"));
        }
        try {
            clang_disposeCXTUResourceUsage.invoke(usage);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_disposeCXTUResourceUsage(CXTUResourceUsage usage) {
        clang_disposeCXTUResourceUsage(usage.pointer());
    }

    private static MethodHandle clang_getTranslationUnitTargetInfo;

    private static MemorySegment clang_getTranslationUnitTargetInfo(MemorySegment CTUnit) {
        if (clang_getTranslationUnitTargetInfo == null) {
            clang_getTranslationUnitTargetInfo = LibclangSymbols.toMethodHandle("clang_getTranslationUnitTargetInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getTranslationUnitTargetInfo"));
        }
        try {
            return (MemorySegment) clang_getTranslationUnitTargetInfo.invoke(CTUnit);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXTargetInfo clang_getTranslationUnitTargetInfo$CXTargetInfo(CXTranslationUnit CTUnit) {
        return new CXTargetInfo(clang_getTranslationUnitTargetInfo(CTUnit.value()));
    }

    private static MethodHandle clang_TargetInfo_dispose;

    private static void clang_TargetInfo_dispose(MemorySegment Info) {
        if (clang_TargetInfo_dispose == null) {
            clang_TargetInfo_dispose = LibclangSymbols.toMethodHandle("clang_TargetInfo_dispose", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_TargetInfo_dispose"));
        }
        try {
            clang_TargetInfo_dispose.invoke(Info);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_TargetInfo_dispose(CXTargetInfo Info) {
        clang_TargetInfo_dispose(Info.value());
    }

    private static MethodHandle clang_TargetInfo_getTriple;

    private static MemorySegment clang_TargetInfo_getTriple(SegmentAllocator allocator, MemorySegment Info) {
        if (clang_TargetInfo_getTriple == null) {
            clang_TargetInfo_getTriple = LibclangSymbols.toMethodHandle("clang_TargetInfo_getTriple", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_TargetInfo_getTriple"));
        }
        try {
            return (MemorySegment) clang_TargetInfo_getTriple.invoke(allocator, Info);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_TargetInfo_getTriple$CXString(SegmentAllocator allocator,CXTargetInfo Info) {
        return new CXString(FunctionUtils.makePointer(clang_TargetInfo_getTriple(allocator,Info.value())));
    }

    private static MethodHandle clang_TargetInfo_getPointerWidth;

    private static int clang_TargetInfo_getPointerWidth(MemorySegment Info) {
        if (clang_TargetInfo_getPointerWidth == null) {
            clang_TargetInfo_getPointerWidth = LibclangSymbols.toMethodHandle("clang_TargetInfo_getPointerWidth", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_TargetInfo_getPointerWidth"));
        }
        try {
            return (int) clang_TargetInfo_getPointerWidth.invoke(Info);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_TargetInfo_getPointerWidth$int(CXTargetInfo Info) {
        return clang_TargetInfo_getPointerWidth(Info.value());
    }

    private static MethodHandle clang_getNullCursor;

    private static MemorySegment clang_getNullCursor(SegmentAllocator allocator) {
        if (clang_getNullCursor == null) {
            clang_getNullCursor = LibclangSymbols.toMethodHandle("clang_getNullCursor", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getNullCursor"));
        }
        try {
            return (MemorySegment) clang_getNullCursor.invoke(allocator);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursor clang_getNullCursor$CXCursor(SegmentAllocator allocator) {
        return new CXCursor(FunctionUtils.makePointer(clang_getNullCursor(allocator)));
    }

    private static MethodHandle clang_getTranslationUnitCursor;

    private static MemorySegment clang_getTranslationUnitCursor(SegmentAllocator allocator, MemorySegment para0) {
        if (clang_getTranslationUnitCursor == null) {
            clang_getTranslationUnitCursor = LibclangSymbols.toMethodHandle("clang_getTranslationUnitCursor", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getTranslationUnitCursor"));
        }
        try {
            return (MemorySegment) clang_getTranslationUnitCursor.invoke(allocator, para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursor clang_getTranslationUnitCursor$CXCursor(SegmentAllocator allocator,CXTranslationUnit para0) {
        return new CXCursor(FunctionUtils.makePointer(clang_getTranslationUnitCursor(allocator,para0.value())));
    }

    private static MethodHandle clang_equalCursors;

    private static int clang_equalCursors(MemorySegment para0, MemorySegment para1) {
        if (clang_equalCursors == null) {
            clang_equalCursors = LibclangSymbols.toMethodHandle("clang_equalCursors", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_equalCursors"));
        }
        try {
            return (int) clang_equalCursors.invoke(para0, para1);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_equalCursors$int(CXCursor para0, CXCursor para1) {
        return clang_equalCursors(para0.pointer(), para1.pointer());
    }

    private static MethodHandle clang_Cursor_isNull;

    private static int clang_Cursor_isNull(MemorySegment cursor) {
        if (clang_Cursor_isNull == null) {
            clang_Cursor_isNull = LibclangSymbols.toMethodHandle("clang_Cursor_isNull", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_isNull"));
        }
        try {
            return (int) clang_Cursor_isNull.invoke(cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_isNull$int(CXCursor cursor) {
        return clang_Cursor_isNull(cursor.pointer());
    }

    private static MethodHandle clang_hashCursor;

    private static int clang_hashCursor(MemorySegment para0) {
        if (clang_hashCursor == null) {
            clang_hashCursor = LibclangSymbols.toMethodHandle("clang_hashCursor", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_hashCursor"));
        }
        try {
            return (int) clang_hashCursor.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_hashCursor$int(CXCursor para0) {
        return clang_hashCursor(para0.pointer());
    }

    private static MethodHandle clang_getCursorKind;

    private static int clang_getCursorKind(MemorySegment para0) {
        if (clang_getCursorKind == null) {
            clang_getCursorKind = LibclangSymbols.toMethodHandle("clang_getCursorKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorKind"));
        }
        try {
            return (int) clang_getCursorKind.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursorKind clang_getCursorKind$CXCursorKind(CXCursor para0) {
        return new CXCursorKind(clang_getCursorKind(para0.pointer()));
    }

    private static MethodHandle clang_isDeclaration;

    private static int clang_isDeclaration(int para0) {
        if (clang_isDeclaration == null) {
            clang_isDeclaration = LibclangSymbols.toMethodHandle("clang_isDeclaration", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isDeclaration"));
        }
        try {
            return (int) clang_isDeclaration.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isDeclaration$int(CXCursorKind para0) {
        return clang_isDeclaration(para0.value());
    }

    private static MethodHandle clang_isInvalidDeclaration;

    private static int clang_isInvalidDeclaration(MemorySegment para0) {
        if (clang_isInvalidDeclaration == null) {
            clang_isInvalidDeclaration = LibclangSymbols.toMethodHandle("clang_isInvalidDeclaration", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isInvalidDeclaration"));
        }
        try {
            return (int) clang_isInvalidDeclaration.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isInvalidDeclaration$int(CXCursor para0) {
        return clang_isInvalidDeclaration(para0.pointer());
    }

    private static MethodHandle clang_isReference;

    private static int clang_isReference(int para0) {
        if (clang_isReference == null) {
            clang_isReference = LibclangSymbols.toMethodHandle("clang_isReference", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isReference"));
        }
        try {
            return (int) clang_isReference.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isReference$int(CXCursorKind para0) {
        return clang_isReference(para0.value());
    }

    private static MethodHandle clang_isExpression;

    private static int clang_isExpression(int para0) {
        if (clang_isExpression == null) {
            clang_isExpression = LibclangSymbols.toMethodHandle("clang_isExpression", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isExpression"));
        }
        try {
            return (int) clang_isExpression.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isExpression$int(CXCursorKind para0) {
        return clang_isExpression(para0.value());
    }

    private static MethodHandle clang_isStatement;

    private static int clang_isStatement(int para0) {
        if (clang_isStatement == null) {
            clang_isStatement = LibclangSymbols.toMethodHandle("clang_isStatement", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isStatement"));
        }
        try {
            return (int) clang_isStatement.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isStatement$int(CXCursorKind para0) {
        return clang_isStatement(para0.value());
    }

    private static MethodHandle clang_isAttribute;

    private static int clang_isAttribute(int para0) {
        if (clang_isAttribute == null) {
            clang_isAttribute = LibclangSymbols.toMethodHandle("clang_isAttribute", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isAttribute"));
        }
        try {
            return (int) clang_isAttribute.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isAttribute$int(CXCursorKind para0) {
        return clang_isAttribute(para0.value());
    }

    private static MethodHandle clang_Cursor_hasAttrs;

    private static int clang_Cursor_hasAttrs(MemorySegment C) {
        if (clang_Cursor_hasAttrs == null) {
            clang_Cursor_hasAttrs = LibclangSymbols.toMethodHandle("clang_Cursor_hasAttrs", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_hasAttrs"));
        }
        try {
            return (int) clang_Cursor_hasAttrs.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_hasAttrs$int(CXCursor C) {
        return clang_Cursor_hasAttrs(C.pointer());
    }

    private static MethodHandle clang_isInvalid;

    private static int clang_isInvalid(int para0) {
        if (clang_isInvalid == null) {
            clang_isInvalid = LibclangSymbols.toMethodHandle("clang_isInvalid", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isInvalid"));
        }
        try {
            return (int) clang_isInvalid.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isInvalid$int(CXCursorKind para0) {
        return clang_isInvalid(para0.value());
    }

    private static MethodHandle clang_isTranslationUnit;

    private static int clang_isTranslationUnit(int para0) {
        if (clang_isTranslationUnit == null) {
            clang_isTranslationUnit = LibclangSymbols.toMethodHandle("clang_isTranslationUnit", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isTranslationUnit"));
        }
        try {
            return (int) clang_isTranslationUnit.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isTranslationUnit$int(CXCursorKind para0) {
        return clang_isTranslationUnit(para0.value());
    }

    private static MethodHandle clang_isPreprocessing;

    private static int clang_isPreprocessing(int para0) {
        if (clang_isPreprocessing == null) {
            clang_isPreprocessing = LibclangSymbols.toMethodHandle("clang_isPreprocessing", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isPreprocessing"));
        }
        try {
            return (int) clang_isPreprocessing.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isPreprocessing$int(CXCursorKind para0) {
        return clang_isPreprocessing(para0.value());
    }

    private static MethodHandle clang_isUnexposed;

    private static int clang_isUnexposed(int para0) {
        if (clang_isUnexposed == null) {
            clang_isUnexposed = LibclangSymbols.toMethodHandle("clang_isUnexposed", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isUnexposed"));
        }
        try {
            return (int) clang_isUnexposed.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isUnexposed$int(CXCursorKind para0) {
        return clang_isUnexposed(para0.value());
    }

    private static MethodHandle clang_getCursorLinkage;

    private static int clang_getCursorLinkage(MemorySegment cursor) {
        if (clang_getCursorLinkage == null) {
            clang_getCursorLinkage = LibclangSymbols.toMethodHandle("clang_getCursorLinkage", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorLinkage"));
        }
        try {
            return (int) clang_getCursorLinkage.invoke(cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXLinkageKind clang_getCursorLinkage$CXLinkageKind(CXCursor cursor) {
        return new CXLinkageKind(clang_getCursorLinkage(cursor.pointer()));
    }

    private static MethodHandle clang_getCursorVisibility;

    private static int clang_getCursorVisibility(MemorySegment cursor) {
        if (clang_getCursorVisibility == null) {
            clang_getCursorVisibility = LibclangSymbols.toMethodHandle("clang_getCursorVisibility", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorVisibility"));
        }
        try {
            return (int) clang_getCursorVisibility.invoke(cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXVisibilityKind clang_getCursorVisibility$CXVisibilityKind(CXCursor cursor) {
        return new CXVisibilityKind(clang_getCursorVisibility(cursor.pointer()));
    }

    private static MethodHandle clang_getCursorAvailability;

    private static int clang_getCursorAvailability(MemorySegment cursor) {
        if (clang_getCursorAvailability == null) {
            clang_getCursorAvailability = LibclangSymbols.toMethodHandle("clang_getCursorAvailability", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorAvailability"));
        }
        try {
            return (int) clang_getCursorAvailability.invoke(cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXAvailabilityKind clang_getCursorAvailability$CXAvailabilityKind(CXCursor cursor) {
        return new CXAvailabilityKind(clang_getCursorAvailability(cursor.pointer()));
    }

    private static MethodHandle clang_getCursorPlatformAvailability;

    private static int clang_getCursorPlatformAvailability(MemorySegment cursor, MemorySegment always_deprecated, MemorySegment deprecated_message, MemorySegment always_unavailable, MemorySegment unavailable_message, MemorySegment availability, int availability_size) {
        if (clang_getCursorPlatformAvailability == null) {
            clang_getCursorPlatformAvailability = LibclangSymbols.toMethodHandle("clang_getCursorPlatformAvailability", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorPlatformAvailability"));
        }
        try {
            return (int) clang_getCursorPlatformAvailability.invoke(cursor, always_deprecated, deprecated_message, always_unavailable, unavailable_message, availability, availability_size);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getCursorPlatformAvailability$int(CXCursor cursor, Pointer<VI32<Integer>> always_deprecated, Pointer<CXString> deprecated_message, Pointer<VI32<Integer>> always_unavailable, Pointer<CXString> unavailable_message, Pointer<CXPlatformAvailability> availability, int availability_size) {
        return clang_getCursorPlatformAvailability(cursor.pointer(), always_deprecated.pointer(), deprecated_message.pointer(), always_unavailable.pointer(), unavailable_message.pointer(), availability.pointer(), availability_size);
    }

    private static MethodHandle clang_disposeCXPlatformAvailability;

    private static void clang_disposeCXPlatformAvailability(MemorySegment availability) {
        if (clang_disposeCXPlatformAvailability == null) {
            clang_disposeCXPlatformAvailability = LibclangSymbols.toMethodHandle("clang_disposeCXPlatformAvailability", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_disposeCXPlatformAvailability"));
        }
        try {
            clang_disposeCXPlatformAvailability.invoke(availability);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_disposeCXPlatformAvailability(Pointer<CXPlatformAvailability> availability) {
        clang_disposeCXPlatformAvailability(availability.pointer());
    }

    private static MethodHandle clang_Cursor_getVarDeclInitializer;

    private static MemorySegment clang_Cursor_getVarDeclInitializer(SegmentAllocator allocator, MemorySegment cursor) {
        if (clang_Cursor_getVarDeclInitializer == null) {
            clang_Cursor_getVarDeclInitializer = LibclangSymbols.toMethodHandle("clang_Cursor_getVarDeclInitializer", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getVarDeclInitializer"));
        }
        try {
            return (MemorySegment) clang_Cursor_getVarDeclInitializer.invoke(allocator, cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursor clang_Cursor_getVarDeclInitializer$CXCursor(SegmentAllocator allocator,CXCursor cursor) {
        return new CXCursor(FunctionUtils.makePointer(clang_Cursor_getVarDeclInitializer(allocator,cursor.pointer())));
    }

    private static MethodHandle clang_Cursor_hasVarDeclGlobalStorage;

    private static int clang_Cursor_hasVarDeclGlobalStorage(MemorySegment cursor) {
        if (clang_Cursor_hasVarDeclGlobalStorage == null) {
            clang_Cursor_hasVarDeclGlobalStorage = LibclangSymbols.toMethodHandle("clang_Cursor_hasVarDeclGlobalStorage", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_hasVarDeclGlobalStorage"));
        }
        try {
            return (int) clang_Cursor_hasVarDeclGlobalStorage.invoke(cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_hasVarDeclGlobalStorage$int(CXCursor cursor) {
        return clang_Cursor_hasVarDeclGlobalStorage(cursor.pointer());
    }

    private static MethodHandle clang_Cursor_hasVarDeclExternalStorage;

    private static int clang_Cursor_hasVarDeclExternalStorage(MemorySegment cursor) {
        if (clang_Cursor_hasVarDeclExternalStorage == null) {
            clang_Cursor_hasVarDeclExternalStorage = LibclangSymbols.toMethodHandle("clang_Cursor_hasVarDeclExternalStorage", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_hasVarDeclExternalStorage"));
        }
        try {
            return (int) clang_Cursor_hasVarDeclExternalStorage.invoke(cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_hasVarDeclExternalStorage$int(CXCursor cursor) {
        return clang_Cursor_hasVarDeclExternalStorage(cursor.pointer());
    }

    private static MethodHandle clang_getCursorLanguage;

    private static int clang_getCursorLanguage(MemorySegment cursor) {
        if (clang_getCursorLanguage == null) {
            clang_getCursorLanguage = LibclangSymbols.toMethodHandle("clang_getCursorLanguage", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorLanguage"));
        }
        try {
            return (int) clang_getCursorLanguage.invoke(cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXLanguageKind clang_getCursorLanguage$CXLanguageKind(CXCursor cursor) {
        return new CXLanguageKind(clang_getCursorLanguage(cursor.pointer()));
    }

    private static MethodHandle clang_getCursorTLSKind;

    private static int clang_getCursorTLSKind(MemorySegment cursor) {
        if (clang_getCursorTLSKind == null) {
            clang_getCursorTLSKind = LibclangSymbols.toMethodHandle("clang_getCursorTLSKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorTLSKind"));
        }
        try {
            return (int) clang_getCursorTLSKind.invoke(cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXTLSKind clang_getCursorTLSKind$CXTLSKind(CXCursor cursor) {
        return new CXTLSKind(clang_getCursorTLSKind(cursor.pointer()));
    }

    private static MethodHandle clang_Cursor_getTranslationUnit;

    private static MemorySegment clang_Cursor_getTranslationUnit(MemorySegment para0) {
        if (clang_Cursor_getTranslationUnit == null) {
            clang_Cursor_getTranslationUnit = LibclangSymbols.toMethodHandle("clang_Cursor_getTranslationUnit", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getTranslationUnit"));
        }
        try {
            return (MemorySegment) clang_Cursor_getTranslationUnit.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXTranslationUnit clang_Cursor_getTranslationUnit$CXTranslationUnit(CXCursor para0) {
        return new CXTranslationUnit(clang_Cursor_getTranslationUnit(para0.pointer()));
    }

    private static MethodHandle clang_createCXCursorSet;

    private static MemorySegment clang_createCXCursorSet() {
        if (clang_createCXCursorSet == null) {
            clang_createCXCursorSet = LibclangSymbols.toMethodHandle("clang_createCXCursorSet", FunctionDescriptor.of(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_createCXCursorSet"));
        }
        try {
            return (MemorySegment) clang_createCXCursorSet.invoke();
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursorSet clang_createCXCursorSet$CXCursorSet() {
        return new CXCursorSet(clang_createCXCursorSet());
    }

    private static MethodHandle clang_disposeCXCursorSet;

    private static void clang_disposeCXCursorSet(MemorySegment cset) {
        if (clang_disposeCXCursorSet == null) {
            clang_disposeCXCursorSet = LibclangSymbols.toMethodHandle("clang_disposeCXCursorSet", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_disposeCXCursorSet"));
        }
        try {
            clang_disposeCXCursorSet.invoke(cset);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_disposeCXCursorSet(CXCursorSet cset) {
        clang_disposeCXCursorSet(cset.value());
    }

    private static MethodHandle clang_CXCursorSet_contains;

    private static int clang_CXCursorSet_contains(MemorySegment cset, MemorySegment cursor) {
        if (clang_CXCursorSet_contains == null) {
            clang_CXCursorSet_contains = LibclangSymbols.toMethodHandle("clang_CXCursorSet_contains", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXCursorSet_contains"));
        }
        try {
            return (int) clang_CXCursorSet_contains.invoke(cset, cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXCursorSet_contains$int(CXCursorSet cset, CXCursor cursor) {
        return clang_CXCursorSet_contains(cset.value(), cursor.pointer());
    }

    private static MethodHandle clang_CXCursorSet_insert;

    private static int clang_CXCursorSet_insert(MemorySegment cset, MemorySegment cursor) {
        if (clang_CXCursorSet_insert == null) {
            clang_CXCursorSet_insert = LibclangSymbols.toMethodHandle("clang_CXCursorSet_insert", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXCursorSet_insert"));
        }
        try {
            return (int) clang_CXCursorSet_insert.invoke(cset, cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXCursorSet_insert$int(CXCursorSet cset, CXCursor cursor) {
        return clang_CXCursorSet_insert(cset.value(), cursor.pointer());
    }

    private static MethodHandle clang_getCursorSemanticParent;

    private static MemorySegment clang_getCursorSemanticParent(SegmentAllocator allocator, MemorySegment cursor) {
        if (clang_getCursorSemanticParent == null) {
            clang_getCursorSemanticParent = LibclangSymbols.toMethodHandle("clang_getCursorSemanticParent", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorSemanticParent"));
        }
        try {
            return (MemorySegment) clang_getCursorSemanticParent.invoke(allocator, cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursor clang_getCursorSemanticParent$CXCursor(SegmentAllocator allocator,CXCursor cursor) {
        return new CXCursor(FunctionUtils.makePointer(clang_getCursorSemanticParent(allocator,cursor.pointer())));
    }

    private static MethodHandle clang_getCursorLexicalParent;

    private static MemorySegment clang_getCursorLexicalParent(SegmentAllocator allocator, MemorySegment cursor) {
        if (clang_getCursorLexicalParent == null) {
            clang_getCursorLexicalParent = LibclangSymbols.toMethodHandle("clang_getCursorLexicalParent", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorLexicalParent"));
        }
        try {
            return (MemorySegment) clang_getCursorLexicalParent.invoke(allocator, cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursor clang_getCursorLexicalParent$CXCursor(SegmentAllocator allocator,CXCursor cursor) {
        return new CXCursor(FunctionUtils.makePointer(clang_getCursorLexicalParent(allocator,cursor.pointer())));
    }

    private static MethodHandle clang_getOverriddenCursors;

    private static void clang_getOverriddenCursors(MemorySegment cursor, MemorySegment overridden, MemorySegment num_overridden) {
        if (clang_getOverriddenCursors == null) {
            clang_getOverriddenCursors = LibclangSymbols.toMethodHandle("clang_getOverriddenCursors", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getOverriddenCursors"));
        }
        try {
            clang_getOverriddenCursors.invoke(cursor, overridden, num_overridden);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_getOverriddenCursors(CXCursor cursor, Pointer<Pointer<CXCursor>> overridden, Pointer<VI32<Integer>> num_overridden) {
        clang_getOverriddenCursors(cursor.pointer(), overridden.pointer(), num_overridden.pointer());
    }

    private static MethodHandle clang_disposeOverriddenCursors;

    private static void clang_disposeOverriddenCursors(MemorySegment overridden) {
        if (clang_disposeOverriddenCursors == null) {
            clang_disposeOverriddenCursors = LibclangSymbols.toMethodHandle("clang_disposeOverriddenCursors", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_disposeOverriddenCursors"));
        }
        try {
            clang_disposeOverriddenCursors.invoke(overridden);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_disposeOverriddenCursors(Pointer<CXCursor> overridden) {
        clang_disposeOverriddenCursors(overridden.pointer());
    }

    private static MethodHandle clang_getIncludedFile;

    private static MemorySegment clang_getIncludedFile(MemorySegment cursor) {
        if (clang_getIncludedFile == null) {
            clang_getIncludedFile = LibclangSymbols.toMethodHandle("clang_getIncludedFile", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getIncludedFile"));
        }
        try {
            return (MemorySegment) clang_getIncludedFile.invoke(cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXFile clang_getIncludedFile$CXFile(CXCursor cursor) {
        return new CXFile(clang_getIncludedFile(cursor.pointer()));
    }

    private static MethodHandle clang_getCursor;

    private static MemorySegment clang_getCursor(SegmentAllocator allocator, MemorySegment para0, MemorySegment para1) {
        if (clang_getCursor == null) {
            clang_getCursor = LibclangSymbols.toMethodHandle("clang_getCursor", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursor"));
        }
        try {
            return (MemorySegment) clang_getCursor.invoke(allocator, para0, para1);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursor clang_getCursor$CXCursor(SegmentAllocator allocator,CXTranslationUnit para0,CXSourceLocation para1) {
        return new CXCursor(FunctionUtils.makePointer(clang_getCursor(allocator,para0.value(),para1.pointer())));
    }

    private static MethodHandle clang_getCursorLocation;

    private static MemorySegment clang_getCursorLocation(SegmentAllocator allocator, MemorySegment para0) {
        if (clang_getCursorLocation == null) {
            clang_getCursorLocation = LibclangSymbols.toMethodHandle("clang_getCursorLocation", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorLocation"));
        }
        try {
            return (MemorySegment) clang_getCursorLocation.invoke(allocator, para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceLocation clang_getCursorLocation$CXSourceLocation(SegmentAllocator allocator,CXCursor para0) {
        return new CXSourceLocation(FunctionUtils.makePointer(clang_getCursorLocation(allocator,para0.pointer())));
    }

    private static MethodHandle clang_getCursorExtent;

    private static MemorySegment clang_getCursorExtent(SegmentAllocator allocator, MemorySegment para0) {
        if (clang_getCursorExtent == null) {
            clang_getCursorExtent = LibclangSymbols.toMethodHandle("clang_getCursorExtent", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorExtent"));
        }
        try {
            return (MemorySegment) clang_getCursorExtent.invoke(allocator, para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceRange clang_getCursorExtent$CXSourceRange(SegmentAllocator allocator,CXCursor para0) {
        return new CXSourceRange(FunctionUtils.makePointer(clang_getCursorExtent(allocator,para0.pointer())));
    }

    private static MethodHandle clang_getCursorType;

    private static MemorySegment clang_getCursorType(SegmentAllocator allocator, MemorySegment C) {
        if (clang_getCursorType == null) {
            clang_getCursorType = LibclangSymbols.toMethodHandle("clang_getCursorType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorType"));
        }
        try {
            return (MemorySegment) clang_getCursorType.invoke(allocator, C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_getCursorType$CXType(SegmentAllocator allocator,CXCursor C) {
        return new CXType(FunctionUtils.makePointer(clang_getCursorType(allocator,C.pointer())));
    }

    private static MethodHandle clang_getTypeSpelling;

    private static MemorySegment clang_getTypeSpelling(SegmentAllocator allocator, MemorySegment CT) {
        if (clang_getTypeSpelling == null) {
            clang_getTypeSpelling = LibclangSymbols.toMethodHandle("clang_getTypeSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getTypeSpelling"));
        }
        try {
            return (MemorySegment) clang_getTypeSpelling.invoke(allocator, CT);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getTypeSpelling$CXString(SegmentAllocator allocator,CXType CT) {
        return new CXString(FunctionUtils.makePointer(clang_getTypeSpelling(allocator,CT.pointer())));
    }

    private static MethodHandle clang_getTypedefDeclUnderlyingType;

    private static MemorySegment clang_getTypedefDeclUnderlyingType(SegmentAllocator allocator, MemorySegment C) {
        if (clang_getTypedefDeclUnderlyingType == null) {
            clang_getTypedefDeclUnderlyingType = LibclangSymbols.toMethodHandle("clang_getTypedefDeclUnderlyingType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getTypedefDeclUnderlyingType"));
        }
        try {
            return (MemorySegment) clang_getTypedefDeclUnderlyingType.invoke(allocator, C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_getTypedefDeclUnderlyingType$CXType(SegmentAllocator allocator,CXCursor C) {
        return new CXType(FunctionUtils.makePointer(clang_getTypedefDeclUnderlyingType(allocator,C.pointer())));
    }

    private static MethodHandle clang_getEnumDeclIntegerType;

    private static MemorySegment clang_getEnumDeclIntegerType(SegmentAllocator allocator, MemorySegment C) {
        if (clang_getEnumDeclIntegerType == null) {
            clang_getEnumDeclIntegerType = LibclangSymbols.toMethodHandle("clang_getEnumDeclIntegerType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getEnumDeclIntegerType"));
        }
        try {
            return (MemorySegment) clang_getEnumDeclIntegerType.invoke(allocator, C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_getEnumDeclIntegerType$CXType(SegmentAllocator allocator,CXCursor C) {
        return new CXType(FunctionUtils.makePointer(clang_getEnumDeclIntegerType(allocator,C.pointer())));
    }

    private static MethodHandle clang_getEnumConstantDeclValue;

    private static long clang_getEnumConstantDeclValue(MemorySegment C) {
        if (clang_getEnumConstantDeclValue == null) {
            clang_getEnumConstantDeclValue = LibclangSymbols.toMethodHandle("clang_getEnumConstantDeclValue", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getEnumConstantDeclValue"));
        }
        try {
            return (long) clang_getEnumConstantDeclValue.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static long clang_getEnumConstantDeclValue$long(CXCursor C) {
        return clang_getEnumConstantDeclValue(C.pointer());
    }

    private static MethodHandle clang_getEnumConstantDeclUnsignedValue;

    private static long clang_getEnumConstantDeclUnsignedValue(MemorySegment C) {
        if (clang_getEnumConstantDeclUnsignedValue == null) {
            clang_getEnumConstantDeclUnsignedValue = LibclangSymbols.toMethodHandle("clang_getEnumConstantDeclUnsignedValue", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getEnumConstantDeclUnsignedValue"));
        }
        try {
            return (long) clang_getEnumConstantDeclUnsignedValue.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static long clang_getEnumConstantDeclUnsignedValue$long(CXCursor C) {
        return clang_getEnumConstantDeclUnsignedValue(C.pointer());
    }

    private static MethodHandle clang_getFieldDeclBitWidth;

    private static int clang_getFieldDeclBitWidth(MemorySegment C) {
        if (clang_getFieldDeclBitWidth == null) {
            clang_getFieldDeclBitWidth = LibclangSymbols.toMethodHandle("clang_getFieldDeclBitWidth", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getFieldDeclBitWidth"));
        }
        try {
            return (int) clang_getFieldDeclBitWidth.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getFieldDeclBitWidth$int(CXCursor C) {
        return clang_getFieldDeclBitWidth(C.pointer());
    }

    private static MethodHandle clang_Cursor_getNumArguments;

    private static int clang_Cursor_getNumArguments(MemorySegment C) {
        if (clang_Cursor_getNumArguments == null) {
            clang_Cursor_getNumArguments = LibclangSymbols.toMethodHandle("clang_Cursor_getNumArguments", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getNumArguments"));
        }
        try {
            return (int) clang_Cursor_getNumArguments.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_getNumArguments$int(CXCursor C) {
        return clang_Cursor_getNumArguments(C.pointer());
    }

    private static MethodHandle clang_Cursor_getArgument;

    private static MemorySegment clang_Cursor_getArgument(SegmentAllocator allocator, MemorySegment C, int i) {
        if (clang_Cursor_getArgument == null) {
            clang_Cursor_getArgument = LibclangSymbols.toMethodHandle("clang_Cursor_getArgument", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getArgument"));
        }
        try {
            return (MemorySegment) clang_Cursor_getArgument.invoke(allocator, C, i);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursor clang_Cursor_getArgument$CXCursor(SegmentAllocator allocator,CXCursor C,int i) {
        return new CXCursor(FunctionUtils.makePointer(clang_Cursor_getArgument(allocator,C.pointer(),i)));
    }

    private static MethodHandle clang_Cursor_getNumTemplateArguments;

    private static int clang_Cursor_getNumTemplateArguments(MemorySegment C) {
        if (clang_Cursor_getNumTemplateArguments == null) {
            clang_Cursor_getNumTemplateArguments = LibclangSymbols.toMethodHandle("clang_Cursor_getNumTemplateArguments", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getNumTemplateArguments"));
        }
        try {
            return (int) clang_Cursor_getNumTemplateArguments.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_getNumTemplateArguments$int(CXCursor C) {
        return clang_Cursor_getNumTemplateArguments(C.pointer());
    }

    private static MethodHandle clang_Cursor_getTemplateArgumentKind;

    private static int clang_Cursor_getTemplateArgumentKind(MemorySegment C, int I) {
        if (clang_Cursor_getTemplateArgumentKind == null) {
            clang_Cursor_getTemplateArgumentKind = LibclangSymbols.toMethodHandle("clang_Cursor_getTemplateArgumentKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getTemplateArgumentKind"));
        }
        try {
            return (int) clang_Cursor_getTemplateArgumentKind.invoke(C, I);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXTemplateArgumentKind clang_Cursor_getTemplateArgumentKind$CXTemplateArgumentKind(CXCursor C, int I) {
        return new CXTemplateArgumentKind(clang_Cursor_getTemplateArgumentKind(C.pointer(), I));
    }

    private static MethodHandle clang_Cursor_getTemplateArgumentType;

    private static MemorySegment clang_Cursor_getTemplateArgumentType(SegmentAllocator allocator, MemorySegment C, int I) {
        if (clang_Cursor_getTemplateArgumentType == null) {
            clang_Cursor_getTemplateArgumentType = LibclangSymbols.toMethodHandle("clang_Cursor_getTemplateArgumentType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getTemplateArgumentType"));
        }
        try {
            return (MemorySegment) clang_Cursor_getTemplateArgumentType.invoke(allocator, C, I);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_Cursor_getTemplateArgumentType$CXType(SegmentAllocator allocator,CXCursor C,int I) {
        return new CXType(FunctionUtils.makePointer(clang_Cursor_getTemplateArgumentType(allocator,C.pointer(),I)));
    }

    private static MethodHandle clang_Cursor_getTemplateArgumentValue;

    private static long clang_Cursor_getTemplateArgumentValue(MemorySegment C, int I) {
        if (clang_Cursor_getTemplateArgumentValue == null) {
            clang_Cursor_getTemplateArgumentValue = LibclangSymbols.toMethodHandle("clang_Cursor_getTemplateArgumentValue", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getTemplateArgumentValue"));
        }
        try {
            return (long) clang_Cursor_getTemplateArgumentValue.invoke(C, I);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static long clang_Cursor_getTemplateArgumentValue$long(CXCursor C, int I) {
        return clang_Cursor_getTemplateArgumentValue(C.pointer(), I);
    }

    private static MethodHandle clang_Cursor_getTemplateArgumentUnsignedValue;

    private static long clang_Cursor_getTemplateArgumentUnsignedValue(MemorySegment C, int I) {
        if (clang_Cursor_getTemplateArgumentUnsignedValue == null) {
            clang_Cursor_getTemplateArgumentUnsignedValue = LibclangSymbols.toMethodHandle("clang_Cursor_getTemplateArgumentUnsignedValue", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getTemplateArgumentUnsignedValue"));
        }
        try {
            return (long) clang_Cursor_getTemplateArgumentUnsignedValue.invoke(C, I);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static long clang_Cursor_getTemplateArgumentUnsignedValue$long(CXCursor C, int I) {
        return clang_Cursor_getTemplateArgumentUnsignedValue(C.pointer(), I);
    }

    private static MethodHandle clang_equalTypes;

    private static int clang_equalTypes(MemorySegment A, MemorySegment B) {
        if (clang_equalTypes == null) {
            clang_equalTypes = LibclangSymbols.toMethodHandle("clang_equalTypes", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_equalTypes"));
        }
        try {
            return (int) clang_equalTypes.invoke(A, B);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_equalTypes$int(CXType A, CXType B) {
        return clang_equalTypes(A.pointer(), B.pointer());
    }

    private static MethodHandle clang_getCanonicalType;

    private static MemorySegment clang_getCanonicalType(SegmentAllocator allocator, MemorySegment T) {
        if (clang_getCanonicalType == null) {
            clang_getCanonicalType = LibclangSymbols.toMethodHandle("clang_getCanonicalType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCanonicalType"));
        }
        try {
            return (MemorySegment) clang_getCanonicalType.invoke(allocator, T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_getCanonicalType$CXType(SegmentAllocator allocator,CXType T) {
        return new CXType(FunctionUtils.makePointer(clang_getCanonicalType(allocator,T.pointer())));
    }

    private static MethodHandle clang_isConstQualifiedType;

    private static int clang_isConstQualifiedType(MemorySegment T) {
        if (clang_isConstQualifiedType == null) {
            clang_isConstQualifiedType = LibclangSymbols.toMethodHandle("clang_isConstQualifiedType", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isConstQualifiedType"));
        }
        try {
            return (int) clang_isConstQualifiedType.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isConstQualifiedType$int(CXType T) {
        return clang_isConstQualifiedType(T.pointer());
    }

    private static MethodHandle clang_Cursor_isMacroFunctionLike;

    private static int clang_Cursor_isMacroFunctionLike(MemorySegment C) {
        if (clang_Cursor_isMacroFunctionLike == null) {
            clang_Cursor_isMacroFunctionLike = LibclangSymbols.toMethodHandle("clang_Cursor_isMacroFunctionLike", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_isMacroFunctionLike"));
        }
        try {
            return (int) clang_Cursor_isMacroFunctionLike.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_isMacroFunctionLike$int(CXCursor C) {
        return clang_Cursor_isMacroFunctionLike(C.pointer());
    }

    private static MethodHandle clang_Cursor_isMacroBuiltin;

    private static int clang_Cursor_isMacroBuiltin(MemorySegment C) {
        if (clang_Cursor_isMacroBuiltin == null) {
            clang_Cursor_isMacroBuiltin = LibclangSymbols.toMethodHandle("clang_Cursor_isMacroBuiltin", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_isMacroBuiltin"));
        }
        try {
            return (int) clang_Cursor_isMacroBuiltin.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_isMacroBuiltin$int(CXCursor C) {
        return clang_Cursor_isMacroBuiltin(C.pointer());
    }

    private static MethodHandle clang_Cursor_isFunctionInlined;

    private static int clang_Cursor_isFunctionInlined(MemorySegment C) {
        if (clang_Cursor_isFunctionInlined == null) {
            clang_Cursor_isFunctionInlined = LibclangSymbols.toMethodHandle("clang_Cursor_isFunctionInlined", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_isFunctionInlined"));
        }
        try {
            return (int) clang_Cursor_isFunctionInlined.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_isFunctionInlined$int(CXCursor C) {
        return clang_Cursor_isFunctionInlined(C.pointer());
    }

    private static MethodHandle clang_isVolatileQualifiedType;

    private static int clang_isVolatileQualifiedType(MemorySegment T) {
        if (clang_isVolatileQualifiedType == null) {
            clang_isVolatileQualifiedType = LibclangSymbols.toMethodHandle("clang_isVolatileQualifiedType", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isVolatileQualifiedType"));
        }
        try {
            return (int) clang_isVolatileQualifiedType.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isVolatileQualifiedType$int(CXType T) {
        return clang_isVolatileQualifiedType(T.pointer());
    }

    private static MethodHandle clang_isRestrictQualifiedType;

    private static int clang_isRestrictQualifiedType(MemorySegment T) {
        if (clang_isRestrictQualifiedType == null) {
            clang_isRestrictQualifiedType = LibclangSymbols.toMethodHandle("clang_isRestrictQualifiedType", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isRestrictQualifiedType"));
        }
        try {
            return (int) clang_isRestrictQualifiedType.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isRestrictQualifiedType$int(CXType T) {
        return clang_isRestrictQualifiedType(T.pointer());
    }

    private static MethodHandle clang_getAddressSpace;

    private static int clang_getAddressSpace(MemorySegment T) {
        if (clang_getAddressSpace == null) {
            clang_getAddressSpace = LibclangSymbols.toMethodHandle("clang_getAddressSpace", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getAddressSpace"));
        }
        try {
            return (int) clang_getAddressSpace.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getAddressSpace$int(CXType T) {
        return clang_getAddressSpace(T.pointer());
    }

    private static MethodHandle clang_getTypedefName;

    private static MemorySegment clang_getTypedefName(SegmentAllocator allocator, MemorySegment CT) {
        if (clang_getTypedefName == null) {
            clang_getTypedefName = LibclangSymbols.toMethodHandle("clang_getTypedefName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getTypedefName"));
        }
        try {
            return (MemorySegment) clang_getTypedefName.invoke(allocator, CT);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getTypedefName$CXString(SegmentAllocator allocator,CXType CT) {
        return new CXString(FunctionUtils.makePointer(clang_getTypedefName(allocator,CT.pointer())));
    }

    private static MethodHandle clang_getPointeeType;

    private static MemorySegment clang_getPointeeType(SegmentAllocator allocator, MemorySegment T) {
        if (clang_getPointeeType == null) {
            clang_getPointeeType = LibclangSymbols.toMethodHandle("clang_getPointeeType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getPointeeType"));
        }
        try {
            return (MemorySegment) clang_getPointeeType.invoke(allocator, T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_getPointeeType$CXType(SegmentAllocator allocator,CXType T) {
        return new CXType(FunctionUtils.makePointer(clang_getPointeeType(allocator,T.pointer())));
    }

    private static MethodHandle clang_getUnqualifiedType;

    private static MemorySegment clang_getUnqualifiedType(SegmentAllocator allocator, MemorySegment CT) {
        if (clang_getUnqualifiedType == null) {
            clang_getUnqualifiedType = LibclangSymbols.toMethodHandle("clang_getUnqualifiedType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getUnqualifiedType"));
        }
        try {
            return (MemorySegment) clang_getUnqualifiedType.invoke(allocator, CT);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_getUnqualifiedType$CXType(SegmentAllocator allocator,CXType CT) {
        return new CXType(FunctionUtils.makePointer(clang_getUnqualifiedType(allocator,CT.pointer())));
    }

    private static MethodHandle clang_getNonReferenceType;

    private static MemorySegment clang_getNonReferenceType(SegmentAllocator allocator, MemorySegment CT) {
        if (clang_getNonReferenceType == null) {
            clang_getNonReferenceType = LibclangSymbols.toMethodHandle("clang_getNonReferenceType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getNonReferenceType"));
        }
        try {
            return (MemorySegment) clang_getNonReferenceType.invoke(allocator, CT);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_getNonReferenceType$CXType(SegmentAllocator allocator,CXType CT) {
        return new CXType(FunctionUtils.makePointer(clang_getNonReferenceType(allocator,CT.pointer())));
    }

    private static MethodHandle clang_getTypeDeclaration;

    private static MemorySegment clang_getTypeDeclaration(SegmentAllocator allocator, MemorySegment T) {
        if (clang_getTypeDeclaration == null) {
            clang_getTypeDeclaration = LibclangSymbols.toMethodHandle("clang_getTypeDeclaration", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getTypeDeclaration"));
        }
        try {
            return (MemorySegment) clang_getTypeDeclaration.invoke(allocator, T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursor clang_getTypeDeclaration$CXCursor(SegmentAllocator allocator,CXType T) {
        return new CXCursor(FunctionUtils.makePointer(clang_getTypeDeclaration(allocator,T.pointer())));
    }

    private static MethodHandle clang_getDeclObjCTypeEncoding;

    private static MemorySegment clang_getDeclObjCTypeEncoding(SegmentAllocator allocator, MemorySegment C) {
        if (clang_getDeclObjCTypeEncoding == null) {
            clang_getDeclObjCTypeEncoding = LibclangSymbols.toMethodHandle("clang_getDeclObjCTypeEncoding", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDeclObjCTypeEncoding"));
        }
        try {
            return (MemorySegment) clang_getDeclObjCTypeEncoding.invoke(allocator, C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getDeclObjCTypeEncoding$CXString(SegmentAllocator allocator,CXCursor C) {
        return new CXString(FunctionUtils.makePointer(clang_getDeclObjCTypeEncoding(allocator,C.pointer())));
    }

    private static MethodHandle clang_Type_getObjCEncoding;

    private static MemorySegment clang_Type_getObjCEncoding(SegmentAllocator allocator, MemorySegment type) {
        if (clang_Type_getObjCEncoding == null) {
            clang_Type_getObjCEncoding = LibclangSymbols.toMethodHandle("clang_Type_getObjCEncoding", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getObjCEncoding"));
        }
        try {
            return (MemorySegment) clang_Type_getObjCEncoding.invoke(allocator, type);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_Type_getObjCEncoding$CXString(SegmentAllocator allocator,CXType type) {
        return new CXString(FunctionUtils.makePointer(clang_Type_getObjCEncoding(allocator,type.pointer())));
    }

    private static MethodHandle clang_getTypeKindSpelling;

    private static MemorySegment clang_getTypeKindSpelling(SegmentAllocator allocator, int K) {
        if (clang_getTypeKindSpelling == null) {
            clang_getTypeKindSpelling = LibclangSymbols.toMethodHandle("clang_getTypeKindSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getTypeKindSpelling"));
        }
        try {
            return (MemorySegment) clang_getTypeKindSpelling.invoke(allocator, K);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getTypeKindSpelling$CXString(SegmentAllocator allocator,CXTypeKind K) {
        return new CXString(FunctionUtils.makePointer(clang_getTypeKindSpelling(allocator,K.value())));
    }

    private static MethodHandle clang_getFunctionTypeCallingConv;

    private static int clang_getFunctionTypeCallingConv(MemorySegment T) {
        if (clang_getFunctionTypeCallingConv == null) {
            clang_getFunctionTypeCallingConv = LibclangSymbols.toMethodHandle("clang_getFunctionTypeCallingConv", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getFunctionTypeCallingConv"));
        }
        try {
            return (int) clang_getFunctionTypeCallingConv.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCallingConv clang_getFunctionTypeCallingConv$CXCallingConv(CXType T) {
        return new CXCallingConv(clang_getFunctionTypeCallingConv(T.pointer()));
    }

    private static MethodHandle clang_getResultType;

    private static MemorySegment clang_getResultType(SegmentAllocator allocator, MemorySegment T) {
        if (clang_getResultType == null) {
            clang_getResultType = LibclangSymbols.toMethodHandle("clang_getResultType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getResultType"));
        }
        try {
            return (MemorySegment) clang_getResultType.invoke(allocator, T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_getResultType$CXType(SegmentAllocator allocator,CXType T) {
        return new CXType(FunctionUtils.makePointer(clang_getResultType(allocator,T.pointer())));
    }

    private static MethodHandle clang_getExceptionSpecificationType;

    private static int clang_getExceptionSpecificationType(MemorySegment T) {
        if (clang_getExceptionSpecificationType == null) {
            clang_getExceptionSpecificationType = LibclangSymbols.toMethodHandle("clang_getExceptionSpecificationType", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getExceptionSpecificationType"));
        }
        try {
            return (int) clang_getExceptionSpecificationType.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getExceptionSpecificationType$int(CXType T) {
        return clang_getExceptionSpecificationType(T.pointer());
    }

    private static MethodHandle clang_getNumArgTypes;

    private static int clang_getNumArgTypes(MemorySegment T) {
        if (clang_getNumArgTypes == null) {
            clang_getNumArgTypes = LibclangSymbols.toMethodHandle("clang_getNumArgTypes", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getNumArgTypes"));
        }
        try {
            return (int) clang_getNumArgTypes.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getNumArgTypes$int(CXType T) {
        return clang_getNumArgTypes(T.pointer());
    }

    private static MethodHandle clang_getArgType;

    private static MemorySegment clang_getArgType(SegmentAllocator allocator, MemorySegment T, int i) {
        if (clang_getArgType == null) {
            clang_getArgType = LibclangSymbols.toMethodHandle("clang_getArgType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getArgType"));
        }
        try {
            return (MemorySegment) clang_getArgType.invoke(allocator, T, i);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_getArgType$CXType(SegmentAllocator allocator,CXType T,int i) {
        return new CXType(FunctionUtils.makePointer(clang_getArgType(allocator,T.pointer(),i)));
    }

    private static MethodHandle clang_Type_getObjCObjectBaseType;

    private static MemorySegment clang_Type_getObjCObjectBaseType(SegmentAllocator allocator, MemorySegment T) {
        if (clang_Type_getObjCObjectBaseType == null) {
            clang_Type_getObjCObjectBaseType = LibclangSymbols.toMethodHandle("clang_Type_getObjCObjectBaseType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getObjCObjectBaseType"));
        }
        try {
            return (MemorySegment) clang_Type_getObjCObjectBaseType.invoke(allocator, T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_Type_getObjCObjectBaseType$CXType(SegmentAllocator allocator,CXType T) {
        return new CXType(FunctionUtils.makePointer(clang_Type_getObjCObjectBaseType(allocator,T.pointer())));
    }

    private static MethodHandle clang_Type_getNumObjCProtocolRefs;

    private static int clang_Type_getNumObjCProtocolRefs(MemorySegment T) {
        if (clang_Type_getNumObjCProtocolRefs == null) {
            clang_Type_getNumObjCProtocolRefs = LibclangSymbols.toMethodHandle("clang_Type_getNumObjCProtocolRefs", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getNumObjCProtocolRefs"));
        }
        try {
            return (int) clang_Type_getNumObjCProtocolRefs.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Type_getNumObjCProtocolRefs$int(CXType T) {
        return clang_Type_getNumObjCProtocolRefs(T.pointer());
    }

    private static MethodHandle clang_Type_getObjCProtocolDecl;

    private static MemorySegment clang_Type_getObjCProtocolDecl(SegmentAllocator allocator, MemorySegment T, int i) {
        if (clang_Type_getObjCProtocolDecl == null) {
            clang_Type_getObjCProtocolDecl = LibclangSymbols.toMethodHandle("clang_Type_getObjCProtocolDecl", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getObjCProtocolDecl"));
        }
        try {
            return (MemorySegment) clang_Type_getObjCProtocolDecl.invoke(allocator, T, i);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursor clang_Type_getObjCProtocolDecl$CXCursor(SegmentAllocator allocator,CXType T,int i) {
        return new CXCursor(FunctionUtils.makePointer(clang_Type_getObjCProtocolDecl(allocator,T.pointer(),i)));
    }

    private static MethodHandle clang_Type_getNumObjCTypeArgs;

    private static int clang_Type_getNumObjCTypeArgs(MemorySegment T) {
        if (clang_Type_getNumObjCTypeArgs == null) {
            clang_Type_getNumObjCTypeArgs = LibclangSymbols.toMethodHandle("clang_Type_getNumObjCTypeArgs", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getNumObjCTypeArgs"));
        }
        try {
            return (int) clang_Type_getNumObjCTypeArgs.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Type_getNumObjCTypeArgs$int(CXType T) {
        return clang_Type_getNumObjCTypeArgs(T.pointer());
    }

    private static MethodHandle clang_Type_getObjCTypeArg;

    private static MemorySegment clang_Type_getObjCTypeArg(SegmentAllocator allocator, MemorySegment T, int i) {
        if (clang_Type_getObjCTypeArg == null) {
            clang_Type_getObjCTypeArg = LibclangSymbols.toMethodHandle("clang_Type_getObjCTypeArg", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getObjCTypeArg"));
        }
        try {
            return (MemorySegment) clang_Type_getObjCTypeArg.invoke(allocator, T, i);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_Type_getObjCTypeArg$CXType(SegmentAllocator allocator,CXType T,int i) {
        return new CXType(FunctionUtils.makePointer(clang_Type_getObjCTypeArg(allocator,T.pointer(),i)));
    }

    private static MethodHandle clang_isFunctionTypeVariadic;

    private static int clang_isFunctionTypeVariadic(MemorySegment T) {
        if (clang_isFunctionTypeVariadic == null) {
            clang_isFunctionTypeVariadic = LibclangSymbols.toMethodHandle("clang_isFunctionTypeVariadic", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isFunctionTypeVariadic"));
        }
        try {
            return (int) clang_isFunctionTypeVariadic.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isFunctionTypeVariadic$int(CXType T) {
        return clang_isFunctionTypeVariadic(T.pointer());
    }

    private static MethodHandle clang_getCursorResultType;

    private static MemorySegment clang_getCursorResultType(SegmentAllocator allocator, MemorySegment C) {
        if (clang_getCursorResultType == null) {
            clang_getCursorResultType = LibclangSymbols.toMethodHandle("clang_getCursorResultType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorResultType"));
        }
        try {
            return (MemorySegment) clang_getCursorResultType.invoke(allocator, C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_getCursorResultType$CXType(SegmentAllocator allocator,CXCursor C) {
        return new CXType(FunctionUtils.makePointer(clang_getCursorResultType(allocator,C.pointer())));
    }

    private static MethodHandle clang_getCursorExceptionSpecificationType;

    private static int clang_getCursorExceptionSpecificationType(MemorySegment C) {
        if (clang_getCursorExceptionSpecificationType == null) {
            clang_getCursorExceptionSpecificationType = LibclangSymbols.toMethodHandle("clang_getCursorExceptionSpecificationType", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorExceptionSpecificationType"));
        }
        try {
            return (int) clang_getCursorExceptionSpecificationType.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getCursorExceptionSpecificationType$int(CXCursor C) {
        return clang_getCursorExceptionSpecificationType(C.pointer());
    }

    private static MethodHandle clang_isPODType;

    private static int clang_isPODType(MemorySegment T) {
        if (clang_isPODType == null) {
            clang_isPODType = LibclangSymbols.toMethodHandle("clang_isPODType", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isPODType"));
        }
        try {
            return (int) clang_isPODType.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isPODType$int(CXType T) {
        return clang_isPODType(T.pointer());
    }

    private static MethodHandle clang_getElementType;

    private static MemorySegment clang_getElementType(SegmentAllocator allocator, MemorySegment T) {
        if (clang_getElementType == null) {
            clang_getElementType = LibclangSymbols.toMethodHandle("clang_getElementType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getElementType"));
        }
        try {
            return (MemorySegment) clang_getElementType.invoke(allocator, T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_getElementType$CXType(SegmentAllocator allocator,CXType T) {
        return new CXType(FunctionUtils.makePointer(clang_getElementType(allocator,T.pointer())));
    }

    private static MethodHandle clang_getNumElements;

    private static long clang_getNumElements(MemorySegment T) {
        if (clang_getNumElements == null) {
            clang_getNumElements = LibclangSymbols.toMethodHandle("clang_getNumElements", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getNumElements"));
        }
        try {
            return (long) clang_getNumElements.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static long clang_getNumElements$long(CXType T) {
        return clang_getNumElements(T.pointer());
    }

    private static MethodHandle clang_getArrayElementType;

    private static MemorySegment clang_getArrayElementType(SegmentAllocator allocator, MemorySegment T) {
        if (clang_getArrayElementType == null) {
            clang_getArrayElementType = LibclangSymbols.toMethodHandle("clang_getArrayElementType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getArrayElementType"));
        }
        try {
            return (MemorySegment) clang_getArrayElementType.invoke(allocator, T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_getArrayElementType$CXType(SegmentAllocator allocator,CXType T) {
        return new CXType(FunctionUtils.makePointer(clang_getArrayElementType(allocator,T.pointer())));
    }

    private static MethodHandle clang_getArraySize;

    private static long clang_getArraySize(MemorySegment T) {
        if (clang_getArraySize == null) {
            clang_getArraySize = LibclangSymbols.toMethodHandle("clang_getArraySize", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getArraySize"));
        }
        try {
            return (long) clang_getArraySize.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static long clang_getArraySize$long(CXType T) {
        return clang_getArraySize(T.pointer());
    }

    private static MethodHandle clang_Type_getNamedType;

    private static MemorySegment clang_Type_getNamedType(SegmentAllocator allocator, MemorySegment T) {
        if (clang_Type_getNamedType == null) {
            clang_Type_getNamedType = LibclangSymbols.toMethodHandle("clang_Type_getNamedType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getNamedType"));
        }
        try {
            return (MemorySegment) clang_Type_getNamedType.invoke(allocator, T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_Type_getNamedType$CXType(SegmentAllocator allocator,CXType T) {
        return new CXType(FunctionUtils.makePointer(clang_Type_getNamedType(allocator,T.pointer())));
    }

    private static MethodHandle clang_Type_isTransparentTagTypedef;

    private static int clang_Type_isTransparentTagTypedef(MemorySegment T) {
        if (clang_Type_isTransparentTagTypedef == null) {
            clang_Type_isTransparentTagTypedef = LibclangSymbols.toMethodHandle("clang_Type_isTransparentTagTypedef", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_isTransparentTagTypedef"));
        }
        try {
            return (int) clang_Type_isTransparentTagTypedef.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Type_isTransparentTagTypedef$int(CXType T) {
        return clang_Type_isTransparentTagTypedef(T.pointer());
    }

    private static MethodHandle clang_Type_getNullability;

    private static int clang_Type_getNullability(MemorySegment T) {
        if (clang_Type_getNullability == null) {
            clang_Type_getNullability = LibclangSymbols.toMethodHandle("clang_Type_getNullability", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getNullability"));
        }
        try {
            return (int) clang_Type_getNullability.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXTypeNullabilityKind clang_Type_getNullability$CXTypeNullabilityKind(CXType T) {
        return new CXTypeNullabilityKind(clang_Type_getNullability(T.pointer()));
    }

    private static MethodHandle clang_Type_getAlignOf;

    private static long clang_Type_getAlignOf(MemorySegment T) {
        if (clang_Type_getAlignOf == null) {
            clang_Type_getAlignOf = LibclangSymbols.toMethodHandle("clang_Type_getAlignOf", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getAlignOf"));
        }
        try {
            return (long) clang_Type_getAlignOf.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static long clang_Type_getAlignOf$long(CXType T) {
        return clang_Type_getAlignOf(T.pointer());
    }

    private static MethodHandle clang_Type_getClassType;

    private static MemorySegment clang_Type_getClassType(SegmentAllocator allocator, MemorySegment T) {
        if (clang_Type_getClassType == null) {
            clang_Type_getClassType = LibclangSymbols.toMethodHandle("clang_Type_getClassType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getClassType"));
        }
        try {
            return (MemorySegment) clang_Type_getClassType.invoke(allocator, T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_Type_getClassType$CXType(SegmentAllocator allocator,CXType T) {
        return new CXType(FunctionUtils.makePointer(clang_Type_getClassType(allocator,T.pointer())));
    }

    private static MethodHandle clang_Type_getSizeOf;

    private static long clang_Type_getSizeOf(MemorySegment T) {
        if (clang_Type_getSizeOf == null) {
            clang_Type_getSizeOf = LibclangSymbols.toMethodHandle("clang_Type_getSizeOf", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getSizeOf"));
        }
        try {
            return (long) clang_Type_getSizeOf.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static long clang_Type_getSizeOf$long(CXType T) {
        return clang_Type_getSizeOf(T.pointer());
    }

    private static MethodHandle clang_Type_getOffsetOf;

    private static long clang_Type_getOffsetOf(MemorySegment T, MemorySegment S) {
        if (clang_Type_getOffsetOf == null) {
            clang_Type_getOffsetOf = LibclangSymbols.toMethodHandle("clang_Type_getOffsetOf", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getOffsetOf"));
        }
        try {
            return (long) clang_Type_getOffsetOf.invoke(T, S);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static long clang_Type_getOffsetOf$long(CXType T, Pointer<VI8<Byte>> S) {
        return clang_Type_getOffsetOf(T.pointer(), S.pointer());
    }

    private static MethodHandle clang_Type_getModifiedType;

    private static MemorySegment clang_Type_getModifiedType(SegmentAllocator allocator, MemorySegment T) {
        if (clang_Type_getModifiedType == null) {
            clang_Type_getModifiedType = LibclangSymbols.toMethodHandle("clang_Type_getModifiedType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getModifiedType"));
        }
        try {
            return (MemorySegment) clang_Type_getModifiedType.invoke(allocator, T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_Type_getModifiedType$CXType(SegmentAllocator allocator,CXType T) {
        return new CXType(FunctionUtils.makePointer(clang_Type_getModifiedType(allocator,T.pointer())));
    }

    private static MethodHandle clang_Type_getValueType;

    private static MemorySegment clang_Type_getValueType(SegmentAllocator allocator, MemorySegment CT) {
        if (clang_Type_getValueType == null) {
            clang_Type_getValueType = LibclangSymbols.toMethodHandle("clang_Type_getValueType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getValueType"));
        }
        try {
            return (MemorySegment) clang_Type_getValueType.invoke(allocator, CT);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_Type_getValueType$CXType(SegmentAllocator allocator,CXType CT) {
        return new CXType(FunctionUtils.makePointer(clang_Type_getValueType(allocator,CT.pointer())));
    }

    private static MethodHandle clang_Cursor_getOffsetOfField;

    private static long clang_Cursor_getOffsetOfField(MemorySegment C) {
        if (clang_Cursor_getOffsetOfField == null) {
            clang_Cursor_getOffsetOfField = LibclangSymbols.toMethodHandle("clang_Cursor_getOffsetOfField", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getOffsetOfField"));
        }
        try {
            return (long) clang_Cursor_getOffsetOfField.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static long clang_Cursor_getOffsetOfField$long(CXCursor C) {
        return clang_Cursor_getOffsetOfField(C.pointer());
    }

    private static MethodHandle clang_Cursor_isAnonymous;

    private static int clang_Cursor_isAnonymous(MemorySegment C) {
        if (clang_Cursor_isAnonymous == null) {
            clang_Cursor_isAnonymous = LibclangSymbols.toMethodHandle("clang_Cursor_isAnonymous", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_isAnonymous"));
        }
        try {
            return (int) clang_Cursor_isAnonymous.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_isAnonymous$int(CXCursor C) {
        return clang_Cursor_isAnonymous(C.pointer());
    }

    private static MethodHandle clang_Cursor_isAnonymousRecordDecl;

    private static int clang_Cursor_isAnonymousRecordDecl(MemorySegment C) {
        if (clang_Cursor_isAnonymousRecordDecl == null) {
            clang_Cursor_isAnonymousRecordDecl = LibclangSymbols.toMethodHandle("clang_Cursor_isAnonymousRecordDecl", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_isAnonymousRecordDecl"));
        }
        try {
            return (int) clang_Cursor_isAnonymousRecordDecl.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_isAnonymousRecordDecl$int(CXCursor C) {
        return clang_Cursor_isAnonymousRecordDecl(C.pointer());
    }

    private static MethodHandle clang_Cursor_isInlineNamespace;

    private static int clang_Cursor_isInlineNamespace(MemorySegment C) {
        if (clang_Cursor_isInlineNamespace == null) {
            clang_Cursor_isInlineNamespace = LibclangSymbols.toMethodHandle("clang_Cursor_isInlineNamespace", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_isInlineNamespace"));
        }
        try {
            return (int) clang_Cursor_isInlineNamespace.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_isInlineNamespace$int(CXCursor C) {
        return clang_Cursor_isInlineNamespace(C.pointer());
    }

    private static MethodHandle clang_Type_getNumTemplateArguments;

    private static int clang_Type_getNumTemplateArguments(MemorySegment T) {
        if (clang_Type_getNumTemplateArguments == null) {
            clang_Type_getNumTemplateArguments = LibclangSymbols.toMethodHandle("clang_Type_getNumTemplateArguments", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getNumTemplateArguments"));
        }
        try {
            return (int) clang_Type_getNumTemplateArguments.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Type_getNumTemplateArguments$int(CXType T) {
        return clang_Type_getNumTemplateArguments(T.pointer());
    }

    private static MethodHandle clang_Type_getTemplateArgumentAsType;

    private static MemorySegment clang_Type_getTemplateArgumentAsType(SegmentAllocator allocator, MemorySegment T, int i) {
        if (clang_Type_getTemplateArgumentAsType == null) {
            clang_Type_getTemplateArgumentAsType = LibclangSymbols.toMethodHandle("clang_Type_getTemplateArgumentAsType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getTemplateArgumentAsType"));
        }
        try {
            return (MemorySegment) clang_Type_getTemplateArgumentAsType.invoke(allocator, T, i);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_Type_getTemplateArgumentAsType$CXType(SegmentAllocator allocator,CXType T,int i) {
        return new CXType(FunctionUtils.makePointer(clang_Type_getTemplateArgumentAsType(allocator,T.pointer(),i)));
    }

    private static MethodHandle clang_Type_getCXXRefQualifier;

    private static int clang_Type_getCXXRefQualifier(MemorySegment T) {
        if (clang_Type_getCXXRefQualifier == null) {
            clang_Type_getCXXRefQualifier = LibclangSymbols.toMethodHandle("clang_Type_getCXXRefQualifier", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_getCXXRefQualifier"));
        }
        try {
            return (int) clang_Type_getCXXRefQualifier.invoke(T);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXRefQualifierKind clang_Type_getCXXRefQualifier$CXRefQualifierKind(CXType T) {
        return new CXRefQualifierKind(clang_Type_getCXXRefQualifier(T.pointer()));
    }

    private static MethodHandle clang_Cursor_isBitField;

    private static int clang_Cursor_isBitField(MemorySegment C) {
        if (clang_Cursor_isBitField == null) {
            clang_Cursor_isBitField = LibclangSymbols.toMethodHandle("clang_Cursor_isBitField", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_isBitField"));
        }
        try {
            return (int) clang_Cursor_isBitField.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_isBitField$int(CXCursor C) {
        return clang_Cursor_isBitField(C.pointer());
    }

    private static MethodHandle clang_isVirtualBase;

    private static int clang_isVirtualBase(MemorySegment para0) {
        if (clang_isVirtualBase == null) {
            clang_isVirtualBase = LibclangSymbols.toMethodHandle("clang_isVirtualBase", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isVirtualBase"));
        }
        try {
            return (int) clang_isVirtualBase.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isVirtualBase$int(CXCursor para0) {
        return clang_isVirtualBase(para0.pointer());
    }

    private static MethodHandle clang_getCXXAccessSpecifier;

    private static int clang_getCXXAccessSpecifier(MemorySegment para0) {
        if (clang_getCXXAccessSpecifier == null) {
            clang_getCXXAccessSpecifier = LibclangSymbols.toMethodHandle("clang_getCXXAccessSpecifier", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCXXAccessSpecifier"));
        }
        try {
            return (int) clang_getCXXAccessSpecifier.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CX_CXXAccessSpecifier clang_getCXXAccessSpecifier$CX_CXXAccessSpecifier(CXCursor para0) {
        return new CX_CXXAccessSpecifier(clang_getCXXAccessSpecifier(para0.pointer()));
    }

    private static MethodHandle clang_Cursor_getStorageClass;

    private static int clang_Cursor_getStorageClass(MemorySegment para0) {
        if (clang_Cursor_getStorageClass == null) {
            clang_Cursor_getStorageClass = LibclangSymbols.toMethodHandle("clang_Cursor_getStorageClass", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getStorageClass"));
        }
        try {
            return (int) clang_Cursor_getStorageClass.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CX_StorageClass clang_Cursor_getStorageClass$CX_StorageClass(CXCursor para0) {
        return new CX_StorageClass(clang_Cursor_getStorageClass(para0.pointer()));
    }

    private static MethodHandle clang_getNumOverloadedDecls;

    private static int clang_getNumOverloadedDecls(MemorySegment cursor) {
        if (clang_getNumOverloadedDecls == null) {
            clang_getNumOverloadedDecls = LibclangSymbols.toMethodHandle("clang_getNumOverloadedDecls", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getNumOverloadedDecls"));
        }
        try {
            return (int) clang_getNumOverloadedDecls.invoke(cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getNumOverloadedDecls$int(CXCursor cursor) {
        return clang_getNumOverloadedDecls(cursor.pointer());
    }

    private static MethodHandle clang_getOverloadedDecl;

    private static MemorySegment clang_getOverloadedDecl(SegmentAllocator allocator, MemorySegment cursor, int index) {
        if (clang_getOverloadedDecl == null) {
            clang_getOverloadedDecl = LibclangSymbols.toMethodHandle("clang_getOverloadedDecl", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getOverloadedDecl"));
        }
        try {
            return (MemorySegment) clang_getOverloadedDecl.invoke(allocator, cursor, index);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursor clang_getOverloadedDecl$CXCursor(SegmentAllocator allocator,CXCursor cursor,int index) {
        return new CXCursor(FunctionUtils.makePointer(clang_getOverloadedDecl(allocator,cursor.pointer(),index)));
    }

    private static MethodHandle clang_getIBOutletCollectionType;

    private static MemorySegment clang_getIBOutletCollectionType(SegmentAllocator allocator, MemorySegment para0) {
        if (clang_getIBOutletCollectionType == null) {
            clang_getIBOutletCollectionType = LibclangSymbols.toMethodHandle("clang_getIBOutletCollectionType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getIBOutletCollectionType"));
        }
        try {
            return (MemorySegment) clang_getIBOutletCollectionType.invoke(allocator, para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_getIBOutletCollectionType$CXType(SegmentAllocator allocator,CXCursor para0) {
        return new CXType(FunctionUtils.makePointer(clang_getIBOutletCollectionType(allocator,para0.pointer())));
    }

    private static MethodHandle clang_visitChildren;

    private static int clang_visitChildren(MemorySegment parent, MemorySegment visitor, MemorySegment client_data) {
        if (clang_visitChildren == null) {
            clang_visitChildren = LibclangSymbols.toMethodHandle("clang_visitChildren", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_visitChildren"));
        }
        try {
            return (int) clang_visitChildren.invoke(parent, visitor, client_data);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_visitChildren$int(CXCursor parent, VPointer<CXCursorVisitor> visitor, CXClientData client_data) {
        return clang_visitChildren(parent.pointer(), visitor.value(), client_data.value());
    }

    private static MethodHandle clang_getCursorUSR;

    private static MemorySegment clang_getCursorUSR(SegmentAllocator allocator, MemorySegment para0) {
        if (clang_getCursorUSR == null) {
            clang_getCursorUSR = LibclangSymbols.toMethodHandle("clang_getCursorUSR", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorUSR"));
        }
        try {
            return (MemorySegment) clang_getCursorUSR.invoke(allocator, para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getCursorUSR$CXString(SegmentAllocator allocator,CXCursor para0) {
        return new CXString(FunctionUtils.makePointer(clang_getCursorUSR(allocator,para0.pointer())));
    }

    private static MethodHandle clang_constructUSR_ObjCClass;

    private static MemorySegment clang_constructUSR_ObjCClass(SegmentAllocator allocator, MemorySegment class_name) {
        if (clang_constructUSR_ObjCClass == null) {
            clang_constructUSR_ObjCClass = LibclangSymbols.toMethodHandle("clang_constructUSR_ObjCClass", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_constructUSR_ObjCClass"));
        }
        try {
            return (MemorySegment) clang_constructUSR_ObjCClass.invoke(allocator, class_name);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_constructUSR_ObjCClass$CXString(SegmentAllocator allocator,Pointer<VI8<Byte>> class_name) {
        return new CXString(FunctionUtils.makePointer(clang_constructUSR_ObjCClass(allocator,class_name.pointer())));
    }

    private static MethodHandle clang_constructUSR_ObjCCategory;

    private static MemorySegment clang_constructUSR_ObjCCategory(SegmentAllocator allocator, MemorySegment class_name, MemorySegment category_name) {
        if (clang_constructUSR_ObjCCategory == null) {
            clang_constructUSR_ObjCCategory = LibclangSymbols.toMethodHandle("clang_constructUSR_ObjCCategory", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_constructUSR_ObjCCategory"));
        }
        try {
            return (MemorySegment) clang_constructUSR_ObjCCategory.invoke(allocator, class_name, category_name);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_constructUSR_ObjCCategory$CXString(SegmentAllocator allocator,Pointer<VI8<Byte>> class_name,Pointer<VI8<Byte>> category_name) {
        return new CXString(FunctionUtils.makePointer(clang_constructUSR_ObjCCategory(allocator,class_name.pointer(),category_name.pointer())));
    }

    private static MethodHandle clang_constructUSR_ObjCProtocol;

    private static MemorySegment clang_constructUSR_ObjCProtocol(SegmentAllocator allocator, MemorySegment protocol_name) {
        if (clang_constructUSR_ObjCProtocol == null) {
            clang_constructUSR_ObjCProtocol = LibclangSymbols.toMethodHandle("clang_constructUSR_ObjCProtocol", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_constructUSR_ObjCProtocol"));
        }
        try {
            return (MemorySegment) clang_constructUSR_ObjCProtocol.invoke(allocator, protocol_name);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_constructUSR_ObjCProtocol$CXString(SegmentAllocator allocator,Pointer<VI8<Byte>> protocol_name) {
        return new CXString(FunctionUtils.makePointer(clang_constructUSR_ObjCProtocol(allocator,protocol_name.pointer())));
    }

    private static MethodHandle clang_constructUSR_ObjCIvar;

    private static MemorySegment clang_constructUSR_ObjCIvar(SegmentAllocator allocator, MemorySegment name, MemorySegment classUSR) {
        if (clang_constructUSR_ObjCIvar == null) {
            clang_constructUSR_ObjCIvar = LibclangSymbols.toMethodHandle("clang_constructUSR_ObjCIvar", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_constructUSR_ObjCIvar"));
        }
        try {
            return (MemorySegment) clang_constructUSR_ObjCIvar.invoke(allocator, name, classUSR);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_constructUSR_ObjCIvar$CXString(SegmentAllocator allocator,Pointer<VI8<Byte>> name,CXString classUSR) {
        return new CXString(FunctionUtils.makePointer(clang_constructUSR_ObjCIvar(allocator,name.pointer(),classUSR.pointer())));
    }

    private static MethodHandle clang_constructUSR_ObjCMethod;

    private static MemorySegment clang_constructUSR_ObjCMethod(SegmentAllocator allocator, MemorySegment name, int isInstanceMethod, MemorySegment classUSR) {
        if (clang_constructUSR_ObjCMethod == null) {
            clang_constructUSR_ObjCMethod = LibclangSymbols.toMethodHandle("clang_constructUSR_ObjCMethod", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_constructUSR_ObjCMethod"));
        }
        try {
            return (MemorySegment) clang_constructUSR_ObjCMethod.invoke(allocator, name, isInstanceMethod, classUSR);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_constructUSR_ObjCMethod$CXString(SegmentAllocator allocator,Pointer<VI8<Byte>> name,int isInstanceMethod,CXString classUSR) {
        return new CXString(FunctionUtils.makePointer(clang_constructUSR_ObjCMethod(allocator,name.pointer(),isInstanceMethod,classUSR.pointer())));
    }

    private static MethodHandle clang_constructUSR_ObjCProperty;

    private static MemorySegment clang_constructUSR_ObjCProperty(SegmentAllocator allocator, MemorySegment property, MemorySegment classUSR) {
        if (clang_constructUSR_ObjCProperty == null) {
            clang_constructUSR_ObjCProperty = LibclangSymbols.toMethodHandle("clang_constructUSR_ObjCProperty", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_constructUSR_ObjCProperty"));
        }
        try {
            return (MemorySegment) clang_constructUSR_ObjCProperty.invoke(allocator, property, classUSR);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_constructUSR_ObjCProperty$CXString(SegmentAllocator allocator,Pointer<VI8<Byte>> property,CXString classUSR) {
        return new CXString(FunctionUtils.makePointer(clang_constructUSR_ObjCProperty(allocator,property.pointer(),classUSR.pointer())));
    }

    private static MethodHandle clang_getCursorSpelling;

    private static MemorySegment clang_getCursorSpelling(SegmentAllocator allocator, MemorySegment para0) {
        if (clang_getCursorSpelling == null) {
            clang_getCursorSpelling = LibclangSymbols.toMethodHandle("clang_getCursorSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorSpelling"));
        }
        try {
            return (MemorySegment) clang_getCursorSpelling.invoke(allocator, para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getCursorSpelling$CXString(SegmentAllocator allocator,CXCursor para0) {
        return new CXString(FunctionUtils.makePointer(clang_getCursorSpelling(allocator,para0.pointer())));
    }

    private static MethodHandle clang_Cursor_getSpellingNameRange;

    private static MemorySegment clang_Cursor_getSpellingNameRange(SegmentAllocator allocator, MemorySegment para0, int pieceIndex, int options) {
        if (clang_Cursor_getSpellingNameRange == null) {
            clang_Cursor_getSpellingNameRange = LibclangSymbols.toMethodHandle("clang_Cursor_getSpellingNameRange", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getSpellingNameRange"));
        }
        try {
            return (MemorySegment) clang_Cursor_getSpellingNameRange.invoke(allocator, para0, pieceIndex, options);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceRange clang_Cursor_getSpellingNameRange$CXSourceRange(SegmentAllocator allocator,CXCursor para0,int pieceIndex,int options) {
        return new CXSourceRange(FunctionUtils.makePointer(clang_Cursor_getSpellingNameRange(allocator,para0.pointer(),pieceIndex,options)));
    }

    private static MethodHandle clang_PrintingPolicy_getProperty;

    private static int clang_PrintingPolicy_getProperty(MemorySegment Policy, int Property) {
        if (clang_PrintingPolicy_getProperty == null) {
            clang_PrintingPolicy_getProperty = LibclangSymbols.toMethodHandle("clang_PrintingPolicy_getProperty", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_PrintingPolicy_getProperty"));
        }
        try {
            return (int) clang_PrintingPolicy_getProperty.invoke(Policy, Property);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_PrintingPolicy_getProperty$int(CXPrintingPolicy Policy, CXPrintingPolicyProperty Property) {
        return clang_PrintingPolicy_getProperty(Policy.value(), Property.value());
    }

    private static MethodHandle clang_PrintingPolicy_setProperty;

    private static void clang_PrintingPolicy_setProperty(MemorySegment Policy, int Property, int Value) {
        if (clang_PrintingPolicy_setProperty == null) {
            clang_PrintingPolicy_setProperty = LibclangSymbols.toMethodHandle("clang_PrintingPolicy_setProperty", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_PrintingPolicy_setProperty"));
        }
        try {
            clang_PrintingPolicy_setProperty.invoke(Policy, Property, Value);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_PrintingPolicy_setProperty(CXPrintingPolicy Policy, CXPrintingPolicyProperty Property, int Value) {
        clang_PrintingPolicy_setProperty(Policy.value(), Property.value(), Value);
    }

    private static MethodHandle clang_getCursorPrintingPolicy;

    private static MemorySegment clang_getCursorPrintingPolicy(MemorySegment para0) {
        if (clang_getCursorPrintingPolicy == null) {
            clang_getCursorPrintingPolicy = LibclangSymbols.toMethodHandle("clang_getCursorPrintingPolicy", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorPrintingPolicy"));
        }
        try {
            return (MemorySegment) clang_getCursorPrintingPolicy.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXPrintingPolicy clang_getCursorPrintingPolicy$CXPrintingPolicy(CXCursor para0) {
        return new CXPrintingPolicy(clang_getCursorPrintingPolicy(para0.pointer()));
    }

    private static MethodHandle clang_PrintingPolicy_dispose;

    private static void clang_PrintingPolicy_dispose(MemorySegment Policy) {
        if (clang_PrintingPolicy_dispose == null) {
            clang_PrintingPolicy_dispose = LibclangSymbols.toMethodHandle("clang_PrintingPolicy_dispose", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_PrintingPolicy_dispose"));
        }
        try {
            clang_PrintingPolicy_dispose.invoke(Policy);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_PrintingPolicy_dispose(CXPrintingPolicy Policy) {
        clang_PrintingPolicy_dispose(Policy.value());
    }

    private static MethodHandle clang_getCursorPrettyPrinted;

    private static MemorySegment clang_getCursorPrettyPrinted(SegmentAllocator allocator, MemorySegment Cursor, MemorySegment Policy) {
        if (clang_getCursorPrettyPrinted == null) {
            clang_getCursorPrettyPrinted = LibclangSymbols.toMethodHandle("clang_getCursorPrettyPrinted", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorPrettyPrinted"));
        }
        try {
            return (MemorySegment) clang_getCursorPrettyPrinted.invoke(allocator, Cursor, Policy);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getCursorPrettyPrinted$CXString(SegmentAllocator allocator,CXCursor Cursor,CXPrintingPolicy Policy) {
        return new CXString(FunctionUtils.makePointer(clang_getCursorPrettyPrinted(allocator,Cursor.pointer(),Policy.value())));
    }

    private static MethodHandle clang_getCursorDisplayName;

    private static MemorySegment clang_getCursorDisplayName(SegmentAllocator allocator, MemorySegment para0) {
        if (clang_getCursorDisplayName == null) {
            clang_getCursorDisplayName = LibclangSymbols.toMethodHandle("clang_getCursorDisplayName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorDisplayName"));
        }
        try {
            return (MemorySegment) clang_getCursorDisplayName.invoke(allocator, para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getCursorDisplayName$CXString(SegmentAllocator allocator,CXCursor para0) {
        return new CXString(FunctionUtils.makePointer(clang_getCursorDisplayName(allocator,para0.pointer())));
    }

    private static MethodHandle clang_getCursorReferenced;

    private static MemorySegment clang_getCursorReferenced(SegmentAllocator allocator, MemorySegment para0) {
        if (clang_getCursorReferenced == null) {
            clang_getCursorReferenced = LibclangSymbols.toMethodHandle("clang_getCursorReferenced", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorReferenced"));
        }
        try {
            return (MemorySegment) clang_getCursorReferenced.invoke(allocator, para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursor clang_getCursorReferenced$CXCursor(SegmentAllocator allocator,CXCursor para0) {
        return new CXCursor(FunctionUtils.makePointer(clang_getCursorReferenced(allocator,para0.pointer())));
    }

    private static MethodHandle clang_getCursorDefinition;

    private static MemorySegment clang_getCursorDefinition(SegmentAllocator allocator, MemorySegment para0) {
        if (clang_getCursorDefinition == null) {
            clang_getCursorDefinition = LibclangSymbols.toMethodHandle("clang_getCursorDefinition", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorDefinition"));
        }
        try {
            return (MemorySegment) clang_getCursorDefinition.invoke(allocator, para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursor clang_getCursorDefinition$CXCursor(SegmentAllocator allocator,CXCursor para0) {
        return new CXCursor(FunctionUtils.makePointer(clang_getCursorDefinition(allocator,para0.pointer())));
    }

    private static MethodHandle clang_isCursorDefinition;

    private static int clang_isCursorDefinition(MemorySegment para0) {
        if (clang_isCursorDefinition == null) {
            clang_isCursorDefinition = LibclangSymbols.toMethodHandle("clang_isCursorDefinition", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_isCursorDefinition"));
        }
        try {
            return (int) clang_isCursorDefinition.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_isCursorDefinition$int(CXCursor para0) {
        return clang_isCursorDefinition(para0.pointer());
    }

    private static MethodHandle clang_getCanonicalCursor;

    private static MemorySegment clang_getCanonicalCursor(SegmentAllocator allocator, MemorySegment para0) {
        if (clang_getCanonicalCursor == null) {
            clang_getCanonicalCursor = LibclangSymbols.toMethodHandle("clang_getCanonicalCursor", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCanonicalCursor"));
        }
        try {
            return (MemorySegment) clang_getCanonicalCursor.invoke(allocator, para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursor clang_getCanonicalCursor$CXCursor(SegmentAllocator allocator,CXCursor para0) {
        return new CXCursor(FunctionUtils.makePointer(clang_getCanonicalCursor(allocator,para0.pointer())));
    }

    private static MethodHandle clang_Cursor_getObjCSelectorIndex;

    private static int clang_Cursor_getObjCSelectorIndex(MemorySegment para0) {
        if (clang_Cursor_getObjCSelectorIndex == null) {
            clang_Cursor_getObjCSelectorIndex = LibclangSymbols.toMethodHandle("clang_Cursor_getObjCSelectorIndex", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getObjCSelectorIndex"));
        }
        try {
            return (int) clang_Cursor_getObjCSelectorIndex.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_getObjCSelectorIndex$int(CXCursor para0) {
        return clang_Cursor_getObjCSelectorIndex(para0.pointer());
    }

    private static MethodHandle clang_Cursor_isDynamicCall;

    private static int clang_Cursor_isDynamicCall(MemorySegment C) {
        if (clang_Cursor_isDynamicCall == null) {
            clang_Cursor_isDynamicCall = LibclangSymbols.toMethodHandle("clang_Cursor_isDynamicCall", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_isDynamicCall"));
        }
        try {
            return (int) clang_Cursor_isDynamicCall.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_isDynamicCall$int(CXCursor C) {
        return clang_Cursor_isDynamicCall(C.pointer());
    }

    private static MethodHandle clang_Cursor_getReceiverType;

    private static MemorySegment clang_Cursor_getReceiverType(SegmentAllocator allocator, MemorySegment C) {
        if (clang_Cursor_getReceiverType == null) {
            clang_Cursor_getReceiverType = LibclangSymbols.toMethodHandle("clang_Cursor_getReceiverType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getReceiverType"));
        }
        try {
            return (MemorySegment) clang_Cursor_getReceiverType.invoke(allocator, C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXType clang_Cursor_getReceiverType$CXType(SegmentAllocator allocator,CXCursor C) {
        return new CXType(FunctionUtils.makePointer(clang_Cursor_getReceiverType(allocator,C.pointer())));
    }

    private static MethodHandle clang_Cursor_getObjCPropertyAttributes;

    private static int clang_Cursor_getObjCPropertyAttributes(MemorySegment C, int reserved) {
        if (clang_Cursor_getObjCPropertyAttributes == null) {
            clang_Cursor_getObjCPropertyAttributes = LibclangSymbols.toMethodHandle("clang_Cursor_getObjCPropertyAttributes", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getObjCPropertyAttributes"));
        }
        try {
            return (int) clang_Cursor_getObjCPropertyAttributes.invoke(C, reserved);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_getObjCPropertyAttributes$int(CXCursor C, int reserved) {
        return clang_Cursor_getObjCPropertyAttributes(C.pointer(), reserved);
    }

    private static MethodHandle clang_Cursor_getObjCPropertyGetterName;

    private static MemorySegment clang_Cursor_getObjCPropertyGetterName(SegmentAllocator allocator, MemorySegment C) {
        if (clang_Cursor_getObjCPropertyGetterName == null) {
            clang_Cursor_getObjCPropertyGetterName = LibclangSymbols.toMethodHandle("clang_Cursor_getObjCPropertyGetterName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getObjCPropertyGetterName"));
        }
        try {
            return (MemorySegment) clang_Cursor_getObjCPropertyGetterName.invoke(allocator, C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_Cursor_getObjCPropertyGetterName$CXString(SegmentAllocator allocator,CXCursor C) {
        return new CXString(FunctionUtils.makePointer(clang_Cursor_getObjCPropertyGetterName(allocator,C.pointer())));
    }

    private static MethodHandle clang_Cursor_getObjCPropertySetterName;

    private static MemorySegment clang_Cursor_getObjCPropertySetterName(SegmentAllocator allocator, MemorySegment C) {
        if (clang_Cursor_getObjCPropertySetterName == null) {
            clang_Cursor_getObjCPropertySetterName = LibclangSymbols.toMethodHandle("clang_Cursor_getObjCPropertySetterName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getObjCPropertySetterName"));
        }
        try {
            return (MemorySegment) clang_Cursor_getObjCPropertySetterName.invoke(allocator, C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_Cursor_getObjCPropertySetterName$CXString(SegmentAllocator allocator,CXCursor C) {
        return new CXString(FunctionUtils.makePointer(clang_Cursor_getObjCPropertySetterName(allocator,C.pointer())));
    }

    private static MethodHandle clang_Cursor_getObjCDeclQualifiers;

    private static int clang_Cursor_getObjCDeclQualifiers(MemorySegment C) {
        if (clang_Cursor_getObjCDeclQualifiers == null) {
            clang_Cursor_getObjCDeclQualifiers = LibclangSymbols.toMethodHandle("clang_Cursor_getObjCDeclQualifiers", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getObjCDeclQualifiers"));
        }
        try {
            return (int) clang_Cursor_getObjCDeclQualifiers.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_getObjCDeclQualifiers$int(CXCursor C) {
        return clang_Cursor_getObjCDeclQualifiers(C.pointer());
    }

    private static MethodHandle clang_Cursor_isObjCOptional;

    private static int clang_Cursor_isObjCOptional(MemorySegment C) {
        if (clang_Cursor_isObjCOptional == null) {
            clang_Cursor_isObjCOptional = LibclangSymbols.toMethodHandle("clang_Cursor_isObjCOptional", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_isObjCOptional"));
        }
        try {
            return (int) clang_Cursor_isObjCOptional.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_isObjCOptional$int(CXCursor C) {
        return clang_Cursor_isObjCOptional(C.pointer());
    }

    private static MethodHandle clang_Cursor_isVariadic;

    private static int clang_Cursor_isVariadic(MemorySegment C) {
        if (clang_Cursor_isVariadic == null) {
            clang_Cursor_isVariadic = LibclangSymbols.toMethodHandle("clang_Cursor_isVariadic", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_isVariadic"));
        }
        try {
            return (int) clang_Cursor_isVariadic.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_isVariadic$int(CXCursor C) {
        return clang_Cursor_isVariadic(C.pointer());
    }

    private static MethodHandle clang_Cursor_isExternalSymbol;

    private static int clang_Cursor_isExternalSymbol(MemorySegment C, MemorySegment language, MemorySegment definedIn, MemorySegment isGenerated) {
        if (clang_Cursor_isExternalSymbol == null) {
            clang_Cursor_isExternalSymbol = LibclangSymbols.toMethodHandle("clang_Cursor_isExternalSymbol", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_isExternalSymbol"));
        }
        try {
            return (int) clang_Cursor_isExternalSymbol.invoke(C, language, definedIn, isGenerated);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Cursor_isExternalSymbol$int(CXCursor C, Pointer<CXString> language, Pointer<CXString> definedIn, Pointer<VI32<Integer>> isGenerated) {
        return clang_Cursor_isExternalSymbol(C.pointer(), language.pointer(), definedIn.pointer(), isGenerated.pointer());
    }

    private static MethodHandle clang_Cursor_getCommentRange;

    private static MemorySegment clang_Cursor_getCommentRange(SegmentAllocator allocator, MemorySegment C) {
        if (clang_Cursor_getCommentRange == null) {
            clang_Cursor_getCommentRange = LibclangSymbols.toMethodHandle("clang_Cursor_getCommentRange", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getCommentRange"));
        }
        try {
            return (MemorySegment) clang_Cursor_getCommentRange.invoke(allocator, C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceRange clang_Cursor_getCommentRange$CXSourceRange(SegmentAllocator allocator,CXCursor C) {
        return new CXSourceRange(FunctionUtils.makePointer(clang_Cursor_getCommentRange(allocator,C.pointer())));
    }

    private static MethodHandle clang_Cursor_getRawCommentText;

    private static MemorySegment clang_Cursor_getRawCommentText(SegmentAllocator allocator, MemorySegment C) {
        if (clang_Cursor_getRawCommentText == null) {
            clang_Cursor_getRawCommentText = LibclangSymbols.toMethodHandle("clang_Cursor_getRawCommentText", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getRawCommentText"));
        }
        try {
            return (MemorySegment) clang_Cursor_getRawCommentText.invoke(allocator, C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_Cursor_getRawCommentText$CXString(SegmentAllocator allocator,CXCursor C) {
        return new CXString(FunctionUtils.makePointer(clang_Cursor_getRawCommentText(allocator,C.pointer())));
    }

    private static MethodHandle clang_Cursor_getBriefCommentText;

    private static MemorySegment clang_Cursor_getBriefCommentText(SegmentAllocator allocator, MemorySegment C) {
        if (clang_Cursor_getBriefCommentText == null) {
            clang_Cursor_getBriefCommentText = LibclangSymbols.toMethodHandle("clang_Cursor_getBriefCommentText", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getBriefCommentText"));
        }
        try {
            return (MemorySegment) clang_Cursor_getBriefCommentText.invoke(allocator, C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_Cursor_getBriefCommentText$CXString(SegmentAllocator allocator,CXCursor C) {
        return new CXString(FunctionUtils.makePointer(clang_Cursor_getBriefCommentText(allocator,C.pointer())));
    }

    private static MethodHandle clang_Cursor_getMangling;

    private static MemorySegment clang_Cursor_getMangling(SegmentAllocator allocator, MemorySegment para0) {
        if (clang_Cursor_getMangling == null) {
            clang_Cursor_getMangling = LibclangSymbols.toMethodHandle("clang_Cursor_getMangling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getMangling"));
        }
        try {
            return (MemorySegment) clang_Cursor_getMangling.invoke(allocator, para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_Cursor_getMangling$CXString(SegmentAllocator allocator,CXCursor para0) {
        return new CXString(FunctionUtils.makePointer(clang_Cursor_getMangling(allocator,para0.pointer())));
    }

    private static MethodHandle clang_Cursor_getCXXManglings;

    private static MemorySegment clang_Cursor_getCXXManglings(MemorySegment para0) {
        if (clang_Cursor_getCXXManglings == null) {
            clang_Cursor_getCXXManglings = LibclangSymbols.toMethodHandle("clang_Cursor_getCXXManglings", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getCXXManglings"));
        }
        try {
            return (MemorySegment) clang_Cursor_getCXXManglings.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<CXStringSet> clang_Cursor_getCXXManglings$Pointer(CXCursor para0) {
        return FunctionUtils.makePointer(clang_Cursor_getCXXManglings(para0.pointer()));
    }

    private static MethodHandle clang_Cursor_getObjCManglings;

    private static MemorySegment clang_Cursor_getObjCManglings(MemorySegment para0) {
        if (clang_Cursor_getObjCManglings == null) {
            clang_Cursor_getObjCManglings = LibclangSymbols.toMethodHandle("clang_Cursor_getObjCManglings", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getObjCManglings"));
        }
        try {
            return (MemorySegment) clang_Cursor_getObjCManglings.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<CXStringSet> clang_Cursor_getObjCManglings$Pointer(CXCursor para0) {
        return FunctionUtils.makePointer(clang_Cursor_getObjCManglings(para0.pointer()));
    }

    private static MethodHandle clang_Cursor_getModule;

    private static MemorySegment clang_Cursor_getModule(MemorySegment C) {
        if (clang_Cursor_getModule == null) {
            clang_Cursor_getModule = LibclangSymbols.toMethodHandle("clang_Cursor_getModule", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_getModule"));
        }
        try {
            return (MemorySegment) clang_Cursor_getModule.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXModule clang_Cursor_getModule$CXModule(CXCursor C) {
        return new CXModule(clang_Cursor_getModule(C.pointer()));
    }

    private static MethodHandle clang_getModuleForFile;

    private static MemorySegment clang_getModuleForFile(MemorySegment para0, MemorySegment para1) {
        if (clang_getModuleForFile == null) {
            clang_getModuleForFile = LibclangSymbols.toMethodHandle("clang_getModuleForFile", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getModuleForFile"));
        }
        try {
            return (MemorySegment) clang_getModuleForFile.invoke(para0, para1);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXModule clang_getModuleForFile$CXModule(CXTranslationUnit para0, CXFile para1) {
        return new CXModule(clang_getModuleForFile(para0.value(), para1.value()));
    }

    private static MethodHandle clang_Module_getASTFile;

    private static MemorySegment clang_Module_getASTFile(MemorySegment Module) {
        if (clang_Module_getASTFile == null) {
            clang_Module_getASTFile = LibclangSymbols.toMethodHandle("clang_Module_getASTFile", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Module_getASTFile"));
        }
        try {
            return (MemorySegment) clang_Module_getASTFile.invoke(Module);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXFile clang_Module_getASTFile$CXFile(CXModule Module) {
        return new CXFile(clang_Module_getASTFile(Module.value()));
    }

    private static MethodHandle clang_Module_getParent;

    private static MemorySegment clang_Module_getParent(MemorySegment Module) {
        if (clang_Module_getParent == null) {
            clang_Module_getParent = LibclangSymbols.toMethodHandle("clang_Module_getParent", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Module_getParent"));
        }
        try {
            return (MemorySegment) clang_Module_getParent.invoke(Module);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXModule clang_Module_getParent$CXModule(CXModule Module) {
        return new CXModule(clang_Module_getParent(Module.value()));
    }

    private static MethodHandle clang_Module_getName;

    private static MemorySegment clang_Module_getName(SegmentAllocator allocator, MemorySegment Module) {
        if (clang_Module_getName == null) {
            clang_Module_getName = LibclangSymbols.toMethodHandle("clang_Module_getName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Module_getName"));
        }
        try {
            return (MemorySegment) clang_Module_getName.invoke(allocator, Module);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_Module_getName$CXString(SegmentAllocator allocator,CXModule Module) {
        return new CXString(FunctionUtils.makePointer(clang_Module_getName(allocator,Module.value())));
    }

    private static MethodHandle clang_Module_getFullName;

    private static MemorySegment clang_Module_getFullName(SegmentAllocator allocator, MemorySegment Module) {
        if (clang_Module_getFullName == null) {
            clang_Module_getFullName = LibclangSymbols.toMethodHandle("clang_Module_getFullName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Module_getFullName"));
        }
        try {
            return (MemorySegment) clang_Module_getFullName.invoke(allocator, Module);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_Module_getFullName$CXString(SegmentAllocator allocator,CXModule Module) {
        return new CXString(FunctionUtils.makePointer(clang_Module_getFullName(allocator,Module.value())));
    }

    private static MethodHandle clang_Module_isSystem;

    private static int clang_Module_isSystem(MemorySegment Module) {
        if (clang_Module_isSystem == null) {
            clang_Module_isSystem = LibclangSymbols.toMethodHandle("clang_Module_isSystem", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Module_isSystem"));
        }
        try {
            return (int) clang_Module_isSystem.invoke(Module);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Module_isSystem$int(CXModule Module) {
        return clang_Module_isSystem(Module.value());
    }

    private static MethodHandle clang_Module_getNumTopLevelHeaders;

    private static int clang_Module_getNumTopLevelHeaders(MemorySegment para0, MemorySegment Module) {
        if (clang_Module_getNumTopLevelHeaders == null) {
            clang_Module_getNumTopLevelHeaders = LibclangSymbols.toMethodHandle("clang_Module_getNumTopLevelHeaders", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Module_getNumTopLevelHeaders"));
        }
        try {
            return (int) clang_Module_getNumTopLevelHeaders.invoke(para0, Module);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Module_getNumTopLevelHeaders$int(CXTranslationUnit para0, CXModule Module) {
        return clang_Module_getNumTopLevelHeaders(para0.value(), Module.value());
    }

    private static MethodHandle clang_Module_getTopLevelHeader;

    private static MemorySegment clang_Module_getTopLevelHeader(MemorySegment para0, MemorySegment Module, int Index) {
        if (clang_Module_getTopLevelHeader == null) {
            clang_Module_getTopLevelHeader = LibclangSymbols.toMethodHandle("clang_Module_getTopLevelHeader", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Module_getTopLevelHeader"));
        }
        try {
            return (MemorySegment) clang_Module_getTopLevelHeader.invoke(para0, Module, Index);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXFile clang_Module_getTopLevelHeader$CXFile(CXTranslationUnit para0, CXModule Module, int Index) {
        return new CXFile(clang_Module_getTopLevelHeader(para0.value(), Module.value(), Index));
    }

    private static MethodHandle clang_CXXConstructor_isConvertingConstructor;

    private static int clang_CXXConstructor_isConvertingConstructor(MemorySegment C) {
        if (clang_CXXConstructor_isConvertingConstructor == null) {
            clang_CXXConstructor_isConvertingConstructor = LibclangSymbols.toMethodHandle("clang_CXXConstructor_isConvertingConstructor", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXXConstructor_isConvertingConstructor"));
        }
        try {
            return (int) clang_CXXConstructor_isConvertingConstructor.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXXConstructor_isConvertingConstructor$int(CXCursor C) {
        return clang_CXXConstructor_isConvertingConstructor(C.pointer());
    }

    private static MethodHandle clang_CXXConstructor_isCopyConstructor;

    private static int clang_CXXConstructor_isCopyConstructor(MemorySegment C) {
        if (clang_CXXConstructor_isCopyConstructor == null) {
            clang_CXXConstructor_isCopyConstructor = LibclangSymbols.toMethodHandle("clang_CXXConstructor_isCopyConstructor", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXXConstructor_isCopyConstructor"));
        }
        try {
            return (int) clang_CXXConstructor_isCopyConstructor.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXXConstructor_isCopyConstructor$int(CXCursor C) {
        return clang_CXXConstructor_isCopyConstructor(C.pointer());
    }

    private static MethodHandle clang_CXXConstructor_isDefaultConstructor;

    private static int clang_CXXConstructor_isDefaultConstructor(MemorySegment C) {
        if (clang_CXXConstructor_isDefaultConstructor == null) {
            clang_CXXConstructor_isDefaultConstructor = LibclangSymbols.toMethodHandle("clang_CXXConstructor_isDefaultConstructor", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXXConstructor_isDefaultConstructor"));
        }
        try {
            return (int) clang_CXXConstructor_isDefaultConstructor.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXXConstructor_isDefaultConstructor$int(CXCursor C) {
        return clang_CXXConstructor_isDefaultConstructor(C.pointer());
    }

    private static MethodHandle clang_CXXConstructor_isMoveConstructor;

    private static int clang_CXXConstructor_isMoveConstructor(MemorySegment C) {
        if (clang_CXXConstructor_isMoveConstructor == null) {
            clang_CXXConstructor_isMoveConstructor = LibclangSymbols.toMethodHandle("clang_CXXConstructor_isMoveConstructor", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXXConstructor_isMoveConstructor"));
        }
        try {
            return (int) clang_CXXConstructor_isMoveConstructor.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXXConstructor_isMoveConstructor$int(CXCursor C) {
        return clang_CXXConstructor_isMoveConstructor(C.pointer());
    }

    private static MethodHandle clang_CXXField_isMutable;

    private static int clang_CXXField_isMutable(MemorySegment C) {
        if (clang_CXXField_isMutable == null) {
            clang_CXXField_isMutable = LibclangSymbols.toMethodHandle("clang_CXXField_isMutable", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXXField_isMutable"));
        }
        try {
            return (int) clang_CXXField_isMutable.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXXField_isMutable$int(CXCursor C) {
        return clang_CXXField_isMutable(C.pointer());
    }

    private static MethodHandle clang_CXXMethod_isDefaulted;

    private static int clang_CXXMethod_isDefaulted(MemorySegment C) {
        if (clang_CXXMethod_isDefaulted == null) {
            clang_CXXMethod_isDefaulted = LibclangSymbols.toMethodHandle("clang_CXXMethod_isDefaulted", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXXMethod_isDefaulted"));
        }
        try {
            return (int) clang_CXXMethod_isDefaulted.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXXMethod_isDefaulted$int(CXCursor C) {
        return clang_CXXMethod_isDefaulted(C.pointer());
    }

    private static MethodHandle clang_CXXMethod_isDeleted;

    private static int clang_CXXMethod_isDeleted(MemorySegment C) {
        if (clang_CXXMethod_isDeleted == null) {
            clang_CXXMethod_isDeleted = LibclangSymbols.toMethodHandle("clang_CXXMethod_isDeleted", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXXMethod_isDeleted"));
        }
        try {
            return (int) clang_CXXMethod_isDeleted.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXXMethod_isDeleted$int(CXCursor C) {
        return clang_CXXMethod_isDeleted(C.pointer());
    }

    private static MethodHandle clang_CXXMethod_isPureVirtual;

    private static int clang_CXXMethod_isPureVirtual(MemorySegment C) {
        if (clang_CXXMethod_isPureVirtual == null) {
            clang_CXXMethod_isPureVirtual = LibclangSymbols.toMethodHandle("clang_CXXMethod_isPureVirtual", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXXMethod_isPureVirtual"));
        }
        try {
            return (int) clang_CXXMethod_isPureVirtual.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXXMethod_isPureVirtual$int(CXCursor C) {
        return clang_CXXMethod_isPureVirtual(C.pointer());
    }

    private static MethodHandle clang_CXXMethod_isStatic;

    private static int clang_CXXMethod_isStatic(MemorySegment C) {
        if (clang_CXXMethod_isStatic == null) {
            clang_CXXMethod_isStatic = LibclangSymbols.toMethodHandle("clang_CXXMethod_isStatic", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXXMethod_isStatic"));
        }
        try {
            return (int) clang_CXXMethod_isStatic.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXXMethod_isStatic$int(CXCursor C) {
        return clang_CXXMethod_isStatic(C.pointer());
    }

    private static MethodHandle clang_CXXMethod_isVirtual;

    private static int clang_CXXMethod_isVirtual(MemorySegment C) {
        if (clang_CXXMethod_isVirtual == null) {
            clang_CXXMethod_isVirtual = LibclangSymbols.toMethodHandle("clang_CXXMethod_isVirtual", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXXMethod_isVirtual"));
        }
        try {
            return (int) clang_CXXMethod_isVirtual.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXXMethod_isVirtual$int(CXCursor C) {
        return clang_CXXMethod_isVirtual(C.pointer());
    }

    private static MethodHandle clang_CXXMethod_isCopyAssignmentOperator;

    private static int clang_CXXMethod_isCopyAssignmentOperator(MemorySegment C) {
        if (clang_CXXMethod_isCopyAssignmentOperator == null) {
            clang_CXXMethod_isCopyAssignmentOperator = LibclangSymbols.toMethodHandle("clang_CXXMethod_isCopyAssignmentOperator", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXXMethod_isCopyAssignmentOperator"));
        }
        try {
            return (int) clang_CXXMethod_isCopyAssignmentOperator.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXXMethod_isCopyAssignmentOperator$int(CXCursor C) {
        return clang_CXXMethod_isCopyAssignmentOperator(C.pointer());
    }

    private static MethodHandle clang_CXXMethod_isMoveAssignmentOperator;

    private static int clang_CXXMethod_isMoveAssignmentOperator(MemorySegment C) {
        if (clang_CXXMethod_isMoveAssignmentOperator == null) {
            clang_CXXMethod_isMoveAssignmentOperator = LibclangSymbols.toMethodHandle("clang_CXXMethod_isMoveAssignmentOperator", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXXMethod_isMoveAssignmentOperator"));
        }
        try {
            return (int) clang_CXXMethod_isMoveAssignmentOperator.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXXMethod_isMoveAssignmentOperator$int(CXCursor C) {
        return clang_CXXMethod_isMoveAssignmentOperator(C.pointer());
    }

    private static MethodHandle clang_CXXRecord_isAbstract;

    private static int clang_CXXRecord_isAbstract(MemorySegment C) {
        if (clang_CXXRecord_isAbstract == null) {
            clang_CXXRecord_isAbstract = LibclangSymbols.toMethodHandle("clang_CXXRecord_isAbstract", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXXRecord_isAbstract"));
        }
        try {
            return (int) clang_CXXRecord_isAbstract.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXXRecord_isAbstract$int(CXCursor C) {
        return clang_CXXRecord_isAbstract(C.pointer());
    }

    private static MethodHandle clang_EnumDecl_isScoped;

    private static int clang_EnumDecl_isScoped(MemorySegment C) {
        if (clang_EnumDecl_isScoped == null) {
            clang_EnumDecl_isScoped = LibclangSymbols.toMethodHandle("clang_EnumDecl_isScoped", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_EnumDecl_isScoped"));
        }
        try {
            return (int) clang_EnumDecl_isScoped.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_EnumDecl_isScoped$int(CXCursor C) {
        return clang_EnumDecl_isScoped(C.pointer());
    }

    private static MethodHandle clang_CXXMethod_isConst;

    private static int clang_CXXMethod_isConst(MemorySegment C) {
        if (clang_CXXMethod_isConst == null) {
            clang_CXXMethod_isConst = LibclangSymbols.toMethodHandle("clang_CXXMethod_isConst", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_CXXMethod_isConst"));
        }
        try {
            return (int) clang_CXXMethod_isConst.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_CXXMethod_isConst$int(CXCursor C) {
        return clang_CXXMethod_isConst(C.pointer());
    }

    private static MethodHandle clang_getTemplateCursorKind;

    private static int clang_getTemplateCursorKind(MemorySegment C) {
        if (clang_getTemplateCursorKind == null) {
            clang_getTemplateCursorKind = LibclangSymbols.toMethodHandle("clang_getTemplateCursorKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getTemplateCursorKind"));
        }
        try {
            return (int) clang_getTemplateCursorKind.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursorKind clang_getTemplateCursorKind$CXCursorKind(CXCursor C) {
        return new CXCursorKind(clang_getTemplateCursorKind(C.pointer()));
    }

    private static MethodHandle clang_getSpecializedCursorTemplate;

    private static MemorySegment clang_getSpecializedCursorTemplate(SegmentAllocator allocator, MemorySegment C) {
        if (clang_getSpecializedCursorTemplate == null) {
            clang_getSpecializedCursorTemplate = LibclangSymbols.toMethodHandle("clang_getSpecializedCursorTemplate", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getSpecializedCursorTemplate"));
        }
        try {
            return (MemorySegment) clang_getSpecializedCursorTemplate.invoke(allocator, C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursor clang_getSpecializedCursorTemplate$CXCursor(SegmentAllocator allocator,CXCursor C) {
        return new CXCursor(FunctionUtils.makePointer(clang_getSpecializedCursorTemplate(allocator,C.pointer())));
    }

    private static MethodHandle clang_getCursorReferenceNameRange;

    private static MemorySegment clang_getCursorReferenceNameRange(SegmentAllocator allocator, MemorySegment C, int NameFlags, int PieceIndex) {
        if (clang_getCursorReferenceNameRange == null) {
            clang_getCursorReferenceNameRange = LibclangSymbols.toMethodHandle("clang_getCursorReferenceNameRange", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorReferenceNameRange"));
        }
        try {
            return (MemorySegment) clang_getCursorReferenceNameRange.invoke(allocator, C, NameFlags, PieceIndex);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceRange clang_getCursorReferenceNameRange$CXSourceRange(SegmentAllocator allocator,CXCursor C,int NameFlags,int PieceIndex) {
        return new CXSourceRange(FunctionUtils.makePointer(clang_getCursorReferenceNameRange(allocator,C.pointer(),NameFlags,PieceIndex)));
    }

    private static MethodHandle clang_getToken;

    private static MemorySegment clang_getToken(MemorySegment TU, MemorySegment Location) {
        if (clang_getToken == null) {
            clang_getToken = LibclangSymbols.toMethodHandle("clang_getToken", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getToken"));
        }
        try {
            return (MemorySegment) clang_getToken.invoke(TU, Location);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<CXToken> clang_getToken$Pointer(CXTranslationUnit TU, CXSourceLocation Location) {
        return FunctionUtils.makePointer(clang_getToken(TU.value(), Location.pointer()));
    }

    private static MethodHandle clang_getTokenKind;

    private static int clang_getTokenKind(MemorySegment para0) {
        if (clang_getTokenKind == null) {
            clang_getTokenKind = LibclangSymbols.toMethodHandle("clang_getTokenKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getTokenKind"));
        }
        try {
            return (int) clang_getTokenKind.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXTokenKind clang_getTokenKind$CXTokenKind(CXToken para0) {
        return new CXTokenKind(clang_getTokenKind(para0.pointer()));
    }

    private static MethodHandle clang_getTokenSpelling;

    private static MemorySegment clang_getTokenSpelling(SegmentAllocator allocator, MemorySegment para0, MemorySegment para1) {
        if (clang_getTokenSpelling == null) {
            clang_getTokenSpelling = LibclangSymbols.toMethodHandle("clang_getTokenSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getTokenSpelling"));
        }
        try {
            return (MemorySegment) clang_getTokenSpelling.invoke(allocator, para0, para1);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getTokenSpelling$CXString(SegmentAllocator allocator,CXTranslationUnit para0,CXToken para1) {
        return new CXString(FunctionUtils.makePointer(clang_getTokenSpelling(allocator,para0.value(),para1.pointer())));
    }

    private static MethodHandle clang_getTokenLocation;

    private static MemorySegment clang_getTokenLocation(SegmentAllocator allocator, MemorySegment para0, MemorySegment para1) {
        if (clang_getTokenLocation == null) {
            clang_getTokenLocation = LibclangSymbols.toMethodHandle("clang_getTokenLocation", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getTokenLocation"));
        }
        try {
            return (MemorySegment) clang_getTokenLocation.invoke(allocator, para0, para1);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceLocation clang_getTokenLocation$CXSourceLocation(SegmentAllocator allocator,CXTranslationUnit para0,CXToken para1) {
        return new CXSourceLocation(FunctionUtils.makePointer(clang_getTokenLocation(allocator,para0.value(),para1.pointer())));
    }

    private static MethodHandle clang_getTokenExtent;

    private static MemorySegment clang_getTokenExtent(SegmentAllocator allocator, MemorySegment para0, MemorySegment para1) {
        if (clang_getTokenExtent == null) {
            clang_getTokenExtent = LibclangSymbols.toMethodHandle("clang_getTokenExtent", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getTokenExtent"));
        }
        try {
            return (MemorySegment) clang_getTokenExtent.invoke(allocator, para0, para1);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceRange clang_getTokenExtent$CXSourceRange(SegmentAllocator allocator,CXTranslationUnit para0,CXToken para1) {
        return new CXSourceRange(FunctionUtils.makePointer(clang_getTokenExtent(allocator,para0.value(),para1.pointer())));
    }

    private static MethodHandle clang_tokenize;

    private static void clang_tokenize(MemorySegment TU, MemorySegment Range, MemorySegment Tokens, MemorySegment NumTokens) {
        if (clang_tokenize == null) {
            clang_tokenize = LibclangSymbols.toMethodHandle("clang_tokenize", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_tokenize"));
        }
        try {
            clang_tokenize.invoke(TU, Range, Tokens, NumTokens);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_tokenize(CXTranslationUnit TU, CXSourceRange Range, Pointer<Pointer<CXToken>> Tokens, Pointer<VI32<Integer>> NumTokens) {
        clang_tokenize(TU.value(), Range.pointer(), Tokens.pointer(), NumTokens.pointer());
    }

    private static MethodHandle clang_annotateTokens;

    private static void clang_annotateTokens(MemorySegment TU, MemorySegment Tokens, int NumTokens, MemorySegment Cursors) {
        if (clang_annotateTokens == null) {
            clang_annotateTokens = LibclangSymbols.toMethodHandle("clang_annotateTokens", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_annotateTokens"));
        }
        try {
            clang_annotateTokens.invoke(TU, Tokens, NumTokens, Cursors);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_annotateTokens(CXTranslationUnit TU, Pointer<CXToken> Tokens, int NumTokens, Pointer<CXCursor> Cursors) {
        clang_annotateTokens(TU.value(), Tokens.pointer(), NumTokens, Cursors.pointer());
    }

    private static MethodHandle clang_disposeTokens;

    private static void clang_disposeTokens(MemorySegment TU, MemorySegment Tokens, int NumTokens) {
        if (clang_disposeTokens == null) {
            clang_disposeTokens = LibclangSymbols.toMethodHandle("clang_disposeTokens", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_disposeTokens"));
        }
        try {
            clang_disposeTokens.invoke(TU, Tokens, NumTokens);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_disposeTokens(CXTranslationUnit TU, Pointer<CXToken> Tokens, int NumTokens) {
        clang_disposeTokens(TU.value(), Tokens.pointer(), NumTokens);
    }

    private static MethodHandle clang_getCursorKindSpelling;

    private static MemorySegment clang_getCursorKindSpelling(SegmentAllocator allocator, int Kind) {
        if (clang_getCursorKindSpelling == null) {
            clang_getCursorKindSpelling = LibclangSymbols.toMethodHandle("clang_getCursorKindSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorKindSpelling"));
        }
        try {
            return (MemorySegment) clang_getCursorKindSpelling.invoke(allocator, Kind);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getCursorKindSpelling$CXString(SegmentAllocator allocator,CXCursorKind Kind) {
        return new CXString(FunctionUtils.makePointer(clang_getCursorKindSpelling(allocator,Kind.value())));
    }

    private static MethodHandle clang_getDefinitionSpellingAndExtent;

    private static void clang_getDefinitionSpellingAndExtent(MemorySegment para0, MemorySegment startBuf, MemorySegment endBuf, MemorySegment startLine, MemorySegment startColumn, MemorySegment endLine, MemorySegment endColumn) {
        if (clang_getDefinitionSpellingAndExtent == null) {
            clang_getDefinitionSpellingAndExtent = LibclangSymbols.toMethodHandle("clang_getDefinitionSpellingAndExtent", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getDefinitionSpellingAndExtent"));
        }
        try {
            clang_getDefinitionSpellingAndExtent.invoke(para0, startBuf, endBuf, startLine, startColumn, endLine, endColumn);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_getDefinitionSpellingAndExtent(CXCursor para0, Pointer<Pointer<VI8<Byte>>> startBuf, Pointer<Pointer<VI8<Byte>>> endBuf, Pointer<VI32<Integer>> startLine, Pointer<VI32<Integer>> startColumn, Pointer<VI32<Integer>> endLine, Pointer<VI32<Integer>> endColumn) {
        clang_getDefinitionSpellingAndExtent(para0.pointer(), startBuf.pointer(), endBuf.pointer(), startLine.pointer(), startColumn.pointer(), endLine.pointer(), endColumn.pointer());
    }

    private static MethodHandle clang_enableStackTraces;

    public static void clang_enableStackTraces() {
        if (clang_enableStackTraces == null) {
            clang_enableStackTraces = LibclangSymbols.toMethodHandle("clang_enableStackTraces", FunctionDescriptor.ofVoid()).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_enableStackTraces"));
        }
        try {
            clang_enableStackTraces.invoke();
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    private static MethodHandle clang_executeOnThread;

    private static void clang_executeOnThread(MemorySegment fn, MemorySegment user_data, int stack_size) {
        if (clang_executeOnThread == null) {
            clang_executeOnThread = LibclangSymbols.toMethodHandle("clang_executeOnThread", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_executeOnThread"));
        }
        try {
            clang_executeOnThread.invoke(fn, user_data, stack_size);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_executeOnThread(Pointer<clang_executeOnThread$fn> fn, Pointer<?> user_data, int stack_size) {
        clang_executeOnThread(fn.pointer(), user_data.pointer(), stack_size);
    }

    private static MethodHandle clang_getCompletionChunkKind;

    private static int clang_getCompletionChunkKind(MemorySegment completion_string, int chunk_number) {
        if (clang_getCompletionChunkKind == null) {
            clang_getCompletionChunkKind = LibclangSymbols.toMethodHandle("clang_getCompletionChunkKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCompletionChunkKind"));
        }
        try {
            return (int) clang_getCompletionChunkKind.invoke(completion_string, chunk_number);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCompletionChunkKind clang_getCompletionChunkKind$CXCompletionChunkKind(CXCompletionString completion_string, int chunk_number) {
        return new CXCompletionChunkKind(clang_getCompletionChunkKind(completion_string.value(), chunk_number));
    }

    private static MethodHandle clang_getCompletionChunkText;

    private static MemorySegment clang_getCompletionChunkText(SegmentAllocator allocator, MemorySegment completion_string, int chunk_number) {
        if (clang_getCompletionChunkText == null) {
            clang_getCompletionChunkText = LibclangSymbols.toMethodHandle("clang_getCompletionChunkText", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCompletionChunkText"));
        }
        try {
            return (MemorySegment) clang_getCompletionChunkText.invoke(allocator, completion_string, chunk_number);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getCompletionChunkText$CXString(SegmentAllocator allocator,CXCompletionString completion_string,int chunk_number) {
        return new CXString(FunctionUtils.makePointer(clang_getCompletionChunkText(allocator,completion_string.value(),chunk_number)));
    }

    private static MethodHandle clang_getCompletionChunkCompletionString;

    private static MemorySegment clang_getCompletionChunkCompletionString(MemorySegment completion_string, int chunk_number) {
        if (clang_getCompletionChunkCompletionString == null) {
            clang_getCompletionChunkCompletionString = LibclangSymbols.toMethodHandle("clang_getCompletionChunkCompletionString", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCompletionChunkCompletionString"));
        }
        try {
            return (MemorySegment) clang_getCompletionChunkCompletionString.invoke(completion_string, chunk_number);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCompletionString clang_getCompletionChunkCompletionString$CXCompletionString(CXCompletionString completion_string, int chunk_number) {
        return new CXCompletionString(clang_getCompletionChunkCompletionString(completion_string.value(), chunk_number));
    }

    private static MethodHandle clang_getNumCompletionChunks;

    private static int clang_getNumCompletionChunks(MemorySegment completion_string) {
        if (clang_getNumCompletionChunks == null) {
            clang_getNumCompletionChunks = LibclangSymbols.toMethodHandle("clang_getNumCompletionChunks", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getNumCompletionChunks"));
        }
        try {
            return (int) clang_getNumCompletionChunks.invoke(completion_string);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getNumCompletionChunks$int(CXCompletionString completion_string) {
        return clang_getNumCompletionChunks(completion_string.value());
    }

    private static MethodHandle clang_getCompletionPriority;

    private static int clang_getCompletionPriority(MemorySegment completion_string) {
        if (clang_getCompletionPriority == null) {
            clang_getCompletionPriority = LibclangSymbols.toMethodHandle("clang_getCompletionPriority", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCompletionPriority"));
        }
        try {
            return (int) clang_getCompletionPriority.invoke(completion_string);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getCompletionPriority$int(CXCompletionString completion_string) {
        return clang_getCompletionPriority(completion_string.value());
    }

    private static MethodHandle clang_getCompletionAvailability;

    private static int clang_getCompletionAvailability(MemorySegment completion_string) {
        if (clang_getCompletionAvailability == null) {
            clang_getCompletionAvailability = LibclangSymbols.toMethodHandle("clang_getCompletionAvailability", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCompletionAvailability"));
        }
        try {
            return (int) clang_getCompletionAvailability.invoke(completion_string);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXAvailabilityKind clang_getCompletionAvailability$CXAvailabilityKind(CXCompletionString completion_string) {
        return new CXAvailabilityKind(clang_getCompletionAvailability(completion_string.value()));
    }

    private static MethodHandle clang_getCompletionNumAnnotations;

    private static int clang_getCompletionNumAnnotations(MemorySegment completion_string) {
        if (clang_getCompletionNumAnnotations == null) {
            clang_getCompletionNumAnnotations = LibclangSymbols.toMethodHandle("clang_getCompletionNumAnnotations", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCompletionNumAnnotations"));
        }
        try {
            return (int) clang_getCompletionNumAnnotations.invoke(completion_string);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getCompletionNumAnnotations$int(CXCompletionString completion_string) {
        return clang_getCompletionNumAnnotations(completion_string.value());
    }

    private static MethodHandle clang_getCompletionAnnotation;

    private static MemorySegment clang_getCompletionAnnotation(SegmentAllocator allocator, MemorySegment completion_string, int annotation_number) {
        if (clang_getCompletionAnnotation == null) {
            clang_getCompletionAnnotation = LibclangSymbols.toMethodHandle("clang_getCompletionAnnotation", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCompletionAnnotation"));
        }
        try {
            return (MemorySegment) clang_getCompletionAnnotation.invoke(allocator, completion_string, annotation_number);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getCompletionAnnotation$CXString(SegmentAllocator allocator,CXCompletionString completion_string,int annotation_number) {
        return new CXString(FunctionUtils.makePointer(clang_getCompletionAnnotation(allocator,completion_string.value(),annotation_number)));
    }

    private static MethodHandle clang_getCompletionParent;

    private static MemorySegment clang_getCompletionParent(SegmentAllocator allocator, MemorySegment completion_string, MemorySegment kind) {
        if (clang_getCompletionParent == null) {
            clang_getCompletionParent = LibclangSymbols.toMethodHandle("clang_getCompletionParent", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCompletionParent"));
        }
        try {
            return (MemorySegment) clang_getCompletionParent.invoke(allocator, completion_string, kind);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getCompletionParent$CXString(SegmentAllocator allocator,CXCompletionString completion_string,Pointer<CXCursorKind> kind) {
        return new CXString(FunctionUtils.makePointer(clang_getCompletionParent(allocator,completion_string.value(),kind.pointer())));
    }

    private static MethodHandle clang_getCompletionBriefComment;

    private static MemorySegment clang_getCompletionBriefComment(SegmentAllocator allocator, MemorySegment completion_string) {
        if (clang_getCompletionBriefComment == null) {
            clang_getCompletionBriefComment = LibclangSymbols.toMethodHandle("clang_getCompletionBriefComment", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCompletionBriefComment"));
        }
        try {
            return (MemorySegment) clang_getCompletionBriefComment.invoke(allocator, completion_string);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getCompletionBriefComment$CXString(SegmentAllocator allocator,CXCompletionString completion_string) {
        return new CXString(FunctionUtils.makePointer(clang_getCompletionBriefComment(allocator,completion_string.value())));
    }

    private static MethodHandle clang_getCursorCompletionString;

    private static MemorySegment clang_getCursorCompletionString(MemorySegment cursor) {
        if (clang_getCursorCompletionString == null) {
            clang_getCursorCompletionString = LibclangSymbols.toMethodHandle("clang_getCursorCompletionString", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCursorCompletionString"));
        }
        try {
            return (MemorySegment) clang_getCursorCompletionString.invoke(cursor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCompletionString clang_getCursorCompletionString$CXCompletionString(CXCursor cursor) {
        return new CXCompletionString(clang_getCursorCompletionString(cursor.pointer()));
    }

    private static MethodHandle clang_getCompletionNumFixIts;

    private static int clang_getCompletionNumFixIts(MemorySegment results, int completion_index) {
        if (clang_getCompletionNumFixIts == null) {
            clang_getCompletionNumFixIts = LibclangSymbols.toMethodHandle("clang_getCompletionNumFixIts", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCompletionNumFixIts"));
        }
        try {
            return (int) clang_getCompletionNumFixIts.invoke(results, completion_index);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_getCompletionNumFixIts$int(Pointer<CXCodeCompleteResults> results, int completion_index) {
        return clang_getCompletionNumFixIts(results.pointer(), completion_index);
    }

    private static MethodHandle clang_getCompletionFixIt;

    private static MemorySegment clang_getCompletionFixIt(SegmentAllocator allocator, MemorySegment results, int completion_index, int fixit_index, MemorySegment replacement_range) {
        if (clang_getCompletionFixIt == null) {
            clang_getCompletionFixIt = LibclangSymbols.toMethodHandle("clang_getCompletionFixIt", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getCompletionFixIt"));
        }
        try {
            return (MemorySegment) clang_getCompletionFixIt.invoke(allocator, results, completion_index, fixit_index, replacement_range);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getCompletionFixIt$CXString(SegmentAllocator allocator,Pointer<CXCodeCompleteResults> results,int completion_index,int fixit_index,Pointer<CXSourceRange> replacement_range) {
        return new CXString(FunctionUtils.makePointer(clang_getCompletionFixIt(allocator,results.pointer(),completion_index,fixit_index,replacement_range.pointer())));
    }

    private static MethodHandle clang_defaultCodeCompleteOptions;

    private static int clang_defaultCodeCompleteOptions() {
        if (clang_defaultCodeCompleteOptions == null) {
            clang_defaultCodeCompleteOptions = LibclangSymbols.toMethodHandle("clang_defaultCodeCompleteOptions", FunctionDescriptor.of(ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_defaultCodeCompleteOptions"));
        }
        try {
            return (int) clang_defaultCodeCompleteOptions.invoke();
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_defaultCodeCompleteOptions$int() {
        return clang_defaultCodeCompleteOptions();
    }

    private static MethodHandle clang_codeCompleteAt;

    private static MemorySegment clang_codeCompleteAt(MemorySegment TU, MemorySegment complete_filename, int complete_line, int complete_column, MemorySegment unsaved_files, int num_unsaved_files, int options) {
        if (clang_codeCompleteAt == null) {
            clang_codeCompleteAt = LibclangSymbols.toMethodHandle("clang_codeCompleteAt", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_codeCompleteAt"));
        }
        try {
            return (MemorySegment) clang_codeCompleteAt.invoke(TU, complete_filename, complete_line, complete_column, unsaved_files, num_unsaved_files, options);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<CXCodeCompleteResults> clang_codeCompleteAt$Pointer(CXTranslationUnit TU, Pointer<VI8<Byte>> complete_filename, int complete_line, int complete_column, Pointer<CXUnsavedFile> unsaved_files, int num_unsaved_files, int options) {
        return FunctionUtils.makePointer(clang_codeCompleteAt(TU.value(), complete_filename.pointer(), complete_line, complete_column, unsaved_files.pointer(), num_unsaved_files, options));
    }

    private static MethodHandle clang_sortCodeCompletionResults;

    private static void clang_sortCodeCompletionResults(MemorySegment Results, int NumResults) {
        if (clang_sortCodeCompletionResults == null) {
            clang_sortCodeCompletionResults = LibclangSymbols.toMethodHandle("clang_sortCodeCompletionResults", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_sortCodeCompletionResults"));
        }
        try {
            clang_sortCodeCompletionResults.invoke(Results, NumResults);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_sortCodeCompletionResults(Pointer<CXCompletionResult> Results, int NumResults) {
        clang_sortCodeCompletionResults(Results.pointer(), NumResults);
    }

    private static MethodHandle clang_disposeCodeCompleteResults;

    private static void clang_disposeCodeCompleteResults(MemorySegment Results) {
        if (clang_disposeCodeCompleteResults == null) {
            clang_disposeCodeCompleteResults = LibclangSymbols.toMethodHandle("clang_disposeCodeCompleteResults", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_disposeCodeCompleteResults"));
        }
        try {
            clang_disposeCodeCompleteResults.invoke(Results);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_disposeCodeCompleteResults(Pointer<CXCodeCompleteResults> Results) {
        clang_disposeCodeCompleteResults(Results.pointer());
    }

    private static MethodHandle clang_codeCompleteGetNumDiagnostics;

    private static int clang_codeCompleteGetNumDiagnostics(MemorySegment Results) {
        if (clang_codeCompleteGetNumDiagnostics == null) {
            clang_codeCompleteGetNumDiagnostics = LibclangSymbols.toMethodHandle("clang_codeCompleteGetNumDiagnostics", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_codeCompleteGetNumDiagnostics"));
        }
        try {
            return (int) clang_codeCompleteGetNumDiagnostics.invoke(Results);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_codeCompleteGetNumDiagnostics$int(Pointer<CXCodeCompleteResults> Results) {
        return clang_codeCompleteGetNumDiagnostics(Results.pointer());
    }

    private static MethodHandle clang_codeCompleteGetDiagnostic;

    private static MemorySegment clang_codeCompleteGetDiagnostic(MemorySegment Results, int Index) {
        if (clang_codeCompleteGetDiagnostic == null) {
            clang_codeCompleteGetDiagnostic = LibclangSymbols.toMethodHandle("clang_codeCompleteGetDiagnostic", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_codeCompleteGetDiagnostic"));
        }
        try {
            return (MemorySegment) clang_codeCompleteGetDiagnostic.invoke(Results, Index);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXDiagnostic clang_codeCompleteGetDiagnostic$CXDiagnostic(Pointer<CXCodeCompleteResults> Results, int Index) {
        return new CXDiagnostic(clang_codeCompleteGetDiagnostic(Results.pointer(), Index));
    }

    private static MethodHandle clang_codeCompleteGetContexts;

    private static long clang_codeCompleteGetContexts(MemorySegment Results) {
        if (clang_codeCompleteGetContexts == null) {
            clang_codeCompleteGetContexts = LibclangSymbols.toMethodHandle("clang_codeCompleteGetContexts", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_codeCompleteGetContexts"));
        }
        try {
            return (long) clang_codeCompleteGetContexts.invoke(Results);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static long clang_codeCompleteGetContexts$long(Pointer<CXCodeCompleteResults> Results) {
        return clang_codeCompleteGetContexts(Results.pointer());
    }

    private static MethodHandle clang_codeCompleteGetContainerKind;

    private static int clang_codeCompleteGetContainerKind(MemorySegment Results, MemorySegment IsIncomplete) {
        if (clang_codeCompleteGetContainerKind == null) {
            clang_codeCompleteGetContainerKind = LibclangSymbols.toMethodHandle("clang_codeCompleteGetContainerKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_codeCompleteGetContainerKind"));
        }
        try {
            return (int) clang_codeCompleteGetContainerKind.invoke(Results, IsIncomplete);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXCursorKind clang_codeCompleteGetContainerKind$CXCursorKind(Pointer<CXCodeCompleteResults> Results, Pointer<VI32<Integer>> IsIncomplete) {
        return new CXCursorKind(clang_codeCompleteGetContainerKind(Results.pointer(), IsIncomplete.pointer()));
    }

    private static MethodHandle clang_codeCompleteGetContainerUSR;

    private static MemorySegment clang_codeCompleteGetContainerUSR(SegmentAllocator allocator, MemorySegment Results) {
        if (clang_codeCompleteGetContainerUSR == null) {
            clang_codeCompleteGetContainerUSR = LibclangSymbols.toMethodHandle("clang_codeCompleteGetContainerUSR", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_codeCompleteGetContainerUSR"));
        }
        try {
            return (MemorySegment) clang_codeCompleteGetContainerUSR.invoke(allocator, Results);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_codeCompleteGetContainerUSR$CXString(SegmentAllocator allocator,Pointer<CXCodeCompleteResults> Results) {
        return new CXString(FunctionUtils.makePointer(clang_codeCompleteGetContainerUSR(allocator,Results.pointer())));
    }

    private static MethodHandle clang_codeCompleteGetObjCSelector;

    private static MemorySegment clang_codeCompleteGetObjCSelector(SegmentAllocator allocator, MemorySegment Results) {
        if (clang_codeCompleteGetObjCSelector == null) {
            clang_codeCompleteGetObjCSelector = LibclangSymbols.toMethodHandle("clang_codeCompleteGetObjCSelector", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_codeCompleteGetObjCSelector"));
        }
        try {
            return (MemorySegment) clang_codeCompleteGetObjCSelector.invoke(allocator, Results);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_codeCompleteGetObjCSelector$CXString(SegmentAllocator allocator,Pointer<CXCodeCompleteResults> Results) {
        return new CXString(FunctionUtils.makePointer(clang_codeCompleteGetObjCSelector(allocator,Results.pointer())));
    }

    private static MethodHandle clang_getClangVersion;

    private static MemorySegment clang_getClangVersion(SegmentAllocator allocator) {
        if (clang_getClangVersion == null) {
            clang_getClangVersion = LibclangSymbols.toMethodHandle("clang_getClangVersion", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getClangVersion"));
        }
        try {
            return (MemorySegment) clang_getClangVersion.invoke(allocator);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXString clang_getClangVersion$CXString(SegmentAllocator allocator) {
        return new CXString(FunctionUtils.makePointer(clang_getClangVersion(allocator)));
    }

    private static MethodHandle clang_toggleCrashRecovery;

    public static void clang_toggleCrashRecovery(int isEnabled) {
        if (clang_toggleCrashRecovery == null) {
            clang_toggleCrashRecovery = LibclangSymbols.toMethodHandle("clang_toggleCrashRecovery", FunctionDescriptor.ofVoid(ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_toggleCrashRecovery"));
        }
        try {
            clang_toggleCrashRecovery.invoke(isEnabled);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    private static MethodHandle clang_getInclusions;

    private static void clang_getInclusions(MemorySegment tu, MemorySegment visitor, MemorySegment client_data) {
        if (clang_getInclusions == null) {
            clang_getInclusions = LibclangSymbols.toMethodHandle("clang_getInclusions", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getInclusions"));
        }
        try {
            clang_getInclusions.invoke(tu, visitor, client_data);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_getInclusions(CXTranslationUnit tu, VPointer<CXInclusionVisitor> visitor, CXClientData client_data) {
        clang_getInclusions(tu.value(), visitor.value(), client_data.value());
    }

    private static MethodHandle clang_Cursor_Evaluate;

    private static MemorySegment clang_Cursor_Evaluate(MemorySegment C) {
        if (clang_Cursor_Evaluate == null) {
            clang_Cursor_Evaluate = LibclangSymbols.toMethodHandle("clang_Cursor_Evaluate", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Cursor_Evaluate"));
        }
        try {
            return (MemorySegment) clang_Cursor_Evaluate.invoke(C);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXEvalResult clang_Cursor_Evaluate$CXEvalResult(CXCursor C) {
        return new CXEvalResult(clang_Cursor_Evaluate(C.pointer()));
    }

    private static MethodHandle clang_EvalResult_getKind;

    private static int clang_EvalResult_getKind(MemorySegment E) {
        if (clang_EvalResult_getKind == null) {
            clang_EvalResult_getKind = LibclangSymbols.toMethodHandle("clang_EvalResult_getKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_EvalResult_getKind"));
        }
        try {
            return (int) clang_EvalResult_getKind.invoke(E);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXEvalResultKind clang_EvalResult_getKind$CXEvalResultKind(CXEvalResult E) {
        return new CXEvalResultKind(clang_EvalResult_getKind(E.value()));
    }

    private static MethodHandle clang_EvalResult_getAsInt;

    private static int clang_EvalResult_getAsInt(MemorySegment E) {
        if (clang_EvalResult_getAsInt == null) {
            clang_EvalResult_getAsInt = LibclangSymbols.toMethodHandle("clang_EvalResult_getAsInt", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_EvalResult_getAsInt"));
        }
        try {
            return (int) clang_EvalResult_getAsInt.invoke(E);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_EvalResult_getAsInt$int(CXEvalResult E) {
        return clang_EvalResult_getAsInt(E.value());
    }

    private static MethodHandle clang_EvalResult_getAsLongLong;

    private static long clang_EvalResult_getAsLongLong(MemorySegment E) {
        if (clang_EvalResult_getAsLongLong == null) {
            clang_EvalResult_getAsLongLong = LibclangSymbols.toMethodHandle("clang_EvalResult_getAsLongLong", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_EvalResult_getAsLongLong"));
        }
        try {
            return (long) clang_EvalResult_getAsLongLong.invoke(E);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static long clang_EvalResult_getAsLongLong$long(CXEvalResult E) {
        return clang_EvalResult_getAsLongLong(E.value());
    }

    private static MethodHandle clang_EvalResult_isUnsignedInt;

    private static int clang_EvalResult_isUnsignedInt(MemorySegment E) {
        if (clang_EvalResult_isUnsignedInt == null) {
            clang_EvalResult_isUnsignedInt = LibclangSymbols.toMethodHandle("clang_EvalResult_isUnsignedInt", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_EvalResult_isUnsignedInt"));
        }
        try {
            return (int) clang_EvalResult_isUnsignedInt.invoke(E);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_EvalResult_isUnsignedInt$int(CXEvalResult E) {
        return clang_EvalResult_isUnsignedInt(E.value());
    }

    private static MethodHandle clang_EvalResult_getAsUnsigned;

    private static long clang_EvalResult_getAsUnsigned(MemorySegment E) {
        if (clang_EvalResult_getAsUnsigned == null) {
            clang_EvalResult_getAsUnsigned = LibclangSymbols.toMethodHandle("clang_EvalResult_getAsUnsigned", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_EvalResult_getAsUnsigned"));
        }
        try {
            return (long) clang_EvalResult_getAsUnsigned.invoke(E);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static long clang_EvalResult_getAsUnsigned$long(CXEvalResult E) {
        return clang_EvalResult_getAsUnsigned(E.value());
    }

    private static MethodHandle clang_EvalResult_getAsDouble;

    private static double clang_EvalResult_getAsDouble(MemorySegment E) {
        if (clang_EvalResult_getAsDouble == null) {
            clang_EvalResult_getAsDouble = LibclangSymbols.toMethodHandle("clang_EvalResult_getAsDouble", FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_EvalResult_getAsDouble"));
        }
        try {
            return (double) clang_EvalResult_getAsDouble.invoke(E);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static double clang_EvalResult_getAsDouble$double(CXEvalResult E) {
        return clang_EvalResult_getAsDouble(E.value());
    }

    private static MethodHandle clang_EvalResult_getAsStr;

    private static MemorySegment clang_EvalResult_getAsStr(MemorySegment E) {
        if (clang_EvalResult_getAsStr == null) {
            clang_EvalResult_getAsStr = LibclangSymbols.toMethodHandle("clang_EvalResult_getAsStr", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_EvalResult_getAsStr"));
        }
        try {
            return (MemorySegment) clang_EvalResult_getAsStr.invoke(E);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<VI8<Byte>> clang_EvalResult_getAsStr$Pointer(CXEvalResult E) {
        return FunctionUtils.makePointer(clang_EvalResult_getAsStr(E.value()));
    }

    private static MethodHandle clang_EvalResult_dispose;

    private static void clang_EvalResult_dispose(MemorySegment E) {
        if (clang_EvalResult_dispose == null) {
            clang_EvalResult_dispose = LibclangSymbols.toMethodHandle("clang_EvalResult_dispose", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_EvalResult_dispose"));
        }
        try {
            clang_EvalResult_dispose.invoke(E);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_EvalResult_dispose(CXEvalResult E) {
        clang_EvalResult_dispose(E.value());
    }

    private static MethodHandle clang_getRemappings;

    private static MemorySegment clang_getRemappings(MemorySegment path) {
        if (clang_getRemappings == null) {
            clang_getRemappings = LibclangSymbols.toMethodHandle("clang_getRemappings", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getRemappings"));
        }
        try {
            return (MemorySegment) clang_getRemappings.invoke(path);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXRemapping clang_getRemappings$CXRemapping(Pointer<VI8<Byte>> path) {
        return new CXRemapping(clang_getRemappings(path.pointer()));
    }

    private static MethodHandle clang_getRemappingsFromFileList;

    private static MemorySegment clang_getRemappingsFromFileList(MemorySegment filePaths, int numFiles) {
        if (clang_getRemappingsFromFileList == null) {
            clang_getRemappingsFromFileList = LibclangSymbols.toMethodHandle("clang_getRemappingsFromFileList", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_getRemappingsFromFileList"));
        }
        try {
            return (MemorySegment) clang_getRemappingsFromFileList.invoke(filePaths, numFiles);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXRemapping clang_getRemappingsFromFileList$CXRemapping(Pointer<Pointer<VI8<Byte>>> filePaths, int numFiles) {
        return new CXRemapping(clang_getRemappingsFromFileList(filePaths.pointer(), numFiles));
    }

    private static MethodHandle clang_remap_getNumFiles;

    private static int clang_remap_getNumFiles(MemorySegment para0) {
        if (clang_remap_getNumFiles == null) {
            clang_remap_getNumFiles = LibclangSymbols.toMethodHandle("clang_remap_getNumFiles", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_remap_getNumFiles"));
        }
        try {
            return (int) clang_remap_getNumFiles.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_remap_getNumFiles$int(CXRemapping para0) {
        return clang_remap_getNumFiles(para0.value());
    }

    private static MethodHandle clang_remap_getFilenames;

    private static void clang_remap_getFilenames(MemorySegment para0, int index, MemorySegment original, MemorySegment transformed) {
        if (clang_remap_getFilenames == null) {
            clang_remap_getFilenames = LibclangSymbols.toMethodHandle("clang_remap_getFilenames", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_remap_getFilenames"));
        }
        try {
            clang_remap_getFilenames.invoke(para0, index, original, transformed);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_remap_getFilenames(CXRemapping para0, int index, Pointer<CXString> original, Pointer<CXString> transformed) {
        clang_remap_getFilenames(para0.value(), index, original.pointer(), transformed.pointer());
    }

    private static MethodHandle clang_remap_dispose;

    private static void clang_remap_dispose(MemorySegment para0) {
        if (clang_remap_dispose == null) {
            clang_remap_dispose = LibclangSymbols.toMethodHandle("clang_remap_dispose", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_remap_dispose"));
        }
        try {
            clang_remap_dispose.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_remap_dispose(CXRemapping para0) {
        clang_remap_dispose(para0.value());
    }

    private static MethodHandle clang_findReferencesInFile;

    private static int clang_findReferencesInFile(MemorySegment cursor, MemorySegment file, MemorySegment visitor) {
        if (clang_findReferencesInFile == null) {
            clang_findReferencesInFile = LibclangSymbols.toMethodHandle("clang_findReferencesInFile", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_findReferencesInFile"));
        }
        try {
            return (int) clang_findReferencesInFile.invoke(cursor, file, visitor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXResult clang_findReferencesInFile$CXResult(CXCursor cursor, CXFile file, CXCursorAndRangeVisitor visitor) {
        return new CXResult(clang_findReferencesInFile(cursor.pointer(), file.value(), visitor.pointer()));
    }

    private static MethodHandle clang_findIncludesInFile;

    private static int clang_findIncludesInFile(MemorySegment TU, MemorySegment file, MemorySegment visitor) {
        if (clang_findIncludesInFile == null) {
            clang_findIncludesInFile = LibclangSymbols.toMethodHandle("clang_findIncludesInFile", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_findIncludesInFile"));
        }
        try {
            return (int) clang_findIncludesInFile.invoke(TU, file, visitor);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXResult clang_findIncludesInFile$CXResult(CXTranslationUnit TU, CXFile file, CXCursorAndRangeVisitor visitor) {
        return new CXResult(clang_findIncludesInFile(TU.value(), file.value(), visitor.pointer()));
    }

    private static MethodHandle clang_index_isEntityObjCContainerKind;

    private static int clang_index_isEntityObjCContainerKind(int para0) {
        if (clang_index_isEntityObjCContainerKind == null) {
            clang_index_isEntityObjCContainerKind = LibclangSymbols.toMethodHandle("clang_index_isEntityObjCContainerKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_index_isEntityObjCContainerKind"));
        }
        try {
            return (int) clang_index_isEntityObjCContainerKind.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_index_isEntityObjCContainerKind$int(CXIdxEntityKind para0) {
        return clang_index_isEntityObjCContainerKind(para0.value());
    }

    private static MethodHandle clang_index_getObjCContainerDeclInfo;

    private static MemorySegment clang_index_getObjCContainerDeclInfo(MemorySegment para0) {
        if (clang_index_getObjCContainerDeclInfo == null) {
            clang_index_getObjCContainerDeclInfo = LibclangSymbols.toMethodHandle("clang_index_getObjCContainerDeclInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_index_getObjCContainerDeclInfo"));
        }
        try {
            return (MemorySegment) clang_index_getObjCContainerDeclInfo.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<CXIdxObjCContainerDeclInfo> clang_index_getObjCContainerDeclInfo$Pointer(Pointer<CXIdxDeclInfo> para0) {
        return FunctionUtils.makePointer(clang_index_getObjCContainerDeclInfo(para0.pointer()));
    }

    private static MethodHandle clang_index_getObjCInterfaceDeclInfo;

    private static MemorySegment clang_index_getObjCInterfaceDeclInfo(MemorySegment para0) {
        if (clang_index_getObjCInterfaceDeclInfo == null) {
            clang_index_getObjCInterfaceDeclInfo = LibclangSymbols.toMethodHandle("clang_index_getObjCInterfaceDeclInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_index_getObjCInterfaceDeclInfo"));
        }
        try {
            return (MemorySegment) clang_index_getObjCInterfaceDeclInfo.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<CXIdxObjCInterfaceDeclInfo> clang_index_getObjCInterfaceDeclInfo$Pointer(Pointer<CXIdxDeclInfo> para0) {
        return FunctionUtils.makePointer(clang_index_getObjCInterfaceDeclInfo(para0.pointer()));
    }

    private static MethodHandle clang_index_getObjCCategoryDeclInfo;

    private static MemorySegment clang_index_getObjCCategoryDeclInfo(MemorySegment para0) {
        if (clang_index_getObjCCategoryDeclInfo == null) {
            clang_index_getObjCCategoryDeclInfo = LibclangSymbols.toMethodHandle("clang_index_getObjCCategoryDeclInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_index_getObjCCategoryDeclInfo"));
        }
        try {
            return (MemorySegment) clang_index_getObjCCategoryDeclInfo.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<CXIdxObjCCategoryDeclInfo> clang_index_getObjCCategoryDeclInfo$Pointer(Pointer<CXIdxDeclInfo> para0) {
        return FunctionUtils.makePointer(clang_index_getObjCCategoryDeclInfo(para0.pointer()));
    }

    private static MethodHandle clang_index_getObjCProtocolRefListInfo;

    private static MemorySegment clang_index_getObjCProtocolRefListInfo(MemorySegment para0) {
        if (clang_index_getObjCProtocolRefListInfo == null) {
            clang_index_getObjCProtocolRefListInfo = LibclangSymbols.toMethodHandle("clang_index_getObjCProtocolRefListInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_index_getObjCProtocolRefListInfo"));
        }
        try {
            return (MemorySegment) clang_index_getObjCProtocolRefListInfo.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<CXIdxObjCProtocolRefListInfo> clang_index_getObjCProtocolRefListInfo$Pointer(Pointer<CXIdxDeclInfo> para0) {
        return FunctionUtils.makePointer(clang_index_getObjCProtocolRefListInfo(para0.pointer()));
    }

    private static MethodHandle clang_index_getObjCPropertyDeclInfo;

    private static MemorySegment clang_index_getObjCPropertyDeclInfo(MemorySegment para0) {
        if (clang_index_getObjCPropertyDeclInfo == null) {
            clang_index_getObjCPropertyDeclInfo = LibclangSymbols.toMethodHandle("clang_index_getObjCPropertyDeclInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_index_getObjCPropertyDeclInfo"));
        }
        try {
            return (MemorySegment) clang_index_getObjCPropertyDeclInfo.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<CXIdxObjCPropertyDeclInfo> clang_index_getObjCPropertyDeclInfo$Pointer(Pointer<CXIdxDeclInfo> para0) {
        return FunctionUtils.makePointer(clang_index_getObjCPropertyDeclInfo(para0.pointer()));
    }

    private static MethodHandle clang_index_getIBOutletCollectionAttrInfo;

    private static MemorySegment clang_index_getIBOutletCollectionAttrInfo(MemorySegment para0) {
        if (clang_index_getIBOutletCollectionAttrInfo == null) {
            clang_index_getIBOutletCollectionAttrInfo = LibclangSymbols.toMethodHandle("clang_index_getIBOutletCollectionAttrInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_index_getIBOutletCollectionAttrInfo"));
        }
        try {
            return (MemorySegment) clang_index_getIBOutletCollectionAttrInfo.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<CXIdxIBOutletCollectionAttrInfo> clang_index_getIBOutletCollectionAttrInfo$Pointer(Pointer<CXIdxAttrInfo> para0) {
        return FunctionUtils.makePointer(clang_index_getIBOutletCollectionAttrInfo(para0.pointer()));
    }

    private static MethodHandle clang_index_getCXXClassDeclInfo;

    private static MemorySegment clang_index_getCXXClassDeclInfo(MemorySegment para0) {
        if (clang_index_getCXXClassDeclInfo == null) {
            clang_index_getCXXClassDeclInfo = LibclangSymbols.toMethodHandle("clang_index_getCXXClassDeclInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_index_getCXXClassDeclInfo"));
        }
        try {
            return (MemorySegment) clang_index_getCXXClassDeclInfo.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static Pointer<CXIdxCXXClassDeclInfo> clang_index_getCXXClassDeclInfo$Pointer(Pointer<CXIdxDeclInfo> para0) {
        return FunctionUtils.makePointer(clang_index_getCXXClassDeclInfo(para0.pointer()));
    }

    private static MethodHandle clang_index_getClientContainer;

    private static MemorySegment clang_index_getClientContainer(MemorySegment para0) {
        if (clang_index_getClientContainer == null) {
            clang_index_getClientContainer = LibclangSymbols.toMethodHandle("clang_index_getClientContainer", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_index_getClientContainer"));
        }
        try {
            return (MemorySegment) clang_index_getClientContainer.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXIdxClientContainer clang_index_getClientContainer$CXIdxClientContainer(Pointer<CXIdxContainerInfo> para0) {
        return new CXIdxClientContainer(clang_index_getClientContainer(para0.pointer()));
    }

    private static MethodHandle clang_index_setClientContainer;

    private static void clang_index_setClientContainer(MemorySegment para0, MemorySegment para1) {
        if (clang_index_setClientContainer == null) {
            clang_index_setClientContainer = LibclangSymbols.toMethodHandle("clang_index_setClientContainer", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_index_setClientContainer"));
        }
        try {
            clang_index_setClientContainer.invoke(para0, para1);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_index_setClientContainer(Pointer<CXIdxContainerInfo> para0, CXIdxClientContainer para1) {
        clang_index_setClientContainer(para0.pointer(), para1.value());
    }

    private static MethodHandle clang_index_getClientEntity;

    private static MemorySegment clang_index_getClientEntity(MemorySegment para0) {
        if (clang_index_getClientEntity == null) {
            clang_index_getClientEntity = LibclangSymbols.toMethodHandle("clang_index_getClientEntity", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_index_getClientEntity"));
        }
        try {
            return (MemorySegment) clang_index_getClientEntity.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXIdxClientEntity clang_index_getClientEntity$CXIdxClientEntity(Pointer<CXIdxEntityInfo> para0) {
        return new CXIdxClientEntity(clang_index_getClientEntity(para0.pointer()));
    }

    private static MethodHandle clang_index_setClientEntity;

    private static void clang_index_setClientEntity(MemorySegment para0, MemorySegment para1) {
        if (clang_index_setClientEntity == null) {
            clang_index_setClientEntity = LibclangSymbols.toMethodHandle("clang_index_setClientEntity", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_index_setClientEntity"));
        }
        try {
            clang_index_setClientEntity.invoke(para0, para1);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_index_setClientEntity(Pointer<CXIdxEntityInfo> para0, CXIdxClientEntity para1) {
        clang_index_setClientEntity(para0.pointer(), para1.value());
    }

    private static MethodHandle clang_IndexAction_create;

    private static MemorySegment clang_IndexAction_create(MemorySegment CIdx) {
        if (clang_IndexAction_create == null) {
            clang_IndexAction_create = LibclangSymbols.toMethodHandle("clang_IndexAction_create", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_IndexAction_create"));
        }
        try {
            return (MemorySegment) clang_IndexAction_create.invoke(CIdx);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXIndexAction clang_IndexAction_create$CXIndexAction(CXIndex CIdx) {
        return new CXIndexAction(clang_IndexAction_create(CIdx.value()));
    }

    private static MethodHandle clang_IndexAction_dispose;

    private static void clang_IndexAction_dispose(MemorySegment para0) {
        if (clang_IndexAction_dispose == null) {
            clang_IndexAction_dispose = LibclangSymbols.toMethodHandle("clang_IndexAction_dispose", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_IndexAction_dispose"));
        }
        try {
            clang_IndexAction_dispose.invoke(para0);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_IndexAction_dispose(CXIndexAction para0) {
        clang_IndexAction_dispose(para0.value());
    }

    private static MethodHandle clang_indexSourceFile;

    private static int clang_indexSourceFile(MemorySegment para0, MemorySegment client_data, MemorySegment index_callbacks, int index_callbacks_size, int index_options, MemorySegment source_filename, MemorySegment command_line_args, int num_command_line_args, MemorySegment unsaved_files, int num_unsaved_files, MemorySegment out_TU, int TU_options) {
        if (clang_indexSourceFile == null) {
            clang_indexSourceFile = LibclangSymbols.toMethodHandle("clang_indexSourceFile", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_indexSourceFile"));
        }
        try {
            return (int) clang_indexSourceFile.invoke(para0, client_data, index_callbacks, index_callbacks_size, index_options, source_filename, command_line_args, num_command_line_args, unsaved_files, num_unsaved_files, out_TU, TU_options);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_indexSourceFile$int(CXIndexAction para0, CXClientData client_data, Pointer<IndexerCallbacks> index_callbacks, int index_callbacks_size, int index_options, Pointer<VI8<Byte>> source_filename, Pointer<Pointer<VI8<Byte>>> command_line_args, int num_command_line_args, Pointer<CXUnsavedFile> unsaved_files, int num_unsaved_files, Pointer<CXTranslationUnit> out_TU, int TU_options) {
        return clang_indexSourceFile(para0.value(), client_data.value(), index_callbacks.pointer(), index_callbacks_size, index_options, source_filename.pointer(), command_line_args.pointer(), num_command_line_args, unsaved_files.pointer(), num_unsaved_files, out_TU.pointer(), TU_options);
    }

    private static MethodHandle clang_indexSourceFileFullArgv;

    private static int clang_indexSourceFileFullArgv(MemorySegment para0, MemorySegment client_data, MemorySegment index_callbacks, int index_callbacks_size, int index_options, MemorySegment source_filename, MemorySegment command_line_args, int num_command_line_args, MemorySegment unsaved_files, int num_unsaved_files, MemorySegment out_TU, int TU_options) {
        if (clang_indexSourceFileFullArgv == null) {
            clang_indexSourceFileFullArgv = LibclangSymbols.toMethodHandle("clang_indexSourceFileFullArgv", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_indexSourceFileFullArgv"));
        }
        try {
            return (int) clang_indexSourceFileFullArgv.invoke(para0, client_data, index_callbacks, index_callbacks_size, index_options, source_filename, command_line_args, num_command_line_args, unsaved_files, num_unsaved_files, out_TU, TU_options);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_indexSourceFileFullArgv$int(CXIndexAction para0, CXClientData client_data, Pointer<IndexerCallbacks> index_callbacks, int index_callbacks_size, int index_options, Pointer<VI8<Byte>> source_filename, Pointer<Pointer<VI8<Byte>>> command_line_args, int num_command_line_args, Pointer<CXUnsavedFile> unsaved_files, int num_unsaved_files, Pointer<CXTranslationUnit> out_TU, int TU_options) {
        return clang_indexSourceFileFullArgv(para0.value(), client_data.value(), index_callbacks.pointer(), index_callbacks_size, index_options, source_filename.pointer(), command_line_args.pointer(), num_command_line_args, unsaved_files.pointer(), num_unsaved_files, out_TU.pointer(), TU_options);
    }

    private static MethodHandle clang_indexTranslationUnit;

    private static int clang_indexTranslationUnit(MemorySegment para0, MemorySegment client_data, MemorySegment index_callbacks, int index_callbacks_size, int index_options, MemorySegment para5) {
        if (clang_indexTranslationUnit == null) {
            clang_indexTranslationUnit = LibclangSymbols.toMethodHandle("clang_indexTranslationUnit", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_indexTranslationUnit"));
        }
        try {
            return (int) clang_indexTranslationUnit.invoke(para0, client_data, index_callbacks, index_callbacks_size, index_options, para5);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_indexTranslationUnit$int(CXIndexAction para0, CXClientData client_data, Pointer<IndexerCallbacks> index_callbacks, int index_callbacks_size, int index_options, CXTranslationUnit para5) {
        return clang_indexTranslationUnit(para0.value(), client_data.value(), index_callbacks.pointer(), index_callbacks_size, index_options, para5.value());
    }

    private static MethodHandle clang_indexLoc_getFileLocation;

    private static void clang_indexLoc_getFileLocation(MemorySegment loc, MemorySegment indexFile, MemorySegment file, MemorySegment line, MemorySegment column, MemorySegment offset) {
        if (clang_indexLoc_getFileLocation == null) {
            clang_indexLoc_getFileLocation = LibclangSymbols.toMethodHandle("clang_indexLoc_getFileLocation", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_indexLoc_getFileLocation"));
        }
        try {
            clang_indexLoc_getFileLocation.invoke(loc, indexFile, file, line, column, offset);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static void clang_indexLoc_getFileLocation(CXIdxLoc loc, Pointer<CXIdxClientFile> indexFile, Pointer<CXFile> file, Pointer<VI32<Integer>> line, Pointer<VI32<Integer>> column, Pointer<VI32<Integer>> offset) {
        clang_indexLoc_getFileLocation(loc.pointer(), indexFile.pointer(), file.pointer(), line.pointer(), column.pointer(), offset.pointer());
    }

    private static MethodHandle clang_indexLoc_getCXSourceLocation;

    private static MemorySegment clang_indexLoc_getCXSourceLocation(SegmentAllocator allocator, MemorySegment loc) {
        if (clang_indexLoc_getCXSourceLocation == null) {
            clang_indexLoc_getCXSourceLocation = LibclangSymbols.toMethodHandle("clang_indexLoc_getCXSourceLocation", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_indexLoc_getCXSourceLocation"));
        }
        try {
            return (MemorySegment) clang_indexLoc_getCXSourceLocation.invoke(allocator, loc);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static CXSourceLocation clang_indexLoc_getCXSourceLocation$CXSourceLocation(SegmentAllocator allocator,CXIdxLoc loc) {
        return new CXSourceLocation(FunctionUtils.makePointer(clang_indexLoc_getCXSourceLocation(allocator,loc.pointer())));
    }

    private static MethodHandle clang_Type_visitFields;

    private static int clang_Type_visitFields(MemorySegment T, MemorySegment visitor, MemorySegment client_data) {
        if (clang_Type_visitFields == null) {
            clang_Type_visitFields = LibclangSymbols.toMethodHandle("clang_Type_visitFields", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new FunctionUtils.SymbolNotFound("clang_Type_visitFields"));
        }
        try {
            return (int) clang_Type_visitFields.invoke(T, visitor, client_data);
        } catch (Throwable e) {
            throw new FunctionUtils.InvokeException(e);
        }
    }
    public static int clang_Type_visitFields$int(CXType T, VPointer<CXFieldVisitor> visitor, CXClientData client_data) {
        return clang_Type_visitFields(T.pointer(), visitor.value(), client_data.value());
    }

}