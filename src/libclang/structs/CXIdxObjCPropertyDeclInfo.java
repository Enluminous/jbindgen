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


public final class CXIdxObjCPropertyDeclInfo implements Pointer<CXIdxObjCPropertyDeclInfo> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxObjCPropertyDeclInfo> list(Pointer<CXIdxObjCPropertyDeclInfo> ptr) {
        return new NList<>(ptr, CXIdxObjCPropertyDeclInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxObjCPropertyDeclInfo> list(Pointer<CXIdxObjCPropertyDeclInfo> ptr, long length) {
        return new NList<>(ptr, length, CXIdxObjCPropertyDeclInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxObjCPropertyDeclInfo> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxObjCPropertyDeclInfo::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxObjCPropertyDeclInfo(Pointer<CXIdxObjCPropertyDeclInfo> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxObjCPropertyDeclInfo(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxObjCPropertyDeclInfo reinterpretSize() {
        return new CXIdxObjCPropertyDeclInfo(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
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

    public CXIdxObjCPropertyDeclInfo declInfo(Pointer<CXIdxDeclInfo> declInfo) {
        ptr.set(ValueLayout.ADDRESS, 0, declInfo.pointer());
        return this;
    }

    public Pointer<CXIdxEntityInfo> getter() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 8));
    }

    public NList<CXIdxEntityInfo> getter(long length) {
        return CXIdxEntityInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 8)), length);
    }

    public CXIdxObjCPropertyDeclInfo getter(Pointer<CXIdxEntityInfo> getter) {
        ptr.set(ValueLayout.ADDRESS, 8, getter.pointer());
        return this;
    }

    public Pointer<CXIdxEntityInfo> setter() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 16));
    }

    public NList<CXIdxEntityInfo> setter(long length) {
        return CXIdxEntityInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 16)), length);
    }

    public CXIdxObjCPropertyDeclInfo setter(Pointer<CXIdxEntityInfo> setter) {
        ptr.set(ValueLayout.ADDRESS, 16, setter.pointer());
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXIdxObjCPropertyDeclInfo{ptr=" + ptr;
//        return STR."""
//                CXIdxObjCPropertyDeclInfo{\
//                declInfo=\{declInfo()},\
//                getter=\{getter()},\
//                setter=\{setter()}}""";
        return "";
    }
}