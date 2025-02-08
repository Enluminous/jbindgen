package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXRefQualifierKind implements I32Op<CXRefQualifierKind>, Info<CXRefQualifierKind> {
    public static final Info.Operations<CXRefQualifierKind> OPERATIONS = I32Op.makeOperations(CXRefQualifierKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXRefQualifierKind(int val) {
        this.val = val;
    }

    public static Array<CXRefQualifierKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXRefQualifierKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXRefQualifierKind> getOperations() {
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

    public static String enumToString(CXRefQualifierKind e) {
        return Utils.enumToString(CXRefQualifierKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXRefQualifierKind that && that.val == val;
    }

    public static final CXRefQualifierKind CXRefQualifier_None = new CXRefQualifierKind(0);
    public static final CXRefQualifierKind CXRefQualifier_LValue = new CXRefQualifierKind(1);
    public static final CXRefQualifierKind CXRefQualifier_RValue = new CXRefQualifierKind(2);
}