package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXEvalResultKind implements I32Op<CXEvalResultKind>, Info<CXEvalResultKind> {
    public static final Info.Operations<CXEvalResultKind> OPERATIONS = I32Op.makeOperations(CXEvalResultKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXEvalResultKind(int val) {
        this.val = val;
    }

    public static Array<CXEvalResultKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXEvalResultKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXEvalResultKind> getOperations() {
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

    public static String enumToString(CXEvalResultKind e) {
        return Utils.enumToString(CXEvalResultKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXEvalResultKind that && that.val == val;
    }

    public static final CXEvalResultKind CXEval_Int = new CXEvalResultKind(1);
    public static final CXEvalResultKind CXEval_Float = new CXEvalResultKind(2);
    public static final CXEvalResultKind CXEval_ObjCStrLiteral = new CXEvalResultKind(3);
    public static final CXEvalResultKind CXEval_StrLiteral = new CXEvalResultKind(4);
    public static final CXEvalResultKind CXEval_CFStr = new CXEvalResultKind(5);
    public static final CXEvalResultKind CXEval_Other = new CXEvalResultKind(6);
    public static final CXEvalResultKind CXEval_UnExposed = new CXEvalResultKind(0);
}