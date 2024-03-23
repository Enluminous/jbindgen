//
// Created by snownf on 23-11-15.
//

#include <iostream>
#include "FunctionGeneratorUtils.h"
#include "StructGeneratorUtils.h"
#include "Value.h"

namespace jbindgen::functiongenerator {
    //  j_type , fd , need allocator
    std::tuple<std::string, std::string, bool> processDirectCallType(const VarDeclare &varDeclare) {
        auto copyMethod = value::method::typeCopy(varDeclare.type);
        switch (copyMethod) {
            case value::method::copy_by_primitive_j_int_call:
            case value::method::copy_by_primitive_j_long_call:
#if NATIVE_UNSUPPORTED
            case value::method::copy_by_primitive_j_bool_call:
            case value::method::copy_by_primitive_j_char_call:
#endif
            case value::method::copy_by_primitive_j_float_call:
            case value::method::copy_by_primitive_j_double_call:
            case value::method::copy_by_primitive_j_short_call:
            case value::method::copy_by_primitive_j_byte_call: {
                auto ffm = value::method::copy_method_2_native_type(copyMethod);
                assertAppend(ffm.type != value::jbasic::type_other, "varDeclare: " + varDeclare.name);
                return {ffm.primitive(), ffm.value_layout(), false};
            }
            case value::method::copy_by_value_j_int_call:
            case value::method::copy_by_value_j_long_call:
            case value::method::copy_by_value_j_float_call:
#if NATIVE_UNSUPPORTED
            case value::method::copy_by_value_j_char_call:
            case value::method::copy_by_value_j_bool_call:
#endif
            case value::method::copy_by_value_j_double_call:
            case value::method::copy_by_value_j_short_call:
            case value::method::copy_by_value_j_byte_call: {
                auto value = value::method::copy_method_2_value_type(copyMethod);
                assertAppend(value.type != value::jbasic::type_other, "varDeclare: " + varDeclare.name);
                return {value.primitive(), value.value_layout(), false};
            }
            case value::method::copy_by_value_memory_segment_call: {
                auto value = value::jext::VPointer;
                return {value.primitive(), value.value_layout(), false};
            }
            case value::method::copy_by_function_ptr_call:
            case value::method::copy_by_array_call: {
                auto len = getArrayLength(value::method::typeCopyWithResultType(varDeclare.type).type);
                if (len == -1) {
                    return {value::jext::Pointer.primitive(), value::jext::Pointer.value_layout(), false};
                }
                return {value::jext::Pointer.primitive(), generateFakeValueLayout(varDeclare.byteSize), true};
            }
            case value::method::copy_by_ext_int128_call:
            case value::method::copy_by_ext_long_double_call:
            case value::method::copy_by_ptr_dest_copy_call:
                return {value::jext::Pointer.primitive(), generateFakeValueLayout(varDeclare.byteSize), true};
            case value::method::copy_by_ptr_copy_call:
                return {value::jext::Pointer.primitive(), value::jext::Pointer.value_layout(), false};
            case value::method::copy_error:
            case value::method::copy_void:
            case value::method::copy_target_void:
            case value::method::copy_internal_function_proto: assertAppend(0, "varDeclare: " + varDeclare.name);
        }
        assertAppend(0, "should not reach here");
    }

    std::string makeName(const VarDeclare &varDeclare, int i) {
        const auto &name = varDeclare.name;
        if (name == NO_NAME) {
            return makeUnnamedParaNamed(i);
        }
        if (std::any_of(JAVA_KEY_WORDS.begin(), JAVA_KEY_WORDS.end(), [name](auto e) { return e == name; })) {
            return name + "$";
        }
        if (name == "allocator")
            return name + "$";
        return name;
    }

