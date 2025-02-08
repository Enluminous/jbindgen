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
public final class CXSourceLocation implements StructOp<CXSourceLocation>, Info<CXSourceLocation> {
   public static final int BYTE_SIZE = 24;
   private final MemorySegment ms;
   public static final Operations<CXSourceLocation> OPERATIONS = StructOp.makeOperations(CXSourceLocation::new, BYTE_SIZE);

   public CXSourceLocation(MemorySegment ms) {
       this.ms = ms;
   }

    public CXSourceLocation(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXSourceLocation> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXSourceLocation.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXSourceLocation> operator() {
       return new StructOpI<>() {
           @Override
           public CXSourceLocation reinterpret() {
               return new CXSourceLocation(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXSourceLocation> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXSourceLocation> getOperations() {
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

    public CXSourceLocation ptr_data(ArrayI<? extends PtrI<?>> ptr_data){
        MemoryUtils.memcpy(ms, 0, ptr_data.operator().value(), 0, 16);
        return this;
    }
    public I32 int_data(){
        return new I32(MemoryUtils.getInt(ms, 16));
    }

    public CXSourceLocation int_data(I32I<?> int_data){
        MemoryUtils.setInt(ms, 16, int_data.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXSourceLocation{" +
                "ptr_data=" + ptr_data() +
                ", int_data=" + int_data() +
                '}';
    }

}