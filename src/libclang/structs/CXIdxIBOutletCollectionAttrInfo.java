package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructI;
import libclang.common.StructOp;
import libclang.structs.CXCursor;
import libclang.structs.CXIdxAttrInfo;
import libclang.structs.CXIdxEntityInfo;
import libclang.structs.CXIdxLoc;
public final class CXIdxIBOutletCollectionAttrInfo implements StructOp<CXIdxIBOutletCollectionAttrInfo>, Info<CXIdxIBOutletCollectionAttrInfo> {
   public static final int BYTE_SIZE = 72;
   private final MemorySegment ms;
   public static final Operations<CXIdxIBOutletCollectionAttrInfo> OPERATIONS = StructOp.makeOperations(CXIdxIBOutletCollectionAttrInfo::new, BYTE_SIZE);

   public CXIdxIBOutletCollectionAttrInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxIBOutletCollectionAttrInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxIBOutletCollectionAttrInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxIBOutletCollectionAttrInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxIBOutletCollectionAttrInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxIBOutletCollectionAttrInfo reinterpret() {
               return new CXIdxIBOutletCollectionAttrInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxIBOutletCollectionAttrInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxIBOutletCollectionAttrInfo> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Ptr<CXIdxAttrInfo> attrInfo(){
        return new Ptr<CXIdxAttrInfo>(MemoryUtils.getAddr(ms, 0), CXIdxAttrInfo.OPERATIONS);
    }

    public CXIdxIBOutletCollectionAttrInfo attrInfo(PtrI<? extends StructI<? extends CXIdxAttrInfo>> attrInfo){
        MemoryUtils.setAddr(ms, 0, attrInfo.operator().value());
        return this;
    }
    public Ptr<CXIdxEntityInfo> objcClass(){
        return new Ptr<CXIdxEntityInfo>(MemoryUtils.getAddr(ms, 8), CXIdxEntityInfo.OPERATIONS);
    }

    public CXIdxIBOutletCollectionAttrInfo objcClass(PtrI<? extends StructI<? extends CXIdxEntityInfo>> objcClass){
        MemoryUtils.setAddr(ms, 8, objcClass.operator().value());
        return this;
    }
    public CXCursor classCursor(){
        return new CXCursor(ms.asSlice(16, 32));
    }

    public CXIdxIBOutletCollectionAttrInfo classCursor(StructI<? extends CXCursor> classCursor){
        MemoryUtils.memcpy(ms, 16, classCursor.operator().value(), 0, 32);
        return this;
    }
    public CXIdxLoc classLoc(){
        return new CXIdxLoc(ms.asSlice(48, 24));
    }

    public CXIdxIBOutletCollectionAttrInfo classLoc(StructI<? extends CXIdxLoc> classLoc){
        MemoryUtils.memcpy(ms, 48, classLoc.operator().value(), 0, 24);
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxIBOutletCollectionAttrInfo{" +
                "attrInfo=" + attrInfo() +
                ", objcClass=" + objcClass() +
                ", classCursor=" + classCursor() +
                ", classLoc=" + classLoc() +
                '}';
    }

}