package libclang;

import libclang.shared.Pointer;
import libclang.shared.VI32List;
import libclang.shared.values.VI32Basic;

import java.lang.foreign.SegmentAllocator;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class LibclangEnums {
    private LibclangEnums() {
    }


    private static <E extends VI32Basic<?>> Optional<String> enumToString(Class<?> klass, E e) {
        return Arrays.stream(klass.getFields()).map(field -> {
            try {
                return (Modifier.isStatic(field.getModifiers()) && e.equals(field.get(null))) ? field.getName() : null;
            } catch (IllegalAccessException ex) {
                return null;
            }
        }).filter(Objects::nonNull).findFirst();
    }

    public static final class CXErrorCode extends VI32Basic<CXErrorCode> {
        public CXErrorCode(int e) {
            super(e);
        }

        public CXErrorCode(Pointer<CXErrorCode> e) {
            super(e);
        }

        public CXErrorCode(CXErrorCode e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXErrorCode> list(Pointer<CXErrorCode> ptr) {
            return new VI32List<>(ptr, CXErrorCode::new);
        }

        public static VI32List<CXErrorCode> list(Pointer<CXErrorCode> ptr, long length) {
            return new VI32List<>(ptr, length, CXErrorCode::new);
        }

        public static VI32List<CXErrorCode> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXErrorCode::new);
        }

        public static VI32List<CXErrorCode> list(SegmentAllocator allocator, CXErrorCode[] c) {
            return new VI32List<>(allocator, c, CXErrorCode::new);
        }

        public static VI32List<CXErrorCode> list(SegmentAllocator allocator, Collection<CXErrorCode> c) {
            return new VI32List<>(allocator, c, CXErrorCode::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXErrorCode e) {
            return LibclangEnums.enumToString(CXErrorCode.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXErrorCode that && that.value().intValue() == value().intValue();
        }


        public static final CXErrorCode CXError_Success = new CXErrorCode(0);
        public static final CXErrorCode CXError_Failure = new CXErrorCode(1);
        public static final CXErrorCode CXError_Crashed = new CXErrorCode(2);
        public static final CXErrorCode CXError_InvalidArguments = new CXErrorCode(3);
        public static final CXErrorCode CXError_ASTReadError = new CXErrorCode(4);

    }

    public static final class CXDiagnosticSeverity extends VI32Basic<CXDiagnosticSeverity> {
        public CXDiagnosticSeverity(int e) {
            super(e);
        }

        public CXDiagnosticSeverity(Pointer<CXDiagnosticSeverity> e) {
            super(e);
        }

        public CXDiagnosticSeverity(CXDiagnosticSeverity e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXDiagnosticSeverity> list(Pointer<CXDiagnosticSeverity> ptr) {
            return new VI32List<>(ptr, CXDiagnosticSeverity::new);
        }

        public static VI32List<CXDiagnosticSeverity> list(Pointer<CXDiagnosticSeverity> ptr, long length) {
            return new VI32List<>(ptr, length, CXDiagnosticSeverity::new);
        }

        public static VI32List<CXDiagnosticSeverity> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXDiagnosticSeverity::new);
        }

        public static VI32List<CXDiagnosticSeverity> list(SegmentAllocator allocator, CXDiagnosticSeverity[] c) {
            return new VI32List<>(allocator, c, CXDiagnosticSeverity::new);
        }

        public static VI32List<CXDiagnosticSeverity> list(SegmentAllocator allocator, Collection<CXDiagnosticSeverity> c) {
            return new VI32List<>(allocator, c, CXDiagnosticSeverity::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXDiagnosticSeverity e) {
            return LibclangEnums.enumToString(CXDiagnosticSeverity.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXDiagnosticSeverity that && that.value().intValue() == value().intValue();
        }


        public static final CXDiagnosticSeverity CXDiagnostic_Ignored = new CXDiagnosticSeverity(0);
        public static final CXDiagnosticSeverity CXDiagnostic_Note = new CXDiagnosticSeverity(1);
        public static final CXDiagnosticSeverity CXDiagnostic_Warning = new CXDiagnosticSeverity(2);
        public static final CXDiagnosticSeverity CXDiagnostic_Error = new CXDiagnosticSeverity(3);
        public static final CXDiagnosticSeverity CXDiagnostic_Fatal = new CXDiagnosticSeverity(4);

    }

    public static final class CXLoadDiag_Error extends VI32Basic<CXLoadDiag_Error> {
        public CXLoadDiag_Error(int e) {
            super(e);
        }

        public CXLoadDiag_Error(Pointer<CXLoadDiag_Error> e) {
            super(e);
        }

        public CXLoadDiag_Error(CXLoadDiag_Error e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXLoadDiag_Error> list(Pointer<CXLoadDiag_Error> ptr) {
            return new VI32List<>(ptr, CXLoadDiag_Error::new);
        }

        public static VI32List<CXLoadDiag_Error> list(Pointer<CXLoadDiag_Error> ptr, long length) {
            return new VI32List<>(ptr, length, CXLoadDiag_Error::new);
        }

        public static VI32List<CXLoadDiag_Error> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXLoadDiag_Error::new);
        }

        public static VI32List<CXLoadDiag_Error> list(SegmentAllocator allocator, CXLoadDiag_Error[] c) {
            return new VI32List<>(allocator, c, CXLoadDiag_Error::new);
        }

        public static VI32List<CXLoadDiag_Error> list(SegmentAllocator allocator, Collection<CXLoadDiag_Error> c) {
            return new VI32List<>(allocator, c, CXLoadDiag_Error::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXLoadDiag_Error e) {
            return LibclangEnums.enumToString(CXLoadDiag_Error.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXLoadDiag_Error that && that.value().intValue() == value().intValue();
        }


        public static final CXLoadDiag_Error CXLoadDiag_None = new CXLoadDiag_Error(0);
        public static final CXLoadDiag_Error CXLoadDiag_Unknown = new CXLoadDiag_Error(1);
        public static final CXLoadDiag_Error CXLoadDiag_CannotLoad = new CXLoadDiag_Error(2);
        public static final CXLoadDiag_Error CXLoadDiag_InvalidFile = new CXLoadDiag_Error(3);

    }

    public static final class CXDiagnosticDisplayOptions extends VI32Basic<CXDiagnosticDisplayOptions> {
        public CXDiagnosticDisplayOptions(int e) {
            super(e);
        }

        public CXDiagnosticDisplayOptions(Pointer<CXDiagnosticDisplayOptions> e) {
            super(e);
        }

        public CXDiagnosticDisplayOptions(CXDiagnosticDisplayOptions e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXDiagnosticDisplayOptions> list(Pointer<CXDiagnosticDisplayOptions> ptr) {
            return new VI32List<>(ptr, CXDiagnosticDisplayOptions::new);
        }

        public static VI32List<CXDiagnosticDisplayOptions> list(Pointer<CXDiagnosticDisplayOptions> ptr, long length) {
            return new VI32List<>(ptr, length, CXDiagnosticDisplayOptions::new);
        }

        public static VI32List<CXDiagnosticDisplayOptions> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXDiagnosticDisplayOptions::new);
        }

        public static VI32List<CXDiagnosticDisplayOptions> list(SegmentAllocator allocator, CXDiagnosticDisplayOptions[] c) {
            return new VI32List<>(allocator, c, CXDiagnosticDisplayOptions::new);
        }

        public static VI32List<CXDiagnosticDisplayOptions> list(SegmentAllocator allocator, Collection<CXDiagnosticDisplayOptions> c) {
            return new VI32List<>(allocator, c, CXDiagnosticDisplayOptions::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXDiagnosticDisplayOptions e) {
            return LibclangEnums.enumToString(CXDiagnosticDisplayOptions.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXDiagnosticDisplayOptions that && that.value().intValue() == value().intValue();
        }


        public static final CXDiagnosticDisplayOptions CXDiagnostic_DisplaySourceLocation = new CXDiagnosticDisplayOptions(1);
        public static final CXDiagnosticDisplayOptions CXDiagnostic_DisplayColumn = new CXDiagnosticDisplayOptions(2);
        public static final CXDiagnosticDisplayOptions CXDiagnostic_DisplaySourceRanges = new CXDiagnosticDisplayOptions(4);
        public static final CXDiagnosticDisplayOptions CXDiagnostic_DisplayOption = new CXDiagnosticDisplayOptions(8);
        public static final CXDiagnosticDisplayOptions CXDiagnostic_DisplayCategoryId = new CXDiagnosticDisplayOptions(16);
        public static final CXDiagnosticDisplayOptions CXDiagnostic_DisplayCategoryName = new CXDiagnosticDisplayOptions(32);

    }

    public static final class CXAvailabilityKind extends VI32Basic<CXAvailabilityKind> {
        public CXAvailabilityKind(int e) {
            super(e);
        }

        public CXAvailabilityKind(Pointer<CXAvailabilityKind> e) {
            super(e);
        }

        public CXAvailabilityKind(CXAvailabilityKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXAvailabilityKind> list(Pointer<CXAvailabilityKind> ptr) {
            return new VI32List<>(ptr, CXAvailabilityKind::new);
        }

        public static VI32List<CXAvailabilityKind> list(Pointer<CXAvailabilityKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXAvailabilityKind::new);
        }

        public static VI32List<CXAvailabilityKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXAvailabilityKind::new);
        }

        public static VI32List<CXAvailabilityKind> list(SegmentAllocator allocator, CXAvailabilityKind[] c) {
            return new VI32List<>(allocator, c, CXAvailabilityKind::new);
        }

        public static VI32List<CXAvailabilityKind> list(SegmentAllocator allocator, Collection<CXAvailabilityKind> c) {
            return new VI32List<>(allocator, c, CXAvailabilityKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXAvailabilityKind e) {
            return LibclangEnums.enumToString(CXAvailabilityKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXAvailabilityKind that && that.value().intValue() == value().intValue();
        }


        public static final CXAvailabilityKind CXAvailability_Available = new CXAvailabilityKind(0);
        public static final CXAvailabilityKind CXAvailability_Deprecated = new CXAvailabilityKind(1);
        public static final CXAvailabilityKind CXAvailability_NotAvailable = new CXAvailabilityKind(2);
        public static final CXAvailabilityKind CXAvailability_NotAccessible = new CXAvailabilityKind(3);

    }

    public static final class CXCursor_ExceptionSpecificationKind extends VI32Basic<CXCursor_ExceptionSpecificationKind> {
        public CXCursor_ExceptionSpecificationKind(int e) {
            super(e);
        }

        public CXCursor_ExceptionSpecificationKind(Pointer<CXCursor_ExceptionSpecificationKind> e) {
            super(e);
        }

        public CXCursor_ExceptionSpecificationKind(CXCursor_ExceptionSpecificationKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXCursor_ExceptionSpecificationKind> list(Pointer<CXCursor_ExceptionSpecificationKind> ptr) {
            return new VI32List<>(ptr, CXCursor_ExceptionSpecificationKind::new);
        }

        public static VI32List<CXCursor_ExceptionSpecificationKind> list(Pointer<CXCursor_ExceptionSpecificationKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXCursor_ExceptionSpecificationKind::new);
        }

        public static VI32List<CXCursor_ExceptionSpecificationKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXCursor_ExceptionSpecificationKind::new);
        }

        public static VI32List<CXCursor_ExceptionSpecificationKind> list(SegmentAllocator allocator, CXCursor_ExceptionSpecificationKind[] c) {
            return new VI32List<>(allocator, c, CXCursor_ExceptionSpecificationKind::new);
        }

        public static VI32List<CXCursor_ExceptionSpecificationKind> list(SegmentAllocator allocator, Collection<CXCursor_ExceptionSpecificationKind> c) {
            return new VI32List<>(allocator, c, CXCursor_ExceptionSpecificationKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXCursor_ExceptionSpecificationKind e) {
            return LibclangEnums.enumToString(CXCursor_ExceptionSpecificationKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXCursor_ExceptionSpecificationKind that && that.value().intValue() == value().intValue();
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

    public static final class CXGlobalOptFlags extends VI32Basic<CXGlobalOptFlags> {
        public CXGlobalOptFlags(int e) {
            super(e);
        }

        public CXGlobalOptFlags(Pointer<CXGlobalOptFlags> e) {
            super(e);
        }

        public CXGlobalOptFlags(CXGlobalOptFlags e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXGlobalOptFlags> list(Pointer<CXGlobalOptFlags> ptr) {
            return new VI32List<>(ptr, CXGlobalOptFlags::new);
        }

        public static VI32List<CXGlobalOptFlags> list(Pointer<CXGlobalOptFlags> ptr, long length) {
            return new VI32List<>(ptr, length, CXGlobalOptFlags::new);
        }

        public static VI32List<CXGlobalOptFlags> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXGlobalOptFlags::new);
        }

        public static VI32List<CXGlobalOptFlags> list(SegmentAllocator allocator, CXGlobalOptFlags[] c) {
            return new VI32List<>(allocator, c, CXGlobalOptFlags::new);
        }

        public static VI32List<CXGlobalOptFlags> list(SegmentAllocator allocator, Collection<CXGlobalOptFlags> c) {
            return new VI32List<>(allocator, c, CXGlobalOptFlags::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXGlobalOptFlags e) {
            return LibclangEnums.enumToString(CXGlobalOptFlags.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXGlobalOptFlags that && that.value().intValue() == value().intValue();
        }


        public static final CXGlobalOptFlags CXGlobalOpt_None = new CXGlobalOptFlags(0);
        public static final CXGlobalOptFlags CXGlobalOpt_ThreadBackgroundPriorityForIndexing = new CXGlobalOptFlags(1);
        public static final CXGlobalOptFlags CXGlobalOpt_ThreadBackgroundPriorityForEditing = new CXGlobalOptFlags(2);
        public static final CXGlobalOptFlags CXGlobalOpt_ThreadBackgroundPriorityForAll = new CXGlobalOptFlags(3);

    }

    public static final class CXTranslationUnit_Flags extends VI32Basic<CXTranslationUnit_Flags> {
        public CXTranslationUnit_Flags(int e) {
            super(e);
        }

        public CXTranslationUnit_Flags(Pointer<CXTranslationUnit_Flags> e) {
            super(e);
        }

        public CXTranslationUnit_Flags(CXTranslationUnit_Flags e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXTranslationUnit_Flags> list(Pointer<CXTranslationUnit_Flags> ptr) {
            return new VI32List<>(ptr, CXTranslationUnit_Flags::new);
        }

        public static VI32List<CXTranslationUnit_Flags> list(Pointer<CXTranslationUnit_Flags> ptr, long length) {
            return new VI32List<>(ptr, length, CXTranslationUnit_Flags::new);
        }

        public static VI32List<CXTranslationUnit_Flags> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXTranslationUnit_Flags::new);
        }

        public static VI32List<CXTranslationUnit_Flags> list(SegmentAllocator allocator, CXTranslationUnit_Flags[] c) {
            return new VI32List<>(allocator, c, CXTranslationUnit_Flags::new);
        }

        public static VI32List<CXTranslationUnit_Flags> list(SegmentAllocator allocator, Collection<CXTranslationUnit_Flags> c) {
            return new VI32List<>(allocator, c, CXTranslationUnit_Flags::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXTranslationUnit_Flags e) {
            return LibclangEnums.enumToString(CXTranslationUnit_Flags.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXTranslationUnit_Flags that && that.value().intValue() == value().intValue();
        }


        public static final CXTranslationUnit_Flags CXTranslationUnit_None = new CXTranslationUnit_Flags(0);
        public static final CXTranslationUnit_Flags CXTranslationUnit_DetailedPreprocessingRecord = new CXTranslationUnit_Flags(1);
        public static final CXTranslationUnit_Flags CXTranslationUnit_Incomplete = new CXTranslationUnit_Flags(2);
        public static final CXTranslationUnit_Flags CXTranslationUnit_PrecompiledPreamble = new CXTranslationUnit_Flags(4);
        public static final CXTranslationUnit_Flags CXTranslationUnit_CacheCompletionResults = new CXTranslationUnit_Flags(8);
        public static final CXTranslationUnit_Flags CXTranslationUnit_ForSerialization = new CXTranslationUnit_Flags(16);
        public static final CXTranslationUnit_Flags CXTranslationUnit_CXXChainedPCH = new CXTranslationUnit_Flags(32);
        public static final CXTranslationUnit_Flags CXTranslationUnit_SkipFunctionBodies = new CXTranslationUnit_Flags(64);
        public static final CXTranslationUnit_Flags CXTranslationUnit_IncludeBriefCommentsInCodeCompletion = new CXTranslationUnit_Flags(128);
        public static final CXTranslationUnit_Flags CXTranslationUnit_CreatePreambleOnFirstParse = new CXTranslationUnit_Flags(256);
        public static final CXTranslationUnit_Flags CXTranslationUnit_KeepGoing = new CXTranslationUnit_Flags(512);
        public static final CXTranslationUnit_Flags CXTranslationUnit_SingleFileParse = new CXTranslationUnit_Flags(1024);
        public static final CXTranslationUnit_Flags CXTranslationUnit_LimitSkipFunctionBodiesToPreamble = new CXTranslationUnit_Flags(2048);
        public static final CXTranslationUnit_Flags CXTranslationUnit_IncludeAttributedTypes = new CXTranslationUnit_Flags(4096);
        public static final CXTranslationUnit_Flags CXTranslationUnit_VisitImplicitAttributes = new CXTranslationUnit_Flags(8192);
        public static final CXTranslationUnit_Flags CXTranslationUnit_IgnoreNonErrorsFromIncludedFiles = new CXTranslationUnit_Flags(16384);
        public static final CXTranslationUnit_Flags CXTranslationUnit_RetainExcludedConditionalBlocks = new CXTranslationUnit_Flags(32768);

    }

    public static final class CXSaveTranslationUnit_Flags extends VI32Basic<CXSaveTranslationUnit_Flags> {
        public CXSaveTranslationUnit_Flags(int e) {
            super(e);
        }

        public CXSaveTranslationUnit_Flags(Pointer<CXSaveTranslationUnit_Flags> e) {
            super(e);
        }

        public CXSaveTranslationUnit_Flags(CXSaveTranslationUnit_Flags e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXSaveTranslationUnit_Flags> list(Pointer<CXSaveTranslationUnit_Flags> ptr) {
            return new VI32List<>(ptr, CXSaveTranslationUnit_Flags::new);
        }

        public static VI32List<CXSaveTranslationUnit_Flags> list(Pointer<CXSaveTranslationUnit_Flags> ptr, long length) {
            return new VI32List<>(ptr, length, CXSaveTranslationUnit_Flags::new);
        }

        public static VI32List<CXSaveTranslationUnit_Flags> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXSaveTranslationUnit_Flags::new);
        }

        public static VI32List<CXSaveTranslationUnit_Flags> list(SegmentAllocator allocator, CXSaveTranslationUnit_Flags[] c) {
            return new VI32List<>(allocator, c, CXSaveTranslationUnit_Flags::new);
        }

        public static VI32List<CXSaveTranslationUnit_Flags> list(SegmentAllocator allocator, Collection<CXSaveTranslationUnit_Flags> c) {
            return new VI32List<>(allocator, c, CXSaveTranslationUnit_Flags::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXSaveTranslationUnit_Flags e) {
            return LibclangEnums.enumToString(CXSaveTranslationUnit_Flags.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXSaveTranslationUnit_Flags that && that.value().intValue() == value().intValue();
        }


        public static final CXSaveTranslationUnit_Flags CXSaveTranslationUnit_None = new CXSaveTranslationUnit_Flags(0);

    }

    public static final class CXSaveError extends VI32Basic<CXSaveError> {
        public CXSaveError(int e) {
            super(e);
        }

        public CXSaveError(Pointer<CXSaveError> e) {
            super(e);
        }

        public CXSaveError(CXSaveError e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXSaveError> list(Pointer<CXSaveError> ptr) {
            return new VI32List<>(ptr, CXSaveError::new);
        }

        public static VI32List<CXSaveError> list(Pointer<CXSaveError> ptr, long length) {
            return new VI32List<>(ptr, length, CXSaveError::new);
        }

        public static VI32List<CXSaveError> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXSaveError::new);
        }

        public static VI32List<CXSaveError> list(SegmentAllocator allocator, CXSaveError[] c) {
            return new VI32List<>(allocator, c, CXSaveError::new);
        }

        public static VI32List<CXSaveError> list(SegmentAllocator allocator, Collection<CXSaveError> c) {
            return new VI32List<>(allocator, c, CXSaveError::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXSaveError e) {
            return LibclangEnums.enumToString(CXSaveError.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXSaveError that && that.value().intValue() == value().intValue();
        }


        public static final CXSaveError CXSaveError_None = new CXSaveError(0);
        public static final CXSaveError CXSaveError_Unknown = new CXSaveError(1);
        public static final CXSaveError CXSaveError_TranslationErrors = new CXSaveError(2);
        public static final CXSaveError CXSaveError_InvalidTU = new CXSaveError(3);

    }

    public static final class CXReparse_Flags extends VI32Basic<CXReparse_Flags> {
        public CXReparse_Flags(int e) {
            super(e);
        }

        public CXReparse_Flags(Pointer<CXReparse_Flags> e) {
            super(e);
        }

        public CXReparse_Flags(CXReparse_Flags e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXReparse_Flags> list(Pointer<CXReparse_Flags> ptr) {
            return new VI32List<>(ptr, CXReparse_Flags::new);
        }

        public static VI32List<CXReparse_Flags> list(Pointer<CXReparse_Flags> ptr, long length) {
            return new VI32List<>(ptr, length, CXReparse_Flags::new);
        }

        public static VI32List<CXReparse_Flags> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXReparse_Flags::new);
        }

        public static VI32List<CXReparse_Flags> list(SegmentAllocator allocator, CXReparse_Flags[] c) {
            return new VI32List<>(allocator, c, CXReparse_Flags::new);
        }

        public static VI32List<CXReparse_Flags> list(SegmentAllocator allocator, Collection<CXReparse_Flags> c) {
            return new VI32List<>(allocator, c, CXReparse_Flags::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXReparse_Flags e) {
            return LibclangEnums.enumToString(CXReparse_Flags.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXReparse_Flags that && that.value().intValue() == value().intValue();
        }


        public static final CXReparse_Flags CXReparse_None = new CXReparse_Flags(0);

    }

    public static final class CXTUResourceUsageKind extends VI32Basic<CXTUResourceUsageKind> {
        public CXTUResourceUsageKind(int e) {
            super(e);
        }

        public CXTUResourceUsageKind(Pointer<CXTUResourceUsageKind> e) {
            super(e);
        }

        public CXTUResourceUsageKind(CXTUResourceUsageKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXTUResourceUsageKind> list(Pointer<CXTUResourceUsageKind> ptr) {
            return new VI32List<>(ptr, CXTUResourceUsageKind::new);
        }

        public static VI32List<CXTUResourceUsageKind> list(Pointer<CXTUResourceUsageKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXTUResourceUsageKind::new);
        }

        public static VI32List<CXTUResourceUsageKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXTUResourceUsageKind::new);
        }

        public static VI32List<CXTUResourceUsageKind> list(SegmentAllocator allocator, CXTUResourceUsageKind[] c) {
            return new VI32List<>(allocator, c, CXTUResourceUsageKind::new);
        }

        public static VI32List<CXTUResourceUsageKind> list(SegmentAllocator allocator, Collection<CXTUResourceUsageKind> c) {
            return new VI32List<>(allocator, c, CXTUResourceUsageKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXTUResourceUsageKind e) {
            return LibclangEnums.enumToString(CXTUResourceUsageKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXTUResourceUsageKind that && that.value().intValue() == value().intValue();
        }


        public static final CXTUResourceUsageKind CXTUResourceUsage_AST = new CXTUResourceUsageKind(1);
        public static final CXTUResourceUsageKind CXTUResourceUsage_Identifiers = new CXTUResourceUsageKind(2);
        public static final CXTUResourceUsageKind CXTUResourceUsage_Selectors = new CXTUResourceUsageKind(3);
        public static final CXTUResourceUsageKind CXTUResourceUsage_GlobalCompletionResults = new CXTUResourceUsageKind(4);
        public static final CXTUResourceUsageKind CXTUResourceUsage_SourceManagerContentCache = new CXTUResourceUsageKind(5);
        public static final CXTUResourceUsageKind CXTUResourceUsage_AST_SideTables = new CXTUResourceUsageKind(6);
        public static final CXTUResourceUsageKind CXTUResourceUsage_SourceManager_Membuffer_Malloc = new CXTUResourceUsageKind(7);
        public static final CXTUResourceUsageKind CXTUResourceUsage_SourceManager_Membuffer_MMap = new CXTUResourceUsageKind(8);
        public static final CXTUResourceUsageKind CXTUResourceUsage_ExternalASTSource_Membuffer_Malloc = new CXTUResourceUsageKind(9);
        public static final CXTUResourceUsageKind CXTUResourceUsage_ExternalASTSource_Membuffer_MMap = new CXTUResourceUsageKind(10);
        public static final CXTUResourceUsageKind CXTUResourceUsage_Preprocessor = new CXTUResourceUsageKind(11);
        public static final CXTUResourceUsageKind CXTUResourceUsage_PreprocessingRecord = new CXTUResourceUsageKind(12);
        public static final CXTUResourceUsageKind CXTUResourceUsage_SourceManager_DataStructures = new CXTUResourceUsageKind(13);
        public static final CXTUResourceUsageKind CXTUResourceUsage_Preprocessor_HeaderSearch = new CXTUResourceUsageKind(14);
        public static final CXTUResourceUsageKind CXTUResourceUsage_MEMORY_IN_BYTES_BEGIN = new CXTUResourceUsageKind(1);
        public static final CXTUResourceUsageKind CXTUResourceUsage_MEMORY_IN_BYTES_END = new CXTUResourceUsageKind(14);
        public static final CXTUResourceUsageKind CXTUResourceUsage_First = new CXTUResourceUsageKind(1);
        public static final CXTUResourceUsageKind CXTUResourceUsage_Last = new CXTUResourceUsageKind(14);

    }

    public static final class CXCursorKind extends VI32Basic<CXCursorKind> {
        public CXCursorKind(int e) {
            super(e);
        }

        public CXCursorKind(Pointer<CXCursorKind> e) {
            super(e);
        }

        public CXCursorKind(CXCursorKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXCursorKind> list(Pointer<CXCursorKind> ptr) {
            return new VI32List<>(ptr, CXCursorKind::new);
        }

        public static VI32List<CXCursorKind> list(Pointer<CXCursorKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXCursorKind::new);
        }

        public static VI32List<CXCursorKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXCursorKind::new);
        }

        public static VI32List<CXCursorKind> list(SegmentAllocator allocator, CXCursorKind[] c) {
            return new VI32List<>(allocator, c, CXCursorKind::new);
        }

        public static VI32List<CXCursorKind> list(SegmentAllocator allocator, Collection<CXCursorKind> c) {
            return new VI32List<>(allocator, c, CXCursorKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXCursorKind e) {
            return LibclangEnums.enumToString(CXCursorKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXCursorKind that && that.value().intValue() == value().intValue();
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

    public static final class CXLinkageKind extends VI32Basic<CXLinkageKind> {
        public CXLinkageKind(int e) {
            super(e);
        }

        public CXLinkageKind(Pointer<CXLinkageKind> e) {
            super(e);
        }

        public CXLinkageKind(CXLinkageKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXLinkageKind> list(Pointer<CXLinkageKind> ptr) {
            return new VI32List<>(ptr, CXLinkageKind::new);
        }

        public static VI32List<CXLinkageKind> list(Pointer<CXLinkageKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXLinkageKind::new);
        }

        public static VI32List<CXLinkageKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXLinkageKind::new);
        }

        public static VI32List<CXLinkageKind> list(SegmentAllocator allocator, CXLinkageKind[] c) {
            return new VI32List<>(allocator, c, CXLinkageKind::new);
        }

        public static VI32List<CXLinkageKind> list(SegmentAllocator allocator, Collection<CXLinkageKind> c) {
            return new VI32List<>(allocator, c, CXLinkageKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXLinkageKind e) {
            return LibclangEnums.enumToString(CXLinkageKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXLinkageKind that && that.value().intValue() == value().intValue();
        }


        public static final CXLinkageKind CXLinkage_Invalid = new CXLinkageKind(0);
        public static final CXLinkageKind CXLinkage_NoLinkage = new CXLinkageKind(1);
        public static final CXLinkageKind CXLinkage_Internal = new CXLinkageKind(2);
        public static final CXLinkageKind CXLinkage_UniqueExternal = new CXLinkageKind(3);
        public static final CXLinkageKind CXLinkage_External = new CXLinkageKind(4);

    }

    public static final class CXVisibilityKind extends VI32Basic<CXVisibilityKind> {
        public CXVisibilityKind(int e) {
            super(e);
        }

        public CXVisibilityKind(Pointer<CXVisibilityKind> e) {
            super(e);
        }

        public CXVisibilityKind(CXVisibilityKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXVisibilityKind> list(Pointer<CXVisibilityKind> ptr) {
            return new VI32List<>(ptr, CXVisibilityKind::new);
        }

        public static VI32List<CXVisibilityKind> list(Pointer<CXVisibilityKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXVisibilityKind::new);
        }

        public static VI32List<CXVisibilityKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXVisibilityKind::new);
        }

        public static VI32List<CXVisibilityKind> list(SegmentAllocator allocator, CXVisibilityKind[] c) {
            return new VI32List<>(allocator, c, CXVisibilityKind::new);
        }

        public static VI32List<CXVisibilityKind> list(SegmentAllocator allocator, Collection<CXVisibilityKind> c) {
            return new VI32List<>(allocator, c, CXVisibilityKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXVisibilityKind e) {
            return LibclangEnums.enumToString(CXVisibilityKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXVisibilityKind that && that.value().intValue() == value().intValue();
        }


        public static final CXVisibilityKind CXVisibility_Invalid = new CXVisibilityKind(0);
        public static final CXVisibilityKind CXVisibility_Hidden = new CXVisibilityKind(1);
        public static final CXVisibilityKind CXVisibility_Protected = new CXVisibilityKind(2);
        public static final CXVisibilityKind CXVisibility_Default = new CXVisibilityKind(3);

    }

    public static final class CXLanguageKind extends VI32Basic<CXLanguageKind> {
        public CXLanguageKind(int e) {
            super(e);
        }

        public CXLanguageKind(Pointer<CXLanguageKind> e) {
            super(e);
        }

        public CXLanguageKind(CXLanguageKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXLanguageKind> list(Pointer<CXLanguageKind> ptr) {
            return new VI32List<>(ptr, CXLanguageKind::new);
        }

        public static VI32List<CXLanguageKind> list(Pointer<CXLanguageKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXLanguageKind::new);
        }

        public static VI32List<CXLanguageKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXLanguageKind::new);
        }

        public static VI32List<CXLanguageKind> list(SegmentAllocator allocator, CXLanguageKind[] c) {
            return new VI32List<>(allocator, c, CXLanguageKind::new);
        }

        public static VI32List<CXLanguageKind> list(SegmentAllocator allocator, Collection<CXLanguageKind> c) {
            return new VI32List<>(allocator, c, CXLanguageKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXLanguageKind e) {
            return LibclangEnums.enumToString(CXLanguageKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXLanguageKind that && that.value().intValue() == value().intValue();
        }


        public static final CXLanguageKind CXLanguage_Invalid = new CXLanguageKind(0);
        public static final CXLanguageKind CXLanguage_C = new CXLanguageKind(1);
        public static final CXLanguageKind CXLanguage_ObjC = new CXLanguageKind(2);
        public static final CXLanguageKind CXLanguage_CPlusPlus = new CXLanguageKind(3);

    }

    public static final class CXTLSKind extends VI32Basic<CXTLSKind> {
        public CXTLSKind(int e) {
            super(e);
        }

        public CXTLSKind(Pointer<CXTLSKind> e) {
            super(e);
        }

        public CXTLSKind(CXTLSKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXTLSKind> list(Pointer<CXTLSKind> ptr) {
            return new VI32List<>(ptr, CXTLSKind::new);
        }

        public static VI32List<CXTLSKind> list(Pointer<CXTLSKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXTLSKind::new);
        }

        public static VI32List<CXTLSKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXTLSKind::new);
        }

        public static VI32List<CXTLSKind> list(SegmentAllocator allocator, CXTLSKind[] c) {
            return new VI32List<>(allocator, c, CXTLSKind::new);
        }

        public static VI32List<CXTLSKind> list(SegmentAllocator allocator, Collection<CXTLSKind> c) {
            return new VI32List<>(allocator, c, CXTLSKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXTLSKind e) {
            return LibclangEnums.enumToString(CXTLSKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXTLSKind that && that.value().intValue() == value().intValue();
        }


        public static final CXTLSKind CXTLS_None = new CXTLSKind(0);
        public static final CXTLSKind CXTLS_Dynamic = new CXTLSKind(1);
        public static final CXTLSKind CXTLS_Static = new CXTLSKind(2);

    }

    public static final class CXTypeKind extends VI32Basic<CXTypeKind> {
        public CXTypeKind(int e) {
            super(e);
        }

        public CXTypeKind(Pointer<CXTypeKind> e) {
            super(e);
        }

        public CXTypeKind(CXTypeKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXTypeKind> list(Pointer<CXTypeKind> ptr) {
            return new VI32List<>(ptr, CXTypeKind::new);
        }

        public static VI32List<CXTypeKind> list(Pointer<CXTypeKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXTypeKind::new);
        }

        public static VI32List<CXTypeKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXTypeKind::new);
        }

        public static VI32List<CXTypeKind> list(SegmentAllocator allocator, CXTypeKind[] c) {
            return new VI32List<>(allocator, c, CXTypeKind::new);
        }

        public static VI32List<CXTypeKind> list(SegmentAllocator allocator, Collection<CXTypeKind> c) {
            return new VI32List<>(allocator, c, CXTypeKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXTypeKind e) {
            return LibclangEnums.enumToString(CXTypeKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXTypeKind that && that.value().intValue() == value().intValue();
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
        public static final CXTypeKind CXType_OCLIntelSubgroupAVCImeResultSingleRefStreamout = new CXTypeKind(172);
        public static final CXTypeKind CXType_OCLIntelSubgroupAVCImeResultDualRefStreamout = new CXTypeKind(173);
        public static final CXTypeKind CXType_OCLIntelSubgroupAVCImeSingleRefStreamin = new CXTypeKind(174);
        public static final CXTypeKind CXType_OCLIntelSubgroupAVCImeDualRefStreamin = new CXTypeKind(175);
        public static final CXTypeKind CXType_ExtVector = new CXTypeKind(176);
        public static final CXTypeKind CXType_Atomic = new CXTypeKind(177);
        public static final CXTypeKind CXType_BTFTagAttributed = new CXTypeKind(178);

    }

    public static final class CXCallingConv extends VI32Basic<CXCallingConv> {
        public CXCallingConv(int e) {
            super(e);
        }

        public CXCallingConv(Pointer<CXCallingConv> e) {
            super(e);
        }

        public CXCallingConv(CXCallingConv e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXCallingConv> list(Pointer<CXCallingConv> ptr) {
            return new VI32List<>(ptr, CXCallingConv::new);
        }

        public static VI32List<CXCallingConv> list(Pointer<CXCallingConv> ptr, long length) {
            return new VI32List<>(ptr, length, CXCallingConv::new);
        }

        public static VI32List<CXCallingConv> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXCallingConv::new);
        }

        public static VI32List<CXCallingConv> list(SegmentAllocator allocator, CXCallingConv[] c) {
            return new VI32List<>(allocator, c, CXCallingConv::new);
        }

        public static VI32List<CXCallingConv> list(SegmentAllocator allocator, Collection<CXCallingConv> c) {
            return new VI32List<>(allocator, c, CXCallingConv::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXCallingConv e) {
            return LibclangEnums.enumToString(CXCallingConv.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXCallingConv that && that.value().intValue() == value().intValue();
        }


        public static final CXCallingConv CXCallingConv_Default = new CXCallingConv(0);
        public static final CXCallingConv CXCallingConv_C = new CXCallingConv(1);
        public static final CXCallingConv CXCallingConv_X86StdCall = new CXCallingConv(2);
        public static final CXCallingConv CXCallingConv_X86FastCall = new CXCallingConv(3);
        public static final CXCallingConv CXCallingConv_X86ThisCall = new CXCallingConv(4);
        public static final CXCallingConv CXCallingConv_X86Pascal = new CXCallingConv(5);
        public static final CXCallingConv CXCallingConv_AAPCS = new CXCallingConv(6);
        public static final CXCallingConv CXCallingConv_AAPCS_VFP = new CXCallingConv(7);
        public static final CXCallingConv CXCallingConv_X86RegCall = new CXCallingConv(8);
        public static final CXCallingConv CXCallingConv_IntelOclBicc = new CXCallingConv(9);
        public static final CXCallingConv CXCallingConv_Win64 = new CXCallingConv(10);
        public static final CXCallingConv CXCallingConv_X86_64Win64 = new CXCallingConv(10);
        public static final CXCallingConv CXCallingConv_X86_64SysV = new CXCallingConv(11);
        public static final CXCallingConv CXCallingConv_X86VectorCall = new CXCallingConv(12);
        public static final CXCallingConv CXCallingConv_Swift = new CXCallingConv(13);
        public static final CXCallingConv CXCallingConv_PreserveMost = new CXCallingConv(14);
        public static final CXCallingConv CXCallingConv_PreserveAll = new CXCallingConv(15);
        public static final CXCallingConv CXCallingConv_AArch64VectorCall = new CXCallingConv(16);
        public static final CXCallingConv CXCallingConv_SwiftAsync = new CXCallingConv(17);
        public static final CXCallingConv CXCallingConv_AArch64SVEPCS = new CXCallingConv(18);
        public static final CXCallingConv CXCallingConv_Invalid = new CXCallingConv(100);
        public static final CXCallingConv CXCallingConv_Unexposed = new CXCallingConv(200);

    }

    public static final class CXTemplateArgumentKind extends VI32Basic<CXTemplateArgumentKind> {
        public CXTemplateArgumentKind(int e) {
            super(e);
        }

        public CXTemplateArgumentKind(Pointer<CXTemplateArgumentKind> e) {
            super(e);
        }

        public CXTemplateArgumentKind(CXTemplateArgumentKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXTemplateArgumentKind> list(Pointer<CXTemplateArgumentKind> ptr) {
            return new VI32List<>(ptr, CXTemplateArgumentKind::new);
        }

        public static VI32List<CXTemplateArgumentKind> list(Pointer<CXTemplateArgumentKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXTemplateArgumentKind::new);
        }

        public static VI32List<CXTemplateArgumentKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXTemplateArgumentKind::new);
        }

        public static VI32List<CXTemplateArgumentKind> list(SegmentAllocator allocator, CXTemplateArgumentKind[] c) {
            return new VI32List<>(allocator, c, CXTemplateArgumentKind::new);
        }

        public static VI32List<CXTemplateArgumentKind> list(SegmentAllocator allocator, Collection<CXTemplateArgumentKind> c) {
            return new VI32List<>(allocator, c, CXTemplateArgumentKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXTemplateArgumentKind e) {
            return LibclangEnums.enumToString(CXTemplateArgumentKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXTemplateArgumentKind that && that.value().intValue() == value().intValue();
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

    public static final class CXTypeNullabilityKind extends VI32Basic<CXTypeNullabilityKind> {
        public CXTypeNullabilityKind(int e) {
            super(e);
        }

        public CXTypeNullabilityKind(Pointer<CXTypeNullabilityKind> e) {
            super(e);
        }

        public CXTypeNullabilityKind(CXTypeNullabilityKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXTypeNullabilityKind> list(Pointer<CXTypeNullabilityKind> ptr) {
            return new VI32List<>(ptr, CXTypeNullabilityKind::new);
        }

        public static VI32List<CXTypeNullabilityKind> list(Pointer<CXTypeNullabilityKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXTypeNullabilityKind::new);
        }

        public static VI32List<CXTypeNullabilityKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXTypeNullabilityKind::new);
        }

        public static VI32List<CXTypeNullabilityKind> list(SegmentAllocator allocator, CXTypeNullabilityKind[] c) {
            return new VI32List<>(allocator, c, CXTypeNullabilityKind::new);
        }

        public static VI32List<CXTypeNullabilityKind> list(SegmentAllocator allocator, Collection<CXTypeNullabilityKind> c) {
            return new VI32List<>(allocator, c, CXTypeNullabilityKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXTypeNullabilityKind e) {
            return LibclangEnums.enumToString(CXTypeNullabilityKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXTypeNullabilityKind that && that.value().intValue() == value().intValue();
        }


        public static final CXTypeNullabilityKind CXTypeNullability_NonNull = new CXTypeNullabilityKind(0);
        public static final CXTypeNullabilityKind CXTypeNullability_Nullable = new CXTypeNullabilityKind(1);
        public static final CXTypeNullabilityKind CXTypeNullability_Unspecified = new CXTypeNullabilityKind(2);
        public static final CXTypeNullabilityKind CXTypeNullability_Invalid = new CXTypeNullabilityKind(3);
        public static final CXTypeNullabilityKind CXTypeNullability_NullableResult = new CXTypeNullabilityKind(4);

    }

    public static final class CXTypeLayoutError extends VI32Basic<CXTypeLayoutError> {
        public CXTypeLayoutError(int e) {
            super(e);
        }

        public CXTypeLayoutError(Pointer<CXTypeLayoutError> e) {
            super(e);
        }

        public CXTypeLayoutError(CXTypeLayoutError e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXTypeLayoutError> list(Pointer<CXTypeLayoutError> ptr) {
            return new VI32List<>(ptr, CXTypeLayoutError::new);
        }

        public static VI32List<CXTypeLayoutError> list(Pointer<CXTypeLayoutError> ptr, long length) {
            return new VI32List<>(ptr, length, CXTypeLayoutError::new);
        }

        public static VI32List<CXTypeLayoutError> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXTypeLayoutError::new);
        }

        public static VI32List<CXTypeLayoutError> list(SegmentAllocator allocator, CXTypeLayoutError[] c) {
            return new VI32List<>(allocator, c, CXTypeLayoutError::new);
        }

        public static VI32List<CXTypeLayoutError> list(SegmentAllocator allocator, Collection<CXTypeLayoutError> c) {
            return new VI32List<>(allocator, c, CXTypeLayoutError::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXTypeLayoutError e) {
            return LibclangEnums.enumToString(CXTypeLayoutError.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXTypeLayoutError that && that.value().intValue() == value().intValue();
        }


        public static final CXTypeLayoutError CXTypeLayoutError_Invalid = new CXTypeLayoutError(-1);
        public static final CXTypeLayoutError CXTypeLayoutError_Incomplete = new CXTypeLayoutError(-2);
        public static final CXTypeLayoutError CXTypeLayoutError_Dependent = new CXTypeLayoutError(-3);
        public static final CXTypeLayoutError CXTypeLayoutError_NotConstantSize = new CXTypeLayoutError(-4);
        public static final CXTypeLayoutError CXTypeLayoutError_InvalidFieldName = new CXTypeLayoutError(-5);
        public static final CXTypeLayoutError CXTypeLayoutError_Undeduced = new CXTypeLayoutError(-6);

    }

    public static final class CXRefQualifierKind extends VI32Basic<CXRefQualifierKind> {
        public CXRefQualifierKind(int e) {
            super(e);
        }

        public CXRefQualifierKind(Pointer<CXRefQualifierKind> e) {
            super(e);
        }

        public CXRefQualifierKind(CXRefQualifierKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXRefQualifierKind> list(Pointer<CXRefQualifierKind> ptr) {
            return new VI32List<>(ptr, CXRefQualifierKind::new);
        }

        public static VI32List<CXRefQualifierKind> list(Pointer<CXRefQualifierKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXRefQualifierKind::new);
        }

        public static VI32List<CXRefQualifierKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXRefQualifierKind::new);
        }

        public static VI32List<CXRefQualifierKind> list(SegmentAllocator allocator, CXRefQualifierKind[] c) {
            return new VI32List<>(allocator, c, CXRefQualifierKind::new);
        }

        public static VI32List<CXRefQualifierKind> list(SegmentAllocator allocator, Collection<CXRefQualifierKind> c) {
            return new VI32List<>(allocator, c, CXRefQualifierKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXRefQualifierKind e) {
            return LibclangEnums.enumToString(CXRefQualifierKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXRefQualifierKind that && that.value().intValue() == value().intValue();
        }


        public static final CXRefQualifierKind CXRefQualifier_None = new CXRefQualifierKind(0);
        public static final CXRefQualifierKind CXRefQualifier_LValue = new CXRefQualifierKind(1);
        public static final CXRefQualifierKind CXRefQualifier_RValue = new CXRefQualifierKind(2);

    }

    public static final class CX_CXXAccessSpecifier extends VI32Basic<CX_CXXAccessSpecifier> {
        public CX_CXXAccessSpecifier(int e) {
            super(e);
        }

        public CX_CXXAccessSpecifier(Pointer<CX_CXXAccessSpecifier> e) {
            super(e);
        }

        public CX_CXXAccessSpecifier(CX_CXXAccessSpecifier e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CX_CXXAccessSpecifier> list(Pointer<CX_CXXAccessSpecifier> ptr) {
            return new VI32List<>(ptr, CX_CXXAccessSpecifier::new);
        }

        public static VI32List<CX_CXXAccessSpecifier> list(Pointer<CX_CXXAccessSpecifier> ptr, long length) {
            return new VI32List<>(ptr, length, CX_CXXAccessSpecifier::new);
        }

        public static VI32List<CX_CXXAccessSpecifier> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CX_CXXAccessSpecifier::new);
        }

        public static VI32List<CX_CXXAccessSpecifier> list(SegmentAllocator allocator, CX_CXXAccessSpecifier[] c) {
            return new VI32List<>(allocator, c, CX_CXXAccessSpecifier::new);
        }

        public static VI32List<CX_CXXAccessSpecifier> list(SegmentAllocator allocator, Collection<CX_CXXAccessSpecifier> c) {
            return new VI32List<>(allocator, c, CX_CXXAccessSpecifier::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CX_CXXAccessSpecifier e) {
            return LibclangEnums.enumToString(CX_CXXAccessSpecifier.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CX_CXXAccessSpecifier that && that.value().intValue() == value().intValue();
        }


        public static final CX_CXXAccessSpecifier CX_CXXInvalidAccessSpecifier = new CX_CXXAccessSpecifier(0);
        public static final CX_CXXAccessSpecifier CX_CXXPublic = new CX_CXXAccessSpecifier(1);
        public static final CX_CXXAccessSpecifier CX_CXXProtected = new CX_CXXAccessSpecifier(2);
        public static final CX_CXXAccessSpecifier CX_CXXPrivate = new CX_CXXAccessSpecifier(3);

    }

    public static final class CX_StorageClass extends VI32Basic<CX_StorageClass> {
        public CX_StorageClass(int e) {
            super(e);
        }

        public CX_StorageClass(Pointer<CX_StorageClass> e) {
            super(e);
        }

        public CX_StorageClass(CX_StorageClass e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CX_StorageClass> list(Pointer<CX_StorageClass> ptr) {
            return new VI32List<>(ptr, CX_StorageClass::new);
        }

        public static VI32List<CX_StorageClass> list(Pointer<CX_StorageClass> ptr, long length) {
            return new VI32List<>(ptr, length, CX_StorageClass::new);
        }

        public static VI32List<CX_StorageClass> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CX_StorageClass::new);
        }

        public static VI32List<CX_StorageClass> list(SegmentAllocator allocator, CX_StorageClass[] c) {
            return new VI32List<>(allocator, c, CX_StorageClass::new);
        }

        public static VI32List<CX_StorageClass> list(SegmentAllocator allocator, Collection<CX_StorageClass> c) {
            return new VI32List<>(allocator, c, CX_StorageClass::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CX_StorageClass e) {
            return LibclangEnums.enumToString(CX_StorageClass.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CX_StorageClass that && that.value().intValue() == value().intValue();
        }


        public static final CX_StorageClass CX_SC_Invalid = new CX_StorageClass(0);
        public static final CX_StorageClass CX_SC_None = new CX_StorageClass(1);
        public static final CX_StorageClass CX_SC_Extern = new CX_StorageClass(2);
        public static final CX_StorageClass CX_SC_Static = new CX_StorageClass(3);
        public static final CX_StorageClass CX_SC_PrivateExtern = new CX_StorageClass(4);
        public static final CX_StorageClass CX_SC_OpenCLWorkGroupLocal = new CX_StorageClass(5);
        public static final CX_StorageClass CX_SC_Auto = new CX_StorageClass(6);
        public static final CX_StorageClass CX_SC_Register = new CX_StorageClass(7);

    }

    public static final class CXChildVisitResult extends VI32Basic<CXChildVisitResult> {
        public CXChildVisitResult(int e) {
            super(e);
        }

        public CXChildVisitResult(Pointer<CXChildVisitResult> e) {
            super(e);
        }

        public CXChildVisitResult(CXChildVisitResult e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXChildVisitResult> list(Pointer<CXChildVisitResult> ptr) {
            return new VI32List<>(ptr, CXChildVisitResult::new);
        }

        public static VI32List<CXChildVisitResult> list(Pointer<CXChildVisitResult> ptr, long length) {
            return new VI32List<>(ptr, length, CXChildVisitResult::new);
        }

        public static VI32List<CXChildVisitResult> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXChildVisitResult::new);
        }

        public static VI32List<CXChildVisitResult> list(SegmentAllocator allocator, CXChildVisitResult[] c) {
            return new VI32List<>(allocator, c, CXChildVisitResult::new);
        }

        public static VI32List<CXChildVisitResult> list(SegmentAllocator allocator, Collection<CXChildVisitResult> c) {
            return new VI32List<>(allocator, c, CXChildVisitResult::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXChildVisitResult e) {
            return LibclangEnums.enumToString(CXChildVisitResult.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXChildVisitResult that && that.value().intValue() == value().intValue();
        }


        public static final CXChildVisitResult CXChildVisit_Break = new CXChildVisitResult(0);
        public static final CXChildVisitResult CXChildVisit_Continue = new CXChildVisitResult(1);
        public static final CXChildVisitResult CXChildVisit_Recurse = new CXChildVisitResult(2);

    }

    public static final class CXPrintingPolicyProperty extends VI32Basic<CXPrintingPolicyProperty> {
        public CXPrintingPolicyProperty(int e) {
            super(e);
        }

        public CXPrintingPolicyProperty(Pointer<CXPrintingPolicyProperty> e) {
            super(e);
        }

        public CXPrintingPolicyProperty(CXPrintingPolicyProperty e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXPrintingPolicyProperty> list(Pointer<CXPrintingPolicyProperty> ptr) {
            return new VI32List<>(ptr, CXPrintingPolicyProperty::new);
        }

        public static VI32List<CXPrintingPolicyProperty> list(Pointer<CXPrintingPolicyProperty> ptr, long length) {
            return new VI32List<>(ptr, length, CXPrintingPolicyProperty::new);
        }

        public static VI32List<CXPrintingPolicyProperty> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXPrintingPolicyProperty::new);
        }

        public static VI32List<CXPrintingPolicyProperty> list(SegmentAllocator allocator, CXPrintingPolicyProperty[] c) {
            return new VI32List<>(allocator, c, CXPrintingPolicyProperty::new);
        }

        public static VI32List<CXPrintingPolicyProperty> list(SegmentAllocator allocator, Collection<CXPrintingPolicyProperty> c) {
            return new VI32List<>(allocator, c, CXPrintingPolicyProperty::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXPrintingPolicyProperty e) {
            return LibclangEnums.enumToString(CXPrintingPolicyProperty.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXPrintingPolicyProperty that && that.value().intValue() == value().intValue();
        }


        public static final CXPrintingPolicyProperty CXPrintingPolicy_Indentation = new CXPrintingPolicyProperty(0);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressSpecifiers = new CXPrintingPolicyProperty(1);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressTagKeyword = new CXPrintingPolicyProperty(2);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_IncludeTagDefinition = new CXPrintingPolicyProperty(3);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressScope = new CXPrintingPolicyProperty(4);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressUnwrittenScope = new CXPrintingPolicyProperty(5);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressInitializers = new CXPrintingPolicyProperty(6);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_ConstantArraySizeAsWritten = new CXPrintingPolicyProperty(7);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_AnonymousTagLocations = new CXPrintingPolicyProperty(8);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressStrongLifetime = new CXPrintingPolicyProperty(9);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressLifetimeQualifiers = new CXPrintingPolicyProperty(10);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressTemplateArgsInCXXConstructors = new CXPrintingPolicyProperty(11);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_Bool = new CXPrintingPolicyProperty(12);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_Restrict = new CXPrintingPolicyProperty(13);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_Alignof = new CXPrintingPolicyProperty(14);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_UnderscoreAlignof = new CXPrintingPolicyProperty(15);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_UseVoidForZeroParams = new CXPrintingPolicyProperty(16);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_TerseOutput = new CXPrintingPolicyProperty(17);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_PolishForDeclaration = new CXPrintingPolicyProperty(18);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_Half = new CXPrintingPolicyProperty(19);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_MSWChar = new CXPrintingPolicyProperty(20);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_IncludeNewlines = new CXPrintingPolicyProperty(21);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_MSVCFormatting = new CXPrintingPolicyProperty(22);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_ConstantsAsWritten = new CXPrintingPolicyProperty(23);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressImplicitBase = new CXPrintingPolicyProperty(24);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_FullyQualifiedName = new CXPrintingPolicyProperty(25);
        public static final CXPrintingPolicyProperty CXPrintingPolicy_LastProperty = new CXPrintingPolicyProperty(25);

    }

    public static final class CXObjCPropertyAttrKind extends VI32Basic<CXObjCPropertyAttrKind> {
        public CXObjCPropertyAttrKind(int e) {
            super(e);
        }

        public CXObjCPropertyAttrKind(Pointer<CXObjCPropertyAttrKind> e) {
            super(e);
        }

        public CXObjCPropertyAttrKind(CXObjCPropertyAttrKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXObjCPropertyAttrKind> list(Pointer<CXObjCPropertyAttrKind> ptr) {
            return new VI32List<>(ptr, CXObjCPropertyAttrKind::new);
        }

        public static VI32List<CXObjCPropertyAttrKind> list(Pointer<CXObjCPropertyAttrKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXObjCPropertyAttrKind::new);
        }

        public static VI32List<CXObjCPropertyAttrKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXObjCPropertyAttrKind::new);
        }

        public static VI32List<CXObjCPropertyAttrKind> list(SegmentAllocator allocator, CXObjCPropertyAttrKind[] c) {
            return new VI32List<>(allocator, c, CXObjCPropertyAttrKind::new);
        }

        public static VI32List<CXObjCPropertyAttrKind> list(SegmentAllocator allocator, Collection<CXObjCPropertyAttrKind> c) {
            return new VI32List<>(allocator, c, CXObjCPropertyAttrKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXObjCPropertyAttrKind e) {
            return LibclangEnums.enumToString(CXObjCPropertyAttrKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXObjCPropertyAttrKind that && that.value().intValue() == value().intValue();
        }


        public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_noattr = new CXObjCPropertyAttrKind(0);
        public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_readonly = new CXObjCPropertyAttrKind(1);
        public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_getter = new CXObjCPropertyAttrKind(2);
        public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_assign = new CXObjCPropertyAttrKind(4);
        public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_readwrite = new CXObjCPropertyAttrKind(8);
        public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_retain = new CXObjCPropertyAttrKind(16);
        public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_copy = new CXObjCPropertyAttrKind(32);
        public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_nonatomic = new CXObjCPropertyAttrKind(64);
        public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_setter = new CXObjCPropertyAttrKind(128);
        public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_atomic = new CXObjCPropertyAttrKind(256);
        public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_weak = new CXObjCPropertyAttrKind(512);
        public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_strong = new CXObjCPropertyAttrKind(1024);
        public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_unsafe_unretained = new CXObjCPropertyAttrKind(2048);
        public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_class = new CXObjCPropertyAttrKind(4096);

    }

    public static final class CXObjCDeclQualifierKind extends VI32Basic<CXObjCDeclQualifierKind> {
        public CXObjCDeclQualifierKind(int e) {
            super(e);
        }

        public CXObjCDeclQualifierKind(Pointer<CXObjCDeclQualifierKind> e) {
            super(e);
        }

        public CXObjCDeclQualifierKind(CXObjCDeclQualifierKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXObjCDeclQualifierKind> list(Pointer<CXObjCDeclQualifierKind> ptr) {
            return new VI32List<>(ptr, CXObjCDeclQualifierKind::new);
        }

        public static VI32List<CXObjCDeclQualifierKind> list(Pointer<CXObjCDeclQualifierKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXObjCDeclQualifierKind::new);
        }

        public static VI32List<CXObjCDeclQualifierKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXObjCDeclQualifierKind::new);
        }

        public static VI32List<CXObjCDeclQualifierKind> list(SegmentAllocator allocator, CXObjCDeclQualifierKind[] c) {
            return new VI32List<>(allocator, c, CXObjCDeclQualifierKind::new);
        }

        public static VI32List<CXObjCDeclQualifierKind> list(SegmentAllocator allocator, Collection<CXObjCDeclQualifierKind> c) {
            return new VI32List<>(allocator, c, CXObjCDeclQualifierKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXObjCDeclQualifierKind e) {
            return LibclangEnums.enumToString(CXObjCDeclQualifierKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXObjCDeclQualifierKind that && that.value().intValue() == value().intValue();
        }


        public static final CXObjCDeclQualifierKind CXObjCDeclQualifier_None = new CXObjCDeclQualifierKind(0);
        public static final CXObjCDeclQualifierKind CXObjCDeclQualifier_In = new CXObjCDeclQualifierKind(1);
        public static final CXObjCDeclQualifierKind CXObjCDeclQualifier_Inout = new CXObjCDeclQualifierKind(2);
        public static final CXObjCDeclQualifierKind CXObjCDeclQualifier_Out = new CXObjCDeclQualifierKind(4);
        public static final CXObjCDeclQualifierKind CXObjCDeclQualifier_Bycopy = new CXObjCDeclQualifierKind(8);
        public static final CXObjCDeclQualifierKind CXObjCDeclQualifier_Byref = new CXObjCDeclQualifierKind(16);
        public static final CXObjCDeclQualifierKind CXObjCDeclQualifier_Oneway = new CXObjCDeclQualifierKind(32);

    }

    public static final class CXNameRefFlags extends VI32Basic<CXNameRefFlags> {
        public CXNameRefFlags(int e) {
            super(e);
        }

        public CXNameRefFlags(Pointer<CXNameRefFlags> e) {
            super(e);
        }

        public CXNameRefFlags(CXNameRefFlags e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXNameRefFlags> list(Pointer<CXNameRefFlags> ptr) {
            return new VI32List<>(ptr, CXNameRefFlags::new);
        }

        public static VI32List<CXNameRefFlags> list(Pointer<CXNameRefFlags> ptr, long length) {
            return new VI32List<>(ptr, length, CXNameRefFlags::new);
        }

        public static VI32List<CXNameRefFlags> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXNameRefFlags::new);
        }

        public static VI32List<CXNameRefFlags> list(SegmentAllocator allocator, CXNameRefFlags[] c) {
            return new VI32List<>(allocator, c, CXNameRefFlags::new);
        }

        public static VI32List<CXNameRefFlags> list(SegmentAllocator allocator, Collection<CXNameRefFlags> c) {
            return new VI32List<>(allocator, c, CXNameRefFlags::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXNameRefFlags e) {
            return LibclangEnums.enumToString(CXNameRefFlags.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXNameRefFlags that && that.value().intValue() == value().intValue();
        }


        public static final CXNameRefFlags CXNameRange_WantQualifier = new CXNameRefFlags(1);
        public static final CXNameRefFlags CXNameRange_WantTemplateArgs = new CXNameRefFlags(2);
        public static final CXNameRefFlags CXNameRange_WantSinglePiece = new CXNameRefFlags(4);

    }

    public static final class CXTokenKind extends VI32Basic<CXTokenKind> {
        public CXTokenKind(int e) {
            super(e);
        }

        public CXTokenKind(Pointer<CXTokenKind> e) {
            super(e);
        }

        public CXTokenKind(CXTokenKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXTokenKind> list(Pointer<CXTokenKind> ptr) {
            return new VI32List<>(ptr, CXTokenKind::new);
        }

        public static VI32List<CXTokenKind> list(Pointer<CXTokenKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXTokenKind::new);
        }

        public static VI32List<CXTokenKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXTokenKind::new);
        }

        public static VI32List<CXTokenKind> list(SegmentAllocator allocator, CXTokenKind[] c) {
            return new VI32List<>(allocator, c, CXTokenKind::new);
        }

        public static VI32List<CXTokenKind> list(SegmentAllocator allocator, Collection<CXTokenKind> c) {
            return new VI32List<>(allocator, c, CXTokenKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXTokenKind e) {
            return LibclangEnums.enumToString(CXTokenKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXTokenKind that && that.value().intValue() == value().intValue();
        }


        public static final CXTokenKind CXToken_Punctuation = new CXTokenKind(0);
        public static final CXTokenKind CXToken_Keyword = new CXTokenKind(1);
        public static final CXTokenKind CXToken_Identifier = new CXTokenKind(2);
        public static final CXTokenKind CXToken_Literal = new CXTokenKind(3);
        public static final CXTokenKind CXToken_Comment = new CXTokenKind(4);

    }

    public static final class CXCompletionChunkKind extends VI32Basic<CXCompletionChunkKind> {
        public CXCompletionChunkKind(int e) {
            super(e);
        }

        public CXCompletionChunkKind(Pointer<CXCompletionChunkKind> e) {
            super(e);
        }

        public CXCompletionChunkKind(CXCompletionChunkKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXCompletionChunkKind> list(Pointer<CXCompletionChunkKind> ptr) {
            return new VI32List<>(ptr, CXCompletionChunkKind::new);
        }

        public static VI32List<CXCompletionChunkKind> list(Pointer<CXCompletionChunkKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXCompletionChunkKind::new);
        }

        public static VI32List<CXCompletionChunkKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXCompletionChunkKind::new);
        }

        public static VI32List<CXCompletionChunkKind> list(SegmentAllocator allocator, CXCompletionChunkKind[] c) {
            return new VI32List<>(allocator, c, CXCompletionChunkKind::new);
        }

        public static VI32List<CXCompletionChunkKind> list(SegmentAllocator allocator, Collection<CXCompletionChunkKind> c) {
            return new VI32List<>(allocator, c, CXCompletionChunkKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXCompletionChunkKind e) {
            return LibclangEnums.enumToString(CXCompletionChunkKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXCompletionChunkKind that && that.value().intValue() == value().intValue();
        }


        public static final CXCompletionChunkKind CXCompletionChunk_Optional = new CXCompletionChunkKind(0);
        public static final CXCompletionChunkKind CXCompletionChunk_TypedText = new CXCompletionChunkKind(1);
        public static final CXCompletionChunkKind CXCompletionChunk_Text = new CXCompletionChunkKind(2);
        public static final CXCompletionChunkKind CXCompletionChunk_Placeholder = new CXCompletionChunkKind(3);
        public static final CXCompletionChunkKind CXCompletionChunk_Informative = new CXCompletionChunkKind(4);
        public static final CXCompletionChunkKind CXCompletionChunk_CurrentParameter = new CXCompletionChunkKind(5);
        public static final CXCompletionChunkKind CXCompletionChunk_LeftParen = new CXCompletionChunkKind(6);
        public static final CXCompletionChunkKind CXCompletionChunk_RightParen = new CXCompletionChunkKind(7);
        public static final CXCompletionChunkKind CXCompletionChunk_LeftBracket = new CXCompletionChunkKind(8);
        public static final CXCompletionChunkKind CXCompletionChunk_RightBracket = new CXCompletionChunkKind(9);
        public static final CXCompletionChunkKind CXCompletionChunk_LeftBrace = new CXCompletionChunkKind(10);
        public static final CXCompletionChunkKind CXCompletionChunk_RightBrace = new CXCompletionChunkKind(11);
        public static final CXCompletionChunkKind CXCompletionChunk_LeftAngle = new CXCompletionChunkKind(12);
        public static final CXCompletionChunkKind CXCompletionChunk_RightAngle = new CXCompletionChunkKind(13);
        public static final CXCompletionChunkKind CXCompletionChunk_Comma = new CXCompletionChunkKind(14);
        public static final CXCompletionChunkKind CXCompletionChunk_ResultType = new CXCompletionChunkKind(15);
        public static final CXCompletionChunkKind CXCompletionChunk_Colon = new CXCompletionChunkKind(16);
        public static final CXCompletionChunkKind CXCompletionChunk_SemiColon = new CXCompletionChunkKind(17);
        public static final CXCompletionChunkKind CXCompletionChunk_Equal = new CXCompletionChunkKind(18);
        public static final CXCompletionChunkKind CXCompletionChunk_HorizontalSpace = new CXCompletionChunkKind(19);
        public static final CXCompletionChunkKind CXCompletionChunk_VerticalSpace = new CXCompletionChunkKind(20);

    }

    public static final class CXCodeComplete_Flags extends VI32Basic<CXCodeComplete_Flags> {
        public CXCodeComplete_Flags(int e) {
            super(e);
        }

        public CXCodeComplete_Flags(Pointer<CXCodeComplete_Flags> e) {
            super(e);
        }

        public CXCodeComplete_Flags(CXCodeComplete_Flags e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXCodeComplete_Flags> list(Pointer<CXCodeComplete_Flags> ptr) {
            return new VI32List<>(ptr, CXCodeComplete_Flags::new);
        }

        public static VI32List<CXCodeComplete_Flags> list(Pointer<CXCodeComplete_Flags> ptr, long length) {
            return new VI32List<>(ptr, length, CXCodeComplete_Flags::new);
        }

        public static VI32List<CXCodeComplete_Flags> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXCodeComplete_Flags::new);
        }

        public static VI32List<CXCodeComplete_Flags> list(SegmentAllocator allocator, CXCodeComplete_Flags[] c) {
            return new VI32List<>(allocator, c, CXCodeComplete_Flags::new);
        }

        public static VI32List<CXCodeComplete_Flags> list(SegmentAllocator allocator, Collection<CXCodeComplete_Flags> c) {
            return new VI32List<>(allocator, c, CXCodeComplete_Flags::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXCodeComplete_Flags e) {
            return LibclangEnums.enumToString(CXCodeComplete_Flags.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXCodeComplete_Flags that && that.value().intValue() == value().intValue();
        }


        public static final CXCodeComplete_Flags CXCodeComplete_IncludeMacros = new CXCodeComplete_Flags(1);
        public static final CXCodeComplete_Flags CXCodeComplete_IncludeCodePatterns = new CXCodeComplete_Flags(2);
        public static final CXCodeComplete_Flags CXCodeComplete_IncludeBriefComments = new CXCodeComplete_Flags(4);
        public static final CXCodeComplete_Flags CXCodeComplete_SkipPreamble = new CXCodeComplete_Flags(8);
        public static final CXCodeComplete_Flags CXCodeComplete_IncludeCompletionsWithFixIts = new CXCodeComplete_Flags(16);

    }

    public static final class CXCompletionContext extends VI32Basic<CXCompletionContext> {
        public CXCompletionContext(int e) {
            super(e);
        }

        public CXCompletionContext(Pointer<CXCompletionContext> e) {
            super(e);
        }

        public CXCompletionContext(CXCompletionContext e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXCompletionContext> list(Pointer<CXCompletionContext> ptr) {
            return new VI32List<>(ptr, CXCompletionContext::new);
        }

        public static VI32List<CXCompletionContext> list(Pointer<CXCompletionContext> ptr, long length) {
            return new VI32List<>(ptr, length, CXCompletionContext::new);
        }

        public static VI32List<CXCompletionContext> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXCompletionContext::new);
        }

        public static VI32List<CXCompletionContext> list(SegmentAllocator allocator, CXCompletionContext[] c) {
            return new VI32List<>(allocator, c, CXCompletionContext::new);
        }

        public static VI32List<CXCompletionContext> list(SegmentAllocator allocator, Collection<CXCompletionContext> c) {
            return new VI32List<>(allocator, c, CXCompletionContext::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXCompletionContext e) {
            return LibclangEnums.enumToString(CXCompletionContext.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXCompletionContext that && that.value().intValue() == value().intValue();
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

    public static final class CXEvalResultKind extends VI32Basic<CXEvalResultKind> {
        public CXEvalResultKind(int e) {
            super(e);
        }

        public CXEvalResultKind(Pointer<CXEvalResultKind> e) {
            super(e);
        }

        public CXEvalResultKind(CXEvalResultKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXEvalResultKind> list(Pointer<CXEvalResultKind> ptr) {
            return new VI32List<>(ptr, CXEvalResultKind::new);
        }

        public static VI32List<CXEvalResultKind> list(Pointer<CXEvalResultKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXEvalResultKind::new);
        }

        public static VI32List<CXEvalResultKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXEvalResultKind::new);
        }

        public static VI32List<CXEvalResultKind> list(SegmentAllocator allocator, CXEvalResultKind[] c) {
            return new VI32List<>(allocator, c, CXEvalResultKind::new);
        }

        public static VI32List<CXEvalResultKind> list(SegmentAllocator allocator, Collection<CXEvalResultKind> c) {
            return new VI32List<>(allocator, c, CXEvalResultKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXEvalResultKind e) {
            return LibclangEnums.enumToString(CXEvalResultKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXEvalResultKind that && that.value().intValue() == value().intValue();
        }


        public static final CXEvalResultKind CXEval_Int = new CXEvalResultKind(1);
        public static final CXEvalResultKind CXEval_Float = new CXEvalResultKind(2);
        public static final CXEvalResultKind CXEval_ObjCStrLiteral = new CXEvalResultKind(3);
        public static final CXEvalResultKind CXEval_StrLiteral = new CXEvalResultKind(4);
        public static final CXEvalResultKind CXEval_CFStr = new CXEvalResultKind(5);
        public static final CXEvalResultKind CXEval_Other = new CXEvalResultKind(6);
        public static final CXEvalResultKind CXEval_UnExposed = new CXEvalResultKind(0);

    }

    public static final class CXVisitorResult extends VI32Basic<CXVisitorResult> {
        public CXVisitorResult(int e) {
            super(e);
        }

        public CXVisitorResult(Pointer<CXVisitorResult> e) {
            super(e);
        }

        public CXVisitorResult(CXVisitorResult e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXVisitorResult> list(Pointer<CXVisitorResult> ptr) {
            return new VI32List<>(ptr, CXVisitorResult::new);
        }

        public static VI32List<CXVisitorResult> list(Pointer<CXVisitorResult> ptr, long length) {
            return new VI32List<>(ptr, length, CXVisitorResult::new);
        }

        public static VI32List<CXVisitorResult> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXVisitorResult::new);
        }

        public static VI32List<CXVisitorResult> list(SegmentAllocator allocator, CXVisitorResult[] c) {
            return new VI32List<>(allocator, c, CXVisitorResult::new);
        }

        public static VI32List<CXVisitorResult> list(SegmentAllocator allocator, Collection<CXVisitorResult> c) {
            return new VI32List<>(allocator, c, CXVisitorResult::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXVisitorResult e) {
            return LibclangEnums.enumToString(CXVisitorResult.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXVisitorResult that && that.value().intValue() == value().intValue();
        }


        public static final CXVisitorResult CXVisit_Break = new CXVisitorResult(0);
        public static final CXVisitorResult CXVisit_Continue = new CXVisitorResult(1);

    }

    public static final class CXResult extends VI32Basic<CXResult> {
        public CXResult(int e) {
            super(e);
        }

        public CXResult(Pointer<CXResult> e) {
            super(e);
        }

        public CXResult(CXResult e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXResult> list(Pointer<CXResult> ptr) {
            return new VI32List<>(ptr, CXResult::new);
        }

        public static VI32List<CXResult> list(Pointer<CXResult> ptr, long length) {
            return new VI32List<>(ptr, length, CXResult::new);
        }

        public static VI32List<CXResult> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXResult::new);
        }

        public static VI32List<CXResult> list(SegmentAllocator allocator, CXResult[] c) {
            return new VI32List<>(allocator, c, CXResult::new);
        }

        public static VI32List<CXResult> list(SegmentAllocator allocator, Collection<CXResult> c) {
            return new VI32List<>(allocator, c, CXResult::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXResult e) {
            return LibclangEnums.enumToString(CXResult.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXResult that && that.value().intValue() == value().intValue();
        }


        public static final CXResult CXResult_Success = new CXResult(0);
        public static final CXResult CXResult_Invalid = new CXResult(1);
        public static final CXResult CXResult_VisitBreak = new CXResult(2);

    }

    public static final class CXIdxEntityKind extends VI32Basic<CXIdxEntityKind> {
        public CXIdxEntityKind(int e) {
            super(e);
        }

        public CXIdxEntityKind(Pointer<CXIdxEntityKind> e) {
            super(e);
        }

        public CXIdxEntityKind(CXIdxEntityKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXIdxEntityKind> list(Pointer<CXIdxEntityKind> ptr) {
            return new VI32List<>(ptr, CXIdxEntityKind::new);
        }

        public static VI32List<CXIdxEntityKind> list(Pointer<CXIdxEntityKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXIdxEntityKind::new);
        }

        public static VI32List<CXIdxEntityKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXIdxEntityKind::new);
        }

        public static VI32List<CXIdxEntityKind> list(SegmentAllocator allocator, CXIdxEntityKind[] c) {
            return new VI32List<>(allocator, c, CXIdxEntityKind::new);
        }

        public static VI32List<CXIdxEntityKind> list(SegmentAllocator allocator, Collection<CXIdxEntityKind> c) {
            return new VI32List<>(allocator, c, CXIdxEntityKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXIdxEntityKind e) {
            return LibclangEnums.enumToString(CXIdxEntityKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXIdxEntityKind that && that.value().intValue() == value().intValue();
        }


        public static final CXIdxEntityKind CXIdxEntity_Unexposed = new CXIdxEntityKind(0);
        public static final CXIdxEntityKind CXIdxEntity_Typedef = new CXIdxEntityKind(1);
        public static final CXIdxEntityKind CXIdxEntity_Function = new CXIdxEntityKind(2);
        public static final CXIdxEntityKind CXIdxEntity_Variable = new CXIdxEntityKind(3);
        public static final CXIdxEntityKind CXIdxEntity_Field = new CXIdxEntityKind(4);
        public static final CXIdxEntityKind CXIdxEntity_EnumConstant = new CXIdxEntityKind(5);
        public static final CXIdxEntityKind CXIdxEntity_ObjCClass = new CXIdxEntityKind(6);
        public static final CXIdxEntityKind CXIdxEntity_ObjCProtocol = new CXIdxEntityKind(7);
        public static final CXIdxEntityKind CXIdxEntity_ObjCCategory = new CXIdxEntityKind(8);
        public static final CXIdxEntityKind CXIdxEntity_ObjCInstanceMethod = new CXIdxEntityKind(9);
        public static final CXIdxEntityKind CXIdxEntity_ObjCClassMethod = new CXIdxEntityKind(10);
        public static final CXIdxEntityKind CXIdxEntity_ObjCProperty = new CXIdxEntityKind(11);
        public static final CXIdxEntityKind CXIdxEntity_ObjCIvar = new CXIdxEntityKind(12);
        public static final CXIdxEntityKind CXIdxEntity_Enum = new CXIdxEntityKind(13);
        public static final CXIdxEntityKind CXIdxEntity_Struct = new CXIdxEntityKind(14);
        public static final CXIdxEntityKind CXIdxEntity_Union = new CXIdxEntityKind(15);
        public static final CXIdxEntityKind CXIdxEntity_CXXClass = new CXIdxEntityKind(16);
        public static final CXIdxEntityKind CXIdxEntity_CXXNamespace = new CXIdxEntityKind(17);
        public static final CXIdxEntityKind CXIdxEntity_CXXNamespaceAlias = new CXIdxEntityKind(18);
        public static final CXIdxEntityKind CXIdxEntity_CXXStaticVariable = new CXIdxEntityKind(19);
        public static final CXIdxEntityKind CXIdxEntity_CXXStaticMethod = new CXIdxEntityKind(20);
        public static final CXIdxEntityKind CXIdxEntity_CXXInstanceMethod = new CXIdxEntityKind(21);
        public static final CXIdxEntityKind CXIdxEntity_CXXConstructor = new CXIdxEntityKind(22);
        public static final CXIdxEntityKind CXIdxEntity_CXXDestructor = new CXIdxEntityKind(23);
        public static final CXIdxEntityKind CXIdxEntity_CXXConversionFunction = new CXIdxEntityKind(24);
        public static final CXIdxEntityKind CXIdxEntity_CXXTypeAlias = new CXIdxEntityKind(25);
        public static final CXIdxEntityKind CXIdxEntity_CXXInterface = new CXIdxEntityKind(26);
        public static final CXIdxEntityKind CXIdxEntity_CXXConcept = new CXIdxEntityKind(27);

    }

    public static final class CXIdxEntityLanguage extends VI32Basic<CXIdxEntityLanguage> {
        public CXIdxEntityLanguage(int e) {
            super(e);
        }

        public CXIdxEntityLanguage(Pointer<CXIdxEntityLanguage> e) {
            super(e);
        }

        public CXIdxEntityLanguage(CXIdxEntityLanguage e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXIdxEntityLanguage> list(Pointer<CXIdxEntityLanguage> ptr) {
            return new VI32List<>(ptr, CXIdxEntityLanguage::new);
        }

        public static VI32List<CXIdxEntityLanguage> list(Pointer<CXIdxEntityLanguage> ptr, long length) {
            return new VI32List<>(ptr, length, CXIdxEntityLanguage::new);
        }

        public static VI32List<CXIdxEntityLanguage> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXIdxEntityLanguage::new);
        }

        public static VI32List<CXIdxEntityLanguage> list(SegmentAllocator allocator, CXIdxEntityLanguage[] c) {
            return new VI32List<>(allocator, c, CXIdxEntityLanguage::new);
        }

        public static VI32List<CXIdxEntityLanguage> list(SegmentAllocator allocator, Collection<CXIdxEntityLanguage> c) {
            return new VI32List<>(allocator, c, CXIdxEntityLanguage::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXIdxEntityLanguage e) {
            return LibclangEnums.enumToString(CXIdxEntityLanguage.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXIdxEntityLanguage that && that.value().intValue() == value().intValue();
        }


        public static final CXIdxEntityLanguage CXIdxEntityLang_None = new CXIdxEntityLanguage(0);
        public static final CXIdxEntityLanguage CXIdxEntityLang_C = new CXIdxEntityLanguage(1);
        public static final CXIdxEntityLanguage CXIdxEntityLang_ObjC = new CXIdxEntityLanguage(2);
        public static final CXIdxEntityLanguage CXIdxEntityLang_CXX = new CXIdxEntityLanguage(3);
        public static final CXIdxEntityLanguage CXIdxEntityLang_Swift = new CXIdxEntityLanguage(4);

    }

    public static final class CXIdxEntityCXXTemplateKind extends VI32Basic<CXIdxEntityCXXTemplateKind> {
        public CXIdxEntityCXXTemplateKind(int e) {
            super(e);
        }

        public CXIdxEntityCXXTemplateKind(Pointer<CXIdxEntityCXXTemplateKind> e) {
            super(e);
        }

        public CXIdxEntityCXXTemplateKind(CXIdxEntityCXXTemplateKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXIdxEntityCXXTemplateKind> list(Pointer<CXIdxEntityCXXTemplateKind> ptr) {
            return new VI32List<>(ptr, CXIdxEntityCXXTemplateKind::new);
        }

        public static VI32List<CXIdxEntityCXXTemplateKind> list(Pointer<CXIdxEntityCXXTemplateKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXIdxEntityCXXTemplateKind::new);
        }

        public static VI32List<CXIdxEntityCXXTemplateKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXIdxEntityCXXTemplateKind::new);
        }

        public static VI32List<CXIdxEntityCXXTemplateKind> list(SegmentAllocator allocator, CXIdxEntityCXXTemplateKind[] c) {
            return new VI32List<>(allocator, c, CXIdxEntityCXXTemplateKind::new);
        }

        public static VI32List<CXIdxEntityCXXTemplateKind> list(SegmentAllocator allocator, Collection<CXIdxEntityCXXTemplateKind> c) {
            return new VI32List<>(allocator, c, CXIdxEntityCXXTemplateKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXIdxEntityCXXTemplateKind e) {
            return LibclangEnums.enumToString(CXIdxEntityCXXTemplateKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXIdxEntityCXXTemplateKind that && that.value().intValue() == value().intValue();
        }


        public static final CXIdxEntityCXXTemplateKind CXIdxEntity_NonTemplate = new CXIdxEntityCXXTemplateKind(0);
        public static final CXIdxEntityCXXTemplateKind CXIdxEntity_Template = new CXIdxEntityCXXTemplateKind(1);
        public static final CXIdxEntityCXXTemplateKind CXIdxEntity_TemplatePartialSpecialization = new CXIdxEntityCXXTemplateKind(2);
        public static final CXIdxEntityCXXTemplateKind CXIdxEntity_TemplateSpecialization = new CXIdxEntityCXXTemplateKind(3);

    }

    public static final class CXIdxAttrKind extends VI32Basic<CXIdxAttrKind> {
        public CXIdxAttrKind(int e) {
            super(e);
        }

        public CXIdxAttrKind(Pointer<CXIdxAttrKind> e) {
            super(e);
        }

        public CXIdxAttrKind(CXIdxAttrKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXIdxAttrKind> list(Pointer<CXIdxAttrKind> ptr) {
            return new VI32List<>(ptr, CXIdxAttrKind::new);
        }

        public static VI32List<CXIdxAttrKind> list(Pointer<CXIdxAttrKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXIdxAttrKind::new);
        }

        public static VI32List<CXIdxAttrKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXIdxAttrKind::new);
        }

        public static VI32List<CXIdxAttrKind> list(SegmentAllocator allocator, CXIdxAttrKind[] c) {
            return new VI32List<>(allocator, c, CXIdxAttrKind::new);
        }

        public static VI32List<CXIdxAttrKind> list(SegmentAllocator allocator, Collection<CXIdxAttrKind> c) {
            return new VI32List<>(allocator, c, CXIdxAttrKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXIdxAttrKind e) {
            return LibclangEnums.enumToString(CXIdxAttrKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXIdxAttrKind that && that.value().intValue() == value().intValue();
        }


        public static final CXIdxAttrKind CXIdxAttr_Unexposed = new CXIdxAttrKind(0);
        public static final CXIdxAttrKind CXIdxAttr_IBAction = new CXIdxAttrKind(1);
        public static final CXIdxAttrKind CXIdxAttr_IBOutlet = new CXIdxAttrKind(2);
        public static final CXIdxAttrKind CXIdxAttr_IBOutletCollection = new CXIdxAttrKind(3);

    }

    public static final class CXIdxDeclInfoFlags extends VI32Basic<CXIdxDeclInfoFlags> {
        public CXIdxDeclInfoFlags(int e) {
            super(e);
        }

        public CXIdxDeclInfoFlags(Pointer<CXIdxDeclInfoFlags> e) {
            super(e);
        }

        public CXIdxDeclInfoFlags(CXIdxDeclInfoFlags e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXIdxDeclInfoFlags> list(Pointer<CXIdxDeclInfoFlags> ptr) {
            return new VI32List<>(ptr, CXIdxDeclInfoFlags::new);
        }

        public static VI32List<CXIdxDeclInfoFlags> list(Pointer<CXIdxDeclInfoFlags> ptr, long length) {
            return new VI32List<>(ptr, length, CXIdxDeclInfoFlags::new);
        }

        public static VI32List<CXIdxDeclInfoFlags> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXIdxDeclInfoFlags::new);
        }

        public static VI32List<CXIdxDeclInfoFlags> list(SegmentAllocator allocator, CXIdxDeclInfoFlags[] c) {
            return new VI32List<>(allocator, c, CXIdxDeclInfoFlags::new);
        }

        public static VI32List<CXIdxDeclInfoFlags> list(SegmentAllocator allocator, Collection<CXIdxDeclInfoFlags> c) {
            return new VI32List<>(allocator, c, CXIdxDeclInfoFlags::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXIdxDeclInfoFlags e) {
            return LibclangEnums.enumToString(CXIdxDeclInfoFlags.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXIdxDeclInfoFlags that && that.value().intValue() == value().intValue();
        }


        public static final CXIdxDeclInfoFlags CXIdxDeclFlag_Skipped = new CXIdxDeclInfoFlags(1);

    }

    public static final class CXIdxObjCContainerKind extends VI32Basic<CXIdxObjCContainerKind> {
        public CXIdxObjCContainerKind(int e) {
            super(e);
        }

        public CXIdxObjCContainerKind(Pointer<CXIdxObjCContainerKind> e) {
            super(e);
        }

        public CXIdxObjCContainerKind(CXIdxObjCContainerKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXIdxObjCContainerKind> list(Pointer<CXIdxObjCContainerKind> ptr) {
            return new VI32List<>(ptr, CXIdxObjCContainerKind::new);
        }

        public static VI32List<CXIdxObjCContainerKind> list(Pointer<CXIdxObjCContainerKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXIdxObjCContainerKind::new);
        }

        public static VI32List<CXIdxObjCContainerKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXIdxObjCContainerKind::new);
        }

        public static VI32List<CXIdxObjCContainerKind> list(SegmentAllocator allocator, CXIdxObjCContainerKind[] c) {
            return new VI32List<>(allocator, c, CXIdxObjCContainerKind::new);
        }

        public static VI32List<CXIdxObjCContainerKind> list(SegmentAllocator allocator, Collection<CXIdxObjCContainerKind> c) {
            return new VI32List<>(allocator, c, CXIdxObjCContainerKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXIdxObjCContainerKind e) {
            return LibclangEnums.enumToString(CXIdxObjCContainerKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXIdxObjCContainerKind that && that.value().intValue() == value().intValue();
        }


        public static final CXIdxObjCContainerKind CXIdxObjCContainer_ForwardRef = new CXIdxObjCContainerKind(0);
        public static final CXIdxObjCContainerKind CXIdxObjCContainer_Interface = new CXIdxObjCContainerKind(1);
        public static final CXIdxObjCContainerKind CXIdxObjCContainer_Implementation = new CXIdxObjCContainerKind(2);

    }

    public static final class CXIdxEntityRefKind extends VI32Basic<CXIdxEntityRefKind> {
        public CXIdxEntityRefKind(int e) {
            super(e);
        }

        public CXIdxEntityRefKind(Pointer<CXIdxEntityRefKind> e) {
            super(e);
        }

        public CXIdxEntityRefKind(CXIdxEntityRefKind e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXIdxEntityRefKind> list(Pointer<CXIdxEntityRefKind> ptr) {
            return new VI32List<>(ptr, CXIdxEntityRefKind::new);
        }

        public static VI32List<CXIdxEntityRefKind> list(Pointer<CXIdxEntityRefKind> ptr, long length) {
            return new VI32List<>(ptr, length, CXIdxEntityRefKind::new);
        }

        public static VI32List<CXIdxEntityRefKind> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXIdxEntityRefKind::new);
        }

        public static VI32List<CXIdxEntityRefKind> list(SegmentAllocator allocator, CXIdxEntityRefKind[] c) {
            return new VI32List<>(allocator, c, CXIdxEntityRefKind::new);
        }

        public static VI32List<CXIdxEntityRefKind> list(SegmentAllocator allocator, Collection<CXIdxEntityRefKind> c) {
            return new VI32List<>(allocator, c, CXIdxEntityRefKind::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXIdxEntityRefKind e) {
            return LibclangEnums.enumToString(CXIdxEntityRefKind.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXIdxEntityRefKind that && that.value().intValue() == value().intValue();
        }


        public static final CXIdxEntityRefKind CXIdxEntityRef_Direct = new CXIdxEntityRefKind(1);
        public static final CXIdxEntityRefKind CXIdxEntityRef_Implicit = new CXIdxEntityRefKind(2);

    }

    public static final class CXSymbolRole extends VI32Basic<CXSymbolRole> {
        public CXSymbolRole(int e) {
            super(e);
        }

        public CXSymbolRole(Pointer<CXSymbolRole> e) {
            super(e);
        }

        public CXSymbolRole(CXSymbolRole e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXSymbolRole> list(Pointer<CXSymbolRole> ptr) {
            return new VI32List<>(ptr, CXSymbolRole::new);
        }

        public static VI32List<CXSymbolRole> list(Pointer<CXSymbolRole> ptr, long length) {
            return new VI32List<>(ptr, length, CXSymbolRole::new);
        }

        public static VI32List<CXSymbolRole> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXSymbolRole::new);
        }

        public static VI32List<CXSymbolRole> list(SegmentAllocator allocator, CXSymbolRole[] c) {
            return new VI32List<>(allocator, c, CXSymbolRole::new);
        }

        public static VI32List<CXSymbolRole> list(SegmentAllocator allocator, Collection<CXSymbolRole> c) {
            return new VI32List<>(allocator, c, CXSymbolRole::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXSymbolRole e) {
            return LibclangEnums.enumToString(CXSymbolRole.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXSymbolRole that && that.value().intValue() == value().intValue();
        }


        public static final CXSymbolRole CXSymbolRole_None = new CXSymbolRole(0);
        public static final CXSymbolRole CXSymbolRole_Declaration = new CXSymbolRole(1);
        public static final CXSymbolRole CXSymbolRole_Definition = new CXSymbolRole(2);
        public static final CXSymbolRole CXSymbolRole_Reference = new CXSymbolRole(4);
        public static final CXSymbolRole CXSymbolRole_Read = new CXSymbolRole(8);
        public static final CXSymbolRole CXSymbolRole_Write = new CXSymbolRole(16);
        public static final CXSymbolRole CXSymbolRole_Call = new CXSymbolRole(32);
        public static final CXSymbolRole CXSymbolRole_Dynamic = new CXSymbolRole(64);
        public static final CXSymbolRole CXSymbolRole_AddressOf = new CXSymbolRole(128);
        public static final CXSymbolRole CXSymbolRole_Implicit = new CXSymbolRole(256);

    }

    public static final class CXIndexOptFlags extends VI32Basic<CXIndexOptFlags> {
        public CXIndexOptFlags(int e) {
            super(e);
        }

        public CXIndexOptFlags(Pointer<CXIndexOptFlags> e) {
            super(e);
        }

        public CXIndexOptFlags(CXIndexOptFlags e) {
            super(e);
            str = e.str;
        }

        public static VI32List<CXIndexOptFlags> list(Pointer<CXIndexOptFlags> ptr) {
            return new VI32List<>(ptr, CXIndexOptFlags::new);
        }

        public static VI32List<CXIndexOptFlags> list(Pointer<CXIndexOptFlags> ptr, long length) {
            return new VI32List<>(ptr, length, CXIndexOptFlags::new);
        }

        public static VI32List<CXIndexOptFlags> list(SegmentAllocator allocator, long length) {
            return new VI32List<>(allocator, length, CXIndexOptFlags::new);
        }

        public static VI32List<CXIndexOptFlags> list(SegmentAllocator allocator, CXIndexOptFlags[] c) {
            return new VI32List<>(allocator, c, CXIndexOptFlags::new);
        }

        public static VI32List<CXIndexOptFlags> list(SegmentAllocator allocator, Collection<CXIndexOptFlags> c) {
            return new VI32List<>(allocator, c, CXIndexOptFlags::new);
        }

        private String str;

        @Override
        public String toString() {
            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
        }

        public static Optional<String> enumToString(CXIndexOptFlags e) {
            return LibclangEnums.enumToString(CXIndexOptFlags.class, e);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CXIndexOptFlags that && that.value().intValue() == value().intValue();
        }


        public static final CXIndexOptFlags CXIndexOpt_None = new CXIndexOptFlags(0);
        public static final CXIndexOptFlags CXIndexOpt_SuppressRedundantRefs = new CXIndexOptFlags(1);
        public static final CXIndexOptFlags CXIndexOpt_IndexFunctionLocalSymbols = new CXIndexOptFlags(2);
        public static final CXIndexOptFlags CXIndexOpt_IndexImplicitTemplateInstantiations = new CXIndexOptFlags(4);
        public static final CXIndexOptFlags CXIndexOpt_SuppressWarnings = new CXIndexOptFlags(8);
        public static final CXIndexOptFlags CXIndexOpt_SkipParsedBodiesInSession = new CXIndexOptFlags(16);

    }

}