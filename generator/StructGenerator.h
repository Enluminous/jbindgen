//
// Created by snownf on 23-11-9.
//

#ifndef JBINDGEN_STRUCTGENERATOR_H
#define JBINDGEN_STRUCTGENERATOR_H

#include <utility>

#include "../analyser/StructDeclaration.h"
#include "GenUtils.h"
#include "TypeManager.h"


namespace jbindgen {
    struct Getter {
        std::string returnTypeName;
        std::string parameterString;
        std::string creator;
    };
    struct Setter {
        std::string parameterString;
        std::string creator;
    };

    typedef std::function<std::string(const StructDeclaration &declaration, const Analyser &analyser,
                                      const StructMember &member)> FN_structMemberName;

    typedef std::function<std::vector<Getter>(const jbindgen::StructMember &structMember,
                                              const Analyser &analyser, const std::string &ptrName)> FN_decodeGetter;

    typedef std::function<std::vector<Setter>(const jbindgen::StructMember &structMember,
                                              const Analyser &analyser,
                                              const std::string &ptrName)> FN_decodeSetter;

    class StructGenerator {
        const StructDeclaration declaration;
        const std::string structsDir;
        const std::string packageName;

        const FN_structMemberName structMemberName;
        const FN_decodeGetter decodeGetter;
        const FN_decodeSetter decodeSetter;
        const Analyser &analyser;

        const GeneratorConfig &config;
        std::shared_ptr<TypeManager> typeManager;

        std::string makeGetterSetter(const std::string &className);

        std::string makeToString(const std::string &className);

    public:

        StructGenerator(StructDeclaration declaration, std::shared_ptr<TypeManager> typeManager,
                        const GeneratorConfig &config);

        void build(const std::string &string);

        void build();
    };
} // jbindgen

#endif //JBINDGEN_STRUCTGENERATOR_H