    //  j parameters , fds , invokes
    std::tuple<std::vector<std::string>, std::vector<std::string>, std::vector<std::string>>
    makeParameter(const jbindgen::FunctionSymbolDeclaration &declare) {
        std::vector<std::string> jParameters;
        std::vector<std::string> fds;
        std::vector<std::string> invokes;
        int i = 0;
        for (const auto &item: declare.paras) {
            //j_type fd
            auto pre = processDirectCallType(item);
            std::string paraName = makeName(item, i);
            jParameters.emplace_back(get<0>(pre) + " " + paraName);
            fds.emplace_back(get<1>(pre));
            invokes.emplace_back(paraName);
            i++;
        }
        return {jParameters, fds, invokes};
    }

    std::vector<FunctionWrapperInfo>
    makeWrappers(const FunctionSymbolDeclaration &declaration, const Analyser &analyser);

    FunctionInfo
    defaultMakeFunctionInfo(const jbindgen::FunctionSymbolDeclaration *declaration, const Analyser &analyser) {
        FunctionInfo info;
        info.functionName = declaration->getName();
        //parameter
        auto parameters = makeParameter(*declaration);
        info.jParameters = get<0>(parameters);
        info.parameterDescriptors = get<1>(parameters);
        info.invokeParameters = get<2>(parameters);
        //result
        info.hasResult = declaration->ret.type.kind != CXType_Void;
        info.needAllocator = false;
        if (info.hasResult) {
            auto ret = processDirectCallType(declaration->ret);
            info.jResult = get<0>(ret);
            info.resultDescriptor = get<1>(ret);
            info.needAllocator = get<2>(ret);
        }
        info.wrappers = makeWrappers(*declaration, analyser);
        return info;
    }

    std::function<std::string(std::string constructorStr)>
    callNewByPointer(const std::string &clazz) {
        return [clazz](auto str) { return "new " + clazz + "(FunctionUtils.makePointer(" + str + "))"; };
    }

    std::function<std::string(std::string constructorStr)>
    callList(const std::string &clazz) {
        return [clazz](auto str) { return clazz + ".list(FunctionUtils.makePointer(" + str + "))"; };
    }

    std::function<std::string(std::string constructorStr)> callMakePointer() {
        return [](auto str) { return "FunctionUtils.makePointer(" + str + ")"; };
    }

    wrapper callPointerLambda(const std::string &name) {
        return {value::makePointer(name), ".pointer()", callMakePointer(), false};
    }

    wrapper callPointerLambda(const value::jbasic::ValueType &name) {
        return {value::makePointer(name), ".pointer()", callMakePointer(), false};
    }

    wrapper callPointerFunctionLambda(const std::string &name) {
        return {value::makePointer(name), ".pointer()", callMakePointer(), false};
    }

