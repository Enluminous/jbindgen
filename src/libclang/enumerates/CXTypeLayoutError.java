package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXTypeLayoutError implements I32Op<CXTypeLayoutError>, Info<CXTypeLayoutError> {
    public static final Info.Operations<CXTypeLayoutError> OPERATIONS = I32Op.makeOperations(CXTypeLayoutError::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXTypeLayoutError(int val) {
        this.val = val;
    }

    public static Array<CXTypeLayoutError> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXTypeLayoutError> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXTypeLayoutError> getOperations() {
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

    public static String enumToString(CXTypeLayoutError e) {
        return Utils.enumToString(CXTypeLayoutError.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXTypeLayoutError that && that.val == val;
    }

    public static final CXTypeLayoutError CXTypeLayoutError_Invalid = new CXTypeLayoutError(-1);
    public static final CXTypeLayoutError CXTypeLayoutError_Incomplete = new CXTypeLayoutError(-2);
    public static final CXTypeLayoutError CXTypeLayoutError_Dependent = new CXTypeLayoutError(-3);
    public static final CXTypeLayoutError CXTypeLayoutError_NotConstantSize = new CXTypeLayoutError(-4);
    public static final CXTypeLayoutError CXTypeLayoutError_InvalidFieldName = new CXTypeLayoutError(-5);
    public static final CXTypeLayoutError CXTypeLayoutError_Undeduced = new CXTypeLayoutError(-6);
}