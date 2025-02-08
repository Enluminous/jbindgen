package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.ArrayI;
import libclang.common.I32;
import libclang.common.I32I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructOp;
public final class CXIdxLoc implements StructOp<CXIdxLoc>, Info<CXIdxLoc> {
   public static final int BYTE_SIZE = 24;
   private final MemorySegment ms;
   public static final Operations<CXIdxLoc> OPERATIONS = StructOp.makeOperations(CXIdxLoc::new, BYTE_SIZE);

   public CXIdxLoc(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxLoc(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxLoc> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxLoc.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxLoc> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxLoc reinterpret() {
               return new CXIdxLoc(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxLoc> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxLoc> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Array<Ptr<Void>> ptr_data(){
        return new Array<Ptr<Void>>(ms.asSlice(0, 16), Ptr.makeOperations(Info.makeOperations()));
    }

    public CXIdxLoc ptr_data(ArrayI<? extends PtrI<?>> ptr_data){
        MemoryUtils.memcpy(ms, 0, ptr_data.operator().value(), 0, 16);
        return this;
    }
    public I32 int_data(){
        return new I32(MemoryUtils.getInt(ms, 16));
    }

    public CXIdxLoc int_data(I32I<?> int_data){
        MemoryUtils.setInt(ms, 16, int_data.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxLoc{" +
                "ptr_data=" + ptr_data() +
                ", int_data=" + int_data() +
                '}';
    }

}