    std::pair<std::string, int> depthName(CXType type, const Analyser &analyser) {
        auto result = value::method::typeCopyWithResultType(type);
        int depth = 0;
        std::string name;
        while (1) {
            if (depth > 102400) assertAppend(0,
                                             "loop over 102400 at std::pair<std::string, int> depthName(CXType type, const Analyser &analyser)");
            result = value::method::typeCopyWithResultType(result.type);
            switch (result.copy) {
                case value::method::copy_by_primitive_j_byte_call:
                case value::method::copy_by_primitive_j_int_call:
#if NATIVE_UNSUPPORTED
                case value::method::copy_by_primitive_j_bool_call:
                case value::method::copy_by_primitive_j_char_call:
#endif
                case value::method::copy_by_primitive_j_long_call:
                case value::method::copy_by_primitive_j_float_call:
                case value::method::copy_by_primitive_j_double_call:
                case value::method::copy_by_primitive_j_short_call: {
                    //primitive type
                    const value::jbasic::NativeType &pointeeType = value::method::copy_method_2_native_type(
                            result.copy);
                    assertAppend(pointeeType.type != value::jbasic::type_other,
                                 "type: " + toStringWithoutConst(result.type));
                    auto value = value::method::native_type_2_value_type(pointeeType);
                    name = value.wrapper() + "<" + value.objectPrimitiveName() + ">";
                    break;
                }
                case value::method::copy_by_value_j_int_call:
#if NATIVE_UNSUPPORTED
                case value::method::copy_by_value_j_char_call:
                case value::method::copy_by_value_j_bool_call:
#endif
                case value::method::copy_by_value_j_long_call:
                case value::method::copy_by_value_j_float_call:
                case value::method::copy_by_value_j_double_call:
                case value::method::copy_by_value_j_short_call:
                case value::method::copy_by_value_j_byte_call:
                case value::method::copy_by_ptr_dest_copy_call: {
                    name = toCXTypeDeclName(analyser, result.type);
                    break;
                }
                case value::method::copy_by_value_memory_segment_call: {
                    name = toCXTypeDeclName(analyser, result.type);
                    break;
                }
                case value::method::copy_by_ext_int128_call:
                case value::method::copy_by_ext_long_double_call: {
                    //ext type
                    auto ext = value::method::copy_method_2_ext_type(result.copy);
                    assertAppend(ext.type != value::jext::EXT_OTHER.type, "");
                    name = ext.native_wrapper;
                    break;
                }
                case value::method::copy_by_function_ptr_call: {
                    name = toCXTypeFunctionPtrName(result.type, analyser);
                    break;
                }
                case value::method::copy_by_array_call: {
                    result.type = clang_getArrayElementType(result.type);
                    depth++;
                    continue;
                }
                case value::method::copy_by_ptr_copy_call: {
                    result.type = toPointeeType(result.type);
                    depth++;
                    continue;
                }
                case value::method::copy_target_void: {
                    name = toCXTypeName(result.type, analyser);
                    break;
                }
                case value::method::copy_void: {
                    name = "?";
                    break;
                }
                case value::method::copy_internal_function_proto:
                case value::method::copy_error: {
                    assertAppend(0, "error met: copy_error || copy_internal_function_proto,type: " +
                                    toStringWithoutConst(type));
                }
            }
            break;
        }
        return {name, depth};
    }

    static std::vector<wrapper> visitDeepType(const CXType &declare, const Analyser &analyser) {
        auto [name, depth] = depthName(declare, analyser);
        assertAppend(!name.empty(), "");
        depth++;
        std::string jType;
        std::string end;
        for (int i = 0; i < depth; ++i) {
            jType += "Pointer<? extends ";
            end += ">";
        }
        return {(wrapper) {jType + name + end, ".pointer()", callMakePointer(), false}};
    }

