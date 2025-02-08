package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXErrorCode implements I32Op<CXErrorCode>, Info<CXErrorCode> {
    public static final Info.Operations<CXErrorCode> OPERATIONS = I32Op.makeOperations(CXErrorCode::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXErrorCode(int val) {
        this.val = val;
    }

    public static Array<CXErrorCode> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXErrorCode> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXErrorCode> getOperations() {
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

    public static String enumToString(CXErrorCode e) {
        return Utils.enumToString(CXErrorCode.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXErrorCode that && that.val == val;
    }

    public static final CXErrorCode CXError_Success = new CXErrorCode(0);
    public static final CXErrorCode CXError_Failure = new CXErrorCode(1);
    public static final CXErrorCode CXError_Crashed = new CXErrorCode(2);
    public static final CXErrorCode CXError_InvalidArguments = new CXErrorCode(3);
    public static final CXErrorCode CXError_ASTReadError = new CXErrorCode(4);
}