package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I32I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructI;
import libclang.common.StructOp;
import libclang.enumerates.CXIdxObjCContainerKind;
import libclang.structs.CXIdxDeclInfo;
public final class CXIdxObjCContainerDeclInfo implements StructOp<CXIdxObjCContainerDeclInfo>, Info<CXIdxObjCContainerDeclInfo> {
   public static final int BYTE_SIZE = 16;
   private final MemorySegment ms;
   public static final Operations<CXIdxObjCContainerDeclInfo> OPERATIONS = StructOp.makeOperations(CXIdxObjCContainerDeclInfo::new, BYTE_SIZE);

   public CXIdxObjCContainerDeclInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxObjCContainerDeclInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxObjCContainerDeclInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxObjCContainerDeclInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxObjCContainerDeclInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxObjCContainerDeclInfo reinterpret() {
               return new CXIdxObjCContainerDeclInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxObjCContainerDeclInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxObjCContainerDeclInfo> getOperations() {
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

    public CXIdxObjCContainerDeclInfo declInfo(PtrI<? extends StructI<? extends CXIdxDeclInfo>> declInfo){
        MemoryUtils.setAddr(ms, 0, declInfo.operator().value());
        return this;
    }
    public CXIdxObjCContainerKind kind(){
        return new CXIdxObjCContainerKind(MemoryUtils.getInt(ms, 8));
    }

    public CXIdxObjCContainerDeclInfo kind(I32I<? extends CXIdxObjCContainerKind> kind){
        MemoryUtils.setInt(ms, 8, kind.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxObjCContainerDeclInfo{" +
                "declInfo=" + declInfo() +
                ", kind=" + kind() +
                '}';
    }

}