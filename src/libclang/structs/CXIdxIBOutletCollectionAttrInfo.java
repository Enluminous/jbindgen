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


public final class CXIdxIBOutletCollectionAttrInfo implements Pointer<CXIdxIBOutletCollectionAttrInfo> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(72, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxIBOutletCollectionAttrInfo> list(Pointer<CXIdxIBOutletCollectionAttrInfo> ptr) {
        return new NList<>(ptr, CXIdxIBOutletCollectionAttrInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxIBOutletCollectionAttrInfo> list(Pointer<CXIdxIBOutletCollectionAttrInfo> ptr, long length) {
        return new NList<>(ptr, length, CXIdxIBOutletCollectionAttrInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxIBOutletCollectionAttrInfo> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxIBOutletCollectionAttrInfo::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxIBOutletCollectionAttrInfo(Pointer<CXIdxIBOutletCollectionAttrInfo> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxIBOutletCollectionAttrInfo(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxIBOutletCollectionAttrInfo reinterpretSize() {
        return new CXIdxIBOutletCollectionAttrInfo(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public Pointer<CXIdxAttrInfo> attrInfo() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 0));
    }

    public NList<CXIdxAttrInfo> attrInfo(long length) {
        return CXIdxAttrInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 0)), length);
    }

    public CXIdxIBOutletCollectionAttrInfo attrInfo(Pointer<CXIdxAttrInfo> attrInfo) {
        ptr.set(ValueLayout.ADDRESS, 0, attrInfo.pointer());
        return this;
    }

    public Pointer<CXIdxEntityInfo> objcClass() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 8));
    }

    public NList<CXIdxEntityInfo> objcClass(long length) {
        return CXIdxEntityInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 8)), length);
    }

    public CXIdxIBOutletCollectionAttrInfo objcClass(Pointer<CXIdxEntityInfo> objcClass) {
        ptr.set(ValueLayout.ADDRESS, 8, objcClass.pointer());
        return this;
    }

    public CXCursor classCursor() {
        return new CXCursor(FunctionUtils.makePointer(ptr.asSlice(16, 32)));
    }

    public CXIdxIBOutletCollectionAttrInfo classCursor(CXCursor classCursor) {
        MemorySegment.copy(classCursor.pointer(), 0, ptr, 16, Math.min(32, classCursor.pointer().byteSize()));
        return this;
    }

    public CXIdxLoc classLoc() {
        return new CXIdxLoc(FunctionUtils.makePointer(ptr.asSlice(48, 24)));
    }

    public CXIdxIBOutletCollectionAttrInfo classLoc(CXIdxLoc classLoc) {
        MemorySegment.copy(classLoc.pointer(), 0, ptr, 48, Math.min(24, classLoc.pointer().byteSize()));
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXIdxIBOutletCollectionAttrInfo{ptr=" + ptr;
//        return STR."""
//                CXIdxIBOutletCollectionAttrInfo{\
//                attrInfo=\{attrInfo()},\
//                objcClass=\{objcClass()},\
//                classCursor=\{classCursor()},\
//                classLoc=\{classLoc()}}""";
        return "";
    }
}