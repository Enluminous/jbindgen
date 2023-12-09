//
// Created by nettal on 23-11-18.
//

#include "TypedefGenerator.h"
#include "TypedefGeneratorUtils.h"
#include "SharedGenerator.h"
#include "StructGenerator.h"
#include "Value.h"
#include "../analyser/Analyser.h"

#include <utility>

namespace jbindgen {
    TypedefGenerator::TypedefGenerator(NormalTypedefDeclaration declaration, std::string defStructPackageName,
                                       std::string defValuePackageName, std::string defEnumPackageName,
                                       std::string defEnumDir,
                                       std::string defStructDir, std::string defValueDir,
                                       std::string defCallbackPackageName,
                                       std::string defCallbackDir, std::string nativeFunctionPackageName,
                                       std::string sharedValueInterfacePackageName, std::string sharedValuePackageName,
                                       std::string sharedVListPackageName,
                                       const Analyser &analyser,
                                       std::string structsDir, std::string packageName,
                                       FN_structMemberName memberRename,
                                       FN_decodeGetter decodeGetter, FN_decodeSetter decodeSetter,
                                       std::string sharedNListPackageName,
                                       std::string sharedPointerInterfacePackageName,
                                       std::string baseSharedPackageName) :
            declaration(std::move(declaration)),
            defsStructPackageName(std::move(defStructPackageName)),
            defsValuePackageName(std::move(defValuePackageName)),
            defEnumDir(std::move(defEnumDir)),
            defStructDir(std::move(defStructDir)),
            defValueDir(std::move(defValueDir)),
            defsEnumPackageName(std::move(defEnumPackageName)),
            defCallbackDir(std::move(defCallbackDir)),
            defsCallbackPackageName(std::move(defCallbackPackageName)),
            nativeFunctionPackageName(std::move(nativeFunctionPackageName)),
            sharedValuePackageName(std::move(sharedValuePackageName)),
            sharedValueInterfacePackageName(std::move(sharedValueInterfacePackageName)),
            sharedVListPackageName(std::move(sharedVListPackageName)),
            analyser(analyser),
            structsDir(std::move(structsDir)),
            packageName(std::move(packageName)),
            structMemberName(std::move(memberRename)),
            decodeGetter(std::move(decodeGetter)),
            decodeSetter(std::move(decodeSetter)),
            sharedNListPackageName(std::move(sharedNListPackageName)),
            sharedPointerInterfacePackageName(std::move(sharedPointerInterfacePackageName)),
            baseSharedPackageName(std::move(baseSharedPackageName)) {
    }

    void TypedefGenerator::genStruct(const std::string &className, CXType type) {
        auto strutDeclaration = clang_getTypeDeclaration(clang_getCanonicalType(type));
        switch (strutDeclaration.kind) {
            case CXCursor_StructDecl:
                for (const auto &item: analyser.structs) {
                    if (clang_equalCursors(item.structType.cursor, strutDeclaration)) {
                        StructGenerator structGenerator(item, structsDir, defsStructPackageName,
                                                        structMemberName, decodeGetter, decodeSetter, analyser);
                        structGenerator.build(className);
                        return;
                    }
                }
            case CXCursor_UnionDecl:
                for (const auto &item: analyser.unions) {
                    if (clang_equalCursors(item.structType.cursor, strutDeclaration)) {
                        StructGenerator structGenerator(item, structsDir, defsStructPackageName,
                                                        structMemberName, decodeGetter, decodeSetter, analyser);
                        structGenerator.build(className);
                        return;
                    }
                }
            case CXCursor_NoDeclFound:
                std::cout << "Can not find exiting declaration for " << className << ", Ignore" << std::endl;
                return;
            default:
                std::cout << "Can not find exiting declaration for " << className << std::endl;
                assert(0);
        }

    }

    std::string TypedefGenerator::getValueContent(std::string className, value::jbasic::ValueType type) {
        return ::jbindgen::getValueContent(std::move(className), type.objectPrimitiveName(),
                                           type.value_layout(), type.primitive(),
                                           sharedVListPackageName, sharedValuePackageName);
    }

