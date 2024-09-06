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


public final class CXIdxObjCCategoryDeclInfo implements Pointer<CXIdxObjCCategoryDeclInfo> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(80, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxObjCCategoryDeclInfo> list(Pointer<CXIdxObjCCategoryDeclInfo> ptr) {
        return new NList<>(ptr, CXIdxObjCCategoryDeclInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxObjCCategoryDeclInfo> list(Pointer<CXIdxObjCCategoryDeclInfo> ptr, long length) {
        return new NList<>(ptr, length, CXIdxObjCCategoryDeclInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxObjCCategoryDeclInfo> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxObjCCategoryDeclInfo::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxObjCCategoryDeclInfo(Pointer<CXIdxObjCCategoryDeclInfo> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxObjCCategoryDeclInfo(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxObjCCategoryDeclInfo reinterpretSize() {
        return new CXIdxObjCCategoryDeclInfo(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
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

    public CXIdxObjCCategoryDeclInfo containerInfo(Pointer<CXIdxObjCContainerDeclInfo> containerInfo) {
        ptr.set(ValueLayout.ADDRESS, 0, containerInfo.pointer());
        return this;
    }

    public Pointer<CXIdxEntityInfo> objcClass() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 8));
    }

    public NList<CXIdxEntityInfo> objcClass(long length) {
        return CXIdxEntityInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 8)), length);
    }

    public CXIdxObjCCategoryDeclInfo objcClass(Pointer<CXIdxEntityInfo> objcClass) {
        ptr.set(ValueLayout.ADDRESS, 8, objcClass.pointer());
        return this;
    }

    public CXCursor classCursor() {
        return new CXCursor(FunctionUtils.makePointer(ptr.asSlice(16, 32)));
    }

    public CXIdxObjCCategoryDeclInfo classCursor(CXCursor classCursor) {
        MemorySegment.copy(classCursor.pointer(), 0, ptr, 16, Math.min(32, classCursor.pointer().byteSize()));
        return this;
    }

    public CXIdxLoc classLoc() {
        return new CXIdxLoc(FunctionUtils.makePointer(ptr.asSlice(48, 24)));
    }

    public CXIdxObjCCategoryDeclInfo classLoc(CXIdxLoc classLoc) {
        MemorySegment.copy(classLoc.pointer(), 0, ptr, 48, Math.min(24, classLoc.pointer().byteSize()));
        return this;
    }

    public Pointer<CXIdxObjCProtocolRefListInfo> protocols() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 72));
    }

    public NList<CXIdxObjCProtocolRefListInfo> protocols(long length) {
        return CXIdxObjCProtocolRefListInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 72)), length);
    }

    public CXIdxObjCCategoryDeclInfo protocols(Pointer<CXIdxObjCProtocolRefListInfo> protocols) {
        ptr.set(ValueLayout.ADDRESS, 72, protocols.pointer());
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXIdxObjCCategoryDeclInfo{ptr=" + ptr + "}";
//        return STR."""
//                CXIdxObjCCategoryDeclInfo{\
//                containerInfo=\{containerInfo()},\
//                objcClass=\{objcClass()},\
//                classCursor=\{classCursor()},\
//                classLoc=\{classLoc()},\
//                protocols=\{protocols()}}""";
        return "";
    }
}