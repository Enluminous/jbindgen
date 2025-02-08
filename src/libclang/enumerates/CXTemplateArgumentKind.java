package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXTemplateArgumentKind implements I32Op<CXTemplateArgumentKind>, Info<CXTemplateArgumentKind> {
    public static final Info.Operations<CXTemplateArgumentKind> OPERATIONS = I32Op.makeOperations(CXTemplateArgumentKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXTemplateArgumentKind(int val) {
        this.val = val;
    }

    public static Array<CXTemplateArgumentKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXTemplateArgumentKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXTemplateArgumentKind> getOperations() {
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

    public static String enumToString(CXTemplateArgumentKind e) {
        return Utils.enumToString(CXTemplateArgumentKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXTemplateArgumentKind that && that.val == val;
    }

    public static final CXTemplateArgumentKind CXTemplateArgumentKind_Null = new CXTemplateArgumentKind(0);
    public static final CXTemplateArgumentKind CXTemplateArgumentKind_Type = new CXTemplateArgumentKind(1);
    public static final CXTemplateArgumentKind CXTemplateArgumentKind_Declaration = new CXTemplateArgumentKind(2);
    public static final CXTemplateArgumentKind CXTemplateArgumentKind_NullPtr = new CXTemplateArgumentKind(3);
    public static final CXTemplateArgumentKind CXTemplateArgumentKind_Integral = new CXTemplateArgumentKind(4);
    public static final CXTemplateArgumentKind CXTemplateArgumentKind_Template = new CXTemplateArgumentKind(5);
    public static final CXTemplateArgumentKind CXTemplateArgumentKind_TemplateExpansion = new CXTemplateArgumentKind(6);
    public static final CXTemplateArgumentKind CXTemplateArgumentKind_Expression = new CXTemplateArgumentKind(7);
    public static final CXTemplateArgumentKind CXTemplateArgumentKind_Pack = new CXTemplateArgumentKind(8);
    public static final CXTemplateArgumentKind CXTemplateArgumentKind_Invalid = new CXTemplateArgumentKind(9);
}