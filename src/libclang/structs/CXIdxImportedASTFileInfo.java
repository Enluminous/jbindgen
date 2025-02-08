package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.StructI;
import libclang.common.StructOp;
import libclang.structs.CXIdxLoc;
import libclang.values.CXFile;
import libclang.values.CXModule;
public final class CXIdxImportedASTFileInfo implements StructOp<CXIdxImportedASTFileInfo>, Info<CXIdxImportedASTFileInfo> {
   public static final int BYTE_SIZE = 48;
   private final MemorySegment ms;
   public static final Operations<CXIdxImportedASTFileInfo> OPERATIONS = StructOp.makeOperations(CXIdxImportedASTFileInfo::new, BYTE_SIZE);

   public CXIdxImportedASTFileInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxImportedASTFileInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxImportedASTFileInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxImportedASTFileInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxImportedASTFileInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxImportedASTFileInfo reinterpret() {
               return new CXIdxImportedASTFileInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxImportedASTFileInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxImportedASTFileInfo> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public CXFile file(){
        return new CXFile(MemoryUtils.getAddr(ms, 0));
    }

    public CXIdxImportedASTFileInfo file(CXFile file){
        MemoryUtils.setAddr(ms, 0, file.operator().value());
        return this;
    }
    public CXModule module(){
        return new CXModule(MemoryUtils.getAddr(ms, 8));
    }

    public CXIdxImportedASTFileInfo module(CXModule module){
        MemoryUtils.setAddr(ms, 8, module.operator().value());
        return this;
    }
    public CXIdxLoc loc(){
        return new CXIdxLoc(ms.asSlice(16, 24));
    }

    public CXIdxImportedASTFileInfo loc(StructI<? extends CXIdxLoc> loc){
        MemoryUtils.memcpy(ms, 16, loc.operator().value(), 0, 24);
        return this;
    }
    public I32 isImplicit(){
        return new I32(MemoryUtils.getInt(ms, 40));
    }

    public CXIdxImportedASTFileInfo isImplicit(I32I<?> isImplicit){
        MemoryUtils.setInt(ms, 40, isImplicit.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxImportedASTFileInfo{" +
                "file=" + file() +
                ", module=" + module() +
                ", loc=" + loc() +
                ", isImplicit=" + isImplicit() +
                '}';
    }

}