    std::vector<wrapper> processWrapperCallType(const VarDeclare &declare, const Analyser &analyser) {
        std::vector<wrapper> optional;
        auto copy = value::method::typeCopyWithResultType(declare.type);
        switch (copy.copy) {
            case value::method::copy_by_function_ptr_call: {
                auto functionName = toCXTypeFunctionPtrName(copy.type, analyser);
                optional.emplace_back(callPointerFunctionLambda(functionName));
                break;
            }
            case value::method::copy_by_ptr_dest_copy_call: {
                auto typeName = toCXTypeDeclName(analyser, copy.type);
                auto result = std::any_of(value::JAVA_UNSUPPORTED.begin(), value::JAVA_UNSUPPORTED.end(),
                                          [&](const auto &item) {
                                              return item == typeName;
                                          });
                if (result)
                    optional.emplace_back((wrapper) {callPointerLambda(typeName)});
                else
                    optional.emplace_back((wrapper) {typeName, ".pointer()", callNewByPointer(typeName), false});
                break;
            }
            case value::method::copy_by_ptr_copy_call: {
                auto pointeeCopy = value::method::typeCopyWithResultType(toPointeeType(copy.type));
                switch (pointeeCopy.copy) {
#if NATIVE_UNSUPPORTED
                    case value::method::copy_by_primitive_j_bool_call:
                    case value::method::copy_by_primitive_j_char_call:
#endif
                    case value::method::copy_by_primitive_j_int_call:
                    case value::method::copy_by_primitive_j_long_call:
                    case value::method::copy_by_primitive_j_float_call:
                    case value::method::copy_by_primitive_j_double_call:
                    case value::method::copy_by_primitive_j_byte_call:
                    case value::method::copy_by_primitive_j_short_call: {
                        //primitive type
                        const value::jbasic::NativeType &pointeeType = copy_method_2_native_type(pointeeCopy.copy);
                        assertAppend(pointeeType.type != value::jbasic::type_other,
                                     "type: " + toStringWithoutConst(pointeeCopy.type));
                        auto value = value::method::native_type_2_value_type(pointeeType);
                        optional.emplace_back(callPointerLambda(value));
                        break;
                    }
                    case value::method::copy_by_value_j_int_call:
#if NATIVE_UNSUPPORTED
                    case value::method::copy_by_value_j_char_call:
                    case value::method::copy_by_value_j_bool_call:
#endif
                    case value::method::copy_by_value_j_long_call:
                    case value::method::copy_by_value_j_float_call:
                    case value::method::copy_by_value_j_double_call:
                    case value::method::copy_by_value_j_short_call:
                    case value::method::copy_by_value_j_byte_call: {
                        //value based
                        const std::string &pointeeName = toCXTypeDeclName(analyser, pointeeCopy.type);
                        optional.emplace_back(callPointerLambda(pointeeName));
                        break;
                    }
                    case value::method::copy_by_value_memory_segment_call: {
                        //value based
                        const std::string &pointeeName = toCXTypeDeclName(analyser, pointeeCopy.type);
                        optional.emplace_back(callPointerLambda(pointeeName));
                        break;
                    }
                    case value::method::copy_by_function_ptr_call: {
                        //FunctionProto**
                        auto functionName = toCXTypeFunctionPtrName(pointeeCopy.type, analyser);
                        optional.emplace_back(callPointerFunctionLambda(value::makePointer(functionName)));
                        break;
                    }
                        //struct ptr liked
                    case value::method::copy_by_ptr_dest_copy_call: {
                        const std::string &pointeeName = toCXTypeDeclName(analyser, pointeeCopy.type);
                        optional.emplace_back(callPointerLambda(pointeeName));
                        break;
                    }
                    case value::method::copy_by_ext_int128_call:
                    case value::method::copy_by_ext_long_double_call: {
                        //ext type
                        auto ext = copy_method_2_ext_type(pointeeCopy.copy);
                        assertAppend(ext.type != value::jext::EXT_OTHER.type,
                                     "type: " + toStringWithoutConst(pointeeCopy.type));
                        optional.emplace_back(callPointerLambda(ext.native_wrapper));
                        break;
                    }
                    case value::method::copy_by_array_call:
                    case value::method::copy_by_ptr_copy_call: {
                        std::vector<wrapper> deep = visitDeepType(pointeeCopy.type, analyser);
                        for (const auto &item: deep) {
                            optional.emplace_back(item);
                        }
                        break;
                    }
                    case value::method::copy_target_void: {
                        optional.emplace_back(callPointerLambda(toCXTypeName(pointeeCopy.type, analyser)));
                        break;
                    }
                    case value::method::copy_internal_function_proto:
                    case value::method::copy_error: {
                        assertAppend(0,
                                     "error met: copy_error || copy_internal_function_proto,type: " +
                                     toStringWithoutConst(pointeeCopy.type));
                    }
                    case value::method::copy_void: {
                        optional.emplace_back(callPointerLambda("?"));
                        break;
                    }
                }
                break;
            }
                assertAppend(0, "should not reach here");
            case value::method::copy_by_array_call: {
                auto elementCopy = value::method::typeCopyWithResultType(clang_getArrayElementType(copy.type));
                switch (elementCopy.copy) {
                    case value::method::copy_by_primitive_j_int_call:
#if NATIVE_UNSUPPORTED
                    case value::method::copy_by_primitive_j_bool_call:
                    case value::method::copy_by_primitive_j_char_call:
#endif
                    case value::method::copy_by_primitive_j_long_call:
                    case value::method::copy_by_primitive_j_float_call:
                    case value::method::copy_by_primitive_j_double_call:
                    case value::method::copy_by_primitive_j_byte_call:
                    case value::method::copy_by_primitive_j_short_call: {
                        const value::jbasic::NativeType &nativeType = copy_method_2_native_type(elementCopy.copy);
                        auto value = value::method::native_type_2_value_type(nativeType);
                        optional.emplace_back((wrapper) {
                                value::makeVList(value),
                                ".pointer()", callList(value.wrapper()), false});
                        break;
                    }
                    case value::method::copy_by_value_j_int_call:
#if NATIVE_UNSUPPORTED
                    case value::method::copy_by_value_j_char_call:
                    case value::method::copy_by_value_j_bool_call:
#endif
                    case value::method::copy_by_value_j_long_call:
                    case value::method::copy_by_value_j_float_call:
                    case value::method::copy_by_value_j_double_call:
                    case value::method::copy_by_value_j_short_call:
                    case value::method::copy_by_value_j_byte_call: {
                        auto value = copy_method_2_value_type(elementCopy.copy);
                        const std::string &valueName = toCXTypeDeclName(analyser, elementCopy.type);
                        optional.emplace_back((wrapper) {
                                value::makeVList(valueName, value), ".pointer()",
                                callList(valueName), false});
                        break;
                    }
                    case value::method::copy_by_value_memory_segment_call: {
                        const std::string &valueName = toCXTypeDeclName(analyser, elementCopy.type);
                        if (isTypedefFunction(elementCopy.type)) {
                            optional.emplace_back((wrapper) {callPointerLambda(valueName)});
                            break;
                        }
                        optional.emplace_back((wrapper) {
                                value::makeVList(valueName, value::jext::VPointer), ".pointer()",
                                callList(valueName), false});
                        break;
                    }
                    case value::method::copy_by_function_ptr_call: {
                        //array have function ptr
                        const std::string &funcName = toCXTypeFunctionPtrName(elementCopy.type, analyser);
                        optional.emplace_back(callPointerFunctionLambda(funcName));
                        break;
                    }
                    case value::method::copy_by_ptr_dest_copy_call: {
                        //other type
                        optional.emplace_back((wrapper) {
                                NList + "<" + toCXTypeDeclName(analyser, elementCopy.type) + ">",
                                ".pointer()", callList(toCXTypeDeclName(analyser, elementCopy.type)), false});
                        break;
                    }
                    case value::method::copy_by_array_call:
                    case value::method::copy_by_ptr_copy_call: {
                        std::vector<wrapper> deep = visitDeepType(elementCopy.type, analyser);
                        for (const auto &item: deep) {
                            optional.emplace_back(item);
                        }
                        break;
                    }
                    case value::method::copy_by_ext_int128_call:
                    case value::method::copy_by_ext_long_double_call: {
                        //ext type
                        auto ext = copy_method_2_ext_type(elementCopy.copy);
                        assertAppend(ext.type != value::jext::EXT_OTHER.type, "");
                        optional.emplace_back((wrapper) {
                                NList + "<" + ext.native_wrapper + ">",
                                ".pointer()", callList(ext.native_wrapper), false});
                        break;
                    }
                    case value::method::copy_error:
                    case value::method::copy_void:
                    case value::method::copy_target_void:
                    case value::method::copy_internal_function_proto: {
                        assertAppend(0,
                                     "error met: copy_error || copy_internal_function_proto || copy_target_void || copy_void,type: " +
                                     toStringWithoutConst(elementCopy.type));
                    }
                }
                break;
            }
            case value::method::copy_by_primitive_j_int_call:
            case value::method::copy_by_primitive_j_long_call:
            case value::method::copy_by_primitive_j_float_call:
            case value::method::copy_by_primitive_j_double_call:
            case value::method::copy_by_primitive_j_short_call:
            case value::method::copy_by_primitive_j_byte_call:
#if NATIVE_UNSUPPORTED
            case value::method::copy_by_primitive_j_char_call:
            case value::method::copy_by_primitive_j_bool_call:
#endif
                optional.emplace_back((wrapper) {copy_method_2_native_type(copy.copy).primitive(), "",
                                                 [](auto s) { return s; }, true});
                break;
            case value::method::copy_by_value_j_int_call:
            case value::method::copy_by_value_j_long_call:
            case value::method::copy_by_value_j_float_call:
            case value::method::copy_by_value_j_double_call:
            case value::method::copy_by_value_j_short_call:
            case value::method::copy_by_value_j_byte_call:
#if NATIVE_UNSUPPORTED
            case value::method::copy_by_value_j_char_call:
            case value::method::copy_by_value_j_bool_call:
#endif
            {
                auto typeName = toCXTypeName(copy.type, analyser);
                optional.emplace_back((wrapper) {typeName, ".value()",
                                                 [typeName](auto s) { return "new " + typeName + "(" + s + ")"; },
                                                 false});
            }
                break;
            case value::method::copy_by_value_memory_segment_call: {
                auto typeName = toCXTypeName(copy.type, analyser);
                if (isTypedefFunction(copy.type)) {
                    optional.emplace_back((wrapper) {value::makeValue(typeName, value::jext::VPointer), ".value()",
                                                     [](auto s) {
                                                         return "new " + value::jext::VPointer.wrapper() + "<>(" + s +
                                                                ")";
                                                     }, false});
                    break;
                }
                optional.emplace_back((wrapper) {typeName, ".value()",
                                                 [typeName](auto s) { return "new " + typeName + "(" + s + ")"; },
                                                 false});
                break;
            }
            case value::method::copy_by_ext_int128_call:
            case value::method::copy_by_ext_long_double_call:
                optional.emplace_back((wrapper) {
                        value::method::copy_method_2_ext_type(copy.copy).native_wrapper, ".pointer()",
                        callNewByPointer(value::method::copy_method_2_ext_type(copy.copy).native_wrapper), false});
                break;
            case value::method::copy_error:
            case value::method::copy_void:
            case value::method::copy_internal_function_proto:
            case value::method::copy_target_void: {
                assertAppend(0,
                             "error met: copy_error || copy_internal_function_proto || copy_target_void || copy_void,type: " +
                             toStringWithoutConst(copy.type));
            }
                break;
        }
        assertAppend(!optional.empty(), "type: " + toStringWithoutConst(copy.type));
        return optional;
    }

    template<typename T>
    static void
    generateWrap(const std::vector<std::vector<T>> &elem, std::vector<std::vector<T> *> &paras) {
        if (elem.empty()) {
            return;
        }
        auto iter = elem[0].begin();
        auto s = *iter;
        paras[0]->emplace_back(s);
        auto batch = paras.size() / elem[0].size();
        decltype(batch) lastBatch = 0;
        for (int i = 1; i <= paras.size(); i++) {
            if (i % batch == 0) {
                auto subElem = std::vector(elem.begin() + 1, elem.end());
                auto subParas = std::vector(paras.begin() + lastBatch, paras.begin() + i);
                generateWrap(subElem, subParas);
                iter++;
                if (iter == elem[0].end())
                    break;
                s = *iter;
                lastBatch = i;
            }
            paras[i]->emplace_back(s);
        }
    }

    std::vector<FunctionWrapperInfo>
    makeWrappers(const FunctionSymbolDeclaration &declaration, const Analyser &analyser) {
        //{optional_parameters{parameter_a_1, parameter_a_2}, {p_b_1. p_b_2}}
        std::vector<std::vector<std::string>> jOptions;
        std::vector<std::vector<std::string>> decodeOptions;
        std::vector<std::vector<std::string>> encodeOptions;
        std::vector<std::vector<bool>> primitives;

        //parameter list:{{parameter_a_1, parameter_b_1}, {p_a_1. p_b_2},{p_a_2,p_b_1},{p_a_2,p_b_2}}
        std::vector<std::vector<std::string> *> jParameters;
        std::vector<std::vector<std::string> *> decodeParameters;
        std::vector<std::vector<std::string> *> encodeParameters;
        std::vector<std::vector<bool> *> targetPrimitives;
        size_t parameterCount = 1;
        for (int ij = 0; ij < declaration.paras.size(); ++ij) {
            auto varName = makeName(declaration.paras[ij], ij);
            auto optionalParameters = processWrapperCallType(declaration.paras[ij], analyser);
            assertAppend(!optionalParameters.empty(), "type: " + toStringWithoutConst(declaration.paras[ij].type));
            parameterCount *= optionalParameters.size();
            std::vector<std::string> jOption;
            std::vector<std::string> decodeOption;
            std::vector<std::string> encodeOption;
            std::vector<bool> primitive;
            for (auto &p: optionalParameters) {
                jOption.emplace_back(p.type + " " + varName);
                decodeOption.emplace_back(varName + p.decode);
                encodeOption.emplace_back(p.getEncode(varName));
                primitive.emplace_back(p.primitive);
            }
            jOptions.emplace_back(jOption);
            decodeOptions.emplace_back(decodeOption);
            encodeOptions.emplace_back(encodeOption);
            primitives.emplace_back(primitive);
        }
        for (int j = 0; j < parameterCount; ++j) {
            jParameters.emplace_back(new std::vector<std::string>());
            decodeParameters.emplace_back(new std::vector<std::string>());
            encodeParameters.emplace_back(new std::vector<std::string>());
            targetPrimitives.emplace_back(new std::vector<bool>());
        }
        generateWrap(jOptions, jParameters);
        generateWrap(decodeOptions, decodeParameters);
        generateWrap(encodeOptions, encodeParameters);
        generateWrap(primitives, targetPrimitives);

        std::vector<FunctionWrapperInfo> wrappers;
        if (declaration.ret.type.kind == CXType_Void) {
            FunctionWrapperInfo info;
            info.wrapperName = declaration.getName();
            //if jParameters.empty(), no wrapper we will have
            for (auto j = 0; j < jParameters.size(); ++j) {
                info.jParameters = *jParameters[j];
                info.decodeParameters = *decodeParameters[j];
                info.encodeParameters = *encodeParameters[j];
                //check whether all primitive type, and then, skip it
                auto primitive = *targetPrimitives[j];
                auto allPrimitive = !std::any_of(primitive.begin(), primitive.end(), [](auto e) { return !e; });
                if (allPrimitive)
                    continue;
                //result is void
                wrappers.emplace_back(info);
            }
        } else {//has result
            auto ret = processWrapperCallType(declaration.ret, analyser);
            for (auto &item: ret) {
                FunctionWrapperInfo info;
                info.wrapperName = declaration.getName() + "$" + item.type;
                if (item.type.contains("<")) {//remove <> for wrapper name
                    info.wrapperName = declaration.getName() + "$"
                                       + item.type.substr(0, item.type.find_first_of('<'));
                }
                for (auto j = 0; j < jParameters.size(); ++j) {
                    info.jParameters = *jParameters[j];
                    info.decodeParameters = *decodeParameters[j];
                    info.encodeParameters = *encodeParameters[j];
                    info.wrappedResult = item.type;
                    info.makeResult = item.getEncode;
                    info.wrappedResultCall = item.decode;
                    wrappers.emplace_back(info);
                }
            }
        }
        for (const auto &item: jParameters) {
            delete item;
        }
        for (const auto &item: decodeParameters) {
            delete item;
        }
        for (const auto &item: encodeParameters) {
            delete item;
        }
        for (const auto &item: targetPrimitives) {
            delete item;
        }
        return wrappers;
    }

} // jbindgen