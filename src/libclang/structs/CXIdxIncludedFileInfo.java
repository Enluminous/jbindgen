package libclang.structs;


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
import java.util.function.Consumer;


public final class CXIdxIncludedFileInfo implements Pointer<CXIdxIncludedFileInfo> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(56, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxIncludedFileInfo> list(Pointer<CXIdxIncludedFileInfo> ptr) {
        return new NList<>(ptr, CXIdxIncludedFileInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxIncludedFileInfo> list(Pointer<CXIdxIncludedFileInfo> ptr, long length) {
        return new NList<>(ptr, length, CXIdxIncludedFileInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxIncludedFileInfo> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxIncludedFileInfo::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxIncludedFileInfo(Pointer<CXIdxIncludedFileInfo> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxIncludedFileInfo(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxIncludedFileInfo reinterpretSize() {
        return new CXIdxIncludedFileInfo(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public CXIdxLoc hashLoc() {
        return new CXIdxLoc(FunctionUtils.makePointer(ptr.asSlice(0, 24)));
    }

    public CXIdxIncludedFileInfo hashLoc(CXIdxLoc hashLoc) {
        MemorySegment.copy(hashLoc.pointer(), 0,ptr, 0, Math.min(24,hashLoc.pointer().byteSize()));
        return this;
    }

    public VI8List<VI8<Byte>> filename(long length) {
        return VI8.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,24)), length);
    }

    public Pointer<VI8<Byte>> filename() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,24));
    }

    public CXIdxIncludedFileInfo filename(Pointer<VI8<Byte>> filename) {
        ptr.set(ValueLayout.ADDRESS, 24, filename.pointer());
        return this;
    }

    public CXIdxIncludedFileInfo filename(NI8 filename) {
        ptr.set(ValueLayout.ADDRESS, 24, filename.pointer());
        return this;
    }

    public CXFile file() {
        return new CXFile(ptr.get(ValueLayout.ADDRESS, 32));
    }

    public CXIdxIncludedFileInfo file(CXFile file) {
        ptr.set(ValueLayout.ADDRESS, 32, file.value());
        return this;
    }

    public int isImport() {
        return ptr.get(ValueLayout.JAVA_INT, 40);
    }

    public CXIdxIncludedFileInfo isImport(int isImport) {
        ptr.set(ValueLayout.JAVA_INT, 40, isImport);
        return this;
    }

    public int isAngled() {
        return ptr.get(ValueLayout.JAVA_INT, 44);
    }

    public CXIdxIncludedFileInfo isAngled(int isAngled) {
        ptr.set(ValueLayout.JAVA_INT, 44, isAngled);
        return this;
    }

    public int isModuleImport() {
        return ptr.get(ValueLayout.JAVA_INT, 48);
    }

    public CXIdxIncludedFileInfo isModuleImport(int isModuleImport) {
        ptr.set(ValueLayout.JAVA_INT, 48, isModuleImport);
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXIdxIncludedFileInfo{ptr=\{ptr}}";
        return STR."""
                CXIdxIncludedFileInfo{\
                hashLoc=\{hashLoc()},\
                filename=\{filename()},\
                file=\{file()},\
                isImport=\{isImport()},\
                isAngled=\{isAngled()},\
                isModuleImport=\{isModuleImport()}}""";
    }
}