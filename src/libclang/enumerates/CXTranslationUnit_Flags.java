package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXTranslationUnit_Flags implements I32Op<CXTranslationUnit_Flags>, Info<CXTranslationUnit_Flags> {
    public static final Info.Operations<CXTranslationUnit_Flags> OPERATIONS = I32Op.makeOperations(CXTranslationUnit_Flags::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXTranslationUnit_Flags(int val) {
        this.val = val;
    }

    public static Array<CXTranslationUnit_Flags> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXTranslationUnit_Flags> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXTranslationUnit_Flags> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Integer value() {
                return val;
            }
        };
    }

    private String str;

    @Override
    public String toString() {
        if (str == null) {
            str = enumToString(this);
            if (str == null) str = String.valueOf(val);
        }
        return str;
    }

    public static String enumToString(CXTranslationUnit_Flags e) {
        return Utils.enumToString(CXTranslationUnit_Flags.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXTranslationUnit_Flags that && that.val == val;
    }

    public static final CXTranslationUnit_Flags CXTranslationUnit_None = new CXTranslationUnit_Flags(0);
    public static final CXTranslationUnit_Flags CXTranslationUnit_DetailedPreprocessingRecord = new CXTranslationUnit_Flags(1);
    public static final CXTranslationUnit_Flags CXTranslationUnit_Incomplete = new CXTranslationUnit_Flags(2);
    public static final CXTranslationUnit_Flags CXTranslationUnit_PrecompiledPreamble = new CXTranslationUnit_Flags(4);
    public static final CXTranslationUnit_Flags CXTranslationUnit_CacheCompletionResults = new CXTranslationUnit_Flags(8);
    public static final CXTranslationUnit_Flags CXTranslationUnit_ForSerialization = new CXTranslationUnit_Flags(16);
    public static final CXTranslationUnit_Flags CXTranslationUnit_CXXChainedPCH = new CXTranslationUnit_Flags(32);
    public static final CXTranslationUnit_Flags CXTranslationUnit_SkipFunctionBodies = new CXTranslationUnit_Flags(64);
    public static final CXTranslationUnit_Flags CXTranslationUnit_IncludeBriefCommentsInCodeCompletion = new CXTranslationUnit_Flags(128);
    public static final CXTranslationUnit_Flags CXTranslationUnit_CreatePreambleOnFirstParse = new CXTranslationUnit_Flags(256);
    public static final CXTranslationUnit_Flags CXTranslationUnit_KeepGoing = new CXTranslationUnit_Flags(512);
    public static final CXTranslationUnit_Flags CXTranslationUnit_SingleFileParse = new CXTranslationUnit_Flags(1024);
    public static final CXTranslationUnit_Flags CXTranslationUnit_LimitSkipFunctionBodiesToPreamble = new CXTranslationUnit_Flags(2048);
    public static final CXTranslationUnit_Flags CXTranslationUnit_IncludeAttributedTypes = new CXTranslationUnit_Flags(4096);
    public static final CXTranslationUnit_Flags CXTranslationUnit_VisitImplicitAttributes = new CXTranslationUnit_Flags(8192);
    public static final CXTranslationUnit_Flags CXTranslationUnit_IgnoreNonErrorsFromIncludedFiles = new CXTranslationUnit_Flags(16384);
    public static final CXTranslationUnit_Flags CXTranslationUnit_RetainExcludedConditionalBlocks = new CXTranslationUnit_Flags(32768);
}