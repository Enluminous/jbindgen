package libclang;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import libclang.LibclangSymbolProvider;
import libclang.common.FP64;
import libclang.common.I32;
import libclang.common.I32I;
import libclang.common.I64;
import libclang.common.I64I;
import libclang.common.I8;
import libclang.common.I8I;
import libclang.common.Info;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructI;
import libclang.common.Utils;
import libclang.enumerates.CXAvailabilityKind;
import libclang.enumerates.CXBinaryOperatorKind;
import libclang.enumerates.CXCallingConv;
import libclang.enumerates.CXCompletionChunkKind;
import libclang.enumerates.CXCursorKind;
import libclang.enumerates.CXDiagnosticSeverity;
import libclang.enumerates.CXErrorCode;
import libclang.enumerates.CXEvalResultKind;
import libclang.enumerates.CXIdxEntityKind;
import libclang.enumerates.CXLanguageKind;
import libclang.enumerates.CXLinkageKind;
import libclang.enumerates.CXLoadDiag_Error;
import libclang.enumerates.CXPrintingPolicyProperty;
import libclang.enumerates.CXRefQualifierKind;
import libclang.enumerates.CXResult;
import libclang.enumerates.CXTLSKind;
import libclang.enumerates.CXTUResourceUsageKind;
import libclang.enumerates.CXTemplateArgumentKind;
import libclang.enumerates.CXTokenKind;
import libclang.enumerates.CXTypeKind;
import libclang.enumerates.CXTypeNullabilityKind;
import libclang.enumerates.CXUnaryOperatorKind;
import libclang.enumerates.CXVisibilityKind;
import libclang.enumerates.CX_CXXAccessSpecifier;
import libclang.enumerates.CX_StorageClass;
import libclang.functions.CXCursorVisitor;
import libclang.functions.CXFieldVisitor;
import libclang.functions.CXInclusionVisitor;
import libclang.functions.func_void_void_ptr_;
import libclang.opaques.sigevent;
import libclang.structs.CXCodeCompleteResults;
import libclang.structs.CXCompletionResult;
import libclang.structs.CXCursor;
import libclang.structs.CXCursorAndRangeVisitor;
import libclang.structs.CXFileUniqueID;
import libclang.structs.CXIdxAttrInfo;
import libclang.structs.CXIdxCXXClassDeclInfo;
import libclang.structs.CXIdxContainerInfo;
import libclang.structs.CXIdxDeclInfo;
import libclang.structs.CXIdxEntityInfo;
import libclang.structs.CXIdxIBOutletCollectionAttrInfo;
import libclang.structs.CXIdxLoc;
import libclang.structs.CXIdxObjCCategoryDeclInfo;
import libclang.structs.CXIdxObjCContainerDeclInfo;
import libclang.structs.CXIdxObjCInterfaceDeclInfo;
import libclang.structs.CXIdxObjCPropertyDeclInfo;
import libclang.structs.CXIdxObjCProtocolRefListInfo;
import libclang.structs.CXIndexOptions;
import libclang.structs.CXPlatformAvailability;
import libclang.structs.CXSourceLocation;
import libclang.structs.CXSourceRange;
import libclang.structs.CXSourceRangeList;
import libclang.structs.CXString;
import libclang.structs.CXStringSet;
import libclang.structs.CXTUResourceUsage;
import libclang.structs.CXToken;
import libclang.structs.CXType;
import libclang.structs.CXUnsavedFile;
import libclang.structs.IndexerCallbacks;
import libclang.structs.itimerspec;
import libclang.structs.timespec;
import libclang.structs.tm;
import libclang.values.CXClientData;
import libclang.values.CXCompletionString;
import libclang.values.CXCursorAndRangeVisitorBlock;
import libclang.values.CXCursorSet;
import libclang.values.CXCursorVisitorBlock;
import libclang.values.CXDiagnostic;
import libclang.values.CXDiagnosticSet;
import libclang.values.CXEvalResult;
import libclang.values.CXFile;
import libclang.values.CXIdxClientContainer;
import libclang.values.CXIdxClientEntity;
import libclang.values.CXIdxClientFile;
import libclang.values.CXIndex;
import libclang.values.CXIndexAction;
import libclang.values.CXModule;
import libclang.values.CXModuleMapDescriptor;
import libclang.values.CXPrintingPolicy;
import libclang.values.CXRemapping;
import libclang.values.CXTargetInfo;
import libclang.values.CXTranslationUnit;
import libclang.values.CXVirtualFileOverlay;
import libclang.values.clock_t;
import libclang.values.clockid_t;
import libclang.values.locale_t;
import libclang.values.pid_t;
import libclang.values.size_t;
import libclang.values.time_t;
import libclang.values.timer_t;
public final class LibclangFunctionSymbols {
    private static MethodHandle clang_getCString;

