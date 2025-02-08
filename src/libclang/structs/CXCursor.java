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
import libclang.enumerates.CXCursorKind;
public final class CXCursor implements StructOp<CXCursor>, Info<CXCursor> {
   public static final int BYTE_SIZE = 32;
   private final MemorySegment ms;
   public static final Operations<CXCursor> OPERATIONS = StructOp.makeOperations(CXCursor::new, BYTE_SIZE);

   public CXCursor(MemorySegment ms) {
       this.ms = ms;
   }

    public CXCursor(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXCursor> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXCursor.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXCursor> operator() {
       return new StructOpI<>() {
           @Override
           public CXCursor reinterpret() {
               return new CXCursor(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXCursor> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXCursor> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public CXCursorKind kind(){
        return new CXCursorKind(MemoryUtils.getInt(ms, 0));
    }

    public CXCursor kind(I32I<? extends CXCursorKind> kind){
        MemoryUtils.setInt(ms, 0, kind.operator().value());
        return this;
    }
    public I32 xdata(){
        return new I32(MemoryUtils.getInt(ms, 4));
    }

    public CXCursor xdata(I32I<?> xdata){
        MemoryUtils.setInt(ms, 4, xdata.operator().value());
        return this;
    }
    public Array<Ptr<Void>> data(){
        return new Array<Ptr<Void>>(ms.asSlice(8, 24), Ptr.makeOperations(Info.makeOperations()));
    }

    public CXCursor data(ArrayI<? extends PtrI<?>> data){
        MemoryUtils.memcpy(ms, 8, data.operator().value(), 0, 24);
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXCursor{" +
                "kind=" + kind() +
                ", xdata=" + xdata() +
                ", data=" + data() +
                '}';
    }

}