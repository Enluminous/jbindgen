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
public final class CXToken implements StructOp<CXToken>, Info<CXToken> {
   public static final int BYTE_SIZE = 24;
   private final MemorySegment ms;
   public static final Operations<CXToken> OPERATIONS = StructOp.makeOperations(CXToken::new, BYTE_SIZE);

   public CXToken(MemorySegment ms) {
       this.ms = ms;
   }

    public CXToken(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXToken> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXToken.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXToken> operator() {
       return new StructOpI<>() {
           @Override
           public CXToken reinterpret() {
               return new CXToken(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXToken> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXToken> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Array<I32> int_data(){
        return new Array<I32>(ms.asSlice(0, 16), I32.OPERATIONS);
    }

    public CXToken int_data(ArrayI<? extends I32I<?>> int_data){
        MemoryUtils.memcpy(ms, 0, int_data.operator().value(), 0, 16);
        return this;
    }
    public Ptr<Void> ptr_data(){
        return new Ptr<Void>(MemoryUtils.getAddr(ms, 16), Info.makeOperations());
    }

    public CXToken ptr_data(PtrI<?> ptr_data){
        MemoryUtils.setAddr(ms, 16, ptr_data.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXToken{" +
                "int_data=" + int_data() +
                ", ptr_data=" + ptr_data() +
                '}';
    }

}