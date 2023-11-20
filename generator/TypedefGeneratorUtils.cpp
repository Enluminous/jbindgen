//
// Created by snownf on 23-11-19.
//

#include "TypedefGeneratorUtils.h"

std::tuple<std::string, std::string>
jbindgen::TypedefGeneratorUtils::defaultNameFunction(const jbindgen::NormalTypedefDeclaration *declaration,
                                                     void *pUserdata) {
    std::tuple<std::string, std::string> a;
    std::get<0>(a) = declaration->mappedStr;
    std::get<1>(a) = declaration->oriStr;
    return a;
}