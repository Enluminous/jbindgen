//
// Created by nettal on 23-11-8.
//

#include "FunctionTypedefDeclaration.h"

#include <utility>

namespace jbindgen {
    FunctionTypedefDeclaration::FunctionTypedefDeclaration(std::string functionName, std::string canonicalName,
                                                           Typed ret) : functionName(std::move(functionName)),
                                                                        canonicalName(std::move(canonicalName)),
                                                                        ret(std::move(ret)) {

    }

    std::ostream &operator<<(std::ostream &stream, const FunctionTypedefDeclaration &function) {
        stream << "#### TypedefFunction " << std::endl;
        stream << "  " << function.ret << " " << function.functionName << " ";
        for (const auto &item: function.paras) {
            stream << item << " ";
        }
        stream << std::endl;
        return stream;
    }
} // jbindgen