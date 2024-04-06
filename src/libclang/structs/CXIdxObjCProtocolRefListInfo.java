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


public final class CXIdxObjCProtocolRefListInfo implements Pointer<CXIdxObjCProtocolRefListInfo> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxObjCProtocolRefListInfo> list(Pointer<CXIdxObjCProtocolRefListInfo> ptr) {
        return new NList<>(ptr, CXIdxObjCProtocolRefListInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxObjCProtocolRefListInfo> list(Pointer<CXIdxObjCProtocolRefListInfo> ptr, long length) {
        return new NList<>(ptr, length, CXIdxObjCProtocolRefListInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxObjCProtocolRefListInfo> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxObjCProtocolRefListInfo::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxObjCProtocolRefListInfo(Pointer<CXIdxObjCProtocolRefListInfo> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxObjCProtocolRefListInfo(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxObjCProtocolRefListInfo reinterpretSize() {
        return new CXIdxObjCProtocolRefListInfo(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public Pointer<Pointer<CXIdxObjCProtocolRefInfo>> protocols() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,0));
    }

    public CXIdxObjCProtocolRefListInfo protocols(Pointer<Pointer<CXIdxObjCProtocolRefInfo>> protocols) {
        ptr.set(ValueLayout.ADDRESS, 0, protocols.pointer());
        return this;
    }

    public int numProtocols() {
        return ptr.get(ValueLayout.JAVA_INT, 8);
    }

    public CXIdxObjCProtocolRefListInfo numProtocols(int numProtocols) {
        ptr.set(ValueLayout.JAVA_INT, 8, numProtocols);
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXIdxObjCProtocolRefListInfo{ptr=\{ptr}}";
        return STR."""
                CXIdxObjCProtocolRefListInfo{\
                protocols=\{protocols()},\
                numProtocols=\{numProtocols()}}""";
    }
}