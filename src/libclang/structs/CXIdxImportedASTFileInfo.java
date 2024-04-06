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


public final class CXIdxImportedASTFileInfo implements Pointer<CXIdxImportedASTFileInfo> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(48, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxImportedASTFileInfo> list(Pointer<CXIdxImportedASTFileInfo> ptr) {
        return new NList<>(ptr, CXIdxImportedASTFileInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxImportedASTFileInfo> list(Pointer<CXIdxImportedASTFileInfo> ptr, long length) {
        return new NList<>(ptr, length, CXIdxImportedASTFileInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxImportedASTFileInfo> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxImportedASTFileInfo::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxImportedASTFileInfo(Pointer<CXIdxImportedASTFileInfo> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxImportedASTFileInfo(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxImportedASTFileInfo reinterpretSize() {
        return new CXIdxImportedASTFileInfo(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public CXFile file() {
        return new CXFile(ptr.get(ValueLayout.ADDRESS, 0));
    }

    public CXIdxImportedASTFileInfo file(CXFile file) {
        ptr.set(ValueLayout.ADDRESS, 0, file.value());
        return this;
    }

    public CXModule module() {
        return new CXModule(ptr.get(ValueLayout.ADDRESS, 8));
    }

    public CXIdxImportedASTFileInfo module(CXModule module) {
        ptr.set(ValueLayout.ADDRESS, 8, module.value());
        return this;
    }

    public CXIdxLoc loc() {
        return new CXIdxLoc(FunctionUtils.makePointer(ptr.asSlice(16, 24)));
    }

    public CXIdxImportedASTFileInfo loc(CXIdxLoc loc) {
        MemorySegment.copy(loc.pointer(), 0,ptr, 16, Math.min(24,loc.pointer().byteSize()));
        return this;
    }

    public int isImplicit() {
        return ptr.get(ValueLayout.JAVA_INT, 40);
    }

    public CXIdxImportedASTFileInfo isImplicit(int isImplicit) {
        ptr.set(ValueLayout.JAVA_INT, 40, isImplicit);
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXIdxImportedASTFileInfo{ptr=\{ptr}}";
        return STR."""
                CXIdxImportedASTFileInfo{\
                file=\{file()},\
                module=\{module()},\
                loc=\{loc()},\
                isImplicit=\{isImplicit()}}""";
    }
}