    std::string TypedefGenerator::getFakeClassContent(std::string className) {
        return std::vformat("public class {0} {{\n"
                            "    private {0}() {{\n"
                            "        throw new UnsupportedOperationException();\n"
                            "    }}\n"
                            "}}\n", std::make_format_args(className));
    }

    // int[100] -> someType
    std::string TypedefGenerator::getPrimitiveTypeArrayContent(std::string className, value::jbasic::NativeType type,
                                                               long elementCount) {
        return std::vformat("import {3};\n"
                            "import {4};\n"
                            "\n"
                            "import java.lang.foreign.Arena;\n"
                            "import java.lang.foreign.MemorySegment;\n"
                            "import java.util.function.Consumer;\n"
                            "\n"
                            "public class {3} extends NList<{2}> {\n"
                            "    public static final long ELEMENT_BYTE_SIZE = {0};\n"
                            "\n"
                            "    public static final long LENGTH = {1};\n"
                            "\n"
                            "    public list(MemorySegment ptr, Arena arena, Consumer<MemorySegment> cleanup) {\n"
                            "        super(ptr, LENGTH, arena, cleanup, {2}::new, ELEMENT_BYTE_SIZE);\n"
                            "    }\n"
                            "\n"
                            "    public list(Arena arena) {\n"
                            "        super(arena, LENGTH, {2}::new, ELEMENT_BYTE_SIZE);\n"
                            "    }\n"
                            "\n"
                            "    public list(MemorySegment ptr) {\n"
                            "        super(ptr, {2}::new, ELEMENT_BYTE_SIZE);\n"
                            "    }\n"
                            "\n"
                            "}\n", std::make_format_args(type.byteSize, elementCount, type.objectPrimitiveName(),
                                                         className, sharedNListPackageName,
                                                         baseSharedPackageName + ".natives." +
                                                         type.objectPrimitiveName()));
    }

    std::string getStructListContent(std::string className, StructDeclaration structDeclaration, long elementCount) {
        return std::vformat("public class {0} extends NList<{1}> {\n"
                            "    public static final long ELEMENT_BYTE_SIZE = {2};\n"
                            "\n"
                            "    public static final long LENGTH = {3};\n"
                            "\n"
                            "    public list(MemorySegment ptr, Arena arena, Consumer<MemorySegment> cleanup) {\n"
                            "        super(ptr, LENGTH, arena, cleanup, {1}::new, ELEMENT_BYTE_SIZE);\n"
                            "    }\n"
                            "\n"
                            "    public list(Arena arena) {\n"
                            "        super(arena, LENGTH, {1}::new, ELEMENT_BYTE_SIZE);\n"
                            "    }\n"
                            "\n"
                            "    public list(MemorySegment ptr) {\n"
                            "        super(ptr, {1}::new, ELEMENT_BYTE_SIZE);\n"
                            "    }\n"
                            "\n"
                            "}\n", std::make_format_args(className, structDeclaration.structType.name,
                                                         structDeclaration.structType.byteSize, elementCount));
    }

