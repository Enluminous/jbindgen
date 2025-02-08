package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32I;
import libclang.common.I8;
import libclang.common.I8I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructOp;
public final class CXIndexOptions implements StructOp<CXIndexOptions>, Info<CXIndexOptions> {
   public static final int BYTE_SIZE = 24;
   private final MemorySegment ms;
   public static final Operations<CXIndexOptions> OPERATIONS = StructOp.makeOperations(CXIndexOptions::new, BYTE_SIZE);

   public CXIndexOptions(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIndexOptions(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIndexOptions> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIndexOptions.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIndexOptions> operator() {
       return new StructOpI<>() {
           @Override
           public CXIndexOptions reinterpret() {
               return new CXIndexOptions(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIndexOptions> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIndexOptions> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public I32 Size(){
        return new I32(MemoryUtils.getInt(ms, 0));
    }

    public CXIndexOptions Size(I32I<?> Size){
        MemoryUtils.setInt(ms, 0, Size.operator().value());
        return this;
    }
    public I8 ThreadBackgroundPriorityForIndexing(){
        return new I8(MemoryUtils.getByte(ms, 4));
    }

    public CXIndexOptions ThreadBackgroundPriorityForIndexing(I8I<?> ThreadBackgroundPriorityForIndexing){
        MemoryUtils.setByte(ms, 4, ThreadBackgroundPriorityForIndexing.operator().value());
        return this;
    }
    public I8 ThreadBackgroundPriorityForEditing(){
        return new I8(MemoryUtils.getByte(ms, 5));
    }

    public CXIndexOptions ThreadBackgroundPriorityForEditing(I8I<?> ThreadBackgroundPriorityForEditing){
        MemoryUtils.setByte(ms, 5, ThreadBackgroundPriorityForEditing.operator().value());
        return this;
    }
    public I32 ExcludeDeclarationsFromPCH(){
        return new I32(MemoryUtils.getInt(ms, 6));
    }

    public CXIndexOptions ExcludeDeclarationsFromPCH(I32I<?> ExcludeDeclarationsFromPCH){
        MemoryUtils.setInt(ms, 6, ExcludeDeclarationsFromPCH.operator().value());
        return this;
    }
    public I32 DisplayDiagnostics(){
        return new I32(MemoryUtils.getInt(ms, 6));
    }

    public CXIndexOptions DisplayDiagnostics(I32I<?> DisplayDiagnostics){
        MemoryUtils.setInt(ms, 6, DisplayDiagnostics.operator().value());
        return this;
    }
    public I32 StorePreamblesInMemory(){
        return new I32(MemoryUtils.getInt(ms, 6));
    }

    public CXIndexOptions StorePreamblesInMemory(I32I<?> StorePreamblesInMemory){
        MemoryUtils.setInt(ms, 6, StorePreamblesInMemory.operator().value());
        return this;
    }
    public Ptr<I8> PreambleStoragePath(){
        return new Ptr<I8>(MemoryUtils.getAddr(ms, 8), I8.OPERATIONS);
    }

    public CXIndexOptions PreambleStoragePath(PtrI<? extends I8I<?>> PreambleStoragePath){
        MemoryUtils.setAddr(ms, 8, PreambleStoragePath.operator().value());
        return this;
    }
    public Ptr<I8> InvocationEmissionPath(){
        return new Ptr<I8>(MemoryUtils.getAddr(ms, 16), I8.OPERATIONS);
    }

    public CXIndexOptions InvocationEmissionPath(PtrI<? extends I8I<?>> InvocationEmissionPath){
        MemoryUtils.setAddr(ms, 16, InvocationEmissionPath.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIndexOptions{" +
                "Size=" + Size() +
                ", ThreadBackgroundPriorityForIndexing=" + ThreadBackgroundPriorityForIndexing() +
                ", ThreadBackgroundPriorityForEditing=" + ThreadBackgroundPriorityForEditing() +
                ", ExcludeDeclarationsFromPCH=" + ExcludeDeclarationsFromPCH() +
                ", DisplayDiagnostics=" + DisplayDiagnostics() +
                ", StorePreamblesInMemory=" + StorePreamblesInMemory() +
                ", PreambleStoragePath=" + PreambleStoragePath() +
                ", InvocationEmissionPath=" + InvocationEmissionPath() +
                '}';
    }

}