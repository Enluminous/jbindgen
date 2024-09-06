package libclang.structs;


import libclang.structs.*;
import libclang.LibclangEnums.*;
import libclang.functions.*;
import libclang.values.*;
import libclang.shared.values.*;
import libclang.shared.*;
import libclang.shared.natives.*;
import libclang.shared.Value;
import libclang.shared.Pointer;
import libclang.shared.FunctionUtils;

import java.lang.foreign.*;
import java.util.function.Consumer;


public final class IndexerCallbacks implements Pointer<IndexerCallbacks> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(64, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<IndexerCallbacks> list(Pointer<IndexerCallbacks> ptr) {
        return new NList<>(ptr, IndexerCallbacks::new, BYTE_SIZE);
    }

    public static NList<IndexerCallbacks> list(Pointer<IndexerCallbacks> ptr, long length) {
        return new NList<>(ptr, length, IndexerCallbacks::new, BYTE_SIZE);
    }

    public static NList<IndexerCallbacks> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, IndexerCallbacks::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public IndexerCallbacks(Pointer<IndexerCallbacks> ptr) {
        this.ptr = ptr.pointer();
    }

    public IndexerCallbacks(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public IndexerCallbacks reinterpretSize() {
        return new IndexerCallbacks(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public VPointer<IndexerCallbacks$abortQuery> abortQuery() {
        return new VPointer<>(ptr.get(ValueLayout.ADDRESS, 0));
    }

    public IndexerCallbacks abortQuery(Pointer<IndexerCallbacks$abortQuery> abortQuery) {
        ptr.set(ValueLayout.ADDRESS, 0, abortQuery.pointer());
        return this;
    }

    public VPointer<IndexerCallbacks$diagnostic> diagnostic() {
        return new VPointer<>(ptr.get(ValueLayout.ADDRESS, 8));
    }

    public IndexerCallbacks diagnostic(Pointer<IndexerCallbacks$diagnostic> diagnostic) {
        ptr.set(ValueLayout.ADDRESS, 8, diagnostic.pointer());
        return this;
    }

    public VPointer<IndexerCallbacks$enteredMainFile> enteredMainFile() {
        return new VPointer<>(ptr.get(ValueLayout.ADDRESS, 16));
    }

    public IndexerCallbacks enteredMainFile(Pointer<IndexerCallbacks$enteredMainFile> enteredMainFile) {
        ptr.set(ValueLayout.ADDRESS, 16, enteredMainFile.pointer());
        return this;
    }

    public VPointer<IndexerCallbacks$ppIncludedFile> ppIncludedFile() {
        return new VPointer<>(ptr.get(ValueLayout.ADDRESS, 24));
    }

    public IndexerCallbacks ppIncludedFile(Pointer<IndexerCallbacks$ppIncludedFile> ppIncludedFile) {
        ptr.set(ValueLayout.ADDRESS, 24, ppIncludedFile.pointer());
        return this;
    }

    public VPointer<IndexerCallbacks$importedASTFile> importedASTFile() {
        return new VPointer<>(ptr.get(ValueLayout.ADDRESS, 32));
    }

    public IndexerCallbacks importedASTFile(Pointer<IndexerCallbacks$importedASTFile> importedASTFile) {
        ptr.set(ValueLayout.ADDRESS, 32, importedASTFile.pointer());
        return this;
    }

    public VPointer<IndexerCallbacks$startedTranslationUnit> startedTranslationUnit() {
        return new VPointer<>(ptr.get(ValueLayout.ADDRESS, 40));
    }

    public IndexerCallbacks startedTranslationUnit(Pointer<IndexerCallbacks$startedTranslationUnit> startedTranslationUnit) {
        ptr.set(ValueLayout.ADDRESS, 40, startedTranslationUnit.pointer());
        return this;
    }

    public VPointer<IndexerCallbacks$indexDeclaration> indexDeclaration() {
        return new VPointer<>(ptr.get(ValueLayout.ADDRESS, 48));
    }

    public IndexerCallbacks indexDeclaration(Pointer<IndexerCallbacks$indexDeclaration> indexDeclaration) {
        ptr.set(ValueLayout.ADDRESS, 48, indexDeclaration.pointer());
        return this;
    }

    public VPointer<IndexerCallbacks$indexEntityReference> indexEntityReference() {
        return new VPointer<>(ptr.get(ValueLayout.ADDRESS, 56));
    }

    public IndexerCallbacks indexEntityReference(Pointer<IndexerCallbacks$indexEntityReference> indexEntityReference) {
        ptr.set(ValueLayout.ADDRESS, 56, indexEntityReference.pointer());
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "IndexerCallbacks{ptr=" + ptr + "}";
        return "IndexerCallbacks{" +
                "abortQuery=" + abortQuery() +
                "diagnostic=" + diagnostic() +
                "enteredMainFile=" + enteredMainFile() +
                "ppIncludedFile=" + ppIncludedFile() +
                "importedASTFile=" + importedASTFile() +
                "startedTranslationUnit=" + startedTranslationUnit() +
                "indexDeclaration=" + indexDeclaration() +
                "indexEntityReference=" + indexEntityReference() + "}";
    }
}