    private static MemorySegment clang_getCString$Raw(MemorySegment string) {
        if (clang_getCString == null) {
            clang_getCString = LibclangSymbolProvider.downcallHandle("clang_getCString", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCString"));
        }
        try {
            return (MemorySegment) clang_getCString.invoke(string);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<I8> clang_getCString(StructI<? extends CXString> string) {
        return new Ptr<I8>(clang_getCString$Raw(string.operator().value()), I8.OPERATIONS);
    }

    private static MethodHandle clang_disposeString;

    private static void clang_disposeString$Raw(MemorySegment string) {
        if (clang_disposeString == null) {
            clang_disposeString = LibclangSymbolProvider.downcallHandle("clang_disposeString", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_disposeString"));
        }
        try {
            clang_disposeString.invoke(string);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_disposeString(StructI<? extends CXString> string) {
        clang_disposeString$Raw(string.operator().value());
    }

    private static MethodHandle clang_disposeStringSet;

    private static void clang_disposeStringSet$Raw(MemorySegment set) {
        if (clang_disposeStringSet == null) {
            clang_disposeStringSet = LibclangSymbolProvider.downcallHandle("clang_disposeStringSet", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_disposeStringSet"));
        }
        try {
            clang_disposeStringSet.invoke(set);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_disposeStringSet(PtrI<? extends StructI<? extends CXStringSet>> set) {
        clang_disposeStringSet$Raw(set.operator().value());
    }

    private static MethodHandle clang_getBuildSessionTimestamp;

    private static long clang_getBuildSessionTimestamp$Raw() {
        if (clang_getBuildSessionTimestamp == null) {
            clang_getBuildSessionTimestamp = LibclangSymbolProvider.downcallHandle("clang_getBuildSessionTimestamp", FunctionDescriptor.of(ValueLayout.JAVA_LONG)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getBuildSessionTimestamp"));
        }
        try {
            return (long) clang_getBuildSessionTimestamp.invoke();
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I64 clang_getBuildSessionTimestamp() {
        return new I64(clang_getBuildSessionTimestamp$Raw());
    }

    private static MethodHandle clang_VirtualFileOverlay_create;

    private static MemorySegment clang_VirtualFileOverlay_create$Raw(int options) {
        if (clang_VirtualFileOverlay_create == null) {
            clang_VirtualFileOverlay_create = LibclangSymbolProvider.downcallHandle("clang_VirtualFileOverlay_create", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_VirtualFileOverlay_create"));
        }
        try {
            return (MemorySegment) clang_VirtualFileOverlay_create.invoke(options);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXVirtualFileOverlay clang_VirtualFileOverlay_create(I32I<?> options) {
        return new CXVirtualFileOverlay(clang_VirtualFileOverlay_create$Raw(options.operator().value()));
    }

    private static MethodHandle clang_VirtualFileOverlay_addFileMapping;

    private static int clang_VirtualFileOverlay_addFileMapping$Raw(MemorySegment arg0, MemorySegment virtualPath, MemorySegment realPath) {
        if (clang_VirtualFileOverlay_addFileMapping == null) {
            clang_VirtualFileOverlay_addFileMapping = LibclangSymbolProvider.downcallHandle("clang_VirtualFileOverlay_addFileMapping", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_VirtualFileOverlay_addFileMapping"));
        }
        try {
            return (int) clang_VirtualFileOverlay_addFileMapping.invoke(arg0, virtualPath, realPath);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXErrorCode clang_VirtualFileOverlay_addFileMapping(CXVirtualFileOverlay arg0, PtrI<? extends I8I<?>> virtualPath, PtrI<? extends I8I<?>> realPath) {
        return new CXErrorCode(clang_VirtualFileOverlay_addFileMapping$Raw(arg0.operator().value(), virtualPath.operator().value(), realPath.operator().value()));
    }

    private static MethodHandle clang_VirtualFileOverlay_setCaseSensitivity;

    private static int clang_VirtualFileOverlay_setCaseSensitivity$Raw(MemorySegment arg0, int caseSensitive) {
        if (clang_VirtualFileOverlay_setCaseSensitivity == null) {
            clang_VirtualFileOverlay_setCaseSensitivity = LibclangSymbolProvider.downcallHandle("clang_VirtualFileOverlay_setCaseSensitivity", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_VirtualFileOverlay_setCaseSensitivity"));
        }
        try {
            return (int) clang_VirtualFileOverlay_setCaseSensitivity.invoke(arg0, caseSensitive);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXErrorCode clang_VirtualFileOverlay_setCaseSensitivity(CXVirtualFileOverlay arg0, I32I<?> caseSensitive) {
        return new CXErrorCode(clang_VirtualFileOverlay_setCaseSensitivity$Raw(arg0.operator().value(), caseSensitive.operator().value()));
    }

    private static MethodHandle clang_VirtualFileOverlay_writeToBuffer;

    private static int clang_VirtualFileOverlay_writeToBuffer$Raw(MemorySegment arg0, int options, MemorySegment out_buffer_ptr, MemorySegment out_buffer_size) {
        if (clang_VirtualFileOverlay_writeToBuffer == null) {
            clang_VirtualFileOverlay_writeToBuffer = LibclangSymbolProvider.downcallHandle("clang_VirtualFileOverlay_writeToBuffer", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_VirtualFileOverlay_writeToBuffer"));
        }
        try {
            return (int) clang_VirtualFileOverlay_writeToBuffer.invoke(arg0, options, out_buffer_ptr, out_buffer_size);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXErrorCode clang_VirtualFileOverlay_writeToBuffer(CXVirtualFileOverlay arg0, I32I<?> options, PtrI<? extends PtrI<? extends I8I<?>>> out_buffer_ptr, PtrI<? extends I32I<?>> out_buffer_size) {
        return new CXErrorCode(clang_VirtualFileOverlay_writeToBuffer$Raw(arg0.operator().value(), options.operator().value(), out_buffer_ptr.operator().value(), out_buffer_size.operator().value()));
    }

    private static MethodHandle clang_free;

    private static void clang_free$Raw(MemorySegment buffer) {
        if (clang_free == null) {
            clang_free = LibclangSymbolProvider.downcallHandle("clang_free", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_free"));
        }
        try {
            clang_free.invoke(buffer);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_free(PtrI<?> buffer) {
        clang_free$Raw(buffer.operator().value());
    }

    private static MethodHandle clang_VirtualFileOverlay_dispose;

    private static void clang_VirtualFileOverlay_dispose$Raw(MemorySegment arg0) {
        if (clang_VirtualFileOverlay_dispose == null) {
            clang_VirtualFileOverlay_dispose = LibclangSymbolProvider.downcallHandle("clang_VirtualFileOverlay_dispose", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_VirtualFileOverlay_dispose"));
        }
        try {
            clang_VirtualFileOverlay_dispose.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_VirtualFileOverlay_dispose(CXVirtualFileOverlay arg0) {
        clang_VirtualFileOverlay_dispose$Raw(arg0.operator().value());
    }

    private static MethodHandle clang_ModuleMapDescriptor_create;

    private static MemorySegment clang_ModuleMapDescriptor_create$Raw(int options) {
        if (clang_ModuleMapDescriptor_create == null) {
            clang_ModuleMapDescriptor_create = LibclangSymbolProvider.downcallHandle("clang_ModuleMapDescriptor_create", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_ModuleMapDescriptor_create"));
        }
        try {
            return (MemorySegment) clang_ModuleMapDescriptor_create.invoke(options);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXModuleMapDescriptor clang_ModuleMapDescriptor_create(I32I<?> options) {
        return new CXModuleMapDescriptor(clang_ModuleMapDescriptor_create$Raw(options.operator().value()));
    }

    private static MethodHandle clang_ModuleMapDescriptor_setFrameworkModuleName;

    private static int clang_ModuleMapDescriptor_setFrameworkModuleName$Raw(MemorySegment arg0, MemorySegment name) {
        if (clang_ModuleMapDescriptor_setFrameworkModuleName == null) {
            clang_ModuleMapDescriptor_setFrameworkModuleName = LibclangSymbolProvider.downcallHandle("clang_ModuleMapDescriptor_setFrameworkModuleName", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_ModuleMapDescriptor_setFrameworkModuleName"));
        }
        try {
            return (int) clang_ModuleMapDescriptor_setFrameworkModuleName.invoke(arg0, name);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXErrorCode clang_ModuleMapDescriptor_setFrameworkModuleName(CXModuleMapDescriptor arg0, PtrI<? extends I8I<?>> name) {
        return new CXErrorCode(clang_ModuleMapDescriptor_setFrameworkModuleName$Raw(arg0.operator().value(), name.operator().value()));
    }

    private static MethodHandle clang_ModuleMapDescriptor_setUmbrellaHeader;

    private static int clang_ModuleMapDescriptor_setUmbrellaHeader$Raw(MemorySegment arg0, MemorySegment name) {
        if (clang_ModuleMapDescriptor_setUmbrellaHeader == null) {
            clang_ModuleMapDescriptor_setUmbrellaHeader = LibclangSymbolProvider.downcallHandle("clang_ModuleMapDescriptor_setUmbrellaHeader", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_ModuleMapDescriptor_setUmbrellaHeader"));
        }
        try {
            return (int) clang_ModuleMapDescriptor_setUmbrellaHeader.invoke(arg0, name);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXErrorCode clang_ModuleMapDescriptor_setUmbrellaHeader(CXModuleMapDescriptor arg0, PtrI<? extends I8I<?>> name) {
        return new CXErrorCode(clang_ModuleMapDescriptor_setUmbrellaHeader$Raw(arg0.operator().value(), name.operator().value()));
    }

    private static MethodHandle clang_ModuleMapDescriptor_writeToBuffer;

    private static int clang_ModuleMapDescriptor_writeToBuffer$Raw(MemorySegment arg0, int options, MemorySegment out_buffer_ptr, MemorySegment out_buffer_size) {
        if (clang_ModuleMapDescriptor_writeToBuffer == null) {
            clang_ModuleMapDescriptor_writeToBuffer = LibclangSymbolProvider.downcallHandle("clang_ModuleMapDescriptor_writeToBuffer", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_ModuleMapDescriptor_writeToBuffer"));
        }
        try {
            return (int) clang_ModuleMapDescriptor_writeToBuffer.invoke(arg0, options, out_buffer_ptr, out_buffer_size);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXErrorCode clang_ModuleMapDescriptor_writeToBuffer(CXModuleMapDescriptor arg0, I32I<?> options, PtrI<? extends PtrI<? extends I8I<?>>> out_buffer_ptr, PtrI<? extends I32I<?>> out_buffer_size) {
        return new CXErrorCode(clang_ModuleMapDescriptor_writeToBuffer$Raw(arg0.operator().value(), options.operator().value(), out_buffer_ptr.operator().value(), out_buffer_size.operator().value()));
    }

    private static MethodHandle clang_ModuleMapDescriptor_dispose;

    private static void clang_ModuleMapDescriptor_dispose$Raw(MemorySegment arg0) {
        if (clang_ModuleMapDescriptor_dispose == null) {
            clang_ModuleMapDescriptor_dispose = LibclangSymbolProvider.downcallHandle("clang_ModuleMapDescriptor_dispose", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_ModuleMapDescriptor_dispose"));
        }
        try {
            clang_ModuleMapDescriptor_dispose.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_ModuleMapDescriptor_dispose(CXModuleMapDescriptor arg0) {
        clang_ModuleMapDescriptor_dispose$Raw(arg0.operator().value());
    }

    private static MethodHandle clock;

    private static long clock$Raw() {
        if (clock == null) {
            clock = LibclangSymbolProvider.downcallHandle("clock", FunctionDescriptor.of(ValueLayout.JAVA_LONG)).orElseThrow(() -> new Utils.SymbolNotFound("clock"));
        }
        try {
            return (long) clock.invoke();
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static clock_t clock() {
        return new clock_t(clock$Raw());
    }

    private static MethodHandle time;

    private static long time$Raw(MemorySegment __timer) {
        if (time == null) {
            time = LibclangSymbolProvider.downcallHandle("time", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("time"));
        }
        try {
            return (long) time.invoke(__timer);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static time_t time(PtrI<? extends I64I<? extends time_t>> __timer) {
        return new time_t(time$Raw(__timer.operator().value()));
    }

    private static MethodHandle difftime;

    private static double difftime$Raw(long __time1, long __time0) {
        if (difftime == null) {
            difftime = LibclangSymbolProvider.downcallHandle("difftime", FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG)).orElseThrow(() -> new Utils.SymbolNotFound("difftime"));
        }
        try {
            return (double) difftime.invoke(__time1, __time0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static FP64 difftime(I64I<? extends time_t> __time1, I64I<? extends time_t> __time0) {
        return new FP64(difftime$Raw(__time1.operator().value(), __time0.operator().value()));
    }

    private static MethodHandle mktime;

    private static long mktime$Raw(MemorySegment __tp) {
        if (mktime == null) {
            mktime = LibclangSymbolProvider.downcallHandle("mktime", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("mktime"));
        }
        try {
            return (long) mktime.invoke(__tp);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static time_t mktime(PtrI<? extends StructI<? extends tm>> __tp) {
        return new time_t(mktime$Raw(__tp.operator().value()));
    }

    private static MethodHandle strftime;

    private static long strftime$Raw(MemorySegment __s, long __maxsize, MemorySegment __format, MemorySegment __tp) {
        if (strftime == null) {
            strftime = LibclangSymbolProvider.downcallHandle("strftime", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("strftime"));
        }
        try {
            return (long) strftime.invoke(__s, __maxsize, __format, __tp);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static size_t strftime(PtrI<? extends I8I<?>> __s, I64I<? extends size_t> __maxsize, PtrI<? extends I8I<?>> __format, PtrI<? extends StructI<? extends tm>> __tp) {
        return new size_t(strftime$Raw(__s.operator().value(), __maxsize.operator().value(), __format.operator().value(), __tp.operator().value()));
    }

    private static MethodHandle strftime_l;

    private static long strftime_l$Raw(MemorySegment __s, long __maxsize, MemorySegment __format, MemorySegment __tp, MemorySegment __loc) {
        if (strftime_l == null) {
            strftime_l = LibclangSymbolProvider.downcallHandle("strftime_l", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("strftime_l"));
        }
        try {
            return (long) strftime_l.invoke(__s, __maxsize, __format, __tp, __loc);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static size_t strftime_l(PtrI<? extends I8I<?>> __s, I64I<? extends size_t> __maxsize, PtrI<? extends I8I<?>> __format, PtrI<? extends StructI<? extends tm>> __tp, locale_t __loc) {
        return new size_t(strftime_l$Raw(__s.operator().value(), __maxsize.operator().value(), __format.operator().value(), __tp.operator().value(), __loc.operator().value()));
    }

    private static MethodHandle gmtime;

    private static MemorySegment gmtime$Raw(MemorySegment __timer) {
        if (gmtime == null) {
            gmtime = LibclangSymbolProvider.downcallHandle("gmtime", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("gmtime"));
        }
        try {
            return (MemorySegment) gmtime.invoke(__timer);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<tm> gmtime(PtrI<? extends I64I<? extends time_t>> __timer) {
        return new Ptr<tm>(gmtime$Raw(__timer.operator().value()), tm.OPERATIONS);
    }

    private static MethodHandle localtime;

    private static MemorySegment localtime$Raw(MemorySegment __timer) {
        if (localtime == null) {
            localtime = LibclangSymbolProvider.downcallHandle("localtime", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("localtime"));
        }
        try {
            return (MemorySegment) localtime.invoke(__timer);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<tm> localtime(PtrI<? extends I64I<? extends time_t>> __timer) {
        return new Ptr<tm>(localtime$Raw(__timer.operator().value()), tm.OPERATIONS);
    }

    private static MethodHandle gmtime_r;

    private static MemorySegment gmtime_r$Raw(MemorySegment __timer, MemorySegment __tp) {
        if (gmtime_r == null) {
            gmtime_r = LibclangSymbolProvider.downcallHandle("gmtime_r", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("gmtime_r"));
        }
        try {
            return (MemorySegment) gmtime_r.invoke(__timer, __tp);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<tm> gmtime_r(PtrI<? extends I64I<? extends time_t>> __timer, PtrI<? extends StructI<? extends tm>> __tp) {
        return new Ptr<tm>(gmtime_r$Raw(__timer.operator().value(), __tp.operator().value()), tm.OPERATIONS);
    }

    private static MethodHandle localtime_r;

    private static MemorySegment localtime_r$Raw(MemorySegment __timer, MemorySegment __tp) {
        if (localtime_r == null) {
            localtime_r = LibclangSymbolProvider.downcallHandle("localtime_r", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("localtime_r"));
        }
        try {
            return (MemorySegment) localtime_r.invoke(__timer, __tp);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<tm> localtime_r(PtrI<? extends I64I<? extends time_t>> __timer, PtrI<? extends StructI<? extends tm>> __tp) {
        return new Ptr<tm>(localtime_r$Raw(__timer.operator().value(), __tp.operator().value()), tm.OPERATIONS);
    }

    private static MethodHandle asctime;

    private static MemorySegment asctime$Raw(MemorySegment __tp) {
        if (asctime == null) {
            asctime = LibclangSymbolProvider.downcallHandle("asctime", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("asctime"));
        }
        try {
            return (MemorySegment) asctime.invoke(__tp);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<I8> asctime(PtrI<? extends StructI<? extends tm>> __tp) {
        return new Ptr<I8>(asctime$Raw(__tp.operator().value()), I8.OPERATIONS);
    }

    private static MethodHandle ctime;

    private static MemorySegment ctime$Raw(MemorySegment __timer) {
        if (ctime == null) {
            ctime = LibclangSymbolProvider.downcallHandle("ctime", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("ctime"));
        }
        try {
            return (MemorySegment) ctime.invoke(__timer);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<I8> ctime(PtrI<? extends I64I<? extends time_t>> __timer) {
        return new Ptr<I8>(ctime$Raw(__timer.operator().value()), I8.OPERATIONS);
    }

    private static MethodHandle asctime_r;

    private static MemorySegment asctime_r$Raw(MemorySegment __tp, MemorySegment __buf) {
        if (asctime_r == null) {
            asctime_r = LibclangSymbolProvider.downcallHandle("asctime_r", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("asctime_r"));
        }
        try {
            return (MemorySegment) asctime_r.invoke(__tp, __buf);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<I8> asctime_r(PtrI<? extends StructI<? extends tm>> __tp, PtrI<? extends I8I<?>> __buf) {
        return new Ptr<I8>(asctime_r$Raw(__tp.operator().value(), __buf.operator().value()), I8.OPERATIONS);
    }

    private static MethodHandle ctime_r;

    private static MemorySegment ctime_r$Raw(MemorySegment __timer, MemorySegment __buf) {
        if (ctime_r == null) {
            ctime_r = LibclangSymbolProvider.downcallHandle("ctime_r", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("ctime_r"));
        }
        try {
            return (MemorySegment) ctime_r.invoke(__timer, __buf);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<I8> ctime_r(PtrI<? extends I64I<? extends time_t>> __timer, PtrI<? extends I8I<?>> __buf) {
        return new Ptr<I8>(ctime_r$Raw(__timer.operator().value(), __buf.operator().value()), I8.OPERATIONS);
    }

    private static MethodHandle tzset;

    private static void tzset$Raw() {
        if (tzset == null) {
            tzset = LibclangSymbolProvider.downcallHandle("tzset", FunctionDescriptor.ofVoid()).orElseThrow(() -> new Utils.SymbolNotFound("tzset"));
        }
        try {
            tzset.invoke();
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void tzset() {
        tzset$Raw();
    }

    private static MethodHandle timegm;

    private static long timegm$Raw(MemorySegment __tp) {
        if (timegm == null) {
            timegm = LibclangSymbolProvider.downcallHandle("timegm", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("timegm"));
        }
        try {
            return (long) timegm.invoke(__tp);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static time_t timegm(PtrI<? extends StructI<? extends tm>> __tp) {
        return new time_t(timegm$Raw(__tp.operator().value()));
    }

    private static MethodHandle timelocal;

    private static long timelocal$Raw(MemorySegment __tp) {
        if (timelocal == null) {
            timelocal = LibclangSymbolProvider.downcallHandle("timelocal", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("timelocal"));
        }
        try {
            return (long) timelocal.invoke(__tp);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static time_t timelocal(PtrI<? extends StructI<? extends tm>> __tp) {
        return new time_t(timelocal$Raw(__tp.operator().value()));
    }

    private static MethodHandle dysize;

    private static int dysize$Raw(int __year) {
        if (dysize == null) {
            dysize = LibclangSymbolProvider.downcallHandle("dysize", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("dysize"));
        }
        try {
            return (int) dysize.invoke(__year);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 dysize(I32I<?> __year) {
        return new I32(dysize$Raw(__year.operator().value()));
    }

    private static MethodHandle nanosleep;

    private static int nanosleep$Raw(MemorySegment __requested_time, MemorySegment __remaining) {
        if (nanosleep == null) {
            nanosleep = LibclangSymbolProvider.downcallHandle("nanosleep", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("nanosleep"));
        }
        try {
            return (int) nanosleep.invoke(__requested_time, __remaining);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 nanosleep(PtrI<? extends StructI<? extends timespec>> __requested_time, PtrI<? extends StructI<? extends timespec>> __remaining) {
        return new I32(nanosleep$Raw(__requested_time.operator().value(), __remaining.operator().value()));
    }

    private static MethodHandle clock_getres;

    private static int clock_getres$Raw(int __clock_id, MemorySegment __res) {
        if (clock_getres == null) {
            clock_getres = LibclangSymbolProvider.downcallHandle("clock_getres", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clock_getres"));
        }
        try {
            return (int) clock_getres.invoke(__clock_id, __res);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clock_getres(I32I<? extends clockid_t> __clock_id, PtrI<? extends StructI<? extends timespec>> __res) {
        return new I32(clock_getres$Raw(__clock_id.operator().value(), __res.operator().value()));
    }

    private static MethodHandle clock_gettime;

    private static int clock_gettime$Raw(int __clock_id, MemorySegment __tp) {
        if (clock_gettime == null) {
            clock_gettime = LibclangSymbolProvider.downcallHandle("clock_gettime", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clock_gettime"));
        }
        try {
            return (int) clock_gettime.invoke(__clock_id, __tp);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clock_gettime(I32I<? extends clockid_t> __clock_id, PtrI<? extends StructI<? extends timespec>> __tp) {
        return new I32(clock_gettime$Raw(__clock_id.operator().value(), __tp.operator().value()));
    }

    private static MethodHandle clock_settime;

    private static int clock_settime$Raw(int __clock_id, MemorySegment __tp) {
        if (clock_settime == null) {
            clock_settime = LibclangSymbolProvider.downcallHandle("clock_settime", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clock_settime"));
        }
        try {
            return (int) clock_settime.invoke(__clock_id, __tp);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clock_settime(I32I<? extends clockid_t> __clock_id, PtrI<? extends StructI<? extends timespec>> __tp) {
        return new I32(clock_settime$Raw(__clock_id.operator().value(), __tp.operator().value()));
    }

    private static MethodHandle clock_nanosleep;

    private static int clock_nanosleep$Raw(int __clock_id, int __flags, MemorySegment __req, MemorySegment __rem) {
        if (clock_nanosleep == null) {
            clock_nanosleep = LibclangSymbolProvider.downcallHandle("clock_nanosleep", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clock_nanosleep"));
        }
        try {
            return (int) clock_nanosleep.invoke(__clock_id, __flags, __req, __rem);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clock_nanosleep(I32I<? extends clockid_t> __clock_id, I32I<?> __flags, PtrI<? extends StructI<? extends timespec>> __req, PtrI<? extends StructI<? extends timespec>> __rem) {
        return new I32(clock_nanosleep$Raw(__clock_id.operator().value(), __flags.operator().value(), __req.operator().value(), __rem.operator().value()));
    }

    private static MethodHandle clock_getcpuclockid;

    private static int clock_getcpuclockid$Raw(int __pid, MemorySegment __clock_id) {
        if (clock_getcpuclockid == null) {
            clock_getcpuclockid = LibclangSymbolProvider.downcallHandle("clock_getcpuclockid", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clock_getcpuclockid"));
        }
        try {
            return (int) clock_getcpuclockid.invoke(__pid, __clock_id);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clock_getcpuclockid(I32I<? extends pid_t> __pid, PtrI<? extends I32I<? extends clockid_t>> __clock_id) {
        return new I32(clock_getcpuclockid$Raw(__pid.operator().value(), __clock_id.operator().value()));
    }

    private static MethodHandle timer_create;

    private static int timer_create$Raw(int __clock_id, MemorySegment __evp, MemorySegment __timerid) {
        if (timer_create == null) {
            timer_create = LibclangSymbolProvider.downcallHandle("timer_create", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("timer_create"));
        }
        try {
            return (int) timer_create.invoke(__clock_id, __evp, __timerid);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 timer_create(I32I<? extends clockid_t> __clock_id, PtrI<? extends sigevent> __evp, PtrI<? extends timer_t> __timerid) {
        return new I32(timer_create$Raw(__clock_id.operator().value(), __evp.operator().value(), __timerid.operator().value()));
    }

    private static MethodHandle timer_delete;

    private static int timer_delete$Raw(MemorySegment __timerid) {
        if (timer_delete == null) {
            timer_delete = LibclangSymbolProvider.downcallHandle("timer_delete", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("timer_delete"));
        }
        try {
            return (int) timer_delete.invoke(__timerid);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 timer_delete(timer_t __timerid) {
        return new I32(timer_delete$Raw(__timerid.operator().value()));
    }

    private static MethodHandle timer_settime;

    private static int timer_settime$Raw(MemorySegment __timerid, int __flags, MemorySegment __value, MemorySegment __ovalue) {
        if (timer_settime == null) {
            timer_settime = LibclangSymbolProvider.downcallHandle("timer_settime", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("timer_settime"));
        }
        try {
            return (int) timer_settime.invoke(__timerid, __flags, __value, __ovalue);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 timer_settime(timer_t __timerid, I32I<?> __flags, PtrI<? extends StructI<? extends itimerspec>> __value, PtrI<? extends StructI<? extends itimerspec>> __ovalue) {
        return new I32(timer_settime$Raw(__timerid.operator().value(), __flags.operator().value(), __value.operator().value(), __ovalue.operator().value()));
    }

    private static MethodHandle timer_gettime;

    private static int timer_gettime$Raw(MemorySegment __timerid, MemorySegment __value) {
        if (timer_gettime == null) {
            timer_gettime = LibclangSymbolProvider.downcallHandle("timer_gettime", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("timer_gettime"));
        }
        try {
            return (int) timer_gettime.invoke(__timerid, __value);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 timer_gettime(timer_t __timerid, PtrI<? extends StructI<? extends itimerspec>> __value) {
        return new I32(timer_gettime$Raw(__timerid.operator().value(), __value.operator().value()));
    }

    private static MethodHandle timer_getoverrun;

    private static int timer_getoverrun$Raw(MemorySegment __timerid) {
        if (timer_getoverrun == null) {
            timer_getoverrun = LibclangSymbolProvider.downcallHandle("timer_getoverrun", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("timer_getoverrun"));
        }
        try {
            return (int) timer_getoverrun.invoke(__timerid);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 timer_getoverrun(timer_t __timerid) {
        return new I32(timer_getoverrun$Raw(__timerid.operator().value()));
    }

    private static MethodHandle timespec_get;

    private static int timespec_get$Raw(MemorySegment __ts, int __base) {
        if (timespec_get == null) {
            timespec_get = LibclangSymbolProvider.downcallHandle("timespec_get", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("timespec_get"));
        }
        try {
            return (int) timespec_get.invoke(__ts, __base);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 timespec_get(PtrI<? extends StructI<? extends timespec>> __ts, I32I<?> __base) {
        return new I32(timespec_get$Raw(__ts.operator().value(), __base.operator().value()));
    }

    private static MethodHandle clang_getFileName;

    private static MemorySegment clang_getFileName$Raw(SegmentAllocator segmentallocator, MemorySegment SFile) {
        if (clang_getFileName == null) {
            clang_getFileName = LibclangSymbolProvider.downcallHandle("clang_getFileName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getFileName"));
        }
        try {
            return (MemorySegment) clang_getFileName.invoke(segmentallocator, SFile);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getFileName(SegmentAllocator segmentallocator, CXFile SFile) {
        return new CXString(clang_getFileName$Raw(segmentallocator, SFile.operator().value()));
    }

    private static MethodHandle clang_getFileTime;

    private static long clang_getFileTime$Raw(MemorySegment SFile) {
        if (clang_getFileTime == null) {
            clang_getFileTime = LibclangSymbolProvider.downcallHandle("clang_getFileTime", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getFileTime"));
        }
        try {
            return (long) clang_getFileTime.invoke(SFile);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static time_t clang_getFileTime(CXFile SFile) {
        return new time_t(clang_getFileTime$Raw(SFile.operator().value()));
    }

    private static MethodHandle clang_getFileUniqueID;

    private static int clang_getFileUniqueID$Raw(MemorySegment file, MemorySegment outID) {
        if (clang_getFileUniqueID == null) {
            clang_getFileUniqueID = LibclangSymbolProvider.downcallHandle("clang_getFileUniqueID", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getFileUniqueID"));
        }
        try {
            return (int) clang_getFileUniqueID.invoke(file, outID);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getFileUniqueID(CXFile file, PtrI<? extends StructI<? extends CXFileUniqueID>> outID) {
        return new I32(clang_getFileUniqueID$Raw(file.operator().value(), outID.operator().value()));
    }

    private static MethodHandle clang_File_isEqual;

    private static int clang_File_isEqual$Raw(MemorySegment file1, MemorySegment file2) {
        if (clang_File_isEqual == null) {
            clang_File_isEqual = LibclangSymbolProvider.downcallHandle("clang_File_isEqual", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_File_isEqual"));
        }
        try {
            return (int) clang_File_isEqual.invoke(file1, file2);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_File_isEqual(CXFile file1, CXFile file2) {
        return new I32(clang_File_isEqual$Raw(file1.operator().value(), file2.operator().value()));
    }

    private static MethodHandle clang_File_tryGetRealPathName;

    private static MemorySegment clang_File_tryGetRealPathName$Raw(SegmentAllocator segmentallocator, MemorySegment file) {
        if (clang_File_tryGetRealPathName == null) {
            clang_File_tryGetRealPathName = LibclangSymbolProvider.downcallHandle("clang_File_tryGetRealPathName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_File_tryGetRealPathName"));
        }
        try {
            return (MemorySegment) clang_File_tryGetRealPathName.invoke(segmentallocator, file);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_File_tryGetRealPathName(SegmentAllocator segmentallocator, CXFile file) {
        return new CXString(clang_File_tryGetRealPathName$Raw(segmentallocator, file.operator().value()));
    }

    private static MethodHandle clang_getNullLocation;

    private static MemorySegment clang_getNullLocation$Raw(SegmentAllocator segmentallocator) {
        if (clang_getNullLocation == null) {
            clang_getNullLocation = LibclangSymbolProvider.downcallHandle("clang_getNullLocation", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getNullLocation"));
        }
        try {
            return (MemorySegment) clang_getNullLocation.invoke(segmentallocator);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceLocation clang_getNullLocation(SegmentAllocator segmentallocator) {
        return new CXSourceLocation(clang_getNullLocation$Raw(segmentallocator));
    }

    private static MethodHandle clang_equalLocations;

    private static int clang_equalLocations$Raw(MemorySegment loc1, MemorySegment loc2) {
        if (clang_equalLocations == null) {
            clang_equalLocations = LibclangSymbolProvider.downcallHandle("clang_equalLocations", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_equalLocations"));
        }
        try {
            return (int) clang_equalLocations.invoke(loc1, loc2);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_equalLocations(StructI<? extends CXSourceLocation> loc1, StructI<? extends CXSourceLocation> loc2) {
        return new I32(clang_equalLocations$Raw(loc1.operator().value(), loc2.operator().value()));
    }

    private static MethodHandle clang_Location_isInSystemHeader;

    private static int clang_Location_isInSystemHeader$Raw(MemorySegment location) {
        if (clang_Location_isInSystemHeader == null) {
            clang_Location_isInSystemHeader = LibclangSymbolProvider.downcallHandle("clang_Location_isInSystemHeader", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Location_isInSystemHeader"));
        }
        try {
            return (int) clang_Location_isInSystemHeader.invoke(location);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Location_isInSystemHeader(StructI<? extends CXSourceLocation> location) {
        return new I32(clang_Location_isInSystemHeader$Raw(location.operator().value()));
    }

    private static MethodHandle clang_Location_isFromMainFile;

    private static int clang_Location_isFromMainFile$Raw(MemorySegment location) {
        if (clang_Location_isFromMainFile == null) {
            clang_Location_isFromMainFile = LibclangSymbolProvider.downcallHandle("clang_Location_isFromMainFile", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Location_isFromMainFile"));
        }
        try {
            return (int) clang_Location_isFromMainFile.invoke(location);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Location_isFromMainFile(StructI<? extends CXSourceLocation> location) {
        return new I32(clang_Location_isFromMainFile$Raw(location.operator().value()));
    }

    private static MethodHandle clang_getNullRange;

    private static MemorySegment clang_getNullRange$Raw(SegmentAllocator segmentallocator) {
        if (clang_getNullRange == null) {
            clang_getNullRange = LibclangSymbolProvider.downcallHandle("clang_getNullRange", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getNullRange"));
        }
        try {
            return (MemorySegment) clang_getNullRange.invoke(segmentallocator);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceRange clang_getNullRange(SegmentAllocator segmentallocator) {
        return new CXSourceRange(clang_getNullRange$Raw(segmentallocator));
    }

    private static MethodHandle clang_getRange;

    private static MemorySegment clang_getRange$Raw(SegmentAllocator segmentallocator, MemorySegment begin, MemorySegment end) {
        if (clang_getRange == null) {
            clang_getRange = LibclangSymbolProvider.downcallHandle("clang_getRange", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getRange"));
        }
        try {
            return (MemorySegment) clang_getRange.invoke(segmentallocator, begin, end);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceRange clang_getRange(SegmentAllocator segmentallocator, StructI<? extends CXSourceLocation> begin, StructI<? extends CXSourceLocation> end) {
        return new CXSourceRange(clang_getRange$Raw(segmentallocator, begin.operator().value(), end.operator().value()));
    }

    private static MethodHandle clang_equalRanges;

    private static int clang_equalRanges$Raw(MemorySegment range1, MemorySegment range2) {
        if (clang_equalRanges == null) {
            clang_equalRanges = LibclangSymbolProvider.downcallHandle("clang_equalRanges", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_equalRanges"));
        }
        try {
            return (int) clang_equalRanges.invoke(range1, range2);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_equalRanges(StructI<? extends CXSourceRange> range1, StructI<? extends CXSourceRange> range2) {
        return new I32(clang_equalRanges$Raw(range1.operator().value(), range2.operator().value()));
    }

    private static MethodHandle clang_Range_isNull;

    private static int clang_Range_isNull$Raw(MemorySegment range) {
        if (clang_Range_isNull == null) {
            clang_Range_isNull = LibclangSymbolProvider.downcallHandle("clang_Range_isNull", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Range_isNull"));
        }
        try {
            return (int) clang_Range_isNull.invoke(range);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Range_isNull(StructI<? extends CXSourceRange> range) {
        return new I32(clang_Range_isNull$Raw(range.operator().value()));
    }

    private static MethodHandle clang_getExpansionLocation;

    private static void clang_getExpansionLocation$Raw(MemorySegment location, MemorySegment file, MemorySegment line, MemorySegment column, MemorySegment offset) {
        if (clang_getExpansionLocation == null) {
            clang_getExpansionLocation = LibclangSymbolProvider.downcallHandle("clang_getExpansionLocation", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getExpansionLocation"));
        }
        try {
            clang_getExpansionLocation.invoke(location, file, line, column, offset);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_getExpansionLocation(StructI<? extends CXSourceLocation> location, PtrI<? extends CXFile> file, PtrI<? extends I32I<?>> line, PtrI<? extends I32I<?>> column, PtrI<? extends I32I<?>> offset) {
        clang_getExpansionLocation$Raw(location.operator().value(), file.operator().value(), line.operator().value(), column.operator().value(), offset.operator().value());
    }

    private static MethodHandle clang_getPresumedLocation;

    private static void clang_getPresumedLocation$Raw(MemorySegment location, MemorySegment filename, MemorySegment line, MemorySegment column) {
        if (clang_getPresumedLocation == null) {
            clang_getPresumedLocation = LibclangSymbolProvider.downcallHandle("clang_getPresumedLocation", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getPresumedLocation"));
        }
        try {
            clang_getPresumedLocation.invoke(location, filename, line, column);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_getPresumedLocation(StructI<? extends CXSourceLocation> location, PtrI<? extends StructI<? extends CXString>> filename, PtrI<? extends I32I<?>> line, PtrI<? extends I32I<?>> column) {
        clang_getPresumedLocation$Raw(location.operator().value(), filename.operator().value(), line.operator().value(), column.operator().value());
    }

    private static MethodHandle clang_getInstantiationLocation;

    private static void clang_getInstantiationLocation$Raw(MemorySegment location, MemorySegment file, MemorySegment line, MemorySegment column, MemorySegment offset) {
        if (clang_getInstantiationLocation == null) {
            clang_getInstantiationLocation = LibclangSymbolProvider.downcallHandle("clang_getInstantiationLocation", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getInstantiationLocation"));
        }
        try {
            clang_getInstantiationLocation.invoke(location, file, line, column, offset);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_getInstantiationLocation(StructI<? extends CXSourceLocation> location, PtrI<? extends CXFile> file, PtrI<? extends I32I<?>> line, PtrI<? extends I32I<?>> column, PtrI<? extends I32I<?>> offset) {
        clang_getInstantiationLocation$Raw(location.operator().value(), file.operator().value(), line.operator().value(), column.operator().value(), offset.operator().value());
    }

    private static MethodHandle clang_getSpellingLocation;

    private static void clang_getSpellingLocation$Raw(MemorySegment location, MemorySegment file, MemorySegment line, MemorySegment column, MemorySegment offset) {
        if (clang_getSpellingLocation == null) {
            clang_getSpellingLocation = LibclangSymbolProvider.downcallHandle("clang_getSpellingLocation", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getSpellingLocation"));
        }
        try {
            clang_getSpellingLocation.invoke(location, file, line, column, offset);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_getSpellingLocation(StructI<? extends CXSourceLocation> location, PtrI<? extends CXFile> file, PtrI<? extends I32I<?>> line, PtrI<? extends I32I<?>> column, PtrI<? extends I32I<?>> offset) {
        clang_getSpellingLocation$Raw(location.operator().value(), file.operator().value(), line.operator().value(), column.operator().value(), offset.operator().value());
    }

    private static MethodHandle clang_getFileLocation;

    private static void clang_getFileLocation$Raw(MemorySegment location, MemorySegment file, MemorySegment line, MemorySegment column, MemorySegment offset) {
        if (clang_getFileLocation == null) {
            clang_getFileLocation = LibclangSymbolProvider.downcallHandle("clang_getFileLocation", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getFileLocation"));
        }
        try {
            clang_getFileLocation.invoke(location, file, line, column, offset);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_getFileLocation(StructI<? extends CXSourceLocation> location, PtrI<? extends CXFile> file, PtrI<? extends I32I<?>> line, PtrI<? extends I32I<?>> column, PtrI<? extends I32I<?>> offset) {
        clang_getFileLocation$Raw(location.operator().value(), file.operator().value(), line.operator().value(), column.operator().value(), offset.operator().value());
    }

    private static MethodHandle clang_getRangeStart;

    private static MemorySegment clang_getRangeStart$Raw(SegmentAllocator segmentallocator, MemorySegment range) {
        if (clang_getRangeStart == null) {
            clang_getRangeStart = LibclangSymbolProvider.downcallHandle("clang_getRangeStart", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getRangeStart"));
        }
        try {
            return (MemorySegment) clang_getRangeStart.invoke(segmentallocator, range);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceLocation clang_getRangeStart(SegmentAllocator segmentallocator, StructI<? extends CXSourceRange> range) {
        return new CXSourceLocation(clang_getRangeStart$Raw(segmentallocator, range.operator().value()));
    }

    private static MethodHandle clang_getRangeEnd;

    private static MemorySegment clang_getRangeEnd$Raw(SegmentAllocator segmentallocator, MemorySegment range) {
        if (clang_getRangeEnd == null) {
            clang_getRangeEnd = LibclangSymbolProvider.downcallHandle("clang_getRangeEnd", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getRangeEnd"));
        }
        try {
            return (MemorySegment) clang_getRangeEnd.invoke(segmentallocator, range);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceLocation clang_getRangeEnd(SegmentAllocator segmentallocator, StructI<? extends CXSourceRange> range) {
        return new CXSourceLocation(clang_getRangeEnd$Raw(segmentallocator, range.operator().value()));
    }

    private static MethodHandle clang_disposeSourceRangeList;

    private static void clang_disposeSourceRangeList$Raw(MemorySegment ranges) {
        if (clang_disposeSourceRangeList == null) {
            clang_disposeSourceRangeList = LibclangSymbolProvider.downcallHandle("clang_disposeSourceRangeList", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_disposeSourceRangeList"));
        }
        try {
            clang_disposeSourceRangeList.invoke(ranges);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_disposeSourceRangeList(PtrI<? extends StructI<? extends CXSourceRangeList>> ranges) {
        clang_disposeSourceRangeList$Raw(ranges.operator().value());
    }

    private static MethodHandle clang_getNumDiagnosticsInSet;

    private static int clang_getNumDiagnosticsInSet$Raw(MemorySegment Diags) {
        if (clang_getNumDiagnosticsInSet == null) {
            clang_getNumDiagnosticsInSet = LibclangSymbolProvider.downcallHandle("clang_getNumDiagnosticsInSet", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getNumDiagnosticsInSet"));
        }
        try {
            return (int) clang_getNumDiagnosticsInSet.invoke(Diags);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getNumDiagnosticsInSet(CXDiagnosticSet Diags) {
        return new I32(clang_getNumDiagnosticsInSet$Raw(Diags.operator().value()));
    }

    private static MethodHandle clang_getDiagnosticInSet;

    private static MemorySegment clang_getDiagnosticInSet$Raw(MemorySegment Diags, int Index) {
        if (clang_getDiagnosticInSet == null) {
            clang_getDiagnosticInSet = LibclangSymbolProvider.downcallHandle("clang_getDiagnosticInSet", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDiagnosticInSet"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticInSet.invoke(Diags, Index);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXDiagnostic clang_getDiagnosticInSet(CXDiagnosticSet Diags, I32I<?> Index) {
        return new CXDiagnostic(clang_getDiagnosticInSet$Raw(Diags.operator().value(), Index.operator().value()));
    }

    private static MethodHandle clang_loadDiagnostics;

    private static MemorySegment clang_loadDiagnostics$Raw(MemorySegment file, MemorySegment error, MemorySegment errorString) {
        if (clang_loadDiagnostics == null) {
            clang_loadDiagnostics = LibclangSymbolProvider.downcallHandle("clang_loadDiagnostics", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_loadDiagnostics"));
        }
        try {
            return (MemorySegment) clang_loadDiagnostics.invoke(file, error, errorString);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXDiagnosticSet clang_loadDiagnostics(PtrI<? extends I8I<?>> file, PtrI<? extends I32I<? extends CXLoadDiag_Error>> error, PtrI<? extends StructI<? extends CXString>> errorString) {
        return new CXDiagnosticSet(clang_loadDiagnostics$Raw(file.operator().value(), error.operator().value(), errorString.operator().value()));
    }

    private static MethodHandle clang_disposeDiagnosticSet;

    private static void clang_disposeDiagnosticSet$Raw(MemorySegment Diags) {
        if (clang_disposeDiagnosticSet == null) {
            clang_disposeDiagnosticSet = LibclangSymbolProvider.downcallHandle("clang_disposeDiagnosticSet", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_disposeDiagnosticSet"));
        }
        try {
            clang_disposeDiagnosticSet.invoke(Diags);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_disposeDiagnosticSet(CXDiagnosticSet Diags) {
        clang_disposeDiagnosticSet$Raw(Diags.operator().value());
    }

    private static MethodHandle clang_getChildDiagnostics;

    private static MemorySegment clang_getChildDiagnostics$Raw(MemorySegment D) {
        if (clang_getChildDiagnostics == null) {
            clang_getChildDiagnostics = LibclangSymbolProvider.downcallHandle("clang_getChildDiagnostics", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getChildDiagnostics"));
        }
        try {
            return (MemorySegment) clang_getChildDiagnostics.invoke(D);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXDiagnosticSet clang_getChildDiagnostics(CXDiagnostic D) {
        return new CXDiagnosticSet(clang_getChildDiagnostics$Raw(D.operator().value()));
    }

    private static MethodHandle clang_disposeDiagnostic;

    private static void clang_disposeDiagnostic$Raw(MemorySegment Diagnostic) {
        if (clang_disposeDiagnostic == null) {
            clang_disposeDiagnostic = LibclangSymbolProvider.downcallHandle("clang_disposeDiagnostic", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_disposeDiagnostic"));
        }
        try {
            clang_disposeDiagnostic.invoke(Diagnostic);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_disposeDiagnostic(CXDiagnostic Diagnostic) {
        clang_disposeDiagnostic$Raw(Diagnostic.operator().value());
    }

    private static MethodHandle clang_formatDiagnostic;

    private static MemorySegment clang_formatDiagnostic$Raw(SegmentAllocator segmentallocator, MemorySegment Diagnostic, int Options) {
        if (clang_formatDiagnostic == null) {
            clang_formatDiagnostic = LibclangSymbolProvider.downcallHandle("clang_formatDiagnostic", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_formatDiagnostic"));
        }
        try {
            return (MemorySegment) clang_formatDiagnostic.invoke(segmentallocator, Diagnostic, Options);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_formatDiagnostic(SegmentAllocator segmentallocator, CXDiagnostic Diagnostic, I32I<?> Options) {
        return new CXString(clang_formatDiagnostic$Raw(segmentallocator, Diagnostic.operator().value(), Options.operator().value()));
    }

    private static MethodHandle clang_defaultDiagnosticDisplayOptions;

    private static int clang_defaultDiagnosticDisplayOptions$Raw() {
        if (clang_defaultDiagnosticDisplayOptions == null) {
            clang_defaultDiagnosticDisplayOptions = LibclangSymbolProvider.downcallHandle("clang_defaultDiagnosticDisplayOptions", FunctionDescriptor.of(ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_defaultDiagnosticDisplayOptions"));
        }
        try {
            return (int) clang_defaultDiagnosticDisplayOptions.invoke();
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_defaultDiagnosticDisplayOptions() {
        return new I32(clang_defaultDiagnosticDisplayOptions$Raw());
    }

    private static MethodHandle clang_getDiagnosticSeverity;

    private static int clang_getDiagnosticSeverity$Raw(MemorySegment arg0) {
        if (clang_getDiagnosticSeverity == null) {
            clang_getDiagnosticSeverity = LibclangSymbolProvider.downcallHandle("clang_getDiagnosticSeverity", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDiagnosticSeverity"));
        }
        try {
            return (int) clang_getDiagnosticSeverity.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXDiagnosticSeverity clang_getDiagnosticSeverity(CXDiagnostic arg0) {
        return new CXDiagnosticSeverity(clang_getDiagnosticSeverity$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_getDiagnosticLocation;

    private static MemorySegment clang_getDiagnosticLocation$Raw(SegmentAllocator segmentallocator, MemorySegment arg0) {
        if (clang_getDiagnosticLocation == null) {
            clang_getDiagnosticLocation = LibclangSymbolProvider.downcallHandle("clang_getDiagnosticLocation", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDiagnosticLocation"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticLocation.invoke(segmentallocator, arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceLocation clang_getDiagnosticLocation(SegmentAllocator segmentallocator, CXDiagnostic arg0) {
        return new CXSourceLocation(clang_getDiagnosticLocation$Raw(segmentallocator, arg0.operator().value()));
    }

    private static MethodHandle clang_getDiagnosticSpelling;

    private static MemorySegment clang_getDiagnosticSpelling$Raw(SegmentAllocator segmentallocator, MemorySegment arg0) {
        if (clang_getDiagnosticSpelling == null) {
            clang_getDiagnosticSpelling = LibclangSymbolProvider.downcallHandle("clang_getDiagnosticSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDiagnosticSpelling"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticSpelling.invoke(segmentallocator, arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getDiagnosticSpelling(SegmentAllocator segmentallocator, CXDiagnostic arg0) {
        return new CXString(clang_getDiagnosticSpelling$Raw(segmentallocator, arg0.operator().value()));
    }

    private static MethodHandle clang_getDiagnosticOption;

    private static MemorySegment clang_getDiagnosticOption$Raw(SegmentAllocator segmentallocator, MemorySegment Diag, MemorySegment Disable) {
        if (clang_getDiagnosticOption == null) {
            clang_getDiagnosticOption = LibclangSymbolProvider.downcallHandle("clang_getDiagnosticOption", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDiagnosticOption"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticOption.invoke(segmentallocator, Diag, Disable);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getDiagnosticOption(SegmentAllocator segmentallocator, CXDiagnostic Diag, PtrI<? extends StructI<? extends CXString>> Disable) {
        return new CXString(clang_getDiagnosticOption$Raw(segmentallocator, Diag.operator().value(), Disable.operator().value()));
    }

    private static MethodHandle clang_getDiagnosticCategory;

    private static int clang_getDiagnosticCategory$Raw(MemorySegment arg0) {
        if (clang_getDiagnosticCategory == null) {
            clang_getDiagnosticCategory = LibclangSymbolProvider.downcallHandle("clang_getDiagnosticCategory", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDiagnosticCategory"));
        }
        try {
            return (int) clang_getDiagnosticCategory.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getDiagnosticCategory(CXDiagnostic arg0) {
        return new I32(clang_getDiagnosticCategory$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_getDiagnosticCategoryName;

    private static MemorySegment clang_getDiagnosticCategoryName$Raw(SegmentAllocator segmentallocator, int Category) {
        if (clang_getDiagnosticCategoryName == null) {
            clang_getDiagnosticCategoryName = LibclangSymbolProvider.downcallHandle("clang_getDiagnosticCategoryName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDiagnosticCategoryName"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticCategoryName.invoke(segmentallocator, Category);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getDiagnosticCategoryName(SegmentAllocator segmentallocator, I32I<?> Category) {
        return new CXString(clang_getDiagnosticCategoryName$Raw(segmentallocator, Category.operator().value()));
    }

    private static MethodHandle clang_getDiagnosticCategoryText;

    private static MemorySegment clang_getDiagnosticCategoryText$Raw(SegmentAllocator segmentallocator, MemorySegment arg0) {
        if (clang_getDiagnosticCategoryText == null) {
            clang_getDiagnosticCategoryText = LibclangSymbolProvider.downcallHandle("clang_getDiagnosticCategoryText", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDiagnosticCategoryText"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticCategoryText.invoke(segmentallocator, arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getDiagnosticCategoryText(SegmentAllocator segmentallocator, CXDiagnostic arg0) {
        return new CXString(clang_getDiagnosticCategoryText$Raw(segmentallocator, arg0.operator().value()));
    }

    private static MethodHandle clang_getDiagnosticNumRanges;

    private static int clang_getDiagnosticNumRanges$Raw(MemorySegment arg0) {
        if (clang_getDiagnosticNumRanges == null) {
            clang_getDiagnosticNumRanges = LibclangSymbolProvider.downcallHandle("clang_getDiagnosticNumRanges", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDiagnosticNumRanges"));
        }
        try {
            return (int) clang_getDiagnosticNumRanges.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getDiagnosticNumRanges(CXDiagnostic arg0) {
        return new I32(clang_getDiagnosticNumRanges$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_getDiagnosticRange;

    private static MemorySegment clang_getDiagnosticRange$Raw(SegmentAllocator segmentallocator, MemorySegment Diagnostic, int Range) {
        if (clang_getDiagnosticRange == null) {
            clang_getDiagnosticRange = LibclangSymbolProvider.downcallHandle("clang_getDiagnosticRange", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDiagnosticRange"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticRange.invoke(segmentallocator, Diagnostic, Range);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceRange clang_getDiagnosticRange(SegmentAllocator segmentallocator, CXDiagnostic Diagnostic, I32I<?> Range) {
        return new CXSourceRange(clang_getDiagnosticRange$Raw(segmentallocator, Diagnostic.operator().value(), Range.operator().value()));
    }

    private static MethodHandle clang_getDiagnosticNumFixIts;

    private static int clang_getDiagnosticNumFixIts$Raw(MemorySegment Diagnostic) {
        if (clang_getDiagnosticNumFixIts == null) {
            clang_getDiagnosticNumFixIts = LibclangSymbolProvider.downcallHandle("clang_getDiagnosticNumFixIts", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDiagnosticNumFixIts"));
        }
        try {
            return (int) clang_getDiagnosticNumFixIts.invoke(Diagnostic);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getDiagnosticNumFixIts(CXDiagnostic Diagnostic) {
        return new I32(clang_getDiagnosticNumFixIts$Raw(Diagnostic.operator().value()));
    }

    private static MethodHandle clang_getDiagnosticFixIt;

    private static MemorySegment clang_getDiagnosticFixIt$Raw(SegmentAllocator segmentallocator, MemorySegment Diagnostic, int FixIt, MemorySegment ReplacementRange) {
        if (clang_getDiagnosticFixIt == null) {
            clang_getDiagnosticFixIt = LibclangSymbolProvider.downcallHandle("clang_getDiagnosticFixIt", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDiagnosticFixIt"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticFixIt.invoke(segmentallocator, Diagnostic, FixIt, ReplacementRange);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getDiagnosticFixIt(SegmentAllocator segmentallocator, CXDiagnostic Diagnostic, I32I<?> FixIt, PtrI<? extends StructI<? extends CXSourceRange>> ReplacementRange) {
        return new CXString(clang_getDiagnosticFixIt$Raw(segmentallocator, Diagnostic.operator().value(), FixIt.operator().value(), ReplacementRange.operator().value()));
    }

    private static MethodHandle clang_createIndex;

    private static MemorySegment clang_createIndex$Raw(int excludeDeclarationsFromPCH, int displayDiagnostics) {
        if (clang_createIndex == null) {
            clang_createIndex = LibclangSymbolProvider.downcallHandle("clang_createIndex", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_createIndex"));
        }
        try {
            return (MemorySegment) clang_createIndex.invoke(excludeDeclarationsFromPCH, displayDiagnostics);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXIndex clang_createIndex(I32I<?> excludeDeclarationsFromPCH, I32I<?> displayDiagnostics) {
        return new CXIndex(clang_createIndex$Raw(excludeDeclarationsFromPCH.operator().value(), displayDiagnostics.operator().value()));
    }

    private static MethodHandle clang_disposeIndex;

    private static void clang_disposeIndex$Raw(MemorySegment index) {
        if (clang_disposeIndex == null) {
            clang_disposeIndex = LibclangSymbolProvider.downcallHandle("clang_disposeIndex", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_disposeIndex"));
        }
        try {
            clang_disposeIndex.invoke(index);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_disposeIndex(CXIndex index) {
        clang_disposeIndex$Raw(index.operator().value());
    }

    private static MethodHandle clang_createIndexWithOptions;

    private static MemorySegment clang_createIndexWithOptions$Raw(MemorySegment options) {
        if (clang_createIndexWithOptions == null) {
            clang_createIndexWithOptions = LibclangSymbolProvider.downcallHandle("clang_createIndexWithOptions", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_createIndexWithOptions"));
        }
        try {
            return (MemorySegment) clang_createIndexWithOptions.invoke(options);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXIndex clang_createIndexWithOptions(PtrI<? extends StructI<? extends CXIndexOptions>> options) {
        return new CXIndex(clang_createIndexWithOptions$Raw(options.operator().value()));
    }

    private static MethodHandle clang_CXIndex_setGlobalOptions;

    private static void clang_CXIndex_setGlobalOptions$Raw(MemorySegment arg0, int options) {
        if (clang_CXIndex_setGlobalOptions == null) {
            clang_CXIndex_setGlobalOptions = LibclangSymbolProvider.downcallHandle("clang_CXIndex_setGlobalOptions", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXIndex_setGlobalOptions"));
        }
        try {
            clang_CXIndex_setGlobalOptions.invoke(arg0, options);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_CXIndex_setGlobalOptions(CXIndex arg0, I32I<?> options) {
        clang_CXIndex_setGlobalOptions$Raw(arg0.operator().value(), options.operator().value());
    }

    private static MethodHandle clang_CXIndex_getGlobalOptions;

    private static int clang_CXIndex_getGlobalOptions$Raw(MemorySegment arg0) {
        if (clang_CXIndex_getGlobalOptions == null) {
            clang_CXIndex_getGlobalOptions = LibclangSymbolProvider.downcallHandle("clang_CXIndex_getGlobalOptions", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXIndex_getGlobalOptions"));
        }
        try {
            return (int) clang_CXIndex_getGlobalOptions.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXIndex_getGlobalOptions(CXIndex arg0) {
        return new I32(clang_CXIndex_getGlobalOptions$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_CXIndex_setInvocationEmissionPathOption;

    private static void clang_CXIndex_setInvocationEmissionPathOption$Raw(MemorySegment arg0, MemorySegment Path) {
        if (clang_CXIndex_setInvocationEmissionPathOption == null) {
            clang_CXIndex_setInvocationEmissionPathOption = LibclangSymbolProvider.downcallHandle("clang_CXIndex_setInvocationEmissionPathOption", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXIndex_setInvocationEmissionPathOption"));
        }
        try {
            clang_CXIndex_setInvocationEmissionPathOption.invoke(arg0, Path);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_CXIndex_setInvocationEmissionPathOption(CXIndex arg0, PtrI<? extends I8I<?>> Path) {
        clang_CXIndex_setInvocationEmissionPathOption$Raw(arg0.operator().value(), Path.operator().value());
    }

    private static MethodHandle clang_isFileMultipleIncludeGuarded;

    private static int clang_isFileMultipleIncludeGuarded$Raw(MemorySegment tu, MemorySegment file) {
        if (clang_isFileMultipleIncludeGuarded == null) {
            clang_isFileMultipleIncludeGuarded = LibclangSymbolProvider.downcallHandle("clang_isFileMultipleIncludeGuarded", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_isFileMultipleIncludeGuarded"));
        }
        try {
            return (int) clang_isFileMultipleIncludeGuarded.invoke(tu, file);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isFileMultipleIncludeGuarded(CXTranslationUnit tu, CXFile file) {
        return new I32(clang_isFileMultipleIncludeGuarded$Raw(tu.operator().value(), file.operator().value()));
    }

    private static MethodHandle clang_getFile;

    private static MemorySegment clang_getFile$Raw(MemorySegment tu, MemorySegment file_name) {
        if (clang_getFile == null) {
            clang_getFile = LibclangSymbolProvider.downcallHandle("clang_getFile", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getFile"));
        }
        try {
            return (MemorySegment) clang_getFile.invoke(tu, file_name);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXFile clang_getFile(CXTranslationUnit tu, PtrI<? extends I8I<?>> file_name) {
        return new CXFile(clang_getFile$Raw(tu.operator().value(), file_name.operator().value()));
    }

    private static MethodHandle clang_getFileContents;

    private static MemorySegment clang_getFileContents$Raw(MemorySegment tu, MemorySegment file, MemorySegment size) {
        if (clang_getFileContents == null) {
            clang_getFileContents = LibclangSymbolProvider.downcallHandle("clang_getFileContents", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getFileContents"));
        }
        try {
            return (MemorySegment) clang_getFileContents.invoke(tu, file, size);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<I8> clang_getFileContents(CXTranslationUnit tu, CXFile file, PtrI<? extends I64I<? extends size_t>> size) {
        return new Ptr<I8>(clang_getFileContents$Raw(tu.operator().value(), file.operator().value(), size.operator().value()), I8.OPERATIONS);
    }

    private static MethodHandle clang_getLocation;

    private static MemorySegment clang_getLocation$Raw(SegmentAllocator segmentallocator, MemorySegment tu, MemorySegment file, int line, int column) {
        if (clang_getLocation == null) {
            clang_getLocation = LibclangSymbolProvider.downcallHandle("clang_getLocation", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getLocation"));
        }
        try {
            return (MemorySegment) clang_getLocation.invoke(segmentallocator, tu, file, line, column);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceLocation clang_getLocation(SegmentAllocator segmentallocator, CXTranslationUnit tu, CXFile file, I32I<?> line, I32I<?> column) {
        return new CXSourceLocation(clang_getLocation$Raw(segmentallocator, tu.operator().value(), file.operator().value(), line.operator().value(), column.operator().value()));
    }

    private static MethodHandle clang_getLocationForOffset;

    private static MemorySegment clang_getLocationForOffset$Raw(SegmentAllocator segmentallocator, MemorySegment tu, MemorySegment file, int offset) {
        if (clang_getLocationForOffset == null) {
            clang_getLocationForOffset = LibclangSymbolProvider.downcallHandle("clang_getLocationForOffset", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getLocationForOffset"));
        }
        try {
            return (MemorySegment) clang_getLocationForOffset.invoke(segmentallocator, tu, file, offset);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceLocation clang_getLocationForOffset(SegmentAllocator segmentallocator, CXTranslationUnit tu, CXFile file, I32I<?> offset) {
        return new CXSourceLocation(clang_getLocationForOffset$Raw(segmentallocator, tu.operator().value(), file.operator().value(), offset.operator().value()));
    }

    private static MethodHandle clang_getSkippedRanges;

    private static MemorySegment clang_getSkippedRanges$Raw(MemorySegment tu, MemorySegment file) {
        if (clang_getSkippedRanges == null) {
            clang_getSkippedRanges = LibclangSymbolProvider.downcallHandle("clang_getSkippedRanges", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getSkippedRanges"));
        }
        try {
            return (MemorySegment) clang_getSkippedRanges.invoke(tu, file);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<CXSourceRangeList> clang_getSkippedRanges(CXTranslationUnit tu, CXFile file) {
        return new Ptr<CXSourceRangeList>(clang_getSkippedRanges$Raw(tu.operator().value(), file.operator().value()), CXSourceRangeList.OPERATIONS);
    }

    private static MethodHandle clang_getAllSkippedRanges;

    private static MemorySegment clang_getAllSkippedRanges$Raw(MemorySegment tu) {
        if (clang_getAllSkippedRanges == null) {
            clang_getAllSkippedRanges = LibclangSymbolProvider.downcallHandle("clang_getAllSkippedRanges", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getAllSkippedRanges"));
        }
        try {
            return (MemorySegment) clang_getAllSkippedRanges.invoke(tu);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<CXSourceRangeList> clang_getAllSkippedRanges(CXTranslationUnit tu) {
        return new Ptr<CXSourceRangeList>(clang_getAllSkippedRanges$Raw(tu.operator().value()), CXSourceRangeList.OPERATIONS);
    }

    private static MethodHandle clang_getNumDiagnostics;

    private static int clang_getNumDiagnostics$Raw(MemorySegment Unit) {
        if (clang_getNumDiagnostics == null) {
            clang_getNumDiagnostics = LibclangSymbolProvider.downcallHandle("clang_getNumDiagnostics", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getNumDiagnostics"));
        }
        try {
            return (int) clang_getNumDiagnostics.invoke(Unit);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getNumDiagnostics(CXTranslationUnit Unit) {
        return new I32(clang_getNumDiagnostics$Raw(Unit.operator().value()));
    }

    private static MethodHandle clang_getDiagnostic;

    private static MemorySegment clang_getDiagnostic$Raw(MemorySegment Unit, int Index) {
        if (clang_getDiagnostic == null) {
            clang_getDiagnostic = LibclangSymbolProvider.downcallHandle("clang_getDiagnostic", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDiagnostic"));
        }
        try {
            return (MemorySegment) clang_getDiagnostic.invoke(Unit, Index);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXDiagnostic clang_getDiagnostic(CXTranslationUnit Unit, I32I<?> Index) {
        return new CXDiagnostic(clang_getDiagnostic$Raw(Unit.operator().value(), Index.operator().value()));
    }

    private static MethodHandle clang_getDiagnosticSetFromTU;

    private static MemorySegment clang_getDiagnosticSetFromTU$Raw(MemorySegment Unit) {
        if (clang_getDiagnosticSetFromTU == null) {
            clang_getDiagnosticSetFromTU = LibclangSymbolProvider.downcallHandle("clang_getDiagnosticSetFromTU", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDiagnosticSetFromTU"));
        }
        try {
            return (MemorySegment) clang_getDiagnosticSetFromTU.invoke(Unit);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXDiagnosticSet clang_getDiagnosticSetFromTU(CXTranslationUnit Unit) {
        return new CXDiagnosticSet(clang_getDiagnosticSetFromTU$Raw(Unit.operator().value()));
    }

    private static MethodHandle clang_getTranslationUnitSpelling;

    private static MemorySegment clang_getTranslationUnitSpelling$Raw(SegmentAllocator segmentallocator, MemorySegment CTUnit) {
        if (clang_getTranslationUnitSpelling == null) {
            clang_getTranslationUnitSpelling = LibclangSymbolProvider.downcallHandle("clang_getTranslationUnitSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getTranslationUnitSpelling"));
        }
        try {
            return (MemorySegment) clang_getTranslationUnitSpelling.invoke(segmentallocator, CTUnit);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getTranslationUnitSpelling(SegmentAllocator segmentallocator, CXTranslationUnit CTUnit) {
        return new CXString(clang_getTranslationUnitSpelling$Raw(segmentallocator, CTUnit.operator().value()));
    }

    private static MethodHandle clang_createTranslationUnitFromSourceFile;

    private static MemorySegment clang_createTranslationUnitFromSourceFile$Raw(MemorySegment CIdx, MemorySegment source_filename, int num_clang_command_line_args, MemorySegment clang_command_line_args, int num_unsaved_files, MemorySegment unsaved_files) {
        if (clang_createTranslationUnitFromSourceFile == null) {
            clang_createTranslationUnitFromSourceFile = LibclangSymbolProvider.downcallHandle("clang_createTranslationUnitFromSourceFile", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_createTranslationUnitFromSourceFile"));
        }
        try {
            return (MemorySegment) clang_createTranslationUnitFromSourceFile.invoke(CIdx, source_filename, num_clang_command_line_args, clang_command_line_args, num_unsaved_files, unsaved_files);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXTranslationUnit clang_createTranslationUnitFromSourceFile(CXIndex CIdx, PtrI<? extends I8I<?>> source_filename, I32I<?> num_clang_command_line_args, PtrI<? extends PtrI<? extends I8I<?>>> clang_command_line_args, I32I<?> num_unsaved_files, PtrI<? extends StructI<? extends CXUnsavedFile>> unsaved_files) {
        return new CXTranslationUnit(clang_createTranslationUnitFromSourceFile$Raw(CIdx.operator().value(), source_filename.operator().value(), num_clang_command_line_args.operator().value(), clang_command_line_args.operator().value(), num_unsaved_files.operator().value(), unsaved_files.operator().value()));
    }

    private static MethodHandle clang_createTranslationUnit;

    private static MemorySegment clang_createTranslationUnit$Raw(MemorySegment CIdx, MemorySegment ast_filename) {
        if (clang_createTranslationUnit == null) {
            clang_createTranslationUnit = LibclangSymbolProvider.downcallHandle("clang_createTranslationUnit", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_createTranslationUnit"));
        }
        try {
            return (MemorySegment) clang_createTranslationUnit.invoke(CIdx, ast_filename);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXTranslationUnit clang_createTranslationUnit(CXIndex CIdx, PtrI<? extends I8I<?>> ast_filename) {
        return new CXTranslationUnit(clang_createTranslationUnit$Raw(CIdx.operator().value(), ast_filename.operator().value()));
    }

    private static MethodHandle clang_createTranslationUnit2;

    private static int clang_createTranslationUnit2$Raw(MemorySegment CIdx, MemorySegment ast_filename, MemorySegment out_TU) {
        if (clang_createTranslationUnit2 == null) {
            clang_createTranslationUnit2 = LibclangSymbolProvider.downcallHandle("clang_createTranslationUnit2", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_createTranslationUnit2"));
        }
        try {
            return (int) clang_createTranslationUnit2.invoke(CIdx, ast_filename, out_TU);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXErrorCode clang_createTranslationUnit2(CXIndex CIdx, PtrI<? extends I8I<?>> ast_filename, PtrI<? extends CXTranslationUnit> out_TU) {
        return new CXErrorCode(clang_createTranslationUnit2$Raw(CIdx.operator().value(), ast_filename.operator().value(), out_TU.operator().value()));
    }

    private static MethodHandle clang_defaultEditingTranslationUnitOptions;

    private static int clang_defaultEditingTranslationUnitOptions$Raw() {
        if (clang_defaultEditingTranslationUnitOptions == null) {
            clang_defaultEditingTranslationUnitOptions = LibclangSymbolProvider.downcallHandle("clang_defaultEditingTranslationUnitOptions", FunctionDescriptor.of(ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_defaultEditingTranslationUnitOptions"));
        }
        try {
            return (int) clang_defaultEditingTranslationUnitOptions.invoke();
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_defaultEditingTranslationUnitOptions() {
        return new I32(clang_defaultEditingTranslationUnitOptions$Raw());
    }

    private static MethodHandle clang_parseTranslationUnit;

    private static MemorySegment clang_parseTranslationUnit$Raw(MemorySegment CIdx, MemorySegment source_filename, MemorySegment command_line_args, int num_command_line_args, MemorySegment unsaved_files, int num_unsaved_files, int options) {
        if (clang_parseTranslationUnit == null) {
            clang_parseTranslationUnit = LibclangSymbolProvider.downcallHandle("clang_parseTranslationUnit", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_parseTranslationUnit"));
        }
        try {
            return (MemorySegment) clang_parseTranslationUnit.invoke(CIdx, source_filename, command_line_args, num_command_line_args, unsaved_files, num_unsaved_files, options);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXTranslationUnit clang_parseTranslationUnit(CXIndex CIdx, PtrI<? extends I8I<?>> source_filename, PtrI<? extends PtrI<? extends I8I<?>>> command_line_args, I32I<?> num_command_line_args, PtrI<? extends StructI<? extends CXUnsavedFile>> unsaved_files, I32I<?> num_unsaved_files, I32I<?> options) {
        return new CXTranslationUnit(clang_parseTranslationUnit$Raw(CIdx.operator().value(), source_filename.operator().value(), command_line_args.operator().value(), num_command_line_args.operator().value(), unsaved_files.operator().value(), num_unsaved_files.operator().value(), options.operator().value()));
    }

    private static MethodHandle clang_parseTranslationUnit2;

    private static int clang_parseTranslationUnit2$Raw(MemorySegment CIdx, MemorySegment source_filename, MemorySegment command_line_args, int num_command_line_args, MemorySegment unsaved_files, int num_unsaved_files, int options, MemorySegment out_TU) {
        if (clang_parseTranslationUnit2 == null) {
            clang_parseTranslationUnit2 = LibclangSymbolProvider.downcallHandle("clang_parseTranslationUnit2", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_parseTranslationUnit2"));
        }
        try {
            return (int) clang_parseTranslationUnit2.invoke(CIdx, source_filename, command_line_args, num_command_line_args, unsaved_files, num_unsaved_files, options, out_TU);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXErrorCode clang_parseTranslationUnit2(CXIndex CIdx, PtrI<? extends I8I<?>> source_filename, PtrI<? extends PtrI<? extends I8I<?>>> command_line_args, I32I<?> num_command_line_args, PtrI<? extends StructI<? extends CXUnsavedFile>> unsaved_files, I32I<?> num_unsaved_files, I32I<?> options, PtrI<? extends CXTranslationUnit> out_TU) {
        return new CXErrorCode(clang_parseTranslationUnit2$Raw(CIdx.operator().value(), source_filename.operator().value(), command_line_args.operator().value(), num_command_line_args.operator().value(), unsaved_files.operator().value(), num_unsaved_files.operator().value(), options.operator().value(), out_TU.operator().value()));
    }

    private static MethodHandle clang_parseTranslationUnit2FullArgv;

    private static int clang_parseTranslationUnit2FullArgv$Raw(MemorySegment CIdx, MemorySegment source_filename, MemorySegment command_line_args, int num_command_line_args, MemorySegment unsaved_files, int num_unsaved_files, int options, MemorySegment out_TU) {
        if (clang_parseTranslationUnit2FullArgv == null) {
            clang_parseTranslationUnit2FullArgv = LibclangSymbolProvider.downcallHandle("clang_parseTranslationUnit2FullArgv", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_parseTranslationUnit2FullArgv"));
        }
        try {
            return (int) clang_parseTranslationUnit2FullArgv.invoke(CIdx, source_filename, command_line_args, num_command_line_args, unsaved_files, num_unsaved_files, options, out_TU);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXErrorCode clang_parseTranslationUnit2FullArgv(CXIndex CIdx, PtrI<? extends I8I<?>> source_filename, PtrI<? extends PtrI<? extends I8I<?>>> command_line_args, I32I<?> num_command_line_args, PtrI<? extends StructI<? extends CXUnsavedFile>> unsaved_files, I32I<?> num_unsaved_files, I32I<?> options, PtrI<? extends CXTranslationUnit> out_TU) {
        return new CXErrorCode(clang_parseTranslationUnit2FullArgv$Raw(CIdx.operator().value(), source_filename.operator().value(), command_line_args.operator().value(), num_command_line_args.operator().value(), unsaved_files.operator().value(), num_unsaved_files.operator().value(), options.operator().value(), out_TU.operator().value()));
    }

    private static MethodHandle clang_defaultSaveOptions;

    private static int clang_defaultSaveOptions$Raw(MemorySegment TU) {
        if (clang_defaultSaveOptions == null) {
            clang_defaultSaveOptions = LibclangSymbolProvider.downcallHandle("clang_defaultSaveOptions", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_defaultSaveOptions"));
        }
        try {
            return (int) clang_defaultSaveOptions.invoke(TU);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_defaultSaveOptions(CXTranslationUnit TU) {
        return new I32(clang_defaultSaveOptions$Raw(TU.operator().value()));
    }

    private static MethodHandle clang_saveTranslationUnit;

    private static int clang_saveTranslationUnit$Raw(MemorySegment TU, MemorySegment FileName, int options) {
        if (clang_saveTranslationUnit == null) {
            clang_saveTranslationUnit = LibclangSymbolProvider.downcallHandle("clang_saveTranslationUnit", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_saveTranslationUnit"));
        }
        try {
            return (int) clang_saveTranslationUnit.invoke(TU, FileName, options);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_saveTranslationUnit(CXTranslationUnit TU, PtrI<? extends I8I<?>> FileName, I32I<?> options) {
        return new I32(clang_saveTranslationUnit$Raw(TU.operator().value(), FileName.operator().value(), options.operator().value()));
    }

    private static MethodHandle clang_suspendTranslationUnit;

    private static int clang_suspendTranslationUnit$Raw(MemorySegment arg0) {
        if (clang_suspendTranslationUnit == null) {
            clang_suspendTranslationUnit = LibclangSymbolProvider.downcallHandle("clang_suspendTranslationUnit", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_suspendTranslationUnit"));
        }
        try {
            return (int) clang_suspendTranslationUnit.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_suspendTranslationUnit(CXTranslationUnit arg0) {
        return new I32(clang_suspendTranslationUnit$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_disposeTranslationUnit;

    private static void clang_disposeTranslationUnit$Raw(MemorySegment arg0) {
        if (clang_disposeTranslationUnit == null) {
            clang_disposeTranslationUnit = LibclangSymbolProvider.downcallHandle("clang_disposeTranslationUnit", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_disposeTranslationUnit"));
        }
        try {
            clang_disposeTranslationUnit.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_disposeTranslationUnit(CXTranslationUnit arg0) {
        clang_disposeTranslationUnit$Raw(arg0.operator().value());
    }

    private static MethodHandle clang_defaultReparseOptions;

    private static int clang_defaultReparseOptions$Raw(MemorySegment TU) {
        if (clang_defaultReparseOptions == null) {
            clang_defaultReparseOptions = LibclangSymbolProvider.downcallHandle("clang_defaultReparseOptions", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_defaultReparseOptions"));
        }
        try {
            return (int) clang_defaultReparseOptions.invoke(TU);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_defaultReparseOptions(CXTranslationUnit TU) {
        return new I32(clang_defaultReparseOptions$Raw(TU.operator().value()));
    }

    private static MethodHandle clang_reparseTranslationUnit;

    private static int clang_reparseTranslationUnit$Raw(MemorySegment TU, int num_unsaved_files, MemorySegment unsaved_files, int options) {
        if (clang_reparseTranslationUnit == null) {
            clang_reparseTranslationUnit = LibclangSymbolProvider.downcallHandle("clang_reparseTranslationUnit", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_reparseTranslationUnit"));
        }
        try {
            return (int) clang_reparseTranslationUnit.invoke(TU, num_unsaved_files, unsaved_files, options);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_reparseTranslationUnit(CXTranslationUnit TU, I32I<?> num_unsaved_files, PtrI<? extends StructI<? extends CXUnsavedFile>> unsaved_files, I32I<?> options) {
        return new I32(clang_reparseTranslationUnit$Raw(TU.operator().value(), num_unsaved_files.operator().value(), unsaved_files.operator().value(), options.operator().value()));
    }

    private static MethodHandle clang_getTUResourceUsageName;

    private static MemorySegment clang_getTUResourceUsageName$Raw(int kind) {
        if (clang_getTUResourceUsageName == null) {
            clang_getTUResourceUsageName = LibclangSymbolProvider.downcallHandle("clang_getTUResourceUsageName", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getTUResourceUsageName"));
        }
        try {
            return (MemorySegment) clang_getTUResourceUsageName.invoke(kind);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<I8> clang_getTUResourceUsageName(I32I<? extends CXTUResourceUsageKind> kind) {
        return new Ptr<I8>(clang_getTUResourceUsageName$Raw(kind.operator().value()), I8.OPERATIONS);
    }

    private static MethodHandle clang_getCXTUResourceUsage;

    private static MemorySegment clang_getCXTUResourceUsage$Raw(SegmentAllocator segmentallocator, MemorySegment TU) {
        if (clang_getCXTUResourceUsage == null) {
            clang_getCXTUResourceUsage = LibclangSymbolProvider.downcallHandle("clang_getCXTUResourceUsage", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCXTUResourceUsage"));
        }
        try {
            return (MemorySegment) clang_getCXTUResourceUsage.invoke(segmentallocator, TU);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXTUResourceUsage clang_getCXTUResourceUsage(SegmentAllocator segmentallocator, CXTranslationUnit TU) {
        return new CXTUResourceUsage(clang_getCXTUResourceUsage$Raw(segmentallocator, TU.operator().value()));
    }

    private static MethodHandle clang_disposeCXTUResourceUsage;

    private static void clang_disposeCXTUResourceUsage$Raw(MemorySegment usage) {
        if (clang_disposeCXTUResourceUsage == null) {
            clang_disposeCXTUResourceUsage = LibclangSymbolProvider.downcallHandle("clang_disposeCXTUResourceUsage", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_disposeCXTUResourceUsage"));
        }
        try {
            clang_disposeCXTUResourceUsage.invoke(usage);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_disposeCXTUResourceUsage(StructI<? extends CXTUResourceUsage> usage) {
        clang_disposeCXTUResourceUsage$Raw(usage.operator().value());
    }

    private static MethodHandle clang_getTranslationUnitTargetInfo;

    private static MemorySegment clang_getTranslationUnitTargetInfo$Raw(MemorySegment CTUnit) {
        if (clang_getTranslationUnitTargetInfo == null) {
            clang_getTranslationUnitTargetInfo = LibclangSymbolProvider.downcallHandle("clang_getTranslationUnitTargetInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getTranslationUnitTargetInfo"));
        }
        try {
            return (MemorySegment) clang_getTranslationUnitTargetInfo.invoke(CTUnit);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXTargetInfo clang_getTranslationUnitTargetInfo(CXTranslationUnit CTUnit) {
        return new CXTargetInfo(clang_getTranslationUnitTargetInfo$Raw(CTUnit.operator().value()));
    }

    private static MethodHandle clang_TargetInfo_dispose;

    private static void clang_TargetInfo_dispose$Raw(MemorySegment Info) {
        if (clang_TargetInfo_dispose == null) {
            clang_TargetInfo_dispose = LibclangSymbolProvider.downcallHandle("clang_TargetInfo_dispose", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_TargetInfo_dispose"));
        }
        try {
            clang_TargetInfo_dispose.invoke(Info);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_TargetInfo_dispose(CXTargetInfo Info) {
        clang_TargetInfo_dispose$Raw(Info.operator().value());
    }

    private static MethodHandle clang_TargetInfo_getTriple;

    private static MemorySegment clang_TargetInfo_getTriple$Raw(SegmentAllocator segmentallocator, MemorySegment Info) {
        if (clang_TargetInfo_getTriple == null) {
            clang_TargetInfo_getTriple = LibclangSymbolProvider.downcallHandle("clang_TargetInfo_getTriple", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_TargetInfo_getTriple"));
        }
        try {
            return (MemorySegment) clang_TargetInfo_getTriple.invoke(segmentallocator, Info);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_TargetInfo_getTriple(SegmentAllocator segmentallocator, CXTargetInfo Info) {
        return new CXString(clang_TargetInfo_getTriple$Raw(segmentallocator, Info.operator().value()));
    }

    private static MethodHandle clang_TargetInfo_getPointerWidth;

    private static int clang_TargetInfo_getPointerWidth$Raw(MemorySegment Info) {
        if (clang_TargetInfo_getPointerWidth == null) {
            clang_TargetInfo_getPointerWidth = LibclangSymbolProvider.downcallHandle("clang_TargetInfo_getPointerWidth", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_TargetInfo_getPointerWidth"));
        }
        try {
            return (int) clang_TargetInfo_getPointerWidth.invoke(Info);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_TargetInfo_getPointerWidth(CXTargetInfo Info) {
        return new I32(clang_TargetInfo_getPointerWidth$Raw(Info.operator().value()));
    }

    private static MethodHandle clang_getNullCursor;

    private static MemorySegment clang_getNullCursor$Raw(SegmentAllocator segmentallocator) {
        if (clang_getNullCursor == null) {
            clang_getNullCursor = LibclangSymbolProvider.downcallHandle("clang_getNullCursor", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getNullCursor"));
        }
        try {
            return (MemorySegment) clang_getNullCursor.invoke(segmentallocator);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursor clang_getNullCursor(SegmentAllocator segmentallocator) {
        return new CXCursor(clang_getNullCursor$Raw(segmentallocator));
    }

    private static MethodHandle clang_getTranslationUnitCursor;

    private static MemorySegment clang_getTranslationUnitCursor$Raw(SegmentAllocator segmentallocator, MemorySegment arg0) {
        if (clang_getTranslationUnitCursor == null) {
            clang_getTranslationUnitCursor = LibclangSymbolProvider.downcallHandle("clang_getTranslationUnitCursor", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getTranslationUnitCursor"));
        }
        try {
            return (MemorySegment) clang_getTranslationUnitCursor.invoke(segmentallocator, arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursor clang_getTranslationUnitCursor(SegmentAllocator segmentallocator, CXTranslationUnit arg0) {
        return new CXCursor(clang_getTranslationUnitCursor$Raw(segmentallocator, arg0.operator().value()));
    }

    private static MethodHandle clang_equalCursors;

    private static int clang_equalCursors$Raw(MemorySegment arg0, MemorySegment arg1) {
        if (clang_equalCursors == null) {
            clang_equalCursors = LibclangSymbolProvider.downcallHandle("clang_equalCursors", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_equalCursors"));
        }
        try {
            return (int) clang_equalCursors.invoke(arg0, arg1);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_equalCursors(StructI<? extends CXCursor> arg0, StructI<? extends CXCursor> arg1) {
        return new I32(clang_equalCursors$Raw(arg0.operator().value(), arg1.operator().value()));
    }

    private static MethodHandle clang_Cursor_isNull;

    private static int clang_Cursor_isNull$Raw(MemorySegment cursor) {
        if (clang_Cursor_isNull == null) {
            clang_Cursor_isNull = LibclangSymbolProvider.downcallHandle("clang_Cursor_isNull", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_isNull"));
        }
        try {
            return (int) clang_Cursor_isNull.invoke(cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_isNull(StructI<? extends CXCursor> cursor) {
        return new I32(clang_Cursor_isNull$Raw(cursor.operator().value()));
    }

    private static MethodHandle clang_hashCursor;

    private static int clang_hashCursor$Raw(MemorySegment arg0) {
        if (clang_hashCursor == null) {
            clang_hashCursor = LibclangSymbolProvider.downcallHandle("clang_hashCursor", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_hashCursor"));
        }
        try {
            return (int) clang_hashCursor.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_hashCursor(StructI<? extends CXCursor> arg0) {
        return new I32(clang_hashCursor$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_getCursorKind;

    private static int clang_getCursorKind$Raw(MemorySegment arg0) {
        if (clang_getCursorKind == null) {
            clang_getCursorKind = LibclangSymbolProvider.downcallHandle("clang_getCursorKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorKind"));
        }
        try {
            return (int) clang_getCursorKind.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursorKind clang_getCursorKind(StructI<? extends CXCursor> arg0) {
        return new CXCursorKind(clang_getCursorKind$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_isDeclaration;

    private static int clang_isDeclaration$Raw(int arg0) {
        if (clang_isDeclaration == null) {
            clang_isDeclaration = LibclangSymbolProvider.downcallHandle("clang_isDeclaration", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_isDeclaration"));
        }
        try {
            return (int) clang_isDeclaration.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isDeclaration(I32I<? extends CXCursorKind> arg0) {
        return new I32(clang_isDeclaration$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_isInvalidDeclaration;

    private static int clang_isInvalidDeclaration$Raw(MemorySegment arg0) {
        if (clang_isInvalidDeclaration == null) {
            clang_isInvalidDeclaration = LibclangSymbolProvider.downcallHandle("clang_isInvalidDeclaration", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_isInvalidDeclaration"));
        }
        try {
            return (int) clang_isInvalidDeclaration.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isInvalidDeclaration(StructI<? extends CXCursor> arg0) {
        return new I32(clang_isInvalidDeclaration$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_isReference;

    private static int clang_isReference$Raw(int arg0) {
        if (clang_isReference == null) {
            clang_isReference = LibclangSymbolProvider.downcallHandle("clang_isReference", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_isReference"));
        }
        try {
            return (int) clang_isReference.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isReference(I32I<? extends CXCursorKind> arg0) {
        return new I32(clang_isReference$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_isExpression;

    private static int clang_isExpression$Raw(int arg0) {
        if (clang_isExpression == null) {
            clang_isExpression = LibclangSymbolProvider.downcallHandle("clang_isExpression", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_isExpression"));
        }
        try {
            return (int) clang_isExpression.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isExpression(I32I<? extends CXCursorKind> arg0) {
        return new I32(clang_isExpression$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_isStatement;

    private static int clang_isStatement$Raw(int arg0) {
        if (clang_isStatement == null) {
            clang_isStatement = LibclangSymbolProvider.downcallHandle("clang_isStatement", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_isStatement"));
        }
        try {
            return (int) clang_isStatement.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isStatement(I32I<? extends CXCursorKind> arg0) {
        return new I32(clang_isStatement$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_isAttribute;

    private static int clang_isAttribute$Raw(int arg0) {
        if (clang_isAttribute == null) {
            clang_isAttribute = LibclangSymbolProvider.downcallHandle("clang_isAttribute", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_isAttribute"));
        }
        try {
            return (int) clang_isAttribute.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isAttribute(I32I<? extends CXCursorKind> arg0) {
        return new I32(clang_isAttribute$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_Cursor_hasAttrs;

    private static int clang_Cursor_hasAttrs$Raw(MemorySegment C) {
        if (clang_Cursor_hasAttrs == null) {
            clang_Cursor_hasAttrs = LibclangSymbolProvider.downcallHandle("clang_Cursor_hasAttrs", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_hasAttrs"));
        }
        try {
            return (int) clang_Cursor_hasAttrs.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_hasAttrs(StructI<? extends CXCursor> C) {
        return new I32(clang_Cursor_hasAttrs$Raw(C.operator().value()));
    }

    private static MethodHandle clang_isInvalid;

    private static int clang_isInvalid$Raw(int arg0) {
        if (clang_isInvalid == null) {
            clang_isInvalid = LibclangSymbolProvider.downcallHandle("clang_isInvalid", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_isInvalid"));
        }
        try {
            return (int) clang_isInvalid.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isInvalid(I32I<? extends CXCursorKind> arg0) {
        return new I32(clang_isInvalid$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_isTranslationUnit;

    private static int clang_isTranslationUnit$Raw(int arg0) {
        if (clang_isTranslationUnit == null) {
            clang_isTranslationUnit = LibclangSymbolProvider.downcallHandle("clang_isTranslationUnit", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_isTranslationUnit"));
        }
        try {
            return (int) clang_isTranslationUnit.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isTranslationUnit(I32I<? extends CXCursorKind> arg0) {
        return new I32(clang_isTranslationUnit$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_isPreprocessing;

    private static int clang_isPreprocessing$Raw(int arg0) {
        if (clang_isPreprocessing == null) {
            clang_isPreprocessing = LibclangSymbolProvider.downcallHandle("clang_isPreprocessing", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_isPreprocessing"));
        }
        try {
            return (int) clang_isPreprocessing.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isPreprocessing(I32I<? extends CXCursorKind> arg0) {
        return new I32(clang_isPreprocessing$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_isUnexposed;

    private static int clang_isUnexposed$Raw(int arg0) {
        if (clang_isUnexposed == null) {
            clang_isUnexposed = LibclangSymbolProvider.downcallHandle("clang_isUnexposed", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_isUnexposed"));
        }
        try {
            return (int) clang_isUnexposed.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isUnexposed(I32I<? extends CXCursorKind> arg0) {
        return new I32(clang_isUnexposed$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_getCursorLinkage;

    private static int clang_getCursorLinkage$Raw(MemorySegment cursor) {
        if (clang_getCursorLinkage == null) {
            clang_getCursorLinkage = LibclangSymbolProvider.downcallHandle("clang_getCursorLinkage", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorLinkage"));
        }
        try {
            return (int) clang_getCursorLinkage.invoke(cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXLinkageKind clang_getCursorLinkage(StructI<? extends CXCursor> cursor) {
        return new CXLinkageKind(clang_getCursorLinkage$Raw(cursor.operator().value()));
    }

    private static MethodHandle clang_getCursorVisibility;

    private static int clang_getCursorVisibility$Raw(MemorySegment cursor) {
        if (clang_getCursorVisibility == null) {
            clang_getCursorVisibility = LibclangSymbolProvider.downcallHandle("clang_getCursorVisibility", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorVisibility"));
        }
        try {
            return (int) clang_getCursorVisibility.invoke(cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXVisibilityKind clang_getCursorVisibility(StructI<? extends CXCursor> cursor) {
        return new CXVisibilityKind(clang_getCursorVisibility$Raw(cursor.operator().value()));
    }

    private static MethodHandle clang_getCursorAvailability;

    private static int clang_getCursorAvailability$Raw(MemorySegment cursor) {
        if (clang_getCursorAvailability == null) {
            clang_getCursorAvailability = LibclangSymbolProvider.downcallHandle("clang_getCursorAvailability", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorAvailability"));
        }
        try {
            return (int) clang_getCursorAvailability.invoke(cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXAvailabilityKind clang_getCursorAvailability(StructI<? extends CXCursor> cursor) {
        return new CXAvailabilityKind(clang_getCursorAvailability$Raw(cursor.operator().value()));
    }

    private static MethodHandle clang_getCursorPlatformAvailability;

    private static int clang_getCursorPlatformAvailability$Raw(MemorySegment cursor, MemorySegment always_deprecated, MemorySegment deprecated_message, MemorySegment always_unavailable, MemorySegment unavailable_message, MemorySegment availability, int availability_size) {
        if (clang_getCursorPlatformAvailability == null) {
            clang_getCursorPlatformAvailability = LibclangSymbolProvider.downcallHandle("clang_getCursorPlatformAvailability", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorPlatformAvailability"));
        }
        try {
            return (int) clang_getCursorPlatformAvailability.invoke(cursor, always_deprecated, deprecated_message, always_unavailable, unavailable_message, availability, availability_size);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getCursorPlatformAvailability(StructI<? extends CXCursor> cursor, PtrI<? extends I32I<?>> always_deprecated, PtrI<? extends StructI<? extends CXString>> deprecated_message, PtrI<? extends I32I<?>> always_unavailable, PtrI<? extends StructI<? extends CXString>> unavailable_message, PtrI<? extends StructI<? extends CXPlatformAvailability>> availability, I32I<?> availability_size) {
        return new I32(clang_getCursorPlatformAvailability$Raw(cursor.operator().value(), always_deprecated.operator().value(), deprecated_message.operator().value(), always_unavailable.operator().value(), unavailable_message.operator().value(), availability.operator().value(), availability_size.operator().value()));
    }

    private static MethodHandle clang_disposeCXPlatformAvailability;

    private static void clang_disposeCXPlatformAvailability$Raw(MemorySegment availability) {
        if (clang_disposeCXPlatformAvailability == null) {
            clang_disposeCXPlatformAvailability = LibclangSymbolProvider.downcallHandle("clang_disposeCXPlatformAvailability", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_disposeCXPlatformAvailability"));
        }
        try {
            clang_disposeCXPlatformAvailability.invoke(availability);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_disposeCXPlatformAvailability(PtrI<? extends StructI<? extends CXPlatformAvailability>> availability) {
        clang_disposeCXPlatformAvailability$Raw(availability.operator().value());
    }

    private static MethodHandle clang_Cursor_getVarDeclInitializer;

    private static MemorySegment clang_Cursor_getVarDeclInitializer$Raw(SegmentAllocator segmentallocator, MemorySegment cursor) {
        if (clang_Cursor_getVarDeclInitializer == null) {
            clang_Cursor_getVarDeclInitializer = LibclangSymbolProvider.downcallHandle("clang_Cursor_getVarDeclInitializer", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getVarDeclInitializer"));
        }
        try {
            return (MemorySegment) clang_Cursor_getVarDeclInitializer.invoke(segmentallocator, cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursor clang_Cursor_getVarDeclInitializer(SegmentAllocator segmentallocator, StructI<? extends CXCursor> cursor) {
        return new CXCursor(clang_Cursor_getVarDeclInitializer$Raw(segmentallocator, cursor.operator().value()));
    }

    private static MethodHandle clang_Cursor_hasVarDeclGlobalStorage;

    private static int clang_Cursor_hasVarDeclGlobalStorage$Raw(MemorySegment cursor) {
        if (clang_Cursor_hasVarDeclGlobalStorage == null) {
            clang_Cursor_hasVarDeclGlobalStorage = LibclangSymbolProvider.downcallHandle("clang_Cursor_hasVarDeclGlobalStorage", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_hasVarDeclGlobalStorage"));
        }
        try {
            return (int) clang_Cursor_hasVarDeclGlobalStorage.invoke(cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_hasVarDeclGlobalStorage(StructI<? extends CXCursor> cursor) {
        return new I32(clang_Cursor_hasVarDeclGlobalStorage$Raw(cursor.operator().value()));
    }

    private static MethodHandle clang_Cursor_hasVarDeclExternalStorage;

    private static int clang_Cursor_hasVarDeclExternalStorage$Raw(MemorySegment cursor) {
        if (clang_Cursor_hasVarDeclExternalStorage == null) {
            clang_Cursor_hasVarDeclExternalStorage = LibclangSymbolProvider.downcallHandle("clang_Cursor_hasVarDeclExternalStorage", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_hasVarDeclExternalStorage"));
        }
        try {
            return (int) clang_Cursor_hasVarDeclExternalStorage.invoke(cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_hasVarDeclExternalStorage(StructI<? extends CXCursor> cursor) {
        return new I32(clang_Cursor_hasVarDeclExternalStorage$Raw(cursor.operator().value()));
    }

    private static MethodHandle clang_getCursorLanguage;

    private static int clang_getCursorLanguage$Raw(MemorySegment cursor) {
        if (clang_getCursorLanguage == null) {
            clang_getCursorLanguage = LibclangSymbolProvider.downcallHandle("clang_getCursorLanguage", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorLanguage"));
        }
        try {
            return (int) clang_getCursorLanguage.invoke(cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXLanguageKind clang_getCursorLanguage(StructI<? extends CXCursor> cursor) {
        return new CXLanguageKind(clang_getCursorLanguage$Raw(cursor.operator().value()));
    }

    private static MethodHandle clang_getCursorTLSKind;

    private static int clang_getCursorTLSKind$Raw(MemorySegment cursor) {
        if (clang_getCursorTLSKind == null) {
            clang_getCursorTLSKind = LibclangSymbolProvider.downcallHandle("clang_getCursorTLSKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorTLSKind"));
        }
        try {
            return (int) clang_getCursorTLSKind.invoke(cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXTLSKind clang_getCursorTLSKind(StructI<? extends CXCursor> cursor) {
        return new CXTLSKind(clang_getCursorTLSKind$Raw(cursor.operator().value()));
    }

    private static MethodHandle clang_Cursor_getTranslationUnit;

    private static MemorySegment clang_Cursor_getTranslationUnit$Raw(MemorySegment arg0) {
        if (clang_Cursor_getTranslationUnit == null) {
            clang_Cursor_getTranslationUnit = LibclangSymbolProvider.downcallHandle("clang_Cursor_getTranslationUnit", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getTranslationUnit"));
        }
        try {
            return (MemorySegment) clang_Cursor_getTranslationUnit.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXTranslationUnit clang_Cursor_getTranslationUnit(StructI<? extends CXCursor> arg0) {
        return new CXTranslationUnit(clang_Cursor_getTranslationUnit$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_createCXCursorSet;

    private static MemorySegment clang_createCXCursorSet$Raw() {
        if (clang_createCXCursorSet == null) {
            clang_createCXCursorSet = LibclangSymbolProvider.downcallHandle("clang_createCXCursorSet", FunctionDescriptor.of(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_createCXCursorSet"));
        }
        try {
            return (MemorySegment) clang_createCXCursorSet.invoke();
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursorSet clang_createCXCursorSet() {
        return new CXCursorSet(clang_createCXCursorSet$Raw());
    }

    private static MethodHandle clang_disposeCXCursorSet;

    private static void clang_disposeCXCursorSet$Raw(MemorySegment cset) {
        if (clang_disposeCXCursorSet == null) {
            clang_disposeCXCursorSet = LibclangSymbolProvider.downcallHandle("clang_disposeCXCursorSet", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_disposeCXCursorSet"));
        }
        try {
            clang_disposeCXCursorSet.invoke(cset);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_disposeCXCursorSet(CXCursorSet cset) {
        clang_disposeCXCursorSet$Raw(cset.operator().value());
    }

    private static MethodHandle clang_CXCursorSet_contains;

    private static int clang_CXCursorSet_contains$Raw(MemorySegment cset, MemorySegment cursor) {
        if (clang_CXCursorSet_contains == null) {
            clang_CXCursorSet_contains = LibclangSymbolProvider.downcallHandle("clang_CXCursorSet_contains", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXCursorSet_contains"));
        }
        try {
            return (int) clang_CXCursorSet_contains.invoke(cset, cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXCursorSet_contains(CXCursorSet cset, StructI<? extends CXCursor> cursor) {
        return new I32(clang_CXCursorSet_contains$Raw(cset.operator().value(), cursor.operator().value()));
    }

    private static MethodHandle clang_CXCursorSet_insert;

    private static int clang_CXCursorSet_insert$Raw(MemorySegment cset, MemorySegment cursor) {
        if (clang_CXCursorSet_insert == null) {
            clang_CXCursorSet_insert = LibclangSymbolProvider.downcallHandle("clang_CXCursorSet_insert", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXCursorSet_insert"));
        }
        try {
            return (int) clang_CXCursorSet_insert.invoke(cset, cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXCursorSet_insert(CXCursorSet cset, StructI<? extends CXCursor> cursor) {
        return new I32(clang_CXCursorSet_insert$Raw(cset.operator().value(), cursor.operator().value()));
    }

    private static MethodHandle clang_getCursorSemanticParent;

    private static MemorySegment clang_getCursorSemanticParent$Raw(SegmentAllocator segmentallocator, MemorySegment cursor) {
        if (clang_getCursorSemanticParent == null) {
            clang_getCursorSemanticParent = LibclangSymbolProvider.downcallHandle("clang_getCursorSemanticParent", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorSemanticParent"));
        }
        try {
            return (MemorySegment) clang_getCursorSemanticParent.invoke(segmentallocator, cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursor clang_getCursorSemanticParent(SegmentAllocator segmentallocator, StructI<? extends CXCursor> cursor) {
        return new CXCursor(clang_getCursorSemanticParent$Raw(segmentallocator, cursor.operator().value()));
    }

    private static MethodHandle clang_getCursorLexicalParent;

    private static MemorySegment clang_getCursorLexicalParent$Raw(SegmentAllocator segmentallocator, MemorySegment cursor) {
        if (clang_getCursorLexicalParent == null) {
            clang_getCursorLexicalParent = LibclangSymbolProvider.downcallHandle("clang_getCursorLexicalParent", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorLexicalParent"));
        }
        try {
            return (MemorySegment) clang_getCursorLexicalParent.invoke(segmentallocator, cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursor clang_getCursorLexicalParent(SegmentAllocator segmentallocator, StructI<? extends CXCursor> cursor) {
        return new CXCursor(clang_getCursorLexicalParent$Raw(segmentallocator, cursor.operator().value()));
    }

    private static MethodHandle clang_getOverriddenCursors;

    private static void clang_getOverriddenCursors$Raw(MemorySegment cursor, MemorySegment overridden, MemorySegment num_overridden) {
        if (clang_getOverriddenCursors == null) {
            clang_getOverriddenCursors = LibclangSymbolProvider.downcallHandle("clang_getOverriddenCursors", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getOverriddenCursors"));
        }
        try {
            clang_getOverriddenCursors.invoke(cursor, overridden, num_overridden);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_getOverriddenCursors(StructI<? extends CXCursor> cursor, PtrI<? extends PtrI<? extends StructI<? extends CXCursor>>> overridden, PtrI<? extends I32I<?>> num_overridden) {
        clang_getOverriddenCursors$Raw(cursor.operator().value(), overridden.operator().value(), num_overridden.operator().value());
    }

    private static MethodHandle clang_disposeOverriddenCursors;

    private static void clang_disposeOverriddenCursors$Raw(MemorySegment overridden) {
        if (clang_disposeOverriddenCursors == null) {
            clang_disposeOverriddenCursors = LibclangSymbolProvider.downcallHandle("clang_disposeOverriddenCursors", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_disposeOverriddenCursors"));
        }
        try {
            clang_disposeOverriddenCursors.invoke(overridden);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_disposeOverriddenCursors(PtrI<? extends StructI<? extends CXCursor>> overridden) {
        clang_disposeOverriddenCursors$Raw(overridden.operator().value());
    }

    private static MethodHandle clang_getIncludedFile;

    private static MemorySegment clang_getIncludedFile$Raw(MemorySegment cursor) {
        if (clang_getIncludedFile == null) {
            clang_getIncludedFile = LibclangSymbolProvider.downcallHandle("clang_getIncludedFile", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getIncludedFile"));
        }
        try {
            return (MemorySegment) clang_getIncludedFile.invoke(cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXFile clang_getIncludedFile(StructI<? extends CXCursor> cursor) {
        return new CXFile(clang_getIncludedFile$Raw(cursor.operator().value()));
    }

    private static MethodHandle clang_getCursor;

    private static MemorySegment clang_getCursor$Raw(SegmentAllocator segmentallocator, MemorySegment arg0, MemorySegment arg1) {
        if (clang_getCursor == null) {
            clang_getCursor = LibclangSymbolProvider.downcallHandle("clang_getCursor", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursor"));
        }
        try {
            return (MemorySegment) clang_getCursor.invoke(segmentallocator, arg0, arg1);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursor clang_getCursor(SegmentAllocator segmentallocator, CXTranslationUnit arg0, StructI<? extends CXSourceLocation> arg1) {
        return new CXCursor(clang_getCursor$Raw(segmentallocator, arg0.operator().value(), arg1.operator().value()));
    }

    private static MethodHandle clang_getCursorLocation;

    private static MemorySegment clang_getCursorLocation$Raw(SegmentAllocator segmentallocator, MemorySegment arg0) {
        if (clang_getCursorLocation == null) {
            clang_getCursorLocation = LibclangSymbolProvider.downcallHandle("clang_getCursorLocation", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorLocation"));
        }
        try {
            return (MemorySegment) clang_getCursorLocation.invoke(segmentallocator, arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceLocation clang_getCursorLocation(SegmentAllocator segmentallocator, StructI<? extends CXCursor> arg0) {
        return new CXSourceLocation(clang_getCursorLocation$Raw(segmentallocator, arg0.operator().value()));
    }

    private static MethodHandle clang_getCursorExtent;

    private static MemorySegment clang_getCursorExtent$Raw(SegmentAllocator segmentallocator, MemorySegment arg0) {
        if (clang_getCursorExtent == null) {
            clang_getCursorExtent = LibclangSymbolProvider.downcallHandle("clang_getCursorExtent", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorExtent"));
        }
        try {
            return (MemorySegment) clang_getCursorExtent.invoke(segmentallocator, arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceRange clang_getCursorExtent(SegmentAllocator segmentallocator, StructI<? extends CXCursor> arg0) {
        return new CXSourceRange(clang_getCursorExtent$Raw(segmentallocator, arg0.operator().value()));
    }

    private static MethodHandle clang_getCursorType;

    private static MemorySegment clang_getCursorType$Raw(SegmentAllocator segmentallocator, MemorySegment C) {
        if (clang_getCursorType == null) {
            clang_getCursorType = LibclangSymbolProvider.downcallHandle("clang_getCursorType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorType"));
        }
        try {
            return (MemorySegment) clang_getCursorType.invoke(segmentallocator, C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_getCursorType(SegmentAllocator segmentallocator, StructI<? extends CXCursor> C) {
        return new CXType(clang_getCursorType$Raw(segmentallocator, C.operator().value()));
    }

    private static MethodHandle clang_getTypeSpelling;

    private static MemorySegment clang_getTypeSpelling$Raw(SegmentAllocator segmentallocator, MemorySegment CT) {
        if (clang_getTypeSpelling == null) {
            clang_getTypeSpelling = LibclangSymbolProvider.downcallHandle("clang_getTypeSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getTypeSpelling"));
        }
        try {
            return (MemorySegment) clang_getTypeSpelling.invoke(segmentallocator, CT);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getTypeSpelling(SegmentAllocator segmentallocator, StructI<? extends CXType> CT) {
        return new CXString(clang_getTypeSpelling$Raw(segmentallocator, CT.operator().value()));
    }

    private static MethodHandle clang_getTypedefDeclUnderlyingType;

    private static MemorySegment clang_getTypedefDeclUnderlyingType$Raw(SegmentAllocator segmentallocator, MemorySegment C) {
        if (clang_getTypedefDeclUnderlyingType == null) {
            clang_getTypedefDeclUnderlyingType = LibclangSymbolProvider.downcallHandle("clang_getTypedefDeclUnderlyingType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getTypedefDeclUnderlyingType"));
        }
        try {
            return (MemorySegment) clang_getTypedefDeclUnderlyingType.invoke(segmentallocator, C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_getTypedefDeclUnderlyingType(SegmentAllocator segmentallocator, StructI<? extends CXCursor> C) {
        return new CXType(clang_getTypedefDeclUnderlyingType$Raw(segmentallocator, C.operator().value()));
    }

    private static MethodHandle clang_getEnumDeclIntegerType;

    private static MemorySegment clang_getEnumDeclIntegerType$Raw(SegmentAllocator segmentallocator, MemorySegment C) {
        if (clang_getEnumDeclIntegerType == null) {
            clang_getEnumDeclIntegerType = LibclangSymbolProvider.downcallHandle("clang_getEnumDeclIntegerType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getEnumDeclIntegerType"));
        }
        try {
            return (MemorySegment) clang_getEnumDeclIntegerType.invoke(segmentallocator, C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_getEnumDeclIntegerType(SegmentAllocator segmentallocator, StructI<? extends CXCursor> C) {
        return new CXType(clang_getEnumDeclIntegerType$Raw(segmentallocator, C.operator().value()));
    }

    private static MethodHandle clang_getEnumConstantDeclValue;

    private static long clang_getEnumConstantDeclValue$Raw(MemorySegment C) {
        if (clang_getEnumConstantDeclValue == null) {
            clang_getEnumConstantDeclValue = LibclangSymbolProvider.downcallHandle("clang_getEnumConstantDeclValue", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getEnumConstantDeclValue"));
        }
        try {
            return (long) clang_getEnumConstantDeclValue.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I64 clang_getEnumConstantDeclValue(StructI<? extends CXCursor> C) {
        return new I64(clang_getEnumConstantDeclValue$Raw(C.operator().value()));
    }

    private static MethodHandle clang_getEnumConstantDeclUnsignedValue;

    private static long clang_getEnumConstantDeclUnsignedValue$Raw(MemorySegment C) {
        if (clang_getEnumConstantDeclUnsignedValue == null) {
            clang_getEnumConstantDeclUnsignedValue = LibclangSymbolProvider.downcallHandle("clang_getEnumConstantDeclUnsignedValue", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getEnumConstantDeclUnsignedValue"));
        }
        try {
            return (long) clang_getEnumConstantDeclUnsignedValue.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I64 clang_getEnumConstantDeclUnsignedValue(StructI<? extends CXCursor> C) {
        return new I64(clang_getEnumConstantDeclUnsignedValue$Raw(C.operator().value()));
    }

    private static MethodHandle clang_Cursor_isBitField;

    private static int clang_Cursor_isBitField$Raw(MemorySegment C) {
        if (clang_Cursor_isBitField == null) {
            clang_Cursor_isBitField = LibclangSymbolProvider.downcallHandle("clang_Cursor_isBitField", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_isBitField"));
        }
        try {
            return (int) clang_Cursor_isBitField.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_isBitField(StructI<? extends CXCursor> C) {
        return new I32(clang_Cursor_isBitField$Raw(C.operator().value()));
    }

    private static MethodHandle clang_getFieldDeclBitWidth;

    private static int clang_getFieldDeclBitWidth$Raw(MemorySegment C) {
        if (clang_getFieldDeclBitWidth == null) {
            clang_getFieldDeclBitWidth = LibclangSymbolProvider.downcallHandle("clang_getFieldDeclBitWidth", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getFieldDeclBitWidth"));
        }
        try {
            return (int) clang_getFieldDeclBitWidth.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getFieldDeclBitWidth(StructI<? extends CXCursor> C) {
        return new I32(clang_getFieldDeclBitWidth$Raw(C.operator().value()));
    }

    private static MethodHandle clang_Cursor_getNumArguments;

    private static int clang_Cursor_getNumArguments$Raw(MemorySegment C) {
        if (clang_Cursor_getNumArguments == null) {
            clang_Cursor_getNumArguments = LibclangSymbolProvider.downcallHandle("clang_Cursor_getNumArguments", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getNumArguments"));
        }
        try {
            return (int) clang_Cursor_getNumArguments.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_getNumArguments(StructI<? extends CXCursor> C) {
        return new I32(clang_Cursor_getNumArguments$Raw(C.operator().value()));
    }

    private static MethodHandle clang_Cursor_getArgument;

    private static MemorySegment clang_Cursor_getArgument$Raw(SegmentAllocator segmentallocator, MemorySegment C, int i) {
        if (clang_Cursor_getArgument == null) {
            clang_Cursor_getArgument = LibclangSymbolProvider.downcallHandle("clang_Cursor_getArgument", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getArgument"));
        }
        try {
            return (MemorySegment) clang_Cursor_getArgument.invoke(segmentallocator, C, i);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursor clang_Cursor_getArgument(SegmentAllocator segmentallocator, StructI<? extends CXCursor> C, I32I<?> i) {
        return new CXCursor(clang_Cursor_getArgument$Raw(segmentallocator, C.operator().value(), i.operator().value()));
    }

    private static MethodHandle clang_Cursor_getNumTemplateArguments;

    private static int clang_Cursor_getNumTemplateArguments$Raw(MemorySegment C) {
        if (clang_Cursor_getNumTemplateArguments == null) {
            clang_Cursor_getNumTemplateArguments = LibclangSymbolProvider.downcallHandle("clang_Cursor_getNumTemplateArguments", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getNumTemplateArguments"));
        }
        try {
            return (int) clang_Cursor_getNumTemplateArguments.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_getNumTemplateArguments(StructI<? extends CXCursor> C) {
        return new I32(clang_Cursor_getNumTemplateArguments$Raw(C.operator().value()));
    }

    private static MethodHandle clang_Cursor_getTemplateArgumentKind;

    private static int clang_Cursor_getTemplateArgumentKind$Raw(MemorySegment C, int I) {
        if (clang_Cursor_getTemplateArgumentKind == null) {
            clang_Cursor_getTemplateArgumentKind = LibclangSymbolProvider.downcallHandle("clang_Cursor_getTemplateArgumentKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getTemplateArgumentKind"));
        }
        try {
            return (int) clang_Cursor_getTemplateArgumentKind.invoke(C, I);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXTemplateArgumentKind clang_Cursor_getTemplateArgumentKind(StructI<? extends CXCursor> C, I32I<?> I) {
        return new CXTemplateArgumentKind(clang_Cursor_getTemplateArgumentKind$Raw(C.operator().value(), I.operator().value()));
    }

    private static MethodHandle clang_Cursor_getTemplateArgumentType;

    private static MemorySegment clang_Cursor_getTemplateArgumentType$Raw(SegmentAllocator segmentallocator, MemorySegment C, int I) {
        if (clang_Cursor_getTemplateArgumentType == null) {
            clang_Cursor_getTemplateArgumentType = LibclangSymbolProvider.downcallHandle("clang_Cursor_getTemplateArgumentType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getTemplateArgumentType"));
        }
        try {
            return (MemorySegment) clang_Cursor_getTemplateArgumentType.invoke(segmentallocator, C, I);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_Cursor_getTemplateArgumentType(SegmentAllocator segmentallocator, StructI<? extends CXCursor> C, I32I<?> I) {
        return new CXType(clang_Cursor_getTemplateArgumentType$Raw(segmentallocator, C.operator().value(), I.operator().value()));
    }

    private static MethodHandle clang_Cursor_getTemplateArgumentValue;

    private static long clang_Cursor_getTemplateArgumentValue$Raw(MemorySegment C, int I) {
        if (clang_Cursor_getTemplateArgumentValue == null) {
            clang_Cursor_getTemplateArgumentValue = LibclangSymbolProvider.downcallHandle("clang_Cursor_getTemplateArgumentValue", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getTemplateArgumentValue"));
        }
        try {
            return (long) clang_Cursor_getTemplateArgumentValue.invoke(C, I);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I64 clang_Cursor_getTemplateArgumentValue(StructI<? extends CXCursor> C, I32I<?> I) {
        return new I64(clang_Cursor_getTemplateArgumentValue$Raw(C.operator().value(), I.operator().value()));
    }

    private static MethodHandle clang_Cursor_getTemplateArgumentUnsignedValue;

    private static long clang_Cursor_getTemplateArgumentUnsignedValue$Raw(MemorySegment C, int I) {
        if (clang_Cursor_getTemplateArgumentUnsignedValue == null) {
            clang_Cursor_getTemplateArgumentUnsignedValue = LibclangSymbolProvider.downcallHandle("clang_Cursor_getTemplateArgumentUnsignedValue", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getTemplateArgumentUnsignedValue"));
        }
        try {
            return (long) clang_Cursor_getTemplateArgumentUnsignedValue.invoke(C, I);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I64 clang_Cursor_getTemplateArgumentUnsignedValue(StructI<? extends CXCursor> C, I32I<?> I) {
        return new I64(clang_Cursor_getTemplateArgumentUnsignedValue$Raw(C.operator().value(), I.operator().value()));
    }

    private static MethodHandle clang_equalTypes;

    private static int clang_equalTypes$Raw(MemorySegment A, MemorySegment B) {
        if (clang_equalTypes == null) {
            clang_equalTypes = LibclangSymbolProvider.downcallHandle("clang_equalTypes", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_equalTypes"));
        }
        try {
            return (int) clang_equalTypes.invoke(A, B);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_equalTypes(StructI<? extends CXType> A, StructI<? extends CXType> B) {
        return new I32(clang_equalTypes$Raw(A.operator().value(), B.operator().value()));
    }

    private static MethodHandle clang_getCanonicalType;

    private static MemorySegment clang_getCanonicalType$Raw(SegmentAllocator segmentallocator, MemorySegment T) {
        if (clang_getCanonicalType == null) {
            clang_getCanonicalType = LibclangSymbolProvider.downcallHandle("clang_getCanonicalType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCanonicalType"));
        }
        try {
            return (MemorySegment) clang_getCanonicalType.invoke(segmentallocator, T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_getCanonicalType(SegmentAllocator segmentallocator, StructI<? extends CXType> T) {
        return new CXType(clang_getCanonicalType$Raw(segmentallocator, T.operator().value()));
    }

    private static MethodHandle clang_isConstQualifiedType;

    private static int clang_isConstQualifiedType$Raw(MemorySegment T) {
        if (clang_isConstQualifiedType == null) {
            clang_isConstQualifiedType = LibclangSymbolProvider.downcallHandle("clang_isConstQualifiedType", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_isConstQualifiedType"));
        }
        try {
            return (int) clang_isConstQualifiedType.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isConstQualifiedType(StructI<? extends CXType> T) {
        return new I32(clang_isConstQualifiedType$Raw(T.operator().value()));
    }

    private static MethodHandle clang_Cursor_isMacroFunctionLike;

    private static int clang_Cursor_isMacroFunctionLike$Raw(MemorySegment C) {
        if (clang_Cursor_isMacroFunctionLike == null) {
            clang_Cursor_isMacroFunctionLike = LibclangSymbolProvider.downcallHandle("clang_Cursor_isMacroFunctionLike", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_isMacroFunctionLike"));
        }
        try {
            return (int) clang_Cursor_isMacroFunctionLike.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_isMacroFunctionLike(StructI<? extends CXCursor> C) {
        return new I32(clang_Cursor_isMacroFunctionLike$Raw(C.operator().value()));
    }

    private static MethodHandle clang_Cursor_isMacroBuiltin;

    private static int clang_Cursor_isMacroBuiltin$Raw(MemorySegment C) {
        if (clang_Cursor_isMacroBuiltin == null) {
            clang_Cursor_isMacroBuiltin = LibclangSymbolProvider.downcallHandle("clang_Cursor_isMacroBuiltin", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_isMacroBuiltin"));
        }
        try {
            return (int) clang_Cursor_isMacroBuiltin.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_isMacroBuiltin(StructI<? extends CXCursor> C) {
        return new I32(clang_Cursor_isMacroBuiltin$Raw(C.operator().value()));
    }

    private static MethodHandle clang_Cursor_isFunctionInlined;

    private static int clang_Cursor_isFunctionInlined$Raw(MemorySegment C) {
        if (clang_Cursor_isFunctionInlined == null) {
            clang_Cursor_isFunctionInlined = LibclangSymbolProvider.downcallHandle("clang_Cursor_isFunctionInlined", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_isFunctionInlined"));
        }
        try {
            return (int) clang_Cursor_isFunctionInlined.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_isFunctionInlined(StructI<? extends CXCursor> C) {
        return new I32(clang_Cursor_isFunctionInlined$Raw(C.operator().value()));
    }

    private static MethodHandle clang_isVolatileQualifiedType;

    private static int clang_isVolatileQualifiedType$Raw(MemorySegment T) {
        if (clang_isVolatileQualifiedType == null) {
            clang_isVolatileQualifiedType = LibclangSymbolProvider.downcallHandle("clang_isVolatileQualifiedType", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_isVolatileQualifiedType"));
        }
        try {
            return (int) clang_isVolatileQualifiedType.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isVolatileQualifiedType(StructI<? extends CXType> T) {
        return new I32(clang_isVolatileQualifiedType$Raw(T.operator().value()));
    }

    private static MethodHandle clang_isRestrictQualifiedType;

    private static int clang_isRestrictQualifiedType$Raw(MemorySegment T) {
        if (clang_isRestrictQualifiedType == null) {
            clang_isRestrictQualifiedType = LibclangSymbolProvider.downcallHandle("clang_isRestrictQualifiedType", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_isRestrictQualifiedType"));
        }
        try {
            return (int) clang_isRestrictQualifiedType.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isRestrictQualifiedType(StructI<? extends CXType> T) {
        return new I32(clang_isRestrictQualifiedType$Raw(T.operator().value()));
    }

    private static MethodHandle clang_getAddressSpace;

    private static int clang_getAddressSpace$Raw(MemorySegment T) {
        if (clang_getAddressSpace == null) {
            clang_getAddressSpace = LibclangSymbolProvider.downcallHandle("clang_getAddressSpace", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getAddressSpace"));
        }
        try {
            return (int) clang_getAddressSpace.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getAddressSpace(StructI<? extends CXType> T) {
        return new I32(clang_getAddressSpace$Raw(T.operator().value()));
    }

    private static MethodHandle clang_getTypedefName;

    private static MemorySegment clang_getTypedefName$Raw(SegmentAllocator segmentallocator, MemorySegment CT) {
        if (clang_getTypedefName == null) {
            clang_getTypedefName = LibclangSymbolProvider.downcallHandle("clang_getTypedefName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getTypedefName"));
        }
        try {
            return (MemorySegment) clang_getTypedefName.invoke(segmentallocator, CT);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getTypedefName(SegmentAllocator segmentallocator, StructI<? extends CXType> CT) {
        return new CXString(clang_getTypedefName$Raw(segmentallocator, CT.operator().value()));
    }

    private static MethodHandle clang_getPointeeType;

    private static MemorySegment clang_getPointeeType$Raw(SegmentAllocator segmentallocator, MemorySegment T) {
        if (clang_getPointeeType == null) {
            clang_getPointeeType = LibclangSymbolProvider.downcallHandle("clang_getPointeeType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getPointeeType"));
        }
        try {
            return (MemorySegment) clang_getPointeeType.invoke(segmentallocator, T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_getPointeeType(SegmentAllocator segmentallocator, StructI<? extends CXType> T) {
        return new CXType(clang_getPointeeType$Raw(segmentallocator, T.operator().value()));
    }

    private static MethodHandle clang_getUnqualifiedType;

    private static MemorySegment clang_getUnqualifiedType$Raw(SegmentAllocator segmentallocator, MemorySegment CT) {
        if (clang_getUnqualifiedType == null) {
            clang_getUnqualifiedType = LibclangSymbolProvider.downcallHandle("clang_getUnqualifiedType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getUnqualifiedType"));
        }
        try {
            return (MemorySegment) clang_getUnqualifiedType.invoke(segmentallocator, CT);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_getUnqualifiedType(SegmentAllocator segmentallocator, StructI<? extends CXType> CT) {
        return new CXType(clang_getUnqualifiedType$Raw(segmentallocator, CT.operator().value()));
    }

    private static MethodHandle clang_getNonReferenceType;

    private static MemorySegment clang_getNonReferenceType$Raw(SegmentAllocator segmentallocator, MemorySegment CT) {
        if (clang_getNonReferenceType == null) {
            clang_getNonReferenceType = LibclangSymbolProvider.downcallHandle("clang_getNonReferenceType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getNonReferenceType"));
        }
        try {
            return (MemorySegment) clang_getNonReferenceType.invoke(segmentallocator, CT);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_getNonReferenceType(SegmentAllocator segmentallocator, StructI<? extends CXType> CT) {
        return new CXType(clang_getNonReferenceType$Raw(segmentallocator, CT.operator().value()));
    }

    private static MethodHandle clang_getTypeDeclaration;

    private static MemorySegment clang_getTypeDeclaration$Raw(SegmentAllocator segmentallocator, MemorySegment T) {
        if (clang_getTypeDeclaration == null) {
            clang_getTypeDeclaration = LibclangSymbolProvider.downcallHandle("clang_getTypeDeclaration", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getTypeDeclaration"));
        }
        try {
            return (MemorySegment) clang_getTypeDeclaration.invoke(segmentallocator, T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursor clang_getTypeDeclaration(SegmentAllocator segmentallocator, StructI<? extends CXType> T) {
        return new CXCursor(clang_getTypeDeclaration$Raw(segmentallocator, T.operator().value()));
    }

    private static MethodHandle clang_getDeclObjCTypeEncoding;

    private static MemorySegment clang_getDeclObjCTypeEncoding$Raw(SegmentAllocator segmentallocator, MemorySegment C) {
        if (clang_getDeclObjCTypeEncoding == null) {
            clang_getDeclObjCTypeEncoding = LibclangSymbolProvider.downcallHandle("clang_getDeclObjCTypeEncoding", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDeclObjCTypeEncoding"));
        }
        try {
            return (MemorySegment) clang_getDeclObjCTypeEncoding.invoke(segmentallocator, C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getDeclObjCTypeEncoding(SegmentAllocator segmentallocator, StructI<? extends CXCursor> C) {
        return new CXString(clang_getDeclObjCTypeEncoding$Raw(segmentallocator, C.operator().value()));
    }

    private static MethodHandle clang_Type_getObjCEncoding;

    private static MemorySegment clang_Type_getObjCEncoding$Raw(SegmentAllocator segmentallocator, MemorySegment type) {
        if (clang_Type_getObjCEncoding == null) {
            clang_Type_getObjCEncoding = LibclangSymbolProvider.downcallHandle("clang_Type_getObjCEncoding", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getObjCEncoding"));
        }
        try {
            return (MemorySegment) clang_Type_getObjCEncoding.invoke(segmentallocator, type);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_Type_getObjCEncoding(SegmentAllocator segmentallocator, StructI<? extends CXType> type) {
        return new CXString(clang_Type_getObjCEncoding$Raw(segmentallocator, type.operator().value()));
    }

    private static MethodHandle clang_getTypeKindSpelling;

    private static MemorySegment clang_getTypeKindSpelling$Raw(SegmentAllocator segmentallocator, int K) {
        if (clang_getTypeKindSpelling == null) {
            clang_getTypeKindSpelling = LibclangSymbolProvider.downcallHandle("clang_getTypeKindSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getTypeKindSpelling"));
        }
        try {
            return (MemorySegment) clang_getTypeKindSpelling.invoke(segmentallocator, K);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getTypeKindSpelling(SegmentAllocator segmentallocator, I32I<? extends CXTypeKind> K) {
        return new CXString(clang_getTypeKindSpelling$Raw(segmentallocator, K.operator().value()));
    }

    private static MethodHandle clang_getFunctionTypeCallingConv;

    private static int clang_getFunctionTypeCallingConv$Raw(MemorySegment T) {
        if (clang_getFunctionTypeCallingConv == null) {
            clang_getFunctionTypeCallingConv = LibclangSymbolProvider.downcallHandle("clang_getFunctionTypeCallingConv", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getFunctionTypeCallingConv"));
        }
        try {
            return (int) clang_getFunctionTypeCallingConv.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCallingConv clang_getFunctionTypeCallingConv(StructI<? extends CXType> T) {
        return new CXCallingConv(clang_getFunctionTypeCallingConv$Raw(T.operator().value()));
    }

    private static MethodHandle clang_getResultType;

    private static MemorySegment clang_getResultType$Raw(SegmentAllocator segmentallocator, MemorySegment T) {
        if (clang_getResultType == null) {
            clang_getResultType = LibclangSymbolProvider.downcallHandle("clang_getResultType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getResultType"));
        }
        try {
            return (MemorySegment) clang_getResultType.invoke(segmentallocator, T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_getResultType(SegmentAllocator segmentallocator, StructI<? extends CXType> T) {
        return new CXType(clang_getResultType$Raw(segmentallocator, T.operator().value()));
    }

    private static MethodHandle clang_getExceptionSpecificationType;

    private static int clang_getExceptionSpecificationType$Raw(MemorySegment T) {
        if (clang_getExceptionSpecificationType == null) {
            clang_getExceptionSpecificationType = LibclangSymbolProvider.downcallHandle("clang_getExceptionSpecificationType", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getExceptionSpecificationType"));
        }
        try {
            return (int) clang_getExceptionSpecificationType.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getExceptionSpecificationType(StructI<? extends CXType> T) {
        return new I32(clang_getExceptionSpecificationType$Raw(T.operator().value()));
    }

    private static MethodHandle clang_getNumArgTypes;

    private static int clang_getNumArgTypes$Raw(MemorySegment T) {
        if (clang_getNumArgTypes == null) {
            clang_getNumArgTypes = LibclangSymbolProvider.downcallHandle("clang_getNumArgTypes", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getNumArgTypes"));
        }
        try {
            return (int) clang_getNumArgTypes.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getNumArgTypes(StructI<? extends CXType> T) {
        return new I32(clang_getNumArgTypes$Raw(T.operator().value()));
    }

    private static MethodHandle clang_getArgType;

    private static MemorySegment clang_getArgType$Raw(SegmentAllocator segmentallocator, MemorySegment T, int i) {
        if (clang_getArgType == null) {
            clang_getArgType = LibclangSymbolProvider.downcallHandle("clang_getArgType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getArgType"));
        }
        try {
            return (MemorySegment) clang_getArgType.invoke(segmentallocator, T, i);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_getArgType(SegmentAllocator segmentallocator, StructI<? extends CXType> T, I32I<?> i) {
        return new CXType(clang_getArgType$Raw(segmentallocator, T.operator().value(), i.operator().value()));
    }

    private static MethodHandle clang_Type_getObjCObjectBaseType;

    private static MemorySegment clang_Type_getObjCObjectBaseType$Raw(SegmentAllocator segmentallocator, MemorySegment T) {
        if (clang_Type_getObjCObjectBaseType == null) {
            clang_Type_getObjCObjectBaseType = LibclangSymbolProvider.downcallHandle("clang_Type_getObjCObjectBaseType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getObjCObjectBaseType"));
        }
        try {
            return (MemorySegment) clang_Type_getObjCObjectBaseType.invoke(segmentallocator, T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_Type_getObjCObjectBaseType(SegmentAllocator segmentallocator, StructI<? extends CXType> T) {
        return new CXType(clang_Type_getObjCObjectBaseType$Raw(segmentallocator, T.operator().value()));
    }

    private static MethodHandle clang_Type_getNumObjCProtocolRefs;

    private static int clang_Type_getNumObjCProtocolRefs$Raw(MemorySegment T) {
        if (clang_Type_getNumObjCProtocolRefs == null) {
            clang_Type_getNumObjCProtocolRefs = LibclangSymbolProvider.downcallHandle("clang_Type_getNumObjCProtocolRefs", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getNumObjCProtocolRefs"));
        }
        try {
            return (int) clang_Type_getNumObjCProtocolRefs.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Type_getNumObjCProtocolRefs(StructI<? extends CXType> T) {
        return new I32(clang_Type_getNumObjCProtocolRefs$Raw(T.operator().value()));
    }

    private static MethodHandle clang_Type_getObjCProtocolDecl;

    private static MemorySegment clang_Type_getObjCProtocolDecl$Raw(SegmentAllocator segmentallocator, MemorySegment T, int i) {
        if (clang_Type_getObjCProtocolDecl == null) {
            clang_Type_getObjCProtocolDecl = LibclangSymbolProvider.downcallHandle("clang_Type_getObjCProtocolDecl", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getObjCProtocolDecl"));
        }
        try {
            return (MemorySegment) clang_Type_getObjCProtocolDecl.invoke(segmentallocator, T, i);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursor clang_Type_getObjCProtocolDecl(SegmentAllocator segmentallocator, StructI<? extends CXType> T, I32I<?> i) {
        return new CXCursor(clang_Type_getObjCProtocolDecl$Raw(segmentallocator, T.operator().value(), i.operator().value()));
    }

    private static MethodHandle clang_Type_getNumObjCTypeArgs;

    private static int clang_Type_getNumObjCTypeArgs$Raw(MemorySegment T) {
        if (clang_Type_getNumObjCTypeArgs == null) {
            clang_Type_getNumObjCTypeArgs = LibclangSymbolProvider.downcallHandle("clang_Type_getNumObjCTypeArgs", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getNumObjCTypeArgs"));
        }
        try {
            return (int) clang_Type_getNumObjCTypeArgs.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Type_getNumObjCTypeArgs(StructI<? extends CXType> T) {
        return new I32(clang_Type_getNumObjCTypeArgs$Raw(T.operator().value()));
    }

    private static MethodHandle clang_Type_getObjCTypeArg;

    private static MemorySegment clang_Type_getObjCTypeArg$Raw(SegmentAllocator segmentallocator, MemorySegment T, int i) {
        if (clang_Type_getObjCTypeArg == null) {
            clang_Type_getObjCTypeArg = LibclangSymbolProvider.downcallHandle("clang_Type_getObjCTypeArg", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getObjCTypeArg"));
        }
        try {
            return (MemorySegment) clang_Type_getObjCTypeArg.invoke(segmentallocator, T, i);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_Type_getObjCTypeArg(SegmentAllocator segmentallocator, StructI<? extends CXType> T, I32I<?> i) {
        return new CXType(clang_Type_getObjCTypeArg$Raw(segmentallocator, T.operator().value(), i.operator().value()));
    }

    private static MethodHandle clang_isFunctionTypeVariadic;

    private static int clang_isFunctionTypeVariadic$Raw(MemorySegment T) {
        if (clang_isFunctionTypeVariadic == null) {
            clang_isFunctionTypeVariadic = LibclangSymbolProvider.downcallHandle("clang_isFunctionTypeVariadic", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_isFunctionTypeVariadic"));
        }
        try {
            return (int) clang_isFunctionTypeVariadic.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isFunctionTypeVariadic(StructI<? extends CXType> T) {
        return new I32(clang_isFunctionTypeVariadic$Raw(T.operator().value()));
    }

    private static MethodHandle clang_getCursorResultType;

    private static MemorySegment clang_getCursorResultType$Raw(SegmentAllocator segmentallocator, MemorySegment C) {
        if (clang_getCursorResultType == null) {
            clang_getCursorResultType = LibclangSymbolProvider.downcallHandle("clang_getCursorResultType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorResultType"));
        }
        try {
            return (MemorySegment) clang_getCursorResultType.invoke(segmentallocator, C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_getCursorResultType(SegmentAllocator segmentallocator, StructI<? extends CXCursor> C) {
        return new CXType(clang_getCursorResultType$Raw(segmentallocator, C.operator().value()));
    }

    private static MethodHandle clang_getCursorExceptionSpecificationType;

    private static int clang_getCursorExceptionSpecificationType$Raw(MemorySegment C) {
        if (clang_getCursorExceptionSpecificationType == null) {
            clang_getCursorExceptionSpecificationType = LibclangSymbolProvider.downcallHandle("clang_getCursorExceptionSpecificationType", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorExceptionSpecificationType"));
        }
        try {
            return (int) clang_getCursorExceptionSpecificationType.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getCursorExceptionSpecificationType(StructI<? extends CXCursor> C) {
        return new I32(clang_getCursorExceptionSpecificationType$Raw(C.operator().value()));
    }

    private static MethodHandle clang_isPODType;

    private static int clang_isPODType$Raw(MemorySegment T) {
        if (clang_isPODType == null) {
            clang_isPODType = LibclangSymbolProvider.downcallHandle("clang_isPODType", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_isPODType"));
        }
        try {
            return (int) clang_isPODType.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isPODType(StructI<? extends CXType> T) {
        return new I32(clang_isPODType$Raw(T.operator().value()));
    }

    private static MethodHandle clang_getElementType;

    private static MemorySegment clang_getElementType$Raw(SegmentAllocator segmentallocator, MemorySegment T) {
        if (clang_getElementType == null) {
            clang_getElementType = LibclangSymbolProvider.downcallHandle("clang_getElementType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getElementType"));
        }
        try {
            return (MemorySegment) clang_getElementType.invoke(segmentallocator, T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_getElementType(SegmentAllocator segmentallocator, StructI<? extends CXType> T) {
        return new CXType(clang_getElementType$Raw(segmentallocator, T.operator().value()));
    }

    private static MethodHandle clang_getNumElements;

    private static long clang_getNumElements$Raw(MemorySegment T) {
        if (clang_getNumElements == null) {
            clang_getNumElements = LibclangSymbolProvider.downcallHandle("clang_getNumElements", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getNumElements"));
        }
        try {
            return (long) clang_getNumElements.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I64 clang_getNumElements(StructI<? extends CXType> T) {
        return new I64(clang_getNumElements$Raw(T.operator().value()));
    }

    private static MethodHandle clang_getArrayElementType;

    private static MemorySegment clang_getArrayElementType$Raw(SegmentAllocator segmentallocator, MemorySegment T) {
        if (clang_getArrayElementType == null) {
            clang_getArrayElementType = LibclangSymbolProvider.downcallHandle("clang_getArrayElementType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getArrayElementType"));
        }
        try {
            return (MemorySegment) clang_getArrayElementType.invoke(segmentallocator, T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_getArrayElementType(SegmentAllocator segmentallocator, StructI<? extends CXType> T) {
        return new CXType(clang_getArrayElementType$Raw(segmentallocator, T.operator().value()));
    }

    private static MethodHandle clang_getArraySize;

    private static long clang_getArraySize$Raw(MemorySegment T) {
        if (clang_getArraySize == null) {
            clang_getArraySize = LibclangSymbolProvider.downcallHandle("clang_getArraySize", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getArraySize"));
        }
        try {
            return (long) clang_getArraySize.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I64 clang_getArraySize(StructI<? extends CXType> T) {
        return new I64(clang_getArraySize$Raw(T.operator().value()));
    }

    private static MethodHandle clang_Type_getNamedType;

    private static MemorySegment clang_Type_getNamedType$Raw(SegmentAllocator segmentallocator, MemorySegment T) {
        if (clang_Type_getNamedType == null) {
            clang_Type_getNamedType = LibclangSymbolProvider.downcallHandle("clang_Type_getNamedType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getNamedType"));
        }
        try {
            return (MemorySegment) clang_Type_getNamedType.invoke(segmentallocator, T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_Type_getNamedType(SegmentAllocator segmentallocator, StructI<? extends CXType> T) {
        return new CXType(clang_Type_getNamedType$Raw(segmentallocator, T.operator().value()));
    }

    private static MethodHandle clang_Type_isTransparentTagTypedef;

    private static int clang_Type_isTransparentTagTypedef$Raw(MemorySegment T) {
        if (clang_Type_isTransparentTagTypedef == null) {
            clang_Type_isTransparentTagTypedef = LibclangSymbolProvider.downcallHandle("clang_Type_isTransparentTagTypedef", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_isTransparentTagTypedef"));
        }
        try {
            return (int) clang_Type_isTransparentTagTypedef.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Type_isTransparentTagTypedef(StructI<? extends CXType> T) {
        return new I32(clang_Type_isTransparentTagTypedef$Raw(T.operator().value()));
    }

    private static MethodHandle clang_Type_getNullability;

    private static int clang_Type_getNullability$Raw(MemorySegment T) {
        if (clang_Type_getNullability == null) {
            clang_Type_getNullability = LibclangSymbolProvider.downcallHandle("clang_Type_getNullability", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getNullability"));
        }
        try {
            return (int) clang_Type_getNullability.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXTypeNullabilityKind clang_Type_getNullability(StructI<? extends CXType> T) {
        return new CXTypeNullabilityKind(clang_Type_getNullability$Raw(T.operator().value()));
    }

    private static MethodHandle clang_Type_getAlignOf;

    private static long clang_Type_getAlignOf$Raw(MemorySegment T) {
        if (clang_Type_getAlignOf == null) {
            clang_Type_getAlignOf = LibclangSymbolProvider.downcallHandle("clang_Type_getAlignOf", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getAlignOf"));
        }
        try {
            return (long) clang_Type_getAlignOf.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I64 clang_Type_getAlignOf(StructI<? extends CXType> T) {
        return new I64(clang_Type_getAlignOf$Raw(T.operator().value()));
    }

    private static MethodHandle clang_Type_getClassType;

    private static MemorySegment clang_Type_getClassType$Raw(SegmentAllocator segmentallocator, MemorySegment T) {
        if (clang_Type_getClassType == null) {
            clang_Type_getClassType = LibclangSymbolProvider.downcallHandle("clang_Type_getClassType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getClassType"));
        }
        try {
            return (MemorySegment) clang_Type_getClassType.invoke(segmentallocator, T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_Type_getClassType(SegmentAllocator segmentallocator, StructI<? extends CXType> T) {
        return new CXType(clang_Type_getClassType$Raw(segmentallocator, T.operator().value()));
    }

    private static MethodHandle clang_Type_getSizeOf;

    private static long clang_Type_getSizeOf$Raw(MemorySegment T) {
        if (clang_Type_getSizeOf == null) {
            clang_Type_getSizeOf = LibclangSymbolProvider.downcallHandle("clang_Type_getSizeOf", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getSizeOf"));
        }
        try {
            return (long) clang_Type_getSizeOf.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I64 clang_Type_getSizeOf(StructI<? extends CXType> T) {
        return new I64(clang_Type_getSizeOf$Raw(T.operator().value()));
    }

    private static MethodHandle clang_Type_getOffsetOf;

    private static long clang_Type_getOffsetOf$Raw(MemorySegment T, MemorySegment S) {
        if (clang_Type_getOffsetOf == null) {
            clang_Type_getOffsetOf = LibclangSymbolProvider.downcallHandle("clang_Type_getOffsetOf", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getOffsetOf"));
        }
        try {
            return (long) clang_Type_getOffsetOf.invoke(T, S);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I64 clang_Type_getOffsetOf(StructI<? extends CXType> T, PtrI<? extends I8I<?>> S) {
        return new I64(clang_Type_getOffsetOf$Raw(T.operator().value(), S.operator().value()));
    }

    private static MethodHandle clang_Type_getModifiedType;

    private static MemorySegment clang_Type_getModifiedType$Raw(SegmentAllocator segmentallocator, MemorySegment T) {
        if (clang_Type_getModifiedType == null) {
            clang_Type_getModifiedType = LibclangSymbolProvider.downcallHandle("clang_Type_getModifiedType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getModifiedType"));
        }
        try {
            return (MemorySegment) clang_Type_getModifiedType.invoke(segmentallocator, T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_Type_getModifiedType(SegmentAllocator segmentallocator, StructI<? extends CXType> T) {
        return new CXType(clang_Type_getModifiedType$Raw(segmentallocator, T.operator().value()));
    }

    private static MethodHandle clang_Type_getValueType;

    private static MemorySegment clang_Type_getValueType$Raw(SegmentAllocator segmentallocator, MemorySegment CT) {
        if (clang_Type_getValueType == null) {
            clang_Type_getValueType = LibclangSymbolProvider.downcallHandle("clang_Type_getValueType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getValueType"));
        }
        try {
            return (MemorySegment) clang_Type_getValueType.invoke(segmentallocator, CT);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_Type_getValueType(SegmentAllocator segmentallocator, StructI<? extends CXType> CT) {
        return new CXType(clang_Type_getValueType$Raw(segmentallocator, CT.operator().value()));
    }

    private static MethodHandle clang_Cursor_getOffsetOfField;

    private static long clang_Cursor_getOffsetOfField$Raw(MemorySegment C) {
        if (clang_Cursor_getOffsetOfField == null) {
            clang_Cursor_getOffsetOfField = LibclangSymbolProvider.downcallHandle("clang_Cursor_getOffsetOfField", FunctionDescriptor.of(ValueLayout.JAVA_LONG, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getOffsetOfField"));
        }
        try {
            return (long) clang_Cursor_getOffsetOfField.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I64 clang_Cursor_getOffsetOfField(StructI<? extends CXCursor> C) {
        return new I64(clang_Cursor_getOffsetOfField$Raw(C.operator().value()));
    }

    private static MethodHandle clang_Cursor_isAnonymous;

    private static int clang_Cursor_isAnonymous$Raw(MemorySegment C) {
        if (clang_Cursor_isAnonymous == null) {
            clang_Cursor_isAnonymous = LibclangSymbolProvider.downcallHandle("clang_Cursor_isAnonymous", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_isAnonymous"));
        }
        try {
            return (int) clang_Cursor_isAnonymous.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_isAnonymous(StructI<? extends CXCursor> C) {
        return new I32(clang_Cursor_isAnonymous$Raw(C.operator().value()));
    }

    private static MethodHandle clang_Cursor_isAnonymousRecordDecl;

    private static int clang_Cursor_isAnonymousRecordDecl$Raw(MemorySegment C) {
        if (clang_Cursor_isAnonymousRecordDecl == null) {
            clang_Cursor_isAnonymousRecordDecl = LibclangSymbolProvider.downcallHandle("clang_Cursor_isAnonymousRecordDecl", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_isAnonymousRecordDecl"));
        }
        try {
            return (int) clang_Cursor_isAnonymousRecordDecl.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_isAnonymousRecordDecl(StructI<? extends CXCursor> C) {
        return new I32(clang_Cursor_isAnonymousRecordDecl$Raw(C.operator().value()));
    }

    private static MethodHandle clang_Cursor_isInlineNamespace;

    private static int clang_Cursor_isInlineNamespace$Raw(MemorySegment C) {
        if (clang_Cursor_isInlineNamespace == null) {
            clang_Cursor_isInlineNamespace = LibclangSymbolProvider.downcallHandle("clang_Cursor_isInlineNamespace", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_isInlineNamespace"));
        }
        try {
            return (int) clang_Cursor_isInlineNamespace.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_isInlineNamespace(StructI<? extends CXCursor> C) {
        return new I32(clang_Cursor_isInlineNamespace$Raw(C.operator().value()));
    }

    private static MethodHandle clang_Type_getNumTemplateArguments;

    private static int clang_Type_getNumTemplateArguments$Raw(MemorySegment T) {
        if (clang_Type_getNumTemplateArguments == null) {
            clang_Type_getNumTemplateArguments = LibclangSymbolProvider.downcallHandle("clang_Type_getNumTemplateArguments", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getNumTemplateArguments"));
        }
        try {
            return (int) clang_Type_getNumTemplateArguments.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Type_getNumTemplateArguments(StructI<? extends CXType> T) {
        return new I32(clang_Type_getNumTemplateArguments$Raw(T.operator().value()));
    }

    private static MethodHandle clang_Type_getTemplateArgumentAsType;

    private static MemorySegment clang_Type_getTemplateArgumentAsType$Raw(SegmentAllocator segmentallocator, MemorySegment T, int i) {
        if (clang_Type_getTemplateArgumentAsType == null) {
            clang_Type_getTemplateArgumentAsType = LibclangSymbolProvider.downcallHandle("clang_Type_getTemplateArgumentAsType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getTemplateArgumentAsType"));
        }
        try {
            return (MemorySegment) clang_Type_getTemplateArgumentAsType.invoke(segmentallocator, T, i);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_Type_getTemplateArgumentAsType(SegmentAllocator segmentallocator, StructI<? extends CXType> T, I32I<?> i) {
        return new CXType(clang_Type_getTemplateArgumentAsType$Raw(segmentallocator, T.operator().value(), i.operator().value()));
    }

    private static MethodHandle clang_Type_getCXXRefQualifier;

    private static int clang_Type_getCXXRefQualifier$Raw(MemorySegment T) {
        if (clang_Type_getCXXRefQualifier == null) {
            clang_Type_getCXXRefQualifier = LibclangSymbolProvider.downcallHandle("clang_Type_getCXXRefQualifier", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_getCXXRefQualifier"));
        }
        try {
            return (int) clang_Type_getCXXRefQualifier.invoke(T);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXRefQualifierKind clang_Type_getCXXRefQualifier(StructI<? extends CXType> T) {
        return new CXRefQualifierKind(clang_Type_getCXXRefQualifier$Raw(T.operator().value()));
    }

    private static MethodHandle clang_isVirtualBase;

    private static int clang_isVirtualBase$Raw(MemorySegment arg0) {
        if (clang_isVirtualBase == null) {
            clang_isVirtualBase = LibclangSymbolProvider.downcallHandle("clang_isVirtualBase", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_isVirtualBase"));
        }
        try {
            return (int) clang_isVirtualBase.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isVirtualBase(StructI<? extends CXCursor> arg0) {
        return new I32(clang_isVirtualBase$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_getCXXAccessSpecifier;

    private static int clang_getCXXAccessSpecifier$Raw(MemorySegment arg0) {
        if (clang_getCXXAccessSpecifier == null) {
            clang_getCXXAccessSpecifier = LibclangSymbolProvider.downcallHandle("clang_getCXXAccessSpecifier", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCXXAccessSpecifier"));
        }
        try {
            return (int) clang_getCXXAccessSpecifier.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CX_CXXAccessSpecifier clang_getCXXAccessSpecifier(StructI<? extends CXCursor> arg0) {
        return new CX_CXXAccessSpecifier(clang_getCXXAccessSpecifier$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_Cursor_getStorageClass;

    private static int clang_Cursor_getStorageClass$Raw(MemorySegment arg0) {
        if (clang_Cursor_getStorageClass == null) {
            clang_Cursor_getStorageClass = LibclangSymbolProvider.downcallHandle("clang_Cursor_getStorageClass", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getStorageClass"));
        }
        try {
            return (int) clang_Cursor_getStorageClass.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CX_StorageClass clang_Cursor_getStorageClass(StructI<? extends CXCursor> arg0) {
        return new CX_StorageClass(clang_Cursor_getStorageClass$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_getNumOverloadedDecls;

    private static int clang_getNumOverloadedDecls$Raw(MemorySegment cursor) {
        if (clang_getNumOverloadedDecls == null) {
            clang_getNumOverloadedDecls = LibclangSymbolProvider.downcallHandle("clang_getNumOverloadedDecls", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getNumOverloadedDecls"));
        }
        try {
            return (int) clang_getNumOverloadedDecls.invoke(cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getNumOverloadedDecls(StructI<? extends CXCursor> cursor) {
        return new I32(clang_getNumOverloadedDecls$Raw(cursor.operator().value()));
    }

    private static MethodHandle clang_getOverloadedDecl;

    private static MemorySegment clang_getOverloadedDecl$Raw(SegmentAllocator segmentallocator, MemorySegment cursor, int index) {
        if (clang_getOverloadedDecl == null) {
            clang_getOverloadedDecl = LibclangSymbolProvider.downcallHandle("clang_getOverloadedDecl", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getOverloadedDecl"));
        }
        try {
            return (MemorySegment) clang_getOverloadedDecl.invoke(segmentallocator, cursor, index);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursor clang_getOverloadedDecl(SegmentAllocator segmentallocator, StructI<? extends CXCursor> cursor, I32I<?> index) {
        return new CXCursor(clang_getOverloadedDecl$Raw(segmentallocator, cursor.operator().value(), index.operator().value()));
    }

    private static MethodHandle clang_getIBOutletCollectionType;

    private static MemorySegment clang_getIBOutletCollectionType$Raw(SegmentAllocator segmentallocator, MemorySegment arg0) {
        if (clang_getIBOutletCollectionType == null) {
            clang_getIBOutletCollectionType = LibclangSymbolProvider.downcallHandle("clang_getIBOutletCollectionType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getIBOutletCollectionType"));
        }
        try {
            return (MemorySegment) clang_getIBOutletCollectionType.invoke(segmentallocator, arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_getIBOutletCollectionType(SegmentAllocator segmentallocator, StructI<? extends CXCursor> arg0) {
        return new CXType(clang_getIBOutletCollectionType$Raw(segmentallocator, arg0.operator().value()));
    }

    private static MethodHandle clang_visitChildren;

    private static int clang_visitChildren$Raw(MemorySegment parent, MemorySegment visitor, MemorySegment client_data) {
        if (clang_visitChildren == null) {
            clang_visitChildren = LibclangSymbolProvider.downcallHandle("clang_visitChildren", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_visitChildren"));
        }
        try {
            return (int) clang_visitChildren.invoke(parent, visitor, client_data);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_visitChildren(StructI<? extends CXCursor> parent, PtrI<? extends CXCursorVisitor.Function> visitor, CXClientData client_data) {
        return new I32(clang_visitChildren$Raw(parent.operator().value(), visitor.operator().value(), client_data.operator().value()));
    }

    private static MethodHandle clang_visitChildrenWithBlock;

    private static int clang_visitChildrenWithBlock$Raw(MemorySegment parent, MemorySegment block) {
        if (clang_visitChildrenWithBlock == null) {
            clang_visitChildrenWithBlock = LibclangSymbolProvider.downcallHandle("clang_visitChildrenWithBlock", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_visitChildrenWithBlock"));
        }
        try {
            return (int) clang_visitChildrenWithBlock.invoke(parent, block);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_visitChildrenWithBlock(StructI<? extends CXCursor> parent, CXCursorVisitorBlock block) {
        return new I32(clang_visitChildrenWithBlock$Raw(parent.operator().value(), block.operator().value()));
    }

    private static MethodHandle clang_getCursorUSR;

    private static MemorySegment clang_getCursorUSR$Raw(SegmentAllocator segmentallocator, MemorySegment arg0) {
        if (clang_getCursorUSR == null) {
            clang_getCursorUSR = LibclangSymbolProvider.downcallHandle("clang_getCursorUSR", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorUSR"));
        }
        try {
            return (MemorySegment) clang_getCursorUSR.invoke(segmentallocator, arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getCursorUSR(SegmentAllocator segmentallocator, StructI<? extends CXCursor> arg0) {
        return new CXString(clang_getCursorUSR$Raw(segmentallocator, arg0.operator().value()));
    }

    private static MethodHandle clang_constructUSR_ObjCClass;

    private static MemorySegment clang_constructUSR_ObjCClass$Raw(SegmentAllocator segmentallocator, MemorySegment class_name) {
        if (clang_constructUSR_ObjCClass == null) {
            clang_constructUSR_ObjCClass = LibclangSymbolProvider.downcallHandle("clang_constructUSR_ObjCClass", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_constructUSR_ObjCClass"));
        }
        try {
            return (MemorySegment) clang_constructUSR_ObjCClass.invoke(segmentallocator, class_name);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_constructUSR_ObjCClass(SegmentAllocator segmentallocator, PtrI<? extends I8I<?>> class_name) {
        return new CXString(clang_constructUSR_ObjCClass$Raw(segmentallocator, class_name.operator().value()));
    }

    private static MethodHandle clang_constructUSR_ObjCCategory;

    private static MemorySegment clang_constructUSR_ObjCCategory$Raw(SegmentAllocator segmentallocator, MemorySegment class_name, MemorySegment category_name) {
        if (clang_constructUSR_ObjCCategory == null) {
            clang_constructUSR_ObjCCategory = LibclangSymbolProvider.downcallHandle("clang_constructUSR_ObjCCategory", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_constructUSR_ObjCCategory"));
        }
        try {
            return (MemorySegment) clang_constructUSR_ObjCCategory.invoke(segmentallocator, class_name, category_name);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_constructUSR_ObjCCategory(SegmentAllocator segmentallocator, PtrI<? extends I8I<?>> class_name, PtrI<? extends I8I<?>> category_name) {
        return new CXString(clang_constructUSR_ObjCCategory$Raw(segmentallocator, class_name.operator().value(), category_name.operator().value()));
    }

    private static MethodHandle clang_constructUSR_ObjCProtocol;

    private static MemorySegment clang_constructUSR_ObjCProtocol$Raw(SegmentAllocator segmentallocator, MemorySegment protocol_name) {
        if (clang_constructUSR_ObjCProtocol == null) {
            clang_constructUSR_ObjCProtocol = LibclangSymbolProvider.downcallHandle("clang_constructUSR_ObjCProtocol", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_constructUSR_ObjCProtocol"));
        }
        try {
            return (MemorySegment) clang_constructUSR_ObjCProtocol.invoke(segmentallocator, protocol_name);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_constructUSR_ObjCProtocol(SegmentAllocator segmentallocator, PtrI<? extends I8I<?>> protocol_name) {
        return new CXString(clang_constructUSR_ObjCProtocol$Raw(segmentallocator, protocol_name.operator().value()));
    }

    private static MethodHandle clang_constructUSR_ObjCIvar;

    private static MemorySegment clang_constructUSR_ObjCIvar$Raw(SegmentAllocator segmentallocator, MemorySegment name, MemorySegment classUSR) {
        if (clang_constructUSR_ObjCIvar == null) {
            clang_constructUSR_ObjCIvar = LibclangSymbolProvider.downcallHandle("clang_constructUSR_ObjCIvar", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_constructUSR_ObjCIvar"));
        }
        try {
            return (MemorySegment) clang_constructUSR_ObjCIvar.invoke(segmentallocator, name, classUSR);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_constructUSR_ObjCIvar(SegmentAllocator segmentallocator, PtrI<? extends I8I<?>> name, StructI<? extends CXString> classUSR) {
        return new CXString(clang_constructUSR_ObjCIvar$Raw(segmentallocator, name.operator().value(), classUSR.operator().value()));
    }

    private static MethodHandle clang_constructUSR_ObjCMethod;

    private static MemorySegment clang_constructUSR_ObjCMethod$Raw(SegmentAllocator segmentallocator, MemorySegment name, int isInstanceMethod, MemorySegment classUSR) {
        if (clang_constructUSR_ObjCMethod == null) {
            clang_constructUSR_ObjCMethod = LibclangSymbolProvider.downcallHandle("clang_constructUSR_ObjCMethod", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_constructUSR_ObjCMethod"));
        }
        try {
            return (MemorySegment) clang_constructUSR_ObjCMethod.invoke(segmentallocator, name, isInstanceMethod, classUSR);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_constructUSR_ObjCMethod(SegmentAllocator segmentallocator, PtrI<? extends I8I<?>> name, I32I<?> isInstanceMethod, StructI<? extends CXString> classUSR) {
        return new CXString(clang_constructUSR_ObjCMethod$Raw(segmentallocator, name.operator().value(), isInstanceMethod.operator().value(), classUSR.operator().value()));
    }

    private static MethodHandle clang_constructUSR_ObjCProperty;

    private static MemorySegment clang_constructUSR_ObjCProperty$Raw(SegmentAllocator segmentallocator, MemorySegment property, MemorySegment classUSR) {
        if (clang_constructUSR_ObjCProperty == null) {
            clang_constructUSR_ObjCProperty = LibclangSymbolProvider.downcallHandle("clang_constructUSR_ObjCProperty", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_constructUSR_ObjCProperty"));
        }
        try {
            return (MemorySegment) clang_constructUSR_ObjCProperty.invoke(segmentallocator, property, classUSR);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_constructUSR_ObjCProperty(SegmentAllocator segmentallocator, PtrI<? extends I8I<?>> property, StructI<? extends CXString> classUSR) {
        return new CXString(clang_constructUSR_ObjCProperty$Raw(segmentallocator, property.operator().value(), classUSR.operator().value()));
    }

    private static MethodHandle clang_getCursorSpelling;

    private static MemorySegment clang_getCursorSpelling$Raw(SegmentAllocator segmentallocator, MemorySegment arg0) {
        if (clang_getCursorSpelling == null) {
            clang_getCursorSpelling = LibclangSymbolProvider.downcallHandle("clang_getCursorSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorSpelling"));
        }
        try {
            return (MemorySegment) clang_getCursorSpelling.invoke(segmentallocator, arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getCursorSpelling(SegmentAllocator segmentallocator, StructI<? extends CXCursor> arg0) {
        return new CXString(clang_getCursorSpelling$Raw(segmentallocator, arg0.operator().value()));
    }

    private static MethodHandle clang_Cursor_getSpellingNameRange;

    private static MemorySegment clang_Cursor_getSpellingNameRange$Raw(SegmentAllocator segmentallocator, MemorySegment arg0, int pieceIndex, int options) {
        if (clang_Cursor_getSpellingNameRange == null) {
            clang_Cursor_getSpellingNameRange = LibclangSymbolProvider.downcallHandle("clang_Cursor_getSpellingNameRange", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getSpellingNameRange"));
        }
        try {
            return (MemorySegment) clang_Cursor_getSpellingNameRange.invoke(segmentallocator, arg0, pieceIndex, options);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceRange clang_Cursor_getSpellingNameRange(SegmentAllocator segmentallocator, StructI<? extends CXCursor> arg0, I32I<?> pieceIndex, I32I<?> options) {
        return new CXSourceRange(clang_Cursor_getSpellingNameRange$Raw(segmentallocator, arg0.operator().value(), pieceIndex.operator().value(), options.operator().value()));
    }

    private static MethodHandle clang_PrintingPolicy_getProperty;

    private static int clang_PrintingPolicy_getProperty$Raw(MemorySegment Policy, int Property) {
        if (clang_PrintingPolicy_getProperty == null) {
            clang_PrintingPolicy_getProperty = LibclangSymbolProvider.downcallHandle("clang_PrintingPolicy_getProperty", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_PrintingPolicy_getProperty"));
        }
        try {
            return (int) clang_PrintingPolicy_getProperty.invoke(Policy, Property);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_PrintingPolicy_getProperty(CXPrintingPolicy Policy, I32I<? extends CXPrintingPolicyProperty> Property) {
        return new I32(clang_PrintingPolicy_getProperty$Raw(Policy.operator().value(), Property.operator().value()));
    }

    private static MethodHandle clang_PrintingPolicy_setProperty;

    private static void clang_PrintingPolicy_setProperty$Raw(MemorySegment Policy, int Property, int Value) {
        if (clang_PrintingPolicy_setProperty == null) {
            clang_PrintingPolicy_setProperty = LibclangSymbolProvider.downcallHandle("clang_PrintingPolicy_setProperty", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_PrintingPolicy_setProperty"));
        }
        try {
            clang_PrintingPolicy_setProperty.invoke(Policy, Property, Value);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_PrintingPolicy_setProperty(CXPrintingPolicy Policy, I32I<? extends CXPrintingPolicyProperty> Property, I32I<?> Value) {
        clang_PrintingPolicy_setProperty$Raw(Policy.operator().value(), Property.operator().value(), Value.operator().value());
    }

    private static MethodHandle clang_getCursorPrintingPolicy;

    private static MemorySegment clang_getCursorPrintingPolicy$Raw(MemorySegment arg0) {
        if (clang_getCursorPrintingPolicy == null) {
            clang_getCursorPrintingPolicy = LibclangSymbolProvider.downcallHandle("clang_getCursorPrintingPolicy", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorPrintingPolicy"));
        }
        try {
            return (MemorySegment) clang_getCursorPrintingPolicy.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXPrintingPolicy clang_getCursorPrintingPolicy(StructI<? extends CXCursor> arg0) {
        return new CXPrintingPolicy(clang_getCursorPrintingPolicy$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_PrintingPolicy_dispose;

    private static void clang_PrintingPolicy_dispose$Raw(MemorySegment Policy) {
        if (clang_PrintingPolicy_dispose == null) {
            clang_PrintingPolicy_dispose = LibclangSymbolProvider.downcallHandle("clang_PrintingPolicy_dispose", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_PrintingPolicy_dispose"));
        }
        try {
            clang_PrintingPolicy_dispose.invoke(Policy);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_PrintingPolicy_dispose(CXPrintingPolicy Policy) {
        clang_PrintingPolicy_dispose$Raw(Policy.operator().value());
    }

    private static MethodHandle clang_getCursorPrettyPrinted;

    private static MemorySegment clang_getCursorPrettyPrinted$Raw(SegmentAllocator segmentallocator, MemorySegment Cursor, MemorySegment Policy) {
        if (clang_getCursorPrettyPrinted == null) {
            clang_getCursorPrettyPrinted = LibclangSymbolProvider.downcallHandle("clang_getCursorPrettyPrinted", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorPrettyPrinted"));
        }
        try {
            return (MemorySegment) clang_getCursorPrettyPrinted.invoke(segmentallocator, Cursor, Policy);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getCursorPrettyPrinted(SegmentAllocator segmentallocator, StructI<? extends CXCursor> Cursor, CXPrintingPolicy Policy) {
        return new CXString(clang_getCursorPrettyPrinted$Raw(segmentallocator, Cursor.operator().value(), Policy.operator().value()));
    }

    private static MethodHandle clang_getCursorDisplayName;

    private static MemorySegment clang_getCursorDisplayName$Raw(SegmentAllocator segmentallocator, MemorySegment arg0) {
        if (clang_getCursorDisplayName == null) {
            clang_getCursorDisplayName = LibclangSymbolProvider.downcallHandle("clang_getCursorDisplayName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorDisplayName"));
        }
        try {
            return (MemorySegment) clang_getCursorDisplayName.invoke(segmentallocator, arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getCursorDisplayName(SegmentAllocator segmentallocator, StructI<? extends CXCursor> arg0) {
        return new CXString(clang_getCursorDisplayName$Raw(segmentallocator, arg0.operator().value()));
    }

    private static MethodHandle clang_getCursorReferenced;

    private static MemorySegment clang_getCursorReferenced$Raw(SegmentAllocator segmentallocator, MemorySegment arg0) {
        if (clang_getCursorReferenced == null) {
            clang_getCursorReferenced = LibclangSymbolProvider.downcallHandle("clang_getCursorReferenced", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorReferenced"));
        }
        try {
            return (MemorySegment) clang_getCursorReferenced.invoke(segmentallocator, arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursor clang_getCursorReferenced(SegmentAllocator segmentallocator, StructI<? extends CXCursor> arg0) {
        return new CXCursor(clang_getCursorReferenced$Raw(segmentallocator, arg0.operator().value()));
    }

    private static MethodHandle clang_getCursorDefinition;

    private static MemorySegment clang_getCursorDefinition$Raw(SegmentAllocator segmentallocator, MemorySegment arg0) {
        if (clang_getCursorDefinition == null) {
            clang_getCursorDefinition = LibclangSymbolProvider.downcallHandle("clang_getCursorDefinition", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorDefinition"));
        }
        try {
            return (MemorySegment) clang_getCursorDefinition.invoke(segmentallocator, arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursor clang_getCursorDefinition(SegmentAllocator segmentallocator, StructI<? extends CXCursor> arg0) {
        return new CXCursor(clang_getCursorDefinition$Raw(segmentallocator, arg0.operator().value()));
    }

    private static MethodHandle clang_isCursorDefinition;

    private static int clang_isCursorDefinition$Raw(MemorySegment arg0) {
        if (clang_isCursorDefinition == null) {
            clang_isCursorDefinition = LibclangSymbolProvider.downcallHandle("clang_isCursorDefinition", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_isCursorDefinition"));
        }
        try {
            return (int) clang_isCursorDefinition.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_isCursorDefinition(StructI<? extends CXCursor> arg0) {
        return new I32(clang_isCursorDefinition$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_getCanonicalCursor;

    private static MemorySegment clang_getCanonicalCursor$Raw(SegmentAllocator segmentallocator, MemorySegment arg0) {
        if (clang_getCanonicalCursor == null) {
            clang_getCanonicalCursor = LibclangSymbolProvider.downcallHandle("clang_getCanonicalCursor", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCanonicalCursor"));
        }
        try {
            return (MemorySegment) clang_getCanonicalCursor.invoke(segmentallocator, arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursor clang_getCanonicalCursor(SegmentAllocator segmentallocator, StructI<? extends CXCursor> arg0) {
        return new CXCursor(clang_getCanonicalCursor$Raw(segmentallocator, arg0.operator().value()));
    }

    private static MethodHandle clang_Cursor_getObjCSelectorIndex;

    private static int clang_Cursor_getObjCSelectorIndex$Raw(MemorySegment arg0) {
        if (clang_Cursor_getObjCSelectorIndex == null) {
            clang_Cursor_getObjCSelectorIndex = LibclangSymbolProvider.downcallHandle("clang_Cursor_getObjCSelectorIndex", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getObjCSelectorIndex"));
        }
        try {
            return (int) clang_Cursor_getObjCSelectorIndex.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_getObjCSelectorIndex(StructI<? extends CXCursor> arg0) {
        return new I32(clang_Cursor_getObjCSelectorIndex$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_Cursor_isDynamicCall;

    private static int clang_Cursor_isDynamicCall$Raw(MemorySegment C) {
        if (clang_Cursor_isDynamicCall == null) {
            clang_Cursor_isDynamicCall = LibclangSymbolProvider.downcallHandle("clang_Cursor_isDynamicCall", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_isDynamicCall"));
        }
        try {
            return (int) clang_Cursor_isDynamicCall.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_isDynamicCall(StructI<? extends CXCursor> C) {
        return new I32(clang_Cursor_isDynamicCall$Raw(C.operator().value()));
    }

    private static MethodHandle clang_Cursor_getReceiverType;

    private static MemorySegment clang_Cursor_getReceiverType$Raw(SegmentAllocator segmentallocator, MemorySegment C) {
        if (clang_Cursor_getReceiverType == null) {
            clang_Cursor_getReceiverType = LibclangSymbolProvider.downcallHandle("clang_Cursor_getReceiverType", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getReceiverType"));
        }
        try {
            return (MemorySegment) clang_Cursor_getReceiverType.invoke(segmentallocator, C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXType clang_Cursor_getReceiverType(SegmentAllocator segmentallocator, StructI<? extends CXCursor> C) {
        return new CXType(clang_Cursor_getReceiverType$Raw(segmentallocator, C.operator().value()));
    }

    private static MethodHandle clang_Cursor_getObjCPropertyAttributes;

    private static int clang_Cursor_getObjCPropertyAttributes$Raw(MemorySegment C, int reserved) {
        if (clang_Cursor_getObjCPropertyAttributes == null) {
            clang_Cursor_getObjCPropertyAttributes = LibclangSymbolProvider.downcallHandle("clang_Cursor_getObjCPropertyAttributes", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getObjCPropertyAttributes"));
        }
        try {
            return (int) clang_Cursor_getObjCPropertyAttributes.invoke(C, reserved);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_getObjCPropertyAttributes(StructI<? extends CXCursor> C, I32I<?> reserved) {
        return new I32(clang_Cursor_getObjCPropertyAttributes$Raw(C.operator().value(), reserved.operator().value()));
    }

    private static MethodHandle clang_Cursor_getObjCPropertyGetterName;

    private static MemorySegment clang_Cursor_getObjCPropertyGetterName$Raw(SegmentAllocator segmentallocator, MemorySegment C) {
        if (clang_Cursor_getObjCPropertyGetterName == null) {
            clang_Cursor_getObjCPropertyGetterName = LibclangSymbolProvider.downcallHandle("clang_Cursor_getObjCPropertyGetterName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getObjCPropertyGetterName"));
        }
        try {
            return (MemorySegment) clang_Cursor_getObjCPropertyGetterName.invoke(segmentallocator, C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_Cursor_getObjCPropertyGetterName(SegmentAllocator segmentallocator, StructI<? extends CXCursor> C) {
        return new CXString(clang_Cursor_getObjCPropertyGetterName$Raw(segmentallocator, C.operator().value()));
    }

    private static MethodHandle clang_Cursor_getObjCPropertySetterName;

    private static MemorySegment clang_Cursor_getObjCPropertySetterName$Raw(SegmentAllocator segmentallocator, MemorySegment C) {
        if (clang_Cursor_getObjCPropertySetterName == null) {
            clang_Cursor_getObjCPropertySetterName = LibclangSymbolProvider.downcallHandle("clang_Cursor_getObjCPropertySetterName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getObjCPropertySetterName"));
        }
        try {
            return (MemorySegment) clang_Cursor_getObjCPropertySetterName.invoke(segmentallocator, C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_Cursor_getObjCPropertySetterName(SegmentAllocator segmentallocator, StructI<? extends CXCursor> C) {
        return new CXString(clang_Cursor_getObjCPropertySetterName$Raw(segmentallocator, C.operator().value()));
    }

    private static MethodHandle clang_Cursor_getObjCDeclQualifiers;

    private static int clang_Cursor_getObjCDeclQualifiers$Raw(MemorySegment C) {
        if (clang_Cursor_getObjCDeclQualifiers == null) {
            clang_Cursor_getObjCDeclQualifiers = LibclangSymbolProvider.downcallHandle("clang_Cursor_getObjCDeclQualifiers", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getObjCDeclQualifiers"));
        }
        try {
            return (int) clang_Cursor_getObjCDeclQualifiers.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_getObjCDeclQualifiers(StructI<? extends CXCursor> C) {
        return new I32(clang_Cursor_getObjCDeclQualifiers$Raw(C.operator().value()));
    }

    private static MethodHandle clang_Cursor_isObjCOptional;

    private static int clang_Cursor_isObjCOptional$Raw(MemorySegment C) {
        if (clang_Cursor_isObjCOptional == null) {
            clang_Cursor_isObjCOptional = LibclangSymbolProvider.downcallHandle("clang_Cursor_isObjCOptional", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_isObjCOptional"));
        }
        try {
            return (int) clang_Cursor_isObjCOptional.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_isObjCOptional(StructI<? extends CXCursor> C) {
        return new I32(clang_Cursor_isObjCOptional$Raw(C.operator().value()));
    }

    private static MethodHandle clang_Cursor_isVariadic;

    private static int clang_Cursor_isVariadic$Raw(MemorySegment C) {
        if (clang_Cursor_isVariadic == null) {
            clang_Cursor_isVariadic = LibclangSymbolProvider.downcallHandle("clang_Cursor_isVariadic", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_isVariadic"));
        }
        try {
            return (int) clang_Cursor_isVariadic.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_isVariadic(StructI<? extends CXCursor> C) {
        return new I32(clang_Cursor_isVariadic$Raw(C.operator().value()));
    }

    private static MethodHandle clang_Cursor_isExternalSymbol;

    private static int clang_Cursor_isExternalSymbol$Raw(MemorySegment C, MemorySegment language, MemorySegment definedIn, MemorySegment isGenerated) {
        if (clang_Cursor_isExternalSymbol == null) {
            clang_Cursor_isExternalSymbol = LibclangSymbolProvider.downcallHandle("clang_Cursor_isExternalSymbol", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_isExternalSymbol"));
        }
        try {
            return (int) clang_Cursor_isExternalSymbol.invoke(C, language, definedIn, isGenerated);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Cursor_isExternalSymbol(StructI<? extends CXCursor> C, PtrI<? extends StructI<? extends CXString>> language, PtrI<? extends StructI<? extends CXString>> definedIn, PtrI<? extends I32I<?>> isGenerated) {
        return new I32(clang_Cursor_isExternalSymbol$Raw(C.operator().value(), language.operator().value(), definedIn.operator().value(), isGenerated.operator().value()));
    }

    private static MethodHandle clang_Cursor_getCommentRange;

    private static MemorySegment clang_Cursor_getCommentRange$Raw(SegmentAllocator segmentallocator, MemorySegment C) {
        if (clang_Cursor_getCommentRange == null) {
            clang_Cursor_getCommentRange = LibclangSymbolProvider.downcallHandle("clang_Cursor_getCommentRange", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getCommentRange"));
        }
        try {
            return (MemorySegment) clang_Cursor_getCommentRange.invoke(segmentallocator, C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceRange clang_Cursor_getCommentRange(SegmentAllocator segmentallocator, StructI<? extends CXCursor> C) {
        return new CXSourceRange(clang_Cursor_getCommentRange$Raw(segmentallocator, C.operator().value()));
    }

    private static MethodHandle clang_Cursor_getRawCommentText;

    private static MemorySegment clang_Cursor_getRawCommentText$Raw(SegmentAllocator segmentallocator, MemorySegment C) {
        if (clang_Cursor_getRawCommentText == null) {
            clang_Cursor_getRawCommentText = LibclangSymbolProvider.downcallHandle("clang_Cursor_getRawCommentText", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getRawCommentText"));
        }
        try {
            return (MemorySegment) clang_Cursor_getRawCommentText.invoke(segmentallocator, C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_Cursor_getRawCommentText(SegmentAllocator segmentallocator, StructI<? extends CXCursor> C) {
        return new CXString(clang_Cursor_getRawCommentText$Raw(segmentallocator, C.operator().value()));
    }

    private static MethodHandle clang_Cursor_getBriefCommentText;

    private static MemorySegment clang_Cursor_getBriefCommentText$Raw(SegmentAllocator segmentallocator, MemorySegment C) {
        if (clang_Cursor_getBriefCommentText == null) {
            clang_Cursor_getBriefCommentText = LibclangSymbolProvider.downcallHandle("clang_Cursor_getBriefCommentText", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getBriefCommentText"));
        }
        try {
            return (MemorySegment) clang_Cursor_getBriefCommentText.invoke(segmentallocator, C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_Cursor_getBriefCommentText(SegmentAllocator segmentallocator, StructI<? extends CXCursor> C) {
        return new CXString(clang_Cursor_getBriefCommentText$Raw(segmentallocator, C.operator().value()));
    }

    private static MethodHandle clang_Cursor_getMangling;

    private static MemorySegment clang_Cursor_getMangling$Raw(SegmentAllocator segmentallocator, MemorySegment arg0) {
        if (clang_Cursor_getMangling == null) {
            clang_Cursor_getMangling = LibclangSymbolProvider.downcallHandle("clang_Cursor_getMangling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getMangling"));
        }
        try {
            return (MemorySegment) clang_Cursor_getMangling.invoke(segmentallocator, arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_Cursor_getMangling(SegmentAllocator segmentallocator, StructI<? extends CXCursor> arg0) {
        return new CXString(clang_Cursor_getMangling$Raw(segmentallocator, arg0.operator().value()));
    }

    private static MethodHandle clang_Cursor_getCXXManglings;

    private static MemorySegment clang_Cursor_getCXXManglings$Raw(MemorySegment arg0) {
        if (clang_Cursor_getCXXManglings == null) {
            clang_Cursor_getCXXManglings = LibclangSymbolProvider.downcallHandle("clang_Cursor_getCXXManglings", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getCXXManglings"));
        }
        try {
            return (MemorySegment) clang_Cursor_getCXXManglings.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<CXStringSet> clang_Cursor_getCXXManglings(StructI<? extends CXCursor> arg0) {
        return new Ptr<CXStringSet>(clang_Cursor_getCXXManglings$Raw(arg0.operator().value()), CXStringSet.OPERATIONS);
    }

    private static MethodHandle clang_Cursor_getObjCManglings;

    private static MemorySegment clang_Cursor_getObjCManglings$Raw(MemorySegment arg0) {
        if (clang_Cursor_getObjCManglings == null) {
            clang_Cursor_getObjCManglings = LibclangSymbolProvider.downcallHandle("clang_Cursor_getObjCManglings", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getObjCManglings"));
        }
        try {
            return (MemorySegment) clang_Cursor_getObjCManglings.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<CXStringSet> clang_Cursor_getObjCManglings(StructI<? extends CXCursor> arg0) {
        return new Ptr<CXStringSet>(clang_Cursor_getObjCManglings$Raw(arg0.operator().value()), CXStringSet.OPERATIONS);
    }

    private static MethodHandle clang_Cursor_getModule;

    private static MemorySegment clang_Cursor_getModule$Raw(MemorySegment C) {
        if (clang_Cursor_getModule == null) {
            clang_Cursor_getModule = LibclangSymbolProvider.downcallHandle("clang_Cursor_getModule", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_getModule"));
        }
        try {
            return (MemorySegment) clang_Cursor_getModule.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXModule clang_Cursor_getModule(StructI<? extends CXCursor> C) {
        return new CXModule(clang_Cursor_getModule$Raw(C.operator().value()));
    }

    private static MethodHandle clang_getModuleForFile;

    private static MemorySegment clang_getModuleForFile$Raw(MemorySegment arg0, MemorySegment arg1) {
        if (clang_getModuleForFile == null) {
            clang_getModuleForFile = LibclangSymbolProvider.downcallHandle("clang_getModuleForFile", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getModuleForFile"));
        }
        try {
            return (MemorySegment) clang_getModuleForFile.invoke(arg0, arg1);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXModule clang_getModuleForFile(CXTranslationUnit arg0, CXFile arg1) {
        return new CXModule(clang_getModuleForFile$Raw(arg0.operator().value(), arg1.operator().value()));
    }

    private static MethodHandle clang_Module_getASTFile;

    private static MemorySegment clang_Module_getASTFile$Raw(MemorySegment Module) {
        if (clang_Module_getASTFile == null) {
            clang_Module_getASTFile = LibclangSymbolProvider.downcallHandle("clang_Module_getASTFile", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Module_getASTFile"));
        }
        try {
            return (MemorySegment) clang_Module_getASTFile.invoke(Module);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXFile clang_Module_getASTFile(CXModule Module) {
        return new CXFile(clang_Module_getASTFile$Raw(Module.operator().value()));
    }

    private static MethodHandle clang_Module_getParent;

    private static MemorySegment clang_Module_getParent$Raw(MemorySegment Module) {
        if (clang_Module_getParent == null) {
            clang_Module_getParent = LibclangSymbolProvider.downcallHandle("clang_Module_getParent", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Module_getParent"));
        }
        try {
            return (MemorySegment) clang_Module_getParent.invoke(Module);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXModule clang_Module_getParent(CXModule Module) {
        return new CXModule(clang_Module_getParent$Raw(Module.operator().value()));
    }

    private static MethodHandle clang_Module_getName;

    private static MemorySegment clang_Module_getName$Raw(SegmentAllocator segmentallocator, MemorySegment Module) {
        if (clang_Module_getName == null) {
            clang_Module_getName = LibclangSymbolProvider.downcallHandle("clang_Module_getName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Module_getName"));
        }
        try {
            return (MemorySegment) clang_Module_getName.invoke(segmentallocator, Module);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_Module_getName(SegmentAllocator segmentallocator, CXModule Module) {
        return new CXString(clang_Module_getName$Raw(segmentallocator, Module.operator().value()));
    }

    private static MethodHandle clang_Module_getFullName;

    private static MemorySegment clang_Module_getFullName$Raw(SegmentAllocator segmentallocator, MemorySegment Module) {
        if (clang_Module_getFullName == null) {
            clang_Module_getFullName = LibclangSymbolProvider.downcallHandle("clang_Module_getFullName", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Module_getFullName"));
        }
        try {
            return (MemorySegment) clang_Module_getFullName.invoke(segmentallocator, Module);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_Module_getFullName(SegmentAllocator segmentallocator, CXModule Module) {
        return new CXString(clang_Module_getFullName$Raw(segmentallocator, Module.operator().value()));
    }

    private static MethodHandle clang_Module_isSystem;

    private static int clang_Module_isSystem$Raw(MemorySegment Module) {
        if (clang_Module_isSystem == null) {
            clang_Module_isSystem = LibclangSymbolProvider.downcallHandle("clang_Module_isSystem", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Module_isSystem"));
        }
        try {
            return (int) clang_Module_isSystem.invoke(Module);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Module_isSystem(CXModule Module) {
        return new I32(clang_Module_isSystem$Raw(Module.operator().value()));
    }

    private static MethodHandle clang_Module_getNumTopLevelHeaders;

    private static int clang_Module_getNumTopLevelHeaders$Raw(MemorySegment arg0, MemorySegment Module) {
        if (clang_Module_getNumTopLevelHeaders == null) {
            clang_Module_getNumTopLevelHeaders = LibclangSymbolProvider.downcallHandle("clang_Module_getNumTopLevelHeaders", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Module_getNumTopLevelHeaders"));
        }
        try {
            return (int) clang_Module_getNumTopLevelHeaders.invoke(arg0, Module);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Module_getNumTopLevelHeaders(CXTranslationUnit arg0, CXModule Module) {
        return new I32(clang_Module_getNumTopLevelHeaders$Raw(arg0.operator().value(), Module.operator().value()));
    }

    private static MethodHandle clang_Module_getTopLevelHeader;

    private static MemorySegment clang_Module_getTopLevelHeader$Raw(MemorySegment arg0, MemorySegment Module, int Index) {
        if (clang_Module_getTopLevelHeader == null) {
            clang_Module_getTopLevelHeader = LibclangSymbolProvider.downcallHandle("clang_Module_getTopLevelHeader", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Module_getTopLevelHeader"));
        }
        try {
            return (MemorySegment) clang_Module_getTopLevelHeader.invoke(arg0, Module, Index);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXFile clang_Module_getTopLevelHeader(CXTranslationUnit arg0, CXModule Module, I32I<?> Index) {
        return new CXFile(clang_Module_getTopLevelHeader$Raw(arg0.operator().value(), Module.operator().value(), Index.operator().value()));
    }

    private static MethodHandle clang_CXXConstructor_isConvertingConstructor;

    private static int clang_CXXConstructor_isConvertingConstructor$Raw(MemorySegment C) {
        if (clang_CXXConstructor_isConvertingConstructor == null) {
            clang_CXXConstructor_isConvertingConstructor = LibclangSymbolProvider.downcallHandle("clang_CXXConstructor_isConvertingConstructor", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXXConstructor_isConvertingConstructor"));
        }
        try {
            return (int) clang_CXXConstructor_isConvertingConstructor.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXXConstructor_isConvertingConstructor(StructI<? extends CXCursor> C) {
        return new I32(clang_CXXConstructor_isConvertingConstructor$Raw(C.operator().value()));
    }

    private static MethodHandle clang_CXXConstructor_isCopyConstructor;

    private static int clang_CXXConstructor_isCopyConstructor$Raw(MemorySegment C) {
        if (clang_CXXConstructor_isCopyConstructor == null) {
            clang_CXXConstructor_isCopyConstructor = LibclangSymbolProvider.downcallHandle("clang_CXXConstructor_isCopyConstructor", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXXConstructor_isCopyConstructor"));
        }
        try {
            return (int) clang_CXXConstructor_isCopyConstructor.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXXConstructor_isCopyConstructor(StructI<? extends CXCursor> C) {
        return new I32(clang_CXXConstructor_isCopyConstructor$Raw(C.operator().value()));
    }

    private static MethodHandle clang_CXXConstructor_isDefaultConstructor;

    private static int clang_CXXConstructor_isDefaultConstructor$Raw(MemorySegment C) {
        if (clang_CXXConstructor_isDefaultConstructor == null) {
            clang_CXXConstructor_isDefaultConstructor = LibclangSymbolProvider.downcallHandle("clang_CXXConstructor_isDefaultConstructor", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXXConstructor_isDefaultConstructor"));
        }
        try {
            return (int) clang_CXXConstructor_isDefaultConstructor.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXXConstructor_isDefaultConstructor(StructI<? extends CXCursor> C) {
        return new I32(clang_CXXConstructor_isDefaultConstructor$Raw(C.operator().value()));
    }

    private static MethodHandle clang_CXXConstructor_isMoveConstructor;

    private static int clang_CXXConstructor_isMoveConstructor$Raw(MemorySegment C) {
        if (clang_CXXConstructor_isMoveConstructor == null) {
            clang_CXXConstructor_isMoveConstructor = LibclangSymbolProvider.downcallHandle("clang_CXXConstructor_isMoveConstructor", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXXConstructor_isMoveConstructor"));
        }
        try {
            return (int) clang_CXXConstructor_isMoveConstructor.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXXConstructor_isMoveConstructor(StructI<? extends CXCursor> C) {
        return new I32(clang_CXXConstructor_isMoveConstructor$Raw(C.operator().value()));
    }

    private static MethodHandle clang_CXXField_isMutable;

    private static int clang_CXXField_isMutable$Raw(MemorySegment C) {
        if (clang_CXXField_isMutable == null) {
            clang_CXXField_isMutable = LibclangSymbolProvider.downcallHandle("clang_CXXField_isMutable", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXXField_isMutable"));
        }
        try {
            return (int) clang_CXXField_isMutable.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXXField_isMutable(StructI<? extends CXCursor> C) {
        return new I32(clang_CXXField_isMutable$Raw(C.operator().value()));
    }

    private static MethodHandle clang_CXXMethod_isDefaulted;

    private static int clang_CXXMethod_isDefaulted$Raw(MemorySegment C) {
        if (clang_CXXMethod_isDefaulted == null) {
            clang_CXXMethod_isDefaulted = LibclangSymbolProvider.downcallHandle("clang_CXXMethod_isDefaulted", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXXMethod_isDefaulted"));
        }
        try {
            return (int) clang_CXXMethod_isDefaulted.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXXMethod_isDefaulted(StructI<? extends CXCursor> C) {
        return new I32(clang_CXXMethod_isDefaulted$Raw(C.operator().value()));
    }

    private static MethodHandle clang_CXXMethod_isDeleted;

    private static int clang_CXXMethod_isDeleted$Raw(MemorySegment C) {
        if (clang_CXXMethod_isDeleted == null) {
            clang_CXXMethod_isDeleted = LibclangSymbolProvider.downcallHandle("clang_CXXMethod_isDeleted", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXXMethod_isDeleted"));
        }
        try {
            return (int) clang_CXXMethod_isDeleted.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXXMethod_isDeleted(StructI<? extends CXCursor> C) {
        return new I32(clang_CXXMethod_isDeleted$Raw(C.operator().value()));
    }

    private static MethodHandle clang_CXXMethod_isPureVirtual;

    private static int clang_CXXMethod_isPureVirtual$Raw(MemorySegment C) {
        if (clang_CXXMethod_isPureVirtual == null) {
            clang_CXXMethod_isPureVirtual = LibclangSymbolProvider.downcallHandle("clang_CXXMethod_isPureVirtual", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXXMethod_isPureVirtual"));
        }
        try {
            return (int) clang_CXXMethod_isPureVirtual.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXXMethod_isPureVirtual(StructI<? extends CXCursor> C) {
        return new I32(clang_CXXMethod_isPureVirtual$Raw(C.operator().value()));
    }

    private static MethodHandle clang_CXXMethod_isStatic;

    private static int clang_CXXMethod_isStatic$Raw(MemorySegment C) {
        if (clang_CXXMethod_isStatic == null) {
            clang_CXXMethod_isStatic = LibclangSymbolProvider.downcallHandle("clang_CXXMethod_isStatic", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXXMethod_isStatic"));
        }
        try {
            return (int) clang_CXXMethod_isStatic.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXXMethod_isStatic(StructI<? extends CXCursor> C) {
        return new I32(clang_CXXMethod_isStatic$Raw(C.operator().value()));
    }

    private static MethodHandle clang_CXXMethod_isVirtual;

    private static int clang_CXXMethod_isVirtual$Raw(MemorySegment C) {
        if (clang_CXXMethod_isVirtual == null) {
            clang_CXXMethod_isVirtual = LibclangSymbolProvider.downcallHandle("clang_CXXMethod_isVirtual", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXXMethod_isVirtual"));
        }
        try {
            return (int) clang_CXXMethod_isVirtual.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXXMethod_isVirtual(StructI<? extends CXCursor> C) {
        return new I32(clang_CXXMethod_isVirtual$Raw(C.operator().value()));
    }

    private static MethodHandle clang_CXXMethod_isCopyAssignmentOperator;

    private static int clang_CXXMethod_isCopyAssignmentOperator$Raw(MemorySegment C) {
        if (clang_CXXMethod_isCopyAssignmentOperator == null) {
            clang_CXXMethod_isCopyAssignmentOperator = LibclangSymbolProvider.downcallHandle("clang_CXXMethod_isCopyAssignmentOperator", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXXMethod_isCopyAssignmentOperator"));
        }
        try {
            return (int) clang_CXXMethod_isCopyAssignmentOperator.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXXMethod_isCopyAssignmentOperator(StructI<? extends CXCursor> C) {
        return new I32(clang_CXXMethod_isCopyAssignmentOperator$Raw(C.operator().value()));
    }

    private static MethodHandle clang_CXXMethod_isMoveAssignmentOperator;

    private static int clang_CXXMethod_isMoveAssignmentOperator$Raw(MemorySegment C) {
        if (clang_CXXMethod_isMoveAssignmentOperator == null) {
            clang_CXXMethod_isMoveAssignmentOperator = LibclangSymbolProvider.downcallHandle("clang_CXXMethod_isMoveAssignmentOperator", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXXMethod_isMoveAssignmentOperator"));
        }
        try {
            return (int) clang_CXXMethod_isMoveAssignmentOperator.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXXMethod_isMoveAssignmentOperator(StructI<? extends CXCursor> C) {
        return new I32(clang_CXXMethod_isMoveAssignmentOperator$Raw(C.operator().value()));
    }

    private static MethodHandle clang_CXXMethod_isExplicit;

    private static int clang_CXXMethod_isExplicit$Raw(MemorySegment C) {
        if (clang_CXXMethod_isExplicit == null) {
            clang_CXXMethod_isExplicit = LibclangSymbolProvider.downcallHandle("clang_CXXMethod_isExplicit", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXXMethod_isExplicit"));
        }
        try {
            return (int) clang_CXXMethod_isExplicit.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXXMethod_isExplicit(StructI<? extends CXCursor> C) {
        return new I32(clang_CXXMethod_isExplicit$Raw(C.operator().value()));
    }

    private static MethodHandle clang_CXXRecord_isAbstract;

    private static int clang_CXXRecord_isAbstract$Raw(MemorySegment C) {
        if (clang_CXXRecord_isAbstract == null) {
            clang_CXXRecord_isAbstract = LibclangSymbolProvider.downcallHandle("clang_CXXRecord_isAbstract", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXXRecord_isAbstract"));
        }
        try {
            return (int) clang_CXXRecord_isAbstract.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXXRecord_isAbstract(StructI<? extends CXCursor> C) {
        return new I32(clang_CXXRecord_isAbstract$Raw(C.operator().value()));
    }

    private static MethodHandle clang_EnumDecl_isScoped;

    private static int clang_EnumDecl_isScoped$Raw(MemorySegment C) {
        if (clang_EnumDecl_isScoped == null) {
            clang_EnumDecl_isScoped = LibclangSymbolProvider.downcallHandle("clang_EnumDecl_isScoped", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_EnumDecl_isScoped"));
        }
        try {
            return (int) clang_EnumDecl_isScoped.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_EnumDecl_isScoped(StructI<? extends CXCursor> C) {
        return new I32(clang_EnumDecl_isScoped$Raw(C.operator().value()));
    }

    private static MethodHandle clang_CXXMethod_isConst;

    private static int clang_CXXMethod_isConst$Raw(MemorySegment C) {
        if (clang_CXXMethod_isConst == null) {
            clang_CXXMethod_isConst = LibclangSymbolProvider.downcallHandle("clang_CXXMethod_isConst", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_CXXMethod_isConst"));
        }
        try {
            return (int) clang_CXXMethod_isConst.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_CXXMethod_isConst(StructI<? extends CXCursor> C) {
        return new I32(clang_CXXMethod_isConst$Raw(C.operator().value()));
    }

    private static MethodHandle clang_getTemplateCursorKind;

    private static int clang_getTemplateCursorKind$Raw(MemorySegment C) {
        if (clang_getTemplateCursorKind == null) {
            clang_getTemplateCursorKind = LibclangSymbolProvider.downcallHandle("clang_getTemplateCursorKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getTemplateCursorKind"));
        }
        try {
            return (int) clang_getTemplateCursorKind.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursorKind clang_getTemplateCursorKind(StructI<? extends CXCursor> C) {
        return new CXCursorKind(clang_getTemplateCursorKind$Raw(C.operator().value()));
    }

    private static MethodHandle clang_getSpecializedCursorTemplate;

    private static MemorySegment clang_getSpecializedCursorTemplate$Raw(SegmentAllocator segmentallocator, MemorySegment C) {
        if (clang_getSpecializedCursorTemplate == null) {
            clang_getSpecializedCursorTemplate = LibclangSymbolProvider.downcallHandle("clang_getSpecializedCursorTemplate", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getSpecializedCursorTemplate"));
        }
        try {
            return (MemorySegment) clang_getSpecializedCursorTemplate.invoke(segmentallocator, C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursor clang_getSpecializedCursorTemplate(SegmentAllocator segmentallocator, StructI<? extends CXCursor> C) {
        return new CXCursor(clang_getSpecializedCursorTemplate$Raw(segmentallocator, C.operator().value()));
    }

    private static MethodHandle clang_getCursorReferenceNameRange;

    private static MemorySegment clang_getCursorReferenceNameRange$Raw(SegmentAllocator segmentallocator, MemorySegment C, int NameFlags, int PieceIndex) {
        if (clang_getCursorReferenceNameRange == null) {
            clang_getCursorReferenceNameRange = LibclangSymbolProvider.downcallHandle("clang_getCursorReferenceNameRange", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorReferenceNameRange"));
        }
        try {
            return (MemorySegment) clang_getCursorReferenceNameRange.invoke(segmentallocator, C, NameFlags, PieceIndex);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceRange clang_getCursorReferenceNameRange(SegmentAllocator segmentallocator, StructI<? extends CXCursor> C, I32I<?> NameFlags, I32I<?> PieceIndex) {
        return new CXSourceRange(clang_getCursorReferenceNameRange$Raw(segmentallocator, C.operator().value(), NameFlags.operator().value(), PieceIndex.operator().value()));
    }

    private static MethodHandle clang_getToken;

    private static MemorySegment clang_getToken$Raw(MemorySegment TU, MemorySegment Location) {
        if (clang_getToken == null) {
            clang_getToken = LibclangSymbolProvider.downcallHandle("clang_getToken", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getToken"));
        }
        try {
            return (MemorySegment) clang_getToken.invoke(TU, Location);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<CXToken> clang_getToken(CXTranslationUnit TU, StructI<? extends CXSourceLocation> Location) {
        return new Ptr<CXToken>(clang_getToken$Raw(TU.operator().value(), Location.operator().value()), CXToken.OPERATIONS);
    }

    private static MethodHandle clang_getTokenKind;

    private static int clang_getTokenKind$Raw(MemorySegment arg0) {
        if (clang_getTokenKind == null) {
            clang_getTokenKind = LibclangSymbolProvider.downcallHandle("clang_getTokenKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getTokenKind"));
        }
        try {
            return (int) clang_getTokenKind.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXTokenKind clang_getTokenKind(StructI<? extends CXToken> arg0) {
        return new CXTokenKind(clang_getTokenKind$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_getTokenSpelling;

    private static MemorySegment clang_getTokenSpelling$Raw(SegmentAllocator segmentallocator, MemorySegment arg0, MemorySegment arg1) {
        if (clang_getTokenSpelling == null) {
            clang_getTokenSpelling = LibclangSymbolProvider.downcallHandle("clang_getTokenSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getTokenSpelling"));
        }
        try {
            return (MemorySegment) clang_getTokenSpelling.invoke(segmentallocator, arg0, arg1);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getTokenSpelling(SegmentAllocator segmentallocator, CXTranslationUnit arg0, StructI<? extends CXToken> arg1) {
        return new CXString(clang_getTokenSpelling$Raw(segmentallocator, arg0.operator().value(), arg1.operator().value()));
    }

    private static MethodHandle clang_getTokenLocation;

    private static MemorySegment clang_getTokenLocation$Raw(SegmentAllocator segmentallocator, MemorySegment arg0, MemorySegment arg1) {
        if (clang_getTokenLocation == null) {
            clang_getTokenLocation = LibclangSymbolProvider.downcallHandle("clang_getTokenLocation", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getTokenLocation"));
        }
        try {
            return (MemorySegment) clang_getTokenLocation.invoke(segmentallocator, arg0, arg1);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceLocation clang_getTokenLocation(SegmentAllocator segmentallocator, CXTranslationUnit arg0, StructI<? extends CXToken> arg1) {
        return new CXSourceLocation(clang_getTokenLocation$Raw(segmentallocator, arg0.operator().value(), arg1.operator().value()));
    }

    private static MethodHandle clang_getTokenExtent;

    private static MemorySegment clang_getTokenExtent$Raw(SegmentAllocator segmentallocator, MemorySegment arg0, MemorySegment arg1) {
        if (clang_getTokenExtent == null) {
            clang_getTokenExtent = LibclangSymbolProvider.downcallHandle("clang_getTokenExtent", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getTokenExtent"));
        }
        try {
            return (MemorySegment) clang_getTokenExtent.invoke(segmentallocator, arg0, arg1);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceRange clang_getTokenExtent(SegmentAllocator segmentallocator, CXTranslationUnit arg0, StructI<? extends CXToken> arg1) {
        return new CXSourceRange(clang_getTokenExtent$Raw(segmentallocator, arg0.operator().value(), arg1.operator().value()));
    }

    private static MethodHandle clang_tokenize;

    private static void clang_tokenize$Raw(MemorySegment TU, MemorySegment Range, MemorySegment Tokens, MemorySegment NumTokens) {
        if (clang_tokenize == null) {
            clang_tokenize = LibclangSymbolProvider.downcallHandle("clang_tokenize", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_tokenize"));
        }
        try {
            clang_tokenize.invoke(TU, Range, Tokens, NumTokens);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_tokenize(CXTranslationUnit TU, StructI<? extends CXSourceRange> Range, PtrI<? extends PtrI<? extends StructI<? extends CXToken>>> Tokens, PtrI<? extends I32I<?>> NumTokens) {
        clang_tokenize$Raw(TU.operator().value(), Range.operator().value(), Tokens.operator().value(), NumTokens.operator().value());
    }

    private static MethodHandle clang_annotateTokens;

    private static void clang_annotateTokens$Raw(MemorySegment TU, MemorySegment Tokens, int NumTokens, MemorySegment Cursors) {
        if (clang_annotateTokens == null) {
            clang_annotateTokens = LibclangSymbolProvider.downcallHandle("clang_annotateTokens", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_annotateTokens"));
        }
        try {
            clang_annotateTokens.invoke(TU, Tokens, NumTokens, Cursors);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_annotateTokens(CXTranslationUnit TU, PtrI<? extends StructI<? extends CXToken>> Tokens, I32I<?> NumTokens, PtrI<? extends StructI<? extends CXCursor>> Cursors) {
        clang_annotateTokens$Raw(TU.operator().value(), Tokens.operator().value(), NumTokens.operator().value(), Cursors.operator().value());
    }

    private static MethodHandle clang_disposeTokens;

    private static void clang_disposeTokens$Raw(MemorySegment TU, MemorySegment Tokens, int NumTokens) {
        if (clang_disposeTokens == null) {
            clang_disposeTokens = LibclangSymbolProvider.downcallHandle("clang_disposeTokens", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_disposeTokens"));
        }
        try {
            clang_disposeTokens.invoke(TU, Tokens, NumTokens);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_disposeTokens(CXTranslationUnit TU, PtrI<? extends StructI<? extends CXToken>> Tokens, I32I<?> NumTokens) {
        clang_disposeTokens$Raw(TU.operator().value(), Tokens.operator().value(), NumTokens.operator().value());
    }

    private static MethodHandle clang_getCursorKindSpelling;

    private static MemorySegment clang_getCursorKindSpelling$Raw(SegmentAllocator segmentallocator, int Kind) {
        if (clang_getCursorKindSpelling == null) {
            clang_getCursorKindSpelling = LibclangSymbolProvider.downcallHandle("clang_getCursorKindSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorKindSpelling"));
        }
        try {
            return (MemorySegment) clang_getCursorKindSpelling.invoke(segmentallocator, Kind);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getCursorKindSpelling(SegmentAllocator segmentallocator, I32I<? extends CXCursorKind> Kind) {
        return new CXString(clang_getCursorKindSpelling$Raw(segmentallocator, Kind.operator().value()));
    }

    private static MethodHandle clang_getDefinitionSpellingAndExtent;

    private static void clang_getDefinitionSpellingAndExtent$Raw(MemorySegment arg0, MemorySegment startBuf, MemorySegment endBuf, MemorySegment startLine, MemorySegment startColumn, MemorySegment endLine, MemorySegment endColumn) {
        if (clang_getDefinitionSpellingAndExtent == null) {
            clang_getDefinitionSpellingAndExtent = LibclangSymbolProvider.downcallHandle("clang_getDefinitionSpellingAndExtent", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getDefinitionSpellingAndExtent"));
        }
        try {
            clang_getDefinitionSpellingAndExtent.invoke(arg0, startBuf, endBuf, startLine, startColumn, endLine, endColumn);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_getDefinitionSpellingAndExtent(StructI<? extends CXCursor> arg0, PtrI<? extends PtrI<? extends I8I<?>>> startBuf, PtrI<? extends PtrI<? extends I8I<?>>> endBuf, PtrI<? extends I32I<?>> startLine, PtrI<? extends I32I<?>> startColumn, PtrI<? extends I32I<?>> endLine, PtrI<? extends I32I<?>> endColumn) {
        clang_getDefinitionSpellingAndExtent$Raw(arg0.operator().value(), startBuf.operator().value(), endBuf.operator().value(), startLine.operator().value(), startColumn.operator().value(), endLine.operator().value(), endColumn.operator().value());
    }

    private static MethodHandle clang_enableStackTraces;

    private static void clang_enableStackTraces$Raw() {
        if (clang_enableStackTraces == null) {
            clang_enableStackTraces = LibclangSymbolProvider.downcallHandle("clang_enableStackTraces", FunctionDescriptor.ofVoid()).orElseThrow(() -> new Utils.SymbolNotFound("clang_enableStackTraces"));
        }
        try {
            clang_enableStackTraces.invoke();
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_enableStackTraces() {
        clang_enableStackTraces$Raw();
    }

    private static MethodHandle clang_executeOnThread;

    private static void clang_executeOnThread$Raw(MemorySegment fn, MemorySegment user_data, int stack_size) {
        if (clang_executeOnThread == null) {
            clang_executeOnThread = LibclangSymbolProvider.downcallHandle("clang_executeOnThread", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_executeOnThread"));
        }
        try {
            clang_executeOnThread.invoke(fn, user_data, stack_size);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_executeOnThread(PtrI<? extends func_void_void_ptr_.Function> fn, PtrI<?> user_data, I32I<?> stack_size) {
        clang_executeOnThread$Raw(fn.operator().value(), user_data.operator().value(), stack_size.operator().value());
    }

    private static MethodHandle clang_getCompletionChunkKind;

    private static int clang_getCompletionChunkKind$Raw(MemorySegment completion_string, int chunk_number) {
        if (clang_getCompletionChunkKind == null) {
            clang_getCompletionChunkKind = LibclangSymbolProvider.downcallHandle("clang_getCompletionChunkKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCompletionChunkKind"));
        }
        try {
            return (int) clang_getCompletionChunkKind.invoke(completion_string, chunk_number);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCompletionChunkKind clang_getCompletionChunkKind(CXCompletionString completion_string, I32I<?> chunk_number) {
        return new CXCompletionChunkKind(clang_getCompletionChunkKind$Raw(completion_string.operator().value(), chunk_number.operator().value()));
    }

    private static MethodHandle clang_getCompletionChunkText;

    private static MemorySegment clang_getCompletionChunkText$Raw(SegmentAllocator segmentallocator, MemorySegment completion_string, int chunk_number) {
        if (clang_getCompletionChunkText == null) {
            clang_getCompletionChunkText = LibclangSymbolProvider.downcallHandle("clang_getCompletionChunkText", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCompletionChunkText"));
        }
        try {
            return (MemorySegment) clang_getCompletionChunkText.invoke(segmentallocator, completion_string, chunk_number);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getCompletionChunkText(SegmentAllocator segmentallocator, CXCompletionString completion_string, I32I<?> chunk_number) {
        return new CXString(clang_getCompletionChunkText$Raw(segmentallocator, completion_string.operator().value(), chunk_number.operator().value()));
    }

    private static MethodHandle clang_getCompletionChunkCompletionString;

    private static MemorySegment clang_getCompletionChunkCompletionString$Raw(MemorySegment completion_string, int chunk_number) {
        if (clang_getCompletionChunkCompletionString == null) {
            clang_getCompletionChunkCompletionString = LibclangSymbolProvider.downcallHandle("clang_getCompletionChunkCompletionString", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCompletionChunkCompletionString"));
        }
        try {
            return (MemorySegment) clang_getCompletionChunkCompletionString.invoke(completion_string, chunk_number);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCompletionString clang_getCompletionChunkCompletionString(CXCompletionString completion_string, I32I<?> chunk_number) {
        return new CXCompletionString(clang_getCompletionChunkCompletionString$Raw(completion_string.operator().value(), chunk_number.operator().value()));
    }

    private static MethodHandle clang_getNumCompletionChunks;

    private static int clang_getNumCompletionChunks$Raw(MemorySegment completion_string) {
        if (clang_getNumCompletionChunks == null) {
            clang_getNumCompletionChunks = LibclangSymbolProvider.downcallHandle("clang_getNumCompletionChunks", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getNumCompletionChunks"));
        }
        try {
            return (int) clang_getNumCompletionChunks.invoke(completion_string);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getNumCompletionChunks(CXCompletionString completion_string) {
        return new I32(clang_getNumCompletionChunks$Raw(completion_string.operator().value()));
    }

    private static MethodHandle clang_getCompletionPriority;

    private static int clang_getCompletionPriority$Raw(MemorySegment completion_string) {
        if (clang_getCompletionPriority == null) {
            clang_getCompletionPriority = LibclangSymbolProvider.downcallHandle("clang_getCompletionPriority", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCompletionPriority"));
        }
        try {
            return (int) clang_getCompletionPriority.invoke(completion_string);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getCompletionPriority(CXCompletionString completion_string) {
        return new I32(clang_getCompletionPriority$Raw(completion_string.operator().value()));
    }

    private static MethodHandle clang_getCompletionAvailability;

    private static int clang_getCompletionAvailability$Raw(MemorySegment completion_string) {
        if (clang_getCompletionAvailability == null) {
            clang_getCompletionAvailability = LibclangSymbolProvider.downcallHandle("clang_getCompletionAvailability", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCompletionAvailability"));
        }
        try {
            return (int) clang_getCompletionAvailability.invoke(completion_string);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXAvailabilityKind clang_getCompletionAvailability(CXCompletionString completion_string) {
        return new CXAvailabilityKind(clang_getCompletionAvailability$Raw(completion_string.operator().value()));
    }

    private static MethodHandle clang_getCompletionNumAnnotations;

    private static int clang_getCompletionNumAnnotations$Raw(MemorySegment completion_string) {
        if (clang_getCompletionNumAnnotations == null) {
            clang_getCompletionNumAnnotations = LibclangSymbolProvider.downcallHandle("clang_getCompletionNumAnnotations", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCompletionNumAnnotations"));
        }
        try {
            return (int) clang_getCompletionNumAnnotations.invoke(completion_string);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getCompletionNumAnnotations(CXCompletionString completion_string) {
        return new I32(clang_getCompletionNumAnnotations$Raw(completion_string.operator().value()));
    }

    private static MethodHandle clang_getCompletionAnnotation;

    private static MemorySegment clang_getCompletionAnnotation$Raw(SegmentAllocator segmentallocator, MemorySegment completion_string, int annotation_number) {
        if (clang_getCompletionAnnotation == null) {
            clang_getCompletionAnnotation = LibclangSymbolProvider.downcallHandle("clang_getCompletionAnnotation", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCompletionAnnotation"));
        }
        try {
            return (MemorySegment) clang_getCompletionAnnotation.invoke(segmentallocator, completion_string, annotation_number);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getCompletionAnnotation(SegmentAllocator segmentallocator, CXCompletionString completion_string, I32I<?> annotation_number) {
        return new CXString(clang_getCompletionAnnotation$Raw(segmentallocator, completion_string.operator().value(), annotation_number.operator().value()));
    }

    private static MethodHandle clang_getCompletionParent;

    private static MemorySegment clang_getCompletionParent$Raw(SegmentAllocator segmentallocator, MemorySegment completion_string, MemorySegment kind) {
        if (clang_getCompletionParent == null) {
            clang_getCompletionParent = LibclangSymbolProvider.downcallHandle("clang_getCompletionParent", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCompletionParent"));
        }
        try {
            return (MemorySegment) clang_getCompletionParent.invoke(segmentallocator, completion_string, kind);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getCompletionParent(SegmentAllocator segmentallocator, CXCompletionString completion_string, PtrI<? extends I32I<? extends CXCursorKind>> kind) {
        return new CXString(clang_getCompletionParent$Raw(segmentallocator, completion_string.operator().value(), kind.operator().value()));
    }

    private static MethodHandle clang_getCompletionBriefComment;

    private static MemorySegment clang_getCompletionBriefComment$Raw(SegmentAllocator segmentallocator, MemorySegment completion_string) {
        if (clang_getCompletionBriefComment == null) {
            clang_getCompletionBriefComment = LibclangSymbolProvider.downcallHandle("clang_getCompletionBriefComment", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCompletionBriefComment"));
        }
        try {
            return (MemorySegment) clang_getCompletionBriefComment.invoke(segmentallocator, completion_string);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getCompletionBriefComment(SegmentAllocator segmentallocator, CXCompletionString completion_string) {
        return new CXString(clang_getCompletionBriefComment$Raw(segmentallocator, completion_string.operator().value()));
    }

    private static MethodHandle clang_getCursorCompletionString;

    private static MemorySegment clang_getCursorCompletionString$Raw(MemorySegment cursor) {
        if (clang_getCursorCompletionString == null) {
            clang_getCursorCompletionString = LibclangSymbolProvider.downcallHandle("clang_getCursorCompletionString", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorCompletionString"));
        }
        try {
            return (MemorySegment) clang_getCursorCompletionString.invoke(cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCompletionString clang_getCursorCompletionString(StructI<? extends CXCursor> cursor) {
        return new CXCompletionString(clang_getCursorCompletionString$Raw(cursor.operator().value()));
    }

    private static MethodHandle clang_getCompletionNumFixIts;

    private static int clang_getCompletionNumFixIts$Raw(MemorySegment results, int completion_index) {
        if (clang_getCompletionNumFixIts == null) {
            clang_getCompletionNumFixIts = LibclangSymbolProvider.downcallHandle("clang_getCompletionNumFixIts", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCompletionNumFixIts"));
        }
        try {
            return (int) clang_getCompletionNumFixIts.invoke(results, completion_index);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_getCompletionNumFixIts(PtrI<? extends StructI<? extends CXCodeCompleteResults>> results, I32I<?> completion_index) {
        return new I32(clang_getCompletionNumFixIts$Raw(results.operator().value(), completion_index.operator().value()));
    }

    private static MethodHandle clang_getCompletionFixIt;

    private static MemorySegment clang_getCompletionFixIt$Raw(SegmentAllocator segmentallocator, MemorySegment results, int completion_index, int fixit_index, MemorySegment replacement_range) {
        if (clang_getCompletionFixIt == null) {
            clang_getCompletionFixIt = LibclangSymbolProvider.downcallHandle("clang_getCompletionFixIt", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCompletionFixIt"));
        }
        try {
            return (MemorySegment) clang_getCompletionFixIt.invoke(segmentallocator, results, completion_index, fixit_index, replacement_range);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getCompletionFixIt(SegmentAllocator segmentallocator, PtrI<? extends StructI<? extends CXCodeCompleteResults>> results, I32I<?> completion_index, I32I<?> fixit_index, PtrI<? extends StructI<? extends CXSourceRange>> replacement_range) {
        return new CXString(clang_getCompletionFixIt$Raw(segmentallocator, results.operator().value(), completion_index.operator().value(), fixit_index.operator().value(), replacement_range.operator().value()));
    }

    private static MethodHandle clang_defaultCodeCompleteOptions;

    private static int clang_defaultCodeCompleteOptions$Raw() {
        if (clang_defaultCodeCompleteOptions == null) {
            clang_defaultCodeCompleteOptions = LibclangSymbolProvider.downcallHandle("clang_defaultCodeCompleteOptions", FunctionDescriptor.of(ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_defaultCodeCompleteOptions"));
        }
        try {
            return (int) clang_defaultCodeCompleteOptions.invoke();
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_defaultCodeCompleteOptions() {
        return new I32(clang_defaultCodeCompleteOptions$Raw());
    }

    private static MethodHandle clang_codeCompleteAt;

    private static MemorySegment clang_codeCompleteAt$Raw(MemorySegment TU, MemorySegment complete_filename, int complete_line, int complete_column, MemorySegment unsaved_files, int num_unsaved_files, int options) {
        if (clang_codeCompleteAt == null) {
            clang_codeCompleteAt = LibclangSymbolProvider.downcallHandle("clang_codeCompleteAt", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_codeCompleteAt"));
        }
        try {
            return (MemorySegment) clang_codeCompleteAt.invoke(TU, complete_filename, complete_line, complete_column, unsaved_files, num_unsaved_files, options);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<CXCodeCompleteResults> clang_codeCompleteAt(CXTranslationUnit TU, PtrI<? extends I8I<?>> complete_filename, I32I<?> complete_line, I32I<?> complete_column, PtrI<? extends StructI<? extends CXUnsavedFile>> unsaved_files, I32I<?> num_unsaved_files, I32I<?> options) {
        return new Ptr<CXCodeCompleteResults>(clang_codeCompleteAt$Raw(TU.operator().value(), complete_filename.operator().value(), complete_line.operator().value(), complete_column.operator().value(), unsaved_files.operator().value(), num_unsaved_files.operator().value(), options.operator().value()), CXCodeCompleteResults.OPERATIONS);
    }

    private static MethodHandle clang_sortCodeCompletionResults;

    private static void clang_sortCodeCompletionResults$Raw(MemorySegment Results, int NumResults) {
        if (clang_sortCodeCompletionResults == null) {
            clang_sortCodeCompletionResults = LibclangSymbolProvider.downcallHandle("clang_sortCodeCompletionResults", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_sortCodeCompletionResults"));
        }
        try {
            clang_sortCodeCompletionResults.invoke(Results, NumResults);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_sortCodeCompletionResults(PtrI<? extends StructI<? extends CXCompletionResult>> Results, I32I<?> NumResults) {
        clang_sortCodeCompletionResults$Raw(Results.operator().value(), NumResults.operator().value());
    }

    private static MethodHandle clang_disposeCodeCompleteResults;

    private static void clang_disposeCodeCompleteResults$Raw(MemorySegment Results) {
        if (clang_disposeCodeCompleteResults == null) {
            clang_disposeCodeCompleteResults = LibclangSymbolProvider.downcallHandle("clang_disposeCodeCompleteResults", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_disposeCodeCompleteResults"));
        }
        try {
            clang_disposeCodeCompleteResults.invoke(Results);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_disposeCodeCompleteResults(PtrI<? extends StructI<? extends CXCodeCompleteResults>> Results) {
        clang_disposeCodeCompleteResults$Raw(Results.operator().value());
    }

    private static MethodHandle clang_codeCompleteGetNumDiagnostics;

    private static int clang_codeCompleteGetNumDiagnostics$Raw(MemorySegment Results) {
        if (clang_codeCompleteGetNumDiagnostics == null) {
            clang_codeCompleteGetNumDiagnostics = LibclangSymbolProvider.downcallHandle("clang_codeCompleteGetNumDiagnostics", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_codeCompleteGetNumDiagnostics"));
        }
        try {
            return (int) clang_codeCompleteGetNumDiagnostics.invoke(Results);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_codeCompleteGetNumDiagnostics(PtrI<? extends StructI<? extends CXCodeCompleteResults>> Results) {
        return new I32(clang_codeCompleteGetNumDiagnostics$Raw(Results.operator().value()));
    }

    private static MethodHandle clang_codeCompleteGetDiagnostic;

    private static MemorySegment clang_codeCompleteGetDiagnostic$Raw(MemorySegment Results, int Index) {
        if (clang_codeCompleteGetDiagnostic == null) {
            clang_codeCompleteGetDiagnostic = LibclangSymbolProvider.downcallHandle("clang_codeCompleteGetDiagnostic", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_codeCompleteGetDiagnostic"));
        }
        try {
            return (MemorySegment) clang_codeCompleteGetDiagnostic.invoke(Results, Index);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXDiagnostic clang_codeCompleteGetDiagnostic(PtrI<? extends StructI<? extends CXCodeCompleteResults>> Results, I32I<?> Index) {
        return new CXDiagnostic(clang_codeCompleteGetDiagnostic$Raw(Results.operator().value(), Index.operator().value()));
    }

    private static MethodHandle clang_codeCompleteGetContexts;

    private static long clang_codeCompleteGetContexts$Raw(MemorySegment Results) {
        if (clang_codeCompleteGetContexts == null) {
            clang_codeCompleteGetContexts = LibclangSymbolProvider.downcallHandle("clang_codeCompleteGetContexts", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_codeCompleteGetContexts"));
        }
        try {
            return (long) clang_codeCompleteGetContexts.invoke(Results);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I64 clang_codeCompleteGetContexts(PtrI<? extends StructI<? extends CXCodeCompleteResults>> Results) {
        return new I64(clang_codeCompleteGetContexts$Raw(Results.operator().value()));
    }

    private static MethodHandle clang_codeCompleteGetContainerKind;

    private static int clang_codeCompleteGetContainerKind$Raw(MemorySegment Results, MemorySegment IsIncomplete) {
        if (clang_codeCompleteGetContainerKind == null) {
            clang_codeCompleteGetContainerKind = LibclangSymbolProvider.downcallHandle("clang_codeCompleteGetContainerKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_codeCompleteGetContainerKind"));
        }
        try {
            return (int) clang_codeCompleteGetContainerKind.invoke(Results, IsIncomplete);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXCursorKind clang_codeCompleteGetContainerKind(PtrI<? extends StructI<? extends CXCodeCompleteResults>> Results, PtrI<? extends I32I<?>> IsIncomplete) {
        return new CXCursorKind(clang_codeCompleteGetContainerKind$Raw(Results.operator().value(), IsIncomplete.operator().value()));
    }

    private static MethodHandle clang_codeCompleteGetContainerUSR;

    private static MemorySegment clang_codeCompleteGetContainerUSR$Raw(SegmentAllocator segmentallocator, MemorySegment Results) {
        if (clang_codeCompleteGetContainerUSR == null) {
            clang_codeCompleteGetContainerUSR = LibclangSymbolProvider.downcallHandle("clang_codeCompleteGetContainerUSR", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_codeCompleteGetContainerUSR"));
        }
        try {
            return (MemorySegment) clang_codeCompleteGetContainerUSR.invoke(segmentallocator, Results);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_codeCompleteGetContainerUSR(SegmentAllocator segmentallocator, PtrI<? extends StructI<? extends CXCodeCompleteResults>> Results) {
        return new CXString(clang_codeCompleteGetContainerUSR$Raw(segmentallocator, Results.operator().value()));
    }

    private static MethodHandle clang_codeCompleteGetObjCSelector;

    private static MemorySegment clang_codeCompleteGetObjCSelector$Raw(SegmentAllocator segmentallocator, MemorySegment Results) {
        if (clang_codeCompleteGetObjCSelector == null) {
            clang_codeCompleteGetObjCSelector = LibclangSymbolProvider.downcallHandle("clang_codeCompleteGetObjCSelector", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_codeCompleteGetObjCSelector"));
        }
        try {
            return (MemorySegment) clang_codeCompleteGetObjCSelector.invoke(segmentallocator, Results);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_codeCompleteGetObjCSelector(SegmentAllocator segmentallocator, PtrI<? extends StructI<? extends CXCodeCompleteResults>> Results) {
        return new CXString(clang_codeCompleteGetObjCSelector$Raw(segmentallocator, Results.operator().value()));
    }

    private static MethodHandle clang_getClangVersion;

    private static MemorySegment clang_getClangVersion$Raw(SegmentAllocator segmentallocator) {
        if (clang_getClangVersion == null) {
            clang_getClangVersion = LibclangSymbolProvider.downcallHandle("clang_getClangVersion", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getClangVersion"));
        }
        try {
            return (MemorySegment) clang_getClangVersion.invoke(segmentallocator);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getClangVersion(SegmentAllocator segmentallocator) {
        return new CXString(clang_getClangVersion$Raw(segmentallocator));
    }

    private static MethodHandle clang_toggleCrashRecovery;

    private static void clang_toggleCrashRecovery$Raw(int isEnabled) {
        if (clang_toggleCrashRecovery == null) {
            clang_toggleCrashRecovery = LibclangSymbolProvider.downcallHandle("clang_toggleCrashRecovery", FunctionDescriptor.ofVoid(ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_toggleCrashRecovery"));
        }
        try {
            clang_toggleCrashRecovery.invoke(isEnabled);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_toggleCrashRecovery(I32I<?> isEnabled) {
        clang_toggleCrashRecovery$Raw(isEnabled.operator().value());
    }

    private static MethodHandle clang_getInclusions;

    private static void clang_getInclusions$Raw(MemorySegment tu, MemorySegment visitor, MemorySegment client_data) {
        if (clang_getInclusions == null) {
            clang_getInclusions = LibclangSymbolProvider.downcallHandle("clang_getInclusions", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getInclusions"));
        }
        try {
            clang_getInclusions.invoke(tu, visitor, client_data);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_getInclusions(CXTranslationUnit tu, PtrI<? extends CXInclusionVisitor.Function> visitor, CXClientData client_data) {
        clang_getInclusions$Raw(tu.operator().value(), visitor.operator().value(), client_data.operator().value());
    }

    private static MethodHandle clang_Cursor_Evaluate;

    private static MemorySegment clang_Cursor_Evaluate$Raw(MemorySegment C) {
        if (clang_Cursor_Evaluate == null) {
            clang_Cursor_Evaluate = LibclangSymbolProvider.downcallHandle("clang_Cursor_Evaluate", FunctionDescriptor.of(ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_Cursor_Evaluate"));
        }
        try {
            return (MemorySegment) clang_Cursor_Evaluate.invoke(C);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXEvalResult clang_Cursor_Evaluate(StructI<? extends CXCursor> C) {
        return new CXEvalResult(clang_Cursor_Evaluate$Raw(C.operator().value()));
    }

    private static MethodHandle clang_EvalResult_getKind;

    private static int clang_EvalResult_getKind$Raw(MemorySegment E) {
        if (clang_EvalResult_getKind == null) {
            clang_EvalResult_getKind = LibclangSymbolProvider.downcallHandle("clang_EvalResult_getKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_EvalResult_getKind"));
        }
        try {
            return (int) clang_EvalResult_getKind.invoke(E);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXEvalResultKind clang_EvalResult_getKind(CXEvalResult E) {
        return new CXEvalResultKind(clang_EvalResult_getKind$Raw(E.operator().value()));
    }

    private static MethodHandle clang_EvalResult_getAsInt;

    private static int clang_EvalResult_getAsInt$Raw(MemorySegment E) {
        if (clang_EvalResult_getAsInt == null) {
            clang_EvalResult_getAsInt = LibclangSymbolProvider.downcallHandle("clang_EvalResult_getAsInt", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_EvalResult_getAsInt"));
        }
        try {
            return (int) clang_EvalResult_getAsInt.invoke(E);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_EvalResult_getAsInt(CXEvalResult E) {
        return new I32(clang_EvalResult_getAsInt$Raw(E.operator().value()));
    }

    private static MethodHandle clang_EvalResult_getAsLongLong;

    private static long clang_EvalResult_getAsLongLong$Raw(MemorySegment E) {
        if (clang_EvalResult_getAsLongLong == null) {
            clang_EvalResult_getAsLongLong = LibclangSymbolProvider.downcallHandle("clang_EvalResult_getAsLongLong", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_EvalResult_getAsLongLong"));
        }
        try {
            return (long) clang_EvalResult_getAsLongLong.invoke(E);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I64 clang_EvalResult_getAsLongLong(CXEvalResult E) {
        return new I64(clang_EvalResult_getAsLongLong$Raw(E.operator().value()));
    }

    private static MethodHandle clang_EvalResult_isUnsignedInt;

    private static int clang_EvalResult_isUnsignedInt$Raw(MemorySegment E) {
        if (clang_EvalResult_isUnsignedInt == null) {
            clang_EvalResult_isUnsignedInt = LibclangSymbolProvider.downcallHandle("clang_EvalResult_isUnsignedInt", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_EvalResult_isUnsignedInt"));
        }
        try {
            return (int) clang_EvalResult_isUnsignedInt.invoke(E);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_EvalResult_isUnsignedInt(CXEvalResult E) {
        return new I32(clang_EvalResult_isUnsignedInt$Raw(E.operator().value()));
    }

    private static MethodHandle clang_EvalResult_getAsUnsigned;

    private static long clang_EvalResult_getAsUnsigned$Raw(MemorySegment E) {
        if (clang_EvalResult_getAsUnsigned == null) {
            clang_EvalResult_getAsUnsigned = LibclangSymbolProvider.downcallHandle("clang_EvalResult_getAsUnsigned", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_EvalResult_getAsUnsigned"));
        }
        try {
            return (long) clang_EvalResult_getAsUnsigned.invoke(E);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I64 clang_EvalResult_getAsUnsigned(CXEvalResult E) {
        return new I64(clang_EvalResult_getAsUnsigned$Raw(E.operator().value()));
    }

    private static MethodHandle clang_EvalResult_getAsDouble;

    private static double clang_EvalResult_getAsDouble$Raw(MemorySegment E) {
        if (clang_EvalResult_getAsDouble == null) {
            clang_EvalResult_getAsDouble = LibclangSymbolProvider.downcallHandle("clang_EvalResult_getAsDouble", FunctionDescriptor.of(ValueLayout.JAVA_DOUBLE, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_EvalResult_getAsDouble"));
        }
        try {
            return (double) clang_EvalResult_getAsDouble.invoke(E);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static FP64 clang_EvalResult_getAsDouble(CXEvalResult E) {
        return new FP64(clang_EvalResult_getAsDouble$Raw(E.operator().value()));
    }

    private static MethodHandle clang_EvalResult_getAsStr;

    private static MemorySegment clang_EvalResult_getAsStr$Raw(MemorySegment E) {
        if (clang_EvalResult_getAsStr == null) {
            clang_EvalResult_getAsStr = LibclangSymbolProvider.downcallHandle("clang_EvalResult_getAsStr", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_EvalResult_getAsStr"));
        }
        try {
            return (MemorySegment) clang_EvalResult_getAsStr.invoke(E);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<I8> clang_EvalResult_getAsStr(CXEvalResult E) {
        return new Ptr<I8>(clang_EvalResult_getAsStr$Raw(E.operator().value()), I8.OPERATIONS);
    }

    private static MethodHandle clang_EvalResult_dispose;

    private static void clang_EvalResult_dispose$Raw(MemorySegment E) {
        if (clang_EvalResult_dispose == null) {
            clang_EvalResult_dispose = LibclangSymbolProvider.downcallHandle("clang_EvalResult_dispose", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_EvalResult_dispose"));
        }
        try {
            clang_EvalResult_dispose.invoke(E);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_EvalResult_dispose(CXEvalResult E) {
        clang_EvalResult_dispose$Raw(E.operator().value());
    }

    private static MethodHandle clang_getRemappings;

    private static MemorySegment clang_getRemappings$Raw(MemorySegment path) {
        if (clang_getRemappings == null) {
            clang_getRemappings = LibclangSymbolProvider.downcallHandle("clang_getRemappings", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getRemappings"));
        }
        try {
            return (MemorySegment) clang_getRemappings.invoke(path);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXRemapping clang_getRemappings(PtrI<? extends I8I<?>> path) {
        return new CXRemapping(clang_getRemappings$Raw(path.operator().value()));
    }

    private static MethodHandle clang_getRemappingsFromFileList;

    private static MemorySegment clang_getRemappingsFromFileList$Raw(MemorySegment filePaths, int numFiles) {
        if (clang_getRemappingsFromFileList == null) {
            clang_getRemappingsFromFileList = LibclangSymbolProvider.downcallHandle("clang_getRemappingsFromFileList", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getRemappingsFromFileList"));
        }
        try {
            return (MemorySegment) clang_getRemappingsFromFileList.invoke(filePaths, numFiles);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXRemapping clang_getRemappingsFromFileList(PtrI<? extends PtrI<? extends I8I<?>>> filePaths, I32I<?> numFiles) {
        return new CXRemapping(clang_getRemappingsFromFileList$Raw(filePaths.operator().value(), numFiles.operator().value()));
    }

    private static MethodHandle clang_remap_getNumFiles;

    private static int clang_remap_getNumFiles$Raw(MemorySegment arg0) {
        if (clang_remap_getNumFiles == null) {
            clang_remap_getNumFiles = LibclangSymbolProvider.downcallHandle("clang_remap_getNumFiles", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_remap_getNumFiles"));
        }
        try {
            return (int) clang_remap_getNumFiles.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_remap_getNumFiles(CXRemapping arg0) {
        return new I32(clang_remap_getNumFiles$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_remap_getFilenames;

    private static void clang_remap_getFilenames$Raw(MemorySegment arg0, int index, MemorySegment original, MemorySegment transformed) {
        if (clang_remap_getFilenames == null) {
            clang_remap_getFilenames = LibclangSymbolProvider.downcallHandle("clang_remap_getFilenames", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_remap_getFilenames"));
        }
        try {
            clang_remap_getFilenames.invoke(arg0, index, original, transformed);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_remap_getFilenames(CXRemapping arg0, I32I<?> index, PtrI<? extends StructI<? extends CXString>> original, PtrI<? extends StructI<? extends CXString>> transformed) {
        clang_remap_getFilenames$Raw(arg0.operator().value(), index.operator().value(), original.operator().value(), transformed.operator().value());
    }

    private static MethodHandle clang_remap_dispose;

    private static void clang_remap_dispose$Raw(MemorySegment arg0) {
        if (clang_remap_dispose == null) {
            clang_remap_dispose = LibclangSymbolProvider.downcallHandle("clang_remap_dispose", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_remap_dispose"));
        }
        try {
            clang_remap_dispose.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_remap_dispose(CXRemapping arg0) {
        clang_remap_dispose$Raw(arg0.operator().value());
    }

    private static MethodHandle clang_findReferencesInFile;

    private static int clang_findReferencesInFile$Raw(MemorySegment cursor, MemorySegment file, MemorySegment visitor) {
        if (clang_findReferencesInFile == null) {
            clang_findReferencesInFile = LibclangSymbolProvider.downcallHandle("clang_findReferencesInFile", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_findReferencesInFile"));
        }
        try {
            return (int) clang_findReferencesInFile.invoke(cursor, file, visitor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXResult clang_findReferencesInFile(StructI<? extends CXCursor> cursor, CXFile file, StructI<? extends CXCursorAndRangeVisitor> visitor) {
        return new CXResult(clang_findReferencesInFile$Raw(cursor.operator().value(), file.operator().value(), visitor.operator().value()));
    }

    private static MethodHandle clang_findIncludesInFile;

    private static int clang_findIncludesInFile$Raw(MemorySegment TU, MemorySegment file, MemorySegment visitor) {
        if (clang_findIncludesInFile == null) {
            clang_findIncludesInFile = LibclangSymbolProvider.downcallHandle("clang_findIncludesInFile", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_findIncludesInFile"));
        }
        try {
            return (int) clang_findIncludesInFile.invoke(TU, file, visitor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXResult clang_findIncludesInFile(CXTranslationUnit TU, CXFile file, StructI<? extends CXCursorAndRangeVisitor> visitor) {
        return new CXResult(clang_findIncludesInFile$Raw(TU.operator().value(), file.operator().value(), visitor.operator().value()));
    }

    private static MethodHandle clang_findReferencesInFileWithBlock;

    private static int clang_findReferencesInFileWithBlock$Raw(MemorySegment arg0, MemorySegment arg1, MemorySegment arg2) {
        if (clang_findReferencesInFileWithBlock == null) {
            clang_findReferencesInFileWithBlock = LibclangSymbolProvider.downcallHandle("clang_findReferencesInFileWithBlock", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_findReferencesInFileWithBlock"));
        }
        try {
            return (int) clang_findReferencesInFileWithBlock.invoke(arg0, arg1, arg2);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXResult clang_findReferencesInFileWithBlock(StructI<? extends CXCursor> arg0, CXFile arg1, CXCursorAndRangeVisitorBlock arg2) {
        return new CXResult(clang_findReferencesInFileWithBlock$Raw(arg0.operator().value(), arg1.operator().value(), arg2.operator().value()));
    }

    private static MethodHandle clang_findIncludesInFileWithBlock;

    private static int clang_findIncludesInFileWithBlock$Raw(MemorySegment arg0, MemorySegment arg1, MemorySegment arg2) {
        if (clang_findIncludesInFileWithBlock == null) {
            clang_findIncludesInFileWithBlock = LibclangSymbolProvider.downcallHandle("clang_findIncludesInFileWithBlock", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_findIncludesInFileWithBlock"));
        }
        try {
            return (int) clang_findIncludesInFileWithBlock.invoke(arg0, arg1, arg2);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXResult clang_findIncludesInFileWithBlock(CXTranslationUnit arg0, CXFile arg1, CXCursorAndRangeVisitorBlock arg2) {
        return new CXResult(clang_findIncludesInFileWithBlock$Raw(arg0.operator().value(), arg1.operator().value(), arg2.operator().value()));
    }

    private static MethodHandle clang_index_isEntityObjCContainerKind;

    private static int clang_index_isEntityObjCContainerKind$Raw(int arg0) {
        if (clang_index_isEntityObjCContainerKind == null) {
            clang_index_isEntityObjCContainerKind = LibclangSymbolProvider.downcallHandle("clang_index_isEntityObjCContainerKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_index_isEntityObjCContainerKind"));
        }
        try {
            return (int) clang_index_isEntityObjCContainerKind.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_index_isEntityObjCContainerKind(I32I<? extends CXIdxEntityKind> arg0) {
        return new I32(clang_index_isEntityObjCContainerKind$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_index_getObjCContainerDeclInfo;

    private static MemorySegment clang_index_getObjCContainerDeclInfo$Raw(MemorySegment arg0) {
        if (clang_index_getObjCContainerDeclInfo == null) {
            clang_index_getObjCContainerDeclInfo = LibclangSymbolProvider.downcallHandle("clang_index_getObjCContainerDeclInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_index_getObjCContainerDeclInfo"));
        }
        try {
            return (MemorySegment) clang_index_getObjCContainerDeclInfo.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<CXIdxObjCContainerDeclInfo> clang_index_getObjCContainerDeclInfo(PtrI<? extends StructI<? extends CXIdxDeclInfo>> arg0) {
        return new Ptr<CXIdxObjCContainerDeclInfo>(clang_index_getObjCContainerDeclInfo$Raw(arg0.operator().value()), CXIdxObjCContainerDeclInfo.OPERATIONS);
    }

    private static MethodHandle clang_index_getObjCInterfaceDeclInfo;

    private static MemorySegment clang_index_getObjCInterfaceDeclInfo$Raw(MemorySegment arg0) {
        if (clang_index_getObjCInterfaceDeclInfo == null) {
            clang_index_getObjCInterfaceDeclInfo = LibclangSymbolProvider.downcallHandle("clang_index_getObjCInterfaceDeclInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_index_getObjCInterfaceDeclInfo"));
        }
        try {
            return (MemorySegment) clang_index_getObjCInterfaceDeclInfo.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<CXIdxObjCInterfaceDeclInfo> clang_index_getObjCInterfaceDeclInfo(PtrI<? extends StructI<? extends CXIdxDeclInfo>> arg0) {
        return new Ptr<CXIdxObjCInterfaceDeclInfo>(clang_index_getObjCInterfaceDeclInfo$Raw(arg0.operator().value()), CXIdxObjCInterfaceDeclInfo.OPERATIONS);
    }

    private static MethodHandle clang_index_getObjCCategoryDeclInfo;

    private static MemorySegment clang_index_getObjCCategoryDeclInfo$Raw(MemorySegment arg0) {
        if (clang_index_getObjCCategoryDeclInfo == null) {
            clang_index_getObjCCategoryDeclInfo = LibclangSymbolProvider.downcallHandle("clang_index_getObjCCategoryDeclInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_index_getObjCCategoryDeclInfo"));
        }
        try {
            return (MemorySegment) clang_index_getObjCCategoryDeclInfo.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<CXIdxObjCCategoryDeclInfo> clang_index_getObjCCategoryDeclInfo(PtrI<? extends StructI<? extends CXIdxDeclInfo>> arg0) {
        return new Ptr<CXIdxObjCCategoryDeclInfo>(clang_index_getObjCCategoryDeclInfo$Raw(arg0.operator().value()), CXIdxObjCCategoryDeclInfo.OPERATIONS);
    }

    private static MethodHandle clang_index_getObjCProtocolRefListInfo;

    private static MemorySegment clang_index_getObjCProtocolRefListInfo$Raw(MemorySegment arg0) {
        if (clang_index_getObjCProtocolRefListInfo == null) {
            clang_index_getObjCProtocolRefListInfo = LibclangSymbolProvider.downcallHandle("clang_index_getObjCProtocolRefListInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_index_getObjCProtocolRefListInfo"));
        }
        try {
            return (MemorySegment) clang_index_getObjCProtocolRefListInfo.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<CXIdxObjCProtocolRefListInfo> clang_index_getObjCProtocolRefListInfo(PtrI<? extends StructI<? extends CXIdxDeclInfo>> arg0) {
        return new Ptr<CXIdxObjCProtocolRefListInfo>(clang_index_getObjCProtocolRefListInfo$Raw(arg0.operator().value()), CXIdxObjCProtocolRefListInfo.OPERATIONS);
    }

    private static MethodHandle clang_index_getObjCPropertyDeclInfo;

    private static MemorySegment clang_index_getObjCPropertyDeclInfo$Raw(MemorySegment arg0) {
        if (clang_index_getObjCPropertyDeclInfo == null) {
            clang_index_getObjCPropertyDeclInfo = LibclangSymbolProvider.downcallHandle("clang_index_getObjCPropertyDeclInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_index_getObjCPropertyDeclInfo"));
        }
        try {
            return (MemorySegment) clang_index_getObjCPropertyDeclInfo.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<CXIdxObjCPropertyDeclInfo> clang_index_getObjCPropertyDeclInfo(PtrI<? extends StructI<? extends CXIdxDeclInfo>> arg0) {
        return new Ptr<CXIdxObjCPropertyDeclInfo>(clang_index_getObjCPropertyDeclInfo$Raw(arg0.operator().value()), CXIdxObjCPropertyDeclInfo.OPERATIONS);
    }

    private static MethodHandle clang_index_getIBOutletCollectionAttrInfo;

    private static MemorySegment clang_index_getIBOutletCollectionAttrInfo$Raw(MemorySegment arg0) {
        if (clang_index_getIBOutletCollectionAttrInfo == null) {
            clang_index_getIBOutletCollectionAttrInfo = LibclangSymbolProvider.downcallHandle("clang_index_getIBOutletCollectionAttrInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_index_getIBOutletCollectionAttrInfo"));
        }
        try {
            return (MemorySegment) clang_index_getIBOutletCollectionAttrInfo.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<CXIdxIBOutletCollectionAttrInfo> clang_index_getIBOutletCollectionAttrInfo(PtrI<? extends StructI<? extends CXIdxAttrInfo>> arg0) {
        return new Ptr<CXIdxIBOutletCollectionAttrInfo>(clang_index_getIBOutletCollectionAttrInfo$Raw(arg0.operator().value()), CXIdxIBOutletCollectionAttrInfo.OPERATIONS);
    }

    private static MethodHandle clang_index_getCXXClassDeclInfo;

    private static MemorySegment clang_index_getCXXClassDeclInfo$Raw(MemorySegment arg0) {
        if (clang_index_getCXXClassDeclInfo == null) {
            clang_index_getCXXClassDeclInfo = LibclangSymbolProvider.downcallHandle("clang_index_getCXXClassDeclInfo", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_index_getCXXClassDeclInfo"));
        }
        try {
            return (MemorySegment) clang_index_getCXXClassDeclInfo.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static Ptr<CXIdxCXXClassDeclInfo> clang_index_getCXXClassDeclInfo(PtrI<? extends StructI<? extends CXIdxDeclInfo>> arg0) {
        return new Ptr<CXIdxCXXClassDeclInfo>(clang_index_getCXXClassDeclInfo$Raw(arg0.operator().value()), CXIdxCXXClassDeclInfo.OPERATIONS);
    }

    private static MethodHandle clang_index_getClientContainer;

    private static MemorySegment clang_index_getClientContainer$Raw(MemorySegment arg0) {
        if (clang_index_getClientContainer == null) {
            clang_index_getClientContainer = LibclangSymbolProvider.downcallHandle("clang_index_getClientContainer", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_index_getClientContainer"));
        }
        try {
            return (MemorySegment) clang_index_getClientContainer.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXIdxClientContainer clang_index_getClientContainer(PtrI<? extends StructI<? extends CXIdxContainerInfo>> arg0) {
        return new CXIdxClientContainer(clang_index_getClientContainer$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_index_setClientContainer;

    private static void clang_index_setClientContainer$Raw(MemorySegment arg0, MemorySegment arg1) {
        if (clang_index_setClientContainer == null) {
            clang_index_setClientContainer = LibclangSymbolProvider.downcallHandle("clang_index_setClientContainer", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_index_setClientContainer"));
        }
        try {
            clang_index_setClientContainer.invoke(arg0, arg1);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_index_setClientContainer(PtrI<? extends StructI<? extends CXIdxContainerInfo>> arg0, CXIdxClientContainer arg1) {
        clang_index_setClientContainer$Raw(arg0.operator().value(), arg1.operator().value());
    }

    private static MethodHandle clang_index_getClientEntity;

    private static MemorySegment clang_index_getClientEntity$Raw(MemorySegment arg0) {
        if (clang_index_getClientEntity == null) {
            clang_index_getClientEntity = LibclangSymbolProvider.downcallHandle("clang_index_getClientEntity", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_index_getClientEntity"));
        }
        try {
            return (MemorySegment) clang_index_getClientEntity.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXIdxClientEntity clang_index_getClientEntity(PtrI<? extends StructI<? extends CXIdxEntityInfo>> arg0) {
        return new CXIdxClientEntity(clang_index_getClientEntity$Raw(arg0.operator().value()));
    }

    private static MethodHandle clang_index_setClientEntity;

    private static void clang_index_setClientEntity$Raw(MemorySegment arg0, MemorySegment arg1) {
        if (clang_index_setClientEntity == null) {
            clang_index_setClientEntity = LibclangSymbolProvider.downcallHandle("clang_index_setClientEntity", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_index_setClientEntity"));
        }
        try {
            clang_index_setClientEntity.invoke(arg0, arg1);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_index_setClientEntity(PtrI<? extends StructI<? extends CXIdxEntityInfo>> arg0, CXIdxClientEntity arg1) {
        clang_index_setClientEntity$Raw(arg0.operator().value(), arg1.operator().value());
    }

    private static MethodHandle clang_IndexAction_create;

    private static MemorySegment clang_IndexAction_create$Raw(MemorySegment CIdx) {
        if (clang_IndexAction_create == null) {
            clang_IndexAction_create = LibclangSymbolProvider.downcallHandle("clang_IndexAction_create", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_IndexAction_create"));
        }
        try {
            return (MemorySegment) clang_IndexAction_create.invoke(CIdx);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXIndexAction clang_IndexAction_create(CXIndex CIdx) {
        return new CXIndexAction(clang_IndexAction_create$Raw(CIdx.operator().value()));
    }

    private static MethodHandle clang_IndexAction_dispose;

    private static void clang_IndexAction_dispose$Raw(MemorySegment arg0) {
        if (clang_IndexAction_dispose == null) {
            clang_IndexAction_dispose = LibclangSymbolProvider.downcallHandle("clang_IndexAction_dispose", FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_IndexAction_dispose"));
        }
        try {
            clang_IndexAction_dispose.invoke(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_IndexAction_dispose(CXIndexAction arg0) {
        clang_IndexAction_dispose$Raw(arg0.operator().value());
    }

    private static MethodHandle clang_indexSourceFile;

    private static int clang_indexSourceFile$Raw(MemorySegment arg0, MemorySegment client_data, MemorySegment index_callbacks, int index_callbacks_size, int index_options, MemorySegment source_filename, MemorySegment command_line_args, int num_command_line_args, MemorySegment unsaved_files, int num_unsaved_files, MemorySegment out_TU, int TU_options) {
        if (clang_indexSourceFile == null) {
            clang_indexSourceFile = LibclangSymbolProvider.downcallHandle("clang_indexSourceFile", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_indexSourceFile"));
        }
        try {
            return (int) clang_indexSourceFile.invoke(arg0, client_data, index_callbacks, index_callbacks_size, index_options, source_filename, command_line_args, num_command_line_args, unsaved_files, num_unsaved_files, out_TU, TU_options);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_indexSourceFile(CXIndexAction arg0, CXClientData client_data, PtrI<? extends StructI<? extends IndexerCallbacks>> index_callbacks, I32I<?> index_callbacks_size, I32I<?> index_options, PtrI<? extends I8I<?>> source_filename, PtrI<? extends PtrI<? extends I8I<?>>> command_line_args, I32I<?> num_command_line_args, PtrI<? extends StructI<? extends CXUnsavedFile>> unsaved_files, I32I<?> num_unsaved_files, PtrI<? extends CXTranslationUnit> out_TU, I32I<?> TU_options) {
        return new I32(clang_indexSourceFile$Raw(arg0.operator().value(), client_data.operator().value(), index_callbacks.operator().value(), index_callbacks_size.operator().value(), index_options.operator().value(), source_filename.operator().value(), command_line_args.operator().value(), num_command_line_args.operator().value(), unsaved_files.operator().value(), num_unsaved_files.operator().value(), out_TU.operator().value(), TU_options.operator().value()));
    }

    private static MethodHandle clang_indexSourceFileFullArgv;

    private static int clang_indexSourceFileFullArgv$Raw(MemorySegment arg0, MemorySegment client_data, MemorySegment index_callbacks, int index_callbacks_size, int index_options, MemorySegment source_filename, MemorySegment command_line_args, int num_command_line_args, MemorySegment unsaved_files, int num_unsaved_files, MemorySegment out_TU, int TU_options) {
        if (clang_indexSourceFileFullArgv == null) {
            clang_indexSourceFileFullArgv = LibclangSymbolProvider.downcallHandle("clang_indexSourceFileFullArgv", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_indexSourceFileFullArgv"));
        }
        try {
            return (int) clang_indexSourceFileFullArgv.invoke(arg0, client_data, index_callbacks, index_callbacks_size, index_options, source_filename, command_line_args, num_command_line_args, unsaved_files, num_unsaved_files, out_TU, TU_options);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_indexSourceFileFullArgv(CXIndexAction arg0, CXClientData client_data, PtrI<? extends StructI<? extends IndexerCallbacks>> index_callbacks, I32I<?> index_callbacks_size, I32I<?> index_options, PtrI<? extends I8I<?>> source_filename, PtrI<? extends PtrI<? extends I8I<?>>> command_line_args, I32I<?> num_command_line_args, PtrI<? extends StructI<? extends CXUnsavedFile>> unsaved_files, I32I<?> num_unsaved_files, PtrI<? extends CXTranslationUnit> out_TU, I32I<?> TU_options) {
        return new I32(clang_indexSourceFileFullArgv$Raw(arg0.operator().value(), client_data.operator().value(), index_callbacks.operator().value(), index_callbacks_size.operator().value(), index_options.operator().value(), source_filename.operator().value(), command_line_args.operator().value(), num_command_line_args.operator().value(), unsaved_files.operator().value(), num_unsaved_files.operator().value(), out_TU.operator().value(), TU_options.operator().value()));
    }

    private static MethodHandle clang_indexTranslationUnit;

    private static int clang_indexTranslationUnit$Raw(MemorySegment arg0, MemorySegment client_data, MemorySegment index_callbacks, int index_callbacks_size, int index_options, MemorySegment arg5) {
        if (clang_indexTranslationUnit == null) {
            clang_indexTranslationUnit = LibclangSymbolProvider.downcallHandle("clang_indexTranslationUnit", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_indexTranslationUnit"));
        }
        try {
            return (int) clang_indexTranslationUnit.invoke(arg0, client_data, index_callbacks, index_callbacks_size, index_options, arg5);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_indexTranslationUnit(CXIndexAction arg0, CXClientData client_data, PtrI<? extends StructI<? extends IndexerCallbacks>> index_callbacks, I32I<?> index_callbacks_size, I32I<?> index_options, CXTranslationUnit arg5) {
        return new I32(clang_indexTranslationUnit$Raw(arg0.operator().value(), client_data.operator().value(), index_callbacks.operator().value(), index_callbacks_size.operator().value(), index_options.operator().value(), arg5.operator().value()));
    }

    private static MethodHandle clang_indexLoc_getFileLocation;

    private static void clang_indexLoc_getFileLocation$Raw(MemorySegment loc, MemorySegment indexFile, MemorySegment file, MemorySegment line, MemorySegment column, MemorySegment offset) {
        if (clang_indexLoc_getFileLocation == null) {
            clang_indexLoc_getFileLocation = LibclangSymbolProvider.downcallHandle("clang_indexLoc_getFileLocation", FunctionDescriptor.ofVoid(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_indexLoc_getFileLocation"));
        }
        try {
            clang_indexLoc_getFileLocation.invoke(loc, indexFile, file, line, column, offset);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static void clang_indexLoc_getFileLocation(StructI<? extends CXIdxLoc> loc, PtrI<? extends CXIdxClientFile> indexFile, PtrI<? extends CXFile> file, PtrI<? extends I32I<?>> line, PtrI<? extends I32I<?>> column, PtrI<? extends I32I<?>> offset) {
        clang_indexLoc_getFileLocation$Raw(loc.operator().value(), indexFile.operator().value(), file.operator().value(), line.operator().value(), column.operator().value(), offset.operator().value());
    }

    private static MethodHandle clang_indexLoc_getCXSourceLocation;

    private static MemorySegment clang_indexLoc_getCXSourceLocation$Raw(SegmentAllocator segmentallocator, MemorySegment loc) {
        if (clang_indexLoc_getCXSourceLocation == null) {
            clang_indexLoc_getCXSourceLocation = LibclangSymbolProvider.downcallHandle("clang_indexLoc_getCXSourceLocation", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_indexLoc_getCXSourceLocation"));
        }
        try {
            return (MemorySegment) clang_indexLoc_getCXSourceLocation.invoke(segmentallocator, loc);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXSourceLocation clang_indexLoc_getCXSourceLocation(SegmentAllocator segmentallocator, StructI<? extends CXIdxLoc> loc) {
        return new CXSourceLocation(clang_indexLoc_getCXSourceLocation$Raw(segmentallocator, loc.operator().value()));
    }

    private static MethodHandle clang_Type_visitFields;

    private static int clang_Type_visitFields$Raw(MemorySegment T, MemorySegment visitor, MemorySegment client_data) {
        if (clang_Type_visitFields == null) {
            clang_Type_visitFields = LibclangSymbolProvider.downcallHandle("clang_Type_visitFields", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS, ValueLayout.ADDRESS)).orElseThrow(() -> new Utils.SymbolNotFound("clang_Type_visitFields"));
        }
        try {
            return (int) clang_Type_visitFields.invoke(T, visitor, client_data);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static I32 clang_Type_visitFields(StructI<? extends CXType> T, PtrI<? extends CXFieldVisitor.Function> visitor, CXClientData client_data) {
        return new I32(clang_Type_visitFields$Raw(T.operator().value(), visitor.operator().value(), client_data.operator().value()));
    }

    private static MethodHandle clang_getBinaryOperatorKindSpelling;

    private static MemorySegment clang_getBinaryOperatorKindSpelling$Raw(SegmentAllocator segmentallocator, int kind) {
        if (clang_getBinaryOperatorKindSpelling == null) {
            clang_getBinaryOperatorKindSpelling = LibclangSymbolProvider.downcallHandle("clang_getBinaryOperatorKindSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getBinaryOperatorKindSpelling"));
        }
        try {
            return (MemorySegment) clang_getBinaryOperatorKindSpelling.invoke(segmentallocator, kind);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getBinaryOperatorKindSpelling(SegmentAllocator segmentallocator, I32I<? extends CXBinaryOperatorKind> kind) {
        return new CXString(clang_getBinaryOperatorKindSpelling$Raw(segmentallocator, kind.operator().value()));
    }

    private static MethodHandle clang_getCursorBinaryOperatorKind;

    private static int clang_getCursorBinaryOperatorKind$Raw(MemorySegment cursor) {
        if (clang_getCursorBinaryOperatorKind == null) {
            clang_getCursorBinaryOperatorKind = LibclangSymbolProvider.downcallHandle("clang_getCursorBinaryOperatorKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorBinaryOperatorKind"));
        }
        try {
            return (int) clang_getCursorBinaryOperatorKind.invoke(cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXBinaryOperatorKind clang_getCursorBinaryOperatorKind(StructI<? extends CXCursor> cursor) {
        return new CXBinaryOperatorKind(clang_getCursorBinaryOperatorKind$Raw(cursor.operator().value()));
    }

    private static MethodHandle clang_getUnaryOperatorKindSpelling;

    private static MemorySegment clang_getUnaryOperatorKindSpelling$Raw(SegmentAllocator segmentallocator, int kind) {
        if (clang_getUnaryOperatorKindSpelling == null) {
            clang_getUnaryOperatorKindSpelling = LibclangSymbolProvider.downcallHandle("clang_getUnaryOperatorKindSpelling", FunctionDescriptor.of(MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE)), ValueLayout.JAVA_INT)).orElseThrow(() -> new Utils.SymbolNotFound("clang_getUnaryOperatorKindSpelling"));
        }
        try {
            return (MemorySegment) clang_getUnaryOperatorKindSpelling.invoke(segmentallocator, kind);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXString clang_getUnaryOperatorKindSpelling(SegmentAllocator segmentallocator, I32I<? extends CXUnaryOperatorKind> kind) {
        return new CXString(clang_getUnaryOperatorKindSpelling$Raw(segmentallocator, kind.operator().value()));
    }

    private static MethodHandle clang_getCursorUnaryOperatorKind;

    private static int clang_getCursorUnaryOperatorKind$Raw(MemorySegment cursor) {
        if (clang_getCursorUnaryOperatorKind == null) {
            clang_getCursorUnaryOperatorKind = LibclangSymbolProvider.downcallHandle("clang_getCursorUnaryOperatorKind", FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)))).orElseThrow(() -> new Utils.SymbolNotFound("clang_getCursorUnaryOperatorKind"));
        }
        try {
            return (int) clang_getCursorUnaryOperatorKind.invoke(cursor);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public static CXUnaryOperatorKind clang_getCursorUnaryOperatorKind(StructI<? extends CXCursor> cursor) {
        return new CXUnaryOperatorKind(clang_getCursorUnaryOperatorKind$Raw(cursor.operator().value()));
    }
}