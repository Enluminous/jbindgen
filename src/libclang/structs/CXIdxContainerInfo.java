package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.StructI;
import libclang.common.StructOp;
import libclang.structs.CXCursor;
public final class CXIdxContainerInfo implements StructOp<CXIdxContainerInfo>, Info<CXIdxContainerInfo> {
   public static final int BYTE_SIZE = 32;
   private final MemorySegment ms;
   public static final Operations<CXIdxContainerInfo> OPERATIONS = StructOp.makeOperations(CXIdxContainerInfo::new, BYTE_SIZE);

   public CXIdxContainerInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxContainerInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxContainerInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxContainerInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxContainerInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxContainerInfo reinterpret() {
               return new CXIdxContainerInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxContainerInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxContainerInfo> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public CXCursor cursor(){
        return new CXCursor(ms.asSlice(0, 32));
    }

    public CXIdxContainerInfo cursor(StructI<? extends CXCursor> cursor){
        MemoryUtils.memcpy(ms, 0, cursor.operator().value(), 0, 32);
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxContainerInfo{" +
                "cursor=" + cursor() +
                '}';
    }

}