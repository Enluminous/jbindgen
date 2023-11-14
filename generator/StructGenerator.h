//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_STRUCTGENERATOR_H
#define JAVABINDGEN_STRUCTGENERATOR_H

#include <utility>

#include "../analyser/StructDeclaration.h"
#include "GenUtils.h"


namespace jbindgen {
    class StructGenerator {
        const StructDeclaration declaration;
        const std::string structsDir;
        const std::string packageName;

        const PFN_structName structRename;
        const PFN_structMemberName memberRename;
        const PFN_decodeGetter decodeGetter;
        const PFN_decodeSetter decodeSetter;

        std::string makeGetterSetter(const std::string &structName, void *memberRenameUserData,
                                     void *decodeGetterUserData, void *decodeSetterUserData);

    public:
        StructGenerator(StructDeclaration declaration, std::string structsDir, std::string packageName,
                        PFN_structName structRename, PFN_structMemberName memberRename,
                        PFN_decodeGetter decodeGetter, PFN_decodeSetter decodeSetter);

        void build(void *structNameUserData, void *memberNameUserData,
                   void *decodeGetterUserData, void *decodeSetterUserData);
    };
} // jbindgen

#endif //JAVABINDGEN_STRUCTGENERATOR_H
