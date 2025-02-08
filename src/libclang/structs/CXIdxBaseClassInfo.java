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
import libclang.structs.CXIdxEntityInfo;
import libclang.structs.CXIdxLoc;
public final class CXIdxBaseClassInfo implements StructOp<CXIdxBaseClassInfo>, Info<CXIdxBaseClassInfo> {
   public static final int BYTE_SIZE = 64;
   private final MemorySegment ms;
   public static final Operations<CXIdxBaseClassInfo> OPERATIONS = StructOp.makeOperations(CXIdxBaseClassInfo::new, BYTE_SIZE);

   public CXIdxBaseClassInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxBaseClassInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxBaseClassInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxBaseClassInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxBaseClassInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxBaseClassInfo reinterpret() {
               return new CXIdxBaseClassInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxBaseClassInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxBaseClassInfo> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Ptr<CXIdxEntityInfo> base(){
        return new Ptr<CXIdxEntityInfo>(MemoryUtils.getAddr(ms, 0), CXIdxEntityInfo.OPERATIONS);
    }

    public CXIdxBaseClassInfo base(PtrI<? extends StructI<? extends CXIdxEntityInfo>> base){
        MemoryUtils.setAddr(ms, 0, base.operator().value());
        return this;
    }
    public CXCursor cursor(){
        return new CXCursor(ms.asSlice(8, 32));
    }

    public CXIdxBaseClassInfo cursor(StructI<? extends CXCursor> cursor){
        MemoryUtils.memcpy(ms, 8, cursor.operator().value(), 0, 32);
        return this;
    }
    public CXIdxLoc loc(){
        return new CXIdxLoc(ms.asSlice(40, 24));
    }

    public CXIdxBaseClassInfo loc(StructI<? extends CXIdxLoc> loc){
        MemoryUtils.memcpy(ms, 40, loc.operator().value(), 0, 24);
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxBaseClassInfo{" +
                "base=" + base() +
                ", cursor=" + cursor() +
                ", loc=" + loc() +
                '}';
    }

}