    void TypedefGenerator::build() {
        using namespace value::jbasic;
        if (DEBUG_LOG)
            std::cout << declaration.oriStr << " -> " << declaration.mappedStr << std::endl;
        std::string genResult = std::vformat("package {};\n\n", std::make_format_args(defsValuePackageName));
        auto encode = value::method::typeCopyWithResultType(declaration.ori);
        switch (encode.copy) {
            case value::method::copy_by_primitive_j_int_call:
            case value::method::copy_by_value_j_int_call:
                genResult += getValueContent(declaration.mappedStr, VInteger);
                break;
            case value::method::copy_by_primitive_j_long_call:
            case value::method::copy_by_value_j_long_call:
                genResult += getValueContent(declaration.mappedStr, VLong);
                break;
            case value::method::copy_by_primitive_j_float_call:
            case value::method::copy_by_value_j_float_call:
                genResult += getValueContent(declaration.mappedStr, VFloat);
                break;
            case value::method::copy_by_primitive_j_double_call:
            case value::method::copy_by_value_j_double_call:
                genResult += getValueContent(declaration.mappedStr, VDouble);
                break;
#if NATIVE_UNSUPPORTED
            case value::method::copy_by_primitive_j_char_call:
            case value::method::copy_by_value_j_char_call:
                assert(0);
                break;
#endif
            case value::method::copy_by_primitive_j_short_call:
            case value::method::copy_by_value_j_short_call:
                genResult += getValueContent(declaration.mappedStr, VShort);
                break;
#if NATIVE_UNSUPPORTED
            case value::method::copy_by_primitive_j_bool_call:
            case value::method::copy_by_value_j_bool_call:
                assert(0);
                break;
#endif
            case value::method::copy_by_value_j_byte_call:
            case value::method::copy_by_primitive_j_byte_call:
                genResult += getValueContent(declaration.mappedStr, VByte);
                break;
            case value::method::copy_by_value_memory_segment_call:
                assert(0);
                break;
            case value::method::copy_by_array_call: {
                auto type = clang_getArrayElementType(encode.type);
                auto decode = value::method::typeCopyWithResultType(type);
                switch (decode.copy) {
                    case value::method::copy_by_primitive_j_int_call:
                    case value::method::copy_by_value_j_int_call:
                        genResult += getPrimitiveTypeArrayContent(declaration.mappedStr, Integer,
                                                                  clang_getNumElements(decode.type));
                        break;
                    case value::method::copy_by_primitive_j_long_call:
                    case value::method::copy_by_value_j_long_call:
                        genResult += getPrimitiveTypeArrayContent(declaration.mappedStr, Long,
                                                                  clang_getNumElements(decode.type));
                        break;
                    case value::method::copy_by_primitive_j_float_call:
                    case value::method::copy_by_value_j_float_call:
                        genResult += getPrimitiveTypeArrayContent(declaration.mappedStr, Float,
                                                                  clang_getNumElements(decode.type));
                        break;
                    case value::method::copy_by_primitive_j_double_call:
                    case value::method::copy_by_value_j_double_call:
                        genResult += getPrimitiveTypeArrayContent(declaration.mappedStr, Double,
                                                                  clang_getNumElements(decode.type));
                        break;
                    case value::method::copy_by_primitive_j_short_call:
                    case value::method::copy_by_value_j_short_call:
                        genResult += getPrimitiveTypeArrayContent(declaration.mappedStr, Short,
                                                                  clang_getNumElements(decode.type));
                        break;
                    case value::method::copy_by_ptr_dest_copy_call: {
                        auto structs = analyser.structs;
                        for (const auto &s: structs) {
                            if (clang_equalCursors(s.structType.cursor, clang_getTypeDeclaration(decode.type))) {
                                genResult += getStructListContent(declaration.mappedStr, s,
                                                                  clang_getNumElements(encode.type));
                                break;
                            }
                            //no declaration found, ignore.
                            return;
                        }
                        break;
                    }
                    default:
                        return;
                        assert(0);
                }
                break;
            }
            case value::method::copy_by_ptr_dest_copy_call:
                genStruct(declaration.mappedStr, encode.type);
                return;
                break;
            case value::method::copy_by_ptr_copy_call: {
                auto pointer = encode.type;
                auto c1 = clang_getPointeeType(pointer);
                if (c1.kind != CXType_FunctionProto) {
                    // eg: typedef char *the_ptr;
                    genResult += getNPointerWithClassName(declaration.mappedStr,
                                                          sharedPointerInterfacePackageName,
                                                          sharedValueInterfacePackageName,
                                                          sharedNListPackageName);
                    break;
                }
                //todo: check whether analyser#typedefFunctions has
                break;
            }
            case value::method::copy_by_ext_int128_call:
                std::cout << "Not Impl" << std::endl;
                assert(0);
                break;
            case value::method::copy_by_ext_long_double_call:
                std::cout << "Not Impl" << std::endl;
                assert(0);
                break;
            case value::method::copy_error:
                assert(0);
                break;
            case value::method::copy_void:
                // void -> someType
                genResult += getFakeClassContent(declaration.mappedStr);
                break;
            case value::method::copy_internal_function_proto:
                assert(0);
                break;
            case value::method::copy_target_void:
                //void or funcPtr -> someType
                genResult += getFakeClassContent(declaration.mappedStr);
                break;
        }
        overwriteFile(defValueDir + "/" + declaration.mappedStr + ".java", genResult);
    }
} // jbindgen