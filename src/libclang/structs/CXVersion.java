package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.StructOp;
public final class CXVersion implements StructOp<CXVersion>, Info<CXVersion> {
   public static final int BYTE_SIZE = 12;
   private final MemorySegment ms;
   public static final Operations<CXVersion> OPERATIONS = StructOp.makeOperations(CXVersion::new, BYTE_SIZE);

   public CXVersion(MemorySegment ms) {
       this.ms = ms;
   }

    public CXVersion(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXVersion> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXVersion.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXVersion> operator() {
       return new StructOpI<>() {
           @Override
           public CXVersion reinterpret() {
               return new CXVersion(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXVersion> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXVersion> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public I32 Major(){
        return new I32(MemoryUtils.getInt(ms, 0));
    }

    public CXVersion Major(I32I<?> Major){
        MemoryUtils.setInt(ms, 0, Major.operator().value());
        return this;
    }
    public I32 Minor(){
        return new I32(MemoryUtils.getInt(ms, 4));
    }

    public CXVersion Minor(I32I<?> Minor){
        MemoryUtils.setInt(ms, 4, Minor.operator().value());
        return this;
    }
    public I32 Subminor(){
        return new I32(MemoryUtils.getInt(ms, 8));
    }

    public CXVersion Subminor(I32I<?> Subminor){
        MemoryUtils.setInt(ms, 8, Subminor.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXVersion{" +
                "Major=" + Major() +
                ", Minor=" + Minor() +
                ", Subminor=" + Subminor() +
                '}';
    }

}