//
// Created by snownf on 23-11-29.
//

#include <iostream>
#include <format>
#include "MacroNormalGeneratorUtils.h"
#include "Value.h"

namespace jbindgen {
    JTypeWithValue MacroNormalGeneratorUtils::getJTypeWithValue(const std::string &string) {
        JTypeWithValue result("", "");
        if (string.empty())
            return result;
        std::string content = "auto x=" + string + ";";
        std::string path = "./tmp/macroNormalGeneratorUtilsTypeAnalyser.cpp";
        overwriteFile(path, content, true);
        {
            auto index = clang_createIndex(0, 0);
            CXTranslationUnit unit{};
            const char *args[] = {"-I", "/usr/include"};
            auto err = clang_parseTranslationUnit2(
                    index,
                    path.c_str(), args, 2,
                    nullptr, 0,
                    CXTranslationUnit_Incomplete |
                    CXTranslationUnit_IncludeAttributedTypes |
                    CXTranslationUnit_SingleFileParse, &unit);
            if (err != CXError_Success || unit == nullptr) {
                std::cerr << "Unable to parse translation unit (" << err << "). Quitting." << std::endl;
                exit(-1);
            }
            assertAppend(clang_TargetInfo_getPointerWidth(clang_getTranslationUnitTargetInfo(unit)) == 64,"x86 platform is not tested!");
            CXCursor cursor = clang_getTranslationUnitCursor(unit);
            clang_visitChildren(
                    cursor,
                    [](CXCursor c, CXCursor parent, CXClientData ptrs) -> CXChildVisitResult {
                        {
                            auto *pResult = static_cast<JTypeWithValue *>(ptrs);
                            {
                                CXType type = clang_getCursorType(c);
                                auto kind = type.kind;
                                assertAppend(kind == CXType_Auto,"kind should be auto");
                            }
                            {
                                CXCursor initializer = clang_Cursor_getVarDeclInitializer(c);
                                CXType type = clang_getCursorType(initializer);
                                if (type.kind == CXType_Invalid) {
                                    *pResult = JTypeWithValue("", "");
                                    if (DEBUG_LOG)
                                        std::cout << "Invalid" << std::endl;
                                    return CXChildVisit_Break;
                                }
                                CXEvalResult cursorEvaluate = clang_Cursor_Evaluate(initializer);
                                auto jtype = value::jbasic::convert_2_j_type(type);
                                switch (clang_EvalResult_getKind(cursorEvaluate)) {
                                    case CXEval_Int:
                                        switch (jtype) {
                                            case value::jbasic::j_int: {
                                                int value = clang_EvalResult_getAsInt(cursorEvaluate);
                                                if (DEBUG_LOG)
                                                    std::cout << "Int:" << value << std::endl;
                                                *pResult = JTypeWithValue("int", std::to_string(value));
                                                return CXChildVisit_Break;
                                            }
                                            case value::jbasic::j_long: {
                                                long long value = clang_EvalResult_getAsLongLong(cursorEvaluate);
                                                if (DEBUG_LOG)
                                                    std::cout << "Long:" << value << std::endl;
                                                *pResult = JTypeWithValue("long", std::to_string(value) + "L");
                                                return CXChildVisit_Break;
                                            }
                                            case value::jbasic::j_bool: {
                                                int value = clang_EvalResult_getAsInt(cursorEvaluate);
                                                std::string str = value == 0 ? "false" : "true";
                                                if (DEBUG_LOG)
                                                    std::cout << "Bool:" << str << std::endl;
                                                *pResult = JTypeWithValue("boolean", str);
                                                // bool is not tested!
                                                // and not happened.
                                                assertAppend(0,"j bool is not tested!");
                                                return CXChildVisit_Break;
                                            }
                                            case value::jbasic::j_byte: {
                                                int value = clang_EvalResult_getAsInt(cursorEvaluate);
                                                if (DEBUG_LOG)
                                                    std::cout << "Byte:" << value << std::endl;
                                                *pResult = JTypeWithValue("byte", std::to_string(value));
                                                return CXChildVisit_Break;
                                            }
                                            case value::jbasic::j_short: {
                                                int value = clang_EvalResult_getAsInt(cursorEvaluate);
                                                if (DEBUG_LOG)
                                                    std::cout << "Short:" << value << std::endl;
                                                *pResult = JTypeWithValue("short", std::to_string(value));
                                                return CXChildVisit_Break;
                                            }
                                            default:
                                                assertAppend(0,"should not reach here");
                                        }
                                        break;
                                    case CXEval_Float:
                                        switch (jtype) {
                                            case value::jbasic::j_float: {
                                                double value = clang_EvalResult_getAsDouble(cursorEvaluate);
                                                if (DEBUG_LOG)
                                                    std::cout << "Float:" << value << std::endl;
                                                *pResult = JTypeWithValue("float", std::to_string(value) + "f");
                                                return CXChildVisit_Break;
                                            }
                                            case value::jbasic::j_double: {
                                                double value = clang_EvalResult_getAsDouble(cursorEvaluate);
                                                if (DEBUG_LOG)
                                                    std::cout << "Double:" << value << std::endl;
                                                *pResult = JTypeWithValue("double", std::to_string(value));
                                                return CXChildVisit_Break;
                                            }
                                            default:
                                            assertAppend(0,"should not reach here");
                                        }
                                        break;
                                    case CXEval_ObjCStrLiteral:
                                    case CXEval_StrLiteral:
                                    case CXEval_CFStr: {
                                        std::string str = clang_EvalResult_getAsStr(cursorEvaluate);
                                        if (DEBUG_LOG)
                                            std::cout << "String:" << str << std::endl;
                                        *pResult = JTypeWithValue("String", "\"" + str + "\"");
                                        return CXChildVisit_Break;
                                    }
                                    case CXEval_Other: {
                                        *pResult = JTypeWithValue("", "");
                                        if (DEBUG_LOG)
                                            std::cout << "Other" << std::endl;
                                        return CXChildVisit_Break;
                                    }
                                    case CXEval_UnExposed: {
                                        *pResult = JTypeWithValue("", "");
                                        if (DEBUG_LOG)
                                            std::cout << "UnExposed" << std::endl;
                                        return CXChildVisit_Break;
                                    }
                                }
                            }
                        }
                        return CXChildVisit_Continue;
                    },
                    &result);
            clang_disposeTranslationUnit(unit);
            clang_disposeIndex(index);
        }
        return result;
    }


    std::string MacroNormalGeneratorUtils::defaultMakeMacro(const NormalMacroDeclaration &declaration) {
        auto [first, second] = declaration.normalDefines;
        if (DEBUG_LOG)
            std::cout << "MacroNormalGenerator: " << first << " -> " << second << " -> " << std::flush;
        auto [jType, value] = getJTypeWithValue(declaration.normalDefines.second);
        if (jType.empty()) {
            return "";
        }
        std::string result = std::vformat("public static final {} {} = {}",
                                          std::make_format_args(jType, first, value));
        return result;
    }
} // jbindgen