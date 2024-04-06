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


public final class CXIdxObjCContainerDeclInfo implements Pointer<CXIdxObjCContainerDeclInfo> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxObjCContainerDeclInfo> list(Pointer<CXIdxObjCContainerDeclInfo> ptr) {
        return new NList<>(ptr, CXIdxObjCContainerDeclInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxObjCContainerDeclInfo> list(Pointer<CXIdxObjCContainerDeclInfo> ptr, long length) {
        return new NList<>(ptr, length, CXIdxObjCContainerDeclInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxObjCContainerDeclInfo> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxObjCContainerDeclInfo::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxObjCContainerDeclInfo(Pointer<CXIdxObjCContainerDeclInfo> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxObjCContainerDeclInfo(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxObjCContainerDeclInfo reinterpretSize() {
        return new CXIdxObjCContainerDeclInfo(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public Pointer<CXIdxDeclInfo> declInfo() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,0));
    }

    public NList<CXIdxDeclInfo> declInfo(long length) {
        return CXIdxDeclInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,0)), length);
    }

    public CXIdxObjCContainerDeclInfo declInfo(Pointer<CXIdxDeclInfo> declInfo) {
        ptr.set(ValueLayout.ADDRESS, 0, declInfo.pointer());
        return this;
    }

    public CXIdxObjCContainerKind kind() {
        return new CXIdxObjCContainerKind(FunctionUtils.makePointer(ptr.asSlice(8,4)));
    }

    public CXIdxObjCContainerDeclInfo kind(CXIdxObjCContainerKind kind) {
        ptr.set(ValueLayout.JAVA_INT, 8, kind.value());
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXIdxObjCContainerDeclInfo{ptr=\{ptr}}";
        return STR."""
                CXIdxObjCContainerDeclInfo{\
                declInfo=\{declInfo()},\
                kind=\{kind()}}""";
    }
}