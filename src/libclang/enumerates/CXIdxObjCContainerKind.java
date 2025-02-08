package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXIdxObjCContainerKind implements I32Op<CXIdxObjCContainerKind>, Info<CXIdxObjCContainerKind> {
    public static final Info.Operations<CXIdxObjCContainerKind> OPERATIONS = I32Op.makeOperations(CXIdxObjCContainerKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXIdxObjCContainerKind(int val) {
        this.val = val;
    }

    public static Array<CXIdxObjCContainerKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXIdxObjCContainerKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXIdxObjCContainerKind> getOperations() {
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

    public static String enumToString(CXIdxObjCContainerKind e) {
        return Utils.enumToString(CXIdxObjCContainerKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXIdxObjCContainerKind that && that.val == val;
    }

    public static final CXIdxObjCContainerKind CXIdxObjCContainer_ForwardRef = new CXIdxObjCContainerKind(0);
    public static final CXIdxObjCContainerKind CXIdxObjCContainer_Interface = new CXIdxObjCContainerKind(1);
    public static final CXIdxObjCContainerKind CXIdxObjCContainer_Implementation = new CXIdxObjCContainerKind(2);
}