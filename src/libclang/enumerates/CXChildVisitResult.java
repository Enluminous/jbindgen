package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXChildVisitResult implements I32Op<CXChildVisitResult>, Info<CXChildVisitResult> {
    public static final Info.Operations<CXChildVisitResult> OPERATIONS = I32Op.makeOperations(CXChildVisitResult::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXChildVisitResult(int val) {
        this.val = val;
    }

    public static Array<CXChildVisitResult> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXChildVisitResult> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXChildVisitResult> getOperations() {
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

    public static String enumToString(CXChildVisitResult e) {
        return Utils.enumToString(CXChildVisitResult.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXChildVisitResult that && that.val == val;
    }

    public static final CXChildVisitResult CXChildVisit_Break = new CXChildVisitResult(0);
    public static final CXChildVisitResult CXChildVisit_Continue = new CXChildVisitResult(1);
    public static final CXChildVisitResult CXChildVisit_Recurse = new CXChildVisitResult(2);
}