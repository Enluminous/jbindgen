package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXDiagnosticSeverity implements I32Op<CXDiagnosticSeverity>, Info<CXDiagnosticSeverity> {
    public static final Info.Operations<CXDiagnosticSeverity> OPERATIONS = I32Op.makeOperations(CXDiagnosticSeverity::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXDiagnosticSeverity(int val) {
        this.val = val;
    }

    public static Array<CXDiagnosticSeverity> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXDiagnosticSeverity> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXDiagnosticSeverity> getOperations() {
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

    public static String enumToString(CXDiagnosticSeverity e) {
        return Utils.enumToString(CXDiagnosticSeverity.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXDiagnosticSeverity that && that.val == val;
    }

    public static final CXDiagnosticSeverity CXDiagnostic_Ignored = new CXDiagnosticSeverity(0);
    public static final CXDiagnosticSeverity CXDiagnostic_Note = new CXDiagnosticSeverity(1);
    public static final CXDiagnosticSeverity CXDiagnostic_Warning = new CXDiagnosticSeverity(2);
    public static final CXDiagnosticSeverity CXDiagnostic_Error = new CXDiagnosticSeverity(3);
    public static final CXDiagnosticSeverity CXDiagnostic_Fatal = new CXDiagnosticSeverity(4);
}