package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXLanguageKind implements I32Op<CXLanguageKind>, Info<CXLanguageKind> {
    public static final Info.Operations<CXLanguageKind> OPERATIONS = I32Op.makeOperations(CXLanguageKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXLanguageKind(int val) {
        this.val = val;
    }

    public static Array<CXLanguageKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXLanguageKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXLanguageKind> getOperations() {
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

    public static String enumToString(CXLanguageKind e) {
        return Utils.enumToString(CXLanguageKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXLanguageKind that && that.val == val;
    }

    public static final CXLanguageKind CXLanguage_Invalid = new CXLanguageKind(0);
    public static final CXLanguageKind CXLanguage_C = new CXLanguageKind(1);
    public static final CXLanguageKind CXLanguage_ObjC = new CXLanguageKind(2);
    public static final CXLanguageKind CXLanguage_CPlusPlus = new CXLanguageKind(3);
}