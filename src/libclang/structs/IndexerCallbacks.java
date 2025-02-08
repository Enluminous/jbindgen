package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructOp;
import libclang.functions.IndexerCallbacks$abortQuery;
import libclang.functions.IndexerCallbacks$diagnostic;
import libclang.functions.IndexerCallbacks$enteredMainFile;
import libclang.functions.IndexerCallbacks$importedASTFile;
import libclang.functions.IndexerCallbacks$indexDeclaration;
import libclang.functions.IndexerCallbacks$indexEntityReference;
import libclang.functions.IndexerCallbacks$ppIncludedFile;
import libclang.functions.IndexerCallbacks$startedTranslationUnit;
public final class IndexerCallbacks implements StructOp<IndexerCallbacks>, Info<IndexerCallbacks> {
   public static final int BYTE_SIZE = 64;
   private final MemorySegment ms;
   public static final Operations<IndexerCallbacks> OPERATIONS = StructOp.makeOperations(IndexerCallbacks::new, BYTE_SIZE);

   public IndexerCallbacks(MemorySegment ms) {
       this.ms = ms;
   }

    public IndexerCallbacks(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<IndexerCallbacks> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, IndexerCallbacks.OPERATIONS, len);
    }

   @Override
   public StructOpI<IndexerCallbacks> operator() {
       return new StructOpI<>() {
           @Override
           public IndexerCallbacks reinterpret() {
               return new IndexerCallbacks(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<IndexerCallbacks> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<IndexerCallbacks> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public IndexerCallbacks$abortQuery abortQuery(){
        return new IndexerCallbacks$abortQuery(MemoryUtils.getAddr(ms, 0));
    }

    public IndexerCallbacks abortQuery(PtrI<? extends IndexerCallbacks$abortQuery.Function> abortQuery){
        MemoryUtils.setAddr(ms, 0, abortQuery.operator().value());
        return this;
    }
    public IndexerCallbacks$diagnostic diagnostic(){
        return new IndexerCallbacks$diagnostic(MemoryUtils.getAddr(ms, 8));
    }

    public IndexerCallbacks diagnostic(PtrI<? extends IndexerCallbacks$diagnostic.Function> diagnostic){
        MemoryUtils.setAddr(ms, 8, diagnostic.operator().value());
        return this;
    }
    public IndexerCallbacks$enteredMainFile enteredMainFile(){
        return new IndexerCallbacks$enteredMainFile(MemoryUtils.getAddr(ms, 16));
    }

    public IndexerCallbacks enteredMainFile(PtrI<? extends IndexerCallbacks$enteredMainFile.Function> enteredMainFile){
        MemoryUtils.setAddr(ms, 16, enteredMainFile.operator().value());
        return this;
    }
    public IndexerCallbacks$ppIncludedFile ppIncludedFile(){
        return new IndexerCallbacks$ppIncludedFile(MemoryUtils.getAddr(ms, 24));
    }

    public IndexerCallbacks ppIncludedFile(PtrI<? extends IndexerCallbacks$ppIncludedFile.Function> ppIncludedFile){
        MemoryUtils.setAddr(ms, 24, ppIncludedFile.operator().value());
        return this;
    }
    public IndexerCallbacks$importedASTFile importedASTFile(){
        return new IndexerCallbacks$importedASTFile(MemoryUtils.getAddr(ms, 32));
    }

    public IndexerCallbacks importedASTFile(PtrI<? extends IndexerCallbacks$importedASTFile.Function> importedASTFile){
        MemoryUtils.setAddr(ms, 32, importedASTFile.operator().value());
        return this;
    }
    public IndexerCallbacks$startedTranslationUnit startedTranslationUnit(){
        return new IndexerCallbacks$startedTranslationUnit(MemoryUtils.getAddr(ms, 40));
    }

    public IndexerCallbacks startedTranslationUnit(PtrI<? extends IndexerCallbacks$startedTranslationUnit.Function> startedTranslationUnit){
        MemoryUtils.setAddr(ms, 40, startedTranslationUnit.operator().value());
        return this;
    }
    public IndexerCallbacks$indexDeclaration indexDeclaration(){
        return new IndexerCallbacks$indexDeclaration(MemoryUtils.getAddr(ms, 48));
    }

    public IndexerCallbacks indexDeclaration(PtrI<? extends IndexerCallbacks$indexDeclaration.Function> indexDeclaration){
        MemoryUtils.setAddr(ms, 48, indexDeclaration.operator().value());
        return this;
    }
    public IndexerCallbacks$indexEntityReference indexEntityReference(){
        return new IndexerCallbacks$indexEntityReference(MemoryUtils.getAddr(ms, 56));
    }

    public IndexerCallbacks indexEntityReference(PtrI<? extends IndexerCallbacks$indexEntityReference.Function> indexEntityReference){
        MemoryUtils.setAddr(ms, 56, indexEntityReference.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "IndexerCallbacks{" +
                "abortQuery=" + abortQuery() +
                ", diagnostic=" + diagnostic() +
                ", enteredMainFile=" + enteredMainFile() +
                ", ppIncludedFile=" + ppIncludedFile() +
                ", importedASTFile=" + importedASTFile() +
                ", startedTranslationUnit=" + startedTranslationUnit() +
                ", indexDeclaration=" + indexDeclaration() +
                ", indexEntityReference=" + indexEntityReference() +
                '}';
    }

}