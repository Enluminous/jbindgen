//
// Created by snownf on 23-11-9.
//

#ifndef JBINDGEN_STRUCTGENERATORUTILS_H
#define JBINDGEN_STRUCTGENERATORUTILS_H

#include <string>
#include <sstream>
#include <vector>
#include <iostream>
#include "Value.h"
#include "../analyser/Analyser.h"
#include "GenUtils.h"
#include "StructGenerator.h"

namespace jbindgen {

    class StructGeneratorUtils {
    public:
        static std::tuple<std::vector<Getter>, std::vector<Setter>>
        defaultStructDecodeShared(const StructMember &structMember, const Analyser &analyser,
                                  const std::string &ptrName);

        static std::vector<Setter>
        defaultStructDecodeSetter(const StructMember &structMember, const Analyser &analyser,
                                  const std::string &ptrName);

        static std::vector<Getter>
        defaultStructDecodeGetter(const jbindgen::StructMember &structMember, const Analyser &analyser,
                                  const std::string &ptrName);

        static std::string
        defaultStructMemberName(const StructDeclaration &declaration, const Analyser &analyser,
                                const StructMember &member);;

        static std::string
        makeCore(const std::string &extraImported, const std::string &packageName, const std::string &structName,
                 long byteSize,
                 const std::string &toString,
                 const std::string &getter_setter);
    };
}


#endif //JBINDGEN_STRUCTGENERATORUTILS_H
