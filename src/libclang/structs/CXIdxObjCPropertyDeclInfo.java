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
import libclang.structs.CXIdxDeclInfo;
import libclang.structs.CXIdxEntityInfo;
public final class CXIdxObjCPropertyDeclInfo implements StructOp<CXIdxObjCPropertyDeclInfo>, Info<CXIdxObjCPropertyDeclInfo> {
   public static final int BYTE_SIZE = 24;
   private final MemorySegment ms;
   public static final Operations<CXIdxObjCPropertyDeclInfo> OPERATIONS = StructOp.makeOperations(CXIdxObjCPropertyDeclInfo::new, BYTE_SIZE);

   public CXIdxObjCPropertyDeclInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxObjCPropertyDeclInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxObjCPropertyDeclInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxObjCPropertyDeclInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxObjCPropertyDeclInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxObjCPropertyDeclInfo reinterpret() {
               return new CXIdxObjCPropertyDeclInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxObjCPropertyDeclInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxObjCPropertyDeclInfo> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Ptr<CXIdxDeclInfo> declInfo(){
        return new Ptr<CXIdxDeclInfo>(MemoryUtils.getAddr(ms, 0), CXIdxDeclInfo.OPERATIONS);
    }

    public CXIdxObjCPropertyDeclInfo declInfo(PtrI<? extends StructI<? extends CXIdxDeclInfo>> declInfo){
        MemoryUtils.setAddr(ms, 0, declInfo.operator().value());
        return this;
    }
    public Ptr<CXIdxEntityInfo> getter(){
        return new Ptr<CXIdxEntityInfo>(MemoryUtils.getAddr(ms, 8), CXIdxEntityInfo.OPERATIONS);
    }

    public CXIdxObjCPropertyDeclInfo getter(PtrI<? extends StructI<? extends CXIdxEntityInfo>> getter){
        MemoryUtils.setAddr(ms, 8, getter.operator().value());
        return this;
    }
    public Ptr<CXIdxEntityInfo> setter(){
        return new Ptr<CXIdxEntityInfo>(MemoryUtils.getAddr(ms, 16), CXIdxEntityInfo.OPERATIONS);
    }

    public CXIdxObjCPropertyDeclInfo setter(PtrI<? extends StructI<? extends CXIdxEntityInfo>> setter){
        MemoryUtils.setAddr(ms, 16, setter.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxObjCPropertyDeclInfo{" +
                "declInfo=" + declInfo() +
                ", getter=" + getter() +
                ", setter=" + setter() +
                '}';
    }

}