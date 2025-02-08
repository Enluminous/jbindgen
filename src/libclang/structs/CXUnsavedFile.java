package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I64;
import libclang.common.I64I;
import libclang.common.I8;
import libclang.common.I8I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructOp;
public final class CXUnsavedFile implements StructOp<CXUnsavedFile>, Info<CXUnsavedFile> {
   public static final int BYTE_SIZE = 24;
   private final MemorySegment ms;
   public static final Operations<CXUnsavedFile> OPERATIONS = StructOp.makeOperations(CXUnsavedFile::new, BYTE_SIZE);

   public CXUnsavedFile(MemorySegment ms) {
       this.ms = ms;
   }

    public CXUnsavedFile(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXUnsavedFile> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXUnsavedFile.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXUnsavedFile> operator() {
       return new StructOpI<>() {
           @Override
           public CXUnsavedFile reinterpret() {
               return new CXUnsavedFile(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXUnsavedFile> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXUnsavedFile> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Ptr<I8> Filename(){
        return new Ptr<I8>(MemoryUtils.getAddr(ms, 0), I8.OPERATIONS);
    }

    public CXUnsavedFile Filename(PtrI<? extends I8I<?>> Filename){
        MemoryUtils.setAddr(ms, 0, Filename.operator().value());
        return this;
    }
    public Ptr<I8> Contents(){
        return new Ptr<I8>(MemoryUtils.getAddr(ms, 8), I8.OPERATIONS);
    }

    public CXUnsavedFile Contents(PtrI<? extends I8I<?>> Contents){
        MemoryUtils.setAddr(ms, 8, Contents.operator().value());
        return this;
    }
    public I64 Length(){
        return new I64(MemoryUtils.getLong(ms, 16));
    }

    public CXUnsavedFile Length(I64I<?> Length){
        MemoryUtils.setLong(ms, 16, Length.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXUnsavedFile{" +
                "Filename=" + Filename() +
                ", Contents=" + Contents() +
                ", Length=" + Length() +
                '}';
    }

}