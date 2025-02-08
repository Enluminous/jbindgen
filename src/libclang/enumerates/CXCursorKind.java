package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXCursorKind implements I32Op<CXCursorKind>, Info<CXCursorKind> {
    public static final Info.Operations<CXCursorKind> OPERATIONS = I32Op.makeOperations(CXCursorKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXCursorKind(int val) {
        this.val = val;
    }

    public static Array<CXCursorKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXCursorKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXCursorKind> getOperations() {
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

    public static String enumToString(CXCursorKind e) {
        return Utils.enumToString(CXCursorKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXCursorKind that && that.val == val;
    }

    public static final CXCursorKind CXCursor_UnexposedDecl = new CXCursorKind(1);
    public static final CXCursorKind CXCursor_StructDecl = new CXCursorKind(2);
    public static final CXCursorKind CXCursor_UnionDecl = new CXCursorKind(3);
    public static final CXCursorKind CXCursor_ClassDecl = new CXCursorKind(4);
    public static final CXCursorKind CXCursor_EnumDecl = new CXCursorKind(5);
    public static final CXCursorKind CXCursor_FieldDecl = new CXCursorKind(6);
    public static final CXCursorKind CXCursor_EnumConstantDecl = new CXCursorKind(7);
    public static final CXCursorKind CXCursor_FunctionDecl = new CXCursorKind(8);
    public static final CXCursorKind CXCursor_VarDecl = new CXCursorKind(9);
    public static final CXCursorKind CXCursor_ParmDecl = new CXCursorKind(10);
    public static final CXCursorKind CXCursor_ObjCInterfaceDecl = new CXCursorKind(11);
    public static final CXCursorKind CXCursor_ObjCCategoryDecl = new CXCursorKind(12);
    public static final CXCursorKind CXCursor_ObjCProtocolDecl = new CXCursorKind(13);
    public static final CXCursorKind CXCursor_ObjCPropertyDecl = new CXCursorKind(14);
    public static final CXCursorKind CXCursor_ObjCIvarDecl = new CXCursorKind(15);
    public static final CXCursorKind CXCursor_ObjCInstanceMethodDecl = new CXCursorKind(16);
    public static final CXCursorKind CXCursor_ObjCClassMethodDecl = new CXCursorKind(17);
    public static final CXCursorKind CXCursor_ObjCImplementationDecl = new CXCursorKind(18);
    public static final CXCursorKind CXCursor_ObjCCategoryImplDecl = new CXCursorKind(19);
    public static final CXCursorKind CXCursor_TypedefDecl = new CXCursorKind(20);
    public static final CXCursorKind CXCursor_CXXMethod = new CXCursorKind(21);
    public static final CXCursorKind CXCursor_Namespace = new CXCursorKind(22);
    public static final CXCursorKind CXCursor_LinkageSpec = new CXCursorKind(23);
    public static final CXCursorKind CXCursor_Constructor = new CXCursorKind(24);
    public static final CXCursorKind CXCursor_Destructor = new CXCursorKind(25);
    public static final CXCursorKind CXCursor_ConversionFunction = new CXCursorKind(26);
    public static final CXCursorKind CXCursor_TemplateTypeParameter = new CXCursorKind(27);
    public static final CXCursorKind CXCursor_NonTypeTemplateParameter = new CXCursorKind(28);
    public static final CXCursorKind CXCursor_TemplateTemplateParameter = new CXCursorKind(29);
    public static final CXCursorKind CXCursor_FunctionTemplate = new CXCursorKind(30);
    public static final CXCursorKind CXCursor_ClassTemplate = new CXCursorKind(31);
    public static final CXCursorKind CXCursor_ClassTemplatePartialSpecialization = new CXCursorKind(32);
    public static final CXCursorKind CXCursor_NamespaceAlias = new CXCursorKind(33);
    public static final CXCursorKind CXCursor_UsingDirective = new CXCursorKind(34);
    public static final CXCursorKind CXCursor_UsingDeclaration = new CXCursorKind(35);
    public static final CXCursorKind CXCursor_TypeAliasDecl = new CXCursorKind(36);
    public static final CXCursorKind CXCursor_ObjCSynthesizeDecl = new CXCursorKind(37);
    public static final CXCursorKind CXCursor_ObjCDynamicDecl = new CXCursorKind(38);
    public static final CXCursorKind CXCursor_CXXAccessSpecifier = new CXCursorKind(39);
    public static final CXCursorKind CXCursor_FirstDecl = new CXCursorKind(1);
    public static final CXCursorKind CXCursor_LastDecl = new CXCursorKind(39);
    public static final CXCursorKind CXCursor_FirstRef = new CXCursorKind(40);
    public static final CXCursorKind CXCursor_ObjCSuperClassRef = new CXCursorKind(40);
    public static final CXCursorKind CXCursor_ObjCProtocolRef = new CXCursorKind(41);
    public static final CXCursorKind CXCursor_ObjCClassRef = new CXCursorKind(42);
    public static final CXCursorKind CXCursor_TypeRef = new CXCursorKind(43);
    public static final CXCursorKind CXCursor_CXXBaseSpecifier = new CXCursorKind(44);
    public static final CXCursorKind CXCursor_TemplateRef = new CXCursorKind(45);
    public static final CXCursorKind CXCursor_NamespaceRef = new CXCursorKind(46);
    public static final CXCursorKind CXCursor_MemberRef = new CXCursorKind(47);
    public static final CXCursorKind CXCursor_LabelRef = new CXCursorKind(48);
    public static final CXCursorKind CXCursor_OverloadedDeclRef = new CXCursorKind(49);
    public static final CXCursorKind CXCursor_VariableRef = new CXCursorKind(50);
    public static final CXCursorKind CXCursor_LastRef = new CXCursorKind(50);
    public static final CXCursorKind CXCursor_FirstInvalid = new CXCursorKind(70);
    public static final CXCursorKind CXCursor_InvalidFile = new CXCursorKind(70);
    public static final CXCursorKind CXCursor_NoDeclFound = new CXCursorKind(71);
    public static final CXCursorKind CXCursor_NotImplemented = new CXCursorKind(72);
    public static final CXCursorKind CXCursor_InvalidCode = new CXCursorKind(73);
    public static final CXCursorKind CXCursor_LastInvalid = new CXCursorKind(73);
    public static final CXCursorKind CXCursor_FirstExpr = new CXCursorKind(100);
    public static final CXCursorKind CXCursor_UnexposedExpr = new CXCursorKind(100);
    public static final CXCursorKind CXCursor_DeclRefExpr = new CXCursorKind(101);
    public static final CXCursorKind CXCursor_MemberRefExpr = new CXCursorKind(102);
    public static final CXCursorKind CXCursor_CallExpr = new CXCursorKind(103);
    public static final CXCursorKind CXCursor_ObjCMessageExpr = new CXCursorKind(104);
    public static final CXCursorKind CXCursor_BlockExpr = new CXCursorKind(105);
    public static final CXCursorKind CXCursor_IntegerLiteral = new CXCursorKind(106);
    public static final CXCursorKind CXCursor_FloatingLiteral = new CXCursorKind(107);
    public static final CXCursorKind CXCursor_ImaginaryLiteral = new CXCursorKind(108);
    public static final CXCursorKind CXCursor_StringLiteral = new CXCursorKind(109);
    public static final CXCursorKind CXCursor_CharacterLiteral = new CXCursorKind(110);
    public static final CXCursorKind CXCursor_ParenExpr = new CXCursorKind(111);
    public static final CXCursorKind CXCursor_UnaryOperator = new CXCursorKind(112);
    public static final CXCursorKind CXCursor_ArraySubscriptExpr = new CXCursorKind(113);
    public static final CXCursorKind CXCursor_BinaryOperator = new CXCursorKind(114);
    public static final CXCursorKind CXCursor_CompoundAssignOperator = new CXCursorKind(115);
    public static final CXCursorKind CXCursor_ConditionalOperator = new CXCursorKind(116);
    public static final CXCursorKind CXCursor_CStyleCastExpr = new CXCursorKind(117);
    public static final CXCursorKind CXCursor_CompoundLiteralExpr = new CXCursorKind(118);
    public static final CXCursorKind CXCursor_InitListExpr = new CXCursorKind(119);
    public static final CXCursorKind CXCursor_AddrLabelExpr = new CXCursorKind(120);
    public static final CXCursorKind CXCursor_StmtExpr = new CXCursorKind(121);
    public static final CXCursorKind CXCursor_GenericSelectionExpr = new CXCursorKind(122);
    public static final CXCursorKind CXCursor_GNUNullExpr = new CXCursorKind(123);
    public static final CXCursorKind CXCursor_CXXStaticCastExpr = new CXCursorKind(124);
    public static final CXCursorKind CXCursor_CXXDynamicCastExpr = new CXCursorKind(125);
    public static final CXCursorKind CXCursor_CXXReinterpretCastExpr = new CXCursorKind(126);
    public static final CXCursorKind CXCursor_CXXConstCastExpr = new CXCursorKind(127);
    public static final CXCursorKind CXCursor_CXXFunctionalCastExpr = new CXCursorKind(128);
    public static final CXCursorKind CXCursor_CXXTypeidExpr = new CXCursorKind(129);
    public static final CXCursorKind CXCursor_CXXBoolLiteralExpr = new CXCursorKind(130);
    public static final CXCursorKind CXCursor_CXXNullPtrLiteralExpr = new CXCursorKind(131);
    public static final CXCursorKind CXCursor_CXXThisExpr = new CXCursorKind(132);
    public static final CXCursorKind CXCursor_CXXThrowExpr = new CXCursorKind(133);
    public static final CXCursorKind CXCursor_CXXNewExpr = new CXCursorKind(134);
    public static final CXCursorKind CXCursor_CXXDeleteExpr = new CXCursorKind(135);
    public static final CXCursorKind CXCursor_UnaryExpr = new CXCursorKind(136);
    public static final CXCursorKind CXCursor_ObjCStringLiteral = new CXCursorKind(137);
    public static final CXCursorKind CXCursor_ObjCEncodeExpr = new CXCursorKind(138);
    public static final CXCursorKind CXCursor_ObjCSelectorExpr = new CXCursorKind(139);
    public static final CXCursorKind CXCursor_ObjCProtocolExpr = new CXCursorKind(140);
    public static final CXCursorKind CXCursor_ObjCBridgedCastExpr = new CXCursorKind(141);
    public static final CXCursorKind CXCursor_PackExpansionExpr = new CXCursorKind(142);
    public static final CXCursorKind CXCursor_SizeOfPackExpr = new CXCursorKind(143);
    public static final CXCursorKind CXCursor_LambdaExpr = new CXCursorKind(144);
    public static final CXCursorKind CXCursor_ObjCBoolLiteralExpr = new CXCursorKind(145);
    public static final CXCursorKind CXCursor_ObjCSelfExpr = new CXCursorKind(146);
    public static final CXCursorKind CXCursor_OMPArraySectionExpr = new CXCursorKind(147);
    public static final CXCursorKind CXCursor_ObjCAvailabilityCheckExpr = new CXCursorKind(148);
    public static final CXCursorKind CXCursor_FixedPointLiteral = new CXCursorKind(149);
    public static final CXCursorKind CXCursor_OMPArrayShapingExpr = new CXCursorKind(150);
    public static final CXCursorKind CXCursor_OMPIteratorExpr = new CXCursorKind(151);
    public static final CXCursorKind CXCursor_CXXAddrspaceCastExpr = new CXCursorKind(152);
    public static final CXCursorKind CXCursor_ConceptSpecializationExpr = new CXCursorKind(153);
    public static final CXCursorKind CXCursor_RequiresExpr = new CXCursorKind(154);
    public static final CXCursorKind CXCursor_CXXParenListInitExpr = new CXCursorKind(155);
    public static final CXCursorKind CXCursor_LastExpr = new CXCursorKind(155);
    public static final CXCursorKind CXCursor_FirstStmt = new CXCursorKind(200);
    public static final CXCursorKind CXCursor_UnexposedStmt = new CXCursorKind(200);
    public static final CXCursorKind CXCursor_LabelStmt = new CXCursorKind(201);
    public static final CXCursorKind CXCursor_CompoundStmt = new CXCursorKind(202);
    public static final CXCursorKind CXCursor_CaseStmt = new CXCursorKind(203);
    public static final CXCursorKind CXCursor_DefaultStmt = new CXCursorKind(204);
    public static final CXCursorKind CXCursor_IfStmt = new CXCursorKind(205);
    public static final CXCursorKind CXCursor_SwitchStmt = new CXCursorKind(206);
    public static final CXCursorKind CXCursor_WhileStmt = new CXCursorKind(207);
    public static final CXCursorKind CXCursor_DoStmt = new CXCursorKind(208);
    public static final CXCursorKind CXCursor_ForStmt = new CXCursorKind(209);
    public static final CXCursorKind CXCursor_GotoStmt = new CXCursorKind(210);
    public static final CXCursorKind CXCursor_IndirectGotoStmt = new CXCursorKind(211);
    public static final CXCursorKind CXCursor_ContinueStmt = new CXCursorKind(212);
    public static final CXCursorKind CXCursor_BreakStmt = new CXCursorKind(213);
    public static final CXCursorKind CXCursor_ReturnStmt = new CXCursorKind(214);
    public static final CXCursorKind CXCursor_GCCAsmStmt = new CXCursorKind(215);
    public static final CXCursorKind CXCursor_AsmStmt = new CXCursorKind(215);
    public static final CXCursorKind CXCursor_ObjCAtTryStmt = new CXCursorKind(216);
    public static final CXCursorKind CXCursor_ObjCAtCatchStmt = new CXCursorKind(217);
    public static final CXCursorKind CXCursor_ObjCAtFinallyStmt = new CXCursorKind(218);
    public static final CXCursorKind CXCursor_ObjCAtThrowStmt = new CXCursorKind(219);
    public static final CXCursorKind CXCursor_ObjCAtSynchronizedStmt = new CXCursorKind(220);
    public static final CXCursorKind CXCursor_ObjCAutoreleasePoolStmt = new CXCursorKind(221);
    public static final CXCursorKind CXCursor_ObjCForCollectionStmt = new CXCursorKind(222);
    public static final CXCursorKind CXCursor_CXXCatchStmt = new CXCursorKind(223);
    public static final CXCursorKind CXCursor_CXXTryStmt = new CXCursorKind(224);
    public static final CXCursorKind CXCursor_CXXForRangeStmt = new CXCursorKind(225);
    public static final CXCursorKind CXCursor_SEHTryStmt = new CXCursorKind(226);
    public static final CXCursorKind CXCursor_SEHExceptStmt = new CXCursorKind(227);
    public static final CXCursorKind CXCursor_SEHFinallyStmt = new CXCursorKind(228);
    public static final CXCursorKind CXCursor_MSAsmStmt = new CXCursorKind(229);
    public static final CXCursorKind CXCursor_NullStmt = new CXCursorKind(230);
    public static final CXCursorKind CXCursor_DeclStmt = new CXCursorKind(231);
    public static final CXCursorKind CXCursor_OMPParallelDirective = new CXCursorKind(232);
    public static final CXCursorKind CXCursor_OMPSimdDirective = new CXCursorKind(233);
    public static final CXCursorKind CXCursor_OMPForDirective = new CXCursorKind(234);
    public static final CXCursorKind CXCursor_OMPSectionsDirective = new CXCursorKind(235);
    public static final CXCursorKind CXCursor_OMPSectionDirective = new CXCursorKind(236);
    public static final CXCursorKind CXCursor_OMPSingleDirective = new CXCursorKind(237);
    public static final CXCursorKind CXCursor_OMPParallelForDirective = new CXCursorKind(238);
    public static final CXCursorKind CXCursor_OMPParallelSectionsDirective = new CXCursorKind(239);
    public static final CXCursorKind CXCursor_OMPTaskDirective = new CXCursorKind(240);
    public static final CXCursorKind CXCursor_OMPMasterDirective = new CXCursorKind(241);
    public static final CXCursorKind CXCursor_OMPCriticalDirective = new CXCursorKind(242);
    public static final CXCursorKind CXCursor_OMPTaskyieldDirective = new CXCursorKind(243);
    public static final CXCursorKind CXCursor_OMPBarrierDirective = new CXCursorKind(244);
    public static final CXCursorKind CXCursor_OMPTaskwaitDirective = new CXCursorKind(245);
    public static final CXCursorKind CXCursor_OMPFlushDirective = new CXCursorKind(246);
    public static final CXCursorKind CXCursor_SEHLeaveStmt = new CXCursorKind(247);
    public static final CXCursorKind CXCursor_OMPOrderedDirective = new CXCursorKind(248);
    public static final CXCursorKind CXCursor_OMPAtomicDirective = new CXCursorKind(249);
    public static final CXCursorKind CXCursor_OMPForSimdDirective = new CXCursorKind(250);
    public static final CXCursorKind CXCursor_OMPParallelForSimdDirective = new CXCursorKind(251);
    public static final CXCursorKind CXCursor_OMPTargetDirective = new CXCursorKind(252);
    public static final CXCursorKind CXCursor_OMPTeamsDirective = new CXCursorKind(253);
    public static final CXCursorKind CXCursor_OMPTaskgroupDirective = new CXCursorKind(254);
    public static final CXCursorKind CXCursor_OMPCancellationPointDirective = new CXCursorKind(255);
    public static final CXCursorKind CXCursor_OMPCancelDirective = new CXCursorKind(256);
    public static final CXCursorKind CXCursor_OMPTargetDataDirective = new CXCursorKind(257);
    public static final CXCursorKind CXCursor_OMPTaskLoopDirective = new CXCursorKind(258);
    public static final CXCursorKind CXCursor_OMPTaskLoopSimdDirective = new CXCursorKind(259);
    public static final CXCursorKind CXCursor_OMPDistributeDirective = new CXCursorKind(260);
    public static final CXCursorKind CXCursor_OMPTargetEnterDataDirective = new CXCursorKind(261);
    public static final CXCursorKind CXCursor_OMPTargetExitDataDirective = new CXCursorKind(262);
    public static final CXCursorKind CXCursor_OMPTargetParallelDirective = new CXCursorKind(263);
    public static final CXCursorKind CXCursor_OMPTargetParallelForDirective = new CXCursorKind(264);
    public static final CXCursorKind CXCursor_OMPTargetUpdateDirective = new CXCursorKind(265);
    public static final CXCursorKind CXCursor_OMPDistributeParallelForDirective = new CXCursorKind(266);
    public static final CXCursorKind CXCursor_OMPDistributeParallelForSimdDirective = new CXCursorKind(267);
    public static final CXCursorKind CXCursor_OMPDistributeSimdDirective = new CXCursorKind(268);
    public static final CXCursorKind CXCursor_OMPTargetParallelForSimdDirective = new CXCursorKind(269);
    public static final CXCursorKind CXCursor_OMPTargetSimdDirective = new CXCursorKind(270);
    public static final CXCursorKind CXCursor_OMPTeamsDistributeDirective = new CXCursorKind(271);
    public static final CXCursorKind CXCursor_OMPTeamsDistributeSimdDirective = new CXCursorKind(272);
    public static final CXCursorKind CXCursor_OMPTeamsDistributeParallelForSimdDirective = new CXCursorKind(273);
    public static final CXCursorKind CXCursor_OMPTeamsDistributeParallelForDirective = new CXCursorKind(274);
    public static final CXCursorKind CXCursor_OMPTargetTeamsDirective = new CXCursorKind(275);
    public static final CXCursorKind CXCursor_OMPTargetTeamsDistributeDirective = new CXCursorKind(276);
    public static final CXCursorKind CXCursor_OMPTargetTeamsDistributeParallelForDirective = new CXCursorKind(277);
    public static final CXCursorKind CXCursor_OMPTargetTeamsDistributeParallelForSimdDirective = new CXCursorKind(278);
    public static final CXCursorKind CXCursor_OMPTargetTeamsDistributeSimdDirective = new CXCursorKind(279);
    public static final CXCursorKind CXCursor_BuiltinBitCastExpr = new CXCursorKind(280);
    public static final CXCursorKind CXCursor_OMPMasterTaskLoopDirective = new CXCursorKind(281);
    public static final CXCursorKind CXCursor_OMPParallelMasterTaskLoopDirective = new CXCursorKind(282);
    public static final CXCursorKind CXCursor_OMPMasterTaskLoopSimdDirective = new CXCursorKind(283);
    public static final CXCursorKind CXCursor_OMPParallelMasterTaskLoopSimdDirective = new CXCursorKind(284);
    public static final CXCursorKind CXCursor_OMPParallelMasterDirective = new CXCursorKind(285);
    public static final CXCursorKind CXCursor_OMPDepobjDirective = new CXCursorKind(286);
    public static final CXCursorKind CXCursor_OMPScanDirective = new CXCursorKind(287);
    public static final CXCursorKind CXCursor_OMPTileDirective = new CXCursorKind(288);
    public static final CXCursorKind CXCursor_OMPCanonicalLoop = new CXCursorKind(289);
    public static final CXCursorKind CXCursor_OMPInteropDirective = new CXCursorKind(290);
    public static final CXCursorKind CXCursor_OMPDispatchDirective = new CXCursorKind(291);
    public static final CXCursorKind CXCursor_OMPMaskedDirective = new CXCursorKind(292);
    public static final CXCursorKind CXCursor_OMPUnrollDirective = new CXCursorKind(293);
    public static final CXCursorKind CXCursor_OMPMetaDirective = new CXCursorKind(294);
    public static final CXCursorKind CXCursor_OMPGenericLoopDirective = new CXCursorKind(295);
    public static final CXCursorKind CXCursor_OMPTeamsGenericLoopDirective = new CXCursorKind(296);
    public static final CXCursorKind CXCursor_OMPTargetTeamsGenericLoopDirective = new CXCursorKind(297);
    public static final CXCursorKind CXCursor_OMPParallelGenericLoopDirective = new CXCursorKind(298);
    public static final CXCursorKind CXCursor_OMPTargetParallelGenericLoopDirective = new CXCursorKind(299);
    public static final CXCursorKind CXCursor_OMPParallelMaskedDirective = new CXCursorKind(300);
    public static final CXCursorKind CXCursor_OMPMaskedTaskLoopDirective = new CXCursorKind(301);
    public static final CXCursorKind CXCursor_OMPMaskedTaskLoopSimdDirective = new CXCursorKind(302);
    public static final CXCursorKind CXCursor_OMPParallelMaskedTaskLoopDirective = new CXCursorKind(303);
    public static final CXCursorKind CXCursor_OMPParallelMaskedTaskLoopSimdDirective = new CXCursorKind(304);
    public static final CXCursorKind CXCursor_OMPErrorDirective = new CXCursorKind(305);
    public static final CXCursorKind CXCursor_LastStmt = new CXCursorKind(305);
    public static final CXCursorKind CXCursor_TranslationUnit = new CXCursorKind(350);
    public static final CXCursorKind CXCursor_FirstAttr = new CXCursorKind(400);
    public static final CXCursorKind CXCursor_UnexposedAttr = new CXCursorKind(400);
    public static final CXCursorKind CXCursor_IBActionAttr = new CXCursorKind(401);
    public static final CXCursorKind CXCursor_IBOutletAttr = new CXCursorKind(402);
    public static final CXCursorKind CXCursor_IBOutletCollectionAttr = new CXCursorKind(403);
    public static final CXCursorKind CXCursor_CXXFinalAttr = new CXCursorKind(404);
    public static final CXCursorKind CXCursor_CXXOverrideAttr = new CXCursorKind(405);
    public static final CXCursorKind CXCursor_AnnotateAttr = new CXCursorKind(406);
    public static final CXCursorKind CXCursor_AsmLabelAttr = new CXCursorKind(407);
    public static final CXCursorKind CXCursor_PackedAttr = new CXCursorKind(408);
    public static final CXCursorKind CXCursor_PureAttr = new CXCursorKind(409);
    public static final CXCursorKind CXCursor_ConstAttr = new CXCursorKind(410);
    public static final CXCursorKind CXCursor_NoDuplicateAttr = new CXCursorKind(411);
    public static final CXCursorKind CXCursor_CUDAConstantAttr = new CXCursorKind(412);
    public static final CXCursorKind CXCursor_CUDADeviceAttr = new CXCursorKind(413);
    public static final CXCursorKind CXCursor_CUDAGlobalAttr = new CXCursorKind(414);
    public static final CXCursorKind CXCursor_CUDAHostAttr = new CXCursorKind(415);
    public static final CXCursorKind CXCursor_CUDASharedAttr = new CXCursorKind(416);
    public static final CXCursorKind CXCursor_VisibilityAttr = new CXCursorKind(417);
    public static final CXCursorKind CXCursor_DLLExport = new CXCursorKind(418);
    public static final CXCursorKind CXCursor_DLLImport = new CXCursorKind(419);
    public static final CXCursorKind CXCursor_NSReturnsRetained = new CXCursorKind(420);
    public static final CXCursorKind CXCursor_NSReturnsNotRetained = new CXCursorKind(421);
    public static final CXCursorKind CXCursor_NSReturnsAutoreleased = new CXCursorKind(422);
    public static final CXCursorKind CXCursor_NSConsumesSelf = new CXCursorKind(423);
    public static final CXCursorKind CXCursor_NSConsumed = new CXCursorKind(424);
    public static final CXCursorKind CXCursor_ObjCException = new CXCursorKind(425);
    public static final CXCursorKind CXCursor_ObjCNSObject = new CXCursorKind(426);
    public static final CXCursorKind CXCursor_ObjCIndependentClass = new CXCursorKind(427);
    public static final CXCursorKind CXCursor_ObjCPreciseLifetime = new CXCursorKind(428);
    public static final CXCursorKind CXCursor_ObjCReturnsInnerPointer = new CXCursorKind(429);
    public static final CXCursorKind CXCursor_ObjCRequiresSuper = new CXCursorKind(430);
    public static final CXCursorKind CXCursor_ObjCRootClass = new CXCursorKind(431);
    public static final CXCursorKind CXCursor_ObjCSubclassingRestricted = new CXCursorKind(432);
    public static final CXCursorKind CXCursor_ObjCExplicitProtocolImpl = new CXCursorKind(433);
    public static final CXCursorKind CXCursor_ObjCDesignatedInitializer = new CXCursorKind(434);
    public static final CXCursorKind CXCursor_ObjCRuntimeVisible = new CXCursorKind(435);
    public static final CXCursorKind CXCursor_ObjCBoxable = new CXCursorKind(436);
    public static final CXCursorKind CXCursor_FlagEnum = new CXCursorKind(437);
    public static final CXCursorKind CXCursor_ConvergentAttr = new CXCursorKind(438);
    public static final CXCursorKind CXCursor_WarnUnusedAttr = new CXCursorKind(439);
    public static final CXCursorKind CXCursor_WarnUnusedResultAttr = new CXCursorKind(440);
    public static final CXCursorKind CXCursor_AlignedAttr = new CXCursorKind(441);
    public static final CXCursorKind CXCursor_LastAttr = new CXCursorKind(441);
    public static final CXCursorKind CXCursor_PreprocessingDirective = new CXCursorKind(500);
    public static final CXCursorKind CXCursor_MacroDefinition = new CXCursorKind(501);
    public static final CXCursorKind CXCursor_MacroExpansion = new CXCursorKind(502);
    public static final CXCursorKind CXCursor_MacroInstantiation = new CXCursorKind(502);
    public static final CXCursorKind CXCursor_InclusionDirective = new CXCursorKind(503);
    public static final CXCursorKind CXCursor_FirstPreprocessing = new CXCursorKind(500);
    public static final CXCursorKind CXCursor_LastPreprocessing = new CXCursorKind(503);
    public static final CXCursorKind CXCursor_ModuleImportDecl = new CXCursorKind(600);
    public static final CXCursorKind CXCursor_TypeAliasTemplateDecl = new CXCursorKind(601);
    public static final CXCursorKind CXCursor_StaticAssert = new CXCursorKind(602);
    public static final CXCursorKind CXCursor_FriendDecl = new CXCursorKind(603);
    public static final CXCursorKind CXCursor_ConceptDecl = new CXCursorKind(604);
    public static final CXCursorKind CXCursor_FirstExtraDecl = new CXCursorKind(600);
    public static final CXCursorKind CXCursor_LastExtraDecl = new CXCursorKind(604);
    public static final CXCursorKind CXCursor_OverloadCandidate = new CXCursorKind(700);
}