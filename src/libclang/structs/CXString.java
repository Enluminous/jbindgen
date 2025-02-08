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
import libclang.common.StructOp;
public final class CXString implements StructOp<CXString>, Info<CXString> {
   public static final int BYTE_SIZE = 16;
   private final MemorySegment ms;
   public static final Operations<CXString> OPERATIONS = StructOp.makeOperations(CXString::new, BYTE_SIZE);

   public CXString(MemorySegment ms) {
       this.ms = ms;
   }

    public CXString(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXString> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXString.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXString> operator() {
       return new StructOpI<>() {
           @Override
           public CXString reinterpret() {
               return new CXString(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXString> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXString> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Ptr<Void> data(){
        return new Ptr<Void>(MemoryUtils.getAddr(ms, 0), Info.makeOperations());
    }

    public CXString data(PtrI<?> data){
        MemoryUtils.setAddr(ms, 0, data.operator().value());
        return this;
    }
    public I32 private_flags(){
        return new I32(MemoryUtils.getInt(ms, 8));
    }

    public CXString private_flags(I32I<?> private_flags){
        MemoryUtils.setInt(ms, 8, private_flags.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXString{" +
                "data=" + data() +
                ", private_flags=" + private_flags() +
                '}';
    }

}