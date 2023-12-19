//
// Created by snownf on 23-11-9.
//

#ifndef JBINDGEN_STRUCTGENERATOR_H
#define JBINDGEN_STRUCTGENERATOR_H

#include <utility>

#include "../analyser/StructDeclaration.h"
#include "GenUtils.h"


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
        const std::string baseSharedPackageName;
        const std::string valuePackageName;
        const std::string functionPackageName;
        const std::string sharedNativesPackageName;
        const std::string enumFullyQualifiedName;

        std::string makeGetterSetter(const std::string &className);

        std::string makeToString(const std::string &className);

    public:

        StructGenerator(StructDeclaration declaration, std::string structsDir, std::string packageName,
                        FN_structMemberName memberRename,
                        FN_decodeGetter decodeGetter, FN_decodeSetter decodeSetter, const Analyser &analyser,
                        std::string baseSharedPackageName, std::string valuePackageName,
                        std::string functionPackageName, std::string sharedNativesPackageName,
                        std::string enumFullyQualifiedName);

        void build(const std::string &string);

        void build();
    };
} // jbindgen

#endif //JBINDGEN_STRUCTGENERATOR_H
