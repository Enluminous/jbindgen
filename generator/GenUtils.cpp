//
// Created by snownf on 23-11-9.
//

#include <fstream>
#include <iostream>
#include <filesystem>
#include "GenUtils.h"
#include "../analyser/Analyser.h"

namespace jbindgen {
    void overwriteFile(const std::string&file, const std::string&content, bool silence) {
        std::filesystem::path parentPath = std::filesystem::path(file).parent_path();
        std::filesystem::create_directories(parentPath);

        //overwrite if exists.
        if (!silence)
            std::cout << "Writing to File " << file << "...";
        std::ofstream outputFile(file);
        if (outputFile.is_open()) {
            outputFile << content;
            outputFile.close();
            if (!silence)
                std::cout << "Done" << std::endl;
        }
        else {
            std::cout << "Can not open file: " << file << std::endl;
            abort();
        }
    }

    std::string generateFakeValueLayout(int64_t byteSize) {
        checkResultSize(byteSize);
        return "MemoryLayout.structLayout(MemoryLayout.sequenceLayout(" + std::to_string(byteSize) + ", " +
               value::jbasic::Byte.value_layout() + "))";
    }

    int64_t getArrayLength(CXType type) {
        if (type.kind == CXType_Elaborated) {
            auto declared = clang_getCursorType(clang_getTypeDeclaration(type));
            return getArrayLength(declared);
        }
        return clang_getNumElements(type);
    }

    std::string toCXCursorString(const CXCursorMap&cxCursorMap, const CXCursor&c) {
        if (!cxCursorMap.contains(c)) {
            std::cout << "UnVisited CXCursor Type: ";
            std::cout << toStringWithoutConst(clang_getCursorType(c)) << std::endl;
            std::cout << "UnVisited CXCursor: ";
            std::cout << toStringIfNullptr(clang_getCursorSpelling(c)) << std::endl;
            assertAppend(0, "");
        }
        auto&decl = cxCursorMap.at(c);
        return decl->getName();
    }

    std::string toCXTypeDeclName(const Analyser&analyser, const CXType&c) {
        if (hasDeclaration(c)) {
            return toCXCursorString(analyser.getCXCursorMap(), clang_getTypeDeclaration(c));
        }
        auto type = removeCXTypeConst(c);
        if (hasDeclaration(type)) {
            return toCXCursorString(analyser.getCXCursorMap(), clang_getTypeDeclaration(type));
        }
        for (const auto&item: analyser.functionPointers) {
            if (clang_equalTypes(item.getCXType(), c)) {
                return item.getName();
            }
        }
        //functions have same signature is equal
        //        for (const auto &item: analyser.functionSymbols) {
        //            if (clang_equalTypes(item.getCXType(), c)) {
        //                return item.getName();
        //            }
        //        }
        for (const auto&item: analyser.typedefFunctions) {
            if (clang_equalTypes(item.getCXType(), c)) {
                return item.getName();
            }
        }
        std::cerr << toStringWithoutConst(c) << std::endl;
        std::cerr << toStringIfNullptr(clang_getCursorSpelling(clang_getTypeDeclaration(c))) << std::endl;
        assertAppend(0, "");
    }

    int32_t getPointeeOrArrayDepth(CXType type) {
        if (isPointer(type.kind)) {
            return getPointeeOrArrayDepth(clang_getPointeeType(type)) + 1;
        }
        if (type.kind == CXType_Elaborated) {
            auto declared = clang_getCursorType(clang_getTypeDeclaration(type));
            return getPointeeOrArrayDepth(declared);
        }
        if (isArrayType(type.kind)) {
            auto ele = clang_getArrayElementType(type);
            return getPointeeOrArrayDepth(ele) + 1;
        }
        return 0;
    }

    CXType toPointeeType(CXType type) {
        if (isPointer(type.kind)) {
            return clang_getPointeeType(type);
        }
        assertAppend(isPointer(type.kind), "");
        abort();
    }

    bool isArrayType(CXTypeKind kind) {
        if (kind == CXType_ConstantArray || kind == CXType_IncompleteArray
            || kind == CXType_VariableArray || kind == CXType_DependentSizedArray)
            return true;
        return false;
    }

    //is function
    bool isFunctionProto(CXTypeKind kind) {
        return kind == CXType_FunctionProto || kind == CXType_FunctionNoProto;
    }

    bool isTypedefFunction(CXType type) {
        assertAppend(hasDeclaration(type), "CXType: " + toStringWithoutConst(type));
        auto canonical = clang_getCanonicalType(type);
        if (!isPointer(canonical.kind))
            return false;
        auto pointee = toPointeeType(canonical);
        return (isFunctionProto(pointee.kind));
    }

    CXType toDeepPointeeOrArrayType(const CXType&type) {
        if (isPointer(type.kind)) {
            auto pointee = toPointeeType(type);
            return toDeepPointeeOrArrayType(pointee);
        }
        if (type.kind == CXType_Elaborated) {
            auto declared = clang_getCursorType(clang_getTypeDeclaration(type));
            return toDeepPointeeOrArrayType(declared);
        }
        if (isArrayType(type.kind))
            return toDeepPointeeOrArrayType(clang_getArrayElementType(type));
        return type;
    }

    std::string toCXTypeName(const CXType&c, const Analyser&analyser) {
        auto copy = value::method::typeCopy(c);
        auto nativeType = copy_method_2_native_type(copy);
        if (nativeType.type != value::jbasic::type_other) {
            // return value::method::native_type_2_value_type(nativeType).wrapper();
            assertAppend(nativeType.type != value::jbasic::type_other, "");
        }
        //ext type
        auto ext = copy_method_2_ext_type(copy);
        if (ext.type != value::jext::EXT_OTHER.type) {
            // return ext.native_wrapper;
            assertAppend(ext.type != value::jext::EXT_OTHER.type, "");
        }
        return toCXTypeDeclName(analyser, c);
    }

    std::string toCXTypeFunctionPtrName(const CXType&c, const Analyser&analyser) {
        if (hasDeclaration(c)) {
            return toCXCursorString(analyser.getCXCursorMap(), clang_getTypeDeclaration(c));
        }
        assertAppend(isPointer(c.kind), "");
        auto pointee = toPointeeType(c);
        assertAppend(isFunctionProto(pointee.kind), "");
        return toCXTypeDeclName(analyser, pointee);
    }

    bool isPointer(CXTypeKind kind) {
        return (kind == CXType_BlockPointer || kind == CXType_Pointer);
    }

    std::string double_to_string(double var) {
        std::stringstream ss;
        ss << std::setprecision(20) << var;
        return ss.str();
    }
} // jbindgen
