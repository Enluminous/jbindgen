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


public final class CXIdxObjCInterfaceDeclInfo implements Pointer<CXIdxObjCInterfaceDeclInfo> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxObjCInterfaceDeclInfo> list(Pointer<CXIdxObjCInterfaceDeclInfo> ptr) {
        return new NList<>(ptr, CXIdxObjCInterfaceDeclInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxObjCInterfaceDeclInfo> list(Pointer<CXIdxObjCInterfaceDeclInfo> ptr, long length) {
        return new NList<>(ptr, length, CXIdxObjCInterfaceDeclInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxObjCInterfaceDeclInfo> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxObjCInterfaceDeclInfo::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxObjCInterfaceDeclInfo(Pointer<CXIdxObjCInterfaceDeclInfo> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxObjCInterfaceDeclInfo(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxObjCInterfaceDeclInfo reinterpretSize() {
        return new CXIdxObjCInterfaceDeclInfo(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public Pointer<CXIdxObjCContainerDeclInfo> containerInfo() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 0));
    }

    public NList<CXIdxObjCContainerDeclInfo> containerInfo(long length) {
        return CXIdxObjCContainerDeclInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 0)), length);
    }

    public CXIdxObjCInterfaceDeclInfo containerInfo(Pointer<CXIdxObjCContainerDeclInfo> containerInfo) {
        ptr.set(ValueLayout.ADDRESS, 0, containerInfo.pointer());
        return this;
    }

    public Pointer<CXIdxBaseClassInfo> superInfo() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 8));
    }

    public NList<CXIdxBaseClassInfo> superInfo(long length) {
        return CXIdxBaseClassInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 8)), length);
    }

    public CXIdxObjCInterfaceDeclInfo superInfo(Pointer<CXIdxBaseClassInfo> superInfo) {
        ptr.set(ValueLayout.ADDRESS, 8, superInfo.pointer());
        return this;
    }

    public Pointer<CXIdxObjCProtocolRefListInfo> protocols() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 16));
    }

    public NList<CXIdxObjCProtocolRefListInfo> protocols(long length) {
        return CXIdxObjCProtocolRefListInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 16)), length);
    }

    public CXIdxObjCInterfaceDeclInfo protocols(Pointer<CXIdxObjCProtocolRefListInfo> protocols) {
        ptr.set(ValueLayout.ADDRESS, 16, protocols.pointer());
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXIdxObjCInterfaceDeclInfo{ptr=" + ptr + "}";
//        return STR."""
//                CXIdxObjCInterfaceDeclInfo{\
//                containerInfo=\{containerInfo()},\
//                superInfo=\{superInfo()},\
//                protocols=\{protocols()}}""";
        return "";
    }
}