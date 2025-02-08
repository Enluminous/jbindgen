package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.ArrayI;
import libclang.common.I64;
import libclang.common.I64I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.StructOp;
public final class CXFileUniqueID implements StructOp<CXFileUniqueID>, Info<CXFileUniqueID> {
   public static final int BYTE_SIZE = 24;
   private final MemorySegment ms;
   public static final Operations<CXFileUniqueID> OPERATIONS = StructOp.makeOperations(CXFileUniqueID::new, BYTE_SIZE);

   public CXFileUniqueID(MemorySegment ms) {
       this.ms = ms;
   }

    public CXFileUniqueID(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXFileUniqueID> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXFileUniqueID.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXFileUniqueID> operator() {
       return new StructOpI<>() {
           @Override
           public CXFileUniqueID reinterpret() {
               return new CXFileUniqueID(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXFileUniqueID> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXFileUniqueID> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Array<I64> data(){
        return new Array<I64>(ms.asSlice(0, 24), I64.OPERATIONS);
    }

    public CXFileUniqueID data(ArrayI<? extends I64I<?>> data){
        MemoryUtils.memcpy(ms, 0, data.operator().value(), 0, 24);
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXFileUniqueID{" +
                "data=" + data() +
                '}';
    }

}