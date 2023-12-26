//
// Created by nettal on 23-11-18.
//

#include "TypedefGenerator.h"
#include "SharedGenerator.h"
#include "StructGenerator.h"
#include "Value.h"
#include "../analyser/Analyser.h"
#include "Generator.h"

#include <utility>

namespace jbindgen {
    TypedefGenerator::TypedefGenerator(NormalTypedefDeclaration declaration, const GeneratorConfig &config,
                                       std::shared_ptr<TypeManager> typeManager) :
            declaration(std::move(declaration)),
            analyser(config.analyser),
            typeManager(std::move(typeManager)),
            baseSharedPackageName(config.shared.basePackageName),
            config(config) {
    }

    void TypedefGenerator::genStruct(const std::string &className, CXType type) {
        auto strutDeclaration = clang_getTypeDeclaration(clang_getCanonicalType(type));
        switch (strutDeclaration.kind) {
            case CXCursor_StructDecl:
                for (const auto &item: analyser.structs) {
                    if (clang_equalCursors(item.structType.cursor, strutDeclaration)) {
                        StructGenerator structGenerator(item,
                                                        typeManager, config);
                        structGenerator.build(className);
                        return;
                    }
                }
            case CXCursor_UnionDecl:
                for (const auto &item: analyser.unions) {
                    if (clang_equalCursors(item.structType.cursor, strutDeclaration)) {
                        StructGenerator structGenerator(item,
                                                        typeManager, config);
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
        return ::jbindgen::getSubValueContentSpecialized(std::move(className), type.wrapper() + "Basic",
                                                         type.list_type(),
                                                         baseSharedPackageName + "." + type.list_type(),
                                                         config.shared.valueInterfaceFullyQualifiedName,
                                                         config.shared.pointerInterfaceFullyQualifiedName,
                                                         config.shared.valuesPackageName
                                                         + "." + type.wrapper() + "Basic",
                                                         type.primitive(), type.objectPrimitiveName());
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
                                                         className, baseSharedPackageName + ".NList",
                                                         baseSharedPackageName + ".natives." +
                                                         type.objectPrimitiveName()));
    }

    std::string
    getStructListContent(std::string className, const StructDeclaration &structDeclaration, long elementCount) {
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
        for (const auto &item: analyser.enums) {
            if (item.getName() == declaration.mappedStr) {
                std::cout << "TypedefGenerator: skip duplicated typedef from enum: " << declaration.mappedStr
                          << std::endl;
                return;
            }
        }
        std::string genResult = std::vformat("package {};\n\n",
                                             std::make_format_args(config.typedefs.valuePackageName));
        auto inIgnoreList = std::any_of(value::JAVA_UNSUPPORTED.begin(), value::JAVA_UNSUPPORTED.end(),
                                        [&](const auto &item) {
                                            return item == declaration.mappedStr;
                                        });
        auto generateDir = config.typedefs.valuesDir;
        if (inIgnoreList)
            genResult += getFakeClassContent(declaration.mappedStr);
        else {
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
                case value::method::copy_by_function_ptr_call: {
                    // typedef function declaration
//                auto name = toCXTypeFunctionPtrName(encode.type, analyser);
                    return;
                }
                case value::method::copy_by_array_call: {
                    generateDir = config.structs.structsDir;//it is struct like
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
                case value::method::copy_by_ptr_dest_copy_call: {
                    genStruct(declaration.mappedStr, encode.type);
                    return;
                }
                case value::method::copy_by_ptr_copy_call: {
                    auto pointer = encode.type;
                    auto c1 = clang_getPointeeType(pointer);
                    if (c1.kind != CXType_FunctionProto) {
                        // eg: typedef char *the_ptr;
                        genResult += getValueContent(declaration.mappedStr, value::jext::VPointer);
                        break;
                    }

                    //todo: check whether analyser#typedefFunctions has
                    return;
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
                case value::method::copy_by_value_memory_segment_call:
                    // void* -> someType
                    genResult += getValueContent(declaration.mappedStr, value::jext::VPointer);
                    break;
            }
        }
        if ((*typeManager).isAlreadyGenerated(declaration)) {
            std::cout << "ignore already generated typedef: " << declaration.mappedStr << std::endl;
            return;
        }
        overwriteFile(generateDir + "/" + declaration.mappedStr + ".java", genResult);
    }
} // jbindgen