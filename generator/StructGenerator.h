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

    typedef std::string(*PFN_structName)(const StructDeclaration &declaration, const Analyser & analyser,
                                         void *pUserdata);

    typedef std::string(*PFN_structMemberName)(const StructDeclaration &declaration, const Analyser & analyser,
                                               const StructMember &member, void *pUserdata);

    typedef std::vector<Getter>(*PFN_decodeGetter)(const jbindgen::StructMember &structMember,
                                                   const Analyser & analyser, const std::string &ptrName,
                                                   void *pUserdata);

    typedef std::vector<Setter>(*PFN_decodeSetter)(const jbindgen::StructMember &structMember,
                                                   const Analyser & analyser,
                                                   const std::string &ptrName, void *pUserdata);

    typedef bool (*PFN_StructGenerationFilter)(const StructDeclaration *structDeclaration,
                                               const Analyser & analyser, void *userData);

    class StructGenerator {
        const StructDeclaration declaration;
        const std::string structsDir;
        const std::string packageName;

        const PFN_structName pfnStructName;
        const PFN_structMemberName pfnStructMemberName;
        const PFN_decodeGetter decodeGetter;
        const PFN_decodeSetter decodeSetter;
        const Analyser &analyser;

        std::string makeGetterSetter(const std::string &structName, void *memberRenameUserData,
                                     void *decodeGetterUserData, void *decodeSetterUserData);

    public:
        StructGenerator(StructDeclaration declaration, std::string structsDir, std::string packageName,
                        PFN_structName structRename, PFN_structMemberName memberRename,
                        PFN_decodeGetter decodeGetter, PFN_decodeSetter decodeSetter, const Analyser &analyser);

        void build(void *structNameUserData, void *memberNameUserData, void *decodeGetterUserData,
                   void *decodeSetterUserData,
                   void *pVoid);
    };
} // jbindgen

#endif //JBINDGEN_STRUCTGENERATOR_H
