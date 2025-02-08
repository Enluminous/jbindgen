package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructI;
import libclang.common.StructOp;
import libclang.structs.CXIdxBaseClassInfo;
import libclang.structs.CXIdxDeclInfo;
public final class CXIdxCXXClassDeclInfo implements StructOp<CXIdxCXXClassDeclInfo>, Info<CXIdxCXXClassDeclInfo> {
   public static final int BYTE_SIZE = 24;
   private final MemorySegment ms;
   public static final Operations<CXIdxCXXClassDeclInfo> OPERATIONS = StructOp.makeOperations(CXIdxCXXClassDeclInfo::new, BYTE_SIZE);

   public CXIdxCXXClassDeclInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxCXXClassDeclInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxCXXClassDeclInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxCXXClassDeclInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxCXXClassDeclInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxCXXClassDeclInfo reinterpret() {
               return new CXIdxCXXClassDeclInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxCXXClassDeclInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxCXXClassDeclInfo> getOperations() {
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

    public CXIdxCXXClassDeclInfo declInfo(PtrI<? extends StructI<? extends CXIdxDeclInfo>> declInfo){
        MemoryUtils.setAddr(ms, 0, declInfo.operator().value());
        return this;
    }
    public Ptr<Ptr<CXIdxBaseClassInfo>> bases(){
        return new Ptr<Ptr<CXIdxBaseClassInfo>>(MemoryUtils.getAddr(ms, 8), Ptr.makeOperations(CXIdxBaseClassInfo.OPERATIONS));
    }

    public CXIdxCXXClassDeclInfo bases(PtrI<? extends PtrI<? extends StructI<? extends CXIdxBaseClassInfo>>> bases){
        MemoryUtils.setAddr(ms, 8, bases.operator().value());
        return this;
    }
    public I32 numBases(){
        return new I32(MemoryUtils.getInt(ms, 16));
    }

    public CXIdxCXXClassDeclInfo numBases(I32I<?> numBases){
        MemoryUtils.setInt(ms, 16, numBases.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxCXXClassDeclInfo{" +
                "declInfo=" + declInfo() +
                ", bases=" + bases() +
                ", numBases=" + numBases() +
                '}';
    }

}