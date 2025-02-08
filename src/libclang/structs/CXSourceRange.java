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
public final class CXSourceRange implements StructOp<CXSourceRange>, Info<CXSourceRange> {
   public static final int BYTE_SIZE = 24;
   private final MemorySegment ms;
   public static final Operations<CXSourceRange> OPERATIONS = StructOp.makeOperations(CXSourceRange::new, BYTE_SIZE);

   public CXSourceRange(MemorySegment ms) {
       this.ms = ms;
   }

    public CXSourceRange(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXSourceRange> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXSourceRange.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXSourceRange> operator() {
       return new StructOpI<>() {
           @Override
           public CXSourceRange reinterpret() {
               return new CXSourceRange(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXSourceRange> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXSourceRange> getOperations() {
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

    public CXSourceRange ptr_data(ArrayI<? extends PtrI<?>> ptr_data){
        MemoryUtils.memcpy(ms, 0, ptr_data.operator().value(), 0, 16);
        return this;
    }
    public I32 begin_int_data(){
        return new I32(MemoryUtils.getInt(ms, 16));
    }

    public CXSourceRange begin_int_data(I32I<?> begin_int_data){
        MemoryUtils.setInt(ms, 16, begin_int_data.operator().value());
        return this;
    }
    public I32 end_int_data(){
        return new I32(MemoryUtils.getInt(ms, 20));
    }

    public CXSourceRange end_int_data(I32I<?> end_int_data){
        MemoryUtils.setInt(ms, 20, end_int_data.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXSourceRange{" +
                "ptr_data=" + ptr_data() +
                ", begin_int_data=" + begin_int_data() +
                ", end_int_data=" + end_int_data() +
                '}';
    }

}