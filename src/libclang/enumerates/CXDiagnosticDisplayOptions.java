package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXDiagnosticDisplayOptions implements I32Op<CXDiagnosticDisplayOptions>, Info<CXDiagnosticDisplayOptions> {
    public static final Info.Operations<CXDiagnosticDisplayOptions> OPERATIONS = I32Op.makeOperations(CXDiagnosticDisplayOptions::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXDiagnosticDisplayOptions(int val) {
        this.val = val;
    }

    public static Array<CXDiagnosticDisplayOptions> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXDiagnosticDisplayOptions> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXDiagnosticDisplayOptions> getOperations() {
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

    public static String enumToString(CXDiagnosticDisplayOptions e) {
        return Utils.enumToString(CXDiagnosticDisplayOptions.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXDiagnosticDisplayOptions that && that.val == val;
    }

    public static final CXDiagnosticDisplayOptions CXDiagnostic_DisplaySourceLocation = new CXDiagnosticDisplayOptions(1);
    public static final CXDiagnosticDisplayOptions CXDiagnostic_DisplayColumn = new CXDiagnosticDisplayOptions(2);
    public static final CXDiagnosticDisplayOptions CXDiagnostic_DisplaySourceRanges = new CXDiagnosticDisplayOptions(4);
    public static final CXDiagnosticDisplayOptions CXDiagnostic_DisplayOption = new CXDiagnosticDisplayOptions(8);
    public static final CXDiagnosticDisplayOptions CXDiagnostic_DisplayCategoryId = new CXDiagnosticDisplayOptions(16);
    public static final CXDiagnosticDisplayOptions CXDiagnostic_DisplayCategoryName = new CXDiagnosticDisplayOptions(32);
}