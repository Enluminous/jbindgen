package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXCursor_ExceptionSpecificationKind implements I32Op<CXCursor_ExceptionSpecificationKind>, Info<CXCursor_ExceptionSpecificationKind> {
    public static final Info.Operations<CXCursor_ExceptionSpecificationKind> OPERATIONS = I32Op.makeOperations(CXCursor_ExceptionSpecificationKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXCursor_ExceptionSpecificationKind(int val) {
        this.val = val;
    }

    public static Array<CXCursor_ExceptionSpecificationKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXCursor_ExceptionSpecificationKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXCursor_ExceptionSpecificationKind> getOperations() {
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

    public static String enumToString(CXCursor_ExceptionSpecificationKind e) {
        return Utils.enumToString(CXCursor_ExceptionSpecificationKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXCursor_ExceptionSpecificationKind that && that.val == val;
    }

    public static final CXCursor_ExceptionSpecificationKind CXCursor_ExceptionSpecificationKind_None = new CXCursor_ExceptionSpecificationKind(0);
    public static final CXCursor_ExceptionSpecificationKind CXCursor_ExceptionSpecificationKind_DynamicNone = new CXCursor_ExceptionSpecificationKind(1);
    public static final CXCursor_ExceptionSpecificationKind CXCursor_ExceptionSpecificationKind_Dynamic = new CXCursor_ExceptionSpecificationKind(2);
    public static final CXCursor_ExceptionSpecificationKind CXCursor_ExceptionSpecificationKind_MSAny = new CXCursor_ExceptionSpecificationKind(3);
    public static final CXCursor_ExceptionSpecificationKind CXCursor_ExceptionSpecificationKind_BasicNoexcept = new CXCursor_ExceptionSpecificationKind(4);
    public static final CXCursor_ExceptionSpecificationKind CXCursor_ExceptionSpecificationKind_ComputedNoexcept = new CXCursor_ExceptionSpecificationKind(5);
    public static final CXCursor_ExceptionSpecificationKind CXCursor_ExceptionSpecificationKind_Unevaluated = new CXCursor_ExceptionSpecificationKind(6);
    public static final CXCursor_ExceptionSpecificationKind CXCursor_ExceptionSpecificationKind_Uninstantiated = new CXCursor_ExceptionSpecificationKind(7);
    public static final CXCursor_ExceptionSpecificationKind CXCursor_ExceptionSpecificationKind_Unparsed = new CXCursor_ExceptionSpecificationKind(8);
    public static final CXCursor_ExceptionSpecificationKind CXCursor_ExceptionSpecificationKind_NoThrow = new CXCursor_ExceptionSpecificationKind(9);
}