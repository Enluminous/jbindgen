//
// Created by snownf on 23-11-9.
//

#ifndef JBINDGEN_STRUCTGENERATORUTILS_H
#define JBINDGEN_STRUCTGENERATORUTILS_H

#include <string>
#include <sstream>
#include <vector>
#include <iostream>
#include <cassert>
#include "Value.h"
#include "../analyser/Analyser.h"
#include "GenUtils.h"
#include "StructGenerator.h"

#define NEXT_LINE  << std::endl
#define END_LINE std::endl

namespace jbindgen {
    class StructGeneratorUtils {
    public:
        static std::tuple<std::vector<Getter>, std::vector<Setter>>
        defaultStructDecodeShared(const StructMember &structMember, const CXCursorMap &cxCursorMap,
                                  const std::string &ptrName);
        static std::vector<Setter>
        defaultStructDecodeSetter(const StructMember &structMember, const CXCursorMap &cxCursorMap,
                                  const std::string &ptrName, void *pUserdata);

        static std::vector<Getter>
        defaultStructDecodeGetter(const jbindgen::StructMember &structMember,
                                  const CXCursorMap &cxCursorMap,
                                  const std::string &ptrName, void *pUserdata);

        static std::string
        defaultStructMemberName(const StructDeclaration &declaration, const CXCursorMap &cxCursorMap,const StructMember &member, void *pUserdata) {
            return member.var.name;
        };

        static std::string
        makeCore(const std::string &imported, const std::string &packageName, const std::string &structName,
                 long byteSize,
                 const std::string &toString,
                 const std::string &getter_setter);
    };
}


#endif //JBINDGEN_STRUCTGENERATORUTILS_H
