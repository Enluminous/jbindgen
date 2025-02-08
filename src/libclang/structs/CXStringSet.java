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
import libclang.structs.CXString;
public final class CXStringSet implements StructOp<CXStringSet>, Info<CXStringSet> {
   public static final int BYTE_SIZE = 16;
   private final MemorySegment ms;
   public static final Operations<CXStringSet> OPERATIONS = StructOp.makeOperations(CXStringSet::new, BYTE_SIZE);

   public CXStringSet(MemorySegment ms) {
       this.ms = ms;
   }

    public CXStringSet(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXStringSet> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXStringSet.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXStringSet> operator() {
       return new StructOpI<>() {
           @Override
           public CXStringSet reinterpret() {
               return new CXStringSet(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXStringSet> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXStringSet> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Ptr<CXString> Strings(){
        return new Ptr<CXString>(MemoryUtils.getAddr(ms, 0), CXString.OPERATIONS);
    }

    public CXStringSet Strings(PtrI<? extends StructI<? extends CXString>> Strings){
        MemoryUtils.setAddr(ms, 0, Strings.operator().value());
        return this;
    }
    public I32 Count(){
        return new I32(MemoryUtils.getInt(ms, 8));
    }

    public CXStringSet Count(I32I<?> Count){
        MemoryUtils.setInt(ms, 8, Count.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXStringSet{" +
                "Strings=" + Strings() +
                ", Count=" + Count() +
                '}';
    }

}