package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXTypeKind implements I32Op<CXTypeKind>, Info<CXTypeKind> {
    public static final Info.Operations<CXTypeKind> OPERATIONS = I32Op.makeOperations(CXTypeKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXTypeKind(int val) {
        this.val = val;
    }

    public static Array<CXTypeKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXTypeKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXTypeKind> getOperations() {
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

    public static String enumToString(CXTypeKind e) {
        return Utils.enumToString(CXTypeKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXTypeKind that && that.val == val;
    }

    public static final CXTypeKind CXType_Invalid = new CXTypeKind(0);
    public static final CXTypeKind CXType_Unexposed = new CXTypeKind(1);
    public static final CXTypeKind CXType_Void = new CXTypeKind(2);
    public static final CXTypeKind CXType_Bool = new CXTypeKind(3);
    public static final CXTypeKind CXType_Char_U = new CXTypeKind(4);
    public static final CXTypeKind CXType_UChar = new CXTypeKind(5);
    public static final CXTypeKind CXType_Char16 = new CXTypeKind(6);
    public static final CXTypeKind CXType_Char32 = new CXTypeKind(7);
    public static final CXTypeKind CXType_UShort = new CXTypeKind(8);
    public static final CXTypeKind CXType_UInt = new CXTypeKind(9);
    public static final CXTypeKind CXType_ULong = new CXTypeKind(10);
    public static final CXTypeKind CXType_ULongLong = new CXTypeKind(11);
    public static final CXTypeKind CXType_UInt128 = new CXTypeKind(12);
    public static final CXTypeKind CXType_Char_S = new CXTypeKind(13);
    public static final CXTypeKind CXType_SChar = new CXTypeKind(14);
    public static final CXTypeKind CXType_WChar = new CXTypeKind(15);
    public static final CXTypeKind CXType_Short = new CXTypeKind(16);
    public static final CXTypeKind CXType_Int = new CXTypeKind(17);
    public static final CXTypeKind CXType_Long = new CXTypeKind(18);
    public static final CXTypeKind CXType_LongLong = new CXTypeKind(19);
    public static final CXTypeKind CXType_Int128 = new CXTypeKind(20);
    public static final CXTypeKind CXType_Float = new CXTypeKind(21);
    public static final CXTypeKind CXType_Double = new CXTypeKind(22);
    public static final CXTypeKind CXType_LongDouble = new CXTypeKind(23);
    public static final CXTypeKind CXType_NullPtr = new CXTypeKind(24);
    public static final CXTypeKind CXType_Overload = new CXTypeKind(25);
    public static final CXTypeKind CXType_Dependent = new CXTypeKind(26);
    public static final CXTypeKind CXType_ObjCId = new CXTypeKind(27);
    public static final CXTypeKind CXType_ObjCClass = new CXTypeKind(28);
    public static final CXTypeKind CXType_ObjCSel = new CXTypeKind(29);
    public static final CXTypeKind CXType_Float128 = new CXTypeKind(30);
    public static final CXTypeKind CXType_Half = new CXTypeKind(31);
    public static final CXTypeKind CXType_Float16 = new CXTypeKind(32);
    public static final CXTypeKind CXType_ShortAccum = new CXTypeKind(33);
    public static final CXTypeKind CXType_Accum = new CXTypeKind(34);
    public static final CXTypeKind CXType_LongAccum = new CXTypeKind(35);
    public static final CXTypeKind CXType_UShortAccum = new CXTypeKind(36);
    public static final CXTypeKind CXType_UAccum = new CXTypeKind(37);
    public static final CXTypeKind CXType_ULongAccum = new CXTypeKind(38);
    public static final CXTypeKind CXType_BFloat16 = new CXTypeKind(39);
    public static final CXTypeKind CXType_Ibm128 = new CXTypeKind(40);
    public static final CXTypeKind CXType_FirstBuiltin = new CXTypeKind(2);
    public static final CXTypeKind CXType_LastBuiltin = new CXTypeKind(40);
    public static final CXTypeKind CXType_Complex = new CXTypeKind(100);
    public static final CXTypeKind CXType_Pointer = new CXTypeKind(101);
    public static final CXTypeKind CXType_BlockPointer = new CXTypeKind(102);
    public static final CXTypeKind CXType_LValueReference = new CXTypeKind(103);
    public static final CXTypeKind CXType_RValueReference = new CXTypeKind(104);
    public static final CXTypeKind CXType_Record = new CXTypeKind(105);
    public static final CXTypeKind CXType_Enum = new CXTypeKind(106);
    public static final CXTypeKind CXType_Typedef = new CXTypeKind(107);
    public static final CXTypeKind CXType_ObjCInterface = new CXTypeKind(108);
    public static final CXTypeKind CXType_ObjCObjectPointer = new CXTypeKind(109);
    public static final CXTypeKind CXType_FunctionNoProto = new CXTypeKind(110);
    public static final CXTypeKind CXType_FunctionProto = new CXTypeKind(111);
    public static final CXTypeKind CXType_ConstantArray = new CXTypeKind(112);
    public static final CXTypeKind CXType_Vector = new CXTypeKind(113);
    public static final CXTypeKind CXType_IncompleteArray = new CXTypeKind(114);
    public static final CXTypeKind CXType_VariableArray = new CXTypeKind(115);
    public static final CXTypeKind CXType_DependentSizedArray = new CXTypeKind(116);
    public static final CXTypeKind CXType_MemberPointer = new CXTypeKind(117);
    public static final CXTypeKind CXType_Auto = new CXTypeKind(118);
    public static final CXTypeKind CXType_Elaborated = new CXTypeKind(119);
    public static final CXTypeKind CXType_Pipe = new CXTypeKind(120);
    public static final CXTypeKind CXType_OCLImage1dRO = new CXTypeKind(121);
    public static final CXTypeKind CXType_OCLImage1dArrayRO = new CXTypeKind(122);
    public static final CXTypeKind CXType_OCLImage1dBufferRO = new CXTypeKind(123);
    public static final CXTypeKind CXType_OCLImage2dRO = new CXTypeKind(124);
    public static final CXTypeKind CXType_OCLImage2dArrayRO = new CXTypeKind(125);
    public static final CXTypeKind CXType_OCLImage2dDepthRO = new CXTypeKind(126);
    public static final CXTypeKind CXType_OCLImage2dArrayDepthRO = new CXTypeKind(127);
    public static final CXTypeKind CXType_OCLImage2dMSAARO = new CXTypeKind(128);
    public static final CXTypeKind CXType_OCLImage2dArrayMSAARO = new CXTypeKind(129);
    public static final CXTypeKind CXType_OCLImage2dMSAADepthRO = new CXTypeKind(130);
    public static final CXTypeKind CXType_OCLImage2dArrayMSAADepthRO = new CXTypeKind(131);
    public static final CXTypeKind CXType_OCLImage3dRO = new CXTypeKind(132);
    public static final CXTypeKind CXType_OCLImage1dWO = new CXTypeKind(133);
    public static final CXTypeKind CXType_OCLImage1dArrayWO = new CXTypeKind(134);
    public static final CXTypeKind CXType_OCLImage1dBufferWO = new CXTypeKind(135);
    public static final CXTypeKind CXType_OCLImage2dWO = new CXTypeKind(136);
    public static final CXTypeKind CXType_OCLImage2dArrayWO = new CXTypeKind(137);
    public static final CXTypeKind CXType_OCLImage2dDepthWO = new CXTypeKind(138);
    public static final CXTypeKind CXType_OCLImage2dArrayDepthWO = new CXTypeKind(139);
    public static final CXTypeKind CXType_OCLImage2dMSAAWO = new CXTypeKind(140);
    public static final CXTypeKind CXType_OCLImage2dArrayMSAAWO = new CXTypeKind(141);
    public static final CXTypeKind CXType_OCLImage2dMSAADepthWO = new CXTypeKind(142);
    public static final CXTypeKind CXType_OCLImage2dArrayMSAADepthWO = new CXTypeKind(143);
    public static final CXTypeKind CXType_OCLImage3dWO = new CXTypeKind(144);
    public static final CXTypeKind CXType_OCLImage1dRW = new CXTypeKind(145);
    public static final CXTypeKind CXType_OCLImage1dArrayRW = new CXTypeKind(146);
    public static final CXTypeKind CXType_OCLImage1dBufferRW = new CXTypeKind(147);
    public static final CXTypeKind CXType_OCLImage2dRW = new CXTypeKind(148);
    public static final CXTypeKind CXType_OCLImage2dArrayRW = new CXTypeKind(149);
    public static final CXTypeKind CXType_OCLImage2dDepthRW = new CXTypeKind(150);
    public static final CXTypeKind CXType_OCLImage2dArrayDepthRW = new CXTypeKind(151);
    public static final CXTypeKind CXType_OCLImage2dMSAARW = new CXTypeKind(152);
    public static final CXTypeKind CXType_OCLImage2dArrayMSAARW = new CXTypeKind(153);
    public static final CXTypeKind CXType_OCLImage2dMSAADepthRW = new CXTypeKind(154);
    public static final CXTypeKind CXType_OCLImage2dArrayMSAADepthRW = new CXTypeKind(155);
    public static final CXTypeKind CXType_OCLImage3dRW = new CXTypeKind(156);
    public static final CXTypeKind CXType_OCLSampler = new CXTypeKind(157);
    public static final CXTypeKind CXType_OCLEvent = new CXTypeKind(158);
    public static final CXTypeKind CXType_OCLQueue = new CXTypeKind(159);
    public static final CXTypeKind CXType_OCLReserveID = new CXTypeKind(160);
    public static final CXTypeKind CXType_ObjCObject = new CXTypeKind(161);
    public static final CXTypeKind CXType_ObjCTypeParam = new CXTypeKind(162);
    public static final CXTypeKind CXType_Attributed = new CXTypeKind(163);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCMcePayload = new CXTypeKind(164);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCImePayload = new CXTypeKind(165);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCRefPayload = new CXTypeKind(166);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCSicPayload = new CXTypeKind(167);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCMceResult = new CXTypeKind(168);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCImeResult = new CXTypeKind(169);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCRefResult = new CXTypeKind(170);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCSicResult = new CXTypeKind(171);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCImeResultSingleReferenceStreamout = new CXTypeKind(172);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCImeResultDualReferenceStreamout = new CXTypeKind(173);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCImeSingleReferenceStreamin = new CXTypeKind(174);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCImeDualReferenceStreamin = new CXTypeKind(175);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCImeResultSingleRefStreamout = new CXTypeKind(172);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCImeResultDualRefStreamout = new CXTypeKind(173);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCImeSingleRefStreamin = new CXTypeKind(174);
    public static final CXTypeKind CXType_OCLIntelSubgroupAVCImeDualRefStreamin = new CXTypeKind(175);
    public static final CXTypeKind CXType_ExtVector = new CXTypeKind(176);
    public static final CXTypeKind CXType_Atomic = new CXTypeKind(177);
    public static final CXTypeKind CXType_BTFTagAttributed = new CXTypeKind(178);
}