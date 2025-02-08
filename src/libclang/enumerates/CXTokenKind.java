package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXTokenKind implements I32Op<CXTokenKind>, Info<CXTokenKind> {
    public static final Info.Operations<CXTokenKind> OPERATIONS = I32Op.makeOperations(CXTokenKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXTokenKind(int val) {
        this.val = val;
    }

    public static Array<CXTokenKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXTokenKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXTokenKind> getOperations() {
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

    public static String enumToString(CXTokenKind e) {
        return Utils.enumToString(CXTokenKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXTokenKind that && that.val == val;
    }

    public static final CXTokenKind CXToken_Punctuation = new CXTokenKind(0);
    public static final CXTokenKind CXToken_Keyword = new CXTokenKind(1);
    public static final CXTokenKind CXToken_Identifier = new CXTokenKind(2);
    public static final CXTokenKind CXToken_Literal = new CXTokenKind(3);
    public static final CXTokenKind CXToken_Comment = new CXTokenKind(4);
}