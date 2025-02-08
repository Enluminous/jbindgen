package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXCompletionContext implements I32Op<CXCompletionContext>, Info<CXCompletionContext> {
    public static final Info.Operations<CXCompletionContext> OPERATIONS = I32Op.makeOperations(CXCompletionContext::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXCompletionContext(int val) {
        this.val = val;
    }

    public static Array<CXCompletionContext> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXCompletionContext> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXCompletionContext> getOperations() {
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

    public static String enumToString(CXCompletionContext e) {
        return Utils.enumToString(CXCompletionContext.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXCompletionContext that && that.val == val;
    }

    public static final CXCompletionContext CXCompletionContext_Unexposed = new CXCompletionContext(0);
    public static final CXCompletionContext CXCompletionContext_AnyType = new CXCompletionContext(1);
    public static final CXCompletionContext CXCompletionContext_AnyValue = new CXCompletionContext(2);
    public static final CXCompletionContext CXCompletionContext_ObjCObjectValue = new CXCompletionContext(4);
    public static final CXCompletionContext CXCompletionContext_ObjCSelectorValue = new CXCompletionContext(8);
    public static final CXCompletionContext CXCompletionContext_CXXClassTypeValue = new CXCompletionContext(16);
    public static final CXCompletionContext CXCompletionContext_DotMemberAccess = new CXCompletionContext(32);
    public static final CXCompletionContext CXCompletionContext_ArrowMemberAccess = new CXCompletionContext(64);
    public static final CXCompletionContext CXCompletionContext_ObjCPropertyAccess = new CXCompletionContext(128);
    public static final CXCompletionContext CXCompletionContext_EnumTag = new CXCompletionContext(256);
    public static final CXCompletionContext CXCompletionContext_UnionTag = new CXCompletionContext(512);
    public static final CXCompletionContext CXCompletionContext_StructTag = new CXCompletionContext(1024);
    public static final CXCompletionContext CXCompletionContext_ClassTag = new CXCompletionContext(2048);
    public static final CXCompletionContext CXCompletionContext_Namespace = new CXCompletionContext(4096);
    public static final CXCompletionContext CXCompletionContext_NestedNameSpecifier = new CXCompletionContext(8192);
    public static final CXCompletionContext CXCompletionContext_ObjCInterface = new CXCompletionContext(16384);
    public static final CXCompletionContext CXCompletionContext_ObjCProtocol = new CXCompletionContext(32768);
    public static final CXCompletionContext CXCompletionContext_ObjCCategory = new CXCompletionContext(65536);
    public static final CXCompletionContext CXCompletionContext_ObjCInstanceMessage = new CXCompletionContext(131072);
    public static final CXCompletionContext CXCompletionContext_ObjCClassMessage = new CXCompletionContext(262144);
    public static final CXCompletionContext CXCompletionContext_ObjCSelectorName = new CXCompletionContext(524288);
    public static final CXCompletionContext CXCompletionContext_MacroName = new CXCompletionContext(1048576);
    public static final CXCompletionContext CXCompletionContext_NaturalLanguage = new CXCompletionContext(2097152);
    public static final CXCompletionContext CXCompletionContext_IncludedFile = new CXCompletionContext(4194304);
    public static final CXCompletionContext CXCompletionContext_Unknown = new CXCompletionContext(8388607);
}