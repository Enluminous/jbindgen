//
// Created by snownf on 23-11-9.
//

#include <fstream>
#include <iostream>
#include <filesystem>
#include "GenUtils.h"

namespace jbindgen {
    void overwriteFile(const std::string &file, const std::string &content) {
        std::filesystem::path parentPath = std::filesystem::path(file).parent_path();
        std::filesystem::create_directories(parentPath);

        //overwrite if exists.
        std::cout << "Writing to File " << file << "...";
        std::ofstream outputFile(file);
        if (outputFile.is_open()) {
            outputFile << content;
            outputFile.close();
            std::cout << "Done" << std::endl;
        } else {
            std::cout << "Can not open file: " << file << std::endl;
            abort();
        }
    }

    std::string generateFakeValueLayout(int64_t byteSize) {
        assert(byteSize % 4 == 0);
        assert(byteSize > 0); //currently is signed
        std::string layout;
        return "MemoryLayout.sequenceLayout(" + std::to_string(byteSize / 4) + "," +
               value::jbasic::Integer.value_layout() + ")";
    }

    int64_t getArrayLength(CXType type) {
        if (type.kind == CXType_Elaborated) {
            auto declared = clang_getCursorType(clang_getTypeDeclaration(type));
            return getArrayLength(declared);
        }
        return clang_getNumElements(type);
    }

    std::string toCXCursorString(const CXCursorMap &cxCursorMap, const CXCursor &c) {
        if (!cxCursorMap.contains(c)) {
            std::cout<<"UnVisited CXCursor Type: ";
            std::cout<<toStringWithoutConst(clang_getCursorType(c))<<std::endl;
            std::cout<<"UnVisited CXCursor: ";
            std::cout<<toString(clang_getCursorSpelling(c))<<std::endl;
            assert(0);
        }
        return cxCursorMap.at(c)->getName();
    }

    std::string toCXTypeString(const CXCursorMap &cxCursorMap, const CXType &c) {
        if (!hasDeclaration(c)) {
            return toCXCursorString(cxCursorMap, clang_getTypeDeclaration(c));
        }
        auto type = removeCXTypeConst(c);
        if (hasDeclaration(type)) {
            return toCXCursorString(cxCursorMap, clang_getTypeDeclaration(type));
        }
        assert(0);
    }

    int32_t getPointeeOrArrayDepth(CXType type) {
        if (type.kind == CXType_Pointer || type.kind == CXType_BlockPointer) {
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

    CXType toPointeeType(CXType type, CXCursor c) {
        if (type.kind == CXType_Elaborated) {
            auto declared = clang_getCursorType(c);
            return toPointeeType(declared, clang_getTypeDeclaration(declared));
        }
        return clang_getPointeeType(type);
    }

    CXType toDeepPointeeType(CXType type, CXCursor c) {
        auto pointee = toPointeeType(type, c);
        if (pointee.kind == CXType_BlockPointer || pointee.kind == CXType_Pointer) {
            return toDeepPointeeType(pointee, clang_getTypeDeclaration(pointee));
        }
        return type;
    }

    bool isArrayType(CXTypeKind kind) {
        if (kind == CXType_ConstantArray || kind == CXType_IncompleteArray || kind
                                                                              == CXType_VariableArray ||
            kind == CXType_DependentSizedArray)
            return true;
        return false;
    }

    CXType toDeepPointeeOrArrayType(const CXType &type, const CXCursor &c) {
        const auto pointee = toPointeeType(type, c);
        if (pointee.kind == CXType_BlockPointer || pointee.kind == CXType_Pointer) {
            return toDeepPointeeOrArrayType(pointee, clang_getTypeDeclaration(pointee));
        }
        if (isArrayType(type.kind))
            return toDeepPointeeOrArrayType(clang_getArrayElementType(type),
                                            clang_getTypeDeclaration(clang_getArrayElementType(type)));
        return type;
    }
} // jbindgen
