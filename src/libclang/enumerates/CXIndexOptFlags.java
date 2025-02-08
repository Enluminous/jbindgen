package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXIndexOptFlags implements I32Op<CXIndexOptFlags>, Info<CXIndexOptFlags> {
    public static final Info.Operations<CXIndexOptFlags> OPERATIONS = I32Op.makeOperations(CXIndexOptFlags::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXIndexOptFlags(int val) {
        this.val = val;
    }

    public static Array<CXIndexOptFlags> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXIndexOptFlags> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXIndexOptFlags> getOperations() {
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

    public static String enumToString(CXIndexOptFlags e) {
        return Utils.enumToString(CXIndexOptFlags.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXIndexOptFlags that && that.val == val;
    }

    public static final CXIndexOptFlags CXIndexOpt_None = new CXIndexOptFlags(0);
    public static final CXIndexOptFlags CXIndexOpt_SuppressRedundantRefs = new CXIndexOptFlags(1);
    public static final CXIndexOptFlags CXIndexOpt_IndexFunctionLocalSymbols = new CXIndexOptFlags(2);
    public static final CXIndexOptFlags CXIndexOpt_IndexImplicitTemplateInstantiations = new CXIndexOptFlags(4);
    public static final CXIndexOptFlags CXIndexOpt_SuppressWarnings = new CXIndexOptFlags(8);
    public static final CXIndexOptFlags CXIndexOpt_SkipParsedBodiesInSession = new CXIndexOptFlags(16);
}