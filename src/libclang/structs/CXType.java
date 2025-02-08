package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.ArrayI;
import libclang.common.I32I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructOp;
import libclang.enumerates.CXTypeKind;
public final class CXType implements StructOp<CXType>, Info<CXType> {
   public static final int BYTE_SIZE = 24;
   private final MemorySegment ms;
   public static final Operations<CXType> OPERATIONS = StructOp.makeOperations(CXType::new, BYTE_SIZE);

   public CXType(MemorySegment ms) {
       this.ms = ms;
   }

    public CXType(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXType> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXType.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXType> operator() {
       return new StructOpI<>() {
           @Override
           public CXType reinterpret() {
               return new CXType(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXType> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXType> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public CXTypeKind kind(){
        return new CXTypeKind(MemoryUtils.getInt(ms, 0));
    }

    public CXType kind(I32I<? extends CXTypeKind> kind){
        MemoryUtils.setInt(ms, 0, kind.operator().value());
        return this;
    }
    public Array<Ptr<Void>> data(){
        return new Array<Ptr<Void>>(ms.asSlice(8, 16), Ptr.makeOperations(Info.makeOperations()));
    }

    public CXType data(ArrayI<? extends PtrI<?>> data){
        MemoryUtils.memcpy(ms, 8, data.operator().value(), 0, 16);
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXType{" +
                "kind=" + kind() +
                ", data=" + data() +
                '}';
    }

}