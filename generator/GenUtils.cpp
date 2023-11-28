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
        assert(byteSize > 0);//currently is signed
        std::string layout;
        return "MemoryLayout.sequenceLayout(" + std::to_string(byteSize / 4) + "," +
               value::jbasic::Integer.value_layout() + ")";
    }

    std::string toStringWithCXCursorMap(CXCursor &cxCursor, const CXCursorMap &map) {
        return map.at(cxCursor)->getName();
    }


    int64_t getArrayLength(CXType type) {
        if (type.kind == CXType_Elaborated) {
            auto declared = clang_getCursorType(clang_getTypeDeclaration(type));
            return getArrayLength(declared);
        }
        return clang_getNumElements(type);
    }

    std::string toPointerName(const VarDeclare &declare) {
        const auto &pointee_type = toPointeeType(declare.type, declare.cursor);
        const auto &extra = declare.extra;
        std::string typeName = toStringWithoutConst(pointee_type);
        if (extra.has_value()) {
            typeName = std::any_cast<std::string>(extra);
        }
        return typeName;
    }

    std::string toArrayName(const VarDeclare &declare) {
        auto name = toStringWithoutConst(clang_getArrayElementType(declare.type));
        if (clang_Cursor_isAnonymous(
                clang_getTypeDeclaration(clang_getArrayElementType(declare.type)))) {
            assert(declare.extra.has_value());
            name = std::any_cast<std::string>(declare.extra);
        }
        return name;
    }

    std::string toVarDeclareString(const VarDeclare &varDeclare) {
        auto name = toStringWithoutConst(varDeclare.type);
        if (clang_Cursor_isAnonymous(clang_getTypeDeclaration(varDeclare.type))) {
            assert(varDeclare.extra.has_value());
            name = any_cast<std::string>(varDeclare.extra);
        }
        return name;
    }

    int32_t getPointeeOrArrayDepth(CXType type) {
        if (type.kind == CXType_Pointer || type.kind == CXType_BlockPointer) {
            return getPointeeOrArrayDepth(clang_getPointeeType(type)) + 1;
        }
        if (type.kind == CXType_Elaborated) {
            auto declared = clang_getCursorType(clang_getTypeDeclaration(type));
            return getPointeeOrArrayDepth(declared);
        }
        if (type.kind == CXType_VariableArray || type.kind == CXType_IncompleteArray ||
            type.kind == CXType_ConstantArray || type.kind == CXType_DependentSizedArray) {
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

    std::string toDeepPointerName(const VarDeclare &declare) {
        auto deepType = toDeepPointeeType(declare.type, declare.cursor);
        return toStringWithoutConst(deepType);
    }

    CXType toDeepPointeeType(CXType type, CXCursor c) {
        auto pointee = toPointeeType(type, c);
        if (pointee.kind == CXType_BlockPointer || pointee.kind == CXType_Pointer) {
            return toPointeeType(type, clang_getTypeDeclaration(type));
        }
        return type;
    }
} // jbindgen