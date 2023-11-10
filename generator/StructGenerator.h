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

        const PFN_rename structRename;
        const PFN_rename memberRename;
        const PFN_decodeGetter decodeGetter;
        const PFN_decodeSetter decodeSetter;

    public:

        StructGenerator(StructDeclaration declaration, std::string structsDir, std::string packageName,
                        PFN_rename structRename, PFN_rename memberRename,
                        PFN_decodeGetter decodeGetter, PFN_decodeSetter decodeSetter)
                : declaration(std::move(declaration)), structsDir(std::move(structsDir)),
                  packageName(std::move(packageName)),
                  structRename(structRename), memberRename(memberRename),
                  decodeGetter(decodeGetter), decodeSetter(decodeSetter) {
        }

        std::string makeGetterSetter(const std::string &structName, void *memberRenameUserData,
                                     void *decodeGetterUserData, void *decodeSetterUserData);

        void build(void *structRenameUserData, void *memberRenameUserData,
                   void *decodeGetterUserData, void *decodeSetterUserData);
    };

} // jbindgen

#endif //JAVABINDGEN_STRUCTGENERATOR_H
