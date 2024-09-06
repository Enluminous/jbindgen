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


public final class CXIdxCXXClassDeclInfo implements Pointer<CXIdxCXXClassDeclInfo> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxCXXClassDeclInfo> list(Pointer<CXIdxCXXClassDeclInfo> ptr) {
        return new NList<>(ptr, CXIdxCXXClassDeclInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxCXXClassDeclInfo> list(Pointer<CXIdxCXXClassDeclInfo> ptr, long length) {
        return new NList<>(ptr, length, CXIdxCXXClassDeclInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxCXXClassDeclInfo> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxCXXClassDeclInfo::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxCXXClassDeclInfo(Pointer<CXIdxCXXClassDeclInfo> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxCXXClassDeclInfo(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxCXXClassDeclInfo reinterpretSize() {
        return new CXIdxCXXClassDeclInfo(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public Pointer<CXIdxDeclInfo> declInfo() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 0));
    }

    public NList<CXIdxDeclInfo> declInfo(long length) {
        return CXIdxDeclInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 0)), length);
    }

    public CXIdxCXXClassDeclInfo declInfo(Pointer<CXIdxDeclInfo> declInfo) {
        ptr.set(ValueLayout.ADDRESS, 0, declInfo.pointer());
        return this;
    }

    public Pointer<Pointer<CXIdxBaseClassInfo>> bases() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 8));
    }

    public CXIdxCXXClassDeclInfo bases(Pointer<Pointer<CXIdxBaseClassInfo>> bases) {
        ptr.set(ValueLayout.ADDRESS, 8, bases.pointer());
        return this;
    }

    public int numBases() {
        return ptr.get(ValueLayout.JAVA_INT, 16);
    }

    public CXIdxCXXClassDeclInfo numBases(int numBases) {
        ptr.set(ValueLayout.JAVA_INT, 16, numBases);
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXIdxCXXClassDeclInfo{ptr=" + ptr;
        return "CXIdxCXXClassDeclInfo{" +
                "declInfo=" + declInfo() +
                "bases=" + bases() +
                "numBases=" + numBases() + "}";
    }
}