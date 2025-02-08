package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXTUResourceUsageKind implements I32Op<CXTUResourceUsageKind>, Info<CXTUResourceUsageKind> {
    public static final Info.Operations<CXTUResourceUsageKind> OPERATIONS = I32Op.makeOperations(CXTUResourceUsageKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXTUResourceUsageKind(int val) {
        this.val = val;
    }

    public static Array<CXTUResourceUsageKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXTUResourceUsageKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXTUResourceUsageKind> getOperations() {
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

    public static String enumToString(CXTUResourceUsageKind e) {
        return Utils.enumToString(CXTUResourceUsageKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXTUResourceUsageKind that && that.val == val;
    }

    public static final CXTUResourceUsageKind CXTUResourceUsage_AST = new CXTUResourceUsageKind(1);
    public static final CXTUResourceUsageKind CXTUResourceUsage_Identifiers = new CXTUResourceUsageKind(2);
    public static final CXTUResourceUsageKind CXTUResourceUsage_Selectors = new CXTUResourceUsageKind(3);
    public static final CXTUResourceUsageKind CXTUResourceUsage_GlobalCompletionResults = new CXTUResourceUsageKind(4);
    public static final CXTUResourceUsageKind CXTUResourceUsage_SourceManagerContentCache = new CXTUResourceUsageKind(5);
    public static final CXTUResourceUsageKind CXTUResourceUsage_AST_SideTables = new CXTUResourceUsageKind(6);
    public static final CXTUResourceUsageKind CXTUResourceUsage_SourceManager_Membuffer_Malloc = new CXTUResourceUsageKind(7);
    public static final CXTUResourceUsageKind CXTUResourceUsage_SourceManager_Membuffer_MMap = new CXTUResourceUsageKind(8);
    public static final CXTUResourceUsageKind CXTUResourceUsage_ExternalASTSource_Membuffer_Malloc = new CXTUResourceUsageKind(9);
    public static final CXTUResourceUsageKind CXTUResourceUsage_ExternalASTSource_Membuffer_MMap = new CXTUResourceUsageKind(10);
    public static final CXTUResourceUsageKind CXTUResourceUsage_Preprocessor = new CXTUResourceUsageKind(11);
    public static final CXTUResourceUsageKind CXTUResourceUsage_PreprocessingRecord = new CXTUResourceUsageKind(12);
    public static final CXTUResourceUsageKind CXTUResourceUsage_SourceManager_DataStructures = new CXTUResourceUsageKind(13);
    public static final CXTUResourceUsageKind CXTUResourceUsage_Preprocessor_HeaderSearch = new CXTUResourceUsageKind(14);
    public static final CXTUResourceUsageKind CXTUResourceUsage_MEMORY_IN_BYTES_BEGIN = new CXTUResourceUsageKind(1);
    public static final CXTUResourceUsageKind CXTUResourceUsage_MEMORY_IN_BYTES_END = new CXTUResourceUsageKind(14);
    public static final CXTUResourceUsageKind CXTUResourceUsage_First = new CXTUResourceUsageKind(1);
    public static final CXTUResourceUsageKind CXTUResourceUsage_Last = new CXTUResourceUsageKind